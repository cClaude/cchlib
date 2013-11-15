/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/SortedMapIteratorTest.java
** Description   :
**
**  3.00.004 2006.02.15 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.SortedMapIteratorTest
**
*/
package cx.ath.choisnet.util;

import java.util.SortedMap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
**
*/
public class SortedMapIteratorTest extends TestCase
{

/**
**
*/
public static Test suite() // ---------------------------------------------
{
 return new TestSuite( SortedMapIteratorTest.class );
}

/**
**
*/
private static SortedMap<Integer,String> getSortedMap() // ----------------
{
 SortedMap<Integer,String> sm = new java.util.TreeMap<Integer,String>();

 sm.put( 3, "3" );
 sm.put( 7, "7" );
 sm.put( 2, "2" );
 sm.put( 5, "5" );
 sm.put( 9, "9" );
 sm.put( 1, "1" );

 return sm;
}

/**
**
*/
public void testIterable() // ---------------------------------------------
{
 final SortedMap<Integer,String>    map     = getSortedMap();
 final int                          size    = map.size();

 SortedMapIterator<Integer,String> iter
        = new SortedMapIterator<Integer,String>( map );

 String prev = "99";

 while( iter.hasNext() ) {
    String  value   = iter.next();
    int     cmp     = value.compareTo( prev );
    int     key     = iter.getLastKey();

    assertTrue( "Prob. d'ordre : value.cmp( prev ) = (" + value + ";" + prev + ") = " + cmp + " - " + map, cmp < 0 );
    assertEquals( "Valeur diff. de la cl� : (key,value) = (" + key + ";" + value + ")", Integer.toString( key ), value );

    prev = value;
    }

 assertEquals( "La taille � chang�e !", size, map.size() );
}

/**
**
*/
public void testRemove() // -----------------------------------------------
{
 final SortedMap<Integer,String>    map     = getSortedMap();
 final int                          size    = map.size();

 SortedMapIterator<Integer,String> iter
        = new SortedMapIterator<Integer,String>( map );

 while( iter.hasNext() ) {
    String  value   = iter.next();
    int     key     = iter.getLastKey();

    assertEquals( "Valeur diff. de la cl� : (key,value) = (" + key + ";" + value + ")", Integer.toString( key ), value );

    iter.remove();
    }

 assertEquals( "La SortedMap devrait �tre vide : " + map, 0, map.size() );
}


} // class

