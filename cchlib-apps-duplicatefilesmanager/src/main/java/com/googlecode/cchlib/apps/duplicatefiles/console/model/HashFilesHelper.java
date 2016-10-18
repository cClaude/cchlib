package com.googlecode.cchlib.apps.duplicatefiles.console.model;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Tools to handles List of {@link HashFiles}
 */
public class HashFilesHelper
{
    private HashFilesHelper()
    {
        // All static
    }

    public static void sortListByPath( final List<HashFiles> list )
    {
        Collections.sort(
                list,
                ( hf1, hf2 ) -> getFirstFile( hf1 ).compareTo( getFirstFile( hf2 ) )
                );
    }

    private static File getFirstFile( final HashFiles hashFiles )
    {
        final Iterator<File> iter = hashFiles.getFiles().iterator();

        if( iter.hasNext() ) {
            return iter.next();
        }
        throw new IllegalStateException( "Missing value in set" );
    }

    public static Comparator<File> getComparator()
    {
        return (f1,f2) -> f1.compareTo( f2 );
    }
}
