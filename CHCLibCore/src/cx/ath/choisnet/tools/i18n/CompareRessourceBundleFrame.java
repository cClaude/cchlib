/**
 * 
 */
package cx.ath.choisnet.tools.i18n;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializer;

//VS 4E -- DO NOT REMOVE THIS LINE!
public class CompareRessourceBundleFrame 
    extends JFrame 
{
    private static Logger slogger = Logger.getLogger(CompareRessourceBundleFrame.class);
    private static final long serialVersionUID = 1L;
    private File leftFile;
    private File rightFile;
    private CompareRessourceBundleTableModel tableModel;
    private JFileChooserInitializer jFileChooserInitializer = new JFileChooserInitializer();
    
    private JMenu jMenuFile;
    private JMenu jMenuLeftFile;
    private JMenu jMenuRightFile;
    private JMenu jMenuLastOpenFiles;
    private JMenuItem jMenuItemLoadLeftFile;
    private JMenuItem jMenuItemSaveLeftFile;
    private JMenuItem jMenuItemLoadRightFile;
    private JMenuItem jMenuItemSaveRightFile;
    private JMenuItem jMenuItemSaveBoth;
    private JMenuItem jMenuItemQuit;
    private JMenuBar jMenuBarFrame;
    private JTable jTableProperties;
    private JScrollPane jScrollPaneProperties;
    private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
    public CompareRessourceBundleFrame()
    {
        initComponents();
        initFixComponents();
    }

    private void initComponents() {
        //setLayout(new BorderLayout());
    	add(getJScrollPaneProperties(), BorderLayout.CENTER);
    	setJMenuBar(getJMenuBarFrame());
    	setSize(640, 440);
    }
    
    private void initFixComponents()
    {
        // TODO: test ONLY
        //this.leftFile  = new File("C:/Datas/Java/JMiiEditor/src/jMiiEditor/i18n/MessagesBundle.properties");
        //this.rightFile = new File("C:/Datas/Java/JMiiEditor/src/jMiiEditor/i18n/MessagesBundle_fr_FR.properties");
        
        updateDisplay();
        
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

    private JMenu getJMenuFile() {
    	if (jMenuFile == null) {
    	    jMenuFile = new JMenu();
    	    jMenuFile.setText("File");
    	    jMenuFile.setOpaque(false);
    	    jMenuFile.add(getJMenuLeftFile());
    	    jMenuFile.add(getJMenuRightFile());
    	    jMenuFile.add(getJMenuLastOpenFiles());
    	    jMenuFile.add(getJMenuItemSaveBoth());
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
    
    private JMenuItem getJMenuItemSaveBoth() {
    	if (jMenuItemSaveBoth == null) {
    	    jMenuItemSaveBoth = new JMenuItem();
    	    jMenuItemSaveBoth.setText("Save both");
    	    jMenuItemSaveBoth.addMouseListener(new MouseAdapter() {
    
    			public void mousePressed(MouseEvent event) {
    				jMenuItem_SaveBoth_MouseMousePressed(event);
    			}
    		});
    	}
    	return jMenuItemSaveBoth;
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

    @SuppressWarnings("unused")
    private static void installLnF()
    {
        try {
            String lnfClassname = PREFERRED_LOOK_AND_FEEL;
            if( lnfClassname == null )
                lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel( lnfClassname );
        }
        catch( Exception e ) {
            System.err.println( "Cannot install " + PREFERRED_LOOK_AND_FEEL
                    + " on this platform:" + e.getMessage() );
        }
    }

    /**
     * Main entry of the class.
     * Note: This class is only created so that you can easily preview the result at runtime.
     * It is not expected to be managed by the designer.
     * You can modify it as you like.
     * @param args 
     */
    public static void main( String[] args )
    {
        installLnF();
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
        
       
//        this.tableModel.addTableModelListener(this.jTableProperties);
//        this.jTableProperties.doLayout();

        //        miiCheckerTableModel = new MiiCheckerTableModel( checker );
//        miiCheckerTableModel.addTableModelListener(miiCheckerTableModel);
//        jTable_CheckResult = miiCheckerTableModel.getJTable();
//        jTable_CheckResult.setModel( miiCheckerTableModel );
//        jScrollPane_CheckResult.setViewportView(jTable_CheckResult);
//        miiCheckerTableModel.setColumnWidth( jTable_CheckResult );
    }
    
    protected JFileChooser getJFileChooser()
    {
        return jFileChooserInitializer.getJFileChooser();
    }
    
    private File getLoadFile()
    {
        final JFileChooser  fc          = getJFileChooser();
        int                 returnVal   = fc.showOpenDialog( this );

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        }
        return null;
    }
    
//    private File getSaveFile(File defautFile)
//    {
//        final JFileChooser  fc          = getJFileChooser();
//        int                 returnVal   = fc.showSaveDialog( this );
//
//        if(returnVal == JFileChooser.APPROVE_OPTION) {
//            return fc.getSelectedFile();
//        }
//        return null;
//    }
    
    private void jMenuItemLoadLeftFile_MouseMousePressed(MouseEvent event) 
    {
        this.leftFile = getLoadFile();
        slogger.info( "Left File:" + this.leftFile);
        updateDisplay();
    }

    private void jMenuItem_SaveLeftFile_MouseMousePressed(MouseEvent event) 
    {
        try {
            tableModel.saveLeftFile(this.leftFile,this.leftFile.getPath()); //TODO add comment
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
        this.rightFile = getLoadFile();
        slogger.info( "Right File:" + this.rightFile);
        updateDisplay();
    }

    private void jMenuItem_SaveRightFile_MouseMousePressed(MouseEvent event) 
    {
        try {
            tableModel.saveRightFile(this.rightFile,this.rightFile.getPath()); //TODO add comment
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

    private void jMenuItem_SaveBoth_MouseMousePressed(MouseEvent event) 
    {
        jMenuItem_SaveLeftFile_MouseMousePressed(null);
        jMenuItem_SaveRightFile_MouseMousePressed(null);
    }

    private void jMenuItem_Quit_MouseMousePressed(MouseEvent event) 
    {
        dispose();
    }

}
