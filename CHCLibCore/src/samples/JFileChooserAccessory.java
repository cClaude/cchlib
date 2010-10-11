/**
 *
 */
package samples;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessory;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessoryDefaultConfigurator;
import cx.ath.choisnet.swing.filechooser.accessory.ImagePreviewAccessory;
import cx.ath.choisnet.swing.filechooser.accessory.LastSelectedFilesAccessory;
import cx.ath.choisnet.swing.filechooser.accessory.LastSelectedFilesAccessoryDefaultConfigurator;
import cx.ath.choisnet.swing.filechooser.accessory.TabbedAccessory;
import cx.ath.choisnet.swing.helpers.LookAndFeelHelpers;

/**
 * Show how to use {@link TabbedAccessory}, {@link ImagePreviewAccessory},
 * {@link LastSelectedFilesAccessory}, {@link LookAndFeelHelpers}
 *
 * @author Claude CHOISNET
 */
//VS 4E -- DO NOT REMOVE THIS LINE!
public class JFileChooserAccessory extends JFrame {

    private static final long   serialVersionUID        = 1L;
    /** @serial */
    private JButton jButton_StdJFileChooser;
    /** @serial */
    private JButton jButton_Bookmarks;
    /** @serial */
    private JButton jButton_Picture;
    /** @serial */
    private JButton jButton_Tabbed;
    /** @serial */
    private JMenu jMenuLookAndFeel;
    /** @serial */
    private JMenuBar jMenuBarFrame;
    /** @serial */
    private JButton jButton_LastSelectedFiles;

    public JFileChooserAccessory()
    {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(3, 2));
        add(getJButton_StdJFileChooser());
        add(getJButton_Bookmarks());
        add(getJButton_Picture());
        add(getJButton_LastSelectedFiles());
        add(getJButton_Tabbed());
        setJMenuBar(getJMenuBarFrame());
        setSize(320, 119);
    }

    private JButton getJButton_LastSelectedFiles() {
        if (jButton_LastSelectedFiles == null) {
            jButton_LastSelectedFiles = new JButton();
            jButton_LastSelectedFiles.setText("Last selected files");
            jButton_LastSelectedFiles.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButton_LastSelectedFilesMouseMousePressed(event);
                }
            });
        }
        return jButton_LastSelectedFiles;
    }

    protected void initFixComponents()
    {
        LookAndFeelHelpers.buildLookAndFeelMenu( this, jMenuLookAndFeel );
    }

    private JButton getJButton_Tabbed() {
        if (jButton_Tabbed == null) {
            jButton_Tabbed = new JButton();
            jButton_Tabbed.setText("Multi Accessories");
            jButton_Tabbed.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButton_TabbedMouseMousePressed(event);
                }
            });
        }
        return jButton_Tabbed;
    }

    private JButton getJButton_Picture() {
        if (jButton_Picture == null) {
            jButton_Picture = new JButton();
            jButton_Picture.setText("Preview pictures");
            jButton_Picture.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButton_PictureMouseMousePressed(event);
                }
            });
        }
        return jButton_Picture;
    }

    private JButton getJButton_Bookmarks() {
        if (jButton_Bookmarks == null) {
            jButton_Bookmarks = new JButton();
            jButton_Bookmarks.setText("Bookmarks");
            jButton_Bookmarks.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButton_BookmarksMouseMousePressed(event);
                }
            });
        }
        return jButton_Bookmarks;
    }

    private JButton getJButton_StdJFileChooser() {
        if (jButton_StdJFileChooser == null) {
            jButton_StdJFileChooser = new JButton();
            jButton_StdJFileChooser.setText("Classic JFileChooser");
            jButton_StdJFileChooser.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButton_StdJFileChooserMouseMousePressed(event);
                }
            });
        }
        return jButton_StdJFileChooser;
    }

    private JMenuBar getJMenuBarFrame() {
        if (jMenuBarFrame == null) {
            jMenuBarFrame = new JMenuBar();
            jMenuBarFrame.add(getJMenuLookAndFeel());
        }
        return jMenuBarFrame;
    }

    private JMenu getJMenuLookAndFeel() {
        if (jMenuLookAndFeel == null) {
            jMenuLookAndFeel = new JMenu();
            jMenuLookAndFeel.setText("Look And Feel");
        }
        return jMenuLookAndFeel;
    }

    public static void main( String[] args )
    {
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                JFileChooserAccessory frame = new JFileChooserAccessory();
                frame.initFixComponents();
                frame.setDefaultCloseOperation( JFileChooserAccessory.EXIT_ON_CLOSE );
                frame.setTitle( "JFileChooserAccessory" );
                frame.getContentPane().setPreferredSize( frame.getSize() );
                frame.pack();
                frame.setLocationRelativeTo( null );
                frame.setVisible( true );
            }
        } );
    }

    private void jButton_StdJFileChooserMouseMousePressed(MouseEvent event)
    {
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog( jfc );
    }

    private void jButton_BookmarksMouseMousePressed(MouseEvent event)
    {
        JFileChooser jfc = new JFileChooser();
        jfc.setAccessory(
                new BookmarksAccessory(
                                jfc,
                                new BookmarksAccessoryDefaultConfigurator()
                                )
                );
        jfc.showOpenDialog( jfc );
    }

    private void jButton_PictureMouseMousePressed(MouseEvent event)
    {
        JFileChooser jfc = new JFileChooser();
        jfc.setAccessory(
                new ImagePreviewAccessory(jfc)
                );
        jfc.showOpenDialog( jfc );
    }

    private void jButton_TabbedMouseMousePressed(MouseEvent event)
    {
        JFileChooser jfc = new JFileChooser();
        jfc.setAccessory(
                new TabbedAccessory()
                    .addTabbedAccessory(
                        new BookmarksAccessory(
                                jfc,
                                new BookmarksAccessoryDefaultConfigurator()
                                )
                        )
                     .addTabbedAccessory(
                         new ImagePreviewAccessory(jfc)
                         )
                     .addTabbedAccessory(
                         new LastSelectedFilesAccessory( jfc, lSFAConf )
                         )
                );
        jfc.showOpenDialog( jfc );
    }

    /** @serial */
    private LastSelectedFilesAccessoryDefaultConfigurator lSFAConf
      = new LastSelectedFilesAccessoryDefaultConfigurator();

    private void jButton_LastSelectedFilesMouseMousePressed(MouseEvent event)
    {
        JFileChooser jfc = new JFileChooser();
        jfc.setAccessory(
                new LastSelectedFilesAccessory( jfc, lSFAConf )
                );
        jfc.showOpenDialog( null );
    }
}
