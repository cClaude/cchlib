package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import java.nio.file.Path;
import com.googlecode.cchlib.apps.emptydirectories.file.folder.FileFolderFactory;

public class Folders
{
    private static FolderFactory INSTANCE = new FileFolderFactory();

    public static Folder createFolder( File file )
    {
        return INSTANCE.createFolder( file );
    }

    public static EmptyFolder createEmptyFolder( File file )
    {
        return INSTANCE.createEmptyFolder( file );
    }

    public static Folder createFolder( Path path )
    {
        return INSTANCE.createFolder( path );
    }

    public static EmptyFolder createEmptyFolder( Path path )
    {
        return INSTANCE.createEmptyFolder( path );
    }

    public static EmptyFolder createCouldBeEmptyFolder( Path path )
    {
        return INSTANCE.createCouldBeEmptyFolder( path );
    }

    public static EmptyFolder createCouldBeEmptyFolder( File file )
    {
        return INSTANCE.createCouldBeEmptyFolder( file );
    }
}
