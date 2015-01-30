package com.googlecode.cchlib.apps.emptydirectories.path.lookup;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.AbstractEmptyDirectoriesLookup;
import com.googlecode.cchlib.apps.emptydirectories.EmptyDirectoriesListener;
import com.googlecode.cchlib.apps.emptydirectories.EmptyDirectoriesLookup;
import com.googlecode.cchlib.apps.emptydirectories.Folders;
import com.googlecode.cchlib.apps.emptydirectories.ScanIOException;
import com.googlecode.cchlib.lang.Enumerable;
import com.googlecode.cchlib.nio.file.FilterHelper;
import com.googlecode.cchlib.util.CancelRequestException;


/**
 * Find empty directories solution base on {@link FileFilter}
 */
public class DefaultEmptyDirectoriesLookup
    extends AbstractEmptyDirectoriesLookup<Filter<Path>>
        implements EmptyDirectoriesLookup<Filter<Path>>, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( DefaultEmptyDirectoriesLookup.class );

    private final List<Path> rootFilesForScan;
    private DirectoryStream.Filter<Path> excludeDirectories;
    private final LinkOption[] linkOption = new LinkOption[0];

    /**
     * Create an {@link DefaultEmptyDirectoriesLookup} object.
     * <br>
     * Does not start search of empty directories, you must
     * call {@link #lookup()} to start this task.
     *
     * @param rootFiles Array of root {@link File} objects
     */
    public DefaultEmptyDirectoriesLookup( final File...rootFiles )
    {
        this.rootFilesForScan = new ArrayList<>( rootFiles.length );

        for( final File f: rootFiles ) {
            this.rootFilesForScan.add( f.toPath() );
            }
    }

    /**
     * Create an {@link DefaultEmptyDirectoriesLookup} object.
     * <br>
     * Does not start search of empty directories, you must
     * call {@link #lookup()} to start this task.
     *
     * @param rootPaths Array of root {@link Path} objects
     */
    public DefaultEmptyDirectoriesLookup( final Path...rootPaths )
    {
        this.rootFilesForScan = new ArrayList<>( rootPaths.length );

        for( final Path p: rootPaths ) {
            this.rootFilesForScan.add( p );
            }
    }

    /**
     * Create an {@link DefaultEmptyDirectoriesLookup} object.
     *
     * @param rootFiles {@link Enumerable} of root {@link File} objects
     */
    public DefaultEmptyDirectoriesLookup( final Enumerable<File> rootFiles )
    {
        this.rootFilesForScan = new ArrayList<>();

        final Enumeration<File> enumeration = rootFiles.enumeration();

        while( enumeration.hasMoreElements() ) {
            this.rootFilesForScan.add( enumeration.nextElement().toPath() );
            }
    }


    /**
     * Clear previous list and compute current list of empty directories
     * (should be call at least once)
     * @throws CancelRequestException if any listeners ask to cancel operation
     * @throws ScanIOException
     */
    @Override
    public void lookup() throws CancelRequestException, ScanIOException
    {
        lookup( FilterHelper.falseFilter() );
    }

    @Override
    public void lookup( @Nonnull final Filter<Path> filter ) throws CancelRequestException, ScanIOException
    {
        for( final EmptyDirectoriesListener l : getListeners() ) {
            l.findStarted();
            }

        this.excludeDirectories = filter;

        for( final Path p : this.rootFilesForScan ) {
            if( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "lookup start from : " + p );
                }

            //if( f.isDirectory() ) {
            if( Files.isDirectory( p, this.linkOption ) ) {
                doScan( p );
                }
            }

        for( final EmptyDirectoriesListener l : getListeners() ) {
            l.findDone();
            }
    }

    /**
     * Launch scan for this folder.
     * @param folder Folder file to scan
     * @throws CancelRequestException if any listeners ask to cancel operation
     * @throws ScanIOException
     */
    private void doScan( final Path folderPath ) throws CancelRequestException, ScanIOException
    {
        if( folderPath.toFile().isFile() ) {
            throw new IllegalStateException( "Not a directory: " + folderPath );
            }

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "doScan:" + folderPath );
            }

        canBeEmpty( folderPath );
    }

    private boolean canBeEmpty( final Path folder ) throws CancelRequestException, ScanIOException
    {
        assert Files.isDirectory( folder, this.linkOption ) : "Not a directory=" + folder;


        if( canBeScan( folder ) ) {
            for( final EmptyDirectoriesListener l : getListeners() ) {
                if( l.isCancel() ) {
                    throw new CancelRequestException();
                    }
                }

            return scanSubFolders( folder );
            }
        else {
            return false;
            }
    }

    private boolean canBeScan( final Path folder ) throws ScanIOException // $codepro.audit.disable blockDepth
    {
        if( this.excludeDirectories != null ) {
            try {
                if( this.excludeDirectories.accept( folder ) ) {
                    if( LOGGER.isDebugEnabled() ) {
                        LOGGER.debug( "Ignore: " + folder );
                        }
                    // Ignore this directory
                    return false;
                    }
                }
            catch( final IOException e ) {
                throw new ScanIOException( e );
                }
            }

        return true;
    }

    private boolean scanSubFolders( final Path folder ) // $codepro.audit.disable booleanMethodNamingConvention
            throws CancelRequestException, ScanIOException
    {
        boolean isEmpty      = true;
        boolean couldBeEmpty = true;

        if( Files.isSymbolicLink( folder ) ) {
            // Do not follow symbolic links
            return false;
        }

        try( DirectoryStream<Path> stream = Files.newDirectoryStream( folder/*, this.directoriesFilter*/ ) ) {

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "check if empty: " + folder );
                }

            for( final Path entryPath : stream ) {
                isEmpty = false;

                if( entryPath.toFile().isDirectory() ) {
                    couldBeEmpty = canBeEmpty( entryPath );
                    }
                else {
                    couldBeEmpty = false;
                    }

                if( ! couldBeEmpty ) {
                    break;
                    }
                }

            if( couldBeEmpty ) {
                if( isEmpty ) {
                    assert folder.toFile().listFiles( (final File pathname) -> false).length == 0;

                    notify( Folders.createEmptyFolder( folder ) );
                    }
                else {
                    assert folder.toFile().list().length != 0;

                    notify( Folders.createCouldBeEmptyFolder( folder ) );
                    }
                return true;
                }
            else {
                return false;
                }
            }
        catch( final NotDirectoryException e ) {
            if( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "Not a directory : " + folder, e );
                }

            return false;
            }
        catch( final AccessDeniedException e ) {
            if( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "Denied access: " + folder, e );
                }

            return false;
            }
        catch( final IOException e ) { // stream.close()
            // I/O error encounted during the iteration, the cause is an IOException
            if( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "Can not read: " + folder, e );
                }

            return false;
            }
    }
}
