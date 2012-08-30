package com.googlecode.cchlib.apps.duplicatefiles.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenuItem;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.JPanelConfig;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.JPanelResult;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.JPanelSearching;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.JPanelSelectFoldersOrFiles;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.confirm.JPanelConfirm;
//import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelConfig;
//import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelResult;
//import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelSearching;
//import cx.ath.choisnet.tools.duplicatefiles.gui.panel.JPanelSelectFoldersOrFiles;
//import cx.ath.choisnet.tools.duplicatefiles.gui.panel.confirm.JPanelConfirm;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.CardLayout;
import java.util.TooManyListenersException;

/**
 * Main frame layout.
 */
public abstract class DuplicateFilesFrameWB extends JFrame
{
    private static final long serialVersionUID = 1L;
    //private static final Logger logger = Logger.getLogger( DuplicateFilesFrameWB.class );
    private DFToolKit dfToolKit;

    public static final String ACTIONCMD_DELETE_EMPTY_DIRECTORIES = "ACTIONCMD_DELETE_EMPTY_DIRECTORIES";
    public static final String ACTIONCMD_RESTART = "ACTIONCMD_RESTART";
    public static final String ACTIONCMD_NEXT = "ACTIONCMD_NEXT";
    public static final String ACTIONCMD_CANCEL = "ACTIONCMD_CANCEL";
    //public static final String ACTIONCMD_SET_LOCALE = "ACTIONCMD_SET_LOCALE";
    public static final String ACTIONCMD_SET_MODE = "ACTIONCMD_SET_MODE";
    public static final String ACTIONCMD_EXIT = "ACTIONCMD_EXIT";
    //public static final String ACTIONCMD_SAVE_PREFS = "ACTIONCMD_SAVE_PREFS";
    public static final String ACTIONCMD_PREFS = "ACTIONCMD_PREFS";

    private JPanel contentPane;
    private ButtonGroup buttonGroupConfigMode = new ButtonGroup();
    private ButtonGroup buttonGroupLanguage = new ButtonGroup();
    private CardLayout                  jPanelMainCardLayout;
    private JButton                     jButtonCancel;
    private JButton                     jButtonNextStep;
    private JButton                     jButtonRestart;
    private JMenu                       jMenuConfig;
    private JMenu                       jMenuConfigMode;
    private JMenu                       jMenuFile;
    //private JMenu                       jMenuItemLanguage;
    private JMenu                       jMenuLookAndFeel;
    private JMenu                       jMenuTools;
    private JMenuItem                   jMenuItemDeleteEmptyDirectories;
    private JMenuItem                   jMenuItemExit;
    //private JMenuItem                   jMenuItem_SavePrefs;
    private JMenuItem                   jMenuItem_Preferences;
    private JPanel                      jPanelMain;
    private JPanelSelectFoldersOrFiles  jPanel0Select;
    private JPanelConfig                jPanel1Config;
    private JPanelConfirm               jPanel4Confirm;
    private JPanelResult                jPanel3Result;
    private JPanelSearching             jPanel2Searching;
    //private JRadioButtonMenuItem        jMenuItemLanguageDefaultSystem;
    private JRadioButtonMenuItem        jMenuItemModeAdvance;
    private JRadioButtonMenuItem        jMenuItemModeBeginner;
    private JRadioButtonMenuItem        jMenuItemModeExpert;

    /**
     * Create the frame.
     * @param dfToolKit
     * @throws TooManyListenersException
     * @throws HeadlessException
     */
    public DuplicateFilesFrameWB( final DFToolKit dfToolKit )
        throws HeadlessException, TooManyListenersException
    {
        this.dfToolKit = dfToolKit;
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBounds(100, 100, 450, 300);
        setSize(650, 500); // Just for design

        JMenuBar jMenuBarMain = new JMenuBar();
        setJMenuBar(jMenuBarMain);

        jMenuFile = new JMenu("File");
        jMenuBarMain.add(jMenuFile);

        jMenuItemExit = new JMenuItem("Exit");
        jMenuItemExit.setActionCommand( ACTIONCMD_EXIT );
        jMenuItemExit.addActionListener( getActionListener() );
        jMenuFile.add( jMenuItemExit );

        jMenuConfig = new JMenu("Configuration");
        jMenuBarMain.add(jMenuConfig);

        jMenuConfigMode = new JMenu("Mode");
        jMenuConfig.add(jMenuConfigMode);

        jMenuItemModeBeginner = new JRadioButtonMenuItem( "Beginner" );
        buttonGroupConfigMode.add( jMenuItemModeBeginner );
        jMenuItemModeBeginner.setActionCommand( ACTIONCMD_SET_MODE );
        jMenuItemModeBeginner.putClientProperty( ConfigMode.class, ConfigMode.BEGINNER );
        jMenuItemModeBeginner.addActionListener( getActionListener() );
        jMenuConfigMode.add( jMenuItemModeBeginner );

        jMenuItemModeAdvance = new JRadioButtonMenuItem( "Advance" );
        buttonGroupConfigMode.add( jMenuItemModeAdvance );
        jMenuItemModeAdvance.setActionCommand( ACTIONCMD_SET_MODE );
        jMenuItemModeAdvance.putClientProperty( ConfigMode.class, ConfigMode.ADVANCED );
        jMenuItemModeAdvance.addActionListener( getActionListener() );
        jMenuConfigMode.add( jMenuItemModeAdvance );

        jMenuItemModeExpert = new JRadioButtonMenuItem( "Expert" );
        buttonGroupConfigMode.add( jMenuItemModeExpert );
        jMenuItemModeExpert.setActionCommand( ACTIONCMD_SET_MODE );
        jMenuItemModeExpert.putClientProperty( ConfigMode.class, ConfigMode.EXPERT );
        jMenuItemModeExpert.addActionListener( getActionListener() );
        jMenuConfigMode.add( jMenuItemModeExpert );

//        JMenu jMenuItemLanguage = createJMenuLanguage();
//        jMenuConfig.add(jMenuItemLanguage);

        jMenuConfig.addSeparator();

        jMenuItem_Preferences = new JMenuItem("Preferences");
        jMenuItem_Preferences.setActionCommand( ACTIONCMD_PREFS );
        jMenuItem_Preferences.addActionListener( getActionListener() );
        jMenuConfig.add(jMenuItem_Preferences);
//      jMenuItem_SavePrefs = new JMenuItem("Save preferences");
//      jMenuItem_SavePrefs.setActionCommand( ACTIONCMD_SAVE_PREFS );
//      jMenuItem_SavePrefs.addActionListener( getActionListener() );
//      jMenuConfig.add(jMenuItem_SavePrefs);

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

        {
            jPanelMain = new JPanel();
            //jPanelMain.setEnabled( false );
            GridBagConstraints gbc_jPanelMain = new GridBagConstraints();
            gbc_jPanelMain.anchor = GridBagConstraints.WEST;
            gbc_jPanelMain.gridwidth = 4;
            gbc_jPanelMain.gridheight = 1;
            gbc_jPanelMain.insets = new Insets(0, 0, 5, 0);
            gbc_jPanelMain.fill = GridBagConstraints.BOTH;
            gbc_jPanelMain.gridx = 0;
            gbc_jPanelMain.gridy = 0;
            contentPane.add(jPanelMain, gbc_jPanelMain);
            jPanelMainCardLayout = new CardLayout(0, 0);
            jPanelMain.setLayout( jPanelMainCardLayout );

//            panel = new JPanel();
//            jTabbedPaneMain.add(panel, "name_57516025965064");
            int panelNumber = 0;
            jPanel0Select = createJPanel0Select();
            jPanelMain.add( jPanel0Select, Integer.toString( panelNumber++ ) );

            jPanel1Config = createJPanel1Config();
            jPanelMain.add( jPanel1Config, Integer.toString( panelNumber++ ) );

            jPanel2Searching = createJPanel2Searching();
            jPanelMain.add( jPanel2Searching, Integer.toString( panelNumber++ ) );

            jPanel3Result = createJPanel3Result();
            jPanelMain.add( jPanel3Result, Integer.toString( panelNumber++ ) );

            jPanel4Confirm = createJPanel4Confirm();
            jPanelMain.add( jPanel4Confirm, Integer.toString( panelNumber++ ) );
        }

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

    protected ButtonGroup getButtonGroupConfigMode()
    {
        return buttonGroupConfigMode;
    }

    protected ButtonGroup getButtonGroupLanguage()
    {
        return buttonGroupLanguage;
    }

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

    public JPanelResult getJPanel3Result()
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

    protected void selectedPanel( final int state )
    {
        jPanelMainCardLayout.show(
                jPanelMain,
                Integer.toString( state )
                );
    }

    public abstract ActionListener getActionListener();
    protected abstract void exitApplication();

    public DFToolKit getDFToolKit()
    {
        if( this.dfToolKit == null ) {
            throw new NullPointerException( "DFToolKit not initialized" );
            }

        return this.dfToolKit;
    }
//    private JMenu createJMenuLanguage()
//    {
//        jMenuItemLanguage = new JMenu("Language");
//
//        LocaleList localeList = new LocaleList();
//
//        for( ListInfo<Locale> li : localeList ) {
//            JMenuItem jmi = new JRadioButtonMenuItem( li.toString() );
//            buttonGroupLanguage.add( jmi );
//            jmi.setActionCommand( ACTIONCMD_SET_LOCALE );
//            jmi.putClientProperty( Locale.class, li.getContent() );
//            jmi.addActionListener( getActionListener() );
//            jMenuItemLanguage.add( jmi );
//            }
//
//        return jMenuItemLanguage;
//    }

    /**
     * @throws TooManyListenersException
     * @throws HeadlessException
     * @wbp.factory
     */
    public JPanelSelectFoldersOrFiles createJPanel0Select()
        throws HeadlessException, TooManyListenersException
    {
        return new JPanelSelectFoldersOrFiles( getDFToolKit() );
    }

    /**
     * @wbp.factory
     */
    public JPanelConfig createJPanel1Config()
    {
        return new JPanelConfig( getDFToolKit() );
    }

    /**
     * @wbp.factory
     */
    public /*static*/ JPanelSearching createJPanel2Searching()
    {
        return new JPanelSearching();
    }

    /**
     * @wbp.factory
     */
    public JPanelResult createJPanel3Result()
    {
        return new JPanelResult( getDFToolKit() );
    }

    /**
     * @wbp.factory
     */
    public JPanelConfirm createJPanel4Confirm()
    {
        return new JPanelConfirm( getDFToolKit() );
    }
}
