package cx.ath.choisnet.tools.duplicatefiles.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelConfig;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelConfirm;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelResult;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelSearching;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelSelectFoldersOrFiles;

//VS 4E -- DO NOT REMOVE THIS LINE!
public class DuplicateFilesFrameVS4E extends JFrame
{
    private static final long serialVersionUID = 1L;
    protected JMenuBar jMenuBarMain;
    private JMenu jMenuConfig;
    protected JMenu jMenuLookAndFeel;
    private JMenu jMenuConfigMode;

    private ButtonGroup buttonGroupConfigMode;
    protected JCheckBoxMenuItem jCheckBoxMenuItemModeBegin;
    protected JCheckBoxMenuItem jCheckBoxMenuItemModeAdvance;
    protected JCheckBoxMenuItem jCheckBoxMenuItemModeExpert;

    protected JTabbedPane jTabbedPaneMain;
    protected JPanelSelectFoldersOrFiles jPanel0Select;
    protected JPanelConfig jPanel1Config;
    protected JPanelSearching jPanel2Searching;
    protected JPanelResult jPanel3Result;
    protected JPanelConfirm jPanel4Confirm;

    private JPanel jPanelBottom;
    protected JButton jButtonNextStep;
    protected JButton jButtonRestart;

    public DuplicateFilesFrameVS4E()
    {
        initComponents();
    }

    private void initComponents() {
        add(getJTabbedPanejTabbedPaneMain(), BorderLayout.CENTER);
        add(getJPanelBottom(), BorderLayout.SOUTH);
        setJMenuBar(getJMenuBarMain());
        initButtonGroup0();
        setSize(600, 500);
    }

    private void initButtonGroup0() {
        buttonGroupConfigMode = new ButtonGroup();
        buttonGroupConfigMode.add(getJCheckBoxMenuItemModeBegin());
        buttonGroupConfigMode.add(getJCheckBoxMenuItemModeAdvance());
        buttonGroupConfigMode.add(getJCheckBoxMenuItemModeExpert());
    }

    private JMenu getJMenuConfigMode() {
        if (jMenuConfigMode == null) {
            jMenuConfigMode = new JMenu();
            jMenuConfigMode.setText("Mode");
            jMenuConfigMode.add(getJCheckBoxMenuItemModeBegin());
            jMenuConfigMode.add(getJCheckBoxMenuItemModeAdvance());
            jMenuConfigMode.add(getJCheckBoxMenuItemModeExpert());
        }
        return jMenuConfigMode;
    }

    private JCheckBoxMenuItem getJCheckBoxMenuItemModeExpert() {
        if (jCheckBoxMenuItemModeExpert == null) {
            jCheckBoxMenuItemModeExpert = new JCheckBoxMenuItem();
            jCheckBoxMenuItemModeExpert.setText("Expert");
        }
        return jCheckBoxMenuItemModeExpert;
    }

    private JCheckBoxMenuItem getJCheckBoxMenuItemModeAdvance() {
        if (jCheckBoxMenuItemModeAdvance == null) {
            jCheckBoxMenuItemModeAdvance = new JCheckBoxMenuItem();
            jCheckBoxMenuItemModeAdvance.setSelected(true);
            jCheckBoxMenuItemModeAdvance.setText("Advance");
        }
        return jCheckBoxMenuItemModeAdvance;
    }

    private JCheckBoxMenuItem getJCheckBoxMenuItemModeBegin() {
        if (jCheckBoxMenuItemModeBegin == null) {
            jCheckBoxMenuItemModeBegin = new JCheckBoxMenuItem();
            jCheckBoxMenuItemModeBegin.setSelected(true);
            jCheckBoxMenuItemModeBegin.setText("Beginner");
        }
        return jCheckBoxMenuItemModeBegin;
    }

    private JTabbedPane getJTabbedPanejTabbedPaneMain() {
        if (jTabbedPaneMain == null) {
            jTabbedPaneMain = new JTabbedPane();
            jTabbedPaneMain.addTab("Select", getJPanel0jPanel0Select());
            jTabbedPaneMain.addTab("Search config", getJPanel1Config());
            jTabbedPaneMain.addTab("Searching...", getJPanel2Searching());
            jTabbedPaneMain.addTab("Duplicates", getJPanel3Result());
            jTabbedPaneMain.addTab("Confirm", getJPanel4Confirm());
        }
        return jTabbedPaneMain;
    }

    private JPanelSelectFoldersOrFiles getJPanel0jPanel0Select() {
        if (jPanel0Select == null) {
            jPanel0Select = new JPanelSelectFoldersOrFiles();
        }
        return jPanel0Select;
    }

    private JPanelConfig getJPanel1Config() {
        if (jPanel1Config == null) {
            jPanel1Config = new JPanelConfig();
        }
        return jPanel1Config;
    }

    private JPanel getJPanel2Searching() {
        if (jPanel2Searching == null) {
            jPanel2Searching = new JPanelSearching();
        }
        return jPanel2Searching;
    }

    private JPanel getJPanel3Result() {
        if (jPanel3Result == null) {
            jPanel3Result = new JPanelResult();
        }
        return jPanel3Result;
    }

    private JPanel getJPanel4Confirm() {
        if (jPanel4Confirm == null) {
            jPanel4Confirm = new JPanelConfirm();
        }
        return jPanel4Confirm;
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
            jMenuBarMain.add(getJMenuConfig());
        }
        return jMenuBarMain;
    }

    private JMenu getJMenuConfig() {
        if (jMenuConfig == null) {
            jMenuConfig = new JMenu();
            jMenuConfig.setText("Config");
            jMenuConfig.setOpaque(false);
            jMenuConfig.add(getJMenuConfigMode());
        }
        return jMenuConfig;
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

}
