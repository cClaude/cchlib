/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package com.googlecode.cchlib.swing.filechooser.accessory;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 *
 * @author Claude CHOISNET
 * Note: Need JRE 1.6
 */
public class BookmarksAccessory
    extends JPanel
        implements TabbedAccessoryInterface
{
    private static final long serialVersionUID = 1L;

    /** @serial */
    private JScrollPane         	jScrollPane_Bookmarks;
    /** @serial */
    private DefaultListModel/*<File>*/ 	listModel_Bookmarks;
    /** @serial */
    private JButton             jButton_AddBookmarks;
    /** @serial */
    private JButton             jButton_RemoveBookmarks;
    /** @serial */
    private JButton             jButton_Refresh;

    /** @serial */
    private JFileChooser    jFileChooser;
    /** @serial */
    private Configurator    configurator;

    private ResourcesUtils resourcesUtils;

    public BookmarksAccessory(
            final JFileChooser  jFileChooser,
            final Configurator  config
            )
    {
        this.jFileChooser = jFileChooser;
        this.configurator = config;
        this.resourcesUtils = new ResourcesUtils( getClass() );

        register();

        listModel_Bookmarks  = new DefaultListModel/*<File>*/();

        for(File f:config.getBookmarks()) {
            listModel_Bookmarks.addElement( f );
            }

        initComponents();
        initLayout();
        register();
    }

    private void initComponents()
    {
        final JList/*<File>*/ jList_Bookmarks = new JList/*<File>*/(listModel_Bookmarks);
        jList_Bookmarks.addMouseListener(
                new MouseAdapter()
                {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                        if (e.getClickCount() == 2) {
                            int     index = jList_Bookmarks.locationToIndex(e.getPoint());
                            Object  o     = listModel_Bookmarks.get( index );

                            if( o instanceof File ) {
                                jFileChooser.setCurrentDirectory( File.class.cast( o ) );
                            }
                        }
                    }
                });

        jScrollPane_Bookmarks = new JScrollPane(jList_Bookmarks);

        jButton_AddBookmarks = getAddBookmarkButton();
        jButton_AddBookmarks.addMouseListener(
                new MouseAdapter()
                {
                    public void mousePressed(MouseEvent event)
                    {
                        File f = jFileChooser.getCurrentDirectory();

                        if( f != null ) {
                            if( configurator.addBookmarkFile( f ) ) {
                                listModel_Bookmarks.addElement( f );
                            }
                        }
                    }
                });

        jButton_RemoveBookmarks = getRemoveBookmarkButton();
        jButton_RemoveBookmarks.addMouseListener(
                new MouseAdapter()
                {
                    public void mousePressed(MouseEvent event)
                    {
                        int[] selectedIx = jList_Bookmarks.getSelectedIndices();

                        for( int i=selectedIx.length - 1; i>=0; i-- ) {
                            Object sel = jList_Bookmarks.getModel().getElementAt(selectedIx[i]);

                            if( sel instanceof File) {
                                File f = File.class.cast( sel );

                                if( configurator.removeBookmark( f ) ) {
                                    listModel_Bookmarks.remove( selectedIx[i] );
                                }
                            }
                        }
                    }
                });

        jButton_Refresh = getRefreshButton();
        jButton_Refresh.addMouseListener(
                new MouseAdapter()
                {
                    public void mousePressed(MouseEvent event)
                    {
                        jFileChooser.rescanCurrentDirectory();
                    }
                });

        //setMargin( new Insets(1, 1, 1, 1));

        Dimension dim = new Dimension(320, 240);
//        setSize(dim.width, dim.height);
//        setMinimumSize(dim);
//        setMaximumSize(dim);
        setPreferredSize(dim);
    }

    private void initLayout()
    {
        this.setLayout( new BorderLayout() );

        super.add(jScrollPane_Bookmarks,BorderLayout.CENTER);

        JPanel jpanel = new JPanel();
        jpanel.add(jButton_Refresh);
        jpanel.add(jButton_AddBookmarks);
        jpanel.add(jButton_RemoveBookmarks);

        super.add(jpanel,BorderLayout.SOUTH);
    }

    /**
     * @return a valid JButton for refresh/rescan current directory,
     */
    public JButton getRefreshButton()
    {
        return resourcesUtils.getJButton( "reload.gif" );
    }

    /**
     * @return a valid JButton for "Add Bookmark" operation
     */
    public JButton getAddBookmarkButton()
    {
        return resourcesUtils.getJButton( "bookmark-add.png" );
    }

    /**
     * @return a valid JButton for "Remove Bookmark" operation
     */
    public JButton getRemoveBookmarkButton()
    {
        return resourcesUtils.getJButton( "bookmark-remove.gif" );
    }

    @Override // TabbedAccessoryInterface
    public String getTabName()
    {
        // TODO Localization
        return null; //"Bookmark";
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

    /**
    *
    */
   public interface Configurator extends Serializable
   {
       /**
        * @return list of already know bookmarks, must
        * be a list of existing directory File object.
        */
       public Collection<File> getBookmarks();

       /**
        * @param file File to add to bookmarks Collection
        * @return return true if file have been had.
        */
       public boolean addBookmarkFile(File file);

       /**
        * @param file File to remove to bookmark Collection
        * @return return true if file have been removed.
        */
       public boolean removeBookmark(File file);
   }
}

