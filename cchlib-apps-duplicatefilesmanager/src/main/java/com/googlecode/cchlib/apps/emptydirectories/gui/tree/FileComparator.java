package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.io.File;
import java.nio.file.Path;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;

class FileComparator
{
    public static boolean equals( 
        final File        file, 
        final EmptyFolder emptyFolder 
        )
    {
        final Path emptyFolderPath = emptyFolder.getPath();
        final Path filePath        = file.toPath();

        return filePath.equals( emptyFolderPath );
    }

    public static boolean equals(
        final EmptyFolder emptyFolder, 
        final File        file 
        )
    {
        return equals( file, emptyFolder );
    }

    public static boolean equals(
        final FileTreeNode2 fileTreeNode, 
        final File          file
        )
    {
        return equals( fileTreeNode.getFile(), file );
    }
    
}