package com.googlecode.cchlib.swing.filechooser.accessory;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;

/**
 * TODOC
 */
public interface BookmarksAccessoryConfigurator extends Serializable
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