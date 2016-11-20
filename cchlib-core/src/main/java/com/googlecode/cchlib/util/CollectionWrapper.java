package com.googlecode.cchlib.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.util.iterator.IteratorWrapper;

/**
 * Give a view of a {@link Collection} using real-time computed values
 *
 * @param <S> Original (source) content type
 * @param <R> Mapped (result) content type
 * @since 4.1.7
 */
public class CollectionWrapper<S,R> implements Collection<R>,Serializable
{
    static final long serialVersionUID = 1L;

    private final Collection<S>  collection;
    private final Wrappable<S,R> wrapper;
    private final Wrappable<R,S> unwrapper;

    /**
     * Create a CollectionWrapper
     *
     * @param collection Original {@link Collection}
     * @param wrapper   A wrapper able to compute mapped values from original values
     * @param unwrapper A wrapper able to compute original values from mapped values
     */
    public CollectionWrapper(
        final Collection<S>  collection,
        final Wrappable<S,R> wrapper,
        final Wrappable<R,S> unwrapper
        )
    {
        this.collection = collection;
        this.wrapper = wrapper;
        this.unwrapper = unwrapper;
    }

    @Override
    public boolean add( final R e )
    {
        return collection.add( unwrapper.wrap( e ) );
    }

    @Override
    public boolean addAll( final Collection<? extends R> c )
    {
        boolean setChange = false;

        for( final R e : c ) {
            final boolean res = add( e );

            if( res ) {
                setChange = true;
                }
            }

        return setChange;
    }

    @Override
    public void clear()
    {
        collection.clear();
    }

    @Override
    public boolean contains( final Object o )
    {
        @SuppressWarnings("unchecked")
        final
        R rO = (R)o; // $codepro.audit.disable unnecessaryCast
        return collection.contains( unwrapper.wrap( rO ) );
    }

    @Override
    public boolean containsAll( final Collection<?> c )
    {
        for( final Object o : c ) {
            if( ! contains( o ) ) {
                return false;
                }
            }
        return true;
    }

    @Override
    public boolean isEmpty()
    {
        return collection.isEmpty();
    }

    @Override
    public Iterator<R> iterator()
    {
        return new IteratorWrapper<>( collection.iterator(), wrapper );
    }

    @Override
    public boolean remove( final Object o )
    {
        @SuppressWarnings("unchecked")
        final
        R rO = (R)o; // $codepro.audit.disable unnecessaryCast
        return collection.remove( unwrapper.wrap( rO ) );
    }

    @Override
    public boolean removeAll( final Collection<?> c )
    {
        boolean setChange = false;

        for( final Object e : c ) {
            final boolean res = remove( e );

            if( res ) {
                setChange = true;
                }
            }

        return setChange;
    }

    @Override
    public int size()
    {
        return collection.size();
    }

    @Override
    public boolean retainAll( final Collection<?> c )
    {
        boolean setChange = false;

        for( final Object e : c ) {
            if( contains( e ) ) {
                final boolean res = remove( e );

                if( res ) {
                    setChange = true;
                    }
                }
        }

        return setChange;
    }

    @Override
    public Object[] toArray()
    {
        final Object[] array = new Object[ collection.size() ];
        int      i     = 0;

        for( final R e : this ) {
            array[ i++ ] = e;
            }

        return array;
    }

    @Override
    public <T> T[] toArray( final T[] a )
    {
        if( a.length >= collection.size() ) {
            int i = 0;

            for( final R entry : this ) {
                @SuppressWarnings("unchecked")
                final
                T tEntry = (T)entry; // $codepro.audit.disable unnecessaryCast
                a[ i++ ] = tEntry;
                }

            return a;
            }
        else {
            final List<T> list = new ArrayList<>( collection.size() );

            for( final R entry : this ) {
                @SuppressWarnings("unchecked")
                final
                T tEntry = (T)entry; // $codepro.audit.disable unnecessaryCast
                list.add( tEntry );
                }

            return list.toArray(a);
            }
    }
}

