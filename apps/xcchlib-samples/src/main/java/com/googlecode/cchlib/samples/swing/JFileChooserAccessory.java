package com.googlecode.cchlib.samples.swing;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import com.googlecode.cchlib.samples.Samples;
import com.googlecode.cchlib.swing.filechooser.FileNameExtensionFilter;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializerCustomize;
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

/**
 * Show how to use {@link TabbedAccessory}, {@link ImagePreviewAccessory},
 * {@link LastSelectedFilesAccessory}, {@link FindAccessory},
 * {@link LookAndFeelMenu}
 */
@SuppressWarnings({
    "squid:MaximumInheritanceDepth", // Swing
    "squid:S00116", "squid:S00117" // Generated code
    })
public class JFileChooserAccessory extends JFrame
{
    private static final long serialVersionUID = 2L;

    private final LastSelectedFilesAccessoryConfigurator lSFAConf
      = new LastSelectedFilesAccessoryDefaultConfigurator();

    private JFileChooserInitializer jFileChooserInitializer;
    private WaitingJFileChooserInitializer waitingJFileChooserInitializer;

    private JMenu jMenuLookAndFeel;
    private JTextField jTextField_LastSelected;

    @SuppressWarnings("squid:S1199") // Generated code
    public JFileChooserAccessory()
    {
        {
            final GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
            gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
            gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
            gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
            getContentPane().setLayout(gridBagLayout);
        }
        {
            final GridBagConstraints gbc_jButton_StdJFileChooser = new GridBagConstraints();
            gbc_jButton_StdJFileChooser.fill = GridBagConstraints.BOTH;
            gbc_jButton_StdJFileChooser.insets = new Insets(0, 0, 5, 5);
            gbc_jButton_StdJFileChooser.gridx = 0;
            gbc_jButton_StdJFileChooser.gridy = 0;

            final JButton jButton_StdJFileChooser = new JButton("Classic JFileChooser");
            jButton_StdJFileChooser.addActionListener(e -> openJFileChooser());
            getContentPane().add(jButton_StdJFileChooser, gbc_jButton_StdJFileChooser);
        }
        {
            final GridBagConstraints gbc_jButton_BookmarksFiles = new GridBagConstraints();
            gbc_jButton_BookmarksFiles.fill = GridBagConstraints.BOTH;
            gbc_jButton_BookmarksFiles.insets = new Insets(0, 0, 5, 5);
            gbc_jButton_BookmarksFiles.gridx = 1;
            gbc_jButton_BookmarksFiles.gridy = 0;

            final JButton jButton_BookmarksFiles = new JButton("Bookmarks (Files)");
            jButton_BookmarksFiles.addActionListener(e -> openJFileChooserWithBookmarksForFiles());
            getContentPane().add(jButton_BookmarksFiles, gbc_jButton_BookmarksFiles);
        }
        {
            final GridBagConstraints gbc_jButton_JFileChooserInitializer = new GridBagConstraints();
            gbc_jButton_JFileChooserInitializer.fill = GridBagConstraints.BOTH;
            gbc_jButton_JFileChooserInitializer.insets = new Insets(0, 0, 5, 5);
            gbc_jButton_JFileChooserInitializer.gridx = 0;
            gbc_jButton_JFileChooserInitializer.gridy = 1;

            final JButton jButton_JFileChooserInitializer = new JButton("JFileChooserInitializer");
            jButton_JFileChooserInitializer.addActionListener(e -> openJFileChooserUsingJFileChooserInitializer());
            getContentPane().add(jButton_JFileChooserInitializer, gbc_jButton_JFileChooserInitializer);
        }
        {
            final GridBagConstraints gbc_jButton_BookmarksFolders = new GridBagConstraints();
            gbc_jButton_BookmarksFolders.fill = GridBagConstraints.BOTH;
            gbc_jButton_BookmarksFolders.insets = new Insets(0, 0, 5, 0);
            gbc_jButton_BookmarksFolders.gridx = 2;
            gbc_jButton_BookmarksFolders.gridy = 0;

            final JButton jButton_BookmarksFolders = new JButton("Bookmarks (Folders)");
            jButton_BookmarksFolders.addActionListener(e -> openJFileChooserWithBookmarksForDirectories());
            getContentPane().add(jButton_BookmarksFolders, gbc_jButton_BookmarksFolders);
        }
        {
            final GridBagConstraints gbc_jButton_Picture = new GridBagConstraints();
            gbc_jButton_Picture.fill = GridBagConstraints.BOTH;
            gbc_jButton_Picture.insets = new Insets(0, 0, 5, 5);
            gbc_jButton_Picture.gridx = 1;
            gbc_jButton_Picture.gridy = 3;

            final JButton jButton_Picture = new JButton("Preview pictures");
            jButton_Picture.addActionListener(e -> openJFileChooserWithPicture());
            getContentPane().add(jButton_Picture, gbc_jButton_Picture);
        }
        {
            final GridBagConstraints gbc_jButton_WaitingJFileChooserInitializer = new GridBagConstraints();
            gbc_jButton_WaitingJFileChooserInitializer.fill = GridBagConstraints.BOTH;
            gbc_jButton_WaitingJFileChooserInitializer.insets = new Insets(0, 0, 5, 5);
            gbc_jButton_WaitingJFileChooserInitializer.gridx = 0;
            gbc_jButton_WaitingJFileChooserInitializer.gridy = 2;

            final JButton jButton_WaitingJFileChooserInitializer = new JButton("WaitingJFileChooserInitializer");
            jButton_WaitingJFileChooserInitializer.addActionListener(e -> openJFileChooserUsingWaitingJFileChooserInitializer());
            getContentPane().add(jButton_WaitingJFileChooserInitializer, gbc_jButton_WaitingJFileChooserInitializer);
        }
        {
            final GridBagConstraints gbc_jButton_LastSelectedFiles = new GridBagConstraints();
            gbc_jButton_LastSelectedFiles.fill = GridBagConstraints.BOTH;
            gbc_jButton_LastSelectedFiles.insets = new Insets(0, 0, 5, 5);
            gbc_jButton_LastSelectedFiles.gridx = 1;
            gbc_jButton_LastSelectedFiles.gridy = 1;

            final JButton jButton_LastSelectedFiles = new JButton("Last selected files");
            jButton_LastSelectedFiles.addActionListener(e -> openJFileChooserWithLastSelectedFilesList());
            getContentPane().add(jButton_LastSelectedFiles, gbc_jButton_LastSelectedFiles);
        }
        {
             final GridBagConstraints gbc_jButton_LastSelectedFolders = new GridBagConstraints();
            gbc_jButton_LastSelectedFolders.fill = GridBagConstraints.BOTH;
            gbc_jButton_LastSelectedFolders.insets = new Insets(0, 0, 5, 0);
            gbc_jButton_LastSelectedFolders.gridx = 2;
            gbc_jButton_LastSelectedFolders.gridy = 1;

            final JButton jButton_LastSelectedFolders = new JButton("Last selected folders");
            jButton_LastSelectedFolders.addActionListener(e -> openJFileChooserWithLastSelectedDirectoriesList());
            getContentPane().add(jButton_LastSelectedFolders, gbc_jButton_LastSelectedFolders);
        }
        {
            final GridBagConstraints gbc_jButton_TabbedFiles = new GridBagConstraints();
            gbc_jButton_TabbedFiles.fill = GridBagConstraints.BOTH;
            gbc_jButton_TabbedFiles.insets = new Insets(0, 0, 5, 5);
            gbc_jButton_TabbedFiles.gridx = 1;
            gbc_jButton_TabbedFiles.gridy = 2;

            final JButton jButton_TabbedFiles = new JButton("Multi Accessories (Files)");
            jButton_TabbedFiles.addActionListener(e -> openJFileChooserWithMultiAccessoryForFiles());
            getContentPane().add(jButton_TabbedFiles, gbc_jButton_TabbedFiles);
        }
        {
            final GridBagConstraints gbc_jButton_TabbedFolders = new GridBagConstraints();
            gbc_jButton_TabbedFolders.fill = GridBagConstraints.BOTH;
            gbc_jButton_TabbedFolders.insets = new Insets(0, 0, 5, 0);
            gbc_jButton_TabbedFolders.gridx = 2;
            gbc_jButton_TabbedFolders.gridy = 2;

            final JButton jButton_TabbedFolders = new JButton("Multi Accessories (folders)");
            jButton_TabbedFolders.addActionListener(e -> openJFileChooserWithMultiAccessoryForDirectories());
            getContentPane().add(jButton_TabbedFolders, gbc_jButton_TabbedFolders);
        }
        {
            final GridBagConstraints gbc_jTextField_LastSelected = new GridBagConstraints();
            gbc_jTextField_LastSelected.gridwidth = 3;
            gbc_jTextField_LastSelected.fill = GridBagConstraints.BOTH;
            gbc_jTextField_LastSelected.gridx = 0;
            gbc_jTextField_LastSelected.gridy = 4;
            this.jTextField_LastSelected = new JTextField("...");
            this.jTextField_LastSelected.setEditable( false );
            getContentPane().add(this.jTextField_LastSelected, gbc_jTextField_LastSelected);
        }
        {
            final JMenuBar jMenuBarFrame = new JMenuBar();
            this.jMenuLookAndFeel = new JMenu("Look And Feel");
            jMenuBarFrame.add( this.jMenuLookAndFeel );
            setJMenuBar(jMenuBarFrame);
        }
        setSize(600, 200);
    }


    protected void initFixComponents()
    {
        final Image iconImage = Samples.getSampleIconImage();
        setIconImage( iconImage );

        new LookAndFeelMenu( this ).buildMenu( this.jMenuLookAndFeel );

        this.jFileChooserInitializer = createJFileChooserInitializer();
    }

    public static void main( final String[] args )
    {
        SwingUtilities.invokeLater( ( ) -> {
            try {
                final JFileChooserAccessory frame = new JFileChooserAccessory();
                frame.initFixComponents();
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                frame.setTitle( "Sample for JFileChooser Accessory" );
                frame.getContentPane().setPreferredSize( frame.getSize() );
                frame.pack();
                frame.setLocationRelativeTo( null );
                frame.setVisible( true );
                }
            catch( final Exception e ) {
                e.printStackTrace( System.err );
                }
        } );
    }

    private void openJFileChooser()
    {
        final JFileChooser jfc = new JFileChooser();
        showOpenDialog( jfc );
    }

    private void openJFileChooserWithBookmarksForFiles()
    {
        final JFileChooser jfc = new JFileChooser();
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
        final JFileChooser jfc = new JFileChooser();
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
        final JFileChooser jfc = new JFileChooser();
        jfc.setAccessory(
                new ImagePreviewAccessory(jfc)
                );
        showOpenDialog( jfc );
    }

    private void openJFileChooserWithMultiAccessoryForFiles()
    {
        final JFileChooser jfc = new JFileChooser();
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
                         new LastSelectedFilesAccessory( jfc, this.lSFAConf )
                         )
                     .addTabbedAccessory(
                         new FindAccessory( jfc )
                         )
                );
        showOpenDialog( jfc );
    }

    protected void openJFileChooserWithMultiAccessoryForDirectories()
    {
        final JFileChooser jfc = new JFileChooser();
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
                         new LastSelectedFilesAccessory( jfc, this.lSFAConf )
                         )
                     .addTabbedAccessory(
                         new FindAccessory( jfc )
                         )
                );
        showOpenDialog( jfc );
    }

    private void openJFileChooserWithLastSelectedFilesList()
    {
        final JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
        jfc.setAccessory(
                new LastSelectedFilesAccessory( jfc, this.lSFAConf )
                );
        showOpenDialog( jfc );
    }

    protected void openJFileChooserWithLastSelectedDirectoriesList()
    {
        final JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        jfc.setAccessory(
                new LastSelectedFilesAccessory( jfc, this.lSFAConf )
                );
        showOpenDialog( jfc );
    }

    private void openJFileChooserUsingJFileChooserInitializer()
    {
        showOpenDialog(
                this.jFileChooserInitializer.getJFileChooser()
                );
    }

    private void openJFileChooserUsingWaitingJFileChooserInitializer()
    {
        final Runnable r = ( ) -> {
            if( JFileChooserAccessory.this.waitingJFileChooserInitializer == null ) {
                final JFileChooserInitializerCustomize     config    = createJFileChooserInitializerConfigurator();
                final Frame         frame            = JFileChooserAccessory.this;
                final String         waitTitle       = "waitTitle";
                final String         waitMessage     = "waitMessage";
                JFileChooserAccessory.this.waitingJFileChooserInitializer = new WaitingJFileChooserInitializer(config, frame, waitTitle, waitMessage );
                }

            showOpenDialog(
                    JFileChooserAccessory.this.waitingJFileChooserInitializer.getJFileChooser()
                    );
        };

        new Thread( r ).start();
    }

    private void showOpenDialog(final JFileChooser jfc)
    {
        setLastSelected( "<<Waiting user>>" );

        final int returnVal = jfc.showOpenDialog(this);

        if( returnVal == JFileChooser.APPROVE_OPTION ) {
            final File f = jfc.getSelectedFile();

            System.out.printf( "You chose to open this file: %s%n", f);
            setLastSelected( f.getPath() );
            }
        else {
            setLastSelected( "<<No selection>>" );
            }
    }

    private void setLastSelected(final String txt)
    {
        this.jTextField_LastSelected.setText(txt);
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
