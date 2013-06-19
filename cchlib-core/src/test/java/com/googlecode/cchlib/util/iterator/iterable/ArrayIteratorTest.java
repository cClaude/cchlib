package com.googlecode.cchlib.util.iterator.iterable;

import java.util.Collection;
import org.junit.Test;

/**
 * Test case
 */
@Deprecated
public class ArrayIteratorTest extends IterableIteratorTestCaseHelper
{
    @Override
    protected <T> IterableIterator<T> buildIterableIterator(
        final Collection<T> c1,
        final Collection<T> c2
        )
    {
        @SuppressWarnings("unchecked")
        T[] array = (T[]) new Object[c1.size() + c2.size()];

        BiIterator<T> bi = new BiIterator<T>( c1, c2 );
        int       i  = 0;

        for(T t:bi) {
            array[ i++ ] = t;
            }

        return new ArrayIterator<T>( array );
    }

    @Test
    public void test_1()
    {
        test_crash1();
    }
    @Test
    public void test_2()
    {
        test_crash2();
    }
    @Test
    public void test_3()
    {
        test1();
    }
    @Test
    public void test4()
    {
        test2();
    }
}
