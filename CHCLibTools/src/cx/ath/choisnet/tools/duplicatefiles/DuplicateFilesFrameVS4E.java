/**
 *
 */
package cx.ath.choisnet.tools.duplicatefiles;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

//VS4E -- DO NOT REMOVE THIS LINE!
public class DuplicateFilesFrameVS4E extends JFrame
{
    private static final long   serialVersionUID        = 1L;
    private JButton jButtonAddDir;
    private JButton jButtonRemDir;
    private JButton jButtonSelectDir;
    private JMenu jMenuConfig;
    private JPanel jPanel0SelectFolders;
    private JPanel jPanel1Searching;
    private JPanel jPanel2Result;
    private JPanel jPanelBottom;
    private JPanel jPanelDirButtons;
    private JScrollPane jScrollPaneSelectedDirs;
    protected JTabbedPane jTabbedPaneMain;
    protected JButton jButtonNextStep;
    protected JButton jButtonRestart;
    protected JList jListSelectedDirs;
    protected JMenu jMenuLookAndFeel;
    protected JMenuBar jMenuBarMain;
    protected JTextField jTextFieldCurrentDir;
    private JLabel jLabelSearchProcess;
    protected JProgressBar jProgressBarFiles;
    protected JProgressBar jProgressBarOctets;
    protected JLabel jLabelCurrentFile;
    protected JTextField jTextFieldCurrentFile;
    protected JLabel jLabelDuplicateSetsFound;
    protected JLabel jLabelDuplicateFilesFound;
    protected JLabel jLabelBytesReadFromDisk;
    protected JButton jButtonCancelScan;
    private JPanel jPanelResultsButtons;
    private JSplitPane jSplitPaneResultMain;
    private JButton jButtonPrevSet;
    protected JList jListDuplicatesFiles;
    protected JList jListKeptIntact;
    protected JList jListWillBeDeleted;
    private JScrollPane jScrollPaneDuplicatesFiles;
    private JSplitPane jSplitPaneResultRight;
    private JScrollPane jScrollPaneKeptIntact;
    private JScrollPane jScrollPaneWillBeDeleted;
    private JButton jButtonNextSet;
    protected JTextField jTextFieldFileInfo;
    private JPanel jPanelDuplicateBottom;
    protected JTable jTableErrorList;
    private JScrollPane jScrollPaneErrorList;
    //private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
    public DuplicateFilesFrameVS4E()
    {
        initComponents();
    }

    private void initComponents() {
        add(getJTabbedPanejTabbedPaneMain(), BorderLayout.CENTER);
        add(getJPanelBottom(), BorderLayout.SOUTH);
        setJMenuBar(getJMenuBarMain());
        setSize(600, 342);
    }

    private JScrollPane getJScrollPaneErrorList() {
        if (jScrollPaneErrorList == null) {
            jScrollPaneErrorList = new JScrollPane();
            jScrollPaneErrorList.setViewportView(getJTableErrorList());
        }
        return jScrollPaneErrorList;
    }

    private JTable getJTableErrorList() {
        if (jTableErrorList == null) {
            jTableErrorList = new JTable();
//          jTableErrorList.setModel(new DefaultTableModel(new Object[][] { { "0x0", "0x1", }, { "1x0", "1x1", }, }, new String[] { "File", "Error", }) {
//              private static final long serialVersionUID = 1L;
//              Class<?>[] types = new Class<?>[] { Object.class, Object.class, };
//
//              public Class<?> getColumnClass(int columnIndex) {
//                  return types[columnIndex];
//              }
//          });
        }
        return jTableErrorList;
    }

    private JPanel getJPanelDuplicateBottom() {
        if (jPanelDuplicateBottom == null) {
            jPanelDuplicateBottom = new JPanel();
            //jPanelDuplicateBottom.add(getJScrollPane0());
            jPanelDuplicateBottom.add(getJButtonCancelScan());
        }
        return jPanelDuplicateBottom;
    }

    private JTextField getJTextFieldFileInfo() {
        if (jTextFieldFileInfo == null) {
            jTextFieldFileInfo = new JTextField();
            //jTextFieldFileInfo.setEnabled(false);
            jTextFieldFileInfo.setEditable( false );
        }
        return jTextFieldFileInfo;
    }

    private JButton getJButtonNextSet() {
        if (jButtonNextSet == null) {
            jButtonNextSet = new JButton();
            jButtonNextSet.setText(">>");
            jButtonNextSet.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonNextSetMouseMousePressed(event);
                }
            });
        }
        return jButtonNextSet;
    }

    private JScrollPane getJScrollPaneWillBeDeleted() {
        if (jScrollPaneWillBeDeleted == null) {
            jScrollPaneWillBeDeleted = new JScrollPane();
            jScrollPaneWillBeDeleted.setViewportView(getJListWillBeDeleted());
        }
        return jScrollPaneWillBeDeleted;
    }

    private JList getJListWillBeDeleted() {
        if (jListWillBeDeleted == null) {
            jListWillBeDeleted = new JList();
//          DefaultListModel listModel = new DefaultListModel();
//          listModel.addElement("WillBeDeleted");
//          listModel.addElement("item1");
//          listModel.addElement("item2");
//          listModel.addElement("item3");
//          jListWillBeDeleted.setModel(listModel);
        }
        return jListWillBeDeleted;
    }

    private JScrollPane getJScrollPaneKeptIntact() {
        if (jScrollPaneKeptIntact == null) {
            jScrollPaneKeptIntact = new JScrollPane();
            jScrollPaneKeptIntact.setViewportView(getJListKeptIntact());
        }
        return jScrollPaneKeptIntact;
    }

    private JList getJListKeptIntact() {
        if (jListKeptIntact == null) {
            jListKeptIntact = new JList();
//          DefaultListModel listModel = new DefaultListModel();
//          listModel.addElement("item0___");
//          listModel.addElement("item1");
//          listModel.addElement("item2");
//          listModel.addElement("item3");
//          jListKeptIntact.setModel(listModel);
        }
        return jListKeptIntact;
    }

    private JSplitPane getJSplitPaneResultRight() {
        if (jSplitPaneResultRight == null) {
            jSplitPaneResultRight = new JSplitPane();
            jSplitPaneResultRight.setDividerLocation(100);
            jSplitPaneResultRight.setOrientation(JSplitPane.VERTICAL_SPLIT);
            jSplitPaneResultRight.setTopComponent(getJScrollPaneKeptIntact());
            jSplitPaneResultRight.setBottomComponent(getJScrollPaneWillBeDeleted());
        }
        return jSplitPaneResultRight;
    }

    private JScrollPane getJScrollPaneDuplicatesFiles() {
        if (jScrollPaneDuplicatesFiles == null) {
            jScrollPaneDuplicatesFiles = new JScrollPane();
            jScrollPaneDuplicatesFiles.setViewportView(getJListDuplicatesFiles());
        }
        return jScrollPaneDuplicatesFiles;
    }

    private JList getJListDuplicatesFiles() {
        if (jListDuplicatesFiles == null) {
            jListDuplicatesFiles = new JList();
//          DefaultListModel listModel = new DefaultListModel();
//          listModel.addElement("jListDuplicatesFiles");
//          listModel.addElement("item1");
//          listModel.addElement("item2");
//          listModel.addElement("item3");
//          jListDuplicatesFiles.setModel(listModel);
        }
        return jListDuplicatesFiles;
    }

    private JButton getJButtonPrevSet() {
        if (jButtonPrevSet == null) {
            jButtonPrevSet = new JButton();
            jButtonPrevSet.setText("<<");
            jButtonPrevSet.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonPrevSetMouseMousePressed(event);
                }
            });
        }
        return jButtonPrevSet;
    }

    private JSplitPane getJSplitPaneResultMain() {
        if (jSplitPaneResultMain == null) {
            jSplitPaneResultMain = new JSplitPane();
            jSplitPaneResultMain.setDividerLocation(100);
            jSplitPaneResultMain.setLeftComponent(getJScrollPaneDuplicatesFiles());
            jSplitPaneResultMain.setRightComponent(getJSplitPaneResultRight());
        }
        return jSplitPaneResultMain;
    }

    private JPanel getJPanelResultsButtons() {
        if (jPanelResultsButtons == null) {
            jPanelResultsButtons = new JPanel();
            jPanelResultsButtons.setLayout(new BoxLayout(jPanelResultsButtons, BoxLayout.X_AXIS));
            jPanelResultsButtons.add(getJButtonPrevSet());
            jPanelResultsButtons.add(getJButtonNextSet());
            jPanelResultsButtons.add(getJTextFieldFileInfo());
        }
        return jPanelResultsButtons;
    }

    private JButton getJButtonCancelScan() {
        if (jButtonCancelScan == null) {
            jButtonCancelScan = new JButton();
            jButtonCancelScan.setText("Cancel");
            jButtonCancelScan.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonCancelScanMouseMousePressed(event);
                }
            });
        }
        return jButtonCancelScan;
    }

    private JLabel getJLabelBytesReadFromDisk() {
        if (jLabelBytesReadFromDisk == null) {
            jLabelBytesReadFromDisk = new JLabel();
            jLabelBytesReadFromDisk.setText("BytesReadFromDisk");
        }
        return jLabelBytesReadFromDisk;
    }

    private JLabel getJLabelDuplicateFilesFound() {
        if (jLabelDuplicateFilesFound == null) {
            jLabelDuplicateFilesFound = new JLabel();
            jLabelDuplicateFilesFound.setText("DuplicateFilesFound");
        }
        return jLabelDuplicateFilesFound;
    }

    private JLabel getJLabelDuplicateSetsFound() {
        if (jLabelDuplicateSetsFound == null) {
            jLabelDuplicateSetsFound = new JLabel();
            jLabelDuplicateSetsFound.setText("DuplicateSetsFound");
        }
        return jLabelDuplicateSetsFound;
    }

    private JTextField getJTextFieldCurrentFile() {
        if (jTextFieldCurrentFile == null) {
            jTextFieldCurrentFile = new JTextField();
            //jTextFieldCurrentFile.setHorizontalAlignment(SwingConstants.RIGHT);
            //jTextFieldCurrentFile.setText("...");
            jTextFieldCurrentFile.setEditable( false );
        }
        return jTextFieldCurrentFile;
    }

    private JLabel getJLabelCurrentFile() {
        if (jLabelCurrentFile == null) {
            jLabelCurrentFile = new JLabel();
            jLabelCurrentFile.setText("CurrentFile");
        }
        return jLabelCurrentFile;
    }

    private JProgressBar getJProgressBarOctets() {
        if (jProgressBarOctets == null) {
            jProgressBarOctets = new JProgressBar();
            //jProgressBarOctets.setString("0 %");
        }
        return jProgressBarOctets;
    }

    private JProgressBar getJProgressBarFiles() {
        if (jProgressBarFiles == null) {
            jProgressBarFiles = new JProgressBar();
            //jProgressBarFiles.setString("0 %");
        }
        return jProgressBarFiles;
    }

    private JLabel getJLabelSearchProcess() {
        if (jLabelSearchProcess == null) {
            jLabelSearchProcess = new JLabel();
            //jLabelSearchProcess.setText("<html>Search progress...<br><br></html>");
            jLabelSearchProcess.setText("Search progress...");
        }
        return jLabelSearchProcess;
    }

    final
    protected JMenu getJMenuLookAndFeel()
    {
        if( jMenuLookAndFeel == null ) {
            jMenuLookAndFeel = new JMenu();
            jMenuLookAndFeel.setText( "Look And Feel" );
        }
        return jMenuLookAndFeel;
    }

    private JScrollPane getJScrollPaneSelectedDirs() {
        if (jScrollPaneSelectedDirs == null) {
            jScrollPaneSelectedDirs = new JScrollPane();
            jScrollPaneSelectedDirs.setViewportView(getJListSelectedDirs());
        }
        return jScrollPaneSelectedDirs;
    }

    private JList getJListSelectedDirs() {
        if (jListSelectedDirs == null) {
            jListSelectedDirs = new JList();
        }
        return jListSelectedDirs;
    }

    private JButton getJButtonRemDir() {
        if (jButtonRemDir == null) {
            jButtonRemDir = new JButton();
            jButtonRemDir.setText("Rem");
            jButtonRemDir.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonRemDirMouseMousePressed(event);
                }
            });
        }
        return jButtonRemDir;
    }

    private JPanel getJPanelDirButtons() {
        if (jPanelDirButtons == null) {
            jPanelDirButtons = new JPanel();
            jPanelDirButtons.setLayout(new BoxLayout(jPanelDirButtons, BoxLayout.X_AXIS));
            jPanelDirButtons.add(getJTextFieldCurrentDir());
            jPanelDirButtons.add(getJButtonSelectDir());
            jPanelDirButtons.add(getJButtonAddDir());
            jPanelDirButtons.add(getJButtonRemDir());
        }
        return jPanelDirButtons;
    }

    private JButton getJButtonAddDir() {
        if (jButtonAddDir == null) {
            jButtonAddDir = new JButton();
            jButtonAddDir.setText("Add");
            jButtonAddDir.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonAddDirMouseMousePressed(event);
                }
            });
        }
        return jButtonAddDir;
    }

    private JButton getJButtonSelectDir() {
        if (jButtonSelectDir == null) {
            jButtonSelectDir = new JButton();
            jButtonSelectDir.setText("...");
            jButtonSelectDir.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonSelectDirMouseMousePressed(event);
                }
            });
        }
        return jButtonSelectDir;
    }

    private JTextField getJTextFieldCurrentDir() {
        if (jTextFieldCurrentDir == null) {
            jTextFieldCurrentDir = new JTextField();
        }
        return jTextFieldCurrentDir;
    }

    private JMenuBar getJMenuBarMain() {
        if (jMenuBarMain == null) {
            jMenuBarMain = new JMenuBar();
            jMenuBarMain.add(getJMenuConfig());
        }
        return jMenuBarMain;
    }

    private JMenu getJMenuConfig()
    {
        if( jMenuConfig == null ) {
            jMenuConfig = new JMenu("Config");
        }
        return jMenuConfig;
    }

    private JPanel getJPanel2Result() {
        if (jPanel2Result == null) {
            jPanel2Result = new JPanel();
            jPanel2Result.setLayout(new BorderLayout());
            jPanel2Result.add(getJSplitPaneResultMain(), BorderLayout.CENTER);
            jPanel2Result.add(getJPanelResultsButtons(), BorderLayout.NORTH);
        }
        return jPanel2Result;
    }

    private JPanel getJPanelBottom() {
        if (jPanelBottom == null) {
            jPanelBottom = new JPanel();
            jPanelBottom.setLayout(new BoxLayout(jPanelBottom, BoxLayout.X_AXIS));
            jPanelBottom.add(getJButtonRestart());
            jPanelBottom.add(getJButtonNextStep());
        }
        return jPanelBottom;
    }

    private JButton getJButtonNextStep() {
        if (jButtonNextStep == null) {
            jButtonNextStep = new JButton();
            jButtonNextStep.setText("Next");
            jButtonNextStep.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonNextStepMouseMousePressed(event);
                }
            });
        }
        return jButtonNextStep;
    }

    private JButton getJButtonRestart() {
        if (jButtonRestart == null) {
            jButtonRestart = new JButton();
            jButtonRestart.setText("Restart");
            jButtonRestart.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonRestartMouseMousePressed(event);
                }
            });
        }
        return jButtonRestart;
    }

    private JTabbedPane getJTabbedPanejTabbedPaneMain() {
        if (jTabbedPaneMain == null) {
            jTabbedPaneMain = new JTabbedPane();
            jTabbedPaneMain.addTab("Select folders", getJPanel0SelectFolders());
            jTabbedPaneMain.addTab("Searching...", getJPanel1Searching());
            jTabbedPaneMain.addTab("Results", getJPanel2Result());
        }
        return jTabbedPaneMain;
    }

    private JPanel getJPanel0SelectFolders() {
        if (jPanel0SelectFolders == null) {
            jPanel0SelectFolders = new JPanel();
            jPanel0SelectFolders.setLayout(new BorderLayout());
            jPanel0SelectFolders.add(getJScrollPaneSelectedDirs(), BorderLayout.CENTER);
            jPanel0SelectFolders.add(getJPanelDirButtons(), BorderLayout.NORTH);
        }
        return jPanel0SelectFolders;
    }

    private JPanel getJPanel1Searching() {
        if (jPanel1Searching == null) {
            jPanel1Searching = new JPanel();
            jPanel1Searching.setLayout(new BoxLayout(jPanel1Searching, BoxLayout.Y_AXIS));
            jPanel1Searching.add(getJLabelSearchProcess());
            jPanel1Searching.add(getJProgressBarFiles());
            jPanel1Searching.add(getJProgressBarOctets());
            jPanel1Searching.add(getJLabelCurrentFile());
            jPanel1Searching.add(getJTextFieldCurrentFile());
            jPanel1Searching.add(getJLabelDuplicateSetsFound());
            jPanel1Searching.add(getJLabelDuplicateFilesFound());
            jPanel1Searching.add(getJLabelBytesReadFromDisk());
            jPanel1Searching.add(getJScrollPaneErrorList());
            jPanel1Searching.add(getJPanelDuplicateBottom());
        }
        return jPanel1Searching;
    }

    public static void main( String[] args )
    {
        //installLnF();
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                DuplicateFilesFrameVS4E frame = new DuplicateFilesFrameVS4E();
                frame.setDefaultCloseOperation( DuplicateFilesFrameVS4E.EXIT_ON_CLOSE );
                frame.setTitle( "DuplicateFilesFrameVS4E" );
                frame.getContentPane().setPreferredSize( frame.getSize() );
                frame.pack();
                frame.setLocationRelativeTo( null );
                frame.setVisible( true );
            }
        } );
    }

    protected void jButtonSelectDirMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

    protected void jButtonAddDirMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

    protected void jButtonRemDirMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

    protected void jButtonRestartMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

    protected void jButtonNextStepMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

    protected void jButtonCancelScanMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

    protected void jButtonPrevSetMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

    protected void jButtonNextSetMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

}
