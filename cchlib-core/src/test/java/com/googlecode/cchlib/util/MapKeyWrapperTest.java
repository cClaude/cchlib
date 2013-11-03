// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class MapKeyWrapperTest
{
    private static final Logger LOGGER = Logger.getLogger( MapKeyWrapperTest.class );

    private Map<Integer,MyType> map;
    private Map<String,MyType>  wrapped;

    @Before
    public void before()
    {
        Wrappable<Integer,String> wrapper = new Wrappable<Integer,String>()
        {
            @Override
            public String wrap( Integer intKey ) throws WrapperException
            {
                return Integer.toString( - intKey.intValue() );
            }
        };
        Wrappable<String,Integer> unwrapper = new Wrappable<String,Integer>()
        {
            @Override
            public Integer wrap( String strKey ) throws WrapperException
            {
                return Integer.valueOf( - Integer.parseInt( strKey ) );
            }
        };
        map     = new HashMap<Integer,MyType>();
        wrapped = new MapKeyWrapper<Integer,String,MyType>( map, wrapper, unwrapper );
    }

    private void addOnMap( int value )
    {
        map.put(
            new Integer( value ), // key
            new MyType( value )   // value
            );
    }
    private void addOnMapKeyWrapper( int value )
    {
        wrapped.put(
            Integer.toString( - value ), // key
            new MyType( value )          // value
            );
    }

    @Test
    public void testsize1()
    {
        Assert.assertTrue( map.isEmpty() );
        Assert.assertTrue( wrapped.isEmpty() );
        Assert.assertEquals( 0, map.size() );
        Assert.assertEquals( 0, wrapped.size() );
        Assert.assertEquals( 0, wrapped.keySet().size() );
        Assert.assertEquals( 0, wrapped.entrySet().size() );
    }

    @Test
    public void testsize2()
    {
        final int expectedSize = 1;
        addOnMap( 1 );
        Assert.assertFalse( map.isEmpty() );
        Assert.assertFalse( wrapped.isEmpty() );
        Assert.assertEquals( expectedSize, map.size() );
        Assert.assertEquals( expectedSize, wrapped.size() );
        Assert.assertEquals( expectedSize, wrapped.keySet().size() );
        Assert.assertEquals( expectedSize, wrapped.entrySet().size() );
    }

    @Test
    public void testsize3()
    {
        final int expectedSize = 30;
        for( int i = 0; i<expectedSize; i++ ) {
            addOnMap( i );
            }
        Assert.assertFalse( map.isEmpty() );
        Assert.assertFalse( wrapped.isEmpty() );
        Assert.assertEquals( expectedSize, map.size() );
        Assert.assertEquals( expectedSize, wrapped.size() );
        Assert.assertEquals( expectedSize, wrapped.keySet().size() );
        Assert.assertEquals( expectedSize, wrapped.entrySet().size() );
    }

    @Test
    public void test_remove_add()
    {
        final int expectedSize = 30;

        for( int i = 0; i<expectedSize; i++ ) {
            addOnMap( i );
            }

        int count = 0;
        for( Map.Entry<String,MyType> e : wrapped.entrySet() ) {
            LOGGER.info( "e = " + e );
            count++;
            }
        Assert.assertEquals( expectedSize, wrapped.size() );
        Assert.assertEquals( expectedSize, count );

        count = 0;
        for( MyType v : wrapped.values() ) {
            LOGGER.info( "v = " + v );
            count++;
            }
        Assert.assertEquals( expectedSize, wrapped.size() );
        Assert.assertEquals( expectedSize, count );

        count = 0;
        for( String k : wrapped.keySet() ) {
            MyType v = wrapped.get( k );
            LOGGER.info( "k = " + k + " - v = " + v );
            Assert.assertNotNull( v );
            count++;
            }
        Assert.assertEquals( expectedSize, wrapped.size() );
        Assert.assertEquals( expectedSize, count );

        // remove entry on mkw : will be removed in map has well
        Assert.assertTrue( map.containsKey( Integer.valueOf( 5 ) ) );
        Assert.assertTrue( wrapped.containsKey( "-5" ) );
        wrapped.remove( "-5" );

        Assert.assertEquals( expectedSize - 1, wrapped.size() );
        Assert.assertEquals( map.size(), wrapped.size() );
        Assert.assertFalse( map.containsKey( Integer.valueOf( 5 ) ) );
        Assert.assertFalse( wrapped.containsKey( "-5" ) );

        // remove entry on map : will be removed in mkw has well
        Assert.assertTrue( map.containsKey( Integer.valueOf( 8 ) ) );
        Assert.assertTrue( wrapped.containsKey( "-8" ) );
        map.remove( Integer.valueOf( 8 ) );

        Assert.assertEquals( expectedSize - 2, wrapped.size() );
        Assert.assertEquals( map.size(), wrapped.size() );
        Assert.assertFalse( map.containsKey( Integer.valueOf( 8 ) ) );
        Assert.assertFalse( wrapped.containsKey( "-8" ) );

        // add entry on mkv : will be added in map has well
        Assert.assertFalse( map.containsKey( Integer.valueOf( 1000 ) ) );
        Assert.assertFalse( wrapped.containsKey( "-1000" ) );
        addOnMapKeyWrapper( 1000 );

        Assert.assertEquals( expectedSize - 1, wrapped.size() );
        Assert.assertEquals( map.size(), wrapped.size() );
        Assert.assertTrue( map.containsKey( Integer.valueOf( 1000 ) ) );
        Assert.assertTrue( wrapped.containsKey( "-1000" ) );
    }

}
