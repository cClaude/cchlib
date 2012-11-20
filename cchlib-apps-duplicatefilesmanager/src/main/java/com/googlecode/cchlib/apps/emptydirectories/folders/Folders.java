package com.googlecode.cchlib.apps.emptydirectories.folders;

import java.io.File;
import java.nio.file.Path;

/**
 * TODOC
 */
public class Folders
{
    private Folders()
    {
        //All static
    }

    public static Folder createFolder( final Path folder )
    {
        return new Folder( folder );
    }

    public static Folder createFolder( final File file )
    {
        return createFolder( file.toPath() );
    }

    public static EmptyFolder createEmptyFolder( final Path folder )
    {
        return new EmptyFolder( folder, EmptyFolder.EFType.IS_EMPTY );
    }

    public static EmptyFolder createCouldBeEmptyFolder( final Path folder )
    {
        return new EmptyFolder( folder, EmptyFolder.EFType.CONTAINT_ONLY_EMPTY_FOLDERS );
    }

}
