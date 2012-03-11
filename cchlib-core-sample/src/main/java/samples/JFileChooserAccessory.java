package samples;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import com.googlecode.cchlib.swing.filechooser.DefaultConfigurator;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializerCustomize;
import com.googlecode.cchlib.swing.filechooser.FileNameExtensionFilter;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.WaitingJFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.accessory.BookmarksAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.DefaultBookmarksAccessoryConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.FindAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.ImagePreviewAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.LastSelectedFilesAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.LastSelectedFilesAccessoryDefaultConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.TabbedAccessory;
import com.googlecode.cchlib.swing.menu.LookAndFeelMenu;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Show how to use {@link TabbedAccessory}, {@link ImagePreviewAccessory},
 * {@link LastSelectedFilesAccessory}, {@link FindAccessory},
 * {@link LookAndFeelMenu}
 */
public class JFileChooserAccessory extends JFrame
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private LastSelectedFilesAccessoryDefaultConfigurator lSFAConf
      = new LastSelectedFilesAccessoryDefaultConfigurator();
    /** @serial */
    private JFileChooserInitializer jFileChooserInitializer;
    /** @serial */
    private WaitingJFileChooserInitializer waitingJFileChooserInitializer;
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
    private JTextField jTextField_LastSelected;
    /** @serial */
    private JButton jButton_JFileChooserInitializer;
    /** @serial */
    private JButton jButton_WaitingJFileChooserInitializer;

    public JFileChooserAccessory()
    {
        initComponents();
    }

    private void initComponents() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
        GridBagConstraints gbc_jButton_StdJFileChooser = new GridBagConstraints();
        gbc_jButton_StdJFileChooser.fill = GridBagConstraints.BOTH;
        gbc_jButton_StdJFileChooser.insets = new Insets(0, 0, 5, 5);
        gbc_jButton_StdJFileChooser.gridx = 0;
        gbc_jButton_StdJFileChooser.gridy = 0;
        getContentPane().add(getJButton_StdJFileChooser(), gbc_jButton_StdJFileChooser);
        GridBagConstraints gbc_jButton_Bookmarks = new GridBagConstraints();
        gbc_jButton_Bookmarks.fill = GridBagConstraints.BOTH;
        gbc_jButton_Bookmarks.insets = new Insets(0, 0, 5, 0);
        gbc_jButton_Bookmarks.gridx = 1;
        gbc_jButton_Bookmarks.gridy = 0;
        getContentPane().add(getJButton_Bookmarks(), gbc_jButton_Bookmarks);
        GridBagConstraints gbc_jButton_JFileChooserInitializer = new GridBagConstraints();
        gbc_jButton_JFileChooserInitializer.fill = GridBagConstraints.BOTH;
        gbc_jButton_JFileChooserInitializer.insets = new Insets(0, 0, 5, 5);
        gbc_jButton_JFileChooserInitializer.gridx = 0;
        gbc_jButton_JFileChooserInitializer.gridy = 1;

        jButton_JFileChooserInitializer = new JButton("JFileChooserInitializer");
        jButton_JFileChooserInitializer.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                jButton_JFileChooserInitializerMouseMousePressed(event);
            }
        });
        getContentPane().add(jButton_JFileChooserInitializer, gbc_jButton_JFileChooserInitializer);
        GridBagConstraints gbc_jButton_Picture = new GridBagConstraints();
        gbc_jButton_Picture.fill = GridBagConstraints.BOTH;
        gbc_jButton_Picture.insets = new Insets(0, 0, 5, 0);
        gbc_jButton_Picture.gridx = 1;
        gbc_jButton_Picture.gridy = 1;
        getContentPane().add(getJButton_Picture(), gbc_jButton_Picture);
        GridBagConstraints gbc_jButton_WaitingJFileChooserInitializer = new GridBagConstraints();
        gbc_jButton_WaitingJFileChooserInitializer.fill = GridBagConstraints.BOTH;
        gbc_jButton_WaitingJFileChooserInitializer.insets = new Insets(0, 0, 5, 5);
        gbc_jButton_WaitingJFileChooserInitializer.gridx = 0;
        gbc_jButton_WaitingJFileChooserInitializer.gridy = 2;

        jButton_WaitingJFileChooserInitializer = new JButton("WaitingJFileChooserInitializer");
        jButton_WaitingJFileChooserInitializer.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                jButton_WaitingJFileChooserInitializerMouseMousePressed(event);
            }
        });
        getContentPane().add(jButton_WaitingJFileChooserInitializer, gbc_jButton_WaitingJFileChooserInitializer);
        GridBagConstraints gbc_jButton_LastSelectedFiles = new GridBagConstraints();
        gbc_jButton_LastSelectedFiles.fill = GridBagConstraints.BOTH;
        gbc_jButton_LastSelectedFiles.insets = new Insets(0, 0, 5, 0);
        gbc_jButton_LastSelectedFiles.gridx = 1;
        gbc_jButton_LastSelectedFiles.gridy = 2;
        getContentPane().add(getJButton_LastSelectedFiles(), gbc_jButton_LastSelectedFiles);
        GridBagConstraints gbc_jButton_Tabbed = new GridBagConstraints();
        gbc_jButton_Tabbed.fill = GridBagConstraints.BOTH;
        gbc_jButton_Tabbed.insets = new Insets(0, 0, 5, 0);
        gbc_jButton_Tabbed.gridx = 1;
        gbc_jButton_Tabbed.gridy = 3;
        getContentPane().add(getJButton_Tabbed(), gbc_jButton_Tabbed);
        GridBagConstraints gbc_jTextField_LastSelected = new GridBagConstraints();
        gbc_jTextField_LastSelected.gridwidth = 2;
        gbc_jTextField_LastSelected.fill = GridBagConstraints.BOTH;
        gbc_jTextField_LastSelected.gridx = 0;
        gbc_jTextField_LastSelected.gridy = 4;
        jTextField_LastSelected = new JTextField("...");
        jTextField_LastSelected.setEditable( false );
        getContentPane().add(jTextField_LastSelected, gbc_jTextField_LastSelected);
        setJMenuBar(getJMenuBarFrame());
        setSize(367, 196);
    }

    protected void initFixComponents()
    {
        Image iconImage = Toolkit.getDefaultToolkit().getImage(
                          getClass().getResource("sample.png")
                          );
        setIconImage( iconImage );

        //MenuHelper.buildLookAndFeelMenu( this, jMenuLookAndFeel );
        new LookAndFeelMenu( this ).buildMenu( jMenuLookAndFeel );

        jFileChooserInitializer = getJFileChooserInitializer();
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
            jButton_Bookmarks = new JButton("Bookmarks");
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
            jButton_StdJFileChooser = new JButton("Classic JFileChooser");
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
            jMenuLookAndFeel = new JMenu("Look And Feel");
        }
        return jMenuLookAndFeel;
    }

    public static void main( String[] args )
    {
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                try {
                    JFileChooserAccessory frame = new JFileChooserAccessory();
                    frame.initFixComponents();
                    frame.setDefaultCloseOperation( JFileChooserAccessory.EXIT_ON_CLOSE );
                    frame.setTitle( "Sample for JFileChooser Accessory" );
                    frame.getContentPane().setPreferredSize( frame.getSize() );
                    frame.pack();
                    frame.setLocationRelativeTo( null );
                    frame.setVisible( true );
                    }
                catch( Exception e ) {
                    e.printStackTrace( System.err );
                    }
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
                                new DefaultBookmarksAccessoryConfigurator()
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
                                new DefaultBookmarksAccessoryConfigurator()
                                )
                        )
                     .addTabbedAccessory(
                         new ImagePreviewAccessory(jfc)
                         )
                     .addTabbedAccessory(
                         new LastSelectedFilesAccessory( jfc, lSFAConf )
                         )
                     .addTabbedAccessory(
                         new FindAccessory( jfc )
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

    private void jButton_WaitingJFileChooserInitializerMouseMousePressed(MouseEvent event)
    {
        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                if( waitingJFileChooserInitializer == null ) {
                    JFileChooserInitializerCustomize     config    = getJFileChooserInitializerConfigurator();
                    Frame         frame    = JFileChooserAccessory.this;
                    String         waitTitle    = "waitTitle";
                    String         waitMessage    = "waitMessage";
                    waitingJFileChooserInitializer = new WaitingJFileChooserInitializer(config, frame, waitTitle, waitMessage );
                    }

                showOpenDialog(
                        waitingJFileChooserInitializer.getJFileChooser()
                        );
            }
        };

        new Thread( r ).start();
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

    private JFileChooserInitializerCustomize getJFileChooserInitializerConfigurator()
    {
        return new DefaultConfigurator()
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
                                    new DefaultBookmarksAccessoryConfigurator()
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
            );
        }

    private JFileChooserInitializer getJFileChooserInitializer()
    {
        return new JFileChooserInitializer(
            getJFileChooserInitializerConfigurator()
            );
    }
}
