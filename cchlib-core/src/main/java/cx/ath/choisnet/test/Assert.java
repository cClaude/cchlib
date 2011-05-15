package cx.ath.choisnet.test;

/**
 * Provide some tools to build test cases
 *
 * @author Claude CHOISNET
 */
public class Assert
{
    private Assert()
    {//All static
    }

    /* *
     * Asserts that two array of Strings are equal.
     * /
    public static void assertEquals( String[] expected, String[] actual )
    {
        assertEquals( "", expected, actual );
    }

    /**
     * Asserts that two array of Strings are equal.
     * /
    public static void assertEquals(
            final String    message,
            final String[]  expected,
            final String[]  actual
            )
    {
        if( expected == null && actual == null ) {
            return;
            }
        if( expected != null && expected.equals( actual ) ) {
            return;
            }
        if( expected == null && actual != null ) {
            junit.framework.Assert.fail(message + " expected=" + expected + " actual=" + actual);
            }
        if( expected != null && actual == null ) {
            junit.framework.Assert.fail(message + " expected=" + expected + " actual=" + actual);
            }

        if( expected.length != actual.length ) {
            junit.framework.Assert.fail(
                    message
                    + " expected(size="+expected.length+")=" + expected
                    + " actual(size="+actual.length+")=" + actual
                    );
            }

        for( int i = 0; i<expected.length; i++ ) {
            String e = expected[ i ];
            String a = actual[ i ];

            if( e == null && a == null) {
                break;
                }
            if( e != null && e.equals( a ) ) {
                break;
                }
            junit.framework.Assert.fail(
                    message
                    + " expected=" + expected
                    + " (item:" + i + "=" + e + ")"
                    + " actual=" + actual
                    + " (item:" + i + "=" + a + ")"
                    );
            }
    }*/

    /**
     * Asserts that two array are equal.
     */
    public static <T> void assertEquals( T[] expected, T[] actual )
    {
        assertEquals( "", expected, actual );
    }

    /**
     * Asserts that two array are equal.
     */
    public static <T> void assertEquals(
            final String    message,
            final T[]       expected,
            final T[]       actual
            )
    {
        if( expected == null && actual == null ) {
            return;
            }
        if( expected != null && expected.equals( actual ) ) {
            return;
            }
        if( expected == null && actual != null ) {
            junit.framework.Assert.fail(message + " expected=" + expected + " actual=" + actual);
            }
        if( expected != null && actual == null ) {
            junit.framework.Assert.fail(message + " expected=" + expected + " actual=" + actual);
            }

        if( expected.length != actual.length ) {
            junit.framework.Assert.fail(
                    message
                    + " expected(size="+expected.length+")=" + expected
                    + " actual(size="+actual.length+")=" + actual
                    );
            }

        for( int i = 0; i<expected.length; i++ ) {
            T e = expected[ i ];
            T a = actual[ i ];

            if( e == null && a == null) {
                break;
                }
            if( e != null && e.equals( a ) ) {
                break;
                }
            junit.framework.Assert.fail(
                    message
                    + " expected=" + expected
                    + " (item:" + i + "=" + e + ")"
                    + " actual=" + actual
                    + " (item:" + i + "=" + a + ")"
                    );
            }
    }
}
