package com.googlecode.cchlib.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Give a view of a {@link Map} using real-time computed values
 * <p><u>Simple example:</u></p>
 * <p>
 * You have a <code>Map&lt;HashCode,String&gt;</code>
 * but you need a File instead of a String has value.<br/>
 * <code>MapWrapper&ltHashCode,String,File&gt;</code> is what you need.
 * </p>
 * <p>
 * HashCode will no create a copy of original {@link Map} but a view on it.
 * </p>
 * @param <K> Key (same for both maps)
 * @param <VS> Original (source) values type
 * @param <VR> Mapped (result) values type
 * @since 4.1.7
 */
public class MapWrapper<K,VS,VR>
    implements Map<K,VR>
{
    private Map<K,VS> map;
    private Wrappable<VS,VR> wrapper;
    private Wrappable<VR,VS> unwrapper;

    /**
     * Create a MapWrapper from a map
     *
     * @param map       {@link Map} with original keys/values
     * @param wrapper   A wrapper able to compute mapped values from original values
     * @param unwrapper A wrapper able to compute original values from mapped values
     */
    public MapWrapper(
            final Map<K,VS>        map,
            final Wrappable<VS,VR> wrapper,
            final Wrappable<VR,VS> unwrapper
            )
    {
        this.map = map;
        this.wrapper = wrapper;
        this.unwrapper = unwrapper;
    }

    @Override
    public void clear()
    {
        map.clear();
    }

    @Override
    public boolean containsKey( Object key )
        throws UnsupportedOperationException
    {
        return map.containsKey( key );
    }

    @Override
    public boolean containsValue( Object value )
        throws UnsupportedOperationException
    {
        @SuppressWarnings("unchecked")
        VR v = (VR)value; // $codepro.audit.disable unnecessaryCast
        return map.containsValue( unwrapper.wrappe( v ) );
    }

    @Override
    public Set<Map.Entry<K,VR>> entrySet()
    {
        return new SetWrapper<Map.Entry<K,VS>,Map.Entry<K,VR>>(
                map.entrySet(),
                new EntryWrapper<K,VS,VR>( wrapper ),
                new EntryWrapper<K,VR,VS>( unwrapper )
            );
    }

    @Override
    public VR get( Object key )
    {
        return wrapper.wrappe(  map.get( key ) );
    }

    @Override
    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    @Override
    public Set<K> keySet()
    {
        return map.keySet();
    }

    @Override
    public VR put( K key, VR value )
        throws UnsupportedOperationException
    {
        VS prev = map.put( key, unwrapper.wrappe( value ) );

        return wrapper.wrappe( prev );
    }

    @Override
    public void putAll( Map<? extends K, ? extends VR> m )
        throws UnsupportedOperationException
    {
        for( Map.Entry<? extends K, ? extends VR> e : m.entrySet() ) {
            put( e.getKey(), e.getValue() );
            }
    }

    @Override
    public VR remove( Object key )
        throws UnsupportedOperationException
    {
        return wrapper.wrappe( map.remove( key ) );
    }

    @Override
    public int size()
    {
        return map.size();
    }

    @Override
    public Collection<VR> values()
    {
        return new CollectionWrapper<VS,VR>( map.values(), wrapper, unwrapper );
    }

    public class EntryWrapper<KEY,V0,V1>
        implements Wrappable<Entry<KEY,V0>,Entry<KEY,V1>>
    {
        private Wrappable<V0,V1> ewrapper;

        public EntryWrapper( Wrappable<V0,V1> ewrapper )
        {
            this.ewrapper = ewrapper;
        }

        @Override
        public Map.Entry<KEY,V1> wrappe( Map.Entry<KEY,V0> o )
                throws WrappeException
        {
            return new WrappedEntry( o );
        }

        private final class WrappedEntry implements Map.Entry<KEY,V1>
        {
            private final Map.Entry<KEY,V0> o;

            private WrappedEntry( Map.Entry<KEY,V0> o )
            {
                this.o = o;
            }

            @Override
            public KEY getKey()
            {
                return o.getKey();
            }

            @Override
            public V1 getValue()
            {
                return ewrapper.wrappe( o.getValue() );
            }

            @Override
            public V1 setValue( V1 value )
            {
                throw new UnsupportedOperationException();
            }

            @Override
            public int hashCode()
            {
                final int prime = 31;
                int result = 1;
                result = prime * result + getOuterType().hashCode();
                result = prime * result + ((o == null) ? 0 : o.hashCode());
                return result;
            }

            @Override
            public boolean equals( Object obj ) // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.obeyEqualsContract.obeyGeneralContractOfEquals
            {
                if( this == obj ) {
                    return true;
                    }
                if( obj == null ) {
                    return false;
                    }
                if( getClass() != obj.getClass() ) {
                    return false;
                    }
                @SuppressWarnings("unchecked")
                WrappedEntry other = (WrappedEntry)obj;
                if( !getOuterType().equals( other.getOuterType() ) ) {
                    return false;
                    }
                if( o == null ) {
                    if( other.o != null ) {
                        return false;
                        }
                    }
                else if( !o.equals( other.o ) ) {
                    return false;
                    }
                return true;
            }

            private EntryWrapper<?,?,?> getOuterType()
            {
                return EntryWrapper.this;
            }
        }
    }
}
