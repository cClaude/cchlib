package com.googlecode.cchlib.apps.emptydirectories.path.folders;

import java.io.File;
import java.nio.file.Path;
import com.googlecode.cchlib.util.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.util.emptydirectories.Folder;
import com.googlecode.cchlib.util.emptydirectories.util.EmptyFolderType;
import com.googlecode.cchlib.util.emptydirectories.util.FolderFactory;

/**
 * @deprecated no replacement
 */
@Deprecated
public class PathFolderFactory implements FolderFactory
{
    public PathFolderFactory()
    {
        // empty
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
    public EmptyFolder createCouldBeEmptyFolder( final Path folder )
    {
        return new PathEmptyFolder( folder, EmptyFolderType.CONTAINT_ONLY_EMPTY_FOLDERS );
    }

    @Override
    public EmptyFolder createCouldBeEmptyFolder( final File folder )
    {
        return createCouldBeEmptyFolder( folder.toPath() );
    }
}
