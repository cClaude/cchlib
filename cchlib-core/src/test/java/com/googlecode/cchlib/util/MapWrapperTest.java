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
public class MapWrapperTest
{
    private static final Logger LOGGER = Logger.getLogger( MapWrapperTest.class );

    private Map<String,MyType>   map;
    private Map<String,Integer>  wrapped;

    @Before
    public void before()
    {
        Wrappable<MyType,Integer> wrapper = new Wrappable<MyType,Integer>()
        {
            @Override
            public Integer wrap( MyType v ) throws WrapperException
            {
                if( v == null ) {
                    return null;
                    }
                else {
                    return Integer.valueOf( v.getContent() );
                    }
            }
        };
        Wrappable<Integer,MyType> unwrapper = new Wrappable<Integer,MyType>()
        {
            @Override
            public MyType wrap( Integer v ) throws WrapperException
            {
                return new MyType( v.intValue() );
            }
        };
        map     = new HashMap<String,MyType>();
        wrapped = new MapWrapper<String,MyType,Integer>( map, wrapper, unwrapper );
    }

    private void addOnMap( int i )
    {
        map.put( Integer.toString( i ), new MyType( i ) );
    }

    private void addOnMapKeyWrapper( int i )
    {
        wrapped.put( Integer.toString( i ), Integer.valueOf( i ) );
    }

    @Test
    public void testsize1()
    {
        Assert.assertTrue( map.isEmpty() );
        Assert.assertTrue( wrapped.isEmpty() );
        Assert.assertEquals( 0, map.size() );
        Assert.assertEquals( 0, wrapped.size() );
        Assert.assertEquals( 0, wrapped.entrySet().size() );
        Assert.assertEquals( 0, wrapped.keySet().size() );
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
        Assert.assertEquals( expectedSize, wrapped.entrySet().size() );
        Assert.assertEquals( expectedSize, wrapped.keySet().size() );
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
        Assert.assertEquals( expectedSize, wrapped.entrySet().size() );
        Assert.assertEquals( expectedSize, wrapped.keySet().size() );
    }


    @Test
    public void test_remove_add()
    {
        final int expectedSize = 30;

        for( int i = 0; i<expectedSize; i++ ) {
            addOnMap( i );
            }

        int count = 0;
        for( Map.Entry<String,Integer> e : wrapped.entrySet() ) {
            LOGGER.info( "e = " + e );
            count++;
            }
        Assert.assertEquals( expectedSize, wrapped.size() );
        Assert.assertEquals( expectedSize, count );

        count = 0;
        for( Integer v : wrapped.values() ) {
            LOGGER.info( "v = " + v );
            count++;
            }
        Assert.assertEquals( expectedSize, wrapped.size() );
        Assert.assertEquals( expectedSize, count );

        count = 0;
        for( String k : wrapped.keySet() ) {
            Integer v = wrapped.get( k );
            LOGGER.info( "k = " + k + " - v = " + v );
            Assert.assertNotNull( v );
            count++;
            }
        Assert.assertEquals( expectedSize, wrapped.size() );
        Assert.assertEquals( expectedSize, count );

        // remove entry on mkw : will be removed in map has well
        Assert.assertTrue( map.containsKey( "5" ) );
        Assert.assertTrue( wrapped.containsKey( "5" ) );
        wrapped.remove( "5" );

        Assert.assertEquals( expectedSize - 1, wrapped.size() );
        Assert.assertEquals( map.size(), wrapped.size() );
        Assert.assertFalse( map.containsKey( "5" ) );
        Assert.assertFalse( wrapped.containsKey( "5" ) );

        // remove entry on map : will be removed in mkw has well
        Assert.assertTrue( map.containsKey( "8" ) );
        Assert.assertTrue( wrapped.containsKey( "8" ) );
        map.remove( "8" );

        Assert.assertEquals( expectedSize - 2, wrapped.size() );
        Assert.assertEquals( map.size(), wrapped.size() );
        Assert.assertFalse( map.containsKey( "8" ) );
        Assert.assertFalse( wrapped.containsKey(  "8" ) );

        // add entry on mkv : will be added in map has well
        Assert.assertFalse( map.containsKey( "1000" ) );
        Assert.assertFalse( wrapped.containsKey( "1000" ) );
        addOnMapKeyWrapper( 1000 );

        Assert.assertEquals( expectedSize - 1, wrapped.size() );
        Assert.assertEquals( map.size(), wrapped.size() );
        Assert.assertTrue( map.containsKey( "1000" ) );
        Assert.assertTrue( wrapped.containsKey( "1000" ) );
    }

}
