package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.io.File;
import java.nio.file.Path;
import com.googlecode.cchlib.apps.emptydirectories.folders.FilePath;
import com.googlecode.cchlib.apps.emptydirectories.folders.Folder;

class FileComparator
{
    public static boolean equals( 
        final File      file, 
        final Folder    folder 
        )
    {
        final Path filePath   = file.toPath();
        final Path folderPath = folder.getPath();

        return filePath.equals( folderPath );
    }

    public static boolean equals(
        final Folder folder, 
        final File   file 
        )
    {
        return equals( file, folder );
    }

    public static boolean equals(
        final FolderTreeNode fileTreeNode, 
        final File           file
        )
    {
        return equals( fileTreeNode.getFolder(), file );
    }

    public static boolean equals(
        final Path   path, 
        final Folder folder
        )
    {
        final Path folderPath = folder.getPath();

        return path.equals( folderPath );
    }

    public static boolean equals( 
        final Folder   folder,
        final FilePath filePath
        )
    {
        final Path folderPath     = folder.getPath();
        final Path filePathPath   = filePath.getFile().toPath();

        return folderPath.equals( filePathPath );
    }
    
}