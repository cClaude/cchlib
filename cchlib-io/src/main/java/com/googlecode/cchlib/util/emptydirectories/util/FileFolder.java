package com.googlecode.cchlib.util.emptydirectories.util;

import java.io.File;
import java.nio.file.Path;
import com.googlecode.cchlib.util.emptydirectories.Folder;

public class FileFolder implements Folder
{
    private static final long serialVersionUID = 1L;
    private final File file;

    public FileFolder( final File emptyDirectoryFile )
    {
        this.file = emptyDirectoryFile;
    }

    @Override
    public Path getPath()
    {
        return this.file.toPath();
    }

    @Override
    public File getFile()
    {
        return this.file;
    }

    @Override
    public int compareTo( final Folder otherFolder )
    {
        final FileFolder other = FileFolder.class.cast( otherFolder );

        return this.file.compareTo( other.file );
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "FileFolder [file=" );
        builder.append( this.file );
        builder.append( ']' );

        return builder.toString();
    }
}
