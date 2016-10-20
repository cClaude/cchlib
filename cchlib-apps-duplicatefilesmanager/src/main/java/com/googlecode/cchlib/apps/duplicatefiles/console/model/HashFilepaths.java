package com.googlecode.cchlib.apps.duplicatefiles.console.model;

import java.util.Collection;
import java.util.TreeSet;
import javax.annotation.Nonnull;

/**
 * Convenience version to store couple of value (hash,file,...)
 */
public class HashFilepaths extends AbstractHash<String>
{
    private static final long serialVersionUID = 1L;

    private TreeSet<String> files;

    /** Default empty constructor */
    public HashFilepaths()
    {
        super();
    }

    /**
     * Create a new object with a <code>hash<code> and an empty set
     *
     * @param hash Hash for this entry
     * @param length
     */
    public HashFilepaths( @Nonnull final String hash, final long length )
    {
        super( hash, length );

        this.files = new TreeSet<>( ModelHelper.getPathComparator() );
    }

    @Override
    public String toString()
    {
        return "" + this.getHash() + "\t" + this.files;
    }

    @Override
    public Collection<String> getFiles()
    {
        return this.files;
    }

    @Override
    public void setFiles( final Collection<String> files )
    {
        this.files = new TreeSet<>( ModelHelper.getPathComparator() );
        this.files.addAll( files );
    }
}
