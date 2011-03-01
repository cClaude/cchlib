package cx.ath.choisnet.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import cx.ath.choisnet.util.iterator.IteratorWrapper;

/**
 *
 * @author Claude CHOISNET
 * @param <T> 
 * @param <O> 
 *
 */
public class WrapperHelper<T,O>
{
    private WrapperHelper()
    {//All static
    }

//    static class DefaultValueWrapper<T,O> implements Wrappable<T,O>
//    {
//        private Wrappable<T,O> wrapper;
//        private O defValue;
//
//        public DefaultValueWrapper(Wrappable<T,O> wrapper, O defValue)
//        {
//            this.wrapper  = wrapper;
//            this.defValue = defValue;
//        }
//
//        public O wrappe(T o)
//        {
//            try {
//                return wrapper.wrappe(o);
//            }
//            catch(Exception e) {
//                return defValue;
//            }
//        }
//    }
    
//    private static final Wrappable<String,Long> WRAPPESTRINGTOLONG = new Wrappable<String,Long>() 
//    {
//        public Long wrappe(String o)
//        {
//            return Long.valueOf( Long.parseLong(o) );
//        }
//    };

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

//    public static final Wrappable<String,Long> wrappeStringToLong()
//    {
//        return WRAPPESTRINGTOLONG;
//    }
//
//    public static final Wrappable<String,Long> wrappeString(Long defValue)
//    {
//        return new DefaultValueWrapper<String,Long>(WRAPPESTRINGTOLONG, defValue);
//    }

//    public static final Wrappable<String,Integer> wrappeStringToInteger()
//    {
//        return new Wrappable<String,Integer>() {
//
//            public Integer wrappe(String o)
//            {
//                return Integer.valueOf(Integer.parseInt(o));
//            }
//        };
//
//    }
//
//    public static final Wrappable<String,Integer> wrappeString(Integer defValue)
//    {
//        return new DefaultValueWrapper<String,Integer>(WrapperHelper.wrappeStringToInteger(), defValue);
//    }
//
//    public static final Wrappable<String,Float> wrappeStringToFloat()
//    {
//        return new Wrappable<String,Float>() {
//
//            public Float wrappe(String o)
//            {
//                return Float.valueOf(Float.parseFloat(o));
//            }
//        };
//
//    }
//
//    public static final Wrappable<String,Float> wrappeString(Float defValue)
//    {
//        return new DefaultValueWrapper<String,Float>( WrapperHelper.wrappeStringToFloat(), defValue );
//    }
//
//    public static final Wrappable<String,Double> wrappeStringToDouble()
//    {
//        return new Wrappable<String,Double>() {
//
//            public Double wrappe(String o)
//            {
//                return Double.valueOf(Double.parseDouble(o));
//            }
//        };
//
//    }
//
//    public static final Wrappable<String,Double> wrappeString(Double defValue)
//    {
//        return new DefaultValueWrapper<String,Double>(WrapperHelper.wrappeStringToDouble(), defValue);
//    }
    
    
    // TODO: TestCase needed !
    @Deprecated // Move to a new class ! (new package?)
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
                        // TODO ! NEED BEST TEST !
                        throw new ArrayStoreException();
                    }
                }

                return array;
            }
        };
    }
    
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
