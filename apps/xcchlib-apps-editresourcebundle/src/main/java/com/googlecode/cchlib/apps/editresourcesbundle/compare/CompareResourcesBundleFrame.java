package com.googlecode.cchlib.apps.editresourcesbundle.compare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Locale;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.editresourcesbundle.EditResourcesBundleApp;
import com.googlecode.cchlib.apps.editresourcesbundle.FilesConfig;
import com.googlecode.cchlib.apps.editresourcesbundle.files.CustomProperties;
import com.googlecode.cchlib.apps.editresourcesbundle.files.FileObject;
import com.googlecode.cchlib.apps.editresourcesbundle.load.LoadDialog;
import com.googlecode.cchlib.apps.editresourcesbundle.prefs.Preferences;
import com.googlecode.cchlib.apps.editresourcesbundle.prefs.PreferencesOpener;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.AutoI18nCoreFactory;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.lang.Threads;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.JFrames;
import com.googlecode.cchlib.swing.filechooser.DefaultJFCCustomizer;
import com.googlecode.cchlib.swing.filechooser.FileNameExtensionFilter;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.WaitingJFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.accessory.BookmarksAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.DefaultBookmarksAccessoryConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.LastSelectedFilesAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.LastSelectedFilesAccessoryDefaultConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.TabbedAccessory;

@I18nName("CompareResourcesBundleFrame")
@SuppressWarnings({"squid:MaximumInheritanceDepth"})
public final class CompareResourcesBundleFrame
    extends CompareResourcesBundleFrameWB
        implements I18nAutoUpdatable
{
    private class FrameActionListener implements ActionListener, Serializable
    {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed( final ActionEvent event )
        {
            final String  actionCommandString = event.getActionCommand();
            final Integer index               = CompareResourcesBundleFrameAction.ACTIONCMD_SAVE_RIGHT_PREFIX.getIndex( actionCommandString );

            if( index != null ) {
                saveFile( index.intValue() + 1 );
                }
            else {
                final CompareResourcesBundleFrameAction action = CompareResourcesBundleFrameAction.valueOf( actionCommandString );

                action.doAction( CompareResourcesBundleFrame.this );
/*
                switch( action ) {
                    case ACTIONCMD_OPEN:
                        jMenuItem_Open();
                        break;

                    case ACTIONCMD_PREFS:
                        openPreferences();
                        break;

                    case ACTIONCMD_QUIT:
                        dispose();
                        break;

                    case ACTIONCMD_SAVE_ALL:
                        saveAll();
                        break;

                    case ACTIONCMD_SAVE_LEFT:
                        saveFile( 0 );
                        break;

                    case ACTIONCMD_SAVE_RIGHT_PREFIX:
                        // should not occur
                        break;
                }
*/
            }
        }
    }

    private final class MyDefaultJFCCustomizer extends DefaultJFCCustomizer
    {
        private static final long serialVersionUID = 1L;

        @Override
        public void perfomeConfig( final JFileChooser jfc )
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
                             CompareResourcesBundleFrame.this.lastSelectedFilesAccessoryDefaultConfigurator
                             )
                         )
                );
        }
    }

    private static final Logger LOGGER = Logger.getLogger(CompareResourcesBundleFrame.class);
    private static final long serialVersionUID = 2L;

    private FilesConfig filesConfig;
    private CompareResourcesBundleTableModel tableModel;
    private JFileChooserInitializer jFileChooserInitializer;
    private FrameActionListener frameActionListener;
    private final Preferences preferences;
    private final LastSelectedFilesAccessoryDefaultConfigurator lastSelectedFilesAccessoryDefaultConfigurator = new LastSelectedFilesAccessoryDefaultConfigurator();
    private final AutoI18nCore autoI18n;

    @I18nString protected String fileSavedMsg = "File '%s' saved."; // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.instanceFieldSecurity
    @I18nString protected String fileSaveNowQuestionMsg = "Save file '%s' now ?"; // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.instanceFieldSecurity
    @I18nString protected String saveLeftFileTypeMsg = "Left File"; // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.instanceFieldSecurity
    @I18nString protected String saveRightFileTypeMsg = "Right File"; // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.instanceFieldSecurity
    @I18nString protected String fileSaveIOException = "Error while saving '%s'"; // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.instanceFieldSecurity
    @I18nString protected String jFileChooserInitializerTitle     = "Waiting..."; // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.instanceFieldSecurity
    @I18nString protected String jFileChooserInitializerMessage   = "Analyze disk structure"; // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.instanceFieldSecurity
    @I18nString protected String msgStringAlertLocaleTitle = "Change language"; // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.instanceFieldSecurity
    @I18nString protected String msgStringAlertLocale = "You need to restart application to apply this language: %s"; // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.instanceFieldSecurity
    @I18nString protected String txtNoFile = "<<NoFile>>"; // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.instanceFieldSecurity

    private final PreferencesOpener preferencesOpener;

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
    public CompareResourcesBundleFrame( final Preferences prefs )
    {
        super( prefs.getNumberOfFiles() );

        this.preferences = prefs;
        this.filesConfig = new FilesConfig( this.preferences );

        setSize( this.preferences.getWindowDimension() );

        final WindowListener wl = new WindowAdapter()
        {
            @Override
            public void windowClosing( final WindowEvent event )
            {
                super.windowClosing( event );

                closeContent();

                System.exit( 0 ); // AppQuit -- FIXME try to remove this
            }
        };
        super.addWindowListener( wl );

        // initialize dynamic components
        super.buildLookAndFeelMenu();

        updateDisplay();

        // Init i18n
        final Locale locale = this.preferences.getLocale();

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "I18n Init: Locale.getDefault()=" + Locale.getDefault() );
            LOGGER.trace( "I18n Init: locale = " + locale );
            LOGGER.trace( "I18n Init: getMessagesBundle() = " + EditResourcesBundleApp.getI18nResourceBundleName() );
            }

        this.autoI18n = AutoI18nCoreFactory.newAutoI18nCore(
                EditResourcesBundleApp.getConfig(),
                EditResourcesBundleApp.getI18nSimpleResourceBundle( locale )
                );

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "I18n Init: done" );
            }

        this.preferencesOpener = new PreferencesOpener( this, this.preferences );

        // Apply i18n !
        performeI18n( this.autoI18n );


        JFrames.handleMinimumSize( this, this.preferences.getCompareFrameMinimumDimension() );
    }

    protected void closeContent()
    {
        if( this.tableModel != null ) {
            // FIXME better handle of save
            for( int i = 0; i<this.filesConfig.getNumberOfFiles(); i++ ) {
                saveFile( i );
                }
            }
    }

    public static final URL getResource( final String name )
    {
        final URL url = CompareResourcesBundleFrame.class.getResource( name );

        if( url == null ) {
            LOGGER.warn( "Could not find resource: " + name );
            }

        return url;
    }

    public static void main()
    {
        LOGGER.info( "started" );

        final Preferences prefs = Preferences.createPreferences();

        prefs.installPreferences();

        SwingUtilities.invokeLater( ( ) -> {
            try {
                final CompareResourcesBundleFrame frame = new CompareResourcesBundleFrame(prefs);
                frame.setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );
                frame.setTitle( "Edit Ressource Bundle" );
                frame.getContentPane().setPreferredSize( frame.getSize() );
                frame.pack();
                frame.setLocationRelativeTo( null );
                frame.setVisible( true );
                frame.getJFileChooserInitializer();
                }
            catch( final Exception e ) {
                LOGGER.error( "Error while building main frame", e );
                DialogHelper.showMessageExceptionDialog( null, "Fatal error", e );
                }
        } );
    }

    protected void updateDisplay()
    {
        if( LOGGER.isInfoEnabled() ) {
            for( int i = 0; i<this.filesConfig.getNumberOfFiles(); i++ ) {
                LOGGER.info( "F[" + i + "]:" + this.filesConfig.getFileObject( i ) );
                }
            }

        if( this.filesConfig.getLeftFileObject() != null ) {
            getjMenuItemSaveLeftFile().setEnabled(
                !this.filesConfig.getLeftFileObject().isReadOnly()
                );
            }
        else {
            getjMenuItemSaveLeftFile().setEnabled( true );
            }

        if( this.filesConfig.isFilesExists() ) {
            this.tableModel = new CompareResourcesBundleTableModel(
                    this.filesConfig,
                    this.autoI18n
                    );
            setjTableProperties( this.tableModel.getJTable() );
            getjScrollPaneProperties().setViewportView( getjTableProperties() );
            getjTableProperties().setModel(this.tableModel);
            getjTableProperties().setAutoCreateRowSorter(true);
            this.tableModel.setColumnWidth(getjTableProperties());
        }
    }

    public JFileChooserInitializer getJFileChooserInitializer()
    {
        if( this.jFileChooserInitializer == null ) {
            final DefaultJFCCustomizer configurator = new MyDefaultJFCCustomizer();

            configurator.setFileFilter(
                new FileNameExtensionFilter(
                        "Properties",
                        "properties"
                        )
                    );
            configurator.setCurrentDirectory( getPreferences().getLastDirectory() );

            this.jFileChooserInitializer = new WaitingJFileChooserInitializer(
                    configurator,
                    this,
                    this.jFileChooserInitializerTitle,
                    this.jFileChooserInitializerMessage
                    );
        }

        return this.jFileChooserInitializer;
    }

    protected void saveFile(
        final int index
        )
    {
        final String            saveFileTypeMsg;
        final FileObject        fileObject;
        final CustomProperties  customProperties;

        LOGGER.info( "request to save index: " + index + " filesConfig = " + this.filesConfig );

        if( index == 0 ) {
            saveFileTypeMsg     = this.saveLeftFileTypeMsg;
            }
        else {
            saveFileTypeMsg     = this.saveRightFileTypeMsg;
            }
        fileObject          = this.filesConfig.getFileObject( index );
        customProperties    = this.tableModel.getCustomProperties( index );

        LOGGER.info( "request to save: " + saveFileTypeMsg );

        if( fileObject.isReadOnly() ) {
            LOGGER.info( "read only file (cancel): " + fileObject );
            }
        else if( !customProperties.isEdited() ) {
            LOGGER.info( "Content not change for: " + fileObject );
            }
        else {
            //Confirm to save
            final int boutonNumber = JOptionPane.showConfirmDialog(
                      this,
                      String.format(
                              this.fileSaveNowQuestionMsg,
                              fileObject.getDisplayName( this.txtNoFile )
                              ),
                      saveFileTypeMsg,
                      JOptionPane.YES_NO_OPTION
                      );

            if( boutonNumber == 1 ) {
                return; // save canceled
                }

            try {
                final boolean res = customProperties.store();

                if( res ) {
                    JOptionPane.showMessageDialog(
                            this,
                            String.format( this.fileSavedMsg, fileObject.getDisplayName( this.txtNoFile ) ) ,
                            saveFileTypeMsg,
                            JOptionPane.INFORMATION_MESSAGE
                            );
                    }
                }
            catch( final IOException e ) {
                LOGGER.error( e );
                DialogHelper.showMessageExceptionDialog(
                    this,
                    String.format( this.fileSaveIOException, fileObject.getDisplayName( this.txtNoFile ) ),
                    e
                    );
                }
        }
    }

    void saveAll()
    {
        for( int i = 0; i<CompareResourcesBundleFrame.this.filesConfig.getNumberOfFiles(); i++ ) {
            saveFile( i );
            }
    }

    void jMenuItem_Open()
    {
        Threads.start( "jMenuItemOpenAsync()", this::jMenuItemOpenAsync );
    }

    private void jMenuItemOpenAsync()
    {
        // TODO: close prev
        closeContent();

        final FilesConfig fc     = new FilesConfig(this.filesConfig);
        fc.setNumberOfFiles( this.preferences.getNumberOfFiles() );

        final LoadDialog  dialog = new LoadDialog(
                CompareResourcesBundleFrame.this,
                fc
                );
        dialog.performeI18n(this.autoI18n);
        dialog.setModal( true );
        dialog.setVisible( true );

        if( fc.isFilesExists() ) {
            this.filesConfig = fc;
            updateDisplay();
            }
    }

    @Override
    protected ActionListener getActionListener()
    {
        if( this.frameActionListener == null ) {
            this.frameActionListener = new FrameActionListener();
            }

        return this.frameActionListener;
    }



    /**
     * I18n this frame !
     *
     * {@inheritDoc}
     */
    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n(this,this.getClass());
        autoI18n.performeI18n(this.preferencesOpener,this.preferencesOpener.getClass());
    }

    public void openPreferences()
    {
        this.preferencesOpener.open();
    }

    public Preferences getPreferences()
    {
        return this.preferences;
    }
}
