package com.googlecode.cchlib.apps.duplicatefiles.gui;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenuItem;
import java.awt.HeadlessException;
import javax.swing.ButtonGroup;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.MyStaticResources;
import com.googlecode.cchlib.apps.emptydirectories.gui.RemoveEmptyDirectoriesPanel;
import com.googlecode.cchlib.apps.emptyfiles.RemoveEmptyFilesJPanel;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
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

    public static final int RemoveEmptyDirectories_TAB = 1;
    public static final int DeleteEmptyFiles_TAB = 2;

    public static final String ACTIONCMD_SET_MODE = "ACTIONCMD_SET_MODE";
    public static final String ACTIONCMD_EXIT     = "ACTIONCMD_EXIT";
    public static final String ACTIONCMD_PREFS    = "ACTIONCMD_PREFS";
    public static final String ACTIONCMD_ABOUT    = "ACTIONCMD_ABOUT";

    private ButtonGroup buttonGroupConfigMode = new ButtonGroup();
    private ButtonGroup buttonGroupLanguage   = new ButtonGroup();
    private Icon                        icon_DuplicateFiles = MyStaticResources.getDuplicateFilesPanelIcon();
    private Icon                        icon_RemoveEmptyDirectories = MyStaticResources.getRemoveEmptyDirectoriesPanelIcon();
    private Icon                        icon_DeleteEmptyFiles = MyStaticResources.getDeleteEmptyFilesPanelIcon();
    private JMenu                       jMenuConfig;
    private JMenu                       jMenuConfigMode;
    private JMenu                       jMenuFile;
    private JMenu                       jMenuLookAndFeel;
    private JMenuItem                   jMenuItemExit;
    private JMenuItem                   jMenuItem_Preferences;

    private DuplicateFilesMainPanel     jPanel_DuplicateFiles;
    private RemoveEmptyDirectoriesPanel jPanel_RemoveEmptyDirectories;
    private RemoveEmptyFilesJPanel      jPanel_DeleteEmptyFiles;

    private JRadioButtonMenuItem        jMenuItemModeAdvance;
    private JRadioButtonMenuItem        jMenuItemModeBeginner;
    private JRadioButtonMenuItem        jMenuItemModeExpert;

    @I18nToolTipText private JTabbedPane contentJTabbedPane;

    private JMenu jMenuHelp;
    private JMenuItem jMenuItem_About;

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

        jMenuHelp = new JMenu("Help");
        jMenuBarMain.add(jMenuHelp);

        jMenuItem_About = new JMenuItem("About");
        jMenuItem_About.setActionCommand( ACTIONCMD_ABOUT );
        jMenuItem_About.addActionListener( getActionListener() );
        jMenuHelp.add(jMenuItem_About);

        jMenuLookAndFeel = new JMenu("Look and Feel");
        jMenuBarMain.add( Box.createHorizontalGlue() );
        jMenuBarMain.add(jMenuLookAndFeel);

        {
        this.contentJTabbedPane = new JTabbedPane();
        setContentPane( this.contentJTabbedPane );

        // Workaround for WindowBuilder
        if( dfToolKit == null ) {this.jPanel_DuplicateFiles = new DuplicateFilesMainPanel();}
        // $hide>>$
        this.jPanel_DuplicateFiles = new DuplicateFilesMainPanel( dfToolKit, getActionListener() );
        // $hide<<$
        this.contentJTabbedPane.addTab("Duplicate files", this.icon_DuplicateFiles, this.jPanel_DuplicateFiles, "Find and remove Duplicate files");

        this.jPanel_RemoveEmptyDirectories = new RemoveEmptyDirectoriesPanel( dfToolKit, this );
        this.contentJTabbedPane.addTab("Remove empty directories", this.icon_RemoveEmptyDirectories, this.jPanel_RemoveEmptyDirectories, "Find and delete empty directories" );
        assert (this.contentJTabbedPane.getTabCount() - 1) == RemoveEmptyDirectories_TAB;

        this.jPanel_DeleteEmptyFiles = new RemoveEmptyFilesJPanel( dfToolKit );
        this.contentJTabbedPane.addTab("Remove empty files", this.icon_DeleteEmptyFiles, this.jPanel_DeleteEmptyFiles, "Find and delete empty files");
        assert (this.contentJTabbedPane.getTabCount() - 1) == DeleteEmptyFiles_TAB;
        }
    }

    protected void setEnabledAt( int index, boolean enabled )
    {
        this.contentJTabbedPane.setEnabledAt( index, enabled );
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
        return jPanel_DuplicateFiles;
    }

    RemoveEmptyDirectoriesPanel getRemoveEmptyDirectoriesPanel()
    {
        return jPanel_RemoveEmptyDirectories;
    }

    RemoveEmptyFilesJPanel getDeleteEmptyFilesPanel()
    {
        return jPanel_DeleteEmptyFiles;
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
}
