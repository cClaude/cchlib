package com.googlecode.cchlib.apps.emptydirectories.file.folder;

import java.io.File;
import java.nio.file.Path;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolderType;
import com.googlecode.cchlib.apps.emptydirectories.Folder;
import com.googlecode.cchlib.apps.emptydirectories.FolderFactory;

/**
 * Create a Folder or an EmptyFolder base on {@link Path} or on a {@link File}
 */
public class FileFolderFactory implements FolderFactory
{
    public FileFolderFactory()
    {
        //All static
    }

    @Override 
    public Folder createFolder( final Path folder )
    {
        return createFolder( folder.toFile() );
    }

    @Override
    public Folder createFolder( final File file )
    {
        return new FileFolder( file );
    }

    @Override 
    public EmptyFolder createEmptyFolder( final Path folder )
    {
        return createEmptyFolder( folder.toFile() );
    }
    
    @Override
    public EmptyFolder createEmptyFolder( File folder )
    {
        return new FileEmptyFolder( folder, EmptyFolderType.IS_EMPTY );
    }
    
    @Override
    public EmptyFolder createCouldBeEmptyFolder( final Path folder )
    {
        return createCouldBeEmptyFolder( folder.toFile() );
    }

    @Override
    public EmptyFolder createCouldBeEmptyFolder( final File folder )
    {
        return new FileEmptyFolder( folder, EmptyFolderType.CONTAINT_ONLY_EMPTY_FOLDERS );
    }

}
