/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/MapEntry.java
** Description   :
**
**  3.02.029 2006.07.20 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.MapEntry
**
*/
package cx.ath.choisnet.util;

import java.util.Map;

/**
** Implémentation minimum de l'interface {@link java.util.Map.Entry}
**
** @author Claude CHOISNET
** @since   3.02.029
** @version 3.02.029
**
*/
public class MapEntry<K,V>
    implements Map.Entry<K,V>
{
    final K key;
    final V value;

    /**
    **
    */
    public MapEntry( final K key, final V value ) // ----------------------
    {
        this.key   = key;
        this.value = value;
    }

    /**
    **
    */
    public K getKey() // --------------------------------------------------
    {
        return this.key;
    }

    /**
    **
    */
    public V getValue() // ------------------------------------------------
    {
        return this.value;
    }

    /**
    **
    */
    public V setValue( final V value ) // ---------------------------------
    {
        throw new UnsupportedOperationException();
    }

    /**
    **
    */
    public boolean equals( final Object o ) // ----------------------------
    {
        if( o == null ) {
            return false;
            }

        MapEntry anOtherMapEntry;

        try {
            anOtherMapEntry = MapEntry.class.cast( o );
            }
        catch( Exception e ) {
            return false;
            }

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
            );

    }

    /**
    **
    */
    public int hashCode() // ----------------------------------------------
    {
        return
            (this.getKey()==null   ? 0 : this.getKey().hashCode())
            ^
            (this.getValue()==null ? 0 : this.getValue().hashCode());
    }

} // class
