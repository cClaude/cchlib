package com.googlecode.cchlib.util.emptydirectories.util;

import java.io.File;
import java.nio.file.Path;
import com.googlecode.cchlib.util.emptydirectories.Folder;

public class FileEmptyFolder extends AbstractEmptyFolder
{
    private static final long serialVersionUID = 1L;
    private final File file;

    protected FileEmptyFolder( final File emptyDirectoryFile, final EmptyFolderType type )
    {
        super( type );

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
        final FileEmptyFolder other = FileEmptyFolder.class.cast( otherFolder );

        return this.file.compareTo( other.file );
    }
}
