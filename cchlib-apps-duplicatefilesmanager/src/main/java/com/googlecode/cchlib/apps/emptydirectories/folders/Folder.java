package com.googlecode.cchlib.apps.emptydirectories.folders;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;

/**
 * TODOC
 */
public class Folder implements Serializable, Comparable<Folder>
{
    private static final long serialVersionUID = 1L;
    private Path path;
    private transient FilePath filePath;

    protected Folder( final Path folder )
    {
        this.path = folder;
    }
    
    public Path getPath()
    {
        return this.path;
    }
    
    public FilePath getFilePath()
    {
        if( filePath == null ) {
            filePath = new FilePath( path, this );
            }
        
        return this.filePath;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if( this == obj ) return true;
        if( obj == null ) return false;
        if( !(obj instanceof Folder) ) return false;
        Folder other = (Folder)obj;
        if( path == null ) {
            if( other.path != null ) return false;
        } else if( !path.equals( other.path ) ) return false;
        return true;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "Folder [path=" );
        builder.append( path );
        builder.append( ']' );
        return builder.toString();
    }

    public int compareToPath( final Path path )
    {
        return this.path.compareTo( path );
    }

    public int compareToFile( final File file )
    {
        return this.path.compareTo( file.toPath() );
    }

    @Override
    public int compareTo( final Folder other )
    {
        return this.path.compareTo( other.path );
    }

}
