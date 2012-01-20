package com.googlecode.cchlib.apps.editresourcesbundle;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Class for VS4E only !
 *
 * @author Claude CHOISNET
 *
 */
// VS 4E -- DO NOT REMOVE THIS LINE!
@Deprecated
public class CompareResourcesBundleFrameVS4E extends JFrame
{
    private static final long serialVersionUID = 1L;

    private JMenu               jMenuFile;
    private JMenu               jMenuOptions;
    private JMenu               jMenuSave;
    private JMenuItem           jMenuItemDefaultLocale;
    private JMenuItem           jMenuItemQuit;
    private JMenuItem           jMenuItemSaveAll;
    private JMenuItem           jMenuItemSaveRightFile;
    private JMenuItem           jMenuItem_Open;
    //protected JCheckBoxMenuItem jCheckBoxMenuItem_ShowLineNumbers;
    protected JMenu             jMenuLookAndFeel;
    protected JMenuBar          jMenuBarFrame;
    protected JMenuItem         jMenuItemSaveLeftFile;
    protected JScrollPane       jScrollPaneProperties;
    protected JTable            jTableProperties;

    public CompareResourcesBundleFrameVS4E()
    {
        initComponents();
    }

    private void initComponents() {
        add(getJScrollPaneProperties(), BorderLayout.CENTER);
        setJMenuBar(getJMenuBarFrame());
        setSize(640, 440);
    }

//    private JCheckBoxMenuItem getJCheckBoxMenuItem_ShowLineNumbers() {
//        if (jCheckBoxMenuItem_ShowLineNumbers == null) {
//            jCheckBoxMenuItem_ShowLineNumbers = new JCheckBoxMenuItem();
//            jCheckBoxMenuItem_ShowLineNumbers.setText("Show line numbers (if possible)");
//        }
//        return jCheckBoxMenuItem_ShowLineNumbers;
//    }

    private JMenuItem getJMenuItem_Open() {
        if (jMenuItem_Open == null) {
            jMenuItem_Open = new JMenuItem();
            jMenuItem_Open.setText("Open...");
            jMenuItem_Open.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jMenuItem_OpenMouseMousePressed(event);
                }
            });
        }
        return jMenuItem_Open;
    }

    private JMenu getJMenuOptions() {
        if (jMenuOptions == null) {
            jMenuOptions = new JMenu();
            jMenuOptions.setText("Options");
            jMenuOptions.setOpaque(false);
            //jMenuOptions.add(getJCheckBoxMenuItem_ShowLineNumbers());
            jMenuOptions.add(getJMenuItemDefaultLocale());
        }
        return jMenuOptions;
    }

    final
    private JMenuItem getJMenuItemDefaultLocale() {
        if (jMenuItemDefaultLocale == null) {
            jMenuItemDefaultLocale = new JMenuItem();
            jMenuItemDefaultLocale.setText("Default Locale");
            jMenuItemDefaultLocale.setEnabled(false); //TODO choose local
            jMenuItemDefaultLocale.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jMenuItemDefaultLocaleMouseMousePressed(event);
                }
            });
        }
        return jMenuItemDefaultLocale;
    }

    final
    private JScrollPane getJScrollPaneProperties()
    {
        if( jScrollPaneProperties == null ) {
            jScrollPaneProperties = new JScrollPane();
            jScrollPaneProperties.setViewportView( getJTableProperties() );
        }
        return jScrollPaneProperties;
    }

    final
    private JTable getJTableProperties()
    {
        if( jTableProperties == null ) {
            jTableProperties = new JTable();
            // jTableProperties.setModel(new
            // javax.swing.table.DefaultTableModel(new Object[][] { { "0x0",
            // "0x1", }, { "1x0", "1x1", }, }, new String[] { "Title 0",
            // "Title 1", }) {
            // private static final long serialVersionUID = 1L;
            // Class<?>[] types = new Class<?>[] { Object.class, Object.class,
            // };
            //
            // public Class<?> getColumnClass(int columnIndex) {
            // return types[columnIndex];
            // }
            // });
        }
        return jTableProperties;
    }

    private JMenuBar getJMenuBarFrame() {
        if (jMenuBarFrame == null) {
            jMenuBarFrame = new JMenuBar();
            jMenuBarFrame.add(getJMenuFile());
            jMenuBarFrame.add(getJMenuOptions());
        }
        return jMenuBarFrame;
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

    final
    private JMenu getJMenuFile() {
        if (jMenuFile == null) {
            jMenuFile = new JMenu();
            jMenuFile.setText("File");
            jMenuFile.setOpaque(false);
            jMenuFile.add(getJMenuItem_Open());
            jMenuFile.add(getJMenuSave());
            jMenuFile.add(getJMenuItemSaveAll());
            jMenuFile.add(getJMenuItemQuit());
        }
        return jMenuFile;
    }

    final
    private JMenuItem getJMenuItemQuit()
    {
        if( jMenuItemQuit == null ) {
            jMenuItemQuit = new JMenuItem();
            jMenuItemQuit.setText( "Quit" );
            jMenuItemQuit.addMouseListener( new MouseAdapter() {

                public void mousePressed( MouseEvent event )
                {
                    jMenuItem_Quit_MouseMousePressed( event );
                }
            } );
        }
        return jMenuItemQuit;
    }

    final
    private JMenuItem getJMenuItemSaveAll()
    {
        if( jMenuItemSaveAll == null ) {
            jMenuItemSaveAll = new JMenuItem();
            jMenuItemSaveAll.setText( "Save all" );
            jMenuItemSaveAll.addMouseListener( new MouseAdapter() {

                public void mousePressed( MouseEvent event )
                {
                    jMenuItem_SaveLeftFile_MouseMousePressed( event );
                    jMenuItem_SaveRightFile_MouseMousePressed( event );
                }
            } );
        }
        return jMenuItemSaveAll;
    }

    final
    private JMenuItem getJMenuItemSaveRightFile()
    {
        if( jMenuItemSaveRightFile == null ) {
            jMenuItemSaveRightFile = new JMenuItem();
            jMenuItemSaveRightFile.setText( "Save right" );
            jMenuItemSaveRightFile.addMouseListener( new MouseAdapter() {

                public void mousePressed( MouseEvent event )
                {
                    jMenuItem_SaveRightFile_MouseMousePressed( event );
                }
            } );
        }
        return jMenuItemSaveRightFile;
    }


    final
    private JMenu getJMenuSave() {
        if (jMenuSave == null) {
            jMenuSave = new JMenu();
            jMenuSave.setText("Save");
            jMenuSave.setOpaque(false);
            jMenuSave.add(getJMenuItemSaveLeftFile());
            jMenuSave.add(getJMenuItemSaveRightFile());
        }
        return jMenuSave;
    }

    final
    private JMenuItem getJMenuItemSaveLeftFile()
    {
        if( jMenuItemSaveLeftFile == null ) {
            jMenuItemSaveLeftFile = new JMenuItem();
            jMenuItemSaveLeftFile.setText( "Save left" );
            jMenuItemSaveLeftFile.addMouseListener( new MouseAdapter() {

                public void mousePressed( MouseEvent event )
                {
                    jMenuItem_SaveLeftFile_MouseMousePressed( event );
                }
            } );
        }
        return jMenuItemSaveLeftFile;
    }

//    public static void main( String[] args )
//    {
//        SwingUtilities.invokeLater( new Runnable() {
//            @Override
//            public void run()
//            {
//                CompareRessourceBundleFrameVS4E frame = new CompareRessourceBundleFrameVS4E();
//                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
//                //frame.setTitle( "CompareRessourceBundleFrame VS4E" );
//                frame.getContentPane().setPreferredSize( frame.getSize() );
//                frame.pack();
//                frame.setLocationRelativeTo( null );
//                frame.setVisible( true );
//            }
//        } );
//    }

    protected void jMenuItem_SaveLeftFile_MouseMousePressed( MouseEvent event )
    {
        throw new UnsupportedOperationException();
    }

    protected void jMenuItem_SaveRightFile_MouseMousePressed( MouseEvent event )
    {
        throw new UnsupportedOperationException();
    }

    protected void jMenuItem_Quit_MouseMousePressed( MouseEvent event )
    {
        dispose();
    }

    protected void jMenuItemDefaultLocaleMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

    protected void jMenuItem_OpenMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }
}
