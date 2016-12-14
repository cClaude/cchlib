package com.googlecode.cchlib.apps.duplicatefiles.console.model;

import java.io.Serializable;
import java.util.Collection;
import javax.annotation.Nonnull;

/**
 * Convenience version to store couple of value (hash,length,...)
 */
abstract class AbstractHash<T extends Comparable<T>> implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String  hash;
    private boolean deleteAllAllowed;
    private long    length;

    /** Default empty constructor */
    AbstractHash()
    {
        // Empty
    }

    /**
     * Create a new object with a {@code hash} and an empty set
     *
     * @param hash Hash for this entry
     * @param length Length for this hash
     */
    AbstractHash( @Nonnull final String hash, final long length )
    {
        this.hash             = hash;
        this.deleteAllAllowed = false;
        this.length           = length;
    }

    public abstract Collection<T> getFiles();
    public abstract void setFiles( final Collection<T> files );

    public final String getHash()
    {
        return this.hash;
    }

    public final void setHash( final String hash )
    {
        this.hash = hash;
    }

    public final boolean isDeleteAllAllowed()
    {
        return this.deleteAllAllowed;
    }

    public void setDeleteAllAllowed( final boolean deleteAllAllowed )
    {
        this.deleteAllAllowed = deleteAllAllowed;
    }

    public final long getLength()
    {
        return this.length;
    }

    public final void setLength( final long length )
    {
        this.length = length;
    }
}
