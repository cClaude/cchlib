/*
** ------------------------------------------------------------------------
** Nom           : cx/ath/choisnet/AllTests.java
** Description   :
** Encodage      : ANSI
**
**  3.00.001 2006.01.31 Claude CHOISNET - Version initiale
** ------------------------------------------------------------------------
**
** cx.ath.choisnet.AllTests
**
**
*/
package cx.ath.choisnet;

import junit.framework.TestSuite;
import junit.framework.Test;

/**
**
** @author Claude CHOISNET
** @since   3.00.001
** @version 3.00.001
**
*/

/**
** TestSuite that runs all the sample tests
*/
public class AllTests
{
/**
**
*/
public static void main( String[] args ) // -------------------------------
{
 junit.textui.TestRunner.run( suite() );
}

/**
**
*/
public static Test suite() // ---------------------------------------------
{
 TestSuite suite= new TestSuite( "CCHLib JUnit Tests" );

 suite.addTest( cx.ath.choisnet.util.ArrayIteratorTest.suite() );
 suite.addTest( cx.ath.choisnet.util.ByteBufferTest.suite() );
 suite.addTest( cx.ath.choisnet.util.SortedMapIteratorTest.suite() );

 suite.addTest( cx.ath.choisnet.io.SerializationTest.suite() );

 suite.addTest( cx.ath.choisnet.lang.reflect.MappableHelperTest.suite() );

 //
 // Tests long !
 //
// suite.addTest( cx.ath.choisnet.util.duplicate.MD5CollectionTest.suite() );
// suite.addTest( cx.ath.choisnet.util.duplicate.MD5FileCollectionCompatorTest.suite() );

 return suite;
}

} // class