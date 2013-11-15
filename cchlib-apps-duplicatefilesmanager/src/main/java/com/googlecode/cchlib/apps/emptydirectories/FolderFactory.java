package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import java.nio.file.Path;

public interface FolderFactory
{
    Folder createFolder( Path folder );
    Folder createFolder( File file );

    EmptyFolder createEmptyFolder( Path folder );
    EmptyFolder createEmptyFolder( File emptyDirectoryFile );

    EmptyFolder createCouldBeEmptyFolder( Path folder );
    EmptyFolder createCouldBeEmptyFolder( File folder );
}
