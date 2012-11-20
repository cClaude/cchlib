package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.io.File;
import java.util.Iterator;
import javax.swing.JTree;
import org.junit.Assert;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.googlecode.cchlib.apps.emptydirectories.folders.Folder;
import com.googlecode.cchlib.apps.emptydirectories.folders.Folders;

/**
 *
 *
 */
public class FileTreeModelTest
{
    private static final Logger logger = Logger.getLogger( FileTreeModelTest.class );
    //private static final String CONST = "MYCONST";
    
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

//    @Test
//    public void testMisc()
//    {
//        if( CONST != CONST ) {
//            Assert.fail("CONST != CONST");
//            }
//        
//        if( ! myCmp( CONST, CONST ) ) {
//            Assert.fail(" myCmp( CONST, CONST )");
//            }
//        
//        String s1 = CONST;
//        String s2 = CONST;
//        
//        if( ! myCmp( s1, s2 ) ) {
//            Assert.fail(" myCmp( s1, s2 )");
//            }
//    }
    
//    private boolean myCmp( String s1, String s2 )
//    {
//        return s1 == s2;
//    }
    
    @Test
    public void myTest1()
    {
        logger.info( "begin FileTreeModelTest myTest1()" );

        Object[][] tstDatas = {
            { "C:/temps"      , 2 },
        };

        runTst( tstDatas );

        logger.info( "end FileTreeModelTest myTest1()" );
    }

    @Test
    public void myTest2()
    {
        logger.info( "begin FileTreeModelTest myTest2()" );

        Object[][] tstDatas = {
            // Path, expected number of nodes in tree
            { "C:/temps"      , 2 },
            { "C:/temps/1"    , 3 },
            // Don't activate this to have a good test case { "C:/temps/1/2", 4 },
            { "C:/temps/1/2/3", 5 },
            { "C:/temps"      , 5 },
            { "C:/temps/1/2"  , 5 },
            { "C:/temps/2/1"  , 7 },
            { "C:/temps/2/2"  , 8 },
            { "C:/temps/2/2"  , 8 },/**/
        };

        runTst( tstDatas );

        logger.info( "end FileTreeModelTest myTest2()" );
    }

    private void runTst( final Object[][] tstDatas )
    {
        final JTree         jTree       = new JTree();
        final FolderTreeModel treeModel   = new FolderTreeModel( jTree );

        for( int i = 0; i<tstDatas.length; i++ ) {
            final String  filepath = String.class.cast(  tstDatas[ i ][ 0 ] );
            final int     size     = Integer.class.cast( tstDatas[ i ][ 1 ] ).intValue();

            logger.info( ">> add: " + filepath );
            addEntry( treeModel, filepath, size );
            }

        Iterator<FolderTreeNode> iter = treeModel.nodeIterator();

        while( iter.hasNext() ) {
            final FolderTreeNode ftn = iter.next();

            logger.info( ">" + ftn.getFolder() );
            }

        logger.info( "--- TREE ---" );
//        FileTreeNode root = tree.getRootNode();
//        displayNode( root, "" );

        for( final FolderTreeNode rn : treeModel.rootNodes() ) {
            displayNode( rn, "" );
            }

        logger.info( "--- DONE ---" );
    }

    private void addEntry(
            final FolderTreeModel     treeModel,
            final String            fs,
            final int               expectedSize
            )
    {
        File f = new File( fs );

        if( ! f.exists() ) {
            f.mkdirs();

            if( ! f.exists() ) {
                logger.error( "WARN: File does not exists (can not be create): " + f );
                }
            }
        
        Folder          edf = Folders.createFolder( f.toPath() );
        FolderTreeNode   n   = treeModel.lookupNode( edf );

        logger.info( ">" + expectedSize + " <-> " + treeModel.size() + " exists? " + n );

        boolean added = treeModel.add( edf );

        logger.info( "Add>" + added + " - " + f /*+ " exists? " + n*/ );
        logger.info( ">" + expectedSize + " <-> " + treeModel.size() );

        logger.info( "--- TREE ---" );
//        FileTreeNode root = tree.getRootNode();
//        displayNode( root, "" );
        for( FolderTreeNode rn : treeModel.rootNodes() ) {
            displayNode( rn, "" );
            }
        
        Assert.assertEquals( n == null /* not in tree */, added );
        // Not alway true: assertTrue( added );
        Assert.assertEquals( expectedSize, treeModel.size() );
        logger.info( "--- DONE ---" );
    }


    private void displayNode(
            final FolderTreeNode     node,
            final String            prefix
            )
    {
        if( node == null ) {
            logger.info( prefix + "node is NULL" );
            }
        else {
            logger.info( prefix + "node: " + node );
            logger.info( prefix + "node data: " + node.getFolder() );

            Iterator<FolderTreeNode> iter = node.iterator();

            if( iter.hasNext() ) {
                logger.info( prefix + "[[" );

                for( FolderTreeNode n : node ) {
                    displayNode( n, prefix + "  " );
                    }

                logger.info( prefix + "]]" );
                }
        }
    }

}
