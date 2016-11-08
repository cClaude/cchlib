package com.googlecode.cchlib.apps.duplicatefiles.swing.gui;

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
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.swing.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.swing.about.AboutDialog;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.search.ScanParams;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.prefs.PreferencesDialogWB;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.PreferencesControler;
import com.googlecode.cchlib.apps.duplicatefiles.swing.services.AutoI18nCoreService;
import com.googlecode.cchlib.apps.duplicatefiles.swing.tools.SerializableIcon;
import com.googlecode.cchlib.apps.duplicatefiles.swing.tools.Tools;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.swing.JFrames;
import com.googlecode.cchlib.swing.SafeSwingUtilities;
import com.googlecode.cchlib.swing.menu.LookAndFeelMenu;
import com.googlecode.cchlib.util.HashMapSet;

/**
 *
 */
@I18nName("DuplicateFilesFrame")
public final class DuplicateFilesFrame // NOSONAR
    extends DuplicateFilesFrameWB
        implements I18nAutoCoreUpdatable
{
    private final class UpdateDisplayTask implements Runnable {
        @Override
        public void run()
        {
            applyConfigMode();

            LOGGER.debug( "updateDisplayAccordState: " + DuplicateFilesFrame.this.mainState );

            getDuplicateFilesMainPanel().selectedPanel( DuplicateFilesFrame.this.mainState );
            getDuplicateFilesMainPanel().getJButtonNextStep().setText( getTxtContinue() );
            getDuplicateFilesMainPanel().getJButtonNextStep().setIcon( DuplicateFilesFrame.this.iconContinue.getIcon() );
            getDuplicateFilesMainPanel().getJButtonRestart().setText( getTxtRestart() );
            getDuplicateFilesMainPanel().getJButtonRestart().setIcon( DuplicateFilesFrame.this.iconRestart.getIcon() );
            getDuplicateFilesMainPanel().getJButtonCancel().setText( getTxtCancel() );

            switch( DuplicateFilesFrame.this.mainState ) {
                case STATE_CONFIRM:
                    refreshConfirm();
                    break;

                case STATE_RESULTS:
                    refreshResult();
                    break;

                case STATE_SEARCHING:
                    refreshSearching();
                    break;

                case STATE_SEARCH_CONFIG:
                    refreshConfig();
                    break;

                case STATE_SELECT_DIRS:
                    refreshSelectDirs();
                    break;

                default:
                    throw new IllegalArgumentException( "DuplicateFilesFrame.mainState: " + DuplicateFilesFrame.this.mainState );
            }
        }

        private void refreshSelectDirs()
        {
            getDuplicateFilesMainPanel().getJPanel2Searching().clear();
            getDuplicateFilesMainPanel().getJPanel3Result().clear();

            getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( false );
            getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( true );
        }

        private void refreshConfig()
        {
            getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( true );
            getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( true );
        }

        private void refreshSearching()
        {
            getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( false );
            getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( false );
            getJMenuLookAndFeel().setEnabled( false );

            new Thread( ( ) -> {
                final ScanParams scanParan = new ScanParams( //
                        getDFToolKit().getPreferences().getMessageDigestAlgorithm(), //
                        getDFToolKit().getPreferences().getMessageDigestBufferSize(), //
                        getDuplicateFilesMainPanel().getJPanel1Config().isIgnoreEmptyFiles(), //
                        getDFToolKit().getPreferences().getMaxParallelFilesPerThread(), //
                        getDuplicateFilesMainPanel().getJPanel0Select().entriesToScans(), //
                        getDuplicateFilesMainPanel().getJPanel1Config().getFileFilterBuilders(), //
                        DuplicateFilesFrame.this.duplicateFiles );
                getDuplicateFilesMainPanel().getJPanel2Searching().startScan( scanParan );
                getJMenuLookAndFeel().setEnabled( true );
                getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( true );
            }, "STATE_SEARCHING" ).start();
        }

        private void refreshResult()
        {
            getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( false );
            getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( false );
            getDuplicateFilesMainPanel().getJButtonNextStep().setText( getTxtRemove() );
            getDuplicateFilesMainPanel().getJButtonCancel().setText( getTxtClearSelection() );
            getDuplicateFilesMainPanel().getJButtonCancel().setEnabled( true );
            SwingUtilities.invokeLater( ( ) -> {
                getDuplicateFilesMainPanel().getJPanel3Result().clear();
                refreshConfig();
            } );
        }

        private void refreshConfirm()
        {
            getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( true );
            getDuplicateFilesMainPanel().getJButtonRestart().setText( getTxtBack() );

            if( DuplicateFilesFrame.this.subState == SUBSTATE_CONFIRM_INIT ) {
                getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( true );
                getDuplicateFilesMainPanel().getJButtonNextStep().setText( getTxtDeleteNow() );
                getDuplicateFilesMainPanel().getJPanel4Confirm().populate( DuplicateFilesFrame.this.duplicateFiles );
            } else {
                getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( false );
            }
        }
    }

    private static final long serialVersionUID = 3L;
    private static final Logger LOGGER = Logger.getLogger( DuplicateFilesFrame.class );

    private transient ActionListener mainActionListener;

    private final Map<String,Set<KeyFileState>> duplicateFiles = new HashMapSet<>();

    private DuplicateFilesState mainState;
    private int                 subState;

    private static final int    SUBSTATE_CONFIRM_INIT  = 0;
    private static final int    SUBSTATE_CONFIRM_DONE  = 1;

    private SerializableIcon iconContinue;
    private SerializableIcon iconRestart;

    public DuplicateFilesFrame(
        final PreferencesControler preferences
        )
        throws
            HeadlessException, // NOSONAR
            TooManyListenersException
    {
        super( preferences );

        // Menu: configMode
        buildConfigModeMenu();

        // Menu: Locale
        buildLocaleMenu();

        // Apply i18n !
        performeI18n( AutoI18nCoreService.getInstance().getAutoI18nCore() );

        setSize( getDFToolKit().getPreferences().getWindowDimension() );

        // Init display
        initFixComponents();
        updateDisplayAccordingState();
        LOGGER.info( "DuplicateFilesFrame() done." );
    }

    private void buildLocaleMenu()
    {
        final Locale currentLocale = getDFToolKit().getPreferences().getLocale();

        buildLocaleMenu( currentLocale );

        // Init i18n
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.info( "I18n Init: Locale.getDefault()=" + Locale.getDefault() );
            LOGGER.info( "I18n Init: locale = " + currentLocale );
            LOGGER.info( "I18n Init: getValidLocale() = " + getDFToolKit().getValidLocale() );
            LOGGER.info( "I18n Init: getI18nResourceBundleName() = " + getDFToolKit().getI18nResourceBundleName() );
            }
    }

    private void buildLocaleMenu( final Locale currentLocale )
    {
        final Enumeration<AbstractButton> localEntriesEnum = getButtonGroupLanguage().getElements();

        while( localEntriesEnum.hasMoreElements() ) {
            final AbstractButton  entry   = localEntriesEnum.nextElement();
            final Object          l       = entry.getClientProperty( Locale.class );

            if( currentLocale == null ) {
                entry.setSelected( l == null );
                }
            else {
                entry.setSelected( currentLocale.equals( l ) );
                }
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
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n(this,this.getClass());
        autoI18n.performeI18n(getDFToolKit(),getDFToolKit().getClass());

        getDuplicateFilesMainPanel().performeI18n( autoI18n );
        getRemoveEmptyDirectoriesPanel().performeI18n( autoI18n );
        getDeleteEmptyFilesPanel().performeI18n( autoI18n );
    }

    private void initFixComponents()
    {
        setIconImage( getDFToolKit().getResources().getAppImage() );

        this.iconContinue = getDFToolKit().getResources().getContinueSerializableIcon();
        this.iconRestart  = getDFToolKit().getResources().getRestartSerializableIcon();

        this.mainState = DuplicateFilesState.STATE_SELECT_DIRS;

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
        final Runnable safeRunner = new UpdateDisplayTask();

        SafeSwingUtilities.invokeLater( safeRunner, "updateDisplayAccordingState()" );
    }

    private void jButtonNextStepActionPerformed()
    {
        if( getDuplicateFilesMainPanel().getJButtonNextStep().isEnabled() ) {
            LOGGER.info( "Next: " + this.mainState );
            getDuplicateFilesMainPanel().getJButtonNextStep().setEnabled( false );

            switch( this.mainState ) {
                case STATE_SELECT_DIRS:
                    actionPerformedSelectDirs();
                    break;

                case STATE_SEARCH_CONFIG:
                    this.mainState = DuplicateFilesState.STATE_SEARCHING;
                    break;

                case STATE_SEARCHING:
                    this.mainState = DuplicateFilesState.STATE_RESULTS;
                    break;

                case STATE_RESULTS:
                    this.mainState = DuplicateFilesState.STATE_CONFIRM;
                    this.subState  = SUBSTATE_CONFIRM_INIT;
                    break;

                case STATE_CONFIRM:
                    if( actionPerformConfirm() ) {
                        return;
                    }
                    break;

                 default:
                    throw new IllegalArgumentException( "Invalid state: " + this.mainState );
            }

            updateDisplayAccordingState();
        }
    }

    private boolean actionPerformConfirm()
    {
        if( this.subState == SUBSTATE_CONFIRM_INIT ) {
            getDuplicateFilesMainPanel().getJButtonRestart().setEnabled( false );

            SafeSwingUtilities.invokeLater(() -> {
                getDuplicateFilesMainPanel().getJPanel4Confirm().doDelete( this.duplicateFiles );

                this.subState = SUBSTATE_CONFIRM_DONE;
                updateDisplayAccordingState();
            }, "SUBSTATE_CONFIRM_INIT");
            return true;
            }
        return false;
    }

    private void actionPerformedSelectDirs()
    {
        if( getDuplicateFilesMainPanel().getJPanel0Select().getEntriesToScanSize() > 0 ) {
            this.mainState = DuplicateFilesState.STATE_SEARCH_CONFIG;
            }
        else {
            getDFToolKit().beep();

            LOGGER.info( "No dir selected" );
            // TODO: Show alert
            }
    }

    @Override
    public ActionListener getActionListener()
    {
        if( this.mainActionListener == null ) {
            // NOSONAR this.mainActionListener = event -> actionPerformedMainActionListener( event );
            this.mainActionListener = this::actionPerformedMainActionListener;
        }
        return this.mainActionListener;
    }

    private void actionPerformedMainActionListener( final ActionEvent event )
    {
        switch( event.getActionCommand() ) {
            case ACTIONCMD_EXIT:
                exitApplication();
                break;

            case DuplicateFilesMainPanel.ACTIONCMD_RESTART:
                actionCmdRestart();
                break;

            case DuplicateFilesMainPanel.ACTIONCMD_NEXT:
                jButtonNextStepActionPerformed();
                break;

            case DuplicateFilesMainPanel.ACTIONCMD_CANCEL:
                actionCmdCancel();
                break;

            case ACTIONCMD_SET_MODE:
                actionSetMode( event );
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
    }

    private void actionSetMode( final ActionEvent event )
    {
        final AbstractButton sourceConfigMode = AbstractButton.class.cast( event.getSource() );
        LOGGER.debug( "source: " + sourceConfigMode );

        getDFToolKit().getPreferences().setConfigMode( ConfigMode.class.cast( sourceConfigMode.getClientProperty( ConfigMode.class ) ) );

        applyConfigMode();
    }

    private void actionCmdCancel()
    {
        if( getDuplicateFilesMainPanel().getJButtonCancel().isEnabled() ) {
            getDuplicateFilesMainPanel().getJButtonCancel().setEnabled( false );
            getDuplicateFilesMainPanel().getJPanel2Searching().cancelProcess();
            getDuplicateFilesMainPanel().getJPanel3Result().clearSelected();
        }
    }

    private void actionCmdRestart()
    {
        if( getDuplicateFilesMainPanel().getJButtonRestart().isEnabled() ) {
            if( this.mainState == DuplicateFilesState.STATE_CONFIRM ) {
                this.mainState = DuplicateFilesState.STATE_RESULTS;
            } else {
                this.mainState = DuplicateFilesState.STATE_SELECT_DIRS;
                getDuplicateFilesMainPanel().getJPanel2Searching().clear();
            }

            updateDisplayAccordingState();
        }
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
    protected void exitApplication()
    {
        // TODO Perform some checks: running : this frame ?
        // TODO Perform some checks: running : empty directories frame ?
        // TODO Save prefs ???

        System.exit( 0 ); // $codepro.audit.disable noExplicitExit // NOSONAR
    }

    private void openPreferences()
    {
        LOGGER.info( "openPreferences() : " + getDFToolKit().getPreferences() );

        final PreferencesControler preferences = getDFToolKit().getPreferences();
        final PreferencesDialogWB dialog = new PreferencesDialogWB(
                getSize()
                );
        dialog.performeI18n( AutoI18nCoreService.getInstance().getAutoI18nCore() );
        dialog.setVisible( true );

        JFrames.handleMinimumSize(dialog, preferences.getMinimumPreferenceDimension());

        LOGGER.info( "openPreferences done : " + preferences );
    }

    public void openAbout()
    {
        AboutDialog.open();

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

        getDuplicateFilesMainPanel().getJPanel0Select().addEntry( entryFile );
    }
}
