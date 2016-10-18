package com.googlecode.cchlib.apps.duplicatefiles.console.model;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

/**
 * Convenience version to store couple of value (hash,file,...)
 */
public class HashFiles implements Serializable
{
    private static final long serialVersionUID = 2L;

    private String          hash;
    private TreeSet<File>   files;
    private boolean         deleteAllAllowed;
    private long            length;

    /** Default empty constructor */
    public HashFiles()
    {
        // Empty
    }

    /**
     * Create a new object with a <code>hash<code> and an empty set
     *
     * @param hash Hash for this entry
     * @param length
     */
    public HashFiles( @Nonnull final String hash, final long length )
    {
        this.hash             = hash;
        this.deleteAllAllowed = false;
        this.length           = length;
        this.files            = new TreeSet<>( HashFilesHelper.getComparator() );
    }

    @Override
    public String toString()
    {
        final List<String> paths = this.files.stream().map( f -> f.getPath() ).collect( Collectors.toList() );

        return "" + this.hash + "\t" + paths;
    }

    public String getHash()
    {
        return this.hash;
    }

    public void setHash( final String hash )
    {
        this.hash = hash;
    }

    public Collection<File> getFiles()
    {
        return this.files;
    }

    public void setFiles( final Collection<File> files )
    {
        this.files = new TreeSet<>( HashFilesHelper.getComparator() );
        this.files.addAll( files );
    }

    public boolean isDeleteAllAllowed()
    {
        return this.deleteAllAllowed;
    }

    public void setDeleteAllAllowed( final boolean deleteAllAllowed )
    {
        this.deleteAllAllowed = deleteAllAllowed;
    }

    public long getLength()
    {
        return this.length;
    }

    public void setLength( final long length )
    {
        this.length = length;
    }
}
