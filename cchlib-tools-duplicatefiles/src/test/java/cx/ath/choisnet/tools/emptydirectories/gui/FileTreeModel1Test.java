package cx.ath.choisnet.tools.emptydirectories.gui;

import java.io.File;
import java.util.Iterator;
import javax.swing.JTree;
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
public class FileTreeModel1Test
{
    private static final Logger logger = Logger.getLogger( FileTreeModel1Test.class );

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
        JTree           jTree   = new JTree();
        FileTreeModel1  tree    = new FileTreeModel1( jTree );

        for( int i = 0; i<tstDatas.length; i++ ) {
            String  fs      = tstDatas[ i ][ 0 ].toString();
            int     size    = Integer.parseInt( tstDatas[ i ][ 1 ].toString() );

            logger.info( ">> add: " + fs );
            addEntry( tree, fs, size );
            }

        Iterator<FileTreeNode> iter = tree.nodeIterator();

        while( iter.hasNext() ) {
            FileTreeNode ftn = iter.next();

            logger.info( ">" + ftn.getFile() );
            }

        logger.info( "--- TREE ---" );
        FileTreeNode root = tree.getRootNode();
        displayNode( root, "" );
        logger.info( "--- DONE ---" );
    }

    private void addEntry( FileTreeModel1 tree, String fs, int expectedSize )
    {
        File f = new File( fs );

        if( ! f.exists() ) {
            logger.error( "WARN: File does not exists : " + f );
            }

        FileTreeNode n = tree.lookupNode( f );

        logger.info( ">" + expectedSize + " <-> " + tree.size() + " exists? " + n );

        boolean added = tree.add( f );

        logger.info( "Add>" + added + " - " + f /*+ " exists? " + n*/ );
        logger.info( ">" + expectedSize + " <-> " + tree.size() );

        logger.info( "--- TREE ---" );
        FileTreeNode root = tree.getRootNode();
        displayNode( root, "" );
        logger.info( "--- DONE ---" );

        Assert.assertEquals( n == null /* not in tree */, added );
        Assert.assertEquals( expectedSize, tree.size() );
    }


    private void displayNode( FileTreeNode node, String prefix )
    {
        if( node == null ) {
            logger.info( prefix + "node is NULL" );
            }
        else {
            logger.info( prefix + "node: " + node );
            logger.info( prefix + "node data: " + node.getFile() );

            Iterator<FileTreeNode> iter = node.iterator();

            if( iter.hasNext() ) {
                logger.info( prefix + "[[" );

                for( FileTreeNode n : node ) {
                    displayNode( n, prefix + "  " );
                    }

                logger.info( prefix + "]]" );
                }
        }
    }

}
