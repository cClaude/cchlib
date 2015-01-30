package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File; // $codepro.audit.disable unnecessaryImport
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore; // $codepro.audit.disable unnecessaryImport
import org.junit.Test;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.nio.file.FilterHelper;
import com.googlecode.cchlib.util.CancelRequestException; // $codepro.audit.disable unnecessaryImport

public class EmptyDirectoriesFinderTest
{
    private static final Logger LOGGER = Logger.getLogger( EmptyDirectoriesFinderTest.class );

    @Test
    public void testFindDirNoCheckDebug()
    {
        LOGGER.info( "testFindDirNoCheckDebug()" );

        final MyEmptyDirectoriesListener listener = new MyEmptyDirectoriesListener();

//      File[] rootDirs = File.listRoots();
//      if( rootDirs.length > 1) {
//          // Only first root for testing
//          rootDirs = new File[]{ rootDirs[0] };
//          } TODO check why this loop on unix (using path /proc)
        final File[] rootDirs = new File[]{ FileHelper.getUserHomeDirFile() };

        LOGGER.info( "#################################" );

        final MyExcludeDirectoriesFilter filter = new MyExcludeDirectoriesFilter();

        LOGGER.info( "#################################" );
        final com.googlecode.cchlib.apps.emptydirectories.file.lookup.DefaultEmptyDirectoriesLookup emptyDirs
            = new com.googlecode.cchlib.apps.emptydirectories.file.lookup.DefaultEmptyDirectoriesLookup( rootDirs );

        emptyDirs.addListener( listener );

        try {
            emptyDirs.lookup( filter );
            }
        catch( CancelRequestException | ScanIOException e ) { // $codepro.audit.disable logExceptions
            LOGGER.warn( "testFindDir() - emptyDirs.lookup() - CancelRequestException" );
            }

        LOGGER.info( "#########[FILTER]#######" );
        LOGGER.info( "filter.getPathList().size() = " + filter.getPathList().size() );

        {
            final Set<Path> pathSet = filter.createPathSet();

            LOGGER.info( "filter.createPathSet().size() = " + pathSet.size() );

            Assert.assertEquals( filter.getPathList().size(), pathSet.size() );

            for( final Path p : filter.getPathList() ) {
                LOGGER.info( "p = " + p );
                }
        }

        LOGGER.info( "#################################" );
        LOGGER.info( "testFindDir() - done" );
        LOGGER.info( "NEWVersion getNewIsEmptyList()     = " + listener.getIsEmptyList().size() );
        LOGGER.info( "NEWVersion getNewCouldBeEmptyList()= " + listener.getCouldBeEmptyList().size() );
        LOGGER.info( "#################################" );
    }

    @Test
    @Ignore // this test is very long to run, and it's not a test... :(
    public void testRec()
    {
        final File[] rootDirs = File.listRoots();
        final Path   folder   = rootDirs[0].toPath();

        testRec( folder, 0 );
    }

    private void testRec( final Path folder, final int testRecMax )
    {
        try( DirectoryStream<Path> stream = Files.newDirectoryStream( folder, FilterHelper.newDirectoriesFilter() ) ) {
            for( final Path entryPath : stream ) {
                LOGGER.info( "entryPath =" + entryPath );

                if( testRecMax < 1 ) {
                    testRec( entryPath, testRecMax + 1);
                    }
                }
            }
        catch( final IOException e ) {
            LOGGER.error( "IOException", e );
            }
    }

    @Test public void testFake(){}
}
