package com.googlecode.cchlib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import javax.annotation.Nullable;

/**
 * This class is a List implementation which sorts the elements using the
 * comparator specified when constructing a new instance.
 * <br>
 * SortedList is design to be use when a List should be kept sorted.
 *
 * @since 4.1.7
 */
public class SortedList<T> extends ArrayList<T>
{
    private static final long serialVersionUID = 1L;

    /**
     * Comparator used to sort the list.
     */
    private Comparator<? super T> comparator = null;

    /**
     * Construct a new instance with the list elements sorted in their
     * {@link java.lang.Comparable} natural ordering.
     */
    public SortedList()
    {
        // empty
    }

    /**
     * Construct a new instance using the given comparator.
     *
     * @param comparator {@link Comparator} to use
     */
    public SortedList( @Nullable final Comparator<? super T> comparator )
    {
        this.comparator = comparator;
    }

    /**
     * Add a new entry to the list. The insertion point is calculated using the
     * comparator.
     *
     * @param e element to be appended to this list
     */
    @Override
    public boolean add( final T e )
    {
        int insertionPoint = Collections.binarySearch( this, e, comparator );

        super.add((insertionPoint > -1) ? insertionPoint : (-insertionPoint) - 1, e );

        return true;
    }

    /**
     * Adds all elements in the specified collection to the list. Each element
     * will be inserted at the correct position to keep the list sorted.
     *
     * @param c collection containing elements to be added to this list
     */
    @Override
    public boolean addAll( final Collection<? extends T> c )
    {
        boolean result = false;

        for( T e : c ) {
            result |= add( e );
            }

        return result;
    }

    /**
     * @see #containsElement(Object)
     */
    @Override
    public boolean contains( final Object o )
    {
        return super.contains( o );
    }

    /**
     * Check, if this list contains the given Element. This is faster than the
     * {@link #contains(Object)} method, since it is based on binary search.
     *
     * @param e element whose presence in this list is to be tested
     * @return <code>true</code>, if the element is contained in this list;
     * <code>false</code>, otherwise.
     */
    public boolean containsElement( final T e )
    {
        return Collections.binarySearch(this, e, comparator) > -1;
    }
}
