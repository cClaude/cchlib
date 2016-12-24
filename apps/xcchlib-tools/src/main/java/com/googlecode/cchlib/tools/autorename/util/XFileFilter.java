package com.googlecode.cchlib.tools.autorename.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class XFileFilter
{
    private final FileFilter fileFilter;

    XFileFilter( final FileFilter fileFilter )
    {
        this.fileFilter = fileFilter;
    }

    public Collection<File> getFiles( final File file )
    {
        final File[] files = file.listFiles( this.fileFilter );

        if( files == null ) {
            return Collections.emptyList();
        }

        final Collection<File> filterFiles = new ArrayList<>();

        for( final File f : files ) {
            if( this.fileFilter.accept( f ) ) {
                filterFiles.add( f );
                }
        }

        return filterFiles;
    }
}
