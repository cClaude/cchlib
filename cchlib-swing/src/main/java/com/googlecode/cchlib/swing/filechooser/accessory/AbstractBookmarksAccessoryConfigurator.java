package com.googlecode.cchlib.swing.filechooser.accessory;

import java.io.File;
import java.util.Collection;

/**
 * Provide a basic implementation for
 * {@link BookmarksAccessoryConfigurator}
 */
public abstract class AbstractBookmarksAccessoryConfigurator
    implements BookmarksAccessoryConfigurator
{
    private static final long serialVersionUID = 2L;

    /**
     * Create AbstractBookmarksAccessoryConfigurator
     */
    public AbstractBookmarksAccessoryConfigurator()
    {
        // empty
    }

    /**
     * Custom method to store bookmarks
     */
    protected abstract void storeBookmarks();

    /**
     * Custom method to add new file in bookmarks
     * @param file File to add
     * @return true if file has been added
     */
    protected abstract boolean add( File file ); // $codepro.audit.disable booleanMethodNamingConvention

    @Override
    public boolean addBookmarkFile( final File file )
    {
        Collection<File> bookmarks = getBookmarks();

        if( ! bookmarks.contains( file ) ) {
            boolean isAdd = add( file );

            if( isAdd ) {
                storeBookmarks();
                }

            return isAdd;
            }
        return false;
    }
}
