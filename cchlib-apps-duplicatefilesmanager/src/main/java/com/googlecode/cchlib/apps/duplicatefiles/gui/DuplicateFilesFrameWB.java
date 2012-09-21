package com.googlecode.cchlib.apps.duplicatefiles.gui;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenuItem;
import java.awt.HeadlessException;
import javax.swing.ButtonGroup;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.apps.emptydirectories.gui.RemoveEmptyDirectoriesPanel;
import com.googlecode.cchlib.i18n.I18nString;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.TooManyListenersException;
import javax.swing.JTabbedPane;

/**
 * Main frame layout.
 */
public abstract class DuplicateFilesFrameWB extends JFrame
{
    private static final long serialVersionUID = 2L;
    private DFToolKit dfToolKit;

    public static final String ACTIONCMD_SET_MODE = "ACTIONCMD_SET_MODE";
    public static final String ACTIONCMD_EXIT = "ACTIONCMD_EXIT";
    public static final String ACTIONCMD_PREFS = "ACTIONCMD_PREFS";

    private ButtonGroup buttonGroupConfigMode = new ButtonGroup();
    private ButtonGroup buttonGroupLanguage   = new ButtonGroup();
    private Icon                        icon_DuplicateFiles; // TODO : add
    private Icon                        icon_jPanel_RemoveEmptyDirectories; // TODO : add
    private JMenu                       jMenuConfig;
    private JMenu                       jMenuConfigMode;
    private JMenu                       jMenuFile;
    private JMenu                       jMenuLookAndFeel;
    private JMenuItem                   jMenuItemExit;
    private JMenuItem                   jMenuItem_Preferences;
    private JPanel                      jPanel_DuplicateFiles; // Workaround for WindowBuilder
    private JPanel                      jPanel_RemoveEmptyDirectories; // Workaround for WindowBuilder
    private JRadioButtonMenuItem        jMenuItemModeAdvance;
    private JRadioButtonMenuItem        jMenuItemModeBeginner;
    private JRadioButtonMenuItem        jMenuItemModeExpert;
    private JTabbedPane                 contentJTabbedPane;
    
    @I18nString private String tip_DuplicateFiles = "Find and remove Duplicate files"; 
    @I18nString private String tip_jPanel_RemoveEmptyDirectories = "Find and delete empty directories";
    
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

        jMenuConfig.addSeparator();

        jMenuItem_Preferences = new JMenuItem("Preferences");
        jMenuItem_Preferences.setActionCommand( ACTIONCMD_PREFS );
        jMenuItem_Preferences.addActionListener( getActionListener() );
        jMenuConfig.add(jMenuItem_Preferences);

        jMenuLookAndFeel = new JMenu("Look and Feel");
        jMenuBarMain.add( Box.createHorizontalGlue() );
        jMenuBarMain.add(jMenuLookAndFeel);
        
        
        {
        contentJTabbedPane = new JTabbedPane();
        setContentPane(contentJTabbedPane);

        // Workaround for WindowBuilder
        jPanel_DuplicateFiles = new JPanel();
        // $hide>>$
        jPanel_DuplicateFiles = new DuplicateFilesMainPanel( dfToolKit, getActionListener() );
        // $hide<<$

        contentJTabbedPane.addTab("Duplicate files", icon_DuplicateFiles, jPanel_DuplicateFiles, null);
        
        // Workaround for WindowBuilder
        jPanel_RemoveEmptyDirectories = new JPanel();
        // $hide>>$
        jPanel_RemoveEmptyDirectories = new RemoveEmptyDirectoriesPanel( dfToolKit, this );
        // $hide<<$
        
        contentJTabbedPane.addTab("Remove empty directories", icon_jPanel_RemoveEmptyDirectories, jPanel_RemoveEmptyDirectories, null );
        }
    }

    protected void updateI18nData()
    {
        contentJTabbedPane.setToolTipTextAt( 0, tip_DuplicateFiles );
        contentJTabbedPane.setToolTipTextAt( 1, tip_jPanel_RemoveEmptyDirectories );
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
    
    public DuplicateFilesMainPanel getDuplicateFilesMainPanel()
    {
        // Workaround for WindowBuilder
        return DuplicateFilesMainPanel.class.cast( jPanel_DuplicateFiles );
    }
    
    public RemoveEmptyDirectoriesPanel getRemoveEmptyDirectoriesPanel()
    {
        // Workaround for WindowBuilder
        return RemoveEmptyDirectoriesPanel.class.cast( jPanel_RemoveEmptyDirectories );
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

//    /**
//     * @throws TooManyListenersException
//     * @throws HeadlessException
//     * @wbp.factory
//     */
//    public JPanelSelectFoldersOrFiles createJPanel0Select()
//        throws HeadlessException, TooManyListenersException
//    {
//        return new JPanelSelectFoldersOrFiles( getDFToolKit() );
//    }
//
//    /**
//     * @wbp.factory
//     */
//    public JPanelConfig createJPanel1Config()
//    {
//        return new JPanelConfig( getDFToolKit() );
//    }
//
//    /**
//     * @wbp.factory
//     */
//    public JPanelSearching createJPanel2Searching()
//    {
//        return new JPanelSearching();
//    }
//
//    /**
//     * @wbp.factory
//     */
//    public JPanelResult createJPanel3Result()
//    {
//        return new JPanelResult( getDFToolKit() );
//    }
//
//    /**
//     * @wbp.factory
//     */
//    public JPanelConfirm createJPanel4Confirm()
//    {
//        return new JPanelConfirm( getDFToolKit() );
//    }
}
