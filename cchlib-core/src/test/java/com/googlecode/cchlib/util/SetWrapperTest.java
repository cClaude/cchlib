// $codepro.audit.disable numericLiterals, reusableImmutables
package com.googlecode.cchlib.util;

import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class SetWrapperTest
{
    private static final Logger LOGGER = Logger.getLogger( SetWrapperTest.class );

    private Set<Integer> set;
    private Set<String>  wrapped;

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
        set       = new HashSet<Integer>();
        wrapped   = new SetWrapper<Integer,String>( set, wrapper, unwrapper );
    }

    private void addOnSet( int i )
    {
        set.add( new Integer( i ) );
    }

    private void addOnSetWrapper( int i )
    {
        wrapped.add( Integer.toString( - i ) );
    }

    @Test
    public void testsize1()
    {
        Assert.assertTrue( set.isEmpty() );
        Assert.assertTrue( wrapped.isEmpty() );
        Assert.assertEquals( 0, set.size() );
        Assert.assertEquals( 0, wrapped.size() );
        Assert.assertEquals( 0, wrapped.toArray().length );
        Assert.assertEquals( 0, wrapped.toArray( new String[0] ).length );
    }
    @Test
    public void testsize2()
    {
        final int expectedSize = 1;
        addOnSet( 1 );
        Assert.assertFalse( set.isEmpty() );
        Assert.assertFalse( wrapped.isEmpty() );
        Assert.assertEquals( expectedSize, set.size() );
        Assert.assertEquals( expectedSize, wrapped.size() );
        Assert.assertEquals( expectedSize, wrapped.toArray().length );
        Assert.assertEquals( expectedSize, wrapped.toArray( new String[0] ).length );
    }

    @Test
    public void testsize3()
    {
        final int expectedSize = 30;
        for( int i = 0; i<expectedSize; i++ ) {
            addOnSet( i );
            }
        Assert.assertFalse( set.isEmpty() );
        Assert.assertFalse( wrapped.isEmpty() );
        Assert.assertEquals( expectedSize, set.size() );
        Assert.assertEquals( expectedSize, wrapped.size() );
        Assert.assertEquals( expectedSize, wrapped.toArray().length );
        Assert.assertEquals( expectedSize, wrapped.toArray( new String[0] ).length );
    }

    @Test
    public void test_remove_add()
    {
        final int expectedSize = 30;
        for( int i = 0; i<expectedSize; i++ ) {
            addOnSet( i );
            }

        int count = 0;
        for( String k : wrapped ) {
            LOGGER.info( "k = " + k );
            count++;
            }
        Assert.assertEquals( expectedSize, wrapped.size() );
        Assert.assertEquals( expectedSize, count );

        // remove entry on mkw : will be removed in map has well
        Assert.assertTrue( set.contains( new Integer( 5 ) ) );
        Assert.assertTrue( wrapped.contains( "-5" ) );
        wrapped.remove( "-5" );

        Assert.assertEquals( expectedSize - 1, wrapped.size() );
        Assert.assertEquals( set.size(), wrapped.size() );
        Assert.assertFalse( set.contains( new Integer( 5 ) ) );
        Assert.assertFalse( wrapped.contains( "-5" ) );

        // remove entry on map : will be removed in mkw has well
        Assert.assertTrue( set.contains( new Integer( 8 ) ) );
        Assert.assertTrue( wrapped.contains( "-8" ) );
        set.remove( new Integer( 8 ) );

        Assert.assertEquals( expectedSize - 2, wrapped.size() );
        Assert.assertEquals( set.size(), wrapped.size() );
        Assert.assertFalse( set.contains( new Integer( 8 ) ) );
        Assert.assertFalse( wrapped.contains( "-8" ) );

        // add entry on mkv : will be added in map has well
        Assert.assertFalse( set.contains( new Integer( 1000 ) ) );
        Assert.assertFalse( wrapped.contains( "-1000" ) );
        addOnSetWrapper( 1000 );

        Assert.assertEquals( expectedSize - 1, wrapped.size() );
        Assert.assertEquals( set.size(), wrapped.size() );
        Assert.assertTrue( set.contains( new Integer( 1000 ) ) );
        Assert.assertTrue( wrapped.contains( "-1000" ) );
    }

}
