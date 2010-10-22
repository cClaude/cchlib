/**
 *
 */
package cx.ath.choisnet.tools.i18n;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
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
public class CompareRessourceBundleFrame
    extends CompareRessourceBundleFrameVS4E
{
    private static final Logger slogger = Logger.getLogger(CompareRessourceBundleFrame.class);
    private static final long serialVersionUID = 1L;
    private FilesConfig filesConfig = new FilesConfig();
    private CompareRessourceBundleTableModel tableModel;
    private JFileChooserInitializer jFileChooserInitializer;
    private LastSelectedFilesAccessoryDefaultConfigurator lastSelectedFilesAccessoryDefaultConfigurator = new LastSelectedFilesAccessoryDefaultConfigurator();

    public CompareRessourceBundleFrame()
    {
        super(); // initComponents();

        initFixComponents();
    }

    private void initFixComponents()
    {
        setIconImage(
                Toolkit.getDefaultToolkit().getImage(
                        getClass().getResource("icon.png")
                        )
                );
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
        slogger.info( "started" );
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                CompareRessourceBundleFrame frame = new CompareRessourceBundleFrame();
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                frame.setTitle( "Compare Ressource Bundle" );
                frame.getContentPane().setPreferredSize( frame.getSize() );
                frame.pack();
                frame.setLocationRelativeTo( null );
                frame.setVisible( true );
                frame.getJFileChooserInitializer();
            }
        } );
    }

    protected void updateDisplay()
    {
        slogger.info( "Left :" + filesConfig.getLeftFileObject());
        slogger.info( "Right:" + filesConfig.getRightFileObject());

        if( filesConfig.getLeftFileObject() != null ) {
            jMenuItemSaveLeftFile.setEnabled(
                !filesConfig.getLeftFileObject().isReadOnly()
                );
        }
        else {
            jMenuItemSaveLeftFile.setEnabled( true );
        }

        if( this.filesConfig.isFilesExists() ) {
            this.tableModel = new CompareRessourceBundleTableModel(
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

    @Override
    protected void jMenuItem_SaveLeftFile_MouseMousePressed(MouseEvent event)
    {
        slogger.info( "request to save Left");
        try {
            if( this.tableModel.saveLeftFile(this.filesConfig.getLeftFileObject()) ) {
                JOptionPane.showMessageDialog(
                        this,
                        "File '" + filesConfig.getLeftFileObject().getDisplayName() + "' saved."
                        ,
                        "Left File",
                        JOptionPane.INFORMATION_MESSAGE
                        );
            }
        }
        catch( FileNotFoundException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void jMenuItem_SaveRightFile_MouseMousePressed(MouseEvent event)
    {
        slogger.info( "request to save Right");
        try {
            if( this.tableModel.saveRightFile(this.filesConfig.getRightFileObject()) ) {
                JOptionPane.showMessageDialog(
                        this,
                        "File '" + filesConfig.getRightFileObject().getDisplayName() + "' saved."
                        ,
                        "Righ File",
                        JOptionPane.INFORMATION_MESSAGE
                        );
            }
        }
        catch( FileNotFoundException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void jMenuItem_Quit_MouseMousePressed(MouseEvent event)
    {
        dispose();
    }
    
    @Override
    protected void jMenuItem_OpenMouseMousePressed(MouseEvent event) 
    {
        FilesConfig fc     = new FilesConfig(filesConfig);
        LoadDialog  dialog = new LoadDialog(
                this,
                this.getJFileChooserInitializer(),
                fc
                );
        dialog.setModal( true );
        dialog.setVisible( true );
        
        if( fc.isFilesExists() ) {
            filesConfig = fc;
            updateDisplay();
        }
    }
}
