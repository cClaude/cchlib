package com.googlecode.cchlib.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Give a view of a {@link Map} using real-time computed keys
 * <p><u>Simple example:</u></p>
 * <p>
 * You have a <code>Map&lt;String,HashCode&gt;</code>
 * but you need to access to map values using
 * a key build on File object.<br/>
 * <code>MapKeyWrapper&ltString,File,HashCode&gt;</code> is what you need.
 * </p>
 * <p>
 * MapKeyWrapper will no create a copy of original {@link Map} but a view on it.
 * </p>
 * @param <KS> Original (source) key type
 * @param <KR> Mapped (result) key type
 * @param <V> content type (same for both maps)
 * @since 4.1.7
 */
public class MapKeyWrapper<KS,KR,V>
    implements Map<KR,V>
{
    private Map<KS,V> map;
    private Wrappable<KS,KR> wrapper;
    private Wrappable<KR,KS> unwrapper;

    /**
     * Create a MapWrapper from a map
     *
     * @param map       {@link Map} with original keys/values
     * @param wrapper   A wrapper able to compute mapped keys from original keys
     * @param unwrapper A wrapper able to compute original keys from mapped keys
     */
    public MapKeyWrapper(
            final Map<KS,V> map,
            final Wrappable<KS,KR> wrapper,
            final Wrappable<KR,KS> unwrapper
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
        @SuppressWarnings("unchecked")
        KR k = (KR)key; // $codepro.audit.disable unnecessaryCast
        return map.containsKey( unwrapper.wrappe( k ) );
    }

    @Override
    public boolean containsValue( Object value )
        throws UnsupportedOperationException
    {
        return map.containsValue( value );
    }

    @Override
    public Set<Map.Entry<KR,V>> entrySet()
    {
        return new SetWrapper<Map.Entry<KS,V>,Map.Entry<KR,V>>(
                map.entrySet(),
                new EntryWrapper<KS,KR,V>( wrapper ),
                new EntryWrapper<KR,KS,V>( unwrapper )
            );
    }

    @Override
    public V get( Object key )
    {
        @SuppressWarnings("unchecked")
        KR k = (KR)key; // $codepro.audit.disable unnecessaryCast

        return map.get( unwrapper.wrappe( k ) );
    }

    @Override
    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    @Override
    public Set<KR> keySet()
    {
        return new SetWrapper<KS,KR>( map.keySet(), wrapper, unwrapper );
    }

    @Override
    public V put( KR key, V value )
        throws UnsupportedOperationException
    {
        return map.put( unwrapper.wrappe( key ), value );
    }

    @Override
    public void putAll( Map<? extends KR, ? extends V> m )
        throws UnsupportedOperationException
    {
        for( Map.Entry<? extends KR, ? extends V> e : m.entrySet() ) {
            put( e.getKey(), e.getValue() );
            }
    }

    @Override
    public V remove( Object key )
        throws UnsupportedOperationException
    {
        @SuppressWarnings("unchecked")
        KR k = (KR)key; // $codepro.audit.disable unnecessaryCast
        return map.remove( unwrapper.wrappe( k ) );
    }

    @Override
    public int size()
    {
        return map.size();
    }

    @Override
    public Collection<V> values()
    {
        return map.values();
    }

    private class EntryWrapper<EK0,EK1,EV>
        implements Wrappable<Map.Entry<EK0,EV>,Map.Entry<EK1,EV>>
    {
        private final Wrappable<EK0,EK1> ewrapper;

        public EntryWrapper( final Wrappable<EK0,EK1> ewrapper )
        {
            this.ewrapper = ewrapper;
        }
        @Override
        public Map.Entry<EK1,EV> wrappe( final Map.Entry<EK0,EV> o )
        {
            return new WrappedEntry( o );
        }

        private final class WrappedEntry implements Map.Entry<EK1, EV>
        {
            private final Entry<EK0, EV> o;

            private WrappedEntry( Entry<EK0, EV> o )
            {
                this.o = o;
            }

            @Override
            public EK1 getKey()
            {
                return ewrapper.wrappe( o.getKey() );
            }

            @Override
            public EV getValue()
            {
                return o.getValue();
            }

            @Override
            public EV setValue( EV value )
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
            public boolean equals( Object obj )
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
