package com.googlecode.cchlib.samples.swing;

import java.awt.Image;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import com.googlecode.cchlib.samples.Samples;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializerCustomize;
import com.googlecode.cchlib.swing.filechooser.FileNameExtensionFilter;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.LasyJFCCustomizer;
import com.googlecode.cchlib.swing.filechooser.WaitingJFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.accessory.BookmarksAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.DefaultBookmarksAccessoryConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.FindAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.ImagePreviewAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.LastSelectedFilesAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.LastSelectedFilesAccessoryConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.LastSelectedFilesAccessoryDefaultConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.TabbedAccessory;
import com.googlecode.cchlib.swing.menu.LookAndFeelMenu;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Show how to use {@link TabbedAccessory}, {@link ImagePreviewAccessory},
 * {@link LastSelectedFilesAccessory}, {@link FindAccessory},
 * {@link LookAndFeelMenu}
 */
public class JFileChooserAccessory extends JFrame
{
    private static final long serialVersionUID = 2L;

    private LastSelectedFilesAccessoryConfigurator lSFAConf
      = new LastSelectedFilesAccessoryDefaultConfigurator();

    private JFileChooserInitializer jFileChooserInitializer;
    private WaitingJFileChooserInitializer waitingJFileChooserInitializer;

    private JMenu jMenuLookAndFeel;
    private JTextField jTextField_LastSelected;

    public JFileChooserAccessory()
    {
        {
            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
            gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
            gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
            gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
            getContentPane().setLayout(gridBagLayout);
        }
        {
            GridBagConstraints gbc_jButton_StdJFileChooser = new GridBagConstraints();
            gbc_jButton_StdJFileChooser.fill = GridBagConstraints.BOTH;
            gbc_jButton_StdJFileChooser.insets = new Insets(0, 0, 5, 5);
            gbc_jButton_StdJFileChooser.gridx = 0;
            gbc_jButton_StdJFileChooser.gridy = 0;

            JButton jButton_StdJFileChooser = new JButton("Classic JFileChooser");
            jButton_StdJFileChooser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openJFileChooser();
                }
            });
            getContentPane().add(jButton_StdJFileChooser, gbc_jButton_StdJFileChooser);
        }
        {
            GridBagConstraints gbc_jButton_BookmarksFiles = new GridBagConstraints();
            gbc_jButton_BookmarksFiles.fill = GridBagConstraints.BOTH;
            gbc_jButton_BookmarksFiles.insets = new Insets(0, 0, 5, 5);
            gbc_jButton_BookmarksFiles.gridx = 1;
            gbc_jButton_BookmarksFiles.gridy = 0;

            JButton jButton_BookmarksFiles = new JButton("Bookmarks (Files)");
            jButton_BookmarksFiles.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openJFileChooserWithBookmarksForFiles();
                }
            });
            getContentPane().add(jButton_BookmarksFiles, gbc_jButton_BookmarksFiles);
        }
        {
            GridBagConstraints gbc_jButton_JFileChooserInitializer = new GridBagConstraints();
            gbc_jButton_JFileChooserInitializer.fill = GridBagConstraints.BOTH;
            gbc_jButton_JFileChooserInitializer.insets = new Insets(0, 0, 5, 5);
            gbc_jButton_JFileChooserInitializer.gridx = 0;
            gbc_jButton_JFileChooserInitializer.gridy = 1;

            JButton jButton_JFileChooserInitializer = new JButton("JFileChooserInitializer");
            jButton_JFileChooserInitializer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openJFileChooserUsingJFileChooserInitializer();
                }
            });
            getContentPane().add(jButton_JFileChooserInitializer, gbc_jButton_JFileChooserInitializer);
        }
        {
            GridBagConstraints gbc_jButton_BookmarksFolders = new GridBagConstraints();
            gbc_jButton_BookmarksFolders.fill = GridBagConstraints.BOTH;
            gbc_jButton_BookmarksFolders.insets = new Insets(0, 0, 5, 0);
            gbc_jButton_BookmarksFolders.gridx = 2;
            gbc_jButton_BookmarksFolders.gridy = 0;

            JButton jButton_BookmarksFolders = new JButton("Bookmarks (Folders)");
            jButton_BookmarksFolders.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openJFileChooserWithBookmarksForDirectories();
                }
            });
            getContentPane().add(jButton_BookmarksFolders, gbc_jButton_BookmarksFolders);
        }
        {
            GridBagConstraints gbc_jButton_Picture = new GridBagConstraints();
            gbc_jButton_Picture.fill = GridBagConstraints.BOTH;
            gbc_jButton_Picture.insets = new Insets(0, 0, 5, 5);
            gbc_jButton_Picture.gridx = 1;
            gbc_jButton_Picture.gridy = 3;

            JButton jButton_Picture = new JButton("Preview pictures");
            jButton_Picture.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openJFileChooserWithPicture();
                }
            });
            getContentPane().add(jButton_Picture, gbc_jButton_Picture);
        }
        {
            GridBagConstraints gbc_jButton_WaitingJFileChooserInitializer = new GridBagConstraints();
            gbc_jButton_WaitingJFileChooserInitializer.fill = GridBagConstraints.BOTH;
            gbc_jButton_WaitingJFileChooserInitializer.insets = new Insets(0, 0, 5, 5);
            gbc_jButton_WaitingJFileChooserInitializer.gridx = 0;
            gbc_jButton_WaitingJFileChooserInitializer.gridy = 2;

            JButton jButton_WaitingJFileChooserInitializer = new JButton("WaitingJFileChooserInitializer");
            jButton_WaitingJFileChooserInitializer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openJFileChooserUsingWaitingJFileChooserInitializer();
                }
            });
            getContentPane().add(jButton_WaitingJFileChooserInitializer, gbc_jButton_WaitingJFileChooserInitializer);
        }
        {
            GridBagConstraints gbc_jButton_LastSelectedFiles = new GridBagConstraints();
            gbc_jButton_LastSelectedFiles.fill = GridBagConstraints.BOTH;
            gbc_jButton_LastSelectedFiles.insets = new Insets(0, 0, 5, 5);
            gbc_jButton_LastSelectedFiles.gridx = 1;
            gbc_jButton_LastSelectedFiles.gridy = 1;

            JButton jButton_LastSelectedFiles = new JButton("Last selected files");
            jButton_LastSelectedFiles.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openJFileChooserWithLastSelectedFilesList();
                }
            });
            getContentPane().add(jButton_LastSelectedFiles, gbc_jButton_LastSelectedFiles);
        }
        {
             GridBagConstraints gbc_jButton_LastSelectedFolders = new GridBagConstraints();
            gbc_jButton_LastSelectedFolders.fill = GridBagConstraints.BOTH;
            gbc_jButton_LastSelectedFolders.insets = new Insets(0, 0, 5, 0);
            gbc_jButton_LastSelectedFolders.gridx = 2;
            gbc_jButton_LastSelectedFolders.gridy = 1;

            JButton jButton_LastSelectedFolders = new JButton("Last selected folders");
            jButton_LastSelectedFolders.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openJFileChooserWithLastSelectedDirectoriesList();
                }
            });
            getContentPane().add(jButton_LastSelectedFolders, gbc_jButton_LastSelectedFolders);
        }
        {
            GridBagConstraints gbc_jButton_TabbedFiles = new GridBagConstraints();
            gbc_jButton_TabbedFiles.fill = GridBagConstraints.BOTH;
            gbc_jButton_TabbedFiles.insets = new Insets(0, 0, 5, 5);
            gbc_jButton_TabbedFiles.gridx = 1;
            gbc_jButton_TabbedFiles.gridy = 2;

            JButton jButton_TabbedFiles = new JButton("Multi Accessories (Files)");
            jButton_TabbedFiles.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openJFileChooserWithMultiAccessoryForFiles();
                }
            });
            getContentPane().add(jButton_TabbedFiles, gbc_jButton_TabbedFiles);
        }
        {
            GridBagConstraints gbc_jButton_TabbedFolders = new GridBagConstraints();
            gbc_jButton_TabbedFolders.fill = GridBagConstraints.BOTH;
            gbc_jButton_TabbedFolders.insets = new Insets(0, 0, 5, 0);
            gbc_jButton_TabbedFolders.gridx = 2;
            gbc_jButton_TabbedFolders.gridy = 2;

            JButton jButton_TabbedFolders = new JButton("Multi Accessories (folders)");
            jButton_TabbedFolders.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openJFileChooserWithMultiAccessoryForDirectories();
                }
            });
            getContentPane().add(jButton_TabbedFolders, gbc_jButton_TabbedFolders);
        }
        {
            GridBagConstraints gbc_jTextField_LastSelected = new GridBagConstraints();
            gbc_jTextField_LastSelected.gridwidth = 3;
            gbc_jTextField_LastSelected.fill = GridBagConstraints.BOTH;
            gbc_jTextField_LastSelected.gridx = 0;
            gbc_jTextField_LastSelected.gridy = 4;
            jTextField_LastSelected = new JTextField("...");
            jTextField_LastSelected.setEditable( false );
            getContentPane().add(jTextField_LastSelected, gbc_jTextField_LastSelected);
        }
        {
            JMenuBar jMenuBarFrame = new JMenuBar();
            jMenuLookAndFeel = new JMenu("Look And Feel");
            jMenuBarFrame.add( jMenuLookAndFeel );
            setJMenuBar(jMenuBarFrame);
        }
        setSize(600, 200);
    }


    protected void initFixComponents()
    {
//        Image iconImage = Toolkit.getDefaultToolkit().getImage(
//                getClass().getResource("sample.png")
//                );
        Image iconImage = Samples.getSampleIconImage();
        setIconImage( iconImage );

        //MenuHelper.buildLookAndFeelMenu( this, jMenuLookAndFeel );
        new LookAndFeelMenu( this ).buildMenu( jMenuLookAndFeel );

        jFileChooserInitializer = createJFileChooserInitializer();
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

    private void openJFileChooser()
    {
        JFileChooser jfc = new JFileChooser();
        showOpenDialog( jfc );
    }

    private void openJFileChooserWithBookmarksForFiles()
    {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
        jfc.setAccessory(
                new BookmarksAccessory(
                                jfc,
                                new DefaultBookmarksAccessoryConfigurator()
                                )
                );
        showOpenDialog( jfc );
    }

    private void openJFileChooserWithBookmarksForDirectories()
    {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        jfc.setAccessory(
                new BookmarksAccessory(
                                jfc,
                                new DefaultBookmarksAccessoryConfigurator()
                                )
                );
        showOpenDialog( jfc );
    }

    private void openJFileChooserWithPicture()
    {
        JFileChooser jfc = new JFileChooser();
        jfc.setAccessory(
                new ImagePreviewAccessory(jfc)
                );
        showOpenDialog( jfc );
    }

    private void openJFileChooserWithMultiAccessoryForFiles()
    {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
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

    protected void openJFileChooserWithMultiAccessoryForDirectories()
    {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
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

    private void openJFileChooserWithLastSelectedFilesList()
    {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
        jfc.setAccessory(
                new LastSelectedFilesAccessory( jfc, lSFAConf )
                );
        showOpenDialog( jfc );
    }

    protected void openJFileChooserWithLastSelectedDirectoriesList()
    {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        jfc.setAccessory(
                new LastSelectedFilesAccessory( jfc, lSFAConf )
                );
        showOpenDialog( jfc );
    }

    private void openJFileChooserUsingJFileChooserInitializer()
    {
        showOpenDialog(
                jFileChooserInitializer.getJFileChooser()
                );
    }

    private void openJFileChooserUsingWaitingJFileChooserInitializer()
    {
        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                if( waitingJFileChooserInitializer == null ) {
                    JFileChooserInitializerCustomize     config    = createJFileChooserInitializerConfigurator();
                    Frame         frame            = JFileChooserAccessory.this;
                    String         waitTitle       = "waitTitle";
                    String         waitMessage     = "waitMessage";
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
        setLastSelected( "<<Waiting user>>" );

        int returnVal = jfc.showOpenDialog(this);

        if( returnVal == JFileChooser.APPROVE_OPTION ) {
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

    private JFileChooserInitializerCustomize createJFileChooserInitializerConfigurator()
    {
        return new LasyJFCCustomizer()
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

    private JFileChooserInitializer createJFileChooserInitializer()
    {
        return new JFileChooserInitializer(
            createJFileChooserInitializerConfigurator()
            );
    }
}
