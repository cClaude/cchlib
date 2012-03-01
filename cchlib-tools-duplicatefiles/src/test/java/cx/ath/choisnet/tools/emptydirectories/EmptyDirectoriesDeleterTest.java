package cx.ath.choisnet.tools.emptydirectories;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import cx.ath.choisnet.util.CancelRequestException;

public class EmptyDirectoriesDeleterTest
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
        File[] rootDirs = { new File( "T:/Data/2011-12-06" ) };
        EmptyDirectoriesDeleter emptyDirs = new EmptyDirectoriesDeleter( rootDirs );

        EmptyDirectoriesListener listener = new EmptyDirectoriesFinderTest.MyEmptyDirectoriesListener();

        emptyDirs.addListener( listener );

        try {
            emptyDirs.lookup();
            System.out.println( "size : " + emptyDirs.getCollection().size() );

            int c = emptyDirs.doDelete();

            System.out.println( "delete count : " + c );
            System.out.println( "size : " + emptyDirs.getCollection().size() );
            }
        catch( CancelRequestException e ) {
            // OK.
            e.printStackTrace();
            }
    }

}
