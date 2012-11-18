package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.io.File;
import org.junit.Assert;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;

/**
 *
 *
 */
public class FileTreeNode2Test
{
    private static final Logger logger = Logger.getLogger( FileTreeNode2Test.class );

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
        final File        aFile_        = new File( "C:/temps" ); 
        final EmptyFolder eEmptyFolder0 = EmptyFolder.createCouldBeEmptyFolder( aFile_.toPath() );
        final EmptyFolder eEmptyFolder1 = EmptyFolder.createCouldBeEmptyFolder( aFile_.getParentFile().toPath() );

        final FileTreeNode2 ftnRoot = new FileTreeNode2( eEmptyFolder1 );
        
        logger.info( "ftnRoot.getDepth() = " + ftnRoot.getDepth() );
        logger.info( "ftnRoot.getChildCount() = " + ftnRoot.getChildCount() );
        
        Assert.assertEquals( "Bad root depth", 0, ftnRoot.getDepth() );
        Assert.assertEquals( 0, ftnRoot.getChildCount() );
        //assertEquals( aFile.getParentFile(), ftnRoot.getFile() );
        assertEquals( eEmptyFolder1, ftnRoot.getFile() );
        Assert.assertNull( ftnRoot.getParent() );
        
        final FileTreeNode2 ftn = ftnRoot.add( eEmptyFolder0 );

        logger.info( "ftn.getDepth() = " + ftn.getDepth() );
        logger.info( "ftn.getChildCount() = " + ftn.getChildCount() );
        
        logger.info( "ftnRoot.getDepth() = " + ftnRoot.getDepth() );
        logger.info( "ftnRoot.getChildCount() = " + ftnRoot.getChildCount() );

        Assert.assertEquals( "Bad node depth", 0, ftn.getDepth() );
        Assert.assertEquals( 0, ftn.getChildCount() );
        assertEquals( eEmptyFolder0, ftn.getFile() );
        Assert.assertNotNull( ftn.getParent() );
        Assert.assertEquals( ftnRoot, ftn.getParent() );

        Assert.assertEquals( "Bad root depth", 1, ftnRoot.getDepth() );
        Assert.assertEquals( 1, ftnRoot.getChildCount() );
        //Assert.assertEquals( aFile.getParentFile(), ftnRoot.getFile() );
        assertEquals( eEmptyFolder1, ftnRoot.getFile() );
        Assert.assertNull( ftnRoot.getParent() );
    }
    
    
    private static void assertEquals(EmptyFolder ef1, EmptyFolder ef2)
    {
        Assert.assertEquals( ef1, ef2 );
    }
}
