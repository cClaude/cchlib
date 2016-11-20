package com.googlecode.cchlib.util.tree;

import java.util.Iterator;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.util.VisitResult;

@SuppressWarnings("boxing")
public class NamedTreeTest
{
    private static final Logger LOGGER = Logger.getLogger( NamedTreeTest.class );
    private int count;

    @Test(expected = NullPointerException.class)
    public void test_errors() throws BadRootNameException
    {
        final NamedTree<Integer> tree = new NamedTree<Integer>();

        tree.put( 1, (String[])null );
    }

    @Test
    public void test_add() throws BadRootNameException
    {
        final NamedTree<Integer> tree = new NamedTree<Integer>();

        tree.put( 1, "root", "pane1" );
        tree.put( 0, "root" );
        tree.put( 2, "root.pane2".split( "\\." ) );
        tree.put( 3, "root", "pane3" );
        tree.put( 4, "root", "pane1", "panel1-1" );

        //Count of tree node.
        final int size = tree.size();

        // Replace value (so size should not change)
        tree.put( 40, "root", "pane1", "panel1-1" );
        Assert.assertEquals("Size should not change",size,tree.size());

        LOGGER.info( "tree.walk()" );
        count = 0;
        tree.walk(
            entry -> {
                count++;
                displayNode( entry );
                return VisitResult.CONTINUE;
            });
        Assert.assertEquals("bad count",size,count);

        LOGGER.info( "createInOrderIterator()" );
        count = 0;
        Iterator<BinaryTreeNode<Integer>> iter = BinaryTree.createInOrderIterator( tree.getRoot() );

        while( iter.hasNext() ) {
            final NamedTreeNode<Integer> entry = (NamedTreeNode<Integer>)iter.next();

            displayNode( entry );
            count++;
            }
        Assert.assertEquals("bad count",size,count);

        LOGGER.info( "createPostOrderIterator()" );
        count = 0;
        iter = BinaryTree.createPostOrderIterator( tree.getRoot() );

        while( iter.hasNext() ) {
            final NamedTreeNode<Integer> entry = (NamedTreeNode<Integer>)iter.next();

            displayNode( entry );
            count++;
            }
        Assert.assertEquals("bad count",size,count);

        LOGGER.info( "createPreOrderIterator()" );
        count = 0;
        iter = BinaryTree.createPreOrderIterator( tree.getRoot() );

        while( iter.hasNext() ) {
            final NamedTreeNode<Integer> entry = (NamedTreeNode<Integer>)iter.next();

            displayNode( entry );
            count++;
            }
        Assert.assertEquals("bad count",size,count);

        LOGGER.info( "walkDepthFirst()" );
        count = 0;
        tree.walkDepthFirst(
                entry -> {
                    displayNode( entry );
                    count++;
                    return VisitResult.CONTINUE;
                });
        Assert.assertEquals("bad count",size,count);

        count = 0;
    }

    private void displayNode( final NamedTreeNode<Integer> node )
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( "node:" );
        sb.append( node.getName() );
        sb.append( " - content:" );
        sb.append( node.getData() );
        sb.append( " - path:" );
        sb.append( node.getPath() );

        LOGGER.info( sb.toString() );
    }
}
