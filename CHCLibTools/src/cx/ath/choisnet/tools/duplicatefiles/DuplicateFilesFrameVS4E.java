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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

//VS 4E -- DO NOT REMOVE THIS LINE!
public class DuplicateFilesFrameVS4E extends JFrame
{
    private static final long serialVersionUID = 1L;
    protected JMenuBar jMenuBarMain;
    //private JMenu jMenuConfig;
    protected JMenu jMenuLookAndFeel;
    
    protected JTabbedPane jTabbedPaneMain;
    protected JPanelSelectFolders jPanel0SelectFolders;
    protected JPanelConfig jPanel1Config;
    protected JPanelSearching jPanel2Searching;
    protected JPanelResult jPanel3Result;
    protected JPanelConfirm jPanel4Confirm;
    
    private JPanel jPanelBottom;
    protected JButton jButtonNextStep;
    protected JButton jButtonRestart;
    //protected JButton jButtonExtra;

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

//    private JButton getJButton0() {
//    	if (jButtonExtra == null) {
//    		jButtonExtra = new JButton();
//    		jButtonExtra.addMouseListener(new MouseAdapter() {
//    
//    			public void mousePressed(MouseEvent event) {
//    				jButtonExtraMouseMousePressed(event);
//    			}
//    		});
//    	}
//    	return jButtonExtra;
//    }

    private JPanel getJPanel4Confirm() {
        if (jPanel4Confirm == null) {
            jPanel4Confirm = new JPanelConfirm();
        }
        return jPanel4Confirm;
    }

    private JPanelConfig getJPanel1Config() {
        if (jPanel1Config == null) {
            jPanel1Config = new JPanelConfig();
        }
        return jPanel1Config;
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

    private JMenuBar getJMenuBarMain() {
        if (jMenuBarMain == null) {
            jMenuBarMain = new JMenuBar();
            //jMenuBarMain.add(getJMenuConfig());
        }
        return jMenuBarMain;
    }

//    private JMenu getJMenuConfig()
//    {
//        if( jMenuConfig == null ) {
//            jMenuConfig = new JMenu("Config");
//        }
//        return jMenuConfig;
//    }

    private JPanel getJPanel3Result() {
        if (jPanel3Result == null) {
            jPanel3Result = new JPanelResult();
//            jPanel3Result.setLayout(new BorderLayout());
//            jPanel3Result.add(getJSplitPaneResultMain(), BorderLayout.CENTER);
//            jPanel3Result.add(getJPanelResultsButtons(), BorderLayout.NORTH);
        }
        return jPanel3Result;
    }

    private JPanel getJPanelBottom() {
    	if (jPanelBottom == null) {
    		jPanelBottom = new JPanel();
    		jPanelBottom.setLayout(new BoxLayout(jPanelBottom, BoxLayout.X_AXIS));
    		jPanelBottom.add(getJButtonRestart());
    		jPanelBottom.add(getJButtonNextStep());
//    		jPanelBottom.add(getJButton0());
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
            jTabbedPaneMain.addTab("Search config", getJPanel1Config());
            jTabbedPaneMain.addTab("Searching...", getJPanel2Searching());
            jTabbedPaneMain.addTab("Duplicates", getJPanel3Result());
            jTabbedPaneMain.addTab("Confirm", getJPanel4Confirm());
        }
        return jTabbedPaneMain;
    }

    private JPanelSelectFolders getJPanel0SelectFolders() {
        if (jPanel0SelectFolders == null) {
            jPanel0SelectFolders = new JPanelSelectFolders();
        }
        return jPanel0SelectFolders;
    }

    private JPanel getJPanel2Searching() {
        if (jPanel2Searching == null) {
            jPanel2Searching = new JPanelSearching();
        }
        return jPanel2Searching;
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

    protected void jButtonRestartMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

    protected void jButtonNextStepMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

    protected void jButtonExtraMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

}
