package com.googlecode.cchlib.util.iterator;

import static org.fest.assertions.api.Assertions.assertThat;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class ComputableIteratorTest extends ComputableIteratorTest_data {

    private static final Logger LOGGER = Logger.getLogger( ComputableIteratorTest.class );

    @Test(expected=UnsupportedOperationException.class)
    public void test_remove_UnsupportedOperationException()
    {
        final Iterator<Integer> iterator = createComputableIterator();

        iterator.next();
        iterator.remove(); // SHOULD FAIL HERE
        Assert.fail();
    }

    @Test(expected=UnsupportedOperationException.class)
    public void test_remove_UnsupportedOperationException_for_ArrayList()
    {
        final Iterator<Integer> iterator = createListIterator();

        iterator.next();
        iterator.remove(); // SHOULD FAIL HERE
        Assert.fail();
    }

    @Test(expected=UnsupportedOperationException.class)
    public void test_remove_2_UnsupportedOperationException()
    {
        final Iterator<Integer> iterator = createComputableIterator();

        iterator.remove(); // SHOULD FAIL HERE
        Assert.fail();
    }

    @Test(expected=UnsupportedOperationException.class)
    public void test_remove_2_UnsupportedOperationException_for_ArrayList()
    {
        final Iterator<Integer> iterator = createListIterator();

        iterator.remove(); // SHOULD FAIL HERE
        Assert.fail();
    }

    @Test(expected=NoSuchElementException.class)
    public void test_next_NoSuchElementException()
    {
        final Iterator<Integer> iterator = createComputableIterator();

        for( int expected = 0; expected < MAX; expected++ ) {
            final Integer value = iterator.next();
            LOGGER.info( "test_next_NoSuchElementException() - value=" + value + " exp: " + expected);
            assertThat( value ).isEqualTo( expected );
        }

        iterator.next(); // SHOULD FAIL HERE
        Assert.fail();
    }

    @Test(expected=NoSuchElementException.class)
    public void test_next_NoSuchElementException_for_ArrayList()
    {
        final Iterator<Integer> iterator = createListIterator();

        for( int expected = 0; expected < MAX; expected++ ) {
            final Integer value = iterator.next();

            LOGGER.info( "test_next_NoSuchElementException_for_ArrayList() - value=" + value );

            assertThat( value ).isEqualTo( expected );
        }

        iterator.next(); // SHOULD FAIL HERE
        Assert.fail();
    }

    @Test
    public void test_values()
    {
        final Iterator<Integer> iterator = createComputableIterator();
        int expected = 0;

        while( iterator.hasNext() ) {
            assertThat( iterator.next() ).isEqualTo( expected++ );
        }

        assertThat( expected ).isEqualTo( MAX );
    }

    @Test
    public void test_values_for_ArrayList()
    {
        final Iterator<Integer> iterator = createListIterator();
        int expected = 0;

        while( iterator.hasNext() ) {
            assertThat( iterator.next() ).isEqualTo( expected++ );
        }

        assertThat( expected ).isEqualTo( MAX );
    }

    @Test
    public void test_values_null_support()
    {
        final Iterator<Integer> iterator = createComputableIteratorWithNull();
        int expected = 0;

        while( iterator.hasNext() ) {
            final Integer value = iterator.next();

            LOGGER.info( "test_values_null_support() - value=" + value );

            if( expected == NULL_INDEX ) {
                assertThat( value ).isNull();;
            } else {
                assertThat( value ).isEqualTo( expected );
            }
            expected++;
        }

        assertThat( expected ).isEqualTo( MAX );
    }

    @Test
    public void test_values_null_support_for_ArrayList()
    {
        final Iterator<Integer> iterator = createListIteratorWithNull();
        int expected = 0;

        while( iterator.hasNext() ) {
            final Integer value = iterator.next();

            LOGGER.info( "test_values_null_support_for_ArrayList() - value=" + value );

            if( expected == NULL_INDEX ) {
                assertThat( value ).isNull();;
            } else {
                assertThat( value ).isEqualTo( expected );
            }
            expected++;
        }

        assertThat( expected ).isEqualTo( MAX );
    }
}
