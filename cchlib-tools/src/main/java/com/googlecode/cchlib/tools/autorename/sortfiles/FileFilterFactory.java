package com.googlecode.cchlib.tools.autorename.sortfiles;

import java.io.FileFilter;

public interface FileFilterFactory
{
    public FileFilter createFileFilter( String[] extensions );
}
