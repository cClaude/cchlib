package cx.ath.choisnet;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 ** TestSuite that runs all the sample tests
 **
 ** @author Claude CHOISNET
 ** @version 3.00.001 2006.01.31 Claude CHOISNET - Version initiale
 */
public class AllTests
{
    public static void main( final String[] args ) // -------------------------------
    {
        junit.textui.TestRunner.run( suite() );
    }

    public static Test suite() // ---------------------------------------------
    {
        final TestSuite suite = new TestSuite( "CCHLib JUnit Tests" );

        suite.addTest( cx.ath.choisnet.util.ByteBufferTest.suite() );

        return suite;
    }

}
