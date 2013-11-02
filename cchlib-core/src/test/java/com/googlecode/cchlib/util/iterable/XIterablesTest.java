// $codepro.audit.disable numericLiterals, importOrder
package com.googlecode.cchlib.util.iterable;

import static org.fest.assertions.Assertions.assertThat;
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
        Collection<Integer> iterable = IterablesTestFactory.createIterable();
        Selectable<Integer> filter   = IterablesTestFactory.createFilter();

        XIterable<Integer> result = XIterables.filter( iterable, filter );
        List<Integer>     list1  = XIterables.newList( result );
        List<Integer>     list2  = result.toList();

        // add additional test code here
        int expected_size = iterable.size() / 2;

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
        Collection<Integer>       iterable = IterablesTestFactory.createIterable();
        Wrappable<Integer,String> wrapper  = IterablesTestFactory.createWrappableIntegerToString();

        XIterable<String>  result = XIterables.wrap( iterable, wrapper );
        List<String>       list1  = XIterables.newList( result );
        List<String>       list2  = result.toList();

        // add additional test code here
        int expected_size = iterable.size();

        assertThat( result ).isNotNull();
        assertThat( list1 ).hasSize( expected_size );
        assertThat( list2 ).hasSize( expected_size );
    }


    @Test
    public void testFilterWrappe_1()
    {
        Collection<Integer>       collection = IterablesTestFactory.createIterable();
        Selectable<Integer>       filter     = IterablesTestFactory.createFilter();
        Wrappable<Integer,String> wrapper    = IterablesTestFactory.createWrappableIntegerToString();

        XIterable<Integer>  result1   = new XIterableImpl<Integer>( collection );
        XIterable<Integer>  result2  = result1.filter( filter );
        XIterable<String>   result3  = result2.wrap( wrapper );

        List<Integer>     result1list1  = result1.toList();
        List<Integer>     result1list2  = result1.toList();
        assertEquals( collection.size(), result1list1.size() );
        assertEquals( collection.size(), result1list2.size() );

        List<Integer>     result2list1  = result2.toList();
        List<Integer>     result2list2  = result2.toList();
        assertEquals( collection.size() / 2, result2list1.size() );
        assertEquals( collection.size() / 2, result2list2.size() );

        List<String>     result3list1  = result3.toList();
        List<String>     result3list2  = result3.toList();
        assertEquals( collection.size() / 2, result3list1.size() );
        assertEquals( collection.size() / 2, result3list2.size() );
    }

    @Test
    public void testFilterWrappeSort_1()
    {
        Collection<Integer>       collection = IterablesTestFactory.createIterable();
        Selectable<Integer>       filter     = IterablesTestFactory.createFilter();
        Wrappable<Integer,String> wrapper    = IterablesTestFactory.createWrappableIntegerToString();
        Comparator<String>        comparator = IterablesTestFactory.createDescendingComparatorForString();

        XIterable<String> result3 = XIterables.filter( collection, filter ).wrap( wrapper ).sort( comparator );

        List<String>     result3list1  = result3.toList();
        List<String>     result3list2  = result3.toList();
        assertEquals( collection.size() / 2, result3list1.size() );
        assertEquals( collection.size() / 2, result3list2.size() );
    }

    @Test
    public void testFilterWrappeSort_2()
    {
        Collection<Integer>       collection = IterablesTestFactory.createIterable();
        Selectable<Integer>       filter     = IterablesTestFactory.createFilter();
        Wrappable<Integer,String> wrapper    = IterablesTestFactory.createWrappableIntegerToString();
        Comparator<String>        comparator = IterablesTestFactory.createDescendingComparatorForString();

        List<String> result = XIterables.filter( collection, filter ).wrap( wrapper ).sort( comparator ).toList();

        assertEquals( collection.size() / 2, result.size() );

        //String firstElement = result.get( 0 );
        //assertEquals( "9998", firstElement );
        assertThat( result ).startsWith( "9998" );
   }

    @Test
    public void testFilterWrappeSort_3()
    {
        Collection<Integer>       collection  = IterablesTestFactory.createIterable();
        Selectable<Integer>       filter      = IterablesTestFactory.createFilter();
        Wrappable<Integer,String> wrapper     = IterablesTestFactory.createWrappableIntegerToString();
        Comparator<String>        comparator  = IterablesTestFactory.createDescendingComparatorForString();

        Wrappable<String,Integer> wrapper2    = IterablesTestFactory.createWrappableStringToInteger();
        Comparator<Integer>       comparator2 = IterablesTestFactory.createComparatorForInteger();
        Selectable<String>        filter2     = IterablesTestFactory.createFilterRemoveEntryIfStringEndWithZero();

        List<Integer> result = XIterables.filter( collection, filter ).wrap( wrapper ).sort( comparator ).filter( filter2 ).wrap( wrapper2 ).sort( comparator2 ).toList();

        //int firstElement = result.get( 0 ).intValue();
        //assertEquals( 2, firstElement );
        assertThat( result ).startsWith( 2 ); // $codepro.audit.disable avoidAutoBoxing
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
    public static void main(String[] args)
    {
        new org.junit.runner.JUnitCore().run(XIterablesTest.class);
    }
}
