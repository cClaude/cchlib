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
 * a key build on File object.<br>
 * <code>MapKeyWrapper&lt;String,File,HashCode&gt;</code> is what you need.
 * </p>
 * <p>
 * MapKeyWrapper will no create a copy of original {@link Map} but a view on it.
 * </p>
 * @param <KS> Original (source) key type
 * @param <KR> Mapped (result) key type
 * @param <V> content type (same for both maps)
 * @since 4.1.7
 */
@SuppressWarnings("squid:S00119")
public class MapKeyWrapper<KS,KR,V>
    implements Map<KR,V>
{
    private final Map<KS,V> map;
    private final Wrappable<KS,KR> wrapper;
    private final Wrappable<KR,KS> unwrapper;

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
        this.map.clear();
    }

    @Override
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public boolean containsKey( final Object key )
        throws UnsupportedOperationException
    {
        @SuppressWarnings("unchecked")
        final
        KR k = (KR)key; // $codepro.audit.disable unnecessaryCast
        return this.map.containsKey( this.unwrapper.wrap( k ) );
    }

    @Override
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public boolean containsValue( final Object value )
        throws UnsupportedOperationException
    {
        return this.map.containsValue( value );
    }

    @Override
    public Set<Map.Entry<KR,V>> entrySet()
    {
        return new SetWrapper<>(
                this.map.entrySet(),
                new EntryWrapper<>( this.wrapper ),
                new EntryWrapper<>( this.unwrapper )
            );
    }

    @Override
    public V get( final Object key )
    {
        @SuppressWarnings("unchecked")
        final
        KR k = (KR)key; // $codepro.audit.disable unnecessaryCast

        return this.map.get( this.unwrapper.wrap( k ) );
    }

    @Override
    public boolean isEmpty()
    {
        return this.map.isEmpty();
    }

    @Override
    public Set<KR> keySet()
    {
        return new SetWrapper<>( this.map.keySet(), this.wrapper, this.unwrapper );
    }

    @Override
    public V put( final KR key, final V value )
    {
        return this.map.put( this.unwrapper.wrap( key ), value );
    }

    @Override
    public void putAll( final Map<? extends KR, ? extends V> m )
    {
        for( final Map.Entry<? extends KR, ? extends V> e : m.entrySet() ) {
            put( e.getKey(), e.getValue() );
            }
    }

    @Override
    public V remove( final Object key )
    {
        @SuppressWarnings("unchecked")
        final
        KR k = (KR)key; // $codepro.audit.disable unnecessaryCast
        return this.map.remove( this.unwrapper.wrap( k ) );
    }

    @Override
    public int size()
    {
        return this.map.size();
    }

    @Override
    public Collection<V> values()
    {
        return this.map.values();
    }

    @SuppressWarnings("squid:S00119")
    private static class EntryWrapper<EK0,EK1,EV>
        implements Wrappable<Map.Entry<EK0,EV>,Map.Entry<EK1,EV>>
    {
        private final Wrappable<EK0,EK1> ewrapper;

        public EntryWrapper( final Wrappable<EK0,EK1> ewrapper )
        {
            this.ewrapper = ewrapper;
        }

        @Override
        public Map.Entry<EK1,EV> wrap( final Map.Entry<EK0,EV> o )
        {
            return new WrappedEntry( o );
        }

        private final class WrappedEntry implements Map.Entry<EK1, EV>
        {
            private final Entry<EK0, EV> o;

            private WrappedEntry( final Entry<EK0, EV> o )
            {
                this.o = o;
            }

            @Override
            public EK1 getKey()
            {
                return EntryWrapper.this.ewrapper.wrap( this.o.getKey() );
            }

            @Override
            public EV getValue()
            {
                return this.o.getValue();
            }

            @Override
            public EV setValue( final EV value )
            {
                throw new UnsupportedOperationException();
            }

            @Override
            public int hashCode()
            {
                final int prime = 31; // $codepro.audit.disable
                int result = 1;
                result = (prime * result) + getOuterType().hashCode();
                result = (prime * result) + ((this.o == null) ? 0 : this.o.hashCode());
                return result;
            }

            @Override
            public boolean equals( final Object obj ) // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.obeyEqualsContract.obeyGeneralContractOfEquals
            {
                if( this == obj ) {
                    return true;
                    }
                if( obj == null ) {
                    return false;
                    }
                if( getClass() != obj.getClass() ) { // $codepro.audit.disable useEquals
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
