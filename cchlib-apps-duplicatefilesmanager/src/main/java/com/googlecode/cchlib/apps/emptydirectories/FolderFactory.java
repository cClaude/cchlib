package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import java.nio.file.Path;

public interface FolderFactory 
{
    public Folder createFolder( Path folder );
    public Folder createFolder( File file );

    public EmptyFolder createEmptyFolder( Path folder );
    public EmptyFolder createEmptyFolder( File emptyDirectoryFile );

    public EmptyFolder createCouldBeEmptyFolder( Path folder );
    public EmptyFolder createCouldBeEmptyFolder( File folder );
}
