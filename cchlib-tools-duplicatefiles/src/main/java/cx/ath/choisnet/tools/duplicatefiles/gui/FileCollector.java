package cx.ath.choisnet.tools.duplicatefiles.gui;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
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
 * @author Claude CHOISNET
 * @see FileFilterHelper
 * @see DirectoryIterator
 */
public class FileCollector
{
    private ArrayList<File> rootDirectoriesList = new ArrayList<File>();
    private FileFilter directoriesFilter;
    private FileFilter filesFilter;

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

    /**
     * Create a DirectoryIterator started to rootFolderFile,
     * with giving {@link FileFilter} to filter File result.
     *
     * @param rootFolderFile    Root File directory for this Iterator
     * @param fileFilter        File filter to select files (any File object)
     *                          than should be in result (could be null).
     * @throws NullPointerException if rootFolderFile is null
     */
    public FileCollector(
            FileFilter  fileFilter,
            File...     rootFolderFiles
            )
    {
        this(fileFilter,null,rootFolderFiles);
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
     * @throws IllegalArgumentException if rootFolderFile is not a directory
     */
    public FileCollector(
            FileFilter  filesFilter,
            FileFilter  directoriesFilter,
            File...     rootFolderFiles
            )
        throws IllegalArgumentException
    {
        for( File f : rootFolderFiles ) {
            if( !f.isDirectory() ) {
                throw new IllegalArgumentException();
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

        this.filesFilter = filesFilter;
    }

    /**
     *
     * @param event
     */
    public void walk( final FileCollectorEvent event )
    {
        final LinkedList<File> directoriesList = new LinkedList<File>( this.rootDirectoriesList );
        final LinkedList<File> dirToCloseList  = new LinkedList<File>();

        while( directoriesList.size() > 0 ) {
            final File 		dir 	= directoriesList.removeLast();
            final File[]	dirs 	= dir.listFiles( this.directoriesFilter );

            // start reading 'dir'
            event.openDirectory( dir );

            if( dirs != null ) {
                for( File d : dirs ) {
                    // Discover dir f
                    //event.discoverDirectory( f );
                    directoriesList.add( d );
                    }
                }

            final File[] files = dir.listFiles( this.filesFilter );

            if( files != null ) {
                for( File f : files ) {
                    // Discover file f.
                    event.discoverFile( f );
                    }
                }

            //event.closeDirectory( dir );
        }
    }


    public static void main(String...args)
    {
        FileCollector fc = new FileCollector( new File( "C:\\Temps\\D" ) );

        fc.walk( new FileCollectorEvent()
            {
                @Override
                public void openDirectory(File directoryFile)
                {
                    System.out.println(
                        "openDirectory: " + directoryFile
                        );
                }
                @Override
                public void discoverFile(File file)
                {
                    System.out.println(
                        "discoverFile: " + file.length()
                        + "|" + file.lastModified()
                        + "|" + file.getPath()
                        );
                }
                @Override
                public void closeDirectory(File directoryFile)
                {
                    System.out.println( "closeDirectory: " + directoryFile );
                }
            });
    }
}
