package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import java.nio.file.Path;
import com.googlecode.cchlib.apps.emptydirectories.file.folder.FileFolderFactory;

public class Folders
{
    private static FolderFactory factory = new FileFolderFactory();

    public static Folder createFolder( File file )
    {
        return factory.createFolder( file );
    }

    public static EmptyFolder createEmptyFolder( File file )
    {
        return factory.createEmptyFolder( file );
    }
    
    public static Folder createFolder( Path path )
    {
        return factory.createFolder( path );
    }

    public static EmptyFolder createEmptyFolder( Path path )
    {
        return factory.createEmptyFolder( path );
    }

    public static EmptyFolder createCouldBeEmptyFolder( Path path )
    {
        return factory.createCouldBeEmptyFolder( path );
    }
    
    public static EmptyFolder createCouldBeEmptyFolder( File file )
    {
        return factory.createCouldBeEmptyFolder( file );
    }
}
