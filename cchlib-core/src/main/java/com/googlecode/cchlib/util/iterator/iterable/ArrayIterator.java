package com.googlecode.cchlib.util.iterator.iterable;

import java.util.Iterator;

/**
 * Wrap an Iterator from an existing Array, or
 * from giving elements.
 * <br/>
 * Note: This Iterator extends also {@link Iterable} interface
 *
 * @param <T> content type
 * @see cx.ath.choisnet.util.iterator.SingletonIterator
 * @since 4.1.7
 */
public class ArrayIterator<T>
    extends com.googlecode.cchlib.util.iterator.ArrayIterator<T>
        implements Iterable<T>,IterableIterator<T>
{
    private int offset;

    /**
     * Wrap an array to have an Iterator
     *
     * @param array array of element to wrap
     */
    public ArrayIterator(T[] array)
    {
        super( array );
        this.offset = 0;
    }

    /**
     * Wrap an array to have an Iterator
     *
     * @param array  array to wrap
     * @param offset first element
     * @param len    number of element to read
     */
    public ArrayIterator(
            T[] array,
            int offset,
            int len
            )
    {
        super(array,offset,len);
        this.offset = offset;
    }

    /**
     * Build an Iterator on giving objects
     *
     * @param clazz type of elements
     * @param o1    first element for Iterator
     * @param o2    second element for Iterator
     */
    public ArrayIterator( Class<T> clazz, T o1, T o2 )
    {
        super(clazz,o1,o2);
        this.offset = 0;
    }

    /**
     * Build an Iterator on giving objects
     *
     * @param clazz type of elements
     * @param o1    first element for Iterator
     * @param o2    second element for Iterator
     * @param o3    third element for Iterator
     */
    public ArrayIterator( Class<T> clazz, T o1, T o2, T o3 )
    {
        super(clazz,o1,o2,o3);
        this.offset = 0;
    }

    /**
     * Returns an iterator over a set of elements of type T.
     * @return this Iterator
     */
    @Override
    public Iterator<T> iterator()
    {
        return new ArrayIterator<T>( super.array, offset, super.len );
    }
}
