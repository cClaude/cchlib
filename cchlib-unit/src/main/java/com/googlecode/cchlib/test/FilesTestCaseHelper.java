package com.googlecode.cchlib.test;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.io.FileFilterHelper;
import com.googlecode.cchlib.io.FileIterator;
import com.googlecode.cchlib.io.SerializableFileFilter;

/**
 * Provide some tools to build test cases
 */
public final class FilesTestCaseHelper
{
    private FilesTestCaseHelper()
    {//All static
    }

    /**
     * Returns a {@link File} {@link Iterator} from given directory (directories
     * will not be include in result)
     * @param fileDirectory file directory to explore
     * @param fileFilter    file filter for result files
     *                      (does not filter directories,
     *                      could be null).
     * @return {@link File} {@link Iterator} from given directory
     */
    public static final Iterator<File> getFilesFrom(
        final File       fileDirectory,
        final FileFilter fileFilter
        )
    {
        final SerializableFileFilter justFileFilter = FileFilterHelper.not(
                    FileFilterHelper.directoryFileFilter()
                    );
        final FileFilter privateFileFilter;

        if( fileFilter == null ) {
            privateFileFilter = justFileFilter;
            }
        else {
            final SerializableFileFilter sfileFilter = fileFilter::accept;

            privateFileFilter = FileFilterHelper.and(
                    justFileFilter,
                    sfileFilter
                    );
            }

        return new FileIterator(
                fileDirectory,
                privateFileFilter
                );
    }

    /**
     * Returns a {@link File} {@link List} from given directory (directories
     * will not be include in result)
     * @param fileDirectory file directory to explore
     * @param fileFilter    file filter for result files
     *                      (does not filter directories,
     *                      could be null).
     * @return {@link File} {@link List} from given directory
     * @since 4.1.7
     * @deprecated use {@link #getFiles(File, FileFilter)} instead
     */
    @Deprecated
    public static final List<File> getFilesListFrom(
        final File       fileDirectory,
        final FileFilter fileFilter
        )
    {
        final List<File>        list = new ArrayList<>();
        final Iterator<File>    iter = getFilesFrom( fileDirectory, fileFilter );

        while( iter.hasNext() ) {
            list.add( iter.next() );
            }

        return list;
    }

    /**
     * Returns a {@link Iterable} {@link File} from given directory (directories
     * will not be include in result)
     * @param fileDirectory file directory to explore
     * @param fileFilter    file filter for result files
     *                      (does not filter directories,
     *                      could be null).
     * @return {@link Iterable} {@link File} from given directory
     * @since 4.2
     */
    public static final Iterable<File> getFiles(
        final File       fileDirectory,
        final FileFilter fileFilter
        )
    {
        return () -> getFilesFrom( fileDirectory, fileFilter );
    }
}
