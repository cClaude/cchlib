package com.googlecode.cchlib.util.iterator;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Wrap an Iterator from an existing Array, or
 * from giving elements.
 *
 * @param <T> content type
 * @see SingletonIterator
 * @since 4.1.7
 */
public class ArrayIterator<T>
    implements Iterator<T>
{
    /** Internal array, should not be modify */
    protected final T[] array;

    /** expected length for iterator */
    protected final int  len;

    /** current index for iterator */
    private int index;

    /**
     * Wrap an array to have an Iterator
     *
     * @param array array of element to wrap
     */
    @SafeVarargs
    public ArrayIterator( final T...array )
    {
        this.array = array;
        this.index = 0;

        if(array == null) {
            this.len = 0;
            }
        else {
            this.len = this.array.length;
            }
    }

    /**
     * Wrap an array to have an Iterator
     *
     * @param array  array to wrap
     * @param offset first element
     * @param len    number of element to read
     */
    public ArrayIterator(
            final T[] array,
            final int offset,
            final int len
            )
    {
        this.array = array;
        this.index = offset;
        this.len   = offset + len;
    }

    /**
     * Initialize internal array from a class
     * @param clazz
     * @param capacity
     */
    private ArrayIterator( final Class<T> clazz, final int capacity)
    {
        //perhaps something better later?
        @SuppressWarnings("unchecked")
        final
        T[] newArray = (T[])Array.newInstance(clazz,capacity);
        this.array = newArray;
        this.index = 0;
        this.len   = capacity;
    }

    /**
     * Build an Iterator on giving objects
     *
     * @param clazz type of elements
     * @param o1    first element for Iterator
     * @param o2    second element for Iterator
     */
    public ArrayIterator( final Class<T> clazz, final T o1, final T o2 )
    {
        this(clazz,2);
        this.array[0] = o1;
        this.array[1] = o2;
    }

    /**
     * Build an Iterator on giving objects
     *
     * @param clazz type of elements
     * @param o1    first element for Iterator
     * @param o2    second element for Iterator
     * @param o3    third element for Iterator
     */
    public ArrayIterator( final Class<T> clazz, final T o1, final T o2, final T o3 )
    {
        this(clazz,3);
        this.array[0] = o1;
        this.array[1] = o2;
        this.array[2] = o3;
    }

    /**
     * Returns true if the iteration has more elements.
     * (In other words, returns true if next would return
     * an element rather than throwing an exception.)
     * @return true if the iteration has more elements.
     */
    @Override
    public boolean hasNext()
    {
        return this.index < this.len;
    }

    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration.
     * @throws NoSuchElementException iteration has no more elements.
     */
    @Override
    @SuppressWarnings({
        "squid:RedundantThrowsDeclarationCheck",
        "squid:S2272" // Yes it is !
        })
    public T next() throws NoSuchElementException
    {
        try {
            return this.array[this.index++];
            }
        catch( final IndexOutOfBoundsException cause ) {
            final NoSuchElementException nsee = new NoSuchElementException();

            nsee.initCause( cause );

            throw nsee ;
            }
    }

    /**
     * Unsupported Operation
     *
     * @throws UnsupportedOperationException
     */
    @Override
    public void remove()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Build a new ArrayIterator. This factory, whose parameter
     * list uses the varargs feature, may be used to create an
     * ArrayIterator set initially containing an arbitrary number
     * of elements, but it is likely to run slower than the constructors
     * that do not use varargs.
     * <BR>
     * But since Java does not allow to build an array from
     * Genetics, this solution is cleaner.
     *
     * @param <T> content type
     * @param entries
     * @return an ArrayIterator
     */
    @SafeVarargs
    public static <T> ArrayIterator<T> of(final T...entries)
    {
        return new ArrayIterator<>( entries );
    }
}
