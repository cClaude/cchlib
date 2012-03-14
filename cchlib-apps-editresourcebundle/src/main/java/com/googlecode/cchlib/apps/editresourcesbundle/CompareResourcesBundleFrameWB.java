package com.googlecode.cchlib.apps.editresourcesbundle;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;
import java.awt.Toolkit;
import java.util.Locale;

/**
 *
 */
public abstract class CompareResourcesBundleFrameWB extends JFrame
{
    private static final long serialVersionUID = 1L;
    public static final String ACTIONCMD_SAVE_PREFS = "ACTIONCMD_SAVE_PREFS";
    public static final String ACTIONCMD_OPEN = "ACTIONCMD_OPEN";
    public static final String ACTIONCMD_QUIT = "ACTIONCMD_QUIT";
    public static final String ACTIONCMD_SAVE_ALL = "ACTIONCMD_SAVE_ALL";
    public static final String ACTIONCMD_SAVE_RIGHT = "ACTIONCMD_SAVE_RIGHT";
    public static final String ACTIONCMD_SAVE_LEFT = "ACTIONCMD_SAVE_LEFT";
    public static final String ACTIONCMD_DEFAULT_LOCAL = "ACTIONCMD_DEFAULT_LOCAL";
    public static final String ACTIONCMD_ENGLISH = "ACTIONCMD_ENGLISH";
    public static final String ACTIONCMD_FRENCH = "ACTIONCMD_FRENCH";

    private JMenuItem           jMenuItemQuit;
    private JMenuItem           jMenuItemSaveAll;
    private JMenuItem           jMenuItemSaveRightFile;
    protected JMenu             jMenuLookAndFeel;
    protected JMenuBar          jMenuBarFrame;
    protected JMenuItem         jMenuItemSaveLeftFile;
    protected JScrollPane       jScrollPaneProperties;
    protected JTable            jTableProperties;
    private JMenu               jMenuItemLanguage;

    private final ButtonGroup buttonGroupLanguage = new ButtonGroup();
    private JRadioButtonMenuItem jRadioButtonMenuItemDefaultLocale;
    //private JRadioButtonMenuItem jRadioButtonMenuItemEnglish;
    //private JRadioButtonMenuItem jRadioButtonMenuItemFrench;

    private JMenu jMenuFile;
    private JMenu jMenuSave;
    private JMenu jMenuOptions;
    private JMenuItem jMenuItemSaveCurrentPrefs;
    private JMenuItem jMenuItemOpen;

    public CompareResourcesBundleFrameWB()
    {
        setIconImage(Toolkit.getDefaultToolkit().getImage(CompareResourcesBundleFrameWB.class.getResource("icon.png")));
        jScrollPaneProperties = new JScrollPane();
        jTableProperties = new JTable();
        jScrollPaneProperties.setViewportView( jTableProperties );
        getContentPane().add(jScrollPaneProperties, BorderLayout.CENTER);

        jMenuFile = new JMenu( "File" );

        jMenuSave = new JMenu( "Save" );

        jMenuItemOpen = new JMenuItem( "Open..." );
        jMenuItemOpen.setActionCommand( ACTIONCMD_OPEN );
        jMenuItemOpen.addActionListener( getActionListener() );
        jMenuFile.add( jMenuItemOpen );

        jMenuItemSaveLeftFile = new JMenuItem( "Save left" );
        jMenuItemSaveLeftFile.setActionCommand( ACTIONCMD_SAVE_LEFT );
        jMenuItemSaveLeftFile.addActionListener( getActionListener() );
        jMenuSave.add( jMenuItemSaveLeftFile );

        jMenuItemSaveRightFile = new JMenuItem( "Save right" );
        jMenuItemSaveRightFile.setActionCommand( ACTIONCMD_SAVE_RIGHT );
        jMenuItemSaveRightFile.addActionListener( getActionListener() );

        jMenuSave.add( jMenuItemSaveRightFile );

        jMenuFile.add( jMenuSave );

        jMenuItemSaveAll = new JMenuItem( "Save all" );
        jMenuItemSaveAll.setActionCommand( ACTIONCMD_SAVE_ALL );
        jMenuItemSaveAll.addActionListener( getActionListener() );
        jMenuFile.add( jMenuItemSaveAll );

        jMenuItemQuit = new JMenuItem("Quit");
        jMenuItemQuit.setActionCommand( ACTIONCMD_QUIT );
        jMenuItemQuit.addActionListener( getActionListener() );
        jMenuFile.add( jMenuItemQuit );

        jMenuBarFrame = new JMenuBar();
        jMenuBarFrame.add( jMenuFile );

        jMenuOptions = new JMenu();
        jMenuOptions.setText("Options");

        jMenuItemLanguage = new JMenu("Language");

        jRadioButtonMenuItemDefaultLocale = new JRadioButtonMenuItem("Default Locale");
        buttonGroupLanguage.add(jRadioButtonMenuItemDefaultLocale);
        jRadioButtonMenuItemDefaultLocale.setSelected(true);
        jRadioButtonMenuItemDefaultLocale.setActionCommand( ACTIONCMD_DEFAULT_LOCAL );
        jRadioButtonMenuItemDefaultLocale.addActionListener( getActionListener() );
        jRadioButtonMenuItemDefaultLocale.putClientProperty( Locale.class, null );
        jMenuItemLanguage.add( jRadioButtonMenuItemDefaultLocale );

        JRadioButtonMenuItem jRadioButtonMenuItemEnglish = new JRadioButtonMenuItem( Locale.ENGLISH.getDisplayLanguage() );
        buttonGroupLanguage.add( jRadioButtonMenuItemEnglish );
        jRadioButtonMenuItemEnglish.setActionCommand( ACTIONCMD_ENGLISH );
        jRadioButtonMenuItemEnglish.addActionListener( getActionListener() );
        jRadioButtonMenuItemEnglish.putClientProperty( Locale.class, Locale.ENGLISH );
        jMenuItemLanguage.add( jRadioButtonMenuItemEnglish );

        JRadioButtonMenuItem jRadioButtonMenuItemFrench = new JRadioButtonMenuItem( Locale.FRENCH.getDisplayLanguage() );
        buttonGroupLanguage.add( jRadioButtonMenuItemFrench );
        jRadioButtonMenuItemFrench.setActionCommand( ACTIONCMD_FRENCH );
        jRadioButtonMenuItemFrench.addActionListener( getActionListener() );
        jRadioButtonMenuItemFrench.putClientProperty( Locale.class, Locale.FRENCH );
        jMenuItemLanguage.add( jRadioButtonMenuItemFrench );

        jMenuOptions.add( jMenuItemLanguage );
        jMenuOptions.addSeparator();

        jMenuItemSaveCurrentPrefs = new JMenuItem( "Save preferences" );
        jMenuItemSaveCurrentPrefs.setActionCommand( ACTIONCMD_SAVE_PREFS );
        jMenuItemSaveCurrentPrefs.addActionListener( getActionListener() );
        jMenuOptions.add( jMenuItemSaveCurrentPrefs );

        jMenuBarFrame.add( jMenuOptions );
        setJMenuBar( jMenuBarFrame );

        jMenuBarFrame.add(Box.createHorizontalGlue());
        jMenuLookAndFeel = new JMenu("Look And Feel");
        jMenuBarFrame.add( jMenuLookAndFeel );
    }

    protected ButtonGroup getButtonGroupLanguage()
    {
        return this.buttonGroupLanguage;
    }

    protected abstract ActionListener getActionListener();
}
