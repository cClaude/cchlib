package com.googlecode.cchlib.apps.duplicatefiles.gui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.TooManyListenersException;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.Tools;
import com.googlecode.cchlib.apps.duplicatefiles.common.AboutDialog;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.PreferencesDialogWB;
import com.googlecode.cchlib.apps.emptydirectories.gui.RemoveEmptyDirectoriesStandaloneApp;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.i18n.config.DefaultI18nBundleFactory;
import com.googlecode.cchlib.i18n.config.I18nPrepAutoUpdatable;
import com.googlecode.cchlib.swing.menu.LookAndFeelMenu;
import com.googlecode.cchlib.util.duplicate.MessageDigestFile;
import cx.ath.choisnet.util.HashMapSet;

/**
 *
 */
final public class DuplicateFilesFrame
    extends DuplicateFilesFrameWB
        implements I18nPrepAutoUpdatable
{
    private static final long serialVersionUID = 2L;
    static final Logger logger = Logger.getLogger( DuplicateFilesFrame.class );
    private RemoveEmptyDirectoriesStandaloneApp removeEmptyDirectories;
    private ActionListener mainActionListener;

    HashMapSet<String,KeyFileState> duplicateFiles = new HashMapSet<String,KeyFileState>();

    private int                 state;
    private int                 subState;
    private final static int    STATE_SELECT_DIRS      = 0;
    private final static int    STATE_SEARCH_CONFIG    = 1;
    private final static int    STATE_SEARCHING        = 2;
    private final static int    STATE_RESULTS          = 3;
    private final static int    STATE_CONFIRM          = 4;
    private final static int    SUBSTATE_CONFIRM_INIT  = 0;
    private final static int    SUBSTATE_CONFIRM_DONE  = 1;

    private AutoI18n autoI18n;

    private Icon iconContinue;
    private Icon iconRestart;

    @I18nString private String txtContinue  = "Continue";
    @I18nString private String txtRestart   = "Restart";
    @I18nString private String txtRemove    = "Remove";
    @I18nString private String txtDeleteNow = "Delete now";
    @I18nString private String txtBack      = "Back";
    @I18nString private String txtCancel            = "Cancel";
    @I18nString private String txtClearSelection    = "Clear selection";

    public DuplicateFilesFrame(
        final DFToolKit dfToolKit
        )
        throws HeadlessException, TooManyListenersException
    {
        super( dfToolKit );

        //
        // Menu: configMode
        //
        {
            Enumeration<AbstractButton> modeEntriesEnum = getButtonGroupConfigMode().getElements();
            ConfigMode                  configMode      = getDFToolKit().getPreferences().getConfigMode();

            while( modeEntriesEnum.hasMoreElements() ) {
                AbstractButton  entry   = modeEntriesEnum.nextElement();
                Object          cf      = entry.getClientProperty( ConfigMode.class );

                entry.setSelected( configMode.equals( cf ) );
                }
        }

        //
        // Menu: Locale
        //
        final Locale locale = getDFToolKit().getPreferences().getLocale();
        {
            Enumeration<AbstractButton> localEntriesEnum = getButtonGroupLanguage().getElements();

            while( localEntriesEnum.hasMoreElements() ) {
                AbstractButton  entry   = localEntriesEnum.nextElement();
                Object          l       = entry.getClientProperty( Locale.class );

                //logger.info( "getButtonGroupLanguage() : entry=" + entry );
                //logger.info( "getButtonGroupLanguage() : l=" + l );

                if( locale == null ) {
                    entry.setSelected( l == null );
                    }
                else {
                    entry.setSelected( locale.equals( l ) );
                    }
                }
        }

        // Init i18n
        if( logger.isTraceEnabled() ) {
            logger.info( "I18n Init: Locale.getDefault()=" + Locale.getDefault() );
            logger.info( "I18n Init: locale = " + locale );
            logger.info( "I18n Init: getMessagesBundle() = " + this.getMessagesBundle() );
            }
        
        // Apply i18n !
        this.autoI18n = DefaultI18nBundleFactory.createDefaultI18nBundle( locale, this ).getAutoI18n();
        performeI18n( autoI18n );

        setSize( getDFToolKit().getPreferences().getWindowDimension() );

        // Init display
        initFixComponents();
        updateDisplayAccordingState();
        logger.info( "DuplicateFilesFrame() done." );
    }

    @Override // I18nPrepAutoUpdatable
    public void performeI18n(AutoI18n autoI18n)
    {
        autoI18n.performeI18n(this,this.getClass());
        autoI18n.performeI18n(getDFToolKit(),getDFToolKit().getClass());
        
        updateI18nData();
        getDuplicateFilesMainPanel().performeI18n( autoI18n );
        getRemoveEmptyDirectoriesPanel().performeI18n( autoI18n );
    }

    @Override // I18nPrepAutoUpdatable
    public String getMessagesBundle()
    {
        return getDFToolKit().getMessagesBundle();
    }

    private void initFixComponents()
    {
        setIconImage( getDFToolKit().getResources().getAppImage() );

        this.iconContinue = getDFToolKit().getResources().getContinueIcon();
        this.iconRestart  = getDFToolKit().getResources().getRestartIcon();

        this.state = STATE_SELECT_DIRS;

        getDuplicateFilesMainPanel().initFixComponents();
        
        // initDynComponents
        //MenuHelper.buildLookAndFeelMenu( this, getJMenuLookAndFeel() );
        LookAndFeelMenu lafMenu = new LookAndFeelMenu( this );
        //FIXME to test
        lafMenu.addChangeLookAndFeelListener( getDuplicateFilesMainPanel().getJPanel1Config() );
        lafMenu.buildMenu( getJMenuLookAndFeel() );

        UIManager.addPropertyChangeListener(
            new PropertyChangeListener()
            {
                @Override
                public void propertyChange( PropertyChangeEvent e )
                {
                    if( "lookAndFeel".equals( e.getPropertyName() ) ) {
                        LookAndFeel oldLAF = LookAndFeel.class.cast( e.getOldValue() );
                        LookAndFeel newLAF = LookAndFeel.class.cast( e.getNewValue() );

                        if( ! newLAF.equals( oldLAF ) ) {
                             //getJPanel1Config().initFixComponents();
                             //
                             // Add extra customization here
                             //

                            if( DuplicateFilesFrame.this.removeEmptyDirectories != null ) {
                                SwingUtilities.updateComponentTreeUI(
                                    DuplicateFilesFrame.this.removeEmptyDirectories
                                    );
                                }
                            }
                        }
                }
            });
        super.setSize( getDFToolKit().getPreferences().getWindowDimension() );
    }

    private void updateDisplayAccordingState()
    {
        final Runnable safeRunner = new Runnable()
        {
            @Override
            public void run()
            {
                logger.debug( "updateDisplayAccordState: " + state );

                //getJTabbedPaneMain().setSelectedIndex( state );
                getDuplicateFilesMainPanel().selectedPanel( state );

                getDuplicateFilesMainPanel().getJButtonNextStep().setText( txtContinue );
                getDuplicateFilesMainPanel().getJButtonNextStep().setIcon( iconContinue );

                getDuplicateFilesMainPanel().getJButtonRestart().setText( txtRestart );
                getDuplicateFilesMainPanel().getJButtonRestart().setIcon( iconRestart );

                getDuplicateFilesMainPanel().getJButtonCancel().setText( txtCancel );

                if( state == STATE_SELECT_DIRS ) {
                    getDuplicateFilesMainPanel().getJPanel2Searching().clear();
                    getDuplicateFilesMainPanel().getJPanel3Result().clear();

                    getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( false );
                    getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( true );
                    }
                else if( state == STATE_SEARCH_CONFIG ) {
                    getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( true );
                    getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( true );
                    }
                else if( state == STATE_SEARCHING ) {
                    getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( false );
                    getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( false );
                    getJMenuLookAndFeel().setEnabled( false );

                    try {
                        getDuplicateFilesMainPanel().getJPanel2Searching().prepareScan(
                                new MessageDigestFile(
                                        "MD5",
                                        getDFToolKit().getPreferences().getMessageDigestBufferSize()
                                        ),
                                    getDuplicateFilesMainPanel().getJPanel1Config().IsIgnoreEmptyFiles()
                                );
                        }
                    catch( NoSuchAlgorithmException ignore ) {
                        logger.error( ignore );
                        }

                    new Thread( new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                getDuplicateFilesMainPanel().getJPanel2Searching().doScan(
                                    getDuplicateFilesMainPanel().getJPanel0Select().entriesToScans(),
                                    getDuplicateFilesMainPanel().getJPanel0Select().entriesToIgnore(),
                                    getDuplicateFilesMainPanel().getJPanel1Config().getFileFilterBuilders(),
                                    duplicateFiles
                                    );
                                getJMenuLookAndFeel().setEnabled( true );
                                getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( true );
                            }
                        }, "STATE_SEARCHING").start();
                    }
                else if( state == STATE_RESULTS ) {
                    getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( false );
                    getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( false );
                    getDuplicateFilesMainPanel().getJButtonNextStep().setText( txtRemove );
                    getDuplicateFilesMainPanel().getJButtonCancel().setText( txtClearSelection );
                    getDuplicateFilesMainPanel().getJButtonCancel().setEnabled( true );

                    SwingUtilities.invokeLater( new Runnable() {
                        @Override
                        public void run()
                        {
                            //getJPanel3Result().initDisplay();
                            getDuplicateFilesMainPanel().getJPanel3Result().clear();
                            getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( true );
                            getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( true );
                        }} );
                    }
                else if( state == STATE_CONFIRM ) {
                    getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( true );
                    getDuplicateFilesMainPanel().getJButtonRestart().setText( txtBack );

                    if( subState == SUBSTATE_CONFIRM_INIT ) {
                        getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( true );
                        getDuplicateFilesMainPanel().getJButtonNextStep().setText( txtDeleteNow  );
                        getDuplicateFilesMainPanel().getJPanel4Confirm().populate( duplicateFiles );
                        }
                    else {
                        getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( false );
                        }
                    }
            }
        };
        Tools.invokeLater( safeRunner, "updateDisplayAccordingState()" );
    }

    private void jButtonNextStep_ActionPerformed()
    {
        if( getDuplicateFilesMainPanel().getJButtonNextStep().isEnabled() ) {
            logger.info( "Next: " + state );
            getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( false );

            if( state == STATE_SELECT_DIRS ) {
                if( getDuplicateFilesMainPanel().getJPanel0Select().getEntriesToScanSize() > 0 ) {
                    state = STATE_SEARCH_CONFIG;
                    }
                else {
                    getDFToolKit().beep();
                    logger.info( "No dir selected" );
                    // TODO: Show alert
                    }
                }
            else if( state == STATE_SEARCH_CONFIG ) {
                state = STATE_SEARCHING;
                }
            else if( state == STATE_SEARCHING ) {
                state = STATE_RESULTS;
                }
            else if( state == STATE_RESULTS ) {
                state = STATE_CONFIRM;
                subState = SUBSTATE_CONFIRM_INIT;
                }
            else if( state == STATE_CONFIRM ) {
                if( subState == SUBSTATE_CONFIRM_INIT ) {
                    getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( false );

                    Tools.invokeLater(new Runnable() {
                        @Override
                        public void run()
                        {
                            getDuplicateFilesMainPanel().getJPanel4Confirm().doDelete( duplicateFiles );

                            //state = STATE_RESULTS;
                            subState = SUBSTATE_CONFIRM_DONE;
                            updateDisplayAccordingState();
                        }
                        }, "SUBSTATE_CONFIRM_INIT");
                    return;
                    }
                }

            updateDisplayAccordingState();
        }
    }

    @Override
    public ActionListener getActionListener()
    {
        if( this.mainActionListener == null ) {
            this.mainActionListener = new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent event )
                {
                    switch( event.getActionCommand() ) {
                        case ACTIONCMD_EXIT :
                            exitApplication();
                            break;

                        case DuplicateFilesMainPanel.ACTIONCMD_RESTART :
                            if( getDuplicateFilesMainPanel().getJButtonRestart().isEnabled() ) {
                                if( state == STATE_CONFIRM ) {
                                    state = STATE_RESULTS;
                                    }
                                else {
                                    state = STATE_SELECT_DIRS;
                                    getDuplicateFilesMainPanel().getJPanel2Searching().clear();
                                    }

                                updateDisplayAccordingState();
                                }
                            break;

                        case DuplicateFilesMainPanel.ACTIONCMD_NEXT:
                            jButtonNextStep_ActionPerformed();
                            break;

                        case DuplicateFilesMainPanel.ACTIONCMD_CANCEL :
                            if( getDuplicateFilesMainPanel().getJButtonCancel().isEnabled() ) {
                                getDuplicateFilesMainPanel().getJButtonCancel().setEnabled( false );
                                getDuplicateFilesMainPanel().getJPanel2Searching().cancelProcess();
                                getDuplicateFilesMainPanel().getJPanel3Result().clearSelected();
                                }
                            break;

//                        case ACTIONCMD_SET_LOCALE :
//                            {
//                            AbstractButton sourceLocale = AbstractButton.class.cast( event.getSource() );
//                            logger.debug( "source: " + sourceLocale );
//
//                            Locale locale = Locale.class.cast( sourceLocale.getClientProperty( Locale.class ) );
//                            logger.debug( "locale: " + locale );
//
//                            getDFToolKit().getPreferences().setLocale( locale );
//
//                            setGuiLocale( locale );
//                            }
//                            break;

                        case ACTIONCMD_SET_MODE :
                            {
                            AbstractButton sourceConfigMode = AbstractButton.class.cast( event.getSource() );
                            logger.debug( "source: " + sourceConfigMode );

                            getDFToolKit().getPreferences().setConfigMode(
                                ConfigMode.class.cast(
                                    sourceConfigMode.getClientProperty( ConfigMode.class )
                                    )
                                );
                            logger.debug( "ConfigMode:" + getDFToolKit().getPreferences().getConfigMode() );

                            getDuplicateFilesMainPanel().getJPanel1Config().updateDisplay( true );
                            getDuplicateFilesMainPanel().getJPanel3Result().updateDisplay();
                            //TODO: more panel ?
                            }
                            break;

                        case ACTIONCMD_PREFS :
                            Tools.run( new Runnable() {
                                @Override
                                public void run()
                                {
                                    openPreferences();
                                }}, ACTIONCMD_PREFS );
                            break;

                        case ACTIONCMD_ABOUT :
                            Tools.run( new Runnable() {
                                @Override
                                public void run()
                                {
                                    openAbout();
                                }}, ACTIONCMD_ABOUT );
                            break;
                            
                        default:
                            logger.warn( "Undefined ActionCommand: " + event.getActionCommand() );
                            break;
                    }
                }
            };
        }
        return this.mainActionListener;
    }

    protected void exitApplication()
    {
        // TODO Perform some checks: running : this frame ?
        // TODO Perform some checks: running : empty directories frame ?
        // TODO Save prefs ???

        System.exit( 0 );
    }

    private void openPreferences()
    {
        logger.info( "openPreferences() : " + getDFToolKit().getPreferences() );

        PreferencesDialogWB dialog = new PreferencesDialogWB(
                getDFToolKit().getPreferences(),
                getSize()
                );
        dialog.setVisible( true );

        logger.info( "openPreferences done : " + getDFToolKit().getPreferences() );
    }

    public void openAbout()
    {
        AboutDialog.open( getDFToolKit(), this.autoI18n );
        
        logger.info( "openAbout done" );
    }

    public void initComponentsJPanelConfirm()
    {
        getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( false );

        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                logger.info( "initComponentsJPanelConfirm begin" );

                try {
                    Thread.sleep( 1000 );
                    }
                catch( InterruptedException e ) {
                    logger.warn( "Interrupted", e );
                    }

                logger.info( "initComponentsJPanelConfirm start" );
                getDuplicateFilesMainPanel().getJPanel3Result().populate( DuplicateFilesFrame.this.duplicateFiles );
                getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( true );
                logger.info( "initComponentsJPanelConfirm done" );
            }
        };
        new Thread( r, "initComponentsJPanelConfirm()" ).start();
    }

    public void setJButtonCancelEnabled( boolean b )
    {
        getDuplicateFilesMainPanel().getJButtonCancel().setEnabled( b );
    }

    public boolean isEnabledJButtonCancel()
    {
        return getDuplicateFilesMainPanel().getJButtonCancel().isEnabled();
    }
}
