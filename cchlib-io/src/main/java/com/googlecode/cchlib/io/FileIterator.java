package com.googlecode.cchlib.io;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * An {@link Iterator} that give all {@link File}
 * under a giving file directory.
 * <p>
 * If rootFolderFile is not a directory (is a File, does not
 * exist, can't access,...); then Iterator will return no elements.
 * </p>
 * <p>
 * If rootFolderFile is <b>not</b> a element return by this Iterator.
 * </p>
 *
 * @see FileFilterHelper
 * @see DirectoryIterator
 * @see FileIterable
 */
public class FileIterator implements  Iterator<File>
{
    private final DirectoryIterator directoryIterator;
    private final LinkedList<File>  currentDirFilesList = new LinkedList<>();
    private final FileFilter        fileFilter;

    /**
     * Create a FileIterator starting from rootFolderFile
     *
     * @param rootFolderFile root File directory for this Iterator
     * @throws NullPointerException if rootFolderFile is null
     */
    public FileIterator(final File rootFolderFile)
    {
        this(rootFolderFile,null,null);
    }

    /**
     * Create a FileIterator starting from rootFolderFile,
     * with giving {@link FileFilter} to filter File result.
     *
     * @param rootFolderFile    Root File directory for this Iterator
     * @param fileFilter        File filter to select files (any File object)
     *                          than should be in result (could be null).
     * @throws NullPointerException if rootFolderFile is null
     */
    public FileIterator(
            final File        rootFolderFile,
            final FileFilter  fileFilter
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
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public FileIterator(
            final File        rootFolderFile,
            final FileFilter  fileFilter,
            final FileFilter  directoryFilter
            )
        throws IllegalArgumentException
    {
        if( !rootFolderFile.isDirectory() ) {
            throw new IllegalArgumentException();
            }

        this.directoryIterator = new DirectoryIterator(
                                        rootFolderFile,
                                        directoryFilter
                                        );
        this.fileFilter = fileFilter;
    }

    /**
     * Returns true if the iteration has more files.
     * (In other words, returns true if next would return
     * an element rather than throwing an exception.)
     * @return true if the iteration has more elements.
     */
    @Override
    public boolean hasNext()
    {
        if( this.currentDirFilesList.size() > 0 ) {
            return true;
            }
        else if( this.directoryIterator.hasNext() ) {
            final File   dir     = this.directoryIterator.next();
            final File[] content = dir.listFiles(this.fileFilter);

            if( content != null ) {
                this.currentDirFilesList.addAll( Arrays.asList( content ) );
                //for( File f : content ) {
                    //currentDirFilesList.add(f);
                    //}
                }
            return hasNext();
            }
        else {
            return false;
            }
    }

    /**
     * Returns the next File in the iteration.
     * @return the next File in the iteration.
     * @throws NoSuchElementException iteration has no more elements.
     */
    @Override
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public File next() throws NoSuchElementException
    {
        // Initialize currentDirFilesList
        // in case of direct call next()
        // without calling hasNext()
        hasNext();

        return this.currentDirFilesList.removeLast();
    }

    /**
     * Unsupported Operation
     *
     * @throws UnsupportedOperationException
     */
    @Override
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public void remove() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }
}
