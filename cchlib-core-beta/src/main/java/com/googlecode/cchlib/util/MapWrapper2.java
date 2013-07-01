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
    static final long serialVersionUID = 1L;
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
    public boolean add( R e )
    {
        return collection.add( unwrapper.wrap( e ) );
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
        collection.clear();
    }

    @Override
    public boolean contains( Object o )
    {
        @SuppressWarnings("unchecked")
        R r = (R)o;
        return collection.contains( unwrapper.wrap( r ) );
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
        return collection.isEmpty();
    }

    @Override
    public Iterator<R> iterator()
    {
        return new IteratorWrapper<S,R>( collection.iterator(), wrapper );
    }

    @Override
    public boolean remove( Object o )
    {
        @SuppressWarnings("unchecked")
        R r = (R)o;
        return collection.remove( unwrapper.wrap( r ) );
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
        return collection.size();
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
        Object[] array = new Object[ collection.size() ];
        int      i     = 0;

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

            for( R e : this ) {
                @SuppressWarnings("unchecked")
                T t = (T)e;
                a[ i++ ] = t;
                }

            return a;
            }
        else {
            List<T> list = new ArrayList<T>( collection.size() );

            for( R e : this ) {
                @SuppressWarnings("unchecked")
                T t = (T)e;
                list.add( t );
                }

            return list.toArray(a);
            }
    }
}

