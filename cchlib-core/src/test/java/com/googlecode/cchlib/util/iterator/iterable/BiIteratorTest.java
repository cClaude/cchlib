package com.googlecode.cchlib.util.iterator.iterable;

import java.util.Collection;

import org.junit.Test;

/**
 * Test case
 */
@Deprecated
public class BiIteratorTest extends IterableIteratorTestCaseHelper
{
    @Override
    protected <T> IterableIterator<T> buildIterableIterator(
        final Collection<T> c1,
        final Collection<T> c2
        )
    {
        return new BiIterator<T>( c1, c2 );
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
