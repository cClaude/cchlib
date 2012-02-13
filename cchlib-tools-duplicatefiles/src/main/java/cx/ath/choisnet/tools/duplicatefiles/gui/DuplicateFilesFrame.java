package cx.ath.choisnet.tools.duplicatefiles.gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.ResourcesLoader;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.i18n.config.DefaultI18nBundleFactory;
import com.googlecode.cchlib.i18n.config.I18nPrepAutoUpdatable;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.filechooser.accessory.DefaultBookmarksAccessoryConfigurator;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializer;
import cx.ath.choisnet.swing.filechooser.WaitingJFileChooserInitializer;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessory;
import cx.ath.choisnet.swing.filechooser.accessory.TabbedAccessory;
import cx.ath.choisnet.swing.helpers.LookAndFeelHelpers;
import cx.ath.choisnet.tools.duplicatefiles.ConfigData;
import cx.ath.choisnet.tools.duplicatefiles.ConfigMode;
import cx.ath.choisnet.tools.duplicatefiles.DFToolKit;
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
    private static final Logger logger = Logger.getLogger( DuplicateFilesFrame.class );
    private RemoveEmptyDirectories removeEmptyDirectories;
    private DFToolKit dfToolKit;
    private ActionListener mainActionListener;

    /* @serial */
    private JFileChooserInitializer jFileChooserInitializer;
    /* @serial */
    private HashMapSet<String,KeyFileState> duplicateFiles = new HashMapSet<String,KeyFileState>();
    /* @serial */
    private int                 state;
    /* @serial */
    private int                 subState;
    private final static int    STATE_SELECT_DIRS      = 0;
    private final static int    STATE_SEARCH_CONFIG    = 1;
    private final static int    STATE_SEARCHING        = 2;
    private final static int    STATE_RESULTS          = 3;
    private final static int    STATE_CONFIRM          = 4;
    private final static int    SUBSTATE_CONFIRM_INIT  = 0;
    private final static int    SUBSTATE_CONFIRM_DONE  = 1;

    /* @serial */
    private AutoI18n autoI18n;

    private Icon iconContinue;
    private Icon iconRestart;

    @I18nString private String txtContinue  = "Continue";
    @I18nString private String txtRestart   = "Restart";
    @I18nString private String txtRemove    = "Remove";
    @I18nString private String txtDeleteNow = "Delete now";
    @I18nString private String txtBack      = "Back";
    @I18nString private String jFileChooserInitializerTitle     = "Waiting...";
    @I18nString private String jFileChooserInitializerMessage   = "Analyze disk structure";
    @I18nString private String txtopenDesktopExceptionTitle = "Can not open file";

    /* @serial */
    private final ConfigData configData;
    /* @serial */
    private ConfigMode mode = ConfigMode.BEGINNER; // default mode
    /* @serial */
    private int bufferSize = 16 * 1024;

    public DuplicateFilesFrame()
    {
        super();

        initFixComponents();

        // Init i18n
        Locale locale = Locale.getDefault();//this.preferences.getLocale();

        if( logger.isTraceEnabled() ) {
            logger.info( "I18n Init: Locale.getDefault()=" + Locale.getDefault() );
            logger.info( "I18n Init: locale = " + locale );
            logger.info( "I18n Init: getMessagesBundle() = " + this.getMessagesBundle() );
            }
        this.autoI18n = DefaultI18nBundleFactory.createDefaultI18nBundle( locale, this ).getAutoI18n();

        // Apply i18n !
        performeI18n(autoI18n);

        // Init display
        updateDisplayAccordingState();
        logger.info( "DuplicateFilesFrame() done." );

        configData = new ConfigData();
    }

    public void performeI18n(AutoI18n autoI18n)
    {
        autoI18n.performeI18n(this,this.getClass());
        autoI18n.performeI18n(getJPanel0Select(),getJPanel0Select().getClass());
        getJPanel1Config().performeI18n(autoI18n);
        autoI18n.performeI18n(getJPanel2Searching(),getJPanel2Searching().getClass());
        autoI18n.performeI18n(getJPanel3Result(),getJPanel3Result().getClass());
        autoI18n.performeI18n(getJPanel4Confirm(),getJPanel4Confirm().getClass());
    }

    private void initFixComponents()
    {
        setIconImage( getDFToolKit().getImage( "icon.png" ) );

        this.iconContinue = getDFToolKit().getIcon( "continue.png" );
        this.iconRestart  = getDFToolKit().getIcon( "restart.png" );

//        // Moving the Icon in a JButton Component
//        getJButtonNextStep().setVerticalTextPosition(SwingConstants.CENTER);
//        getJButtonNextStep().setHorizontalTextPosition(SwingConstants.LEFT);

//        // Init. mode state
//        getJMenuItemModeBegin().setSelected( true );

        this.state = STATE_SELECT_DIRS;

        getJPanel0Select().initFixComponents( getDFToolKit() );
        getJPanel1Config().initFixComponents( getDFToolKit() );
        getJPanel2Searching().initFixComponents( getDFToolKit() );
        getJPanel3Result().initComponents( duplicateFiles, getDFToolKit() );

        //        // init
        //        modeListener.actionPerformed( null );
        mode = ConfigMode.BEGINNER; // TODO: prefs

        // initDynComponents
        LookAndFeelHelpers.buildLookAndFeelMenu( this, getJMenuLookAndFeel() );

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
                            // TODO extra customization
                            getJPanel1Config().initFixComponents( getDFToolKit() );

                            if( DuplicateFilesFrame.this.removeEmptyDirectories != null ) {
                                // DuplicateFilesFrame.this.removeEmptyDirectories.init();
                                }
                            }
                        }
                }
            });
    }

    /**
     * Make sure to be outside swing even threads
     * @param safeRunner
     */
    private static void invokeLater( final Runnable safeRunner )
    {
        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                SwingUtilities.invokeLater( safeRunner );
            }
        }).start();
    }

    private void updateDisplayAccordingState()
    {
        final Runnable safeRunner = new Runnable()
        {
            @Override
            public void run()
            {
                logger.debug( "updateDisplayAccordState: " + state );

                getJTabbedPaneMain().setSelectedIndex( state );
                getJButtonNextStep().setText( txtContinue );
                getJButtonNextStep().setIcon( iconContinue );
                getJButtonRestart().setText( txtRestart );
                getJButtonRestart().setIcon( iconRestart );

                if( state == STATE_SELECT_DIRS ) {
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
                                new MessageDigestFile( "MD5", bufferSize  ),
                                getJPanel1Config().IsIgnoreEmptyFiles()
                                );
                        }
                    catch( NoSuchAlgorithmException ignore ) {
                        logger.error( ignore );
                        }

                    Runnable r = new Runnable()
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

                            //getJButtonNextStep().setEnabled( true );
                            getJMenuLookAndFeel().setEnabled( true );
                            getJButtonRestart().setEnabled( true );
                        }
                    };

                    new Thread(r ).start();
                    }
                else if( state == STATE_RESULTS ) {
                    getJButtonRestart().setEnabled( false );
                    getJButtonNextStep().setEnabled( false );
                    getJButtonNextStep().setText( txtRemove );

                    SwingUtilities.invokeLater( new Runnable() {
                        @Override
                        public void run()
                        {
                            getJPanel3Result().initDisplay();
                            getJButtonRestart().setEnabled( true );
                            getJButtonNextStep().setEnabled( true );
                        }} );
                    }
                else if( state == STATE_CONFIRM ) {
                    getJButtonRestart().setEnabled( true );
                    getJButtonRestart().setText( txtBack  );

                    if( subState == SUBSTATE_CONFIRM_INIT ) {
                        getJButtonNextStep().setEnabled( true );
                        getJButtonNextStep().setText( txtDeleteNow  );
                        getJPanel4Confirm().initComponents( duplicateFiles );
                        }
                    else {
                        getJButtonNextStep().setEnabled( false );
                        }
                    }
            }
        };
        invokeLater( safeRunner );
    }

    private JFileChooserInitializer getJFileChooserInitializer()
    {
        if( jFileChooserInitializer == null ) {
            final JFileChooserInitializer.DefaultConfigurator configurator = new JFileChooserInitializer.DefaultConfigurator()
            {
                private static final long serialVersionUID = 1L;

                public void perfomeConfig( JFileChooser jfc )
                {
                    super.perfomeConfig( jfc );

                    jfc.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
                    jfc.setMultiSelectionEnabled( true );
                    jfc.setAccessory( new TabbedAccessory()
                            .addTabbedAccessory( new BookmarksAccessory(
                                    jfc,
                                    new DefaultBookmarksAccessoryConfigurator() ) ) );
                }
            };

            //jFileChooserInitializer = new JFileChooserInitializer( configurator );
            jFileChooserInitializer = new WaitingJFileChooserInitializer(
                    configurator,
                    this,
                    jFileChooserInitializerTitle,
                    jFileChooserInitializerMessage
                    );
        }

        return jFileChooserInitializer;
    }

    public static void main()
    {
        final String title = "Duplicate Files Manager";

        logger.info( "starting..." );
        //Locale.setDefault( Locale.ENGLISH ); // Debug Only

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
                    );
            }
        catch( Exception e ) {
            logger.warn( e );
            }

        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                try {
                    DuplicateFilesFrame frame = new DuplicateFilesFrame();
                    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                    frame.setTitle( title );
                    frame.getContentPane().setPreferredSize( frame.getSize() );
                    frame.pack();
                    frame.setLocationRelativeTo( null );
                    frame.setVisible( true );
                    frame.getJFileChooserInitializer();
                    }
                catch( Throwable e ) {
                    logger.fatal( "Can't load application", e );

                    DialogHelper.showMessageExceptionDialog( title, e );
                    }
            }
        } );
    }

    private void jButtonNextStep_ActionPerformed()
    {
        if( getJButtonNextStep().isEnabled() ) {
            logger.info( "Next: " + state );

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
                    getJButtonNextStep().setEnabled( false );
                    getJButtonRestart().setEnabled( false );

                    invokeLater(new Runnable() {
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
                    removeEmptyDirectories = RemoveEmptyDirectories.start();
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
    public DFToolKit getDFToolKit()
    {
        if( this.dfToolKit == null ) {
            this.dfToolKit = new DFToolKit()
            {
                private static final long serialVersionUID = 1L;
                private Locale locale;

                @Override // DFToolKit
                public JFileChooser getJFileChooser()
                {
                    return getJFileChooserInitializer().getJFileChooser();
                }
                @Override // DFToolKit
                public void beep()
                {
                    Toolkit.getDefaultToolkit().beep();
                }
                @Override // DFToolKit
                public void openDesktop( File file )
                {
                    java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

                    try {
                        logger.info( "trying to open: " + file );
                        desktop.open( file );
                        }
                    catch( IOException e ) {
                        // FIXME improve this. Show dialog to user
                        e.printStackTrace();
                        DialogHelper.showMessageExceptionDialog(
                                getMainWindow(),
                                txtopenDesktopExceptionTitle,
                                e
                                );
                        }
                }
                @Override // DFToolKit
                public ConfigMode getConfigMode()
                {
                    return mode;
                }
                @Override // DFToolKit
                public void sleep(long ms)
                {
                    try {
                        Thread.sleep( ms );
                        }
                    catch( InterruptedException ignore ) {
                        }
                }
                @Override // DFToolKit
                public Image getImage(String name)
                {
                    return ResourcesLoader.getImage( name );
                }
                @Override // DFToolKit
                public Icon getIcon(String name)
                {
                    return ResourcesLoader.getImageIcon( name );
                }
                @Override // DFToolKit
                public ConfigData getConfigData()
                {
                    return configData;
                }
                @Override
                public Locale getValidLocale()
                {
                    if( getLocale() == null ) {
                        return Locale.getDefault();
                        }
                    return getLocale();
                }
                @Override
                public Locale getLocale()
                {
                    return locale;
                }
                @Override
                public void setLocale( final Locale locale )
                {
                    this.locale = locale;
                }
                @Override
                public Window getMainWindow()
                {
                    return DuplicateFilesFrame.this;
                }
                @Override
                public void setEnabledJButtonCancel( boolean b )
                {
                    DuplicateFilesFrame.this.getJButtonCancel().setEnabled( b );
                }
                @Override
                public boolean isEnabledJButtonCancel()
                {
                    return DuplicateFilesFrame.this.getJButtonCancel().isEnabled();
                }
                @Override
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
                            getJPanel3Result().initComponents(duplicateFiles, dfToolKit);
                            getJButtonNextStep().setEnabled( true );
                            logger.info( "initComponentsJPanelConfirm done" );
                        }
                    };
                    new Thread( r ).start();
                }
            };
            }
        return this.dfToolKit;
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
                                }
                            break;

                        case ACTIONCMD_SET_LOCALE :
//                          //TODO
//                          super.getJMenuItemLanguageEnglish().setEnabled( false );
//                          super.getJMenuItemLanguageFrench().setEnabled( false );
//                          super.getJMenuItemLanguageDefaultSystem().setEnabled( true );
//                          super.getJMenuItemLanguageDefaultSystem().setSelected( true );
                            break;

                        case ACTIONCMD_SET_MODE :
                            AbstractButton source = AbstractButton.class.cast( event.getSource() );
                            logger.info( "source: " + source );
//                            ActionListener modeListener = new ActionListener()
//                            {
//                                @Override
//                                public void actionPerformed( ActionEvent e )
//                                {
//                                    logger.debug("ActionEvent:" +e);
//
//                                    if( getJMenuItemModeBegin().isSelected() ) {
//                                        mode = ConfigMode.BEGINNER;
//                                        }
//                                    else if( getJMenuItemModeAdvance().isSelected() ) {
//                                        mode = ConfigMode.ADVANCED;
//                                        }
//                                    else if( getJMenuItemModeExpert().isSelected() ) {
//                                        mode = ConfigMode.EXPERT;
//                                        }
//
//                                    logger.debug( "ConfigMode:" + mode );
//
//                                    getJPanel1Config().updateDisplayMode( mode, true );
//                                    getJPanel3Result().updateDisplayMode( mode );
//                                    //TODO: more !!!
//                                }
//                            };
                            mode = ConfigMode.class.cast( source.getClientProperty( ConfigMode.class ) );
                            logger.debug( "ConfigMode:" + mode );

                            getJPanel1Config().updateDisplayMode( mode, true );
                            getJPanel3Result().updateDisplayMode( mode );
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

    @Override
    public String getMessagesBundle()
    {
        return ResourcesLoader.class.getPackage().getName()
                + ".MessagesBundle";
    }
}
