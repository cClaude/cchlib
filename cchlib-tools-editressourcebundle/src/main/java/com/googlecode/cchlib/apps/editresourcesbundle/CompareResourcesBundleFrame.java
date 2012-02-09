package com.googlecode.cchlib.apps.editresourcesbundle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.editresourcesbundle.prefs.Preferences;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.i18n.config.DefaultI18nBundleFactory;
import com.googlecode.cchlib.i18n.config.I18nPrepAutoUpdatable;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.filechooser.accessory.DefaultBookmarksAccessoryConfigurator;

import cx.ath.choisnet.swing.filechooser.FileNameExtensionFilter;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializer;
import cx.ath.choisnet.swing.filechooser.WaitingJFileChooserInitializer;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessory;
import cx.ath.choisnet.swing.filechooser.accessory.LastSelectedFilesAccessory;
import cx.ath.choisnet.swing.filechooser.accessory.LastSelectedFilesAccessoryDefaultConfigurator;
import cx.ath.choisnet.swing.filechooser.accessory.TabbedAccessory;
import cx.ath.choisnet.swing.helpers.LookAndFeelHelpers;

/**
 *
 * @author Claude CHOISNET
 */
// not public
class CompareResourcesBundleFrame
    extends CompareResourcesBundleFrameWB
        implements I18nPrepAutoUpdatable
{
    private static final Logger logger = Logger.getLogger(CompareResourcesBundleFrame.class);
    private static final long serialVersionUID = 1L;
    /* @serial */
    private FilesConfig filesConfig = new FilesConfig();
    /* @serial */
    private CompareResourcesBundleTableModel tableModel;
    /* @serial */
    private JFileChooserInitializer jFileChooserInitializer;
    /* @serial */
    private FrameActionListener frameActionListener;
    /* @serial */
    private Preferences preferences;
    /* @serial */
    private LastSelectedFilesAccessoryDefaultConfigurator lastSelectedFilesAccessoryDefaultConfigurator = new LastSelectedFilesAccessoryDefaultConfigurator();
    /* @serial */
    private AutoI18n autoI18n;

    @I18nString private String fileSavedMsg = "File '%s' saved.";
    @I18nString private String fileSaveNowQuestionMsg = "Save file '%s' now ?";
    @I18nString private String saveLeftFileTypeMsg = "Left File";
    @I18nString private String saveRightFileTypeMsg = "Right File";
    @I18nString private String fileSaveIOException = "Error while saving '%s'";
    @I18nString private String jFileChooserInitializerTitle     = "Waiting...";
    @I18nString private String jFileChooserInitializerMessage   = "Analyze disk structure";
    @I18nString private String msgStringAlertLocaleTitle = "Change language";
    @I18nString private String msgStringAlertLocale = "You need to restart application to apply this language: %s";

    /**
     * For I18n only
     */
    public CompareResourcesBundleFrame()
    {
        this( Preferences.createPreferences() );
    }

    /**
     * Build main frame using giving preferences
     * @param prefs preferences to use
     */
    public CompareResourcesBundleFrame( Preferences prefs )
    {
        super(); // initComponents();

        this.preferences = prefs;

        //setSize(640, 440);
        setSize( this.preferences.getWindowDimension() );

        WindowListener wl = new WindowAdapter()
        {
            @Override
            public void windowClosing( final WindowEvent event )
            {
                super.windowClosing( event );

                if( tableModel != null ) {
                    saveFile( true ); // save left
                    saveFile( false ); // save right
                    }

                System.exit( 0 );
            }
        };
        super.addWindowListener( wl );

        //setIconImage( getImage( "icon.png" ) );

        // initDynComponents
        LookAndFeelHelpers.buildLookAndFeelMenu( this, jMenuLookAndFeel );

        updateDisplay();

        //lastSelectedFilesAccessoryDefaultConfigurator.getLastSelectedFiles();

        // Init i18n
        Locale locale = this.preferences.getLocale();

        if( logger.isTraceEnabled() ) {
            logger.info( "I18n Init: Locale.getDefault()=" + Locale.getDefault() );
            logger.info( "I18n Init: locale = " + locale );
            }
        this.autoI18n = DefaultI18nBundleFactory.createDefaultI18nBundle( locale, this ).getAutoI18n();

        // FIXME: set menu checkbox to good locale !!!
        // FIXME: set menu checkbox to good locale !!!
        // FIXME: set menu checkbox to good locale !!!

        //logger.info( "Locale use by I18n: " + this.autoI18n.setI18n( i18n ) );

        // Apply i18n !
        performeI18n(autoI18n);
    }

    public static final URL getResource( final String name )
    {
        final URL url = CompareResourcesBundleFrame.class.getResource( name );

        if( url == null ) {
            logger.warn( "Could not find resource: " + name );
            }

        return url;
    }

//    private static final Image getImage( final String image )
//    {
//        URL url = getResource( image );
//
//        if( url != null ) {
//            return Toolkit.getDefaultToolkit().getImage( url );
//            }
//
//        logger.error( "Could create image: " + image );
//        return null;
//    }

    public static void main(/* String[] args*/)
    {
        logger.info( "started" );

        final Preferences prefs = Preferences.createPreferences();

        prefs.installPreferences();

        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                try {
                    CompareResourcesBundleFrame frame = new CompareResourcesBundleFrame(prefs);
                    // frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                    frame.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
                    frame.setTitle( "Edit Ressource Bundle" );
                    frame.getContentPane().setPreferredSize( frame.getSize() );
                    frame.pack();
                    frame.setLocationRelativeTo( null );
                    frame.setVisible( true );
                    frame.getJFileChooserInitializer();
                    }
                catch( Exception e ) {
                    logger.error( "Error while building main frame", e );
                    DialogHelper.showMessageExceptionDialog( null, "Fatal error", e );
                    }
            }
        } );
    }

    protected void updateDisplay()
    {
        logger.info( "Left :" + filesConfig.getLeftFileObject());
        logger.info( "Right:" + filesConfig.getRightFileObject());

        if( filesConfig.getLeftFileObject() != null ) {
            jMenuItemSaveLeftFile.setEnabled(
                !filesConfig.getLeftFileObject().isReadOnly()
                );
            }
        else {
            jMenuItemSaveLeftFile.setEnabled( true );
            }

        if( this.filesConfig.isFilesExists() ) {
            this.tableModel = new CompareResourcesBundleTableModel(
                    this.filesConfig,
                    this.autoI18n
                    );
            jTableProperties = this.tableModel.getJTable();
            jScrollPaneProperties.setViewportView( jTableProperties );
            jTableProperties.setModel(this.tableModel);
            jTableProperties.setAutoCreateRowSorter(true);
            this.tableModel.setColumnWidth(jTableProperties);
        }
    }

    public JFileChooserInitializer getJFileChooserInitializer()
    {
        if( jFileChooserInitializer == null ) {
            JFileChooserInitializer.DefaultConfigurator configurator = new JFileChooserInitializer.DefaultConfigurator()
            {
                private static final long serialVersionUID = 1L;
                public void perfomeConfig(JFileChooser jfc)
                {
                    super.perfomeConfig( jfc );

                    jfc.setAccessory(
                        new TabbedAccessory()
                            .addTabbedAccessory(
                                new BookmarksAccessory(
                                    jfc,
                                    new DefaultBookmarksAccessoryConfigurator()
                                    )
                                )
                             .addTabbedAccessory(
                                 new LastSelectedFilesAccessory(
                                     jfc,
                                     lastSelectedFilesAccessoryDefaultConfigurator
                                     )
                                 )
                        );
                }
            };
            configurator.setFileFilter(
                    new FileNameExtensionFilter(
                            "Properties",
                            "properties"
                            )
                        );
            configurator.setCurrentDirectory( getPreferences().getLastDirectory() );

            jFileChooserInitializer = new WaitingJFileChooserInitializer(
                    configurator,
                    this,
                    jFileChooserInitializerTitle,
                    jFileChooserInitializerMessage
                    );
        }

        return jFileChooserInitializer;
    }

    protected void saveFile(
        final boolean isLeft
        )
    {
        final String            saveFileTypeMsg;
        final FileObject        fileObject;
        final CustomProperties  customProperties;

        if( isLeft ) {
            saveFileTypeMsg     = saveLeftFileTypeMsg;
            fileObject          = filesConfig.getLeftFileObject();
            customProperties    = tableModel.getLeftCustomProperties();
            }
        else {
            saveFileTypeMsg     = saveRightFileTypeMsg;
            fileObject          = filesConfig.getRightFileObject();
            customProperties    = tableModel.getRightCustomProperties();
            }

        logger.info( "request to save: " + saveFileTypeMsg);

        if( fileObject.isReadOnly() ) {
            logger.info( "read only file (cancel): " + fileObject );
            }
        else if( !customProperties.isEdited() ) {
            logger.info( "Content not change for: " + fileObject );
            }
        else {
            //Confirm to save
            int n = JOptionPane.showConfirmDialog(
                      this,
                      String.format(
                              fileSaveNowQuestionMsg,
                              fileObject.getDisplayName()
                              ),
                      saveFileTypeMsg,
                      JOptionPane.YES_NO_OPTION
                      );

            if( n == 1 ) {
                return; // save canceled
                }

            try {
                final boolean res = customProperties.store();

                if( res ) {
                    JOptionPane.showMessageDialog(
                            this,
                            String.format( fileSavedMsg, fileObject.getDisplayName() ) ,
                            saveFileTypeMsg,
                            JOptionPane.INFORMATION_MESSAGE
                            );
                    }
                }
            catch( IOException e ) {
                logger.error( e );
                DialogHelper.showMessageExceptionDialog(
                    this,
                    String.format( fileSaveIOException, fileObject.getDisplayName() ),
                    e
                    );
                }
        }
    }

    private void jMenuItem_Open()
    {
        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                FilesConfig fc     = new FilesConfig(filesConfig);
                LoadDialog  dialog = new LoadDialog(
                        CompareResourcesBundleFrame.this,
                        fc
                        );
                dialog.performeI18n(autoI18n);
                dialog.setModal( true );
                dialog.setVisible( true );

                if( fc.isFilesExists() ) {
                    filesConfig = fc;
                    updateDisplay();
                    }
            }
        }).start();
    }

    @Override
    protected ActionListener getActionListener()
    {
        if( frameActionListener == null ) {
            this.frameActionListener = new FrameActionListener();
            }

        return frameActionListener;
    }

    private class FrameActionListener implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent event )
        {
            final String c = event.getActionCommand();

            if( ACTIONCMD_SAVE_PREFS.equals( c ) ) {
                preferences.setLookAndFeelClassName();
                preferences.setLocale( Locale.getDefault() );

                preferences.setWindowDimension( getSize() );

                // TODO: Add here extra preferences values

                savePreferences();
                }
            else if( ACTIONCMD_OPEN.equals( c ) ) {
                jMenuItem_Open();
                }
            else if( ACTIONCMD_QUIT.equals( c ) ) {
                dispose();
                }
            else if( ACTIONCMD_SAVE_ALL.equals( c ) ) {
                saveFile( true /*isLeft*/ );
                saveFile( false /*isLeft*/ );
                }
            else if( ACTIONCMD_SAVE_RIGHT.equals( c ) ) {
                saveFile( false /*isLeft*/ );
                }
            else if( ACTIONCMD_SAVE_LEFT.equals( c ) ) {
                saveFile( true /*isLeft*/ );
                }
            else if( ACTIONCMD_DEFAULT_LOCAL.equals( c ) ) {
                setGuiLocale( null );
                }
            else if( ACTIONCMD_ENGLISH.equals( c ) ) {
                setGuiLocale( Locale.ENGLISH );
                }
            else if( ACTIONCMD_FRENCH.equals( c ) ) {
                setGuiLocale( Locale.FRENCH );
                }
        }
    }

    /**
     * I18n this frame !
     *
     * @param autoI18n
     */
    @Override // I18nAutoUpdatable
    public void performeI18n( AutoI18n autoI18n )
    {
        autoI18n.performeI18n(this,this.getClass());
    }

    private void savePreferences()
    {
        try {
            preferences.save();
            }
        catch( IOException e ) {
            DialogHelper.showMessageExceptionDialog(
                CompareResourcesBundleFrame.this,
                "Error while saving preferences", //title
                e
                );
            }
    }

    public void setGuiLocale( final Locale locale )
    {
        preferences.setLocale( locale );

        savePreferences();

        JOptionPane.showMessageDialog(
            this,
            String.format( msgStringAlertLocale, locale ),
            msgStringAlertLocaleTitle,
            JOptionPane.INFORMATION_MESSAGE
            );
    }

    @Override // I18nPrepAutoUpdatable
    public String getMessagesBundle()
    {
        return DefaultI18nBundleFactory.getMessagesBundle( EditResourcesBundleApp.class );
    }

    public Preferences getPreferences()
    {
        return this.preferences;
    }
}
