// $codepro.audit.disable importOrder, numericLiterals
package com.googlecode.cchlib.util.iterable;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
        final Collection<Integer> iterable = IterablesTestFactory.createIterable();
        final Selectable<Integer> filter   = IterablesTestFactory.createFilter();

        final Iterable<Integer>   result = Iterables.filter( iterable, filter );
        final List<Integer>       list1  = Iterables.newList( result );
        final List<Integer>       list2  = Iterables.newList( result );

        // add additional test code here
        final int expected_size = iterable.size() / 2;

        assertThat( result ).isNotNull();
        assertThat( list1 ).hasSize( expected_size );
        assertThat( list2 ).hasSize( expected_size );
    }

    public void testFilter_2()
    {
        final Collection<Integer> iterable = IterablesTestFactory.createIterable();
        final Selectable<Integer> filter   = IterablesTestFactory.createFilter();

        final Iterable<Integer>   result = Iterables.filter( iterable.iterator(), filter);
        final List<Integer>       list1  = Iterables.newList( result );
        final List<Integer>       list2  = Iterables.newList( result );

        // add additional test code here
        assertNotNull(result);
        assertEquals( iterable.size() / 2, list1.size() );
        assertEquals( iterable.size() / 2, list2.size() );
    }

    public void testFilter_3()
    {
        final Collection<Integer> iterable = IterablesTestFactory.createIterable();
        final Selectable<Integer> filter   = IterablesTestFactory.createFilter();

        final Iterable<Integer>   result = Iterables.filter( Iterators.toEnumeration( iterable.iterator() ), filter);
        final List<Integer>       list1  = Iterables.newList( result );
        final List<Integer>       list2  = Iterables.newList( result );

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
        final Collection<Integer>       iterable = IterablesTestFactory.createIterable();
        final Wrappable<Integer,String> wrapper  = IterablesTestFactory.createWrappableIntegerToString();

        final Iterable<String>   result = Iterables.wrap(iterable, wrapper);
        final List<String>       list1  = Iterables.newList( result );
        final List<String>       list2  = Iterables.newList( result );

        // add additional test code here
        assertNotNull(result);
        assertEquals( iterable.size(), list1.size() );
        assertEquals( iterable.size(), list2.size() );
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
        new org.junit.runner.JUnitCore().run(IterablesTest.class);
    }
}
