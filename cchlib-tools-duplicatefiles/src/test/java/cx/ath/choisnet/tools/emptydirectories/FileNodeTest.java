package cx.ath.choisnet.tools.emptydirectories;

import java.io.File;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 *
 */
public class FileNodeTest
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
    public void myTest()
    {
        File        f   = new File( "C:/temps/1/2/3" );
        FileNode    fn  = new FileNode( f );

        System.out.println( "fn= " + fn );

        Assert.assertEquals( 5, fn.size() );
        Assert.assertEquals( f, fn.getFile() );
        
        int count = 0;
        
        for( int i = 0; i<fn.size(); i++ ) {
            System.out.println( "1. f(" + i + ")=" + fn.getFile( i ) );
            count++;
            }

        Assert.assertEquals( fn.size(), count );
        count = 0;
        
        for( File fp : fn ) {
            System.out.println( "2. f[" + count + "]=" + fp );
            count++;
            }

        Assert.assertEquals( fn.size(), count );
        count = 0;
    }

}
