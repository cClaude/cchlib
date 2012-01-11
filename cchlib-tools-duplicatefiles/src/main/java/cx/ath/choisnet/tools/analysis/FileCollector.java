package cx.ath.choisnet.tools.analysis;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import cx.ath.choisnet.io.DirectoryIterator;
import cx.ath.choisnet.io.FileFilterHelper;

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
 */
public class FileCollector
{
    private ArrayList<File> rootDirectoriesList = new ArrayList<File>();
    private FileFilter directoriesFilter;
    private FileFilter filesFilter;
	private boolean cancel	= false;
	private boolean running	= false;
	private CancelState cancelState;

    /**
     * Create a FileCollector able to inspect rootFolderFiles
     *
     * @throws NullPointerException if rootFolderFile is null
     * @param rootFolderFiles List of root files directory
     */
    public FileCollector(File...rootFolderFiles)
    {
        this(null,null,rootFolderFiles);
    }

    /* *
     * Create a DirectoryIterator started to rootFolderFile,
     * with giving {@link FileFilter} to filter File result.
     *
     * @param rootFolderFile    Root File directory for this Iterator
     * @param fileFilter        File filter to select files (any File object)
     *                          than should be in result (could be null).
     * @throws NullPointerException if rootFolderFile is null
     * /
    public FileCollector(
            FileFilter  fileFilter,
            File...     rootFolderFiles
            )
    {
        this(fileFilter,null,rootFolderFiles);
    }*/

    /**
     * Create a DirectoryIterator started to rootFolderFile,
     * with giving {@link FileFilter}.
     *
     * @param rootFolderFile    Root File directory for this Iterator
     * @param directoryFilter   File filter to select directories than should
     *                          be explored (could be null) .
     * @param fileFilter        File filter to select files (any File object)
     *                          than should be in result (could be null).
     * @throws NullPointerException if rootFolderFile is null
     * @throws IllegalArgumentException if rootFolderFile is not a directory
     */
    public FileCollector(
            final FileFilter  directoriesFilter,
            final FileFilter  filesFilter,
            final File...     rootFolderFiles
            )
        throws IllegalArgumentException
    {
        for( File f : rootFolderFiles ) {
            if( !f.isDirectory() ) {
            	if( f.isFile() ) {
                    throw new IllegalArgumentException( "Received file instead directory: " + f );
            		}
                }
            this.rootDirectoriesList.add( f );
            }

        Collections.reverse( rootDirectoriesList );

        if( directoriesFilter != null ) {
            this.directoriesFilter = FileFilterHelper.and(
                FileFilterHelper.directoryFileFilter(),
                directoriesFilter
                );
            }
        else {
            this.directoriesFilter = FileFilterHelper.directoryFileFilter();
            }

        if( filesFilter != null ) {
            this.filesFilter = FileFilterHelper.and(
            		FileFilterHelper.not(
            			FileFilterHelper.directoryFileFilter()
            			),
                    directoriesFilter
                    );   	
        	}
        else {
            this.filesFilter = FileFilterHelper.not(
        			FileFilterHelper.directoryFileFilter()
        			);
        	}
    }

    /**
     *
     * @param event
     */
    public void walk( final FileCollectorVisitor event )
    {
    	this.cancel			= false;
    	this.running 	 	= true;
    	this.cancelState 	= null;

        final LinkedList<File> rootList = new LinkedList<File>( this.rootDirectoriesList );
        final LinkedList<File> dirsList = new LinkedList<File>( this.rootDirectoriesList );

        while( rootList.size() > 0 || dirsList.size() > 0 ) {
            final File dir;
            
            if( dirsList.size() > 0 ) {
            	dir = dirsList.removeLast();
            	
                // start reading 'dir'
                event.openDirectory( dir );
            	}
            else {
            	dir = rootList.removeLast();
            	
                // start reading 'dir'
                event.openRootDirectory( dir );
            	}
            final File[] dirs = dir.listFiles( this.directoriesFilter );

            if( dirs != null ) {
                for( File d : dirs ) {
                    // Discover dir d
                	dirsList.add( d );
                    }
                }

            final File[] files = dir.listFiles( this.filesFilter );

            if( files != null ) {
                for( File f : files ) {
                    // Discover file f
                    event.discoverFile( f );
                    }
                }
            
            if( cancel ) {
            	// Store current state.
            	this.cancelState = new CancelState( rootList, dirsList );
            	break;
            	}
        	}
        
    	this.running = false;
   }
    
    public void cancel()
    {
    	this.cancel = true;
    }
    
    public boolean isRunning()
    {
    	return this.running;
    }
    
    public CancelState getCancelState()
    {
    	return this.cancelState;
    }
    
    /**
     * 
     *
     */
    public class CancelState implements Serializable
    {
		private static final long serialVersionUID = 1L;
		private LinkedList<File> rootList;
    	private LinkedList<File> dirsList;
    	
    	/**
    	 * 
    	 * @param rootList
    	 * @param dirsList
    	 */
    	protected CancelState(
    		final LinkedList<File> rootList,
    		final LinkedList<File> dirsList
    		)
    	{
    		this.rootList = rootList;
    		this.dirsList = dirsList;
    	}

		/**
		 * @return the rootList
		 */
		public List<File> getRootList()
		{
			return rootList;
		}

		/**
		 * @param rootList the rootList to set
		 */
		public void setRootList( final List<File> rootList) 
		{
			this.rootList = new LinkedList<File>( rootList );
		}

		/**
		 * @return the dirsList
		 */
		public List<File> getDirsList() 
		{
			return dirsList;
		}

		/**
		 * @param dirsList the dirsList to set
		 */
		public void setDirsList( final List<File> dirsList)
		{
			this.dirsList = new LinkedList<File>( dirsList );
		}
    }
}
