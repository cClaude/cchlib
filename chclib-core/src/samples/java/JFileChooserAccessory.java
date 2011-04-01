/**
 *
 */
package samples;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import cx.ath.choisnet.swing.filechooser.FileNameExtensionFilter;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializer;
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
    private LastSelectedFilesAccessoryDefaultConfigurator lSFAConf
      = new LastSelectedFilesAccessoryDefaultConfigurator();
    /** @serial */
    private JFileChooserInitializer jFileChooserInitializer;
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
    /** @serial */
    private JPanel jPanel_Buttons;
    /** @serial */
    private JTextField jTextField_LastSelected;
    /** @serial */
    private JButton jButton_JFileChooserInitializer;

    public JFileChooserAccessory()
    {
        initComponents();
    }

    private void initComponents() {
        add(getJPanel_Buttons(), BorderLayout.CENTER);
        add(getJTextField_LastSelected(), BorderLayout.SOUTH);
        setJMenuBar(getJMenuBarFrame());
        setSize(320, 119);
    }

    protected void initFixComponents()
    {
        setIconImage(
            Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("sample.png")
                )
            );

        LookAndFeelHelpers.buildLookAndFeelMenu( this, jMenuLookAndFeel );

        jFileChooserInitializer = getJFileChooserInitializer();
    }

    private JButton getJButton_JFileChooserInitializer() {
        if (jButton_JFileChooserInitializer == null) {
            jButton_JFileChooserInitializer = new JButton();
            jButton_JFileChooserInitializer.setText("JFileChooserInitializer");
            jButton_JFileChooserInitializer.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButton_JFileChooserInitializerMouseMousePressed(event);
                }
            });
        }
        return jButton_JFileChooserInitializer;
    }

    private JTextField getJTextField_LastSelected() {
        if (jTextField_LastSelected == null) {
            jTextField_LastSelected = new JTextField();
            jTextField_LastSelected.setText("...");
            jTextField_LastSelected.setEditable( false );
        }
        return jTextField_LastSelected;
    }

    private JPanel getJPanel_Buttons() {
        if (jPanel_Buttons == null) {
            jPanel_Buttons = new JPanel();
            jPanel_Buttons.setLayout(new GridLayout(3, 2));
            jPanel_Buttons.add(getJButton_StdJFileChooser());
            jPanel_Buttons.add(getJButton_Bookmarks());
            jPanel_Buttons.add(getJButton_Picture());
            jPanel_Buttons.add(getJButton_LastSelectedFiles());
            jPanel_Buttons.add(getJButton_Tabbed());
            jPanel_Buttons.add(getJButton_JFileChooserInitializer());
        }
        return jPanel_Buttons;
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
                frame.setTitle( "Sample for JFileChooser Accessory" );
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
        showOpenDialog( jfc );
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
        showOpenDialog( jfc );
    }

    private void jButton_PictureMouseMousePressed(MouseEvent event)
    {
        JFileChooser jfc = new JFileChooser();
        jfc.setAccessory(
                new ImagePreviewAccessory(jfc)
                );
        showOpenDialog( jfc );
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
        showOpenDialog( jfc );
    }

    private void jButton_LastSelectedFilesMouseMousePressed(MouseEvent event)
    {
        JFileChooser jfc = new JFileChooser();
        jfc.setAccessory(
                new LastSelectedFilesAccessory( jfc, lSFAConf )
                );
        showOpenDialog( jfc );
    }

    private void jButton_JFileChooserInitializerMouseMousePressed(MouseEvent event)
    {
        showOpenDialog(
                jFileChooserInitializer.getJFileChooser()
                );
    }

    private void showOpenDialog(JFileChooser jfc)
    {
        int returnVal = jfc.showOpenDialog(this);

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();

            System.out.printf( "You chose to open this file: %s\n", f);
            setLastSelected( f.getPath() );
            }
        else {
            setLastSelected( "<<No selection>>" );
        }
    }

    private void setLastSelected(String txt)
    {
        jTextField_LastSelected.setText(txt);
    }

    private JFileChooserInitializer getJFileChooserInitializer()
    {
        return new JFileChooserInitializer(
            new JFileChooserInitializer.DefaultConfigurator()
            {
                private static final long serialVersionUID = 1L;
                public void perfomeConfig(JFileChooser jfc)
                {
                    super.perfomeConfig( jfc );

                    jfc.setAccessory(
                        new TabbedAccessory()
                            .addTabbedAccessory(
                                new BookmarksAccessory(
                                    jfc,
                                    new BookmarksAccessoryDefaultConfigurator()
                                    )
                                )
                             .addTabbedAccessory(
                                 new LastSelectedFilesAccessory(
                                     jfc,
                                     lSFAConf
                                     )
                                 )
                        );
                }
            }
            .setFileFilter(
                new FileNameExtensionFilter(
                    "Properties",
                    "properties"
                    )
                )
            .setFileFilter(
                new FileNameExtensionFilter(
                    "Pictures",
                    "gif",
                    "jpeg",
                    "jpg",
                    "png"
                    )
                )
            );
        }
}
