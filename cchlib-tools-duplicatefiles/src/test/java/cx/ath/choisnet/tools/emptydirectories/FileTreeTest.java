package cx.ath.choisnet.tools.emptydirectories;

import java.io.File;
import java.util.Iterator;
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
public class FileTreeTest
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
        Object[][] tstDatas = {
            { "C:/temps/1/2/3", 5 },
            { "C:/temps"      , 5 },
            { "C:/temps/1/2"  , 5 },
            { "C:/temps/2/1"  , 7 },
            { "C:/temps/2/2"  , 8 },
            { "C:/temps/2/2"  , 8 },/**/
        };

        FileTree tree = new FileTree();

        for( int i = 0; i<tstDatas.length; i++ ) {
            String  fs      = tstDatas[ i ][ 0 ].toString();
            int     size    = Integer.parseInt( tstDatas[ i ][ 1 ].toString() );

            addEntry( tree, fs, size );
            }

        for( File f : tree ) {
            System.out.println( ">" + f );
            }

        System.out.println( "--- TREE ---" );
        displayNode( tree.getRoot(), "" );
        System.out.println( "--- DONE ---" );
    }

    private void addEntry( FileTree tree, String fs, int expectedSize )
    {
        File f = new File( fs );

        if( ! f.exists() ) {
            System.err.println( "WARN: File does not exists : " + f );
            }

        SimpleTreeNode<File> n = tree.lookupNode( f );
        //SimpleTreeNode<File> n2 = tree.lookupNode_( f );
        //Assert.assertEquals( n, n2 );

        System.out.println( ">" + expectedSize + " <-> " + tree.size() + " exists? " + n );
        boolean added = tree.add( f );

        n = tree.lookupNode( f );
        //n2 = tree.lookupNode_( f );
        //Assert.assertEquals( n, n2 );

        System.out.println( "Add>" + added + " - " + f + " exists? " + n );
        System.out.println( ">" + expectedSize + " <-> " + tree.size() );

        Assert.assertEquals( expectedSize, tree.size() );
    }


    private void displayNode( SimpleTreeNode<File> node, String prefix )
    {
        if( node == null ) {
            System.out.println( prefix + "node is NULL" );
            }
        else {
            System.out.println( prefix + "node: " + node );
            System.out.println( prefix + "node data: " + node.getData() );

            Iterator<SimpleTreeNode<File>> iter = node.iterator();

            if( iter.hasNext() ) {
                System.out.println( prefix + "[[" );

                for( SimpleTreeNode<File> n : node ) {
                    displayNode( n, prefix + "  " );
                    }

                System.out.println( prefix + "]]" );
                }
        }
    }

}
