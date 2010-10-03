package cx.ath.choisnet.io;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.LinkedList;
import cx.ath.choisnet.ToDo;

/**
 *
 * @author Claude CHOISNET
 * @see FileFilterHelper
 */
@ToDo(action=ToDo.Action.DOCUMENTATION)
public class FileIterator
    implements  Iterator<File>,
                Iterable<File>
{
    private DirectoryIterator directoryIterator;
    private LinkedList<File>  currentDirFilesList = new LinkedList<File>();
    private FileFilter fileFilter;

    public FileIterator(File rootFolderFile)
    {
        this(rootFolderFile,null,null);
    }

    public FileIterator(File rootFolderFile, FileFilter fileFilter)
    {
        this(rootFolderFile,fileFilter,null);
    }

    public FileIterator(File rootFolderFile, FileFilter fileFilter, FileFilter directoryfileFilter)
    {
        this.directoryIterator = new DirectoryIterator(rootFolderFile,directoryfileFilter);
        this.fileFilter = fileFilter;
    }

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

    public File next()
        throws java.util.NoSuchElementException
    {
        return currentDirFilesList.removeLast();
    }

    public void remove()
        throws java.lang.UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    public Iterator<File> iterator()
    {
        return this;
    }
}
