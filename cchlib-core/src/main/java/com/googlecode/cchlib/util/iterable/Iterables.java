package com.googlecode.cchlib.util.iterable;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.NeedTestCases;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrappeException;
import com.googlecode.cchlib.util.iterator.EnumerationIterator;
import com.googlecode.cchlib.util.iterator.IteratorFilter;
import com.googlecode.cchlib.util.iterator.IteratorWrapper;
import com.googlecode.cchlib.util.iterator.Selectable;

/**
 * This class contains static utility methods that operate on or return objects of type {@link Iterable}.
 *
 * @see XIterables
 * @since 1.4.8
 */
@NeedDoc
@NeedTestCases
public class Iterables
{
    private Iterables() {} 
    
    public static <T> Iterable<T> create( Enumeration<T> enumeration )
    {
        return new OnlyOnceIterable<T>( new EnumerationIterator<T>( enumeration ) );
    }

    public static <T> Iterable<T> create( Iterator<T> iterator )
    {
        return new OnlyOnceIterable<T>( iterator );
    }

    /**
     * {@link #wrappe(Iterable, Wrappable)} for {@link Iterator} objects
     */
    public static <S,R> Iterable<R> wrappe(Iterator<S> iterator, Wrappable<? super S,? extends R> wrapper )
        throws WrappeException
    {
        return wrappe( Iterables.create( iterator ), wrapper );
    }
    
    /**
     * {@link #wrappe(Iterable, Wrappable)} for {@link Enumeration} objects
     */
    public static <S,R> Iterable<R> wrappe( Enumeration<S> enumeration, Wrappable<? super S,? extends R> wrapper )
            throws WrappeException
    {
        return wrappe( Iterables.create( enumeration ), wrapper );
    }

    /**
     * Guava like function for {@link #wrappe(Iterable, Wrappable)}
     */
    public static <F,T> Iterable<T> transform(Iterable<F> fromIterable, Wrappable<? super F,? extends T> function)
    {
        return wrappe( fromIterable, function );
    }
 
    /**
     * Returns an {@link Iterable} that applies <code>wrapper</code> to each element of fromIterable.
     * <p>
     * The returned iterable's iterator supports remove() if the provided iterator does.
     * After a successful remove() call, <code>iterable</code> no longer contains the 
     * corresponding element.
     * </p>
     * @param iterable
     * @param wrapper
     * @return an {@link Iterable} that applies <code>wrapper</code> to each element of fromIterable.
     * @throws WrappeException if any
     */
    public static <S,R> Iterable<R> wrappe(
        final Iterable<S>                      iterable,
        final Wrappable<? super S,? extends R> wrapper
        ) throws WrappeException
    {
        return new Iterable<R>()
        {
            @Override
            public Iterator<R> iterator()
            {
                return new IteratorWrapper<S,R>( iterable.iterator(), wrapper );
            }
        };
    }

    /**
     * {@link #filter(Iterable, Selectable)} for {@link Iterator} objects
     */
    public static <T> Iterable<T> filter( Iterator<T> iterator, Selectable<T> filter )
    {
        return filter( Iterables.create( iterator ), filter );
    }

    /**
     * {@link #filter(Iterable, Selectable)} for {@link Enumeration} objects
     */
    public static <T> Iterable<T> filter( Enumeration<T> enumeration, Selectable<T> filter )
    {
        return filter( Iterables.create( enumeration ), filter );
    }
    
    /**
     * Returns the elements of <code>unfiltered<code> that satisfy a filter. 
     * The resulting iterable's iterator does not support remove().
     *
     * @param unfiltered
     * @param filter
     * @return TODOC
     */
    public static <T> Iterable<T> filter(
        final Iterable<T>           unfiltered,
        final Selectable<? super T> filter
        )
    {
        return new Iterable<T>()
        {
            @Override
            public Iterator<T> iterator()
            {
                return new IteratorFilter<T>( unfiltered.iterator(), filter );
            }
        };
    }

    /**
     *
     * @param iterable
     * @return TODOC
     */
    public static <T> List<T> newList( Iterable<T> iterable )
    {
        ArrayList<T> list = new ArrayList<T>();

        for( T entry : iterable ) {
            list.add( entry );
            }

        return list;
    }
}
