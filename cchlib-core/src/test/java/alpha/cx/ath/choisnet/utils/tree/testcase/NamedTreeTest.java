/**
 *
 */
package alpha.cx.ath.choisnet.utils.tree.testcase;

import java.util.Iterator;
import org.apache.log4j.Logger;
import alpha.com.googlecode.cchlib.utils.VisitResult;
import alpha.com.googlecode.cchlib.utils.Visitor;
import alpha.cx.ath.choisnet.utils.tree.BadRootNameException;
import alpha.cx.ath.choisnet.utils.tree.BinaryTree;
import alpha.cx.ath.choisnet.utils.tree.BinaryTreeNode;
import alpha.cx.ath.choisnet.utils.tree.NamedTree;
import alpha.cx.ath.choisnet.utils.tree.NamedTreeNode;
import junit.framework.TestCase;

/**
 * Test cases for SimpleTree
 * @author Claude CHOISNET
 */
public class NamedTreeTest extends TestCase
{
    private final static Logger slogger = Logger.getLogger( NamedTreeTest.class );
    private int count;

    public void test_errors()
    {
        NamedTree<Integer> tree = new NamedTree<Integer>();

        try {
            //Should crash
            tree.put( 1, (String[])null );
            fail();
        }
        catch( NullPointerException ok ) {
            //Ok
        }
        catch( BadRootNameException e ) {
            fail();
        }
    }

    public void test_add() throws BadRootNameException
    {
        NamedTree<Integer> tree = new NamedTree<Integer>();
        
        tree.put( 1, "root", "pane1" );
        tree.put( 0, "root" );
        tree.put( 2, "root.pane2".split( "\\." ) );
        tree.put( 3, "root", "pane3" );
        tree.put( 4, "root", "pane1", "panel1-1" );
        //Count of tree node.
        int size = tree.size();

        // Replace value (so size should not change)
        tree.put( 40, "root", "pane1", "panel1-1" );
        assertEquals("Size should not change",size,tree.size());
        
        slogger.info( "tree.walk()" );
        count = 0;
        tree.walk( 
            new Visitor<NamedTreeNode<Integer>>()
            {
                @Override
                public VisitResult visite( NamedTreeNode<Integer> entry )
                {
                    count++;
                    displayNode( entry );
                    return VisitResult.CONTINUE;
                }
            });
        assertEquals("bad count",size,count);

        slogger.info( "createInOrderIterator()" );
        count = 0;
        Iterator<BinaryTreeNode<Integer>> iter = BinaryTree.createInOrderIterator( tree.getRoot() );
        
        while( iter.hasNext() ) {
            NamedTreeNode<Integer> entry = (NamedTreeNode<Integer>)iter.next();
            displayNode( entry );
            count++;
        }
        assertEquals("bad count",size,count);

        slogger.info( "createPostOrderIterator()" );
        count = 0;
        iter = BinaryTree.createPostOrderIterator( tree.getRoot() );
        
        while( iter.hasNext() ) {
            NamedTreeNode<Integer> entry = (NamedTreeNode<Integer>)iter.next();
            displayNode( entry );
            count++;
        }
        assertEquals("bad count",size,count);
        
        slogger.info( "createPreOrderIterator()" );
        count = 0;
        iter = BinaryTree.createPreOrderIterator( tree.getRoot() );
        
        while( iter.hasNext() ) {
            NamedTreeNode<Integer> entry = (NamedTreeNode<Integer>)iter.next();
            displayNode( entry );
            count++;
        }
        assertEquals("bad count",size,count);
        
        slogger.info( "walkDepthFirst()" );
        count = 0;
        tree.walkDepthFirst( 
                new Visitor<NamedTreeNode<Integer>>()
                {
                    @Override
                    public VisitResult visite( NamedTreeNode<Integer> entry )
                    {
                        displayNode( entry );
                        count++;
                        return VisitResult.CONTINUE;
                    }
                });
        assertEquals("bad count",size,count);

        count = 0;
    }

    private void displayNode( NamedTreeNode<Integer> n )
    {
        StringBuilder sb = new StringBuilder();

        sb.append( "node:" );
        sb.append( n.getName() );
        sb.append( " - content:" );
        sb.append( n.getData() );
        sb.append( " - path:" );
        sb.append( n.getPath() );

        slogger.info( sb.toString() );
    }
}
