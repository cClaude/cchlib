package com.googlecode.cchlib.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.util.iterator.IteratorWrapper;

// already exist
public class MapWrapper2<S,R> implements Collection<R>,Serializable
{
    private static final long serialVersionUID = 2L;

    private Collection<S>  collection;
    private Wrappable<S,R> wrapper;
    private Wrappable<R,S> unwrapper;

    public MapWrapper2(
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
        collection.clear();
    }

    @Override
    public boolean contains( final Object o )
    {
        @SuppressWarnings("unchecked")
        R rO = (R)o;
        return collection.contains( unwrapper.wrap( rO ) );
    }

    @Override
    public boolean containsAll( final Collection<?> c )
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
        return collection.isEmpty();
    }

    @Override
    public Iterator<R> iterator()
    {
        return new IteratorWrapper<S,R>( collection.iterator(), wrapper );
    }

    @Override
    public boolean remove( final Object o )
    {
        @SuppressWarnings("unchecked")
        R rO = (R)o;
        return collection.remove( unwrapper.wrap( rO ) );
    }

    @Override
    public boolean removeAll( Collection<?> c )
    {
        boolean setChange = false;

        for( final Object e : c ) {
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
        return collection.size();
    }

    @Override
    public boolean retainAll( Collection<?> c )
    {
        boolean setChange = false;

        for( final Object e : c ) {
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
        final Object[] array = new Object[ collection.size() ];
        int            i     = 0;

        for( R e : this ) {
            array[ i++ ] = e;
            }

        return array;
    }

    @Override
    public <T> T[] toArray( T[] a )
    {
        if( a.length >= collection.size() ) {
            int i = 0;

            for( final R e : this ) {
                @SuppressWarnings("unchecked")
                T tE = (T)e;
                a[ i++ ] = tE;
                }

            return a;
            }
        else {
            final List<T> list = new ArrayList<T>( collection.size() );

            for( final R e : this ) {
                @SuppressWarnings("unchecked")
                T tE = (T)e;
                list.add( tE );
                }

            return list.toArray( a );
            }
    }
}

