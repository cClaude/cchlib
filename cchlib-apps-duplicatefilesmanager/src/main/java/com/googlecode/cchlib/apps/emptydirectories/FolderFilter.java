package com.googlecode.cchlib.apps.emptydirectories;

import java.io.FileFilter;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;

public interface FolderFilter
{
    public DirectoryStream.Filter<Path> toFilter();
    public FileFilter toFileFilter();
}
