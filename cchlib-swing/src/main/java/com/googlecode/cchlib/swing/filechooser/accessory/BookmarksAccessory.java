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
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.swing.list.JPopupMenuForJList;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * {@link JFileChooser} accessory able to book mark your
 * favorite folders.
 *
 * @see javax.swing.JFileChooser#setAccessory(javax.swing.JComponent)
 * @see TabbedAccessory
 */
public class BookmarksAccessory
    extends JPanel
        implements TabbedAccessoryInterface
{
    private static final long serialVersionUID = 2L;
    private static final String ACTION_ADD     = "ACTION_ADD";
    private static final String ACTION_REMOVE  = "ACTION_REMOVE";
    private static final String ACTION_REFRESH = "ACTION_REFRESH";
    private static final Object ACTION_OBJECT  = "INDEX";

    private static ResourcesUtils resourcesUtils = new ResourcesUtils();

    private ActionListener actionListener;
    private BookmarksAccessoryConfigurator configurator;
    private JFileChooser jFileChooser;

    private JList<File>             jList_Bookmarks;
    private DefaultListModel<File>  listModel_Bookmarks;

    private JButton                 jButton_AddBookmarks;
    private JButton                 jButton_RemoveBookmarks;
    private JButton                 jButton_Refresh;

    /**
     * Create a BookmarksAccessory
     *
     * @param jFileChooser  {@link JFileChooser} to use
     * @param config        Initial configuration for this BookmarksAccessory
     */
    public BookmarksAccessory(
            final JFileChooser                   jFileChooser,
            final BookmarksAccessoryConfigurator config
            )
    {
        this.jFileChooser           = jFileChooser;
        this.configurator           = config;
        this.listModel_Bookmarks    = new DefaultListModel<File>();

        fillBookmarkList();

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{25, 25, 25, 0};
        gridBagLayout.rowHeights = new int[]{150, 22, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
        setPreferredSize( new Dimension(320, 240) );

        {
            JScrollPane scrollPane_jList_Bookmarks = new JScrollPane();
            GridBagConstraints gbc_scrollPane_jList_Bookmarks = new GridBagConstraints();
            gbc_scrollPane_jList_Bookmarks.gridwidth = 4;
            gbc_scrollPane_jList_Bookmarks.fill = GridBagConstraints.BOTH;
            gbc_scrollPane_jList_Bookmarks.insets = new Insets(0, 0, 5, 5);
            gbc_scrollPane_jList_Bookmarks.gridx = 0;
            gbc_scrollPane_jList_Bookmarks.gridy = 0;
            add(scrollPane_jList_Bookmarks, gbc_scrollPane_jList_Bookmarks);

            jList_Bookmarks = new JList<File>(listModel_Bookmarks);
            scrollPane_jList_Bookmarks.setViewportView(jList_Bookmarks);
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

        }
        {
            // NEEDED for WindowsBuilder jButton_AddBookmarks = new JButton( ACTION_ADD );
            jButton_AddBookmarks = createAddBookmarkButton();
            jButton_AddBookmarks.setActionCommand( ACTION_ADD );
            jButton_AddBookmarks.addActionListener( getActionListener() );
            GridBagConstraints gbc_jButton_AddBookmarks = new GridBagConstraints();
            gbc_jButton_AddBookmarks.fill = GridBagConstraints.BOTH;
            gbc_jButton_AddBookmarks.insets = new Insets(0, 0, 0, 5);
            gbc_jButton_AddBookmarks.gridx = 0;
            gbc_jButton_AddBookmarks.gridy = 1;
            add(jButton_AddBookmarks, gbc_jButton_AddBookmarks);
        }
        {
            // NEEDED for WindowsBuilder jButton_RemoveBookmarks = new JButton( ACTION_REMOVE );
            jButton_RemoveBookmarks = createRemoveBookmarkButton();
            jButton_RemoveBookmarks.setActionCommand( ACTION_REMOVE );
            jButton_RemoveBookmarks.addActionListener( getActionListener() );
            GridBagConstraints gbc_jButton_RemoveBookmarks = new GridBagConstraints();
            gbc_jButton_RemoveBookmarks.fill = GridBagConstraints.BOTH;
            gbc_jButton_RemoveBookmarks.insets = new Insets(0, 0, 0, 5);
            gbc_jButton_RemoveBookmarks.gridx = 1;
            gbc_jButton_RemoveBookmarks.gridy = 1;
            add(jButton_RemoveBookmarks, gbc_jButton_RemoveBookmarks);
        }
        {
            // NEEDED for WindowsBuilder jButton_Refresh = new JButton( ACTION_REFRESH );
            jButton_Refresh = createRefreshButton();
            jButton_Refresh.setActionCommand( ACTION_REFRESH );
            jButton_Refresh.addActionListener( getActionListener() );
            GridBagConstraints gbc_jButton_Refresh = new GridBagConstraints();
            gbc_jButton_Refresh.fill = GridBagConstraints.BOTH;
            gbc_jButton_Refresh.insets = new Insets(0, 0, 0, 5);
            gbc_jButton_Refresh.gridx = 2;
            gbc_jButton_Refresh.gridy = 1;
            add(jButton_Refresh, gbc_jButton_Refresh);
        }

        createPopupMenu();
        register();
    }

    private void createPopupMenu()
    {
        final JPopupMenuForJList<File> popupMenu = new JPopupMenuForJList<File>( jList_Bookmarks )
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected JPopupMenu createContextMenu( int rowIndex )
            {
                JPopupMenu cm = new JPopupMenu();

                addCopyMenuItem(
                    cm,
                    //new JMenuItem( resourcesUtils.getText( ResourcesUtils.TXTID_COPY ) ),
                    resourcesUtils.getText( ResourcesUtils.ID.COPY ),
                    rowIndex
                    );

                //addJMenuItem( cm, "menuItemTxt", getActionListener(), actionCommandString, actionObjectType, actionObject );
                //addJMenuItem( cm, "menuItemTxt", getActionListener(), actionCommandString );
                addJMenuItem(
                    cm,
                    resourcesUtils.getText( ResourcesUtils.ID.BOOKMARK_REMOVE ),
                    getActionListener(),
                    ACTION_REMOVE,
                    ACTION_OBJECT,
                    new Integer( rowIndex )
                    );

                return cm;
            }
        };

        popupMenu.setMenu();
    }

    private ActionListener getActionListener()
    {
        if( actionListener == null ) {
            actionListener = new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent event )
                {
                    String cmd = event.getActionCommand();

                    if( ACTION_ADD.equals( cmd ) ) {
                        doAdd();
                        }
                    else if( ACTION_REMOVE.equals( cmd ) ) {
                        if( event.getSource() instanceof JButton ) {
                            // Remove selected entries in list
                            doRemove( jList_Bookmarks.getSelectedIndices() );
                            }
                        else if( event.getSource() instanceof JMenuItem ) {
                            // Remove current entry (using contextual menu)
                            final int index = Integer.class.cast(
                                    JMenuItem.class.cast( event.getSource() )
                                        .getClientProperty( ACTION_OBJECT )
                                    );
                            doRemove( new int[]{ index } );
                            }
                        }
                    else if( ACTION_REFRESH.equals( cmd ) ) {
                        doRefresh();
                        }
                 }
            };
            }
        return actionListener;
    }

    private void fillBookmarkList()
    {
        this.listModel_Bookmarks.clear();

        for( File f : configurator.getBookmarks() ) {
            this.listModel_Bookmarks.addElement( f );
            }
    }

    private void doAdd()
    {
        File f = jFileChooser.getCurrentDirectory();

        if( f != null ) {
            if( configurator.addBookmarkFile( f ) ) {
                listModel_Bookmarks.addElement( f );
                }
            }
    }

    private void doRemove( final int[] indexToRemove )
    {
        for( int i = indexToRemove.length - 1; i>=0; i-- ) {
            File f = listModel_Bookmarks.getElementAt( indexToRemove[ i ] );

            if( configurator.removeBookmark( f ) ) {
                listModel_Bookmarks.remove( indexToRemove[i] );
                }
            }
    }

    private void doRefresh()
    {
        //
        // Refresh JFileChooser display
        //
        File dir = jFileChooser.getCurrentDirectory();
        File tmpDir = FileHelper.getTmpDirFile();

        if( tmpDir.equals( dir ) ) {
            tmpDir = FileHelper.getUserHomeDirFile();
            }

        jFileChooser.setCurrentDirectory( tmpDir );
        jFileChooser.setCurrentDirectory( dir );

        //
        // Refresh config (if configurator allow this)
        //
        fillBookmarkList();
    }


    /**
     * @return a valid JButton for refresh/rescan current directory,
     * @wbp.factory
     */
    private JButton createRefreshButton()
    {
        return resourcesUtils.getJButton( ResourcesUtils.ID.BOOKMARK_UPDATE );
    }

    /**
     * @return a valid JButton for "Add Bookmark" operation
     */
    private JButton createAddBookmarkButton()
    {
        return resourcesUtils.getJButton( ResourcesUtils.ID.BOOKMARK_ADD );
    }

    /**
     * @return a valid JButton for "Remove Bookmark" operation
     * @wbp.factory
     */
    private JButton createRemoveBookmarkButton()
    {
        return resourcesUtils.getJButton( ResourcesUtils.ID.BOOKMARK_REMOVE );
    }

    @Override // TabbedAccessoryInterface
    public String getTabName()
    {
        return null; //"Bookmark"; // TODO Localization
    }

    @Override // TabbedAccessoryInterface
    public Icon getTabIcon()
    {
        return resourcesUtils.getImageIcon( ResourcesUtils.ID.BOOKMARK_ADD );
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

