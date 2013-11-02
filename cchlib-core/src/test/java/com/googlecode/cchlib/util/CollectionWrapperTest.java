// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.util;

import java.util.ArrayList;
import java.util.Collection;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class CollectionWrapperTest
{
    private static final Logger logger = Logger.getLogger( CollectionWrapperTest.class );
    private Collection<Integer> collection;
    private Collection<String>  wrapped;

    @Before
    public void before()
    {
        Wrappable<Integer,String> wrapper = new Wrappable<Integer,String>()
        {
            @Override
            public String wrap( Integer o ) throws WrapperException
            {
                return Integer.toString( - o.intValue() );
            }
        };
        Wrappable<String,Integer> unwrapper = new Wrappable<String,Integer>()
        {
            @Override
            public Integer wrap( String o ) throws WrapperException
            {
                return Integer.valueOf( - Integer.parseInt( o ) );
            }
        };
        collection = new ArrayList<Integer>();
        wrapped    = new CollectionWrapper<Integer,String>( collection, wrapper, unwrapper );
    }

    private void addOnCollection( int i )
    {
        collection.add( new Integer( i ) );
    }

    private void addOnCollectionWrapper( int i )
    {
        wrapped.add( Integer.toString( - i ) );
    }

    @Test
    public void testsize1()
    {
        Assert.assertTrue( collection.isEmpty() );
        Assert.assertTrue( wrapped.isEmpty() );
        Assert.assertEquals( 0, collection.size() );
        Assert.assertEquals( 0, wrapped.size() );
        Assert.assertEquals( 0, wrapped.toArray().length );
        Assert.assertEquals( 0, wrapped.toArray( new String[0] ).length );
    }
    @Test
    public void testsize2()
    {
        final int expectedSize = 1;
        addOnCollection( 1 );
        Assert.assertFalse( collection.isEmpty() );
        Assert.assertFalse( wrapped.isEmpty() );
        Assert.assertEquals( expectedSize, collection.size() );
        Assert.assertEquals( expectedSize, wrapped.size() );
        Assert.assertEquals( expectedSize, wrapped.toArray().length );
        Assert.assertEquals( expectedSize, wrapped.toArray( new String[0] ).length );
    }

    @Test
    public void testsize3()
    {
        final int expectedSize = 30;
        for( int i = 0; i<expectedSize; i++ ) {
            addOnCollection( i );
            }
        Assert.assertFalse( collection.isEmpty() );
        Assert.assertFalse( wrapped.isEmpty() );
        Assert.assertEquals( expectedSize, collection.size() );
        Assert.assertEquals( expectedSize, wrapped.size() );
        Assert.assertEquals( expectedSize, wrapped.toArray().length );
        Assert.assertEquals( expectedSize, wrapped.toArray( new String[0] ).length );
    }

    @Test
    public void test_remove_add()
    {
        final int expectedSize = 30;
        for( int i = 0; i<expectedSize; i++ ) {
            addOnCollection( i );
            }

        int count = 0;
        for( String k : wrapped ) {
            logger.info( "k = " + k );
            count++;
            }
        Assert.assertEquals( expectedSize, wrapped.size() );
        Assert.assertEquals( expectedSize, count );

        // remove entry on mkw : will be removed in map has well
        Assert.assertTrue( collection.contains( new Integer( 5 ) ) );
        Assert.assertTrue( wrapped.contains( "-5" ) );
        wrapped.remove( "-5" );

        Assert.assertEquals( expectedSize - 1, wrapped.size() );
        Assert.assertEquals( collection.size(), wrapped.size() );
        Assert.assertFalse( collection.contains( new Integer( 5 ) ) );
        Assert.assertFalse( wrapped.contains( "-5" ) );

        // remove entry on collection : will be removed in mkw has well
        Assert.assertTrue( collection.contains( new Integer( 8 ) ) );
        Assert.assertTrue( wrapped.contains( "-8" ) );
        collection.remove( new Integer( 8 ) );

        Assert.assertEquals( expectedSize - 2, wrapped.size() );
        Assert.assertEquals( collection.size(), wrapped.size() );
        Assert.assertFalse( collection.contains( new Integer( 8 ) ) );
        Assert.assertFalse( wrapped.contains( "-8" ) );

        // add entry on mkv : will be added in collection has well
        Assert.assertFalse( collection.contains( new Integer( 1000 ) ) );
        Assert.assertFalse( wrapped.contains( "-1000" ) );
        addOnCollectionWrapper( 1000 );

        Assert.assertEquals( expectedSize - 1, wrapped.size() );
        Assert.assertEquals( collection.size(), wrapped.size() );
        Assert.assertTrue( collection.contains( new Integer( 1000 ) ) );
        Assert.assertTrue( wrapped.contains( "-1000" ) );
    }

}
