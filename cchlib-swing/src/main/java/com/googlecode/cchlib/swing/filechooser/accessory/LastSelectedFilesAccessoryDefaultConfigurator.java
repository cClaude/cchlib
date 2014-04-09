package com.googlecode.cchlib.swing.filechooser.accessory;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Provide a basic implementation for
 * {@link LastSelectedFilesAccessoryConfigurator}
 */
public class LastSelectedFilesAccessoryDefaultConfigurator
    implements LastSelectedFilesAccessoryConfigurator
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private /*Queue*/LinkedList<File> lastSelectedFiles;
    /** @serial */
    private int maxSelectedFilesSize = 10;
    /** @serial */
    private boolean autoApproveSelection;

    /**
     * Create a {@link LastSelectedFilesAccessoryConfigurator}
     * initialized with an empty list of files limited to 10 values.
     * <BR>
     * Double-click auto approve selection.
     * <BR>
     * This constructor is provide to have a quick solution
     * when writing your program, but if you want that
     * the "last selected files list" persist between
     * different launchings of your application, you should
     * consider to use a stored list.
     */
    public LastSelectedFilesAccessoryDefaultConfigurator()
    {
        this( null, 10, true );
    }

    /**
     * Create a {@link LastSelectedFilesAccessoryConfigurator}
     * initialized with your list of files limited to
     * maxSelectedFileListSize values.
     * <BR>
     * if list contain more than maxSelectedFileListSize
     * values, oldest extra files  are removed (presume
     * that oldest values are at the beginning of the list).
     *
     * @param lastSelectedFileList    List of last selected Files
     * @param maxSelectedFileListSize Max selected files to keep in list
     * @param autoApproveSelection    How to handle double-click
     */
    public LastSelectedFilesAccessoryDefaultConfigurator(
            LinkedList<File>    lastSelectedFileList,
            int                 maxSelectedFileListSize,
            boolean             autoApproveSelection
            )
    {
        if( lastSelectedFileList == null) {
            this.lastSelectedFiles = new LinkedList<File>();
        }
        else {
            this.lastSelectedFiles = lastSelectedFileList;
        }
        this.maxSelectedFilesSize = maxSelectedFileListSize;
        this.autoApproveSelection = autoApproveSelection;

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
    @Override
    public boolean getAutoApproveSelection()
    {
        return autoApproveSelection;
    }
}
