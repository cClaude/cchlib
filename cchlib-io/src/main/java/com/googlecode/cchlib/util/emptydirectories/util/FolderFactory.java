package com.googlecode.cchlib.util.emptydirectories.util;

import java.io.File;
import java.nio.file.Path;
import com.googlecode.cchlib.util.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.util.emptydirectories.Folder;

public interface FolderFactory
{
    Folder createFolder( Path folder );
    Folder createFolder( File file );

    EmptyFolder createEmptyFolder( Path folder );
    EmptyFolder createEmptyFolder( File emptyDirectoryFile );

    EmptyFolder createCouldBeEmptyFolder( Path folder );
    EmptyFolder createCouldBeEmptyFolder( File folder );
}
