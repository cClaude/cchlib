package com.googlecode.cchlib.apps.editresourcesbundle;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import cx.ath.choisnet.i18n.I18nString;
import cx.ath.choisnet.swing.filechooser.FileNameExtensionFilter;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializer;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessory;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessoryDefaultConfigurator;
import cx.ath.choisnet.swing.filechooser.accessory.LastSelectedFilesAccessory;
import cx.ath.choisnet.swing.filechooser.accessory.LastSelectedFilesAccessoryDefaultConfigurator;
import cx.ath.choisnet.swing.filechooser.accessory.TabbedAccessory;
import cx.ath.choisnet.swing.helpers.LookAndFeelHelpers;

/**
 * cx.ath.choisnet.tools.i18n.CompareRessourceBundleFrame
 *
 * @author Claude CHOISNET
 *
 */
public class CompareResourcesBundleFrame
    extends CompareResourcesBundleFrameWB
{
    private static final Logger logger = Logger.getLogger(CompareResourcesBundleFrame.class);
    private static final long serialVersionUID = 1L;
    private FilesConfig filesConfig = new FilesConfig();
    private CompareResourcesBundleTableModel tableModel;
    private JFileChooserInitializer jFileChooserInitializer;
    private LastSelectedFilesAccessoryDefaultConfigurator lastSelectedFilesAccessoryDefaultConfigurator = new LastSelectedFilesAccessoryDefaultConfigurator();
    @I18nString // TODO i18n
    private final String fileSavedMsg = "File '%s' saved.";
    @I18nString // TODO i18n
    private final String fileSaveNowQuestionMsg = "Save file '%s' now ?";
    @I18nString // TODO i18n
    private final String saveLeftFileTypeMsg = "Left File";
    @I18nString // TODO i18n
    private final String saveRightFileTypeMsg = "Right File";

    public CompareResourcesBundleFrame()
    {
        super(); // initComponents();

        WindowListener wl = new WindowAdapter()
        {
            @Override
            public void windowClosing( final WindowEvent event )
            {
                super.windowClosing( event );

                if( tableModel != null ) {
                    // FIXME add a dialog to ask if should be saved
                    saveFile( true ); // save left
                    saveFile( false ); // save right
                    }

                System.exit( 0 );
            }
        };
        super.addWindowListener( wl );

        initFixComponents();
    }

    public static final URL getResource( final String name )
    {
        final URL url = CompareResourcesBundleFrame.class.getResource( name );

        if( url == null ) {
            logger.warn( "Could not find resource: " + name );
            }

        return url;
    }

    public static final Image getImage( final String image )
    {
        URL url = getResource( image );

        if( url != null ) {
            return Toolkit.getDefaultToolkit().getImage( url );
            }

        logger.error( "Could create image: " + image );
        return null;
    }

    private void initFixComponents()
    {
        setIconImage( getImage( "icon.png" ) );
        // build menu (VS4E does not support Box!)
        jMenuBarFrame.add(Box.createHorizontalGlue());
        jMenuBarFrame.add(getJMenuLookAndFeel());

        // initDynComponents
        LookAndFeelHelpers.buildLookAndFeelMenu( this, this.jMenuLookAndFeel );

        //TODO: prefs !

        updateDisplay();

        //lastSelectedFilesAccessoryDefaultConfigurator.getLastSelectedFiles();
    }

    public static void main( String[] args )
    {
        logger.info( "started" );
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                try {
                    CompareResourcesBundleFrame frame = new CompareResourcesBundleFrame();
                    // frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                    frame.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
                    frame.setTitle( "Compare Ressource Bundle" );
                    frame.getContentPane().setPreferredSize( frame.getSize() );
                    frame.pack();
                    frame.setLocationRelativeTo( null );
                    frame.setVisible( true );
                    frame.getJFileChooserInitializer();
                    }
                catch( Exception e ) {
                    logger.error( "Error while building main frame", e );
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
                    this.filesConfig
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
            jFileChooserInitializer = new JFileChooserInitializer(
                new JFileChooserInitializer.DefaultConfigurator()
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
                                        new BookmarksAccessoryDefaultConfigurator()
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
                }
                .setFileFilter(
                    new FileNameExtensionFilter(
                        "Properties",
                        "properties"
                        )
                    )
                );
        }
        return jFileChooserInitializer;
    }

    protected void saveFile(
        final boolean isLeft
        )
    {
        final String        saveFileTypeMsg;
        final FileObject    fileObject;

        if( isLeft ) {
            saveFileTypeMsg = saveLeftFileTypeMsg;
            fileObject      = filesConfig.getLeftFileObject();
            }
        else {
            saveFileTypeMsg = saveRightFileTypeMsg;
            fileObject      = filesConfig.getRightFileObject();
            }

        logger.info( "request to save: " + saveFileTypeMsg);

        if( fileObject.isReadOnly() ) {
            logger.info( "read only file (cancel): " + fileObject );
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
                final boolean res;

                if( isLeft ) {
                    res = this.tableModel.saveLeftFile( fileObject );
                    }
                else {
                    res = this.tableModel.saveRightFile( fileObject );
                    }

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
                // FIXME add a dialog show this error
                logger.error( e );
                }
        }
    }

    @Override
    protected void jMenuItem_SaveLeftFile_MouseMousePressed(MouseEvent event)
    {
        saveFile( true /*isLeft*/ );
    }

    @Override
    protected void jMenuItem_SaveRightFile_MouseMousePressed(MouseEvent event)
    {
        saveFile( false /*isLeft*/ );
    }

    @Override
    protected void jMenuItem_Quit_MouseMousePressed(MouseEvent event)
    {
        dispose();
    }

    @Override
    protected void jMenuItem_OpenMouseMousePressed(MouseEvent event)
    {
        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                FilesConfig fc     = new FilesConfig(filesConfig);
                LoadDialog  dialog = new LoadDialog(
                        CompareResourcesBundleFrame.this,
                        CompareResourcesBundleFrame.this.getJFileChooserInitializer(),
                        fc
                        );
                dialog.setModal( true );
                dialog.setVisible( true );

                if( fc.isFilesExists() ) {
                    filesConfig = fc;
                    updateDisplay();
                    }
            }
        }).start();
    }
}
