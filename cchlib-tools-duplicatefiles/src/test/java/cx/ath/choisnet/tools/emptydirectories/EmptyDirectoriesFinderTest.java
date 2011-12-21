package cx.ath.choisnet.tools.emptydirectories;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import cx.ath.choisnet.util.CancelRequestException;

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
        EmptyDirectoriesFinder emptyDirs = new EmptyDirectoriesFinder( rootDirs );

        EmptyDirectoriesListener listener = new MyEmptyDirectoriesListener();

        emptyDirs.addListener( listener );

        try {
            emptyDirs.find();
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
        public void newEntry( File emptyDirectoryFile )
        {
            System.out.println( "addEntry: " + emptyDirectoryFile );
        }
    }
}
