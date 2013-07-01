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
    private Set<S> set;
    private Wrappable<S,R> wrapper;
    private Wrappable<R,S> unwrapper;

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
    public boolean add( R e )
    {
        return set.add( unwrapper.wrap( e ) );
    }

    @Override
    public boolean addAll( Collection<? extends R> c )
    {
        boolean setChange = false;

        for( R e : c ) {
            boolean res = add( e );

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
    public boolean contains( Object o )
    {
        @SuppressWarnings("unchecked")
        R r = (R)o; // $codepro.audit.disable unnecessaryCast
        return set.contains( unwrapper.wrap( r ) );
    }

    @Override
    public boolean containsAll( Collection<?> c )
    {
        for( Object o : c ) {
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
        return new IteratorWrapper<S,R>( set.iterator(), wrapper );
    }

    @Override
    public boolean remove( Object o )
    {
        @SuppressWarnings("unchecked")
        R r = (R)o; // $codepro.audit.disable unnecessaryCast
        return set.remove( unwrapper.wrap( r ) );
    }

    @Override
    public boolean removeAll( Collection<?> c )
    {
        boolean setChange = false;

        for( Object e : c ) {
            boolean res = remove( e );

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
    public boolean retainAll( Collection<?> c )
    {
        boolean setChange = false;

        for( Object e : c ) {
            if( contains( e ) ) {
                boolean res = remove( e );

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
        Object[] array = new Object[ set.size() ];
        int      i     = 0;

        for( R e : this ) {
            array[ i++ ] = e;
            }

        return array;
    }

    @Override
    public <T> T[] toArray( T[] a )
    {
        if( a.length >= set.size() ) {
            int i = 0;

            for( R e : this ) {
                @SuppressWarnings("unchecked")
                T t = (T)e; // $codepro.audit.disable unnecessaryCast
                a[ i++ ] = t;
                }

            return a;
            }
        else {
            List<T> list = new ArrayList<T>( set.size() );

            for( R e : this ) {
                @SuppressWarnings("unchecked")
                T t = (T)e; // $codepro.audit.disable unnecessaryCast
                list.add( t );
                }

            return list.toArray(a);
            }
    }
}

