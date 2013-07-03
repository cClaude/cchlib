package com.googlecode.cchlib.util.iterable;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.iterator.Iterators;
import com.googlecode.cchlib.util.iterator.Selectable;

/**
 * The class <code>IterablesTest</code> contains tests for the class <code>{@link Iterables}</code>.
 *
 * @version $Revision: 1.0 $
 */
public class IterablesTest
{
    @Test
    public void testFilter_1()
    {
        Collection<Integer> iterable = IterablesTestFactory.createIterable();
        Selectable<Integer> filter   = IterablesTestFactory.createFilter();

        Iterable<Integer>   result = Iterables.filter( iterable, filter );
        List<Integer>       list1  = Iterables.newList( result );
        List<Integer>       list2  = Iterables.newList( result );

        // add additional test code here
        int expected_size = iterable.size() / 2;

        assertThat( result ).isNotNull();
        assertThat( list1 ).hasSize( expected_size );
        assertThat( list2 ).hasSize( expected_size );
    }

    public void testFilter_2()
    {
        Collection<Integer> iterable = IterablesTestFactory.createIterable();
        Selectable<Integer> filter   = IterablesTestFactory.createFilter();

        Iterable<Integer>   result = Iterables.filter( iterable.iterator(), filter);
        List<Integer>       list1  = Iterables.newList( result );
        List<Integer>       list2  = Iterables.newList( result );

        // add additional test code here
        assertNotNull(result);
        assertEquals( iterable.size() / 2, list1.size() );
        assertEquals( iterable.size() / 2, list2.size() );
    }

    public void testFilter_3()
    {
        Collection<Integer> iterable = IterablesTestFactory.createIterable();
        Selectable<Integer> filter   = IterablesTestFactory.createFilter();

        Iterable<Integer>   result = Iterables.filter( Iterators.toEnumeration( iterable.iterator() ), filter);
        List<Integer>       list1  = Iterables.newList( result );
        List<Integer>       list2  = Iterables.newList( result );

        // add additional test code here
        assertNotNull(result);
        assertEquals( iterable.size() / 2, list1.size() );
        assertEquals( iterable.size() / 2, list2.size() );
    }

    /**
     * Run the Iterable<Object> {@link Iterables#wrap(Iterable, Wrappable)} method test.
     */
    @Test
    public void testWrappe_1()
    {
        Collection<Integer>       iterable = IterablesTestFactory.createIterable();
        Wrappable<Integer,String> wrapper  = IterablesTestFactory.createWrappableIntegerToString();

        Iterable<String>   result = Iterables.wrap(iterable, wrapper);
        List<String>       list1  = Iterables.newList( result );
        List<String>       list2  = Iterables.newList( result );

        // add additional test code here
        assertNotNull(result);
        assertEquals( iterable.size(), list1.size() );
        assertEquals( iterable.size(), list2.size() );
    }

    /**
     * Perform pre-test initialization.
     */
    @Before
    public void setUp() throws Exception
    {
        // add additional set up code here
    }

    /**
     * Perform post-test clean-up.
     */
    @After
    public void tearDown() throws Exception
    {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     */
    public static void main(String[] args)
    {
        new org.junit.runner.JUnitCore().run(IterablesTest.class);
    }
}
