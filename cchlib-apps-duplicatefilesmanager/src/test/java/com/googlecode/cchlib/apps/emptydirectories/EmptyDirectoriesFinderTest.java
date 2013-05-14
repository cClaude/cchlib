package com.googlecode.cchlib.apps.emptydirectories;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream; // $codepro.audit.disable unnecessaryImport
import java.nio.file.Files; // $codepro.audit.disable unnecessaryImport
import java.nio.file.Path;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import com.googlecode.cchlib.nio.file.FilterHelper; // $codepro.audit.disable unnecessaryImport
import com.googlecode.cchlib.util.CancelRequestException;

public class EmptyDirectoriesFinderTest
{
    private final static Logger logger = Logger.getLogger( EmptyDirectoriesFinderTest.class );

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
        logger.info( "testFindDirNoCheckDebug()" );

        MyEmptyDirectoriesListener listener = new MyEmptyDirectoriesListener();

        //File[] rootDirs = { new File( "T:/Data" ) };
        File[] rootDirs = File.listRoots();
        if( rootDirs.length > 1) {
            // Only first root for testing
            rootDirs = new File[]{ rootDirs[0] };
            }

        logger.info( "#################################" );

        MyExcludeDirectoriesFilter filter = new MyExcludeDirectoriesFilter();

        logger.info( "#################################" );
        com.googlecode.cchlib.apps.emptydirectories.file.lookup.DefaultEmptyDirectoriesLookup emptyDirs
            = new com.googlecode.cchlib.apps.emptydirectories.file.lookup.DefaultEmptyDirectoriesLookup( rootDirs );

        emptyDirs.addListener( listener );

        try {
            emptyDirs.lookup( filter );
            }
        catch( CancelRequestException | ScanIOException e ) {
            logger.warn( "testFindDir() - emptyDirs.lookup() - CancelRequestException" );
            }

        logger.info( "#########[FILTER]#######" );
        logger.info( "filter.getPathList().size() = " + filter.getPathList().size() );

        {
            Set<Path> pathSet = filter.createPathSet();
            
            logger.info( "filter.createPathSet().size() = " + pathSet.size() );
            
            assertEquals( filter.getPathList().size(), pathSet.size() );

            for( Path p : filter.getPathList() ) {
                logger.info( "p = " + p );
                }
        }
        
        
        logger.info( "#################################" );
        logger.info( "testFindDir() - done" );
        logger.info( "NEWVersion getNewIsEmptyList()     = " + listener.getIsEmptyList().size() );
        logger.info( "NEWVersion getNewCouldBeEmptyList()= " + listener.getCouldBeEmptyList().size() );
        logger.info( "#################################" );
    }
//
//    @Ignore
//    @Test
//    public void testFindDir()
//    {
//        logger.info( "testFindDir()" );
//
//        MyEmptyDirectoriesListener listener = new MyEmptyDirectoriesListener();
//
//        //File[] rootDirs = { new File( "T:/Data" ) };
//        File[] rootDirs = File.listRoots();
//        if( rootDirs.length > 1) {
//            // Only first root for testing
//            rootDirs = new File[]{ rootDirs[0] };
//            }
//
//        logger.info( "#################################" );
//        com.googlecode.cchlib.apps.emptydirectories.file.lookup.DefaultEmptyDirectoriesLookup oldEmptyDirs
//        = new com.googlecode.cchlib.apps.emptydirectories.file.lookup.DefaultEmptyDirectoriesLookup( rootDirs );
//
//        oldEmptyDirs.addListener( listener );
//        
//        MyExcludeDirectoriesFilter filter = new MyExcludeDirectoriesFilter();
//        
//        try {
//            oldEmptyDirs.lookup( filter );
//            }
//        catch( CancelRequestException e ) {
//            logger.warn( "testFindDir() - oldEmptyDirs.lookup() - CancelRequestException" );
//            }
//
//        logger.info( "#################################" );
//        com.googlecode.cchlib.apps.emptydirectories.path.lookup.DefaultEmptyDirectoriesLookup emptyDirs
//            = new com.googlecode.cchlib.apps.emptydirectories.path.lookup.DefaultEmptyDirectoriesLookup( rootDirs );
//
//        emptyDirs.addListener( listener );
//
//        try {
//            emptyDirs.lookup( filter );
//            }
//        catch( CancelRequestException | ScanIOException e ) {
//            logger.warn( "testFindDir() - emptyDirs.lookup() - CancelRequestException" );
//            }
//
//        logger.info( "#########[FILTER]#######" );
//        logger.info( "filter.getPathList().size() = " + filter.getPathList().size() );
//        logger.info( "filter.getFileList().size() = " + filter.getFileList().size() );
//        {
//            Set<Path> pathSet = filter.createPathSet();
//            Set<File> fileSet = filter.createFileSet();
//            
//            logger.info( "filter.createPathSet().size() = " + pathSet.size() );
//            logger.info( "filter.createFileSet().size() = " + fileSet.size() );
//            
//            assertEquals( filter.getPathList().size(), pathSet.size() );
//            assertEquals( filter.getFileList().size(), fileSet.size() );
//            
//            
//            for( Path p : filter.getPathList() ) {
//                logger.info( "p = " + p );
//                }
//        }
//        
//        
//        logger.info( "#################################" );
//        logger.info( "testFindDir() - done" );
//        logger.info( "NEWVersion getNewIsEmptyList()     = " + listener.getNewIsEmptyList().size() );
//        logger.info( "NEWVersion getNewCouldBeEmptyList()= " + listener.getNewCouldBeEmptyList().size() );
//        logger.info( "OLDVersion size= " + listener.getOldList().size() );
//        logger.info( "#################################" );
//
//        
//        
//        assertEquals( "Sizes does not match", listener.getOldList().size(), listener.getNewIsEmptyList().size() );
//
//        Map<File,EmptyFolder> newVersionMap = new HashMap<>();
//
//        List<File> notFoundInOldVersionList = new SortedList<>();
//        List<File> notFoundInNewVersionList = new SortedList<>();
//
//        for( EmptyFolder newValue : listener.getNewIsEmptyList() ) {
//            assertTrue( newValue.isEmpty() );
//
//            File f = newValue.getPath().toFile();
//
//            if( ! listener.getOldList().contains( f ) ) {
//                // Not found in old version
//                notFoundInOldVersionList.add( f );
//                //logger.info( "notFoundInOldVersionList : " + f );
//                }
//
//            newVersionMap.put( f, newValue );
//            }
//
//        logger.info( "#################################" );
//
//        for( File oldValue : listener.getOldList() ) {
//            final File f = oldValue;
//
//            if( ! newVersionMap.containsKey( f ) ) {
//                // Not found in NEW version
//                logger.info( "notFoundInNewVersionList : " + f );
//                }
//            }
//        logger.info( "#################################" );
//
//        logger.info( "notFoundInNewVersionList size= " + notFoundInNewVersionList.size() );
//        logger.info( "notFoundInOldVersionList size= " + notFoundInOldVersionList.size() );
//    }

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
                logger.info( "entryPath =" + entryPath );

                if( testRecMax < 1 ) {
                    testRec( entryPath, testRecMax + 1);
                    }
                }
            }
        catch( IOException e ) {
            logger.error( "IOException", e );
            }
    }
    
    @Test public void testFake(){}
}
