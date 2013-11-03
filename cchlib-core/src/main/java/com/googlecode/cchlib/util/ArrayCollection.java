package com.googlecode.cchlib.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

import com.googlecode.cchlib.util.iterator.ArrayIterator;

/**
 * Unmodifiable {@link Collection} base on an array
 *
 * @param <E>
 */
public class ArrayCollection<E>
    extends AbstractCollection<E>
{
    private E[] array;
    private int offset;
    private int length;

    /**
     * Create an ArrayCollection using giving array
     *
     * @param array Array to wrap for this collection.
     */
    public ArrayCollection( final E[] array )
    {
        this( array, 0, array.length );
    }

    /**
     * Create an ArrayCollection based on a part of giving array.
     *
     * @param array Array to wrap for this collection.
     * @param offset first element
     * @param len number of element to read
     */
    public ArrayCollection(
        final E[] array,
        final int offset,
        final int len
        )
    {
        this.array  = array;  // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.mutabilityOfArrays
        this.offset = 0;
        this.length = len;
    }

    @Override
    public int size()
    {
        return this.length;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new ArrayIterator<E>( this.array, this.offset, this.length );
    }
}
