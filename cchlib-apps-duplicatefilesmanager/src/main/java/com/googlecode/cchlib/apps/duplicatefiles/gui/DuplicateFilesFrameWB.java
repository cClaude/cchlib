package com.googlecode.cchlib.apps.duplicatefiles.gui;

import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.TooManyListenersException;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKitService;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.IconResources;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.PreferencesControler;
import com.googlecode.cchlib.apps.emptydirectories.gui.RemoveEmptyDirectoriesPanel;
import com.googlecode.cchlib.apps.emptyfiles.RemoveEmptyFilesJPanel;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;

/**
 * Main frame layout.
 */
public abstract class DuplicateFilesFrameWB extends DuplicateFilesFrameI18n // NOSONAR
{
    private final class DuplicateFilesFrameWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(final WindowEvent e) {
            exitApplication();
        }
    }

    private static final long serialVersionUID = 3L;
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

    private DuplicateFilesMainPanel           jPanel_DuplicateFiles;
    private final RemoveEmptyDirectoriesPanel jPanel_RemoveEmptyDirectories;
    private final RemoveEmptyFilesJPanel      jPanel_DeleteEmptyFiles;

    private final JRadioButtonMenuItem        jMenuItemModeAdvance;
    private final JRadioButtonMenuItem        jMenuItemModeBeginner;
    private final JRadioButtonMenuItem        jMenuItemModeExpert;

    @I18nToolTipText private final JTabbedPane contentJTabbedPane;

    private final JMenu     jMenuHelp;
    private final JMenuItem jMenuItem_About;

    /**
     * Create the frame.
     * @param dfToolKit
     * @throws TooManyListenersException
     * @throws HeadlessException
     */
    public DuplicateFilesFrameWB( final PreferencesControler preferences )
        throws HeadlessException, TooManyListenersException
    {
        this.dfToolKit = AppToolKitService.getInstance().createAppToolKit( preferences, (DuplicateFilesFrame)this );

        addWindowListener(new DuplicateFilesFrameWindowAdapter());

        setSize(650, 500); // Just for design // $codepro.audit.disable numericLiterals

        final JMenuBar jMenuBarMain = new JMenuBar();
        setJMenuBar(jMenuBarMain);

        this.jMenuFile = new JMenu("File");
        jMenuBarMain.add(this.jMenuFile);

        this.jMenuItemExit = new JMenuItem("Exit");
        this.jMenuItemExit.setActionCommand( ACTIONCMD_EXIT );
        this.jMenuItemExit.addActionListener( getActionListener() );
        this.jMenuFile.add( this.jMenuItemExit );

        this.jMenuConfig = new JMenu("Configuration");
        jMenuBarMain.add(this.jMenuConfig);

        this.jMenuConfigMode = new JMenu("Mode");
        this.jMenuConfig.add(this.jMenuConfigMode);

        this.jMenuItemModeBeginner = new JRadioButtonMenuItem( "Beginner" );
        this.buttonGroupConfigMode.add( this.jMenuItemModeBeginner );
        this.jMenuItemModeBeginner.setActionCommand( ACTIONCMD_SET_MODE );
        this.jMenuItemModeBeginner.putClientProperty( ConfigMode.class, ConfigMode.BEGINNER );
        this.jMenuItemModeBeginner.addActionListener( getActionListener() );
        this.jMenuConfigMode.add( this.jMenuItemModeBeginner );

        this.jMenuItemModeAdvance = new JRadioButtonMenuItem( "Advance" );
        this.buttonGroupConfigMode.add( this.jMenuItemModeAdvance );
        this.jMenuItemModeAdvance.setActionCommand( ACTIONCMD_SET_MODE );
        this.jMenuItemModeAdvance.putClientProperty( ConfigMode.class, ConfigMode.ADVANCED );
        this.jMenuItemModeAdvance.addActionListener( getActionListener() );
        this.jMenuConfigMode.add( this.jMenuItemModeAdvance );

        this.jMenuItemModeExpert = new JRadioButtonMenuItem( "Expert" );
        this.buttonGroupConfigMode.add( this.jMenuItemModeExpert );
        this.jMenuItemModeExpert.setActionCommand( ACTIONCMD_SET_MODE );
        this.jMenuItemModeExpert.putClientProperty( ConfigMode.class, ConfigMode.EXPERT );
        this.jMenuItemModeExpert.addActionListener( getActionListener() );
        this.jMenuConfigMode.add( this.jMenuItemModeExpert );

        this.jMenuConfig.addSeparator();

        this.jMenuItem_Preferences = new JMenuItem("Preferences");
        this.jMenuItem_Preferences.setActionCommand( ACTIONCMD_PREFS );
        this.jMenuItem_Preferences.addActionListener( getActionListener() );
        this.jMenuConfig.add(this.jMenuItem_Preferences);

        this.jMenuHelp = new JMenu("Help");
        jMenuBarMain.add(this.jMenuHelp);

        this.jMenuItem_About = new JMenuItem("About");
        this.jMenuItem_About.setActionCommand( ACTIONCMD_ABOUT );
        this.jMenuItem_About.addActionListener( getActionListener() );
        this.jMenuHelp.add(this.jMenuItem_About);

        this.jMenuLookAndFeel = new JMenu("Look and Feel");
        jMenuBarMain.add( Box.createHorizontalGlue() );
        jMenuBarMain.add(this.jMenuLookAndFeel);

        {
        this.contentJTabbedPane = new JTabbedPane();
        setContentPane( this.contentJTabbedPane );

        // Workaround for WindowBuilder
        if( this.dfToolKit == null ) {
            this.jPanel_DuplicateFiles = new DuplicateFilesMainPanel();
            }
        // $hide>>$
        this.jPanel_DuplicateFiles = new DuplicateFilesMainPanel( this );
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

    protected void setEnabledAt( final int index, final boolean enabled )
    {
        this.contentJTabbedPane.setEnabledAt( index, enabled );
    }

    protected ButtonGroup getButtonGroupConfigMode()
    {
        return this.buttonGroupConfigMode;
    }

    protected ButtonGroup getButtonGroupLanguage()
    {
        return this.buttonGroupLanguage;
    }

    protected JMenu getJMenuLookAndFeel()
    {
        return this.jMenuLookAndFeel;
    }

    public DuplicateFilesMainPanel getDuplicateFilesMainPanel()
    {
        return this.jPanel_DuplicateFiles;
    }

    RemoveEmptyDirectoriesPanel getRemoveEmptyDirectoriesPanel()
    {
        return this.jPanel_RemoveEmptyDirectories;
    }

    RemoveEmptyFilesJPanel getDeleteEmptyFilesPanel()
    {
        return this.jPanel_DeleteEmptyFiles;
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
