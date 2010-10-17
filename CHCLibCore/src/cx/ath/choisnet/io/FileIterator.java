package cx.ath.choisnet.io;

import java.io.File;
import java.io.FileFilter;
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
 * @author Claude CHOISNET
 * @see FileFilterHelper
 * @see DirectoryIterator
 */
public class FileIterator
    implements  Iterator<File>,
                Iterable<File>
{
    private DirectoryIterator directoryIterator;
    private LinkedList<File>  currentDirFilesList = new LinkedList<File>();
    private FileFilter fileFilter;

    /**
     * Create a FileIterator started to rootFolderFile
     * 
     * @param rootFolderFile root File directory for this Iterator
     * @throws NullPointerException if rootFolderFile is null
     */
    public FileIterator(File rootFolderFile)
    {
        this(rootFolderFile,null,null);
    }

    /**
     * Create a DirectoryIterator started to rootFolderFile,
     * with giving {@link FileFilter} to filter File result.
     * 
     * @param rootFolderFile    Root File directory for this Iterator
     * @param fileFilter        File filter to select files (any File object)
     *                          than should be in result (could be null).
     * @throws NullPointerException if rootFolderFile is null
     */
    public FileIterator(
            File        rootFolderFile, 
            FileFilter  fileFilter
            )
    {
        this(rootFolderFile,fileFilter,null);
    }

    /**
     * Create a DirectoryIterator started to rootFolderFile,
     * with giving {@link FileFilter}.
     * 
     * @param rootFolderFile    Root File directory for this Iterator
     * @param fileFilter        File filter to select files (any File object)
     *                          than should be in result (could be null).
     * @param directoryFilter   File filter to select directories than should
     *                          be explored (could be null) . 
     * @throws NullPointerException if rootFolderFile is null
     */
    public FileIterator(
            File        rootFolderFile,
            FileFilter  fileFilter,
            FileFilter  directoryFilter
            )
    {
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
    public boolean hasNext()
    {
        if(currentDirFilesList.size() > 0) {
            return true;
        }
        else if( directoryIterator.hasNext() ) {
            File   dir     = directoryIterator.next();
            File[] content = dir.listFiles(this.fileFilter);

            if(content != null) {
                for( File f : content ) {
                    currentDirFilesList.add(f);
                }
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
    public File next() throws NoSuchElementException
    {
        // Initialize currentDirFilesList
        // in case of direct call next()
        // without calling hasNext()
        hasNext();

        return currentDirFilesList.removeLast();
    }

    /**
     * Unsupported Operation
     * 
     * @throws UnsupportedOperationException
     */
    public void remove() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over a set of Files. 
     * @return this Iterator
     */
    public Iterator<File> iterator()
    {
        return this;
    }
}
