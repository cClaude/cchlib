/**
 * 
 */
package cx.ath.choisnet.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import cx.ath.choisnet.ToDo;
import cx.ath.choisnet.util.iterator.IteratorWrapper;

/**
 * TODO: Doc!
 * @author Claude CHOISNET
 */
@ToDo
public class SetWrapper<S,R> implements Set<R>
{
    private Set<S> set;
    private Wrappable<S,R> wrapper;
    private Wrappable<R,S> unwrapper;

    /**
     * 
     * @param set
     * @param wrapper
     * @param unwrapper
     */
    public SetWrapper(
        final Set<S> set,
        final Wrappable<S,R> wrapper,
        final Wrappable<R,S> unwrapper
        )
    {
        this.set = set;
        this.wrapper = wrapper;
        this.unwrapper = unwrapper;
    }

    /**
     * TODO: Doc!
     */
    @Override
    public boolean add( R e )
    {
        return set.add( unwrapper.wrappe( e ) );
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

    /**
     * TODO: Doc!
     */
    @Override
    public boolean contains( Object o )
    {
        @SuppressWarnings("unchecked")
        R r = (R)o;
        return set.contains( unwrapper.wrappe( r ) );
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

    /**
     * TODO: Doc!
     */
    @Override
    public boolean remove( Object o )
    {
        @SuppressWarnings("unchecked")
        R r = (R)o;
        return set.remove( unwrapper.wrappe( r ) );
    }

    /**
     * TODO: Doc!
     */
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
                T t = (T)e;
                a[ i++ ] = t;
                }

            return a;
            }
        else {
            List<T> list = new ArrayList<T>( set.size() );

            for( R e : this ) {
                @SuppressWarnings("unchecked")
                T t = (T)e;
                list.add( t );
                }

            return list.toArray(a);
            }
    }
}

