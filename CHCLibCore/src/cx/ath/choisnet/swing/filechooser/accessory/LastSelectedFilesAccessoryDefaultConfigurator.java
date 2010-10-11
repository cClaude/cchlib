package cx.ath.choisnet.swing.filechooser.accessory;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * TODO: Doc !
 *
 * @author Claude CHOISNET
 */
public class LastSelectedFilesAccessoryDefaultConfigurator
    implements LastSelectedFilesAccessory.Configurator
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private /*Queue*/LinkedList<File> lastSelectedFiles;
    /** @serial */
    private int maxSelectedFilesSize = 10;

    /**
     *
     */
    public LastSelectedFilesAccessoryDefaultConfigurator()
    {
        this( 10 );
    }

    /**
     *
     * @param maxSelectedFileListSize
     */
    public LastSelectedFilesAccessoryDefaultConfigurator(
            int maxSelectedFileListSize
            )
    {
        this( null, maxSelectedFileListSize );
    }

    /**
     *
     * @param lastSelectedFileList
     * @param maxSelectedFileListSize
     */
    public LastSelectedFilesAccessoryDefaultConfigurator(
            LinkedList<File>    lastSelectedFileList,
            int                 maxSelectedFileListSize
            )
    {
        if( lastSelectedFileList == null) {
            this.lastSelectedFiles = new LinkedList<File>();
        }
        else {
            this.lastSelectedFiles = lastSelectedFileList;
        }
        this.maxSelectedFilesSize = maxSelectedFileListSize;

        purgeQueue(
                this.lastSelectedFiles,
                this.maxSelectedFilesSize
                );
    }

    private static <T> void addToQueue( Queue<T> q, T o, int maxSize )
    {
        q.add( o );

        purgeQueue( q, maxSize );
    }

    private static <T> void purgeQueue( Queue<T> q, int maxSize )
    {
        while( q.size() > maxSize ) {
            q.poll();
        }
    }

    @Override
    public Collection<File> getLastSelectedFiles()
    {
        return lastSelectedFiles;
    }
    @Override
    public boolean addLastSelectedFile( File file )
    {
        String path = file.getPath();

        for(File f:lastSelectedFiles) {
            if(path.equals( f.getPath() )) {
                return false;
            }
        }

        addToQueue(lastSelectedFiles,file,maxSelectedFilesSize);

        return true;
    }
    @Override
    public boolean removeLastSelectedFile( File file )
    {
        boolean         found   = false;
        String          path    = file.getPath();
        Iterator<File>  iter    = lastSelectedFiles.iterator();

        while( iter.hasNext() ) {
            File f = iter.next();

            if( path.equals( f.getPath() ) ) {
                found = true;
                iter.remove();
            }
        }

        return found;
    }
}
