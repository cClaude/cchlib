package cx.ath.choisnet.tools.duplicatefiles.gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.apache.log4j.Logger;

import com.googlecode.cchlib.apps.duplicatefiles.ResourcesLoader;

import cx.ath.choisnet.i18n.AutoI18n;
import cx.ath.choisnet.i18n.I18nString;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializer;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializer.DefaultConfigurator;
import cx.ath.choisnet.swing.filechooser.WaitingJFileChooserInitializer;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessory;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessoryDefaultConfigurator;
import cx.ath.choisnet.swing.filechooser.accessory.TabbedAccessory;
import cx.ath.choisnet.swing.helpers.LookAndFeelHelpers;
import cx.ath.choisnet.tools.duplicatefiles.ConfigData;
import cx.ath.choisnet.tools.duplicatefiles.ConfigMode;
import cx.ath.choisnet.tools.duplicatefiles.DFToolKit;
import cx.ath.choisnet.tools.duplicatefiles.I18nBundle;
import cx.ath.choisnet.tools.duplicatefiles.KeyFileState;
import cx.ath.choisnet.tools.emptydirectories.gui.RemoveEmptyDirectories;
import cx.ath.choisnet.util.HashMapSet;
import cx.ath.choisnet.util.checksum.MessageDigestFile;

/**
 *
 */
final public class DuplicateFilesFrame
    extends DuplicateFilesFrameWB
        implements DFToolKit
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( DuplicateFilesFrame.class );
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
    private AutoI18n autoI18n = I18nBundle.getAutoI18n();

    private Icon iconContinue;
    private Icon iconRestart;

    @I18nString private String txtContinue  = "Continue";
    @I18nString private String txtRestart   = "Restart";
    @I18nString private String txtRemove    = "Remove";
    @I18nString private String txtDeleteNow = "Delete now";
    @I18nString private String txtBack      = "Back";
    @I18nString private String jFileChooserInitializerTitle     = "Waiting...";
    @I18nString private String jFileChooserInitializerMessage   = "Analyze disk structure";

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
        autoI18n.performeI18n(jPanel0Select,jPanel0Select.getClass());
        //autoI18n.performeI18n(jPanel1Config,jPanel1Config.getClass());
        jPanel1Config.performeI18n(autoI18n);
        autoI18n.performeI18n(jPanel2Searching,jPanel2Searching.getClass());
        autoI18n.performeI18n(jPanel3Result,jPanel3Result.getClass());
        autoI18n.performeI18n(jPanel4Confirm,jPanel4Confirm.getClass());
    }

    private void initFixComponents()
    {
        setIconImage( getImage( "icon.png" ) );

        //TODO
        super.jMenuItemLanguageEnglish.setEnabled( false );
        super.jMenuItemLanguageFrench.setEnabled( false );
        super.jMenuItemLanguageDefaultSystem.setEnabled( true );
        super.jMenuItemLanguageDefaultSystem.setSelected( true );

        this.iconContinue = getIcon( "continue.png" );
        this.iconRestart  = getIcon( "restart.png" );

        // Moving the Icon in a JButton Component
        jButtonNextStep.setVerticalTextPosition(SwingConstants.CENTER);
        jButtonNextStep.setHorizontalTextPosition(SwingConstants.LEFT);

        // Init. mode state
        jMenuItemModeBegin.setSelected( true );

        this.state = STATE_SELECT_DIRS;

        ActionListener modeListener = new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                logger.debug("ActionEvent:" +e);

                if( jMenuItemModeBegin.isSelected() ) {
                    mode = ConfigMode.BEGINNER;
                    }
                else if( jMenuItemModeAdvance.isSelected() ) {
                    mode = ConfigMode.ADVANCED;
                    }
                else if( jMenuItemModeExpert.isSelected() ) {
                    mode = ConfigMode.EXPERT;
                    }

                logger.debug( "ConfigMode:" + mode );

                jPanel1Config.updateDisplayMode( mode, true );
                jPanel3Result.updateDisplayMode( mode );
                //TODO: more !!!
            }
        };
        jMenuItemModeBegin.addActionListener(modeListener);
        jMenuItemModeAdvance.addActionListener(modeListener);
        jMenuItemModeExpert.addActionListener(modeListener);

        jTabbedPaneMain.setEnabled( false );

        jPanel0Select.initFixComponents( this );
        jPanel1Config.initFixComponents( this );
        jPanel2Searching.initFixComponents( this );
        jPanel3Result.initFixComponents( duplicateFiles, this );

        // init
        modeListener.actionPerformed( null );

        // build menu (VS4E does not support Box!)
        jMenuBarMain.add( Box.createHorizontalGlue() );
        jMenuBarMain.add( getJMenuLookAndFeel() );

        // initDynComponents
        LookAndFeelHelpers.buildLookAndFeelMenu( this, jMenuLookAndFeel );

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
                                jPanel1Config.initFixComponents( DuplicateFilesFrame.this );

                                if( DuplicateFilesFrame.this.removeEmptyDirectories != null ) {
//                                    DuplicateFilesFrame.this.removeEmptyDirectories.init();

                                    }
                                }
                            }
                    }
                });
    }

    protected void updateDisplayAccordingState()
    {
        logger.debug( "updateDisplayAccordState: " + state );

        jTabbedPaneMain.setSelectedIndex( state );
        jButtonNextStep.setText( txtContinue );
        jButtonNextStep.setIcon( iconContinue );
        jButtonRestart.setText( txtRestart );
        jButtonRestart.setIcon( iconRestart );

        if( state == STATE_SELECT_DIRS ) {
            jButtonRestart.setEnabled( false );
            jButtonNextStep.setEnabled( true );
        }
        else if( state == STATE_SEARCH_CONFIG ) {
            jButtonRestart.setEnabled( true );
            jButtonNextStep.setEnabled( true );
        }
        else if( state == STATE_SEARCHING ) {
            jButtonRestart.setEnabled( false );
            jButtonNextStep.setEnabled( false );
            jMenuLookAndFeel.setEnabled( false );

            try {
                jPanel2Searching.prepareScan(
                        new MessageDigestFile( "MD5", bufferSize  ),
                        jPanel1Config.IsIgnoreEmptyFiles()
                        );
                }
            catch( NoSuchAlgorithmException ignore ) {
                logger.error( ignore );
                }

            new Thread( new Runnable() {
                @Override
                public void run()
                {
                    jPanel2Searching.doScan(
                            //jPanel0SelectFolders,
                            jPanel0Select.entriesToScans(),
                            jPanel0Select.entriesToIgnore(),
                            jPanel1Config.getFileFilterBuilders(),
                            duplicateFiles
                            );

                    jButtonNextStep.setEnabled( true );
                    jMenuLookAndFeel.setEnabled( true );
                    jButtonRestart.setEnabled( true );
                }
            } ).start();
        }
        else if( state == STATE_RESULTS ) {
            jButtonRestart.setEnabled( false );
            jButtonNextStep.setEnabled( false );
            jButtonNextStep.setText( txtRemove );

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                public void run()
                {
                    jPanel3Result.initDisplay();
                    jButtonRestart.setEnabled( true );
                    jButtonNextStep.setEnabled( true );
                }
            });
        }
        else if( state == STATE_CONFIRM ) {
            jButtonRestart.setEnabled( true );
            jButtonRestart.setText( txtBack  );

            if( subState == SUBSTATE_CONFIRM_INIT ) {
                jButtonNextStep.setEnabled( true );
                jButtonNextStep.setText( txtDeleteNow  );

                jPanel4Confirm.initComponents( duplicateFiles );
            }
            else {
                jButtonNextStep.setEnabled( false );
            }
        }
    }

    private JFileChooserInitializer getJFileChooserInitializer()
    {
        if( jFileChooserInitializer == null ) {
            final DefaultConfigurator configurator = new JFileChooserInitializer.DefaultConfigurator()
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
                                    new BookmarksAccessoryDefaultConfigurator() ) ) );
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

    public static void main( String[] args )
    {
        logger.info( "starting..." );
        //Locale.setDefault( Locale.ENGLISH ); // Debug Only

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
                    );
            }
        catch(Exception e ) {
            logger.warn( e );
            }

        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                try {
                    DuplicateFilesFrame frame = new DuplicateFilesFrame();
                    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                    frame.setTitle( "Duplicate Files Manager" );
                    frame.getContentPane().setPreferredSize( frame.getSize() );
                    frame.pack();
                    frame.setLocationRelativeTo( null );
                    frame.setVisible( true );
                    frame.getJFileChooserInitializer();
                }
                catch( Throwable e ) {
                    logger.fatal( "Can't load application", e );
                    e.printStackTrace();
                }
            }
        } );
    }

    @Override
    protected void jButtonRestartMouseMousePressed( MouseEvent event )
    {
        if( jButtonRestart.isEnabled() ) {
            if( state == STATE_CONFIRM ) {
                state = STATE_RESULTS;
                }
            else {
                state = STATE_SELECT_DIRS;
                 jPanel2Searching.clear();
                }

            updateDisplayAccordingState();
            }
    }

    @Override
    protected void jButtonNextStepMouseMousePressed( MouseEvent event )
    {
        if( jButtonNextStep.isEnabled() ) {
            logger.info( "Next: " + state );

            if( state == STATE_SELECT_DIRS ) {
                if( jPanel0Select.getEntriesToScanSize() > 0 ) {
                    state = STATE_SEARCH_CONFIG;
                    }
                else {
                    beep();
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
                    jButtonNextStep.setEnabled( false );
                    jButtonRestart.setEnabled( false );

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run()
                        {
                            jPanel4Confirm.doDelete(DuplicateFilesFrame.this,duplicateFiles);

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

    private RemoveEmptyDirectories removeEmptyDirectories;

    @Override
    protected void jMenuItemDeleteEmptyDirectoriesActionPerformed( ActionEvent event )
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
}
