package com.googlecode.cchlib.util.iterable;

import java.util.Iterator;
import com.googlecode.cchlib.util.iterator.ArrayIterator;

/**
 * Wrap an  {@link Iterable} from an existing Array, or from giving elements.
 *
 * @param <T> content type
 * @since 4.1.8
 */
public class ArrayIterable<T> implements Iterable<T>
{
    private T[] array;
    private int offset;
    private int len;

    /**
     * Wrap an array to have an Iterator
     *
     * @param array array of element to wrap
     */
    public ArrayIterable(T...array)
    {
        this( array, 0, array.length );
    }

    /**
     * Wrap an array to have an Iterator
     *
     * @param array  array to wrap
     * @param offset first element
     * @param len    number of element to read
     */
    public ArrayIterable(
        T[] array,
        int offset,
        int len
        )
    {
        this.array  = array;
        this.offset = offset;
        this.len    = len;
    }

    /**
     * Returns an iterator over a set of elements of type T.
     * @return this Iterator
     */
    @Override
    public Iterator<T> iterator()
    {
        return new ArrayIterator<T>( array, offset, len );
    }
}
