package com.googlecode.cchlib.apps.duplicatefiles.gui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TooManyListenersException;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.Tools;
import com.googlecode.cchlib.apps.duplicatefiles.common.AboutDialog;
import com.googlecode.cchlib.apps.duplicatefiles.gui.prefs.PreferencesDialogWB;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.PreferencesControler;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.AutoI18nCoreFactory;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.swing.JFrames;
import com.googlecode.cchlib.swing.SafeSwingUtilities;
import com.googlecode.cchlib.swing.menu.LookAndFeelMenu;
import com.googlecode.cchlib.util.HashMapSet;

/**
 *
 */
@I18nName("DuplicateFilesFrame")
final public class DuplicateFilesFrame
    extends DuplicateFilesFrameWB
        implements I18nAutoCoreUpdatable
{
    private static final long serialVersionUID = 2L;
    private static final Logger LOGGER = Logger.getLogger( DuplicateFilesFrame.class );

    private ActionListener mainActionListener;

    private final Map<String,Set<KeyFileState>> duplicateFiles = new HashMapSet<>();

    private DuplicateFilesState   state;
    private int     subState;

    private static final int    SUBSTATE_CONFIRM_INIT  = 0;
    private static final int    SUBSTATE_CONFIRM_DONE  = 1;

    private final AutoI18nCore autoI18n;

    private Icon iconContinue;
    private Icon iconRestart;

    public DuplicateFilesFrame(
        final PreferencesControler preferences
        )
        throws HeadlessException, TooManyListenersException
    {
        super( preferences );

        // Menu: configMode
        buildConfigModeMenu();

        // Menu: Locale
        buildLocaleMenu();

        // Apply i18n !
        this.autoI18n = initI18n();

        setSize( getDFToolKit().getPreferences().getWindowDimension() );

        // Init display
        initFixComponents();
        updateDisplayAccordingState();
        LOGGER.info( "DuplicateFilesFrame() done." );
    }

    private AutoI18nCore initI18n()
    {
        final AutoI18nCore autoI18n = AutoI18nCoreFactory.createAutoI18nCore(
                getDFToolKit().getAutoI18nConfig(),
                getDFToolKit().getI18nResourceBundleName(),
                getDFToolKit().getValidLocale()
                );
        performeI18n( autoI18n );

        return autoI18n;
    }

    private void buildLocaleMenu()
    {
        final Locale locale = getDFToolKit().getPreferences().getLocale();

        {
            final Enumeration<AbstractButton> localEntriesEnum = getButtonGroupLanguage().getElements();

            while( localEntriesEnum.hasMoreElements() ) {
                final AbstractButton  entry   = localEntriesEnum.nextElement();
                final Object          l       = entry.getClientProperty( Locale.class );

                if( locale == null ) {
                    entry.setSelected( l == null );
                    }
                else {
                    entry.setSelected( locale.equals( l ) );
                    }
                }
        }

        // Init i18n
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.info( "I18n Init: Locale.getDefault()=" + Locale.getDefault() );
            LOGGER.info( "I18n Init: locale = " + locale );
            LOGGER.info( "I18n Init: getValidLocale() = " + getDFToolKit().getValidLocale() );
            LOGGER.info( "I18n Init: getI18nResourceBundleName() = " + getDFToolKit().getI18nResourceBundleName() );
            }
    }

    private void buildConfigModeMenu()
    {
        final Enumeration<AbstractButton> modeEntriesEnum = getButtonGroupConfigMode().getElements();
        final ConfigMode                  configMode      = getDFToolKit().getPreferences().getConfigMode();

        while( modeEntriesEnum.hasMoreElements() ) {
            final AbstractButton  entry   = modeEntriesEnum.nextElement();
            final Object          cf      = entry.getClientProperty( ConfigMode.class );

            entry.setSelected( configMode.equals( cf ) );
            }
    }

    @Override // I18nPrepHelperAutoUpdatable
    public void performeI18n(final AutoI18nCore autoI18n)
    {
        autoI18n.performeI18n(this,this.getClass());
        autoI18n.performeI18n(getDFToolKit(),getDFToolKit().getClass());

        //updateI18nData();
        getDuplicateFilesMainPanel().performeI18n( autoI18n );
        getRemoveEmptyDirectoriesPanel().performeI18n( autoI18n );
        getDeleteEmptyFilesPanel().performeI18n( autoI18n );
    }

    private void initFixComponents()
    {
        setIconImage( getDFToolKit().getResources().getAppImage() );

        this.iconContinue = getDFToolKit().getResources().getContinueIcon();
        this.iconRestart  = getDFToolKit().getResources().getRestartIcon();

        this.state = DuplicateFilesState.STATE_SELECT_DIRS;

        getDuplicateFilesMainPanel().initFixComponents();

        // initDynComponents
        final LookAndFeelMenu lafMenu = new LookAndFeelMenu( this );

        lafMenu.addChangeLookAndFeelListener( getDuplicateFilesMainPanel().getJPanel1Config() );
        lafMenu.buildMenu( getJMenuLookAndFeel() );

        // Handle lookAndFeel dynamics modifications.
        UIManager.addPropertyChangeListener( event -> {
            if( "lookAndFeel".equals( event.getPropertyName() ) ) {
                handleLookAndFeelChange( event );
                }
        } );
        super.setSize( getDFToolKit().getPreferences().getWindowDimension() );
    }

    private void handleLookAndFeelChange( final PropertyChangeEvent event )
    {
        final LookAndFeel oldLAF = LookAndFeel.class.cast( event.getOldValue() );
        final LookAndFeel newLAF = LookAndFeel.class.cast( event.getNewValue() );

        if( ! newLAF.equals( oldLAF ) ) {
             //
             // Add extra customization here
             //
            }
    }

    private void updateDisplayAccordingState()
    {
        final Runnable safeRunner = () -> {
            applyConfigMode();

            LOGGER.debug( "updateDisplayAccordState: " + this.state );

            getDuplicateFilesMainPanel().selectedPanel( this.state );
            getDuplicateFilesMainPanel().getJButtonNextStep().setText( getTxtContinue() );
            getDuplicateFilesMainPanel().getJButtonNextStep().setIcon( this.iconContinue );
            getDuplicateFilesMainPanel().getJButtonRestart().setText( getTxtRestart() );
            getDuplicateFilesMainPanel().getJButtonRestart().setIcon( this.iconRestart );
            getDuplicateFilesMainPanel().getJButtonCancel().setText( getTxtCancel() );

            switch( this.state ) {
                case STATE_CONFIRM:
                    getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( true );
                    getDuplicateFilesMainPanel().getJButtonRestart().setText( getTxtBack() );

                    if( this.subState == SUBSTATE_CONFIRM_INIT ) {
                        getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( true );
                        getDuplicateFilesMainPanel().getJButtonNextStep().setText( getTxtDeleteNow()  );
                        getDuplicateFilesMainPanel().getJPanel4Confirm().populate( this.duplicateFiles );
                    }
                    else {
                        getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( false );
                    }
                    break;

                case STATE_RESULTS:
                    getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( false );
                    getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( false );
                    getDuplicateFilesMainPanel().getJButtonNextStep().setText( getTxtRemove() );
                    getDuplicateFilesMainPanel().getJButtonCancel().setText( getTxtClearSelection() );
                    getDuplicateFilesMainPanel().getJButtonCancel().setEnabled( true );
                    SwingUtilities.invokeLater(() -> {
                        getDuplicateFilesMainPanel().getJPanel3Result().clear();
                        getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( true );
                        getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( true );
                    });
                    break;

                case STATE_SEARCHING:
                    getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( false );
                    getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( false );
                    getJMenuLookAndFeel().setEnabled( false );

                    new Thread(() -> {
                        getDuplicateFilesMainPanel().getJPanel2Searching().startScan(
                                getDFToolKit().getPreferences().getMessageDigestAlgorithm(),
                                getDFToolKit().getPreferences().getMessageDigestBufferSize(),
                                getDuplicateFilesMainPanel().getJPanel1Config().isIgnoreEmptyFiles(),
                                getDFToolKit().getPreferences().getMaxParallelFilesPerThread(),
                                getDuplicateFilesMainPanel().getJPanel0Select().entriesToScans(),
                                getDuplicateFilesMainPanel().getJPanel0Select().entriesToIgnore(),
                                getDuplicateFilesMainPanel().getJPanel1Config().getFileFilterBuilders(),
                                this.duplicateFiles
                        );
                        getJMenuLookAndFeel().setEnabled( true );
                        getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( true );
                    }, "STATE_SEARCHING").start();
                    break;

                case STATE_SEARCH_CONFIG:
                    getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( true );
                    getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( true );
                    break;

                case STATE_SELECT_DIRS:
                    getDuplicateFilesMainPanel().getJPanel2Searching().clear();
                    getDuplicateFilesMainPanel().getJPanel3Result().clear();

                    getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( false );
                    getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( true );
                    break;
            }
        };
        SafeSwingUtilities.invokeLater( safeRunner, "updateDisplayAccordingState()" );
    }

    private void jButtonNextStep_ActionPerformed()
    {
        if( getDuplicateFilesMainPanel().getJButtonNextStep().isEnabled() ) {
            LOGGER.info( "Next: " + this.state );
            getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( false );

            if( this.state == DuplicateFilesState.STATE_SELECT_DIRS ) {
                if( getDuplicateFilesMainPanel().getJPanel0Select().getEntriesToScanSize() > 0 ) {
                    this.state = DuplicateFilesState.STATE_SEARCH_CONFIG;
                    }
                else {
                    getDFToolKit().beep();

                    LOGGER.info( "No dir selected" );
                    // TODO: Show alert
                    }
                }
            else if( this.state == DuplicateFilesState.STATE_SEARCH_CONFIG ) {
                this.state = DuplicateFilesState.STATE_SEARCHING;
                }
            else if( this.state == DuplicateFilesState.STATE_SEARCHING ) {
                this.state = DuplicateFilesState.STATE_RESULTS;
                }
            else if( this.state == DuplicateFilesState.STATE_RESULTS ) {
                this.state = DuplicateFilesState.STATE_CONFIRM;
                this.subState = SUBSTATE_CONFIRM_INIT;
                }
            else if( this.state == DuplicateFilesState.STATE_CONFIRM ) {
                if( this.subState == SUBSTATE_CONFIRM_INIT ) {
                    getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( false );

                    SafeSwingUtilities.invokeLater(() -> {
                        getDuplicateFilesMainPanel().getJPanel4Confirm().doDelete( this.duplicateFiles );

                        //state = STATE_RESULTS;
                        this.subState = SUBSTATE_CONFIRM_DONE;
                        updateDisplayAccordingState();
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
            this.mainActionListener = (final ActionEvent event) -> {
                switch( event.getActionCommand() ) {
                    case ACTIONCMD_EXIT:
                        exitApplication();
                        break;
                    case DuplicateFilesMainPanel.ACTIONCMD_RESTART:
                        if( getDuplicateFilesMainPanel().getJButtonRestart().isEnabled() ) {
                            if( this.state == DuplicateFilesState.STATE_CONFIRM ) {
                                this.state = DuplicateFilesState.STATE_RESULTS;
                            } else {
                                this.state = DuplicateFilesState.STATE_SELECT_DIRS;
                                getDuplicateFilesMainPanel().getJPanel2Searching().clear();
                            }

                            updateDisplayAccordingState();
                        }
                        break;
                    case DuplicateFilesMainPanel.ACTIONCMD_NEXT:
                        jButtonNextStep_ActionPerformed();
                        break;
                    case DuplicateFilesMainPanel.ACTIONCMD_CANCEL:
                        if( getDuplicateFilesMainPanel().getJButtonCancel().isEnabled() ) {
                            getDuplicateFilesMainPanel().getJButtonCancel().setEnabled( false );
                            getDuplicateFilesMainPanel().getJPanel2Searching().cancelProcess();
                            getDuplicateFilesMainPanel().getJPanel3Result().clearSelected();
                        }
                        break;
                    case ACTIONCMD_SET_MODE: {
                        final AbstractButton sourceConfigMode = AbstractButton.class.cast( event.getSource() );
                        LOGGER.debug( "source: " + sourceConfigMode );

                        getDFToolKit().getPreferences().setConfigMode( ConfigMode.class.cast( sourceConfigMode.getClientProperty( ConfigMode.class ) ) );

                        applyConfigMode();
                    }
                        break;
                    case ACTIONCMD_PREFS:
                        Tools.run( this::openPreferences, ACTIONCMD_PREFS );
                        break;
                    case ACTIONCMD_ABOUT:
                        Tools.run( this::openAbout, ACTIONCMD_ABOUT );
                        break;
                    default:
                        LOGGER.warn( "Undefined ActionCommand: " + event.getActionCommand() );
                        break;
                }
            };
        }
        return this.mainActionListener;
    }

    protected void applyConfigMode()
    {
        final ConfigMode mode = getDFToolKit().getPreferences().getConfigMode();
        LOGGER.debug( "ConfigMode:" + mode );

        getDuplicateFilesMainPanel().getJPanel1Config().updateDisplay( true );
        getDuplicateFilesMainPanel().getJPanel3Result().updateDisplay();

        final boolean advanced = mode != ConfigMode.BEGINNER;

        setEnabledAt( REMOVE_EMPTY_DIRECTORIES_TAB, advanced );
        setEnabledAt( DELETE_EMPTY_FILES_TAB, advanced );
        //TODO: more panel ?
    }

    @Override
    @SuppressWarnings("squid:S1147")
    protected void exitApplication()
    {
        // TODO Perform some checks: running : this frame ?
        // TODO Perform some checks: running : empty directories frame ?
        // TODO Save prefs ???

        System.exit( 0 ); // $codepro.audit.disable noExplicitExit
    }

    private void openPreferences()
    {
        LOGGER.info( "openPreferences() : " + getDFToolKit().getPreferences() );

        final PreferencesControler preferences = getDFToolKit().getPreferences();
        final PreferencesDialogWB dialog = new PreferencesDialogWB(
                getSize()
                );
        dialog.performeI18n( this.autoI18n );
        dialog.setVisible( true );

        JFrames.handleMinimumSize(dialog, preferences.getMinimumPreferenceDimension());

        LOGGER.info( "openPreferences done : " + preferences );
    }

    public void openAbout()
    {
        AboutDialog.open( this.autoI18n );

        LOGGER.info( "openAbout done" );
    }

    public void initComponentsJPanelConfirm()
    {
        getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( false );

        final Runnable task = () -> {
            LOGGER.info( "initComponentsJPanelConfirm begin" );

            try {
                Thread.sleep( 1000 );
            }
            catch( final InterruptedException e ) {
                LOGGER.warn( "Interrupted", e );
            }

            try {
                LOGGER.info( "initComponentsJPanelConfirm start" );
                getDuplicateFilesMainPanel().getJPanel3Result().populate( DuplicateFilesFrame.this.duplicateFiles );
            } finally {
                getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( true );
                LOGGER.info( "initComponentsJPanelConfirm done" );
            }
        };
        new Thread( task, "initComponentsJPanelConfirm()" ).start();
    }

    public void setJButtonCancelEnabled( final boolean b )
    {
        getDuplicateFilesMainPanel().getJButtonCancel().setEnabled( b );
    }

    public boolean isEnabledJButtonCancel()
    {
        return getDuplicateFilesMainPanel().getJButtonCancel().isEnabled();
    }

    /** Add entry from CLI */
    public void addEntry( final String entry )
    {
        final File entryFile = new File( entry );

        getDuplicateFilesMainPanel().getJPanel0Select().addEntry( entryFile , false );
    }
}
