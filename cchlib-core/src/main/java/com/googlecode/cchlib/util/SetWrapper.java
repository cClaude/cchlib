package com.googlecode.cchlib.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.googlecode.cchlib.util.iterator.IteratorWrapper;

/**
 * Give a view of a {@link Set} using real-time computed values
 *
 * @param <S> Original (source) content type
 * @param <R> Mapped (result) content type
 * @since 4.1.7
 */
public class SetWrapper<S,R> implements Set<R>, Serializable
{
    static final long serialVersionUID = 1L;
    private final Set<S> set;
    private final Wrappable<S,R> wrapper;
    private final Wrappable<R,S> unwrapper;

    /**
     * Create a CollectionWrapper
     *
     * @param set       Original {@link Set}
     * @param wrapper   A wrapper able to compute mapped values from original values
     * @param unwrapper A wrapper able to compute original values from mapped values
     */
    public SetWrapper(
        final Set<S>         set,
        final Wrappable<S,R> wrapper,
        final Wrappable<R,S> unwrapper
        )
    {
        this.set = set;
        this.wrapper = wrapper;
        this.unwrapper = unwrapper;
    }

    @Override
    public boolean add( final R e )
    {
        return set.add( unwrapper.wrap( e ) );
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
        set.clear();
    }

    @Override
    public boolean contains( final Object o )
    {
        @SuppressWarnings("unchecked")
        final R value = (R)o; // $codepro.audit.disable unnecessaryCast
        return set.contains( unwrapper.wrap( value ) );
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
        return set.isEmpty();
    }

    @Override
    public Iterator<R> iterator()
    {
        return new IteratorWrapper<>( set.iterator(), wrapper );
    }

    @Override
    public boolean remove( final Object o )
    {
        @SuppressWarnings("unchecked")
        final R value = (R)o; // $codepro.audit.disable unnecessaryCast
        return set.remove( unwrapper.wrap( value ) );
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
        return set.size();
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
        final Object[] array = new Object[ set.size() ];
        int      i     = 0;

        for( final R e : this ) {
            array[ i++ ] = e;
            }

        return array;
    }

    @Override
    public <T> T[] toArray( final T[] a )
    {
        if( a.length >= set.size() ) {
            int i = 0;

            for( final R e : this ) {
                @SuppressWarnings("unchecked")
                final T value = (T)e; // $codepro.audit.disable unnecessaryCast
                a[ i++ ] = value;
                }

            return a;
            }
        else {
            final List<T> list = new ArrayList<>( set.size() );

            for( final R e : this ) {
                @SuppressWarnings("unchecked")
                final T value = (T)e; // $codepro.audit.disable unnecessaryCast
                list.add( value );
                }

            return list.toArray( a );
            }
    }
}

