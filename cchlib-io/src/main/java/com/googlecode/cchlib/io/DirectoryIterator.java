package com.googlecode.cchlib.io;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * An {@link Iterator} that give all {@link File} directory
 * under a giving file directory.
 * <p>
 * If rootFolderFile is not a directory (is a File, does not
 * exist, can't access,...); then Iterator will return no elements.
 * </p>
 * <p>
 * If rootFolderFile is a directory, it will always the first
 * elements of Iterator (event if not match with {@link FileFilter})
 * </p>
 *
 * @see FileIterator
 */
public class DirectoryIterator // $codepro.audit.disable cloneWithoutCloneable
    implements  Iterator<File>,
                Iterable<File>
{
    private final LinkedList<File> foldersList;
    private FileFilter       directoryFileFilter;

    /**
     * Create a DirectoryIterator starting from rootFolderFile,
     * with no {@link FileFilter}.
     *
     * @param rootFolderFile root File directory for this Iterator
     * @throws NullPointerException if rootFolderFile is null
     */
    public DirectoryIterator(final File rootFolderFile)
    {
        this(rootFolderFile, null );
    }

    /**
     * Create a DirectoryIterator starting from rootFolderFile,
     * with giving {@link FileFilter}.
     *
     * @param rootFolderFile    Root File directory for this Iterator
     * @param directoryFilter   File filter to select directories than should
     *                          be explored and in result (could be null) .
     * @throws NullPointerException if rootFolderFile is null
     */
    public DirectoryIterator(
            final File        rootFolderFile,
            final FileFilter  directoryFilter
            )
    {
        this.foldersList = new LinkedList<>();

        if( directoryFilter == null ) {
            this.directoryFileFilter = FileFilterHelper.trueFileFilter();
            }
        else {
            this.directoryFileFilter = directoryFilter;
            }

        if( rootFolderFile.isDirectory() ) {
            this.foldersList.add(rootFolderFile);
            }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    protected Object clone() throws CloneNotSupportedException // NOSONAR
    {
        throw new CloneNotSupportedException();
    }

    /**
     * Add giving folderFile in internal queue.
     *
     * @param folderFile A valid File directory
     */
    protected void addFolder(final File folderFile)
    {
        this.foldersList.add(folderFile);
    }

    private void addArray(final File folder)
    {
        addFiles(
            folder.listFiles(this.directoryFileFilter)
            );
    }

    private void addFiles(final File[] folderContentFiles)
    {
        if(folderContentFiles != null) {
            for( final File f : folderContentFiles ) {
                if(f.isDirectory()) {
                    this.foldersList.add(f);
                    }
                }
            }
    }

    /**
     * Returns true if the iteration has more directories.
     * (In other words, returns true if next would return
     * an element rather than throwing an exception.)
     * @return true if the iteration has more elements.
     */
    @Override
    public boolean hasNext()
    {
        return this.foldersList.size() > 0;
    }

    /**
     * Returns the next File directory in the iteration.
     * @return the next File directory in the iteration.
     * @throws NoSuchElementException iteration has no more elements.
     */
    @Override
    public File next() throws java.util.NoSuchElementException // NOSONAR
    {
        final File folder = this.foldersList.removeLast();

        addArray(folder);

        return folder;
    }

    /**
     * Unsupported Operation
     *
     * @throws UnsupportedOperationException
     */
    @Override
    public void remove() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over a set of Files.
     * @return this Iterator
     */
    @Override
    public Iterator<File> iterator()
    {
        return this;
    }
}
