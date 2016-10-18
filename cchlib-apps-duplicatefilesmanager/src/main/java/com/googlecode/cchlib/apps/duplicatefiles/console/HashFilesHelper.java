package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.googlecode.cchlib.util.SortedList;

public class HashFilesHelper {

    private HashFilesHelper()
    {
        // All static
    }

    /**
     *
     * @param sortedDuplicates
     * @param verbose
     * @return
     */
    public static List<HashFiles> convert( //
        final List<HashFile> sortedDuplicates,
        final boolean        verbose
        )
    {
        final Map<String,HashFiles> resultSet = new HashMap<>();

        for( final HashFile hashFile : sortedDuplicates ) {
            final String hash = hashFile.getHash();

            HashFiles hashFiles = resultSet.get( hash );

            if( hashFiles == null ) {
                hashFiles = new HashFiles( hash, hashFile.getFile().length() );
                resultSet.put( hash, hashFiles );
            }

            if( hashFile.getFile().length() == hashFiles.getLength() ) {
                hashFiles.getFiles().add( hashFile.getFile() );
            } else if( verbose ) {
                CLIHelper.printMessage( "#Skip (length): " + hashFile.getFile() );
            }
        }

        return convertAndSort( resultSet );
    }

    private static List<HashFiles> convertAndSort( final Map<String, HashFiles> resultSet )
    {
        final List<HashFiles> resultList = new SortedList<>(
                (hf1,hf2) -> hf1.getHash().compareTo( hf2.getHash() )
                );
        resultList.addAll( resultSet.values() );
        return resultList;
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
