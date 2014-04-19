package com.googlecode.cchlib.apps.duplicatefiles.gui;

import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKitService;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.IconResources;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;
import com.googlecode.cchlib.apps.emptydirectories.gui.RemoveEmptyDirectoriesPanel;
import com.googlecode.cchlib.apps.emptyfiles.RemoveEmptyFilesJPanel;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.TooManyListenersException;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
/**
 * Main frame layout.
 */
public abstract class DuplicateFilesFrameWB extends JFrame // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.preferInterfacesToAbstractClasses, largeNumberOfFields
{
    private static final long serialVersionUID = 2L;
    private final AppToolKit dfToolKit;

    public static final int REMOVE_EMPTY_DIRECTORIES_TAB = 1;
    public static final int DELETE_EMPTY_FILES_TAB = 2;

    public static final String ACTIONCMD_SET_MODE = "ACTIONCMD_SET_MODE";
    public static final String ACTIONCMD_EXIT     = "ACTIONCMD_EXIT";
    public static final String ACTIONCMD_PREFS    = "ACTIONCMD_PREFS";
    public static final String ACTIONCMD_ABOUT    = "ACTIONCMD_ABOUT";

    private final IconResources iconResources = IconResources.getInstance();

    private final ButtonGroup buttonGroupConfigMode = new ButtonGroup();
    private final ButtonGroup buttonGroupLanguage   = new ButtonGroup();

    private final JMenu                       jMenuConfig;
    private final JMenu                       jMenuConfigMode;
    private final JMenu                       jMenuFile;
    private final JMenu                       jMenuLookAndFeel;
    private final JMenuItem                   jMenuItemExit;
    private final JMenuItem                   jMenuItem_Preferences;

    private DuplicateFilesMainPanel     jPanel_DuplicateFiles;
    private final RemoveEmptyDirectoriesPanel jPanel_RemoveEmptyDirectories;
    private final RemoveEmptyFilesJPanel      jPanel_DeleteEmptyFiles;

    private final JRadioButtonMenuItem        jMenuItemModeAdvance;
    private final JRadioButtonMenuItem        jMenuItemModeBeginner;
    private final JRadioButtonMenuItem        jMenuItemModeExpert;

    @I18nToolTipText private final JTabbedPane contentJTabbedPane;

    private final JMenu jMenuHelp;
    private final JMenuItem jMenuItem_About;

    /**
     * Create the frame.
     * @param dfToolKit
     * @throws TooManyListenersException
     * @throws HeadlessException
     */
    public DuplicateFilesFrameWB( final Preferences preferences )
        throws HeadlessException, TooManyListenersException
    {
        this.dfToolKit = AppToolKitService.getInstance().createAppToolKit( preferences, (DuplicateFilesFrame)this );

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });

        setSize(650, 500); // Just for design // $codepro.audit.disable numericLiterals

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
        this.jPanel_DuplicateFiles = new DuplicateFilesMainPanel( getActionListener() );
        // $hide<<$
        this.contentJTabbedPane.addTab("Duplicate files", this.iconResources.getDuplicateFilesPanelIcon(), this.jPanel_DuplicateFiles, "Find and remove Duplicate files");

        this.jPanel_RemoveEmptyDirectories = new RemoveEmptyDirectoriesPanel( this );
        this.contentJTabbedPane.addTab("Remove empty directories", this.iconResources.getRemoveEmptyDirectoriesPanelIcon(), this.jPanel_RemoveEmptyDirectories, "Find and delete empty directories" );
        assert (this.contentJTabbedPane.getTabCount() - 1) == REMOVE_EMPTY_DIRECTORIES_TAB;

        this.jPanel_DeleteEmptyFiles = new RemoveEmptyFilesJPanel();
        this.contentJTabbedPane.addTab("Remove empty files", this.iconResources.getDeleteEmptyFilesPanelIcon(), this.jPanel_DeleteEmptyFiles, "Find and delete empty files");
        assert (this.contentJTabbedPane.getTabCount() - 1) == DELETE_EMPTY_FILES_TAB;
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

    public AppToolKit getDFToolKit()
    {
        if( this.dfToolKit == null ) {
            throw new NullPointerException( "DFToolKit not initialized" );
            }

        return this.dfToolKit;
    }
}
