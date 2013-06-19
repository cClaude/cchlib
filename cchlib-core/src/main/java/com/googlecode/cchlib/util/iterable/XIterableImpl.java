package com.googlecode.cchlib.util.iterable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrappeException;
import com.googlecode.cchlib.util.iterator.Selectable;

/**
 * Default implementation for {@link XIterable}
 *
 * @since 4.1.8
 */
public class XIterableImpl<T> implements XIterable<T>
{
    private Iterable<T> iterable;

    /**
     * Create a new {@link XIterable} object based on a standard {@link Iterable}.
     *
     * @param iterable
     */
    public XIterableImpl( final Iterable<T> iterable )
    {
        this.iterable = iterable;
    }

    /**
     *
     * @param iterable
     * @param wrapper
     * @throws WrappeException
     */
    public <S> XIterableImpl(
        final Iterable<S>                      iterable,
        final Wrappable<? super S,? extends T> wrapper
        ) throws WrappeException
    {
        this.iterable = new IterableWrapper<S,T>( iterable, wrapper );
    }

    /**
     *
     * @param iterable
     * @param filter
     */
    public XIterableImpl(
        final Iterable<T>           iterable,
        final Selectable<? super T> filter 
        )
    {
        this.iterable = new IterableFilter<T>( iterable, filter );
    }

    @Override
    public Iterator<T> iterator()
    {
        return iterable.iterator();
    }

    @Override
    public <R> XIterable<R> wrappe( final Wrappable<? super T,? extends R> wrapper )
    {
        return new XIterableImpl<R>( this, wrapper );
    }

    @Override
    public XIterable<T> filter( final Selectable<? super T> filter )
    {
        return new XIterableImpl<T>( this, filter );
    }

    @Override
    public XIterable<T> sort( Comparator<? super T> comparator )
    {
        List<T> list = toList();

        Collections.sort( list, comparator );

        return new XIterableImpl<T>( list );
    }

    @Override
    public List<T> toList()
    {
        return addToList( new ArrayList<T>() );
    }

    @Override
    public List<T> setToList( final List<T> list )
    {
        list.clear();
        addToList( list );

        return list;
    }

    @Override
    public List<T> addToList( final List<T> list )
    {
        for( Iterator<T> iter = this.iterator(); iter.hasNext(); ) {
            list.add( iter.next() );
            }

        return list;
    }

}
