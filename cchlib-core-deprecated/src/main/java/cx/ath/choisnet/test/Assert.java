package cx.ath.choisnet.test;

import java.util.Arrays;

/**
 * Provide some extra tools to build JUnit test cases
 * 
 * @deprecated use {@link com.googlecode.cchlib.test.ArrayAssert} instead.
 */
@Deprecated
final
public class Assert
{
    private Assert()
    {//All static
    }

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
            return; // Both null
            }
        
        if( expected != null && expected.equals( actual ) ) {
            return; // Same ref.
            }
        
        if( Arrays.equals( actual, expected) ) {
        	return; // Same content (quicker)
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

    /**
     * Compare byte arrays
     *
     * @param message
     * @param expected
     * @param actual
     */
    public static void assertEquals(
            String message,
            byte[] expected,
            byte[] actual
            )
    {
        junit.framework.Assert.assertEquals(
                String.format(
                        "%s - Not same size",
                        message
                        ),
                expected.length,
                actual.length
                );

        for(int i=0; i<expected.length;i++) {
            junit.framework.Assert.assertEquals(
                String.format(
                        "%s - Not same value offet %d",
                        message,
                        i
                        ),
                expected[i],
                actual[i]
               );
        }
    }

    /**
     * Compare byte arrays
     *
     * @param expected
     * @param actual
     */
    public static void assertEquals(
            byte[] expected,
            byte[] actual
            )
    {
        assertEquals("byte[] not equals", expected, actual);
    }

    /**
     * Compare char arrays
     *
     * @param message
     * @param expected
     * @param actual
     * @since 4.1.5
     */
    public static void assertEquals(
            String message,
            char[] expected,
            char[] actual
            )
    {
        junit.framework.Assert.assertEquals(
                String.format(
                        "%s - Not same size",
                        message
                        ),
                expected.length,
                actual.length
                );

        for(int i=0; i<expected.length;i++) {
            junit.framework.Assert.assertEquals(
                String.format(
                        "%s - Not same value offet %d",
                        message,
                        i
                        ),
                expected[i],
                actual[i]
               );
        }
    }
}
