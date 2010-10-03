package cx.ath.choisnet.io;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.LinkedList;
import cx.ath.choisnet.ToDo;

/**
 * 
 * @author Claude CHOISNET
 *
 */
@ToDo(action=ToDo.Action.DOCUMENTATION)
public class DirectoryIterator
    implements  Iterator<File>,
                Iterable<File>
{
    private LinkedList<File> foldersList;
    private FileFilter       fileFilter;

    public DirectoryIterator(File rootFolderFile)
    {
        this(rootFolderFile, null );
    }

    public DirectoryIterator(File rootFolderFile, FileFilter fileFilter)
    {
        this.foldersList = new LinkedList<File>();

        if( fileFilter == null ) {
            this.fileFilter  = FileFilterHelper.trueFileFilter();
        }
        else {
            this.fileFilter  = fileFilter;
        }
        
        if( rootFolderFile.isDirectory() ) {
            foldersList.add(rootFolderFile);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException();
    }

    protected void addFolder(File folderFile)
    {
        foldersList.add(folderFile);
    }

    private void addArray(File folder)
    {
        addFiles(folder.listFiles(fileFilter));
    }

    private void addFiles(File[] folderContentFiles)
    {
        if(folderContentFiles != null) {
            for( File f : folderContentFiles ) {
                if(f.isDirectory()) {
                    foldersList.add(f);
                }
            }
        }
    }

    public boolean hasNext()
    {
        return foldersList.size() > 0;
    }

    public File next()
        throws java.util.NoSuchElementException
    {
        File folder = foldersList.removeLast();

        addArray(folder);

        return folder;
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
