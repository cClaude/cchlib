package com.googlecode.cchlib.apps.emptydirectories.file.folder;

import java.io.File;
import java.nio.file.Path;
import com.googlecode.cchlib.apps.emptydirectories.Folder;

public class FileFolder implements Folder
{
    private static final long serialVersionUID = 1L;
    private File file;

    public FileFolder( File emptyDirectoryFile )
    {
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
    public int compareTo( Folder otherFolder )
    {
        FileFolder other = FileFolder.class.cast( otherFolder );

        return this.file.compareTo( other .file );
    }
}
