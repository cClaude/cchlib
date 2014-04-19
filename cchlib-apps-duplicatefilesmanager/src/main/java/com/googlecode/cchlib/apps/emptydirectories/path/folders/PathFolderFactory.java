package com.googlecode.cchlib.apps.emptydirectories.path.folders;

import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolderType;
import com.googlecode.cchlib.apps.emptydirectories.Folder;
import com.googlecode.cchlib.apps.emptydirectories.FolderFactory;
import java.io.File;
import java.nio.file.Path;

@Deprecated
public class PathFolderFactory implements FolderFactory
{
    public PathFolderFactory()
    {
    }

    @Override
    public Folder createFolder( final Path folder )
    {
        return new PathFolder( folder );
    }

    @Override
    public Folder createFolder( final File file )
    {
        return createFolder( file.toPath() );
    }

    @Override
    public EmptyFolder createEmptyFolder( final Path folder )
    {
        return new PathEmptyFolder( folder, EmptyFolderType.IS_EMPTY );
    }

    @Override
    public EmptyFolder createEmptyFolder( final File folder )
    {
        return new PathEmptyFolder( folder.toPath(), EmptyFolderType.IS_EMPTY );
    }

    @Override
    public EmptyFolder createCouldBeEmptyFolder( Path folder )
    {
        return new PathEmptyFolder( folder, EmptyFolderType.CONTAINT_ONLY_EMPTY_FOLDERS );
    }

    @Override
    public EmptyFolder createCouldBeEmptyFolder( File folder )
    {
        return createCouldBeEmptyFolder( folder.toPath() );
    }
}
