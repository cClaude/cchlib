package com.googlecode.cchlib.util.iterable;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.iterator.Selectable;

/**
 * The class <code>XIterablesTest</code> contains tests for the class <code>{@link XIterables}</code>.
 *
 * @version $Revision: 1.0 $
 */
public class XIterablesTest
{
    /**
     * Run the Iterable<Object> filter(Iterable<T>,Selectable<T>) method test.
     */
    @Test
    public void testFilter_1()
    {
        final Collection<Integer> iterable = IterablesTestFactory.createIterable();
        final Selectable<Integer> filter   = IterablesTestFactory.createFilter();

        final XIterable<Integer> result = XIterables.filter( iterable, filter );
        final List<Integer>     list1  = XIterables.newList( result );
        final List<Integer>     list2  = result.toList();

        // add additional test code here
        final int expected_size = iterable.size() / 2;

        assertThat( result ).isNotNull();
        assertThat( list1 ).hasSize( expected_size );
        assertThat( list2 ).hasSize( expected_size );
    }

    /**
     * Run the Iterable<Object> {@link XIterables#wrap(Iterable, Wrappable)} method test.
     */
    @Test
    public void testWrappe_1()
    {
        final Collection<Integer>       iterable = IterablesTestFactory.createIterable();
        final Wrappable<Integer,String> wrapper  = IterablesTestFactory.createWrappableIntegerToString();

        final XIterable<String>  result = XIterables.wrap( iterable, wrapper );
        final List<String>       list1  = XIterables.newList( result );
        final List<String>       list2  = result.toList();

        // add additional test code here
        final int expected_size = iterable.size();

        assertThat( result ).isNotNull();
        assertThat( list1 ).hasSize( expected_size );
        assertThat( list2 ).hasSize( expected_size );
    }


    @Test
    public void testFilterWrappe_1()
    {
        final Collection<Integer>       collection = IterablesTestFactory.createIterable();
        final Selectable<Integer>       filter     = IterablesTestFactory.createFilter();
        final Wrappable<Integer,String> wrapper    = IterablesTestFactory.createWrappableIntegerToString();

        final XIterable<Integer>  result1   = new XIterableImpl<Integer>( collection );
        final XIterable<Integer>  result2  = result1.filter( filter );
        final XIterable<String>   result3  = result2.wrap( wrapper );

        final List<Integer>     result1list1  = result1.toList();
        final List<Integer>     result1list2  = result1.toList();
        assertEquals( collection.size(), result1list1.size() );
        assertEquals( collection.size(), result1list2.size() );

        final List<Integer>     result2list1  = result2.toList();
        final List<Integer>     result2list2  = result2.toList();
        assertEquals( collection.size() / 2, result2list1.size() );
        assertEquals( collection.size() / 2, result2list2.size() );

        final List<String>     result3list1  = result3.toList();
        final List<String>     result3list2  = result3.toList();
        assertEquals( collection.size() / 2, result3list1.size() );
        assertEquals( collection.size() / 2, result3list2.size() );
    }

    @Test
    public void testFilterWrappeSort_1()
    {
        final Collection<Integer>       collection = IterablesTestFactory.createIterable();
        final Selectable<Integer>       filter     = IterablesTestFactory.createFilter();
        final Wrappable<Integer,String> wrapper    = IterablesTestFactory.createWrappableIntegerToString();
        final Comparator<String>        comparator = IterablesTestFactory.createDescendingComparatorForString();

        final XIterable<String> result3 = XIterables.filter( collection, filter ).wrap( wrapper ).sort( comparator );

        final List<String>     result3list1  = result3.toList();
        final List<String>     result3list2  = result3.toList();
        assertEquals( collection.size() / 2, result3list1.size() );
        assertEquals( collection.size() / 2, result3list2.size() );
    }

    @Test
    public void testFilterWrappeSort_2()
    {
        final Collection<Integer>       collection = IterablesTestFactory.createIterable();
        final Selectable<Integer>       filter     = IterablesTestFactory.createFilter();
        final Wrappable<Integer,String> wrapper    = IterablesTestFactory.createWrappableIntegerToString();
        final Comparator<String>        comparator = IterablesTestFactory.createDescendingComparatorForString();

        final List<String> result = XIterables.filter( collection, filter ).wrap( wrapper ).sort( comparator ).toList();

        assertEquals( collection.size() / 2, result.size() );

        //String firstElement = result.get( 0 );
        //assertEquals( "9998", firstElement );
        assertThat( result ).startsWith( "9998" );
   }

    @Test
    public void testFilterWrappeSort_3()
    {
        final Collection<Integer>       collection  = IterablesTestFactory.createIterable();
        final Selectable<Integer>       filter      = IterablesTestFactory.createFilter();
        final Wrappable<Integer,String> wrapper     = IterablesTestFactory.createWrappableIntegerToString();
        final Comparator<String>        comparator  = IterablesTestFactory.createDescendingComparatorForString();

        final Wrappable<String,Integer> wrapper2    = IterablesTestFactory.createWrappableStringToInteger();
        final Comparator<Integer>       comparator2 = IterablesTestFactory.createComparatorForInteger();
        final Selectable<String>        filter2     = IterablesTestFactory.createFilterRemoveEntryIfStringEndWithZero();

        final List<Integer> result = XIterables.filter( collection, filter ).wrap( wrapper ).sort( comparator ).filter( filter2 ).wrap( wrapper2 ).sort( comparator2 ).toList();

        assertThat( result ).startsWith( Integer.valueOf( 2 ) );
    }

    /**
     * Perform pre-test initialization.
     */
    @Before
    public void setUp()
    {
        // add additional set up code here
    }

    /**
     * Perform post-test clean-up.
     */
    @After
    public void tearDown()
    {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     */
    public static void main(final String[] args)
    {
        new org.junit.runner.JUnitCore().run(XIterablesTest.class);
    }
}
