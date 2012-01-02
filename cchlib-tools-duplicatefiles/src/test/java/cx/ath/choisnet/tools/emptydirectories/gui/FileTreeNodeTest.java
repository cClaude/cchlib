package cx.ath.choisnet.tools.emptydirectories.gui;

import java.io.File;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 *
 */
public class FileTreeNodeTest
{
    private static final Logger logger = Logger.getLogger( FileTreeNodeTest.class );

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
        final File          aFile   = new File( "C:/temps" );
        final FileTreeNode  ftnRoot = new FileTreeNode( aFile.getParentFile() );
        
        logger.info( "ftnRoot.getDepth() = " + ftnRoot.getDepth() );
        logger.info( "ftnRoot.getChildCount() = " + ftnRoot.getChildCount() );
        
        Assert.assertEquals( "Bad root depth", 0, ftnRoot.getDepth() );
        Assert.assertEquals( 0, ftnRoot.getChildCount() );
        Assert.assertEquals( aFile.getParentFile(), ftnRoot.getFile() );
        Assert.assertNull( ftnRoot.getParent() );
        
        final FileTreeNode  ftn = ftnRoot.add( aFile );

        logger.info( "ftn.getDepth() = " + ftn.getDepth() );
        logger.info( "ftn.getChildCount() = " + ftn.getChildCount() );
        
        logger.info( "ftnRoot.getDepth() = " + ftnRoot.getDepth() );
        logger.info( "ftnRoot.getChildCount() = " + ftnRoot.getChildCount() );

        Assert.assertEquals( "Bad node depth", 0, ftn.getDepth() );
        Assert.assertEquals( 0, ftn.getChildCount() );
        Assert.assertEquals( aFile, ftn.getFile() );
        Assert.assertNotNull( ftn.getParent() );
        Assert.assertEquals( ftnRoot, ftn.getParent() );

        Assert.assertEquals( "Bad root depth", 1, ftnRoot.getDepth() );
        Assert.assertEquals( 1, ftnRoot.getChildCount() );
        Assert.assertEquals( aFile.getParentFile(), ftnRoot.getFile() );
        Assert.assertNull( ftnRoot.getParent() );
    }
}
