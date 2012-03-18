package cx.ath.choisnet.util;

import java.util.Enumeration;

/**
 * @deprecated use {@link com.googlecode.cchlib.util.WrapperHelper} instead
 */
public class WrapperHelper<T,O>
{
    private WrapperHelper()
    {//All static
    }

    /**
     * Create a Wrappable object using Object.toString()
     * @param <T> type to wrap
     * @return a wrapper
     */
    public static final <T> Wrappable<T,String> wrappeToString()
    {
        return new Wrappable<T,String>()
        {
            public String wrappe(T o)
            {
                return o.toString();
            }
        };
    }
/*
    // TO DO: TestCase needed !
    x
    @ Deprecated // Move to a new class ! (new package?)
    public static final <E,O> Collection<O> wrappeCollection(
            final Collection<E>   collection,
            final Wrappable<E,O>  wrapper
            )
    {
        return new Collection<O>()
        {
            @Override
            public boolean add( O arg0 )
            {
                throw new UnsupportedOperationException();
            }
            @Override
            public boolean addAll( Collection<? extends O> arg0 )
            {
                throw new UnsupportedOperationException();
            }
            @Override
            public void clear()
            {
                throw new UnsupportedOperationException();
            }
            @Override
            public boolean contains( Object o )
            {
                Iterator<O> iter = iterator();

                while( iter.hasNext() ) {
                    O e = iter.next();

                    if(o==null ? e==null : o.equals(e)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean containsAll( Collection<?> c )
            {
                for( Object o : c ) {
                    if( !contains(o) ) {
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
            public Iterator<O> iterator()
            {
                return new IteratorWrapper<E,O>(collection,wrapper);
            }
            @Override
            public boolean remove( Object o )
            {
                throw new UnsupportedOperationException();
            }
            @Override
            public boolean removeAll( Collection<?> c )
            {
                throw new UnsupportedOperationException();
            }
            @Override
            public boolean retainAll( Collection<?> c )
            {
                throw new UnsupportedOperationException();
            }
            @Override
            public int size()
            {
                return collection.size();
            }
            @SuppressWarnings("unchecked")
            @Override
            public Object[] toArray()
            {
                final Object[] objs   = collection.toArray();
                final Object[] result = new Object[objs.length];

                for(int i=0;i<objs.length;i++) {
                    result[i] = wrapper.wrappe( (E)objs[i] );
                }

                return result;
            }
            @Override
            public <X> X[] toArray( X[] a )
            {
                if( a == null) {
                    throw new NullPointerException();
                }

                X[] array;

                if( a.length >= size()) {
                    array = a;

                    if( a.length > size() ) {
                        Arrays.fill( array, null );
                    }
                }
                else {
                    // Doing: array = new T[ size() ];
                    Class<?> clazz = a.getClass().getComponentType();
                    Object   arr   = Array.newInstance(clazz, size());

                    @SuppressWarnings("unchecked")
                    X[] unckeckarray = (X[])arr;
                    array = unckeckarray;
                }

                Iterator<O> iter = iterator();
                int         i    = 0;

                while(iter.hasNext()) {
                    O next = iter.next();

                    try {
                        @SuppressWarnings("unchecked")
                        X uncheck = (X)next;
                        array[ i ] = uncheck;
                    	}
                    catch( NullPointerException e ) {
                        // TO DO ! NEED BESTER TESTS !
                        x
                        throw new ArrayStoreException();
                    	}
                	}

                return array;
            }
        };
    }
*/
    /**
     * Wrap an Enumeration.
     *
     * @param <E> Type off the giving Enumeration
     * @param <O> Type off the returning Enumeration
     * @param enumeration Enumeration to wrap
     * @param wrapper     Wrapper to use
     * @return an new Enumeration consuming the giving while running
     */
    public static final <E,O> Enumeration<O> wrappeEnumeration(
            final Enumeration<E>    enumeration,
            final Wrappable<E,O>    wrapper
            )
    {
        return new Enumeration<O>()
        {
            @Override
            public boolean hasMoreElements()
            {
                return enumeration.hasMoreElements();
            }
            @Override
            public O nextElement()
            {
                 return wrapper.wrappe( enumeration.nextElement() );
            }
        };
    }
}
