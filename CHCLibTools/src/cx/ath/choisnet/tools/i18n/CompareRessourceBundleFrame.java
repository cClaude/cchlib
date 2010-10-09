/**
 * 
 */
package cx.ath.choisnet.tools.i18n;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
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
//VS 4E -- DO NOT REMOVE THIS LINE!
public class CompareRessourceBundleFrame 
    extends JFrame 
{
    private static final Logger slogger = Logger.getLogger(CompareRessourceBundleFrame.class);
    private static final long serialVersionUID = 1L;
    private FileObject leftFile     = new FileObject();
    private FileObject rightFile    = new FileObject();
    private CompareRessourceBundleTableModel tableModel;
    private JFileChooserInitializer jFileChooserInitializer = new JFileChooserInitializer();
    
    private JMenu jMenuFile;
    private JMenu jMenuLeftFile;
    private JMenu jMenuRightFile;
    private JMenu jMenuLastOpenFiles;
    private JMenu jMenuLookAndFeel;
    private JMenuItem jMenuItemLoadLeftFile;
    private JMenuItem jMenuItemLoadLeftFileReadOnly;
    private JMenuItem jMenuItemSaveLeftFile;
    private JMenuItem jMenuItemLoadRightFile;
    private JMenuItem jMenuItemSaveRightFile;
    private JMenuItem jMenuItemSaveAll;
    private JMenuItem jMenuItemQuit;
    private JMenuBar jMenuBarFrame;
    private JTable jTableProperties;
    private JScrollPane jScrollPaneProperties;
    //private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";

    public CompareRessourceBundleFrame()
    {
        initComponents();
        initFixComponents();
    }

    private void initComponents() {
    	add(getJScrollPaneProperties(), BorderLayout.CENTER);
    	setJMenuBar(getJMenuBarFrame());
    	setSize(640, 440);
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
        
        updateDisplay();

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
                                                 new LastSelectedFilesAccessoryDefaultConfigurator() 
                                                 )
                                         )
                                );
                    }
                });

        jMenuLastOpenFiles.setEnabled(false);
        
    }

    private JScrollPane getJScrollPaneProperties() {
    	if (jScrollPaneProperties == null) {
    	    jScrollPaneProperties = new JScrollPane();
    	    jScrollPaneProperties.setViewportView(getJTableProperties());
    	}
    	return jScrollPaneProperties;
    }

    private JTable getJTableProperties() {
    	if (jTableProperties == null) {
    	    jTableProperties = new JTable();
//    	    jTableProperties.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { { "0x0", "0x1", }, { "1x0", "1x1", }, }, new String[] { "Title 0", "Title 1", }) {
//    			private static final long serialVersionUID = 1L;
//    			Class<?>[] types = new Class<?>[] { Object.class, Object.class, };
//    
//    			public Class<?> getColumnClass(int columnIndex) {
//    				return types[columnIndex];
//    			}
//    		});
    	}
    	return jTableProperties;
    }

    private JMenuBar getJMenuBarFrame() {
    	if (jMenuBarFrame == null) {
    		jMenuBarFrame = new JMenuBar();
            jMenuBarFrame.add(getJMenuFile());
    	}
    	return jMenuBarFrame;
    }
    
    private JMenu getJMenuLookAndFeel() {
        if (jMenuLookAndFeel == null) {
            jMenuLookAndFeel = new JMenu();
            jMenuLookAndFeel.setText("Look And Feel");
        }
        return jMenuLookAndFeel;
    }
    
    private JMenuItem getJMenuItemLoadLeftFileReadOnly() {
        if (jMenuItemLoadLeftFileReadOnly == null) {
            jMenuItemLoadLeftFileReadOnly = new JMenuItem();
            jMenuItemLoadLeftFileReadOnly.setText("Load left read only");
            jMenuItemLoadLeftFileReadOnly.addMouseListener(new MouseAdapter() {
    
                public void mousePressed(MouseEvent event) {
                    jMenuItemLoadLeftFileReadOnly_MouseMousePressed(event);
                }
            });
        }
        return jMenuItemLoadLeftFileReadOnly;
    }

    private JMenu getJMenuFile() {
    	if (jMenuFile == null) {
    		jMenuFile = new JMenu();
    		jMenuFile.setText("File");
    		jMenuFile.setOpaque(false);
    		jMenuFile.add(getJMenuLeftFile());
    		jMenuFile.add(getJMenuRightFile());
    		jMenuFile.add(getJMenuLastOpenFiles());
    		jMenuFile.add(getJMenuItemSaveAll());
    		jMenuFile.add(getJMenuItemQuit());
    	}
    	return jMenuFile;
    }

    private JMenuItem getJMenuItemQuit() {
    	if (jMenuItemQuit == null) {
    	    jMenuItemQuit = new JMenuItem();
    	    jMenuItemQuit.setText("Quit");
    	    jMenuItemQuit.addMouseListener(new MouseAdapter() {
    
    			public void mousePressed(MouseEvent event) {
    				jMenuItem_Quit_MouseMousePressed(event);
    			}
    		});
    	}
    	return jMenuItemQuit;
    }

    private JMenu getJMenuLastOpenFiles()
    {
        if(jMenuLastOpenFiles == null) {
            jMenuLastOpenFiles = new JMenu();
            jMenuLastOpenFiles.setText( "Last open files" );
        }

        return jMenuLastOpenFiles;
    }
    
    private JMenuItem getJMenuItemSaveAll() {
    	if (jMenuItemSaveAll == null) {
    	    jMenuItemSaveAll = new JMenuItem();
    	    jMenuItemSaveAll.setText("Save all");
    	    jMenuItemSaveAll.addMouseListener(new MouseAdapter() {
    
    			public void mousePressed(MouseEvent event) {
    		        jMenuItem_SaveLeftFile_MouseMousePressed(event);
    		        jMenuItem_SaveRightFile_MouseMousePressed(event);
    			}
    		});
    	}
    	return jMenuItemSaveAll;
    }

    private JMenu getJMenuRightFile() {
    	if (jMenuRightFile == null) {
    	    jMenuRightFile = new JMenu();
    	    jMenuRightFile.setText("Right");
    	    jMenuRightFile.setOpaque(false);
    	    jMenuRightFile.add(getJMenuItemLoadRightFile());
    	    jMenuRightFile.add(getJMenuItemSaveRightFile());
    	}
    	return jMenuRightFile;
    }

    private JMenuItem getJMenuItemSaveRightFile() {
    	if (jMenuItemSaveRightFile == null) {
    	    jMenuItemSaveRightFile = new JMenuItem();
    	    jMenuItemSaveRightFile.setText("Save right");
    	    jMenuItemSaveRightFile.addMouseListener(new MouseAdapter() {
    
    			public void mousePressed(MouseEvent event) {
    				jMenuItem_SaveRightFile_MouseMousePressed(event);
    			}
    		});
    	}
    	return jMenuItemSaveRightFile;
    }

    private JMenuItem getJMenuItemLoadRightFile() {
    	if (jMenuItemLoadRightFile == null) {
    	    jMenuItemLoadRightFile = new JMenuItem();
    	    jMenuItemLoadRightFile.setText("Load right");
    	    jMenuItemLoadRightFile.addMouseListener(new MouseAdapter() {
    
    			public void mousePressed(MouseEvent event) {
    				jMenuItem_LoadRightFile_MouseMousePressed(event);
    			}
    		});
    	}
    	return jMenuItemLoadRightFile;
    }

    private JMenu getJMenuLeftFile() {
    	if (jMenuLeftFile == null) {
    		jMenuLeftFile = new JMenu();
    		jMenuLeftFile.setText("Left");
    		jMenuLeftFile.setOpaque(false);
    		jMenuLeftFile.add(getJMenuItemLoadLeftFile());
    		jMenuLeftFile.add(getJMenuItemLoadLeftFileReadOnly());
    		jMenuLeftFile.add(getJMenuItemSaveLeftFile());
    	}
    	return jMenuLeftFile;
    }

    private JMenuItem getJMenuItemSaveLeftFile() {
    	if (jMenuItemSaveLeftFile == null) {
    	    jMenuItemSaveLeftFile = new JMenuItem();
    	    jMenuItemSaveLeftFile.setText("Save left");
    	    jMenuItemSaveLeftFile.addMouseListener(new MouseAdapter() {
    
    			public void mousePressed(MouseEvent event) {
    				jMenuItem_SaveLeftFile_MouseMousePressed(event);
    			}
    		});
    	}
    	return jMenuItemSaveLeftFile;
    }

    private JMenuItem getJMenuItemLoadLeftFile() {
    	if (jMenuItemLoadLeftFile == null) {
    	    jMenuItemLoadLeftFile = new JMenuItem();
    	    jMenuItemLoadLeftFile.setText("Load left");
    	    jMenuItemLoadLeftFile.addMouseListener(new MouseAdapter() {
    
    			public void mousePressed(MouseEvent event) {
    				jMenuItemLoadLeftFile_MouseMousePressed(event);
    			}
    		});
    	}
    	return jMenuItemLoadLeftFile;
    }

//    @SuppressWarnings("unused")
//    private static void installLnF()
//    {
//        try {
//            String lnfClassname = PREFERRED_LOOK_AND_FEEL;
//            if( lnfClassname == null )
//                lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
//            UIManager.setLookAndFeel( lnfClassname );
//        }
//        catch( Exception e ) {
//            System.err.println( "Cannot install " + PREFERRED_LOOK_AND_FEEL
//                    + " on this platform:" + e.getMessage() );
//        }
//    }

    public static void main( String[] args )
    {
        //installLnF();
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                CompareRessourceBundleFrame frame = new CompareRessourceBundleFrame();
                frame.setDefaultCloseOperation( CompareRessourceBundleFrame.EXIT_ON_CLOSE );
                frame.setTitle( "CompareRessourceBundleFrame" );
                frame.getContentPane().setPreferredSize( frame.getSize() );
                frame.pack();
                frame.setLocationRelativeTo( null );
                frame.setVisible( true );
            }
        } );
    }
    
    protected void updateDisplay()
    {
        slogger.info( "Left :" + leftFile);
        slogger.info( "Right:" + rightFile);

        if( leftFile != null ) {
            jMenuItemSaveLeftFile.setEnabled( 
                !leftFile.isReadOnly() 
                );
        }
        else {
            jMenuItemSaveLeftFile.setEnabled( true );
        }
        
        try {
            this.tableModel = new CompareRessourceBundleTableModel(
                                        this.leftFile, 
                                        this.rightFile
                                        );
        }
        catch( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        jTableProperties = tableModel.getJTable();
        jScrollPaneProperties.setViewportView( jTableProperties );
        jTableProperties.setModel(this.tableModel);
        jTableProperties.setAutoCreateRowSorter(true);
        tableModel.setColumnWidth(jTableProperties);
    }
    
    protected JFileChooser getJFileChooser()
    {
        return jFileChooserInitializer.getJFileChooser();
    }
    
    private FileObject getLoadFile(boolean readOnly)
    {
        final JFileChooser  fc          = getJFileChooser();
        int                 returnVal   = fc.showOpenDialog( this );

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return new FileObject(fc.getSelectedFile(), readOnly);
        }
        return null;
    }
    
    private void jMenuItemLoadLeftFile_MouseMousePressed(MouseEvent event) 
    {
        this.leftFile = getLoadFile(false);
        slogger.info( "Left File:" + this.leftFile);
        updateDisplay();
    }

    private void jMenuItemLoadLeftFileReadOnly_MouseMousePressed(MouseEvent event) 
    {
        this.leftFile = getLoadFile(true);
        slogger.info( "Left File:" + this.leftFile);
        updateDisplay();
    }

    private void jMenuItem_SaveLeftFile_MouseMousePressed(MouseEvent event) 
    {
        slogger.info( "request to save Left");
        try {
            if( tableModel.saveLeftFile(this.leftFile) ) {
                JOptionPane.showMessageDialog(
                        this,
                        "File '" + leftFile.getDisplayName() + "' saved."
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

    private void jMenuItem_LoadRightFile_MouseMousePressed(MouseEvent event) 
    {
        this.rightFile = getLoadFile(false);
        slogger.info( "Right File:" + this.rightFile);
        updateDisplay();
    }

    private void jMenuItem_SaveRightFile_MouseMousePressed(MouseEvent event) 
    {
        slogger.info( "request to save Right");
        try {
            if( tableModel.saveRightFile(this.rightFile) ) {
                JOptionPane.showMessageDialog(
                        this,
                        "File '" + rightFile.getDisplayName() + "' saved."
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

    private void jMenuItem_Quit_MouseMousePressed(MouseEvent event) 
    {
        dispose();
    }
}
