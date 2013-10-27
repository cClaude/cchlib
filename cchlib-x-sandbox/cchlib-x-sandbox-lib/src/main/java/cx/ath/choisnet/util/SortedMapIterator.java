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
** Construction d'un {@link Iterator} � partir d'objet SortedMap,
** on iter sur les valeurs (et non pas sur les cl�s), de plus la
** m�thode remove() est support�e.
** </P>
**
** @author Claude CHOISNET
** @since   2.01.022
** @version 3.00.004
**
** @see Iterator
** @see FlattenIterator
** @see FlattenIterable
** @see SingletonIterator
** @see java.util.SortedMap
** @see java.util.TreeMap
*/
public class SortedMapIterator<K,V>
    implements
        Iterator<V>,
        Iterable<V>
{
/** */
private SortedMap<K,V> initialSortedMap;

/** */
private Iterator<K> iter;

/** */
private K prevKey;

/**
** Construction d'un SortedMapIterator � partir d'objet SortedMap
**
*/
public SortedMapIterator( final SortedMap<K,V> sortedMap ) // -------------
{
 this.initialSortedMap  = sortedMap;
 this.prevKey           = null;
 this.iter              = new ComputableIterator<K>()
    {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private SortedMap<K,V> currentSortedMap = sortedMap;
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public K computeNext() // - - - - - - - - - - - - - - - - - - - - -
            throws java.util.NoSuchElementException
        {
            K key = this.currentSortedMap.lastKey();

            if( key == null ) {
                throw new java.util.NoSuchElementException();
                }

            this.currentSortedMap  = this.currentSortedMap.headMap( key );

            return key;
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    };
}

/**
**
*/
@Override
public boolean hasNext() // -----------------------------------------------
{
 return this.iter.hasNext();
}

/**
** Retourne la valeur de l'�l�ment suivant
*/
@Override
public V next() // --------------------------------------------------------
    throws java.util.NoSuchElementException
{
 try {
    this.prevKey = this.iter.next();

    return this.initialSortedMap.get( this.prevKey );
    }
 catch( java.util.NoSuchElementException e ) {
    this.prevKey = null;

    throw e;
    }
}

/**
** Retourne la cl� du dernier �l�ment trait� par la m�thode next();
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

/**
**
*/
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

/**
**
** java -cp build/classes cx.ath.choisnet.util.SortedMapIterator
public static void main( String[] args ) // -------------------------------
{
 SortedMap<Integer,String> sm = new java.util.TreeMap<Integer,String>();

 sm.put( 3, "3" );
 sm.put( 7, "7" );
 sm.put( 2, "2" );
 sm.put( 5, "5" );
 sm.put( 9, "9" );
 sm.put( 1, "1" );

 System.out.println( "SortedMap = " + sm );

 SortedMapIterator<Integer,String> iter
        = new SortedMapIterator<Integer,String>( sm );

 while( iter.hasNext() ) {
    String value = iter.next();

    System.out.println( "V = " + value );
    System.out.println( "SortedMap AV = " + sm );
    iter.remove();
    System.out.println( "SortedMap AP = " + sm );

    }

}
*/

} // class
