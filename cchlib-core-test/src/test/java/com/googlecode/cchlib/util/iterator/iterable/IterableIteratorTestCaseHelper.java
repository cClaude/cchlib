// $codepro.audit.disable avoidAutoBoxing, numericLiterals
package com.googlecode.cchlib.util.iterator.iterable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Assert;


/**
 * Test case
 */
//public
abstract class IterableIteratorTestCaseHelper
{
    private static final Logger LOGGER = Logger.getLogger(IterableIteratorTestCaseHelper.class);

  /**
   * Build an unmodifiable collection with giving values
   * @param values values to add in Collection
   * @return a Collection populate with values
   */
    public static final <T> Collection<T> buildCollection(T[] values)
    {
        List<T> c = new ArrayList<T>(values.length);

        for(T v:values) {
            c.add( v );
            }

        return Collections.unmodifiableCollection( c );
    }

  /**
   * Build an empty collection of integers
   * @return an empty Collection of Integer
   */
    public static final <T> Collection<T> buildEmptyCollection()
    {
        return Collections.emptyList();
    }

    /**
     * Default implementation for {@link #buildCollection1()}
     * @return a Collection of Integer
     */
    public static final Collection<Integer> buildDefaultIntegerCollection1()
    {
        //return buildCollection( 0, 1, 2, 3 );
        return buildCollection( new Integer[]{ 0, 1, 2, 3 });
    }

    /**
     * Default implementation for {@link #buildCollection2()}
     * @return a Collection of Integer
     */
    public static final Collection<Integer> buildDefaultIntegerCollection2()
    {
        //return buildCollection( 4, 5, 6 );
        return buildCollection( new Integer[]{ 4, 5, 6 });
    }

    /**
     * Test if IterableIterator could be reuse
     *
     * @param <T>
     * @param iterableIterator
     * @param size
     */
    @Deprecated
    public static <T> void test(IterableIterator<T> iterableIterator, final int size)
    {
        int count = test( iterableIterator );

        Assert.assertEquals("Not same size !", size, count);

        count = test( iterableIterator );

        Assert.assertEquals("Iterator already read (no more element)", 0, count);

        count = test( iterableIterator.iterator() );

        Assert.assertEquals("Not same size !", size, count);
    }

    private static <T> int test(Iterator<T> iter)
    {
        int  count = 0;

        while( iter.hasNext() ) {
            iter.next();
            count++;
        }

        return count;
    }

    /**
     * Build a IterableIterator
     * @param <T>
     * @param c1
     * @param c2
     * @return
     */
    @Deprecated
    protected abstract <T> IterableIterator<T> buildIterableIterator(
        final Collection<T> c1,
        final Collection<T> c2
        );

    public final void test_crash1()
    {
        try {
            buildIterableIterator( null, buildEmptyCollection() );

            Assert.fail( "Should crash");
            }
        catch( NullPointerException e ) { // $codepro.audit.disable logExceptions
            LOGGER.info( "Crash ok" );
            }
    }

    public final void test_crash2()
    {
        try {
            buildIterableIterator( buildEmptyCollection(), null );

            Assert.fail( "Should crash");
            }
        catch( NullPointerException e ) { // $codepro.audit.disable logExceptions
            LOGGER.info( "Crash ok" );
            }
    }

    public final void test1()
    {
        test( buildIterableIterator( buildEmptyCollection(), buildEmptyCollection() ), 0 );
    }

    public final void test2()
    {
        Collection<Integer> c1 = buildDefaultIntegerCollection1();
        Collection<Integer> c2 = buildDefaultIntegerCollection2();

        test( buildIterableIterator( c1, c2 ), c1.size() + c2.size() );
    }
}
