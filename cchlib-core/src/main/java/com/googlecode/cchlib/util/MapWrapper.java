package com.googlecode.cchlib.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Give a view of a {@link Map} using real-time computed values
 * <p>
 * <u>Simple example:</u>
 * <p>
 * You have a {@code Map&lt;HashCode,String&gt;} but you need a File instead
 * of a String has value.<br>
 * {@code MapWrapper&lt;HashCode,String,File&gt;} is what you need.
 * <p>
 * HashCode will no create a copy of original {@link Map} but a view on it.
 *
 * @param <K>
 *            Key (same for both maps)
 * @param <VS>
 *            Original (source) values type
 * @param <VR>
 *            Mapped (result) values type
 * @since 4.1.7
 */
@SuppressWarnings("squid:S00119")
public class MapWrapper<K,VS,VR>
    implements Map<K,VR>
{
    private final Map<K,VS>        map;
    private final Wrappable<VS,VR> wrapper;
    private final Wrappable<VR,VS> unwrapper;

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
        this.map.clear();
    }

    @Override
    public boolean containsKey( final Object key )
    {
        return this.map.containsKey( key );
    }

    @Override
    public boolean containsValue( final Object value )
    {
        @SuppressWarnings("unchecked")
        final
        VR v = (VR)value; // $codepro.audit.disable unnecessaryCast
        return this.map.containsValue( this.unwrapper.wrap( v ) );
    }

    @Override
    public Set<Map.Entry<K,VR>> entrySet()
    {
        return new SetWrapper<>(
                this.map.entrySet(),
                new EntryWrapper<>( this.wrapper ),
                new EntryWrapper<>( this.unwrapper )
            );
    }

    @Override
    public VR get( final Object key )
    {
        return this.wrapper.wrap(  this.map.get( key ) );
    }

    @Override
    public boolean isEmpty()
    {
        return this.map.isEmpty();
    }

    @Override
    public Set<K> keySet()
    {
        return this.map.keySet();
    }

    @Override
    public VR put( final K key, final VR value )
    {
        final VS prev = this.map.put( key, this.unwrapper.wrap( value ) );

        return this.wrapper.wrap( prev );
    }

    @Override
    public void putAll( final Map<? extends K, ? extends VR> m )
    {
        for( final Map.Entry<? extends K, ? extends VR> e : m.entrySet() ) {
            put( e.getKey(), e.getValue() );
            }
    }

    @Override
    public VR remove( final Object key )
    {
        return this.wrapper.wrap( this.map.remove( key ) );
    }

    @Override
    public int size()
    {
        return this.map.size();
    }

    @Override
    public Collection<VR> values()
    {
        return new CollectionWrapper<>( this.map.values(), this.wrapper, this.unwrapper );
    }

    public static class EntryWrapper<KEY,V0,V1>
        implements Wrappable<Entry<KEY,V0>,Entry<KEY,V1>>
    {
        private final Wrappable<V0,V1> ewrapper;

        public EntryWrapper( final Wrappable<V0,V1> ewrapper )
        {
            this.ewrapper = ewrapper;
        }

        @Override
        public Map.Entry<KEY,V1> wrap( final Map.Entry<KEY,V0> o )
        {
            return new WrappedEntry( o );
        }

        private final class WrappedEntry implements Map.Entry<KEY,V1>
        {
            private final Map.Entry<KEY,V0> o;

            private WrappedEntry( final Map.Entry<KEY,V0> o )
            {
                this.o = o;
            }

            @Override
            public KEY getKey()
            {
                return this.o.getKey();
            }

            @Override
            public V1 getValue()
            {
                return EntryWrapper.this.ewrapper.wrap( this.o.getValue() );
            }

            @Override
            public V1 setValue( final V1 value )
            {
                throw new UnsupportedOperationException();
            }

            @Override
            public int hashCode()
            {
                final int prime = 31;
                int result = 1;
                result = (prime * result) + getOuterType().hashCode();
                result = (prime * result) + ((this.o == null) ? 0 : this.o.hashCode());
                return result;
            }

            @Override
            public boolean equals( final Object obj )
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
                final
                WrappedEntry other = (WrappedEntry)obj;
                if( !getOuterType().equals( other.getOuterType() ) ) {
                    return false;
                    }
                if( this.o == null ) {
                    if( other.o != null ) {
                        return false;
                        }
                    }
                else if( !this.o.equals( other.o ) ) {
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
