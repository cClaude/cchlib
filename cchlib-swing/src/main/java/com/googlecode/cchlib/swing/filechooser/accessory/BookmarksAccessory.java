package com.googlecode.cchlib.swing.filechooser.accessory;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * TODOC
 */
public class BookmarksAccessory
    extends JPanel
        implements TabbedAccessoryInterface
{
    private static final long serialVersionUID = 1L;
    private static ResourcesUtils resourcesUtils = new ResourcesUtils( BookmarksAccessory.class );
    private BookmarksAccessoryConfigurator configurator;

    private JList<File>             jList_Bookmarks;
    private DefaultListModel<File>  listModel_Bookmarks;

    private JButton                 jButton_AddBookmarks;
    private JButton                 jButton_RemoveBookmarks;
    private JButton                 jButton_Refresh;

    /**
     * TODOC
     * @param jFileChooser
     * @param config
     */
    public BookmarksAccessory(
            final JFileChooser                   jFileChooser,
            final BookmarksAccessoryConfigurator config
            )
    {
        this.configurator = config;
        this.listModel_Bookmarks  = new DefaultListModel<File>();

        for( File f:config.getBookmarks() ) {
            this.listModel_Bookmarks.addElement( f );
            }

        {
            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
            gridBagLayout.rowHeights = new int[]{0, 0, 0};
            gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
            gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
            setLayout(gridBagLayout);
        }
        {
            JScrollPane scrollPane = new JScrollPane();
            GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
            gbc_scrollPane.gridwidth = 5;
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.gridx = 0;
            gbc_scrollPane.gridy = 0;
            add(scrollPane, gbc_scrollPane);

            jList_Bookmarks = new JList<File>(listModel_Bookmarks);
            jList_Bookmarks.addMouseListener( new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                        if( e.getClickCount() == 2 ) {
                            int     index   = jList_Bookmarks.locationToIndex(e.getPoint());
                            File    file    = listModel_Bookmarks.get( index );

                            jFileChooser.setCurrentDirectory( file );
                        }
                    }
                });
            scrollPane.setViewportView(jList_Bookmarks);
        }
        {
            jButton_AddBookmarks = createAddBookmarkButton();
            jButton_AddBookmarks.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    File f = jFileChooser.getCurrentDirectory();

                    if( f != null ) {
                        if( configurator.addBookmarkFile( f ) ) {
                            listModel_Bookmarks.addElement( f );
                            }
                        }
                }
            });
            GridBagConstraints gbc_jButton_AddBookmarks = new GridBagConstraints();
            gbc_jButton_AddBookmarks.insets = new Insets(0, 0, 0, 5);
            gbc_jButton_AddBookmarks.gridx = 1;
            gbc_jButton_AddBookmarks.gridy = 1;
            add(jButton_AddBookmarks, gbc_jButton_AddBookmarks);
        }
        {
            jButton_RemoveBookmarks = createRemoveBookmarkButton();
            jButton_RemoveBookmarks.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    int[] selectedIx = jList_Bookmarks.getSelectedIndices();

                    for( int i=selectedIx.length - 1; i>=0; i-- ) {
                        Object sel = listModel_Bookmarks.getElementAt(selectedIx[i]);

                        if( sel instanceof File) {
                            File f = File.class.cast( sel );

                            if( configurator.removeBookmark( f ) ) {
                                listModel_Bookmarks.remove( selectedIx[i] );
                            }
                        }
                    }
                }
            });
            GridBagConstraints gbc_jButton_RemoveBookmarks = new GridBagConstraints();
            gbc_jButton_RemoveBookmarks.insets = new Insets(0, 0, 0, 5);
            gbc_jButton_RemoveBookmarks.gridx = 2;
            gbc_jButton_RemoveBookmarks.gridy = 1;
            add(jButton_RemoveBookmarks, gbc_jButton_RemoveBookmarks);
        }
        {
            jButton_Refresh = createRefreshButton();
            jButton_Refresh.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                }
            });
            GridBagConstraints gbc_jButton_Refresh = new GridBagConstraints();
            gbc_jButton_Refresh.gridx = 3;
            gbc_jButton_Refresh.gridy = 1;
            add(jButton_Refresh, gbc_jButton_Refresh);
        }

        setPreferredSize( new Dimension(320, 240) );

        register();
    }

    /**
     * @return a valid JButton for refresh/rescan current directory,
     * @wbp.factory
     */
    public JButton createRefreshButton()
    {
        return resourcesUtils.getJButton( "reload.gif" );
    }

    /**
     * @return a valid JButton for "Add Bookmark" operation
     * @wbp.factory
     */
    public JButton createAddBookmarkButton()
    {
        return resourcesUtils.getJButton( "bookmark-add.png" );
    }

    /**
     * @return a valid JButton for "Remove Bookmark" operation
     * @wbp.factory
     */
    public JButton createRemoveBookmarkButton()
    {
        return resourcesUtils.getJButton( "bookmark-remove.gif" );
    }

    @Override // TabbedAccessoryInterface
    public String getTabName()
    {
        return null; //"Bookmark"; // TODO Localization
    }

    @Override // TabbedAccessoryInterface
    public Icon getTabIcon()
    {
        return resourcesUtils.getImageIcon( "bookmark-add.png" );
    }

    @Override // TabbedAccessoryInterface
    public Component getComponent()
    {
        return this;
    }

    @Override // TabbedAccessoryInterface
    public void register()
    {
    }

    @Override // TabbedAccessoryInterface
    public void unregister()
    {
    }
}

