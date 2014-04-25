package com.googlecode.cchlib.apps.editresourcesbundle.compare;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.googlecode.cchlib.apps.editresourcesbundle.Resources;
import com.googlecode.cchlib.swing.menu.LookAndFeelMenu;

/**
 *
 */
public abstract class CompareResourcesBundleFrameWB extends JFrame // $codepro.audit.disable largeNumberOfFields
{
    private static final long serialVersionUID = 3L;

    private final int numberOfFiles;

    private JMenuBar     jMenuBarFrame;
    private JMenu        jMenuFile;
    private JMenu        jMenuOptions;
    private JMenuItem    jMenuItemOpen;
    private JMenuItem    jMenuItemQuit;
    private JMenuItem    jMenuItemSaveAll;
    private JMenu        jMenuSave;
    private JMenuItem[]  jMenuItemSaveRightFile;
    private JMenu        jMenuLookAndFeel;
    private JMenuItem    jMenuItemSaveLeftFile;
    private final JScrollPane  jScrollPaneProperties;
    private JTable       jTableProperties;
    private JMenuItem    preferencesJMenuItem;

    public CompareResourcesBundleFrameWB( final int numberOfFiles )
    {
        this.numberOfFiles = numberOfFiles;

        setIconImage(Toolkit.getDefaultToolkit().getImage(Resources.class.getResource("icon.png")));
        jScrollPaneProperties = new JScrollPane();
        jTableProperties = new JTable();
        jScrollPaneProperties.setViewportView( getjTableProperties() );
        getContentPane().add(getjScrollPaneProperties(), BorderLayout.CENTER);

        updateJMenuBar();
    }

    protected void updateJMenuBar()
    {
        if( this.jMenuItemSaveRightFile != null ) {
            if( this.jMenuItemSaveRightFile.length == this.numberOfFiles ) {
                return;
                }
            }

        jMenuFile = new JMenu( "File" );

        jMenuItemOpen = new JMenuItem( "Open..." );
        jMenuItemOpen.setActionCommand( CompareResourcesBundleFrameAction.ACTIONCMD_OPEN.name() );
        jMenuItemOpen.addActionListener( getActionListener() );
        jMenuFile.add( jMenuItemOpen );

        jMenuSave = new JMenu( "Save" );

        // Left file
        jMenuItemSaveLeftFile = new JMenuItem( "Save left" );
        jMenuItemSaveLeftFile.setActionCommand( CompareResourcesBundleFrameAction.ACTIONCMD_SAVE_LEFT.name() );
        jMenuItemSaveLeftFile.addActionListener( getActionListener() );

        jMenuSave.add( jMenuItemSaveLeftFile );

        // Rigth files
        jMenuItemSaveRightFile = new JMenuItem[ this.numberOfFiles - 1 ];

        for( int i = 0; i<jMenuItemSaveRightFile.length; i++ ) {
            jMenuItemSaveRightFile[ i ] = new JMenuItem( "Save right" ); // $codepro.audit.disable avoidInstantiationInLoops
            jMenuItemSaveRightFile[ i ].setActionCommand( CompareResourcesBundleFrameAction.ACTIONCMD_SAVE_RIGHT_PREFIX.getActionCommand( i ) );
            jMenuItemSaveRightFile[ i ].addActionListener( getActionListener() );

            jMenuSave.add( jMenuItemSaveRightFile[ i ] );
            }
        jMenuFile.add( jMenuSave );

        jMenuItemSaveAll = new JMenuItem( "Save all" );
        jMenuItemSaveAll.setActionCommand( CompareResourcesBundleFrameAction.ACTIONCMD_SAVE_ALL.name() );
        jMenuItemSaveAll.addActionListener( getActionListener() );
        jMenuFile.add( jMenuItemSaveAll );

        jMenuItemQuit = new JMenuItem("Quit");
        jMenuItemQuit.setActionCommand( CompareResourcesBundleFrameAction.ACTIONCMD_QUIT.name() );
        jMenuItemQuit.addActionListener( getActionListener() );
        jMenuFile.add( jMenuItemQuit );

        jMenuBarFrame = new JMenuBar();
        jMenuBarFrame.add( jMenuFile );

        jMenuOptions = new JMenu();
        jMenuOptions.setText("Options");

        preferencesJMenuItem = new JMenuItem("Prerences");
        preferencesJMenuItem.setActionCommand( CompareResourcesBundleFrameAction.ACTIONCMD_PREFS.name() );
        preferencesJMenuItem.addActionListener( getActionListener() );
        jMenuOptions.add(preferencesJMenuItem);

        jMenuBarFrame.add( jMenuOptions );
        setJMenuBar( jMenuBarFrame );

        jMenuBarFrame.add(Box.createHorizontalGlue());
        jMenuLookAndFeel = new JMenu("Look And Feel");
        jMenuBarFrame.add( jMenuLookAndFeel );
    }

    protected abstract ActionListener getActionListener();

    protected void buildLookAndFeelMenu()
    {
        new LookAndFeelMenu( this ).buildMenu( jMenuLookAndFeel );
    }

    protected JMenuItem getjMenuItemSaveLeftFile()
    {
        return jMenuItemSaveLeftFile;
    }

    protected JTable getjTableProperties()
    {
        return jTableProperties;
    }

    protected void setjTableProperties( final JTable jTableProperties )
    {
        this.jTableProperties = jTableProperties;
    }

    protected JScrollPane getjScrollPaneProperties()
    {
        return jScrollPaneProperties;
    }

}
