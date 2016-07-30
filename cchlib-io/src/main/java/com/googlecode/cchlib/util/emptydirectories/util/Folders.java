package com.googlecode.cchlib.util.emptydirectories.util;

import java.io.File;
import java.nio.file.Path;
import com.googlecode.cchlib.util.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.util.emptydirectories.Folder;

public class Folders
{
    private static final FolderFactory INSTANCE = new FileFolderFactory();

    private Folders()
    {
        // All static
    }

    public static Folder createFolder( final File file )
    {
        return INSTANCE.createFolder( file );
    }

    public static EmptyFolder createEmptyFolder( final File file )
    {
        return INSTANCE.createEmptyFolder( file );
    }

    public static Folder createFolder( final Path path )
    {
        return INSTANCE.createFolder( path );
    }

    public static EmptyFolder createEmptyFolder( final Path path )
    {
        return INSTANCE.createEmptyFolder( path );
    }

    public static EmptyFolder createCouldBeEmptyFolder( final Path path )
    {
        return INSTANCE.createCouldBeEmptyFolder( path );
    }

    public static EmptyFolder createCouldBeEmptyFolder( final File file )
    {
        return INSTANCE.createCouldBeEmptyFolder( file );
    }
}
