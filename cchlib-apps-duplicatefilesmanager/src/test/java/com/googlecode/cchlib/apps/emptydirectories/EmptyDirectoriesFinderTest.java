package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
//import com.googlecode.cchlib.apps.emptydirectories.DefaultEmptyDirectoriesLookup;
//import com.googlecode.cchlib.apps.emptydirectories.EmptyDirectoriesListener;
import com.googlecode.cchlib.util.CancelRequestException;

public class EmptyDirectoriesFinderTest
{
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
    public void testFindDir()
    {
        File[] rootDirs = { new File( "T:/Data" ) };
        DefaultEmptyDirectoriesLookup emptyDirs = new DefaultEmptyDirectoriesLookup( rootDirs );

        EmptyDirectoriesListener listener = new MyEmptyDirectoriesListener();

        emptyDirs.addListener( listener );

        try {
            emptyDirs.lookup();
            }
        catch( CancelRequestException e ) {
            e.printStackTrace();
            }
    }

    static class MyEmptyDirectoriesListener implements EmptyDirectoriesListener
    {
        private boolean isCancel = false;

        @Override
        public boolean isCancel()
        {
            return isCancel;
        }
        @Override
        public void findStarted()
        {
            System.out.println( "start finding" );
        }
        @Override
        public void findDone()
        {
            System.out.println( "find done" );
        }
        @Override
        public void newEntry( EmptyFolder emptyDirectoryFile )
        {
            System.out.println( "addEntry: " + emptyDirectoryFile );
        }
    }
}
