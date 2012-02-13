package cx.ath.choisnet.tools.duplicatefiles.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractButton;
import javax.swing.Box;
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
import cx.ath.choisnet.tools.duplicatefiles.ConfigMode;
import cx.ath.choisnet.tools.duplicatefiles.DFToolKit;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelConfig;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelConfirm;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelResult;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelSearching;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelSelectFoldersOrFiles;
import java.awt.event.ActionListener;
import java.util.Locale;

/**
 *
 *
 */
public abstract class DuplicateFilesFrameWB extends JFrame
{
    private static final long serialVersionUID = 1L;
    public static final String ACTIONCMD_DELETE_EMPTY_DIRECTORIES = "ACTIONCMD_DELETE_EMPTY_DIRECTORIES";
    public static final String ACTIONCMD_RESTART = "ACTIONCMD_RESTART";
    public static final String ACTIONCMD_NEXT = "ACTIONCMD_NEXT";
    public static final String ACTIONCMD_CANCEL = "ACTIONCMD_CANCEL";
    public static final String ACTIONCMD_SET_LOCALE = "ACTIONCMD_SET_LOCALE";
    public static final String ACTIONCMD_SET_MODE = "ACTIONCMD_SET_MODE";
    //private static final Logger logger = Logger.getLogger( DuplicateFilesFrameWB.class );
    private JPanel contentPane;
    private ButtonGroup buttonGroupConfigMode = new ButtonGroup();
    private ButtonGroup buttonGroupLanguage = new ButtonGroup();
    private JMenu jMenuLookAndFeel;
    private JPanelSelectFoldersOrFiles  jPanel0Select;
    private JPanelConfig                jPanel1Config;
    private JPanelSearching             jPanel2Searching;
    private JPanelResult                jPanel3Result;
    private JPanelConfirm               jPanel4Confirm;
    private JButton                     jButtonNextStep;
    private JMenuBar                    jMenuBarMain;
    private JButton                     jButtonRestart;
    private JButton                     jButtonCancel;
    private JMenu                       jMenuConfig;
    private JMenu                       jMenuTools;
    private JMenuItem                   jMenuItemDeleteEmptyDirectories;
    private JMenu                       jMenuConfigMode;
    private JTabbedPane                 jTabbedPaneMain;

    /**
     * Create the frame.
     * @param dfToolKit
     */
    public DuplicateFilesFrameWB()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBounds(100, 100, 450, 300);
        setSize(650, 500);

        jMenuBarMain = new JMenuBar();
        setJMenuBar(jMenuBarMain);

        jMenuConfig = new JMenu("Config");
        jMenuBarMain.add(jMenuConfig);

        jMenuConfigMode = new JMenu("Mode");
        jMenuConfig.add(jMenuConfigMode);

        JRadioButtonMenuItem jMenuItemModeBeginner = new JRadioButtonMenuItem( "Beginner" );
        jMenuItemModeBeginner.setSelected(true); // TODO prefs !
        buttonGroupConfigMode.add( jMenuItemModeBeginner );
        jMenuItemModeBeginner.setActionCommand( ACTIONCMD_SET_MODE );
        jMenuItemModeBeginner.putClientProperty( ConfigMode.class, ConfigMode.BEGINNER );
        jMenuItemModeBeginner.addActionListener( getActionListener() );
        jMenuConfigMode.add( jMenuItemModeBeginner );

        JRadioButtonMenuItem jMenuItemModeAdvance = new JRadioButtonMenuItem( "Advance" );
        buttonGroupConfigMode.add( jMenuItemModeAdvance );
        jMenuItemModeAdvance.setActionCommand( ACTIONCMD_SET_MODE );
        jMenuItemModeAdvance.putClientProperty( ConfigMode.class, ConfigMode.ADVANCED );
        jMenuItemModeAdvance.addActionListener( getActionListener() );
        jMenuConfigMode.add( jMenuItemModeAdvance );

        JRadioButtonMenuItem jMenuItemModeExpert = new JRadioButtonMenuItem( "Expert" );
        buttonGroupConfigMode.add( jMenuItemModeExpert );
        jMenuItemModeExpert.setActionCommand( ACTIONCMD_SET_MODE );
        jMenuItemModeExpert.putClientProperty( ConfigMode.class, ConfigMode.EXPERT );
        jMenuItemModeExpert.addActionListener( getActionListener() );
        jMenuConfigMode.add( jMenuItemModeExpert );

        JMenu jMenuItemLanguage = createJMenuLanguage();
        jMenuConfig.add(jMenuItemLanguage);

        jMenuTools = new JMenu("Tools");
        jMenuBarMain.add(jMenuTools);

        jMenuItemDeleteEmptyDirectories = new JMenuItem("Delete Empty Directories");
        jMenuItemDeleteEmptyDirectories.setActionCommand( ACTIONCMD_DELETE_EMPTY_DIRECTORIES );
        jMenuItemDeleteEmptyDirectories.addActionListener( getActionListener() );
        jMenuTools.add(jMenuItemDeleteEmptyDirectories);

        jMenuLookAndFeel = new JMenu("Look and Feel");
        jMenuBarMain.add( Box.createHorizontalGlue() );
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
        jTabbedPaneMain.setEnabled( false );
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
        jButtonRestart.setActionCommand( ACTIONCMD_RESTART );
        jButtonRestart.addActionListener( getActionListener() );
        GridBagConstraints gbc_jButtonRestart = new GridBagConstraints();
        gbc_jButtonRestart.anchor = GridBagConstraints.WEST;
        gbc_jButtonRestart.insets = new Insets(0, 0, 0, 5);
        gbc_jButtonRestart.gridx = 0;
        gbc_jButtonRestart.gridy = 1;
        contentPane.add(jButtonRestart, gbc_jButtonRestart);

        jButtonCancel = new JButton("Cancel");
        jButtonCancel.setActionCommand( ACTIONCMD_CANCEL );
        jButtonCancel.addActionListener( getActionListener() );
        GridBagConstraints gbc_jButtonCancel = new GridBagConstraints();
        gbc_jButtonCancel.anchor = GridBagConstraints.WEST;
        gbc_jButtonCancel.insets = new Insets(0, 0, 0, 5);
        gbc_jButtonCancel.gridx = 1;
        gbc_jButtonCancel.gridy = 1;
        contentPane.add(jButtonCancel, gbc_jButtonCancel);

        jButtonNextStep = new JButton( "Next" );
        jButtonNextStep.setActionCommand( ACTIONCMD_NEXT );
        jButtonNextStep.addActionListener( getActionListener() );
        GridBagConstraints gbc_jButtonNextStep = new GridBagConstraints();
        gbc_jButtonNextStep.anchor = GridBagConstraints.EAST;
        gbc_jButtonNextStep.gridx = 3;
        gbc_jButtonNextStep.gridy = 1;
        contentPane.add(getJButtonNextStep(), gbc_jButtonNextStep);
    }

//    protected JTabbedPane getJTabbedPaneMain()
//    {
//        return jTabbedPaneMain;
//    }

    protected JMenu getJMenuLookAndFeel()
    {
        return jMenuLookAndFeel;
    }

    protected JButton getJButtonNextStep()
    {
        return jButtonNextStep;
    }

    protected JButton getJButtonCancel()
    {
        return jButtonCancel;
    }

    public abstract ActionListener getActionListener();
    public abstract DFToolKit getDFToolKit();

    /**
     * @wbp.factory
     */
    public JMenu createJMenuLanguage()
    {
        JMenu jMenuItemLanguage = new JMenu("Language");

        JMenuItem jMenuItemLanguageDefaultSystem = new JRadioButtonMenuItem(" Default system" );
        buttonGroupLanguage.add( jMenuItemLanguageDefaultSystem );
        jMenuItemLanguageDefaultSystem.setSelected(true); // TODO: prefs
        jMenuItemLanguageDefaultSystem.setActionCommand( ACTIONCMD_SET_LOCALE );
        jMenuItemLanguageDefaultSystem.putClientProperty( Locale.class, null );
        jMenuItemLanguageDefaultSystem.addActionListener( getActionListener() );
        jMenuItemLanguage.add( jMenuItemLanguageDefaultSystem );

        JMenuItem jMenuItemLanguageEnglish = new JRadioButtonMenuItem( "English" );
        buttonGroupLanguage.add( jMenuItemLanguageEnglish );
        jMenuItemLanguageEnglish.setActionCommand( ACTIONCMD_SET_LOCALE );
        jMenuItemLanguageEnglish.putClientProperty( Locale.class, Locale.ENGLISH );
        jMenuItemLanguageEnglish.addActionListener( getActionListener() );
        jMenuItemLanguage.add( jMenuItemLanguageEnglish );

        JMenuItem jMenuItemLanguageFrench = new JRadioButtonMenuItem( "French" );
        buttonGroupLanguage.add( jMenuItemLanguageFrench );
        jMenuItemLanguageFrench.setActionCommand( ACTIONCMD_SET_LOCALE );
        jMenuItemLanguageFrench.putClientProperty( Locale.class, Locale.FRENCH );
        jMenuItemLanguageFrench.addActionListener( getActionListener() );
        jMenuItemLanguage.add( jMenuItemLanguageFrench );

        return jMenuItemLanguage;
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
     * @param modalBlocker
     * @param dfToolKit
     * @wbp.factory
     */
    public JPanelConfirm createJPanel4Confirm()
    {
        return new JPanelConfirm( getDFToolKit() );
    }

    protected JPanelSelectFoldersOrFiles getJPanel0Select()
    {
        return jPanel0Select;
    }

    protected JPanelConfig getJPanel1Config()
    {
        return jPanel1Config;
    }

    protected JPanelSearching getJPanel2Searching()
    {
        return jPanel2Searching;
    }

    protected JPanelResult getJPanel3Result()
    {
        return jPanel3Result;
    }

    protected JPanelConfirm getJPanel4Confirm()
    {
        return jPanel4Confirm;
    }

    protected AbstractButton getJButtonRestart()
    {
        return jButtonRestart;
    }

    protected JTabbedPane getJTabbedPaneMain()
    {
        return jTabbedPaneMain;
    }

}
