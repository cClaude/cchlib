package cx.ath.choisnet.tools.duplicatefiles.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelConfig;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelConfirm;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelResult;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelSearching;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelSelectFoldersOrFiles;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 *
 */
public abstract class DuplicateFilesFrameWB extends JFrame
{
    private static final long serialVersionUID = 1L;
    //private static final Logger logger = Logger.getLogger( DuplicateFilesFrameWB.class );
    private JPanel contentPane;
    protected JRadioButtonMenuItem jMenuItemModeBegin;
    protected JRadioButtonMenuItem jMenuItemModeAdvance;
    protected JRadioButtonMenuItem jMenuItemModeExpert;
    private ButtonGroup buttonGroupConfigMode = new ButtonGroup();
    private ButtonGroup buttonGroupLanguage = new ButtonGroup();
    protected JMenu jMenuLookAndFeel;
    protected JTabbedPane jTabbedPaneMain;
    protected JPanelSelectFoldersOrFiles	jPanel0Select;
    protected JPanelConfig 					jPanel1Config;
    protected JPanelSearching 				jPanel2Searching;
    protected JPanelResult 					jPanel3Result;
    protected JPanelConfirm 				jPanel4Confirm;
    protected JButton jButtonNextStep;
    protected JMenuBar jMenuBarMain;
    protected JButton jButtonRestart;
    private JButton jButtonCancel;
    private JMenu jMenuConfig;
    private JMenu jMenuTools;
    protected JMenuItem jMenuItemDeleteEmptyDirectories;
    private JMenu jMenuConfigMode;
    private JMenu jMenuItemLanguage;
    protected JMenuItem jMenuItemLanguageDefaultSystem;
    protected JMenuItem jMenuItemLanguageEnglish;
    protected JMenuItem jMenuItemLanguageFrench;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DuplicateFilesFrameWB frame = new DuplicateFilesFrameWB()
                    {
                        private static final long serialVersionUID = 1L;
                        @Override
                        protected void jButtonNextStepMouseMousePressed( MouseEvent event )
                        {
                            throw new UnsupportedOperationException();
                        }
                        @Override
                        protected void jButtonRestartMouseMousePressed( MouseEvent event )
                        {
                            throw new UnsupportedOperationException();
                        }
                        @Override
                        protected void jMenuItemDeleteEmptyDirectoriesActionPerformed( ActionEvent event )
                        {
                            throw new UnsupportedOperationException();
                        }
                    };
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public DuplicateFilesFrameWB() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBounds(100, 100, 450, 300);
        setSize(650, 500);

        jMenuBarMain = new JMenuBar();
        setJMenuBar(jMenuBarMain);

        jMenuConfig = new JMenu("Config");
        jMenuBarMain.add(jMenuConfig);

        jMenuConfigMode = new JMenu("Mode");
        jMenuConfig.add(jMenuConfigMode);

        jMenuItemModeBegin = new JRadioButtonMenuItem("Beginner");
        jMenuItemModeBegin.setSelected(true);
        buttonGroupConfigMode.add(jMenuItemModeBegin);
        jMenuConfigMode.add(jMenuItemModeBegin);

        jMenuItemModeAdvance = new JRadioButtonMenuItem("Advance");
        buttonGroupConfigMode.add(jMenuItemModeAdvance);
        jMenuConfigMode.add(jMenuItemModeAdvance);

        jMenuItemModeExpert = new JRadioButtonMenuItem("Expert");
        buttonGroupConfigMode.add(jMenuItemModeExpert);
        jMenuConfigMode.add(jMenuItemModeExpert);

        jMenuItemLanguage = new JMenu("Language");
        jMenuConfig.add(jMenuItemLanguage);

        jMenuItemLanguageDefaultSystem = new JMenuItem("Default system");
        jMenuItemLanguageDefaultSystem.setSelected(true);
        buttonGroupLanguage.add(jMenuItemLanguageDefaultSystem);
        jMenuItemLanguage.add(jMenuItemLanguageDefaultSystem);

        jMenuItemLanguageEnglish = new JMenuItem("English");
        buttonGroupLanguage.add(jMenuItemLanguageEnglish);
        jMenuItemLanguage.add(jMenuItemLanguageEnglish);

        jMenuItemLanguageFrench = new JMenuItem("French");
        buttonGroupLanguage.add(jMenuItemLanguageFrench);
        jMenuItemLanguage.add(jMenuItemLanguageFrench);

        jMenuTools = new JMenu("Tools");
        jMenuBarMain.add(jMenuTools);

        jMenuItemDeleteEmptyDirectories = new JMenuItem("Delete Empty Directories");
        jMenuItemDeleteEmptyDirectories.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    jMenuItemDeleteEmptyDirectoriesActionPerformed( e );
                }
            });
        jMenuTools.add(jMenuItemDeleteEmptyDirectories);

        jMenuLookAndFeel = new JMenu("Look and Feel");
        jMenuBarMain.add(jMenuLookAndFeel);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{100, 0, 0};
        gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        jTabbedPaneMain = new JTabbedPane();
        GridBagConstraints gbc_jTabbedPaneMain = new GridBagConstraints();
        gbc_jTabbedPaneMain.anchor = GridBagConstraints.WEST;
        gbc_jTabbedPaneMain.gridwidth = 4;
        gbc_jTabbedPaneMain.gridheight = 1;
        gbc_jTabbedPaneMain.insets = new Insets(0, 0, 5, 0);
        gbc_jTabbedPaneMain.fill = GridBagConstraints.BOTH;
        gbc_jTabbedPaneMain.gridx = 0;
        gbc_jTabbedPaneMain.gridy = 0;
        contentPane.add(jTabbedPaneMain, gbc_jTabbedPaneMain);

        jPanel0Select = createJPanel0Select();
        jTabbedPaneMain.addTab("Select", null, jPanel0Select, null);

        jPanel1Config = createJPanel1Config();
        jTabbedPaneMain.addTab("Search config", null, jPanel1Config, null);

        jPanel2Searching = createJPanel2Searching();
        jTabbedPaneMain.addTab("Search config", null, jPanel2Searching, null);

        jPanel3Result = createJPanel3Result();
        jTabbedPaneMain.addTab("Duplicates", null, jPanel3Result, null);

        jPanel4Confirm = createJPanel4Confirm();
        jTabbedPaneMain.addTab("Confirm", null, jPanel4Confirm, null);

        jButtonRestart = new JButton("Restart");
        jButtonRestart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent event )
            {
                if( jButtonRestart.isEnabled() ) {
                    jButtonRestartMouseMousePressed( event );
                    }
            }
        });
        GridBagConstraints gbc_jButtonRestart = new GridBagConstraints();
        gbc_jButtonRestart.anchor = GridBagConstraints.WEST;
        gbc_jButtonRestart.insets = new Insets(0, 0, 0, 5);
        gbc_jButtonRestart.gridx = 0;
        gbc_jButtonRestart.gridy = 1;
        contentPane.add(jButtonRestart, gbc_jButtonRestart);

        jButtonCancel = new JButton("Cancel");
        GridBagConstraints gbc_jButtonCancel = new GridBagConstraints();
        gbc_jButtonCancel.anchor = GridBagConstraints.WEST;
        gbc_jButtonCancel.insets = new Insets(0, 0, 0, 5);
        gbc_jButtonCancel.gridx = 1;
        gbc_jButtonCancel.gridy = 1;
        contentPane.add(jButtonCancel, gbc_jButtonCancel);

        jButtonNextStep = new JButton("Next");
        jButtonNextStep.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if( jButtonNextStep.isEnabled() ) {
                    jButtonNextStepMouseMousePressed( e );
                    }
            }
        });
        GridBagConstraints gbc_jButtonNextStep = new GridBagConstraints();
        gbc_jButtonNextStep.anchor = GridBagConstraints.EAST;
        gbc_jButtonNextStep.gridx = 3;
        gbc_jButtonNextStep.gridy = 1;
        contentPane.add(jButtonNextStep, gbc_jButtonNextStep);
    }

    /**
     * @wbp.factory
     */
    public static JPanelSelectFoldersOrFiles createJPanel0Select()
    {
        return new JPanelSelectFoldersOrFiles();
    }

    /**
     * @wbp.factory
     */
    public static JPanelConfig createJPanel1Config()
    {
        return new JPanelConfig();
    }

    /**
     * @wbp.factory
     */
    public static JPanelSearching createJPanel2Searching()
    {
        return new JPanelSearching();
    }

    /**
     * @wbp.factory
     */
    public static JPanelResult createJPanel3Result()
    {
        return new JPanelResult();
    }

    /**
     * @wbp.factory
     */
    public static JPanelConfirm createJPanel4Confirm()
    {
        return new JPanelConfirm();
    }

    protected abstract void jButtonNextStepMouseMousePressed( MouseEvent event );
    protected abstract void jButtonRestartMouseMousePressed( MouseEvent event );
    protected abstract void jMenuItemDeleteEmptyDirectoriesActionPerformed( ActionEvent event );

    protected JTabbedPane getJTabbedPaneMain()
    {
        return jTabbedPaneMain;
    }

    protected JMenu getJMenuLookAndFeel()
    {
        return jMenuLookAndFeel;
    }
    protected JButton getJButtonNextStep() {
        return jButtonNextStep;
    }

    public JButton getJButtonCancel() {
        return jButtonCancel;
    }

}
