package cx.ath.choisnet.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @deprecated use {@link com.googlecode.cchlib.util.MapKeyWrapper} instead
 */
@Deprecated
public class MapKeyWrapper<KS,KR,V>
    implements Map<KR,V>
{
    private Map<KS,V> map;
    private Wrappable<KS,KR> wrapper;
    private Wrappable<KR,KS> unwrapper;


    /**
     * Create a MapWrapper from a map
     *
     * @param map Map to wrap
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

    /**
     * 
     */
    @Override
    public boolean containsKey( Object key )
        throws UnsupportedOperationException
    {
        @SuppressWarnings("unchecked")
        KR k = (KR)key; // $codepro.audit.disable unnecessaryCast
        return map.containsKey( unwrapper.wrappe( k ) );
    }

    /**
     * 
     */
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

    /**
     * 
     */
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

    /**
     * 
     */
    @Override
    public V put( KR key, V value )
        throws UnsupportedOperationException
    {
        return map.put( unwrapper.wrappe( key ), value );
    }

    /**
     * 
     */
    @Override
    public void putAll( Map<? extends KR, ? extends V> m )
        throws UnsupportedOperationException
    {
        for( Map.Entry<? extends KR, ? extends V> e : m.entrySet() ) {
            put( e.getKey(), e.getValue() );
        }
    }

    /**
     * 
     */
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

    /**
     * @deprecated
     */
    @Deprecated
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
            return new Map.Entry<EK1,EV>()
            {
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
                    return (getKey()==null   ? 0 : getKey().hashCode()) ^
                           (getValue()==null ? 0 : getValue().hashCode());
                 }

                @Override
                public boolean equals(Object obj) { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.obeyEqualsContract.obeyGeneralContractOfEquals
                    if (obj == null) {
                        return false;
                    }
                    if (getClass() != obj.getClass()) {
                        return false;
                    }
                    // F IXME: TO DO !!! equals !
                    return true;
                }
            };
        }
    }
}
