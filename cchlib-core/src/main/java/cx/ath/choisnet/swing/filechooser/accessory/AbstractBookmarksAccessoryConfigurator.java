package cx.ath.choisnet.swing.filechooser.accessory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Provide a basic implementation for
 * {@link BookmarksAccessory.Configurator}
 */
public abstract class AbstractBookmarksAccessoryConfigurator
    implements BookmarksAccessory.Configurator
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private /*List*/ArrayList<File> bookmarks;

    /**
     *
     * @param bookmarkFiles
     */
    public AbstractBookmarksAccessoryConfigurator(
            final List<File> bookmarkFiles
            )
    {
        this.bookmarks = new ArrayList<File>( bookmarkFiles );
    }

    /**
     * TODO: Doc!
     * @param list
     * @param file
     * @return
     */
    protected static boolean add(
        final List<File>    list, 
        final File          file 
        )
    {
        if( file.isDirectory() ) {
            boolean found = false;

            for(File f:list) {
                if( f.getPath().equals( file.getPath() ) ) {
                    found = true;
                }
            }

            if( !found ) {
                list.add( file );
                return true;
            }
        }
        return false;
    }

    /**
     * Custom method to store content of ArrayList
     *
     * @param filesList {@link ArrayList} of {@link File} objects.
     */
    protected abstract void storeBookmarks( ArrayList<File> filesList );

    /**
     * {@inheritDoc}
     * 
     * @return a sorted array of {@link File} objects
     */
    @Override
    public Collection<File> getBookmarks()
    {
        // be sure content is sorted
        Collections.sort( bookmarks );
        
        return bookmarks;
    }

    @Override
    public boolean addBookmarkFile( File file )
    {
        if( ! bookmarks.contains( file ) ) {
            boolean isAdd = add(bookmarks,file);

            if( isAdd ) {
                storeBookmarks( bookmarks );
            }

            return isAdd;
        }
        return false;
    }

    @Override
    public boolean removeBookmark( File file )
    {
        bookmarks.remove( file );

        storeBookmarks( bookmarks );

        return true;
    }
}
