package com.googlecode.cchlib.io;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;

/**
 *
 * @since 4.1.8
 */
public class FileIterable implements Iterable<File>
{
    private File        rootFolderFile;
    private FileFilter  fileFilter;
    private FileFilter  directoryFilter;

    /**
     * Create a FileIterable starting from rootFolderFile
     *
     * @param rootFolderFile root File directory for this Iterator
     * @throws NullPointerException if rootFolderFile is null
     */
    public FileIterable(File rootFolderFile)
    {
        this(rootFolderFile,null,null);
    }

    /**
     * Create a FileIterable starting from rootFolderFile,
     * with giving {@link FileFilter} to filter File result.
     *
     * @param rootFolderFile    Root File directory for this Iterator
     * @param fileFilter        File filter to select files (any File object)
     *                          than should be in result (could be null).
     * @throws NullPointerException if rootFolderFile is null
     */
    public FileIterable(
            File        rootFolderFile,
            FileFilter  fileFilter
            )
    {
        this(rootFolderFile,fileFilter,null);
    }

    /**
     * Create a FileIterator starting from rootFolderFile,
     * with giving {@link FileFilter}.
     *
     * @param rootFolderFile    Root File directory for this Iterator
     * @param fileFilter        File filter to select files (any File object)
     *                          than should be in result (could be null).
     * @param directoryFilter   File filter to select directories than should
     *                          be explored (could be null) .
     * @throws NullPointerException if rootFolderFile is null
     * @throws IllegalArgumentException if rootFolderFile is not a directory
     */
    public FileIterable(
        final File rootFolderFile,
        final FileFilter fileFilter,
        final FileFilter directoryFilter
        )
    {
        if( !rootFolderFile.isDirectory() ) {
            throw new IllegalArgumentException();
            }

        this.rootFolderFile  = rootFolderFile;
        this.fileFilter      = fileFilter;
        this.directoryFilter = directoryFilter;
    }

    @Override
    public Iterator<File> iterator()
    {
        return new FileIterator( rootFolderFile, fileFilter, directoryFilter );
    }
}
