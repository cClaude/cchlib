package com.googlecode.cchlib.apps.emptydirectories.file.folder;

import java.io.File;
import java.nio.file.Path;
import com.googlecode.cchlib.apps.emptydirectories.AbstractEmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolderType;
import com.googlecode.cchlib.apps.emptydirectories.Folder;

public class FileEmptyFolder extends AbstractEmptyFolder// implements EmptyFolder
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
        return file.toPath();
    }

    @Override
    public File getFile()
    {
        return file;
    }

    @Override
    public int compareTo( final Folder otherFolder )
    {
        final FileEmptyFolder other = FileEmptyFolder.class.cast( otherFolder );

        return this.file.compareTo( other.file );
    }
}
