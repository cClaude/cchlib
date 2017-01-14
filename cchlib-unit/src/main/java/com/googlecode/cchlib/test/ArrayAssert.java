package com.googlecode.cchlib.test;

import java.util.Arrays;
import org.junit.Assert;

/**
 * Provide some extra tools to build JUnit test cases
 * with arrays.
 * <br>
 * Note: new versions version of JUnit already include this
 * feature.
 */
@SuppressWarnings({
    "squid:S1192", // Duplicated String literals
    "squid:S00100" // naming convention
    })
public final class ArrayAssert
{
    private ArrayAssert()
    {//All static
    }

    private static <T> void rowAppend( final StringBuilder sb, final T array )
    {
        sb.append( array );
    }

    /**
     * Asserts that two arrays are equal. If they are not, an AssertionError is thrown.
     *
     * @param <T>
     *            Array type
     * @param expected
     *            expected bytes array value.
     * @param actual
     *            actual bytes array value.
     */
    public static <T> void assertEquals(
        final T[] expected,
        final T[] actual
        )
    {
        assertEquals( null, expected, actual );
    }

    /**
     * Asserts that two arrays are equal. If they are not, an AssertionError is
     * thrown with the given message.
     *
     * @param <T>
     *            Array type
     * @param message
     *            the identifying message for the AssertionError (null okay)
     * @param expected
     *            expected bytes array value.
     * @param actual
     *            actual bytes array value.
     */
    @SuppressWarnings({
        "squid:S2583" // Already evaluated (be clearest)
        })
    public static <T> void assertEquals(
        final String message,
        final T[]    expected,
        final T[]    actual
        )
    {
        if( expected == actual ) {
            return; // Same ref.
            }

        if( Arrays.equals( actual, expected) ) {
            return; // Same content (quicker)
            }

        if( (expected == null) && (actual != null) ) {
            final StringBuilder sb = assertEquals_expected_not_null_actual_null( message, actual );

            Assert.fail( sb.toString() );
            return; // Just to avoid SONAR warning
           }

        if( (expected != null) && (actual == null) ) {
            final StringBuilder sb = new StringBuilder();

            if( message != null ) {
                sb.append( message );
                sb.append( ' ' );
                }
            sb.append( "expected=");
            rowAppend( sb, expected );
            sb.append( " actual=null" );

            Assert.fail( sb.toString() );
            return; // Just to avoid SONAR warning
            }

        if( expected != null ) {
            if( expected.length != actual.length ) {
                final StringBuilder sb = new StringBuilder();

                if( message != null ) {
                    sb.append( message );
                    sb.append( ' ' );
                    }

                sb.append( "expected(size=");
                sb.append( expected.length );
                sb.append( ")=" );
                rowAppend( sb, expected );
                sb.append( " actual(size=" );
                sb.append( actual.length );
                sb.append( ")=" );
                rowAppend( sb, actual );

                Assert.fail( sb.toString() );
                }

            for( int i = 0; i<expected.length; i++ ) {
                final T e = expected[ i ];
                final T a = actual[ i ];

                if( (e == null) && (a == null)) {
                    continue;
                    }

                if( (e != null) && e.equals( a ) ) {
                    continue;
                    }

                // Not match !
                final StringBuilder sb = new StringBuilder();

                if( message != null ) {
                    sb.append( message );
                    sb.append( ' ' );
                    }

                sb.append( "expected" );
                rowAppend( sb, expected );

                sb.append( " (item[:" );
                sb.append( i );
                sb.append( "]=" );
                sb.append( e );

                sb.append( ") actual=" );
                rowAppend( sb, actual );

                sb.append( " (item[:" );
                sb.append( i );
                sb.append( "]=" );
                sb.append( a );
                sb.append( ')' );

                Assert.fail( sb.toString() );
                }
        }
    }

    private static <T> StringBuilder assertEquals_expected_not_null_actual_null(
        final String message,
        final T[]    actual
        )
    {
        final StringBuilder sb = new StringBuilder();

        if( message != null ) {
            sb.append( message );
            sb.append( ' ' );
            }

        sb.append( "expected=null actual=" );
        rowAppend( sb, actual );

        return sb;
    }

    /**
     * Asserts that two bytes arrays are equal. If they
     * are not, an AssertionError is thrown with the
     * given message.
     *
     * @param message the identifying message for the AssertionError (null okay)
     * @param expected expected bytes array value.
     * @param actual actual bytes array value.
     */
    @SuppressWarnings({
        "boxing",
        "squid:MethodCyclomaticComplexity",
        "squid:S2583" // Already evaluated (be clearest)
        })
    public static void assertEquals(
            final String message,
            final byte[] expected,
            final byte[] actual
            )
    {
        if( expected == actual ) {
            return; // Same ref.
            }

        if( Arrays.equals( actual, expected) ) {
            return; // Same content (quicker)
            }

        if( (expected == null) && (actual != null) ) {
            final StringBuilder sb = assertEquals_bytes_expected_null_actual_not_null( message, actual );

            Assert.fail( sb.toString() );
            return; // Just to avoid SONAR warning
            }

        if( (expected != null) && (actual == null) ) {
            final StringBuilder sb = assertEquals_bytes_expected_not_null_actual_null( message, expected );

            Assert.fail( sb.toString() );
            }
        else if( expected != null ) {
            if( expected.length != actual.length ) {
                final StringBuilder sb = assertEquals_bytes_expected_actual_not_same_size( message, expected, actual );

                Assert.fail( sb.toString() );
                }
            else {
                for( int i=0; i<expected.length; i++ ) {
                    if( expected[ i ] == actual[ i ] ) {
                        continue;
                        }

                failBadValueAtOffset( message, i, expected[ i ], actual[ i ] );
                }
            }
        }
    }

    private static StringBuilder assertEquals_bytes_expected_actual_not_same_size(
        final String message,
        final byte[] expected,
        final byte[] actual
        )
    {
        final StringBuilder sb = new StringBuilder();

        if( message != null ) {
            sb.append( message );
            sb.append( ' ' );
            }

        sb.append( "expected(size=");
        sb.append( expected.length );
        sb.append( ")=" );
        rowAppend( sb, expected );
        sb.append( " actual(size=" );
        sb.append( actual.length );
        sb.append( ")=" );
        rowAppend( sb, actual );

        return sb;
    }

    private static StringBuilder assertEquals_bytes_expected_not_null_actual_null(
        final String message,
        final byte[] expected
        )
    {
        final StringBuilder sb = new StringBuilder();

        if( message != null ) {
            sb.append( message );
            sb.append( ' ' );
            }

        sb.append( "expected=");
        rowAppend( sb, expected );

        return sb.append( " actual=null" );
    }

    private static StringBuilder assertEquals_bytes_expected_null_actual_not_null(
        final String message,
        final byte[] actual
        )
    {
        final StringBuilder sb = new StringBuilder();

        if( message != null ) {
            sb.append( message );
            sb.append( ' ' );
            }

        sb.append( "expected=null actual=" );
        rowAppend( sb, actual );

        return sb;
    }

    /**
     * Asserts that two bytes arrays are equal. If they
     * are not, an AssertionError is thrown.
     *
     * @param expected expected bytes array value.
     * @param actual actual bytes array value.
     */
    public static void assertEquals(
            final byte[] expected,
            final byte[] actual
            )
    {
        assertEquals("byte[] not equals", expected, actual);
    }

    /**
     * Asserts that two chars arrays are equal. If they
     * are not, an AssertionError is thrown with the
     * given message.
     *
     * @param message the identifying message for the AssertionError (null okay)
     * @param expected expected chars array value.
     * @param actual actual chars array value.
     */
    @SuppressWarnings({
        "boxing",
        "squid:S2583" // Already evaluated (be clearest)
        })
    public static void assertEquals(
        final String message,
        final char[] expected,
        final char[] actual
        )
    {
        if( expected == actual ) {
            return; // Same ref.
            }

        if( Arrays.equals( actual, expected) ) {
            return; // Same content (quicker)
            }

        if( (expected == null) && (actual != null) ) {
            final StringBuilder sb = new StringBuilder();

            if( message != null ) {
                sb.append( message );
                sb.append( ' ' );
                }
            sb.append( "expected=null actual=" );
            sb.append( actual );

            Assert.fail( sb.toString() );
            return; // Just to avoid SONAR warning
            }

        if( (expected != null) && (actual == null) ) {
            final StringBuilder sb = new StringBuilder();

            if( message != null ) {
                sb.append( message );
                sb.append( ' ' );
                }
            sb.append( "expected=" );
            sb.append( expected );
            sb.append( " actual=null" );

            Assert.fail( sb.toString() );
            return; // Just to avoid SONAR warning
            }

        if( expected != null ) {
            if( expected.length != actual.length ) {
                final StringBuilder sb = new StringBuilder();

                if( message != null ) {
                    sb.append( message );
                    sb.append( ' ' );
                    }
                sb.append( "expected(size=");
                sb.append( expected.length );
                sb.append( ")=" );
                sb.append( expected );
                sb.append( " actual(size=" );
                sb.append( actual.length );
                sb.append( ")=" );
                sb.append( actual );

                Assert.fail( sb.toString() );
                return; // Just to avoid SONAR warning
                }

            for( int i=0; i<expected.length; i++ ) {
                if( expected[ i ] == actual[ i ] ) {
                    continue;
                    }
                failBadValueAtOffset( message, i, expected[ i ], actual[ i ] );
            }
        }
    }

    private static void failBadValueAtOffset(
        final String    message,
        final int       offset,
        final Object    expectedValueAtOffset,
        final Object    actualValueAtOffset
        )
    {
        final StringBuilder sb = new StringBuilder();

        if( message != null ) {
            sb.append( message );
            sb.append( " - " );
            }

        sb.append( "Not same value at offet: " );
        sb.append( offset );
        sb.append( " expected=" );
        sb.append( expectedValueAtOffset );
        sb.append( " actual=" );
        sb.append( actualValueAtOffset );

        Assert.fail( sb.toString() );
    }
}
