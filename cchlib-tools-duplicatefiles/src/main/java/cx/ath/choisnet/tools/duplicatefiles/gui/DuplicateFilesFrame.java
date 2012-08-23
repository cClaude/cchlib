package cx.ath.choisnet.tools.duplicatefiles.gui;

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
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.Tools;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.PreferencesDialogWB;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.i18n.config.DefaultI18nBundleFactory;
import com.googlecode.cchlib.i18n.config.I18nPrepAutoUpdatable;
import com.googlecode.cchlib.swing.menu.LookAndFeelMenu;
import cx.ath.choisnet.tools.duplicatefiles.KeyFileState;
import cx.ath.choisnet.tools.emptydirectories.gui.RemoveEmptyDirectories;
import cx.ath.choisnet.util.HashMapSet;
import cx.ath.choisnet.util.checksum.MessageDigestFile;

/**
 *
 */
final public class DuplicateFilesFrame
    extends DuplicateFilesFrameWB
        implements I18nPrepAutoUpdatable
{
    private static final long serialVersionUID = 2L;
    static final Logger logger = Logger.getLogger( DuplicateFilesFrame.class );
    private RemoveEmptyDirectories removeEmptyDirectories;
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
    //@I18nString private String msgStringSavePrefsExceptionTitle =
    //@I18nString private String msgStringAlertLocale = "You need to restart application to apply this language: %s";
    //@I18nString private String msgStringDefaultLocale;
    //@I18nString private String msgStringAlertLocaleTitle = "Change language";

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
        this.autoI18n = DefaultI18nBundleFactory.createDefaultI18nBundle( locale, this ).getAutoI18n();

        setSize( getDFToolKit().getPreferences().getWindowDimension() );

        // Apply i18n !
        performeI18n( autoI18n );

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
        autoI18n.performeI18n(getJPanel0Select(),getJPanel0Select().getClass());
        getJPanel1Config().performeI18n(autoI18n);
        autoI18n.performeI18n(getJPanel2Searching(),getJPanel2Searching().getClass());
        autoI18n.performeI18n(getJPanel3Result(),getJPanel3Result().getClass());
        autoI18n.performeI18n(getJPanel4Confirm(),getJPanel4Confirm().getClass());
    }

    @Override // I18nPrepAutoUpdatable
    public String getMessagesBundle()
    {
//        return ResourcesLoader.class.getPackage().getName()
//                + ".MessagesBundle";
        return getDFToolKit().getMessagesBundle();
    }

    private void initFixComponents()
    {
        setIconImage( getDFToolKit().getImage( "icon.png" ) );

        this.iconContinue = getDFToolKit().getIcon( "continue.png" );
        this.iconRestart  = getDFToolKit().getIcon( "restart.png" );

        this.state = STATE_SELECT_DIRS;

        getJPanel0Select().initFixComponents();
        //getJPanel1Config().initFixComponents();
        getJPanel2Searching().initFixComponents( getDFToolKit() );
        // no need here : getJPanel3Result().populate( duplicateFiles, getDFToolKit() );

        // initDynComponents
        //MenuHelper.buildLookAndFeelMenu( this, getJMenuLookAndFeel() );
        LookAndFeelMenu lafMenu = new LookAndFeelMenu( this );
        //FIXME to test
        lafMenu.addChangeLookAndFeelListener( getJPanel1Config() );
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
                selectedPanel( state );

                getJButtonNextStep().setText( txtContinue );
                getJButtonNextStep().setIcon( iconContinue );

                getJButtonRestart().setText( txtRestart );
                getJButtonRestart().setIcon( iconRestart );

                getJButtonCancel().setText( txtCancel );

                if( state == STATE_SELECT_DIRS ) {
                    getJPanel2Searching().clear();
                    getJPanel3Result().clear();

                    getJButtonRestart().setEnabled( false );
                    getJButtonNextStep().setEnabled( true );
                    }
                else if( state == STATE_SEARCH_CONFIG ) {
                    getJButtonRestart().setEnabled( true );
                    getJButtonNextStep().setEnabled( true );
                    }
                else if( state == STATE_SEARCHING ) {
                    getJButtonRestart().setEnabled( false );
                    getJButtonNextStep().setEnabled( false );
                    getJMenuLookAndFeel().setEnabled( false );

                    try {
                        getJPanel2Searching().prepareScan(
                                new MessageDigestFile(
                                        "MD5",
                                        getDFToolKit().getPreferences().getMessageDigestBufferSize()
                                        ),
                                getJPanel1Config().IsIgnoreEmptyFiles()
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
                                getJPanel2Searching().doScan(
                                    getJPanel0Select().entriesToScans(),
                                    getJPanel0Select().entriesToIgnore(),
                                    getJPanel1Config().getFileFilterBuilders(),
                                    duplicateFiles
                                    );
                                getJMenuLookAndFeel().setEnabled( true );
                                getJButtonRestart().setEnabled( true );
                            }
                        }).start();
                    }
                else if( state == STATE_RESULTS ) {
                    getJButtonRestart().setEnabled( false );
                    getJButtonNextStep().setEnabled( false );
                    getJButtonNextStep().setText( txtRemove );
                    getJButtonCancel().setText( txtClearSelection );
                    getJButtonCancel().setEnabled( true );

                    SwingUtilities.invokeLater( new Runnable() {
                        @Override
                        public void run()
                        {
                            //getJPanel3Result().initDisplay();
                            getJPanel3Result().clear();
                            getJButtonRestart().setEnabled( true );
                            getJButtonNextStep().setEnabled( true );
                        }} );
                    }
                else if( state == STATE_CONFIRM ) {
                    getJButtonRestart().setEnabled( true );
                    getJButtonRestart().setText( txtBack );

                    if( subState == SUBSTATE_CONFIRM_INIT ) {
                        getJButtonNextStep().setEnabled( true );
                        getJButtonNextStep().setText( txtDeleteNow  );
                        getJPanel4Confirm().populate( duplicateFiles );
                        }
                    else {
                        getJButtonNextStep().setEnabled( false );
                        }
                    }
            }
        };
        Tools.invokeLater( safeRunner );
    }

    private void jButtonNextStep_ActionPerformed()
    {
        if( getJButtonNextStep().isEnabled() ) {
            logger.info( "Next: " + state );
            getJButtonNextStep().setEnabled( false );

            if( state == STATE_SELECT_DIRS ) {
                if( getJPanel0Select().getEntriesToScanSize() > 0 ) {
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
                    getJButtonRestart().setEnabled( false );

                    Tools.invokeLater(new Runnable() {
                        @Override
                        public void run()
                        {
                            getJPanel4Confirm().doDelete( duplicateFiles );

                            //state = STATE_RESULTS;
                            subState = SUBSTATE_CONFIRM_DONE;
                            updateDisplayAccordingState();
                        }
                        });
                    return;
                    }
                }

            updateDisplayAccordingState();
        }
    }

    private void jMenuItemDeleteEmptyDirectories_ActionPerformed()
    {
        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                if( removeEmptyDirectories == null ) {
                    removeEmptyDirectories = RemoveEmptyDirectories.start( getDFToolKit() );
                    }
                else {
                    removeEmptyDirectories.setVisible( true );
                    removeEmptyDirectories.setExtendedState( JFrame.NORMAL );
                    }

                //TODO improve here ! add windows handler !

                UIManager.addPropertyChangeListener(
                        new PropertyChangeListener()
                        {
                            @Override
                            public void propertyChange( PropertyChangeEvent e )
                            {
                                // TODO: Refresh display
                            }
                        });
            }
        };

        new Thread( r ).start();
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

                        case ACTIONCMD_RESTART :
                            if( getJButtonRestart().isEnabled() ) {
                                if( state == STATE_CONFIRM ) {
                                    state = STATE_RESULTS;
                                    }
                                else {
                                    state = STATE_SELECT_DIRS;
                                    getJPanel2Searching().clear();
                                    }

                                updateDisplayAccordingState();
                                }
                            break;

                        case ACTIONCMD_NEXT:
                            jButtonNextStep_ActionPerformed();
                            break;

                        case ACTIONCMD_DELETE_EMPTY_DIRECTORIES :
                            jMenuItemDeleteEmptyDirectories_ActionPerformed();
                            break;

                        case ACTIONCMD_CANCEL :
                            if( getJButtonCancel().isEnabled() ) {
                                getJButtonCancel().setEnabled( false );
                                getJPanel2Searching().cancelProcess();
                                getJPanel3Result().clearSelected();
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

                            getJPanel1Config().updateDisplay( true );
                            getJPanel3Result().updateDisplay();
                            //TODO: more panel ?
                            }
                            break;

                        case ACTIONCMD_PREFS :
                            Tools.run( new Runnable() {
                                @Override
                                public void run()
                                {
                                    openPreferences();
                                }} );
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

    public void initComponentsJPanelConfirm()
    {
        getJButtonNextStep().setEnabled( false );

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
                getJPanel3Result().populate( DuplicateFilesFrame.this.duplicateFiles );
                getJButtonNextStep().setEnabled( true );
                logger.info( "initComponentsJPanelConfirm done" );
            }
        };
        new Thread( r ).start();
    }

    public void setJButtonCancelEnabled( boolean b )
    {
        getJButtonCancel().setEnabled( b );
    }

    public boolean isEnabledJButtonCancel()
    {
        return getJButtonCancel().isEnabled();
    }
}
