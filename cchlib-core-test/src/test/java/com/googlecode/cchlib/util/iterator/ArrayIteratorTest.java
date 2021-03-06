package com.googlecode.cchlib.util.iterator;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Test case
 */
public class ArrayIteratorTest
{
    private static final Logger LOGGER = Logger.getLogger(ArrayIteratorTest.class);

    @SuppressWarnings("boxing")
    private static final Integer[] ARRAY_INT = {
        1,2,3,4,5,6
    };
    private static final List<?>[] ARRAY_LIST = {
        buildList( 1 ),
        buildList( 2 ),
        buildList( 3 ),
        buildList( 4 )
    };

    @Test
    public void test1()
    {
        final Iterator<Integer> iter  = new ArrayIterator<Integer>( ARRAY_INT );

        int count         = 0;
        int expectedValue = 1;

        while( iter.hasNext() ) {
            final Integer actual = iter.next();
            assertThat( actual ).isEqualTo( expectedValue );

            expectedValue++;
            count++;
        }

        assertThat( count ).as( "Not same size !" ).isEqualTo( ARRAY_INT.length );
    }

    @Test
    public void test2()
    {
        final int                    offset = 2;
        final int                    len    = 3;
        final ArrayIterator<Integer> iter   = new ArrayIterator<Integer>( ARRAY_INT, offset, len );

        Integer firstValue    = null;
        int     count         = 0;
        int     expectedValue = ARRAY_INT[ offset ];

        while( iter.hasNext() ) {
            final Integer actual = iter.next();

            if( firstValue == null ) {
                firstValue = actual;
            }

            assertThat( actual ).isEqualTo( expectedValue );

            expectedValue++;
            count++;
        }

        assertEquals("bad size !", len, count);
        assertEquals("bad first value !", ARRAY_INT[offset], firstValue);
    }

    @Test
    public void test3()
    {
        final int offset = 2;
        final int len    = ARRAY_INT.length - offset;
        final ArrayIterator<Integer> iter  = new ArrayIterator<Integer>(ARRAY_INT,offset,len);
        int                    count = 0;

        while( iter.hasNext() ) {
            iter.next();
            count++;
        }

        assertEquals("bad size !", len, count);
    }

    @Test
    public void testBadLen()
    {
        final int offset = 2;
        final int len    = (ARRAY_INT.length - offset) + 1;
        final ArrayIterator<Integer> iter  = new ArrayIterator<Integer>(ARRAY_INT,offset,len);
        int                    count = 0;

        try {
            while( iter.hasNext() ) {
                iter.next();
                count++;
            }
            fail( "Should fail" );
        }
        catch( final NoSuchElementException ignore ) {
            //ok
        }

        assertEquals("bad size !", ARRAY_INT.length - offset, count);
    }

    @Test
    public void test_BuildFromItems()
    {
        test_BuildFromItems(Integer.class,ARRAY_INT);
        test_BuildFromItems(List.class,ARRAY_LIST);

        final Iterator<Integer> iter = ArrayIterator.of( Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3) );
        test_BuildFromItems( Integer.class, iter, 3 );
    }

    private static <T> void test_BuildFromItems(
        final Class<T>  clazz,
        final T[]       items
        )
    {
        ArrayIterator<T> iter = new ArrayIterator<T>(clazz, items[0], items[1]);
        test_BuildFromItems( clazz, iter, 2 );

        iter = new ArrayIterator<T>(clazz, items[0], items[1], items[2]);
        test_BuildFromItems( clazz, iter, 3 );
    }

    private static <T> void test_BuildFromItems(
        final Class<T>      clazz,
        final Iterator<T>   iter,
        final int           xCount
        )
    {
        int count = 0;

        while( iter.hasNext() ) {
            final T item = iter.next();
            count++;
            LOGGER.info( "T class: " + item.getClass() + " value: " + item);

            assertTrue("Type look bad!", clazz.isAssignableFrom( item.getClass() ) );
        }

        assertEquals( "Bad size !", xCount, count);
    }

    private static List<String> buildList(final int v)
    {
        final List<String> lst = new ArrayList<String>(v);

        for( int i = 0;i<v;i++) {
            lst.add( Integer.toString( i ) );
        }

        return lst;
    }
}
