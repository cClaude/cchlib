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
    Collection<File> getBookmarks();

    /**
     * @param file File to add to bookmarks Collection
     * @return true if file have been had.
     */
    boolean addBookmarkFile(File file);

    /**
     * @param file File to remove to bookmark Collection
     * @return true if file have been removed.
     */
    boolean removeBookmark(File file);
  }
