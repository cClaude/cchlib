package com.googlecode.cchlib.test;

import org.junit.Assert;

/**
 * Provide some extra tools to build JUnit test cases
 * with arrays.
 * <br>
 * Note: new versions version of JUnit already include this
 * feature.
 * 
 * @since 4.1.7
 */
final
public class ArrayAssert
{
    private ArrayAssert()
    {//All static
    }

    /**
     * Asserts that two arrays are equal. If they 
     * are not, an AssertionError is thrown.
     * 
     * @param expected expected bytes array value.
     * @param actual actual bytes array value.
     */
    public static <T> void assertEquals( 
        final T[] expected, 
        final T[] actual 
        )
    {
        assertEquals( null, expected, actual );
    }

    /**
     * Asserts that two arrays are equal. If they 
     * are not, an AssertionError is thrown with the 
     * given message.
     * 
     * @param message the identifying message for the AssertionError (null okay)
     * @param expected expected bytes array value.
     * @param actual actual bytes array value.
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
            final StringBuilder sb = new StringBuilder();
            
            if( message != null ) {
                sb.append( message );
                sb.append( ' ' );
                }
            
            sb.append( "expected=null actual=" );
            sb.append( actual );

            Assert.fail( sb.toString() );
            }
        
        if( expected != null && actual == null ) {
            final StringBuilder sb = new StringBuilder();
            
            if( message != null ) {
                sb.append( message );
                sb.append( ' ' );
                }
            sb.append( "expected=");
            sb.append( expected );
            sb.append( " actual=null" );

            Assert.fail( sb.toString() );
            }

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
            }

        for( int i = 0; i<expected.length; i++ ) {
            T e = expected[ i ];
            T a = actual[ i ];

            if( e == null && a == null) {
                continue;
                }
            
            if( e != null && e.equals( a ) ) {
                continue;
                }
            
            // Not match !
            final StringBuilder sb = new StringBuilder();
            
            if( message != null ) {
                sb.append( message );
                sb.append( ' ' );
                }
            
            sb.append( "expected" );
            sb.append( expected );
            
            sb.append( " (item[:" );
            sb.append( i );
            sb.append( "]=" );
            sb.append( e );
            
            sb.append( ") actual=" );
            sb.append( actual );
            
            sb.append( " (item[:" );
            sb.append( i );
            sb.append( "]=" );
            sb.append( a );
            sb.append( ")" );
            
            Assert.fail( sb.toString() );
            }
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
    public static void assertEquals(
            final String message,
            final byte[] expected,
            final byte[] actual
            )
    {
        if( expected == null && actual == null ) {
            return;
            }
        
        if( expected != null && expected.equals( actual ) ) {
            return;
            }
        
        if( expected == null && actual != null ) {
            final StringBuilder sb = new StringBuilder();
            
            if( message != null ) {
                sb.append( message );
                sb.append( ' ' );
                }
            
            sb.append( "expected=null actual=" );
            sb.append( actual );

            Assert.fail( sb.toString() );
            }
        
        if( expected != null && actual == null ) {
            final StringBuilder sb = new StringBuilder();
            
            if( message != null ) {
                sb.append( message );
                sb.append( ' ' );
                }

            sb.append( "expected=");
            sb.append( expected );
            sb.append( " actual=null" );

            Assert.fail( sb.toString() );
            }
        
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
            }

        for( int i=0; i<expected.length; i++ ) {
            if( expected[ i ] == actual[ i ] ) {
                continue;
                }
            
            failBadValueAtOffset( message, i, expected[ i ], actual[ i ] );
        }
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
    public static void assertEquals(
            final String message,
            final char[] expected,
            final char[] actual
            )
    {
        if( expected == null && actual == null ) {
            return;
            }
        if( expected != null && expected.equals( actual ) ) {
            return;
            }
        if( expected == null && actual != null ) {
            final StringBuilder sb = new StringBuilder();
            
            if( message != null ) {
                sb.append( message );
                sb.append( ' ' );
                }
            sb.append( "expected=null actual=" );
            sb.append( actual );
            
            Assert.fail( sb.toString() );
            }
        if( expected != null && actual == null ) {
            final StringBuilder sb = new StringBuilder();
            
            if( message != null ) {
                sb.append( message );
                sb.append( ' ' );
                }
            sb.append( "expected=" );
            sb.append( expected );
            sb.append( " actual=null" );
            
            Assert.fail( sb.toString() );
            }
        
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
            }

        for( int i=0; i<expected.length; i++ ) {
            if( expected[ i ] == actual[ i ] ) {
                continue;
                }
            failBadValueAtOffset( message, i, expected[ i ], actual[ i ] );
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
