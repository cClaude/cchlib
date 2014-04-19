package com.googlecode.cchlib.apps.emptydirectories.path.folders;

import com.googlecode.cchlib.apps.emptydirectories.Folder;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;

@Deprecated
public class PathFolder implements Folder, Serializable, Comparable<Folder>
{
    private static final long serialVersionUID = 1L;
    private Path path;
    //private transient FilePath transientFilePath;

    protected PathFolder( final Path folderPath )
    {
        if( folderPath == null ) {
            throw new IllegalArgumentException();
            }
        this.path = folderPath;
    }

    @Override
    public Path getPath()
    {
        return this.path;
    }

    @Override
    public File getFile()
    {
        return this.path.toFile();
    }

//    public FilePath getFilePath()
//    {
//        if( transientFilePath == null ) {
//            transientFilePath = new FilePath( path, this );
//            }
//
//        return this.transientFilePath;
//    }

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
    public int compareTo( final Folder aEmptyFolder )
    {
        PathFolder other = PathFolder.class.cast( aEmptyFolder );

        return this.path.compareTo( other.path );
    }
}
