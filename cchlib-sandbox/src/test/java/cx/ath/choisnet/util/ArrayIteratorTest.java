/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/ArrayIteratorTest.java
** Description   :
**
**  3.00.001 2006.01.31 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.ArrayIteratorTest
**
*/
package cx.ath.choisnet.util;

import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
**
*/
public class ArrayIteratorTest extends TestCase
{
/**
**
*/
protected final String[] arrayOfString = {
    "String1", "String2", "String3", "String4"
    };

//ArrayIterator

//  protected int fValue1;
//  protected int fValue2;
//
//  protected void setUp() {
//      fValue1= 2;
//      fValue2= 3;
//  }

/**
**
*/
public static Test suite() // ---------------------------------------------
{
 return new TestSuite( ArrayIteratorTest.class );
}

/**
**
*/
public void testIterable() // ---------------------------------------------
{
 Iterator<String> iter  = new ArrayIterator<String>( arrayOfString );
 int              size  = 0;

 while( iter.hasNext() ) {
    String s = iter.next();

    assertEquals( s, arrayOfString[ size ] );

    size++;
    }

 assertEquals( size, arrayOfString.length );
 //assertTrue( size == arrayOfString.length );
}

/**
**
public void testEquals() {
 assertTrue( size == arrayOfString.length );
    assertEquals(12, 12);
    assertEquals(12L, 12L);
    assertEquals(new Long(12), new Long(12));

    assertEquals("Size", 12, 13);
    assertEquals("Capacity", 12.0, 11.99, 0.0);
}
*/

/**
**
public static void main (String[] args)
{
    junit.textui.TestRunner.run(suite());
}
*/

} // class