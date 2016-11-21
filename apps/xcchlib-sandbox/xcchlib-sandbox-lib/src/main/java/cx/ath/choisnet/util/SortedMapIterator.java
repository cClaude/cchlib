/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/util/SortedMapIterator.java
 ** Description   :
 **
 **  2.01.022 2005.11.01 Claude CHOISNET - Version initiale
 **  3.00.004 2006.02.15 Claude CHOISNET
 **                      Reprise documentation.
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.util.SortedMapIterator
 **
 */
package cx.ath.choisnet.util;

import java.util.Iterator;
import java.util.SortedMap;

/**
 ** <P>
 * Construction d'un {@link Iterator} e partir d'objet SortedMap, on iter sur les valeurs (et non pas sur les cles), de
 * plus la methode remove() est supportee.
 * </P>
 **
 ** @author Claude CHOISNET
 ** @since 2.01.022
 ** @version 3.00.004
 **
 ** @see Iterator
 ** @see FlattenIterator
 ** @see FlattenIterable
 ** @see SingletonIterator
 ** @see java.util.SortedMap
 ** @see java.util.TreeMap
 */
public class SortedMapIterator<K, V> implements Iterator<V>, Iterable<V> {

    private SortedMap<K, V> initialSortedMap;
    private Iterator<K>     iter;
    private K               prevKey;

    /**
     ** Construction d'un SortedMapIterator à partir d'objet SortedMap
     **
     */
    public SortedMapIterator( final SortedMap<K, V> sortedMap ) // -------------
    {
        this.initialSortedMap = sortedMap;
        this.prevKey = null;
        this.iter = new ComputableIterator<K>() {
            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            private SortedMap<K, V> currentSortedMap = sortedMap;

            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            @Override
            public K computeNext() // - - - - - - - - - - - - - - - - - - - - -
                    throws java.util.NoSuchElementException
            {
                final K key = this.currentSortedMap.lastKey();

                if( key == null ) {
                    throw new java.util.NoSuchElementException();
                }

                this.currentSortedMap = this.currentSortedMap.headMap( key );

                return key;
            }
            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        };
    }

    @Override
    public boolean hasNext() // -----------------------------------------------
    {
        return this.iter.hasNext();
    }

    /**
     ** Retourne la valeur de l'élément suivant
     */
    @Override
    public V next() // --------------------------------------------------------
            throws java.util.NoSuchElementException
    {
        try {
            this.prevKey = this.iter.next();

            return this.initialSortedMap.get( this.prevKey );
        }
        catch( final java.util.NoSuchElementException e ) {
            this.prevKey = null;

            throw e;
        }
    }

    /**
     ** Retourne la clé du dernier élement traité par la méthode next();
     **
     ** @since 3.00.004
     */
    public K getLastKey() // --------------------------------------------------
            throws java.util.NoSuchElementException
    {
        if( this.prevKey == null ) {
            throw new java.util.NoSuchElementException();
        }

        return this.prevKey;
    }

    @Override
    public void remove() // ---------------------------------------------------
            throws IllegalStateException
    {
        if( this.prevKey == null ) {
            throw new IllegalStateException();
        }

        this.initialSortedMap.remove( this.prevKey );
    }

    /**
**
*/
    @Override
    public Iterator<V> iterator() // ------------------------------------------
    {
        return this;
    }
}
