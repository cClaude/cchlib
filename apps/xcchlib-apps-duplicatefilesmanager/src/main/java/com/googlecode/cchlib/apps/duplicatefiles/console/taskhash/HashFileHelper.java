package com.googlecode.cchlib.apps.duplicatefiles.console.taskhash;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;
import com.googlecode.cchlib.util.SortedList;

/**
 * Tools to handles List of {@link HashFile} and List of {@link HashFiles}
 */
public class HashFileHelper
{
    private HashFileHelper()
    {
        // All static
    }

    public static List<HashFiles> convert( //
       final List<HashFile> hashFileList,
       final boolean        verbose
       )
    {
       sortListByHash( hashFileList );

       return convertSorted( hashFileList, verbose );
    }

    private static void sortListByHash( final List<HashFile> list )
    {
       Collections.sort(
           list,
           ( hf1, hf2 ) -> hf1.getHash().compareTo( hf2.getHash() )
           );
    }

    private static List<HashFiles> convertSorted( //
        final List<HashFile> sortedHashFileList,
        final boolean        verbose
        )
    {
        final Map<String,HashFiles> resultSet = new HashMap<>();

        for( final HashFile hashFile : sortedHashFileList ) {
            final String hash       = hashFile.getHash();
            HashFiles    hashFiles  = resultSet.get( hash );
            final long   fileLength = hashFile.getFile().length();

            if( hashFiles == null ) {
                hashFiles = new HashFiles( hash, fileLength );
                resultSet.put( hash, hashFiles );
            }

            if( fileLength == hashFiles.getLength() ) {
                hashFiles.getFiles().add( hashFile.getFile() );
            }
            else if( verbose ) {
                CLIHelper.printMessage(
                        "#Skip (length): " + hashFile.getFile()
                            + " found " + fileLength
                            + " expected " + hashFiles.getLength()
                        );
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
}
