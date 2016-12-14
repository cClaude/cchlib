package com.googlecode.cchlib.apps.duplicatefiles.console.model;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

/**
 * Convenience version to store couple of value (hash,file,...)
 */
public class HashFiles extends AbstractHash<File>
{
    private static final long serialVersionUID = 3L;

    private TreeSet<File> files;

    /** Default empty constructor */
    public HashFiles()
    {
        super();
    }

    /**
     * Create a new object with a {@code hash} and an empty set
     *
     * @param hash Hash for this entry
     * @param length
     */
    public HashFiles( @Nonnull final String hash, final long length )
    {
        super( hash, length );

        this.files = new TreeSet<>( ModelHelper.getFileComparator() );
    }

    @Override
    public String toString()
    {
        final List<String> paths = this.files.stream().map( f -> f.getPath() ).collect( Collectors.toList() );

        return "" + this.getHash() + "\t" + paths;
    }

    @Override
    public Collection<File> getFiles()
    {
        return this.files;
    }

    @Override
    public void setFiles( final Collection<File> files )
    {
        this.files = new TreeSet<>( ModelHelper.getFileComparator() );
        this.files.addAll( files );
    }
}
