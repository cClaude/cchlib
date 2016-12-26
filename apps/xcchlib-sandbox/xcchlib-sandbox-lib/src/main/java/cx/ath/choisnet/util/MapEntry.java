package cx.ath.choisnet.util;

import java.util.Map;

/**
 * Implementation minimum de l'interface {@link java.util.Map.Entry}
 *
 * @since 3.02
 */
public class MapEntry<K,V> implements Map.Entry<K,V>
{
    final K key;
    final V value;

    public MapEntry( final K key, final V value )
    {
        this.key   = key;
        this.value = value;
    }

    @Override
    public K getKey()
    {
        return this.key;
    }

    @Override
    public V getValue()
    {
        return this.value;
    }

    @Override
    public V setValue( final V value )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals( final Object o )
    {
        if( o == null ) {
            return false;
            }

        if( o instanceof MapEntry ) {
            final MapEntry<?,?> anOtherMapEntry = MapEntry.class.cast( o );

            return
                    (
                        this.getKey() == null ?
                            anOtherMapEntry.getKey() == null
                            :
                            this.getKey().equals( anOtherMapEntry.getKey() )
                    )
                    &&
                    (
                        this.getValue() == null ?
                            anOtherMapEntry.getValue() == null
                            :
                            this.getValue().equals( anOtherMapEntry.getValue() )
                    );            }
        else {
            return false;
            }
    }

    @Override
    public int hashCode()
    {
        return
            (this.getKey()==null   ? 0 : this.getKey().hashCode())
            ^
            (this.getValue()==null ? 0 : this.getValue().hashCode());
    }
}
