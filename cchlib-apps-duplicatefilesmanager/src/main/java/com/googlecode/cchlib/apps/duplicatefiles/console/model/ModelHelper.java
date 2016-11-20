package com.googlecode.cchlib.apps.duplicatefiles.console.model;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Tools to handles List of {@link HashFiles}
 */
public class ModelHelper
{
    private ModelHelper()
    {
        // All static
    }

    public static <T extends Comparable<T>> void sortListByPath( final List<AbstractHash<T>> list )
    {
        Collections.sort(
                list,
                ( hf1, hf2 ) -> getFirstFile( hf1 ).compareTo( getFirstFile( hf2 ) )
                );
    }

    private static <T extends Comparable<T>> T getFirstFile( final AbstractHash<T> hashFiles )
    {
        return getFirstEntry( hashFiles.getFiles() );
    }

    private static <T> T getFirstEntry( final Collection<T> collection )
    {
        final Iterator<T> iter = collection.iterator();

        if( iter.hasNext() ) {
            return iter.next();
        }
        throw new IllegalStateException( "Missing value in set" );
    }

    public static Comparator<File> getFileComparator()
    {
        return (f1,f2) -> f1.compareTo( f2 );
    }

    public static Comparator<String> getPathComparator()
    {
        return (f1,f2) -> f1.compareTo( f2 );
    }
}
