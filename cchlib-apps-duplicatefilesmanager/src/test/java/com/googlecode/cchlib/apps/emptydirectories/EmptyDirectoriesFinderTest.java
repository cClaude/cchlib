package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream; // $codepro.audit.disable unnecessaryImport
import java.nio.file.Files; // $codepro.audit.disable unnecessaryImport
import java.nio.file.Path;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.nio.file.FilterHelper; // $codepro.audit.disable unnecessaryImport
import com.googlecode.cchlib.util.CancelRequestException;

public class EmptyDirectoriesFinderTest
{
    private static final Logger LOGGER = Logger.getLogger( EmptyDirectoriesFinderTest.class );

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testFindDirNoCheckDebug()
    {
        LOGGER.info( "testFindDirNoCheckDebug()" );

        MyEmptyDirectoriesListener listener = new MyEmptyDirectoriesListener();

//      File[] rootDirs = File.listRoots();
//      if( rootDirs.length > 1) {
//          // Only first root for testing
//          rootDirs = new File[]{ rootDirs[0] };
//          } TODO check why this loop on unix (using path /proc)
        File[] rootDirs = new File[]{ FileHelper.getUserHomeDirFile() };

        LOGGER.info( "#################################" );

        MyExcludeDirectoriesFilter filter = new MyExcludeDirectoriesFilter();

        LOGGER.info( "#################################" );
        com.googlecode.cchlib.apps.emptydirectories.file.lookup.DefaultEmptyDirectoriesLookup emptyDirs
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
            Set<Path> pathSet = filter.createPathSet();

            LOGGER.info( "filter.createPathSet().size() = " + pathSet.size() );

            Assert.assertEquals( filter.getPathList().size(), pathSet.size() );

            for( Path p : filter.getPathList() ) {
                LOGGER.info( "p = " + p );
                }
        }


        LOGGER.info( "#################################" );
        LOGGER.info( "testFindDir() - done" );
        LOGGER.info( "NEWVersion getNewIsEmptyList()     = " + listener.getIsEmptyList().size() );
        LOGGER.info( "NEWVersion getNewCouldBeEmptyList()= " + listener.getCouldBeEmptyList().size() );
        LOGGER.info( "#################################" );
    }

    @Test @Ignore
    public void testRec()
    {
        File[] rootDirs = File.listRoots();
        Path   folder   = rootDirs[0].toPath();

        testRec( folder, 0 );
    }

    private void testRec( Path folder, int testRecMax )
    {
        try( DirectoryStream<Path> stream = Files.newDirectoryStream( folder, FilterHelper.newDirectoriesFilter() ) ) {
            for( Path entryPath : stream ) {
                LOGGER.info( "entryPath =" + entryPath );

                if( testRecMax < 1 ) {
                    testRec( entryPath, testRecMax + 1);
                    }
                }
            }
        catch( IOException e ) {
            LOGGER.error( "IOException", e );
            }
    }

    @Test public void testFake(){}
}
