package com.googlecode.cchlib.util.iterable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrapperException;
import com.googlecode.cchlib.util.iterator.Selectable;

/**
 * Default implementation for {@link XIterable}
 *
 * @since 4.1.8
 */
//NOT public
class XIterableImpl<T> implements XIterable<T>
{
    private final Iterable<T> iterable;

    /**
     * Create a new {@link XIterable} object based on a standard {@link Iterable}.
     *
     * @param iterable
     */
    public XIterableImpl( final Iterable<T> iterable )
    {
        this.iterable = iterable;
    }

    @NeedDoc
    @SuppressWarnings({
        "squid:RedundantThrowsDeclarationCheck",
        })
    public <S> XIterableImpl(
        final Iterable<S>                      iterable,
        final Wrappable<? super S,? extends T> wrapper
        ) throws WrapperException
    {
        this.iterable = new IterableWrapper<>( iterable, wrapper );
    }

    @NeedDoc
    public XIterableImpl(
        final Iterable<T>           iterable,
        final Selectable<? super T> filter
        )
    {
        this.iterable = new IterableFilter<>( iterable, filter );
    }

    @Override
    public Iterator<T> iterator()
    {
        return this.iterable.iterator();
    }

    @Override
    public <R> XIterable<R> wrap( final Wrappable<? super T,? extends R> wrapper )
    {
        return new XIterableImpl<>( this, wrapper );
    }

    @Override
    public XIterable<T> filter( final Selectable<? super T> filter )
    {
        return new XIterableImpl<>( this, filter );
    }

    @Override
    public XIterable<T> sort( final Comparator<? super T> comparator )
    {
        final List<T> list = toList();

        Collections.sort( list, comparator );

        return new XIterableImpl<>( list );
    }

    @Override
    public List<T> toList()
    {
        return addToList( new ArrayList<>() );
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
        for( final Iterator<T> iter = this.iterator(); iter.hasNext(); ) {
            list.add( iter.next() );
            }

        return list;
    }

}
