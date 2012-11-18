package com.googlecode.cchlib.apps.emptydirectories;

import java.util.Collection;

/**
 * Find empty directories
 *
 */
public interface EmptyDirectoriesDeleterInterface
    extends EmptyDirectoriesLookup
{
    /**
     *
     */
    public Collection<EmptyFolder> getCollection();

    /**
     *
     */
    public int doAction( EmptyDirectoriesDeleterAction action );

    /**
     *
     */
    public int doDelete( EmptyDirectoriesDeleterActionListener listener );

   /**
    *
    */
    public int doDelete();
}
