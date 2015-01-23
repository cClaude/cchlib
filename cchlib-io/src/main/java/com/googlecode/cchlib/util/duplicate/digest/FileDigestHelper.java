package com.googlecode.cchlib.util.duplicate.digest;


/**
 * @see FileDigest
 * @since 4.2
 */
public final class FileDigestHelper {
    private static final char[] HEX = {
        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
        };

    /** All static */
    private FileDigestHelper()
    {
    }

    /**
     *
     *
     * @param stringBuilder A {@link StringBuilder} ver=
     * @param digestKey disgestKey to transform into String
     * @return Hex String
     */

    /**
     * Compute hex String value for digestKey and add value to the <code>stringBuilder</code>.
     *
     * @param stringBuilder
     *            {@link StringBuilder} within result will be appended
     * @param digestKey
     *            digestKey to convert
     */
    public static void computeDigestKeyString( final StringBuilder stringBuilder, final byte[] digestKey )
    {
        for( final byte b : digestKey ) {
            stringBuilder.append( HEX[(b & 0x00f0)>>4 ] );
            stringBuilder.append( HEX[(b & 0x000f) ] );
            }
    }

    /**
     * Compute hex String value for digestKey and add value to the <code>stringBuilder</code>.
     *
     * @param stringBuilder
     *            {@link StringBuilder} within result will be appended
     * @param resetStringBuilder
     *            if true <code>stringBuilder</code> will be reset before adding hex String of digestKey.
     * @param digestKey
     *            digestKey to convert
     *
     * @return <code>stringBuilder</code> as a String.
     */
    public static String computeDigestKeyString( final StringBuilder stringBuilder, final boolean resetStringBuilder, final byte[] digestKey )
    {
        if( resetStringBuilder ) {
            stringBuilder.setLength( 0 );
        }

        computeDigestKeyString( stringBuilder, digestKey );

        return stringBuilder.toString();
    }

    /**
     * Compute hex String value for digestKey
     *
     * @param digestKey
     *            digestKey to convert
     * @return hex String value for digestKey
     */
    public static String computeDigestKeyString( final byte[] digestKey )
    {
        final StringBuilder stringBuilder = new StringBuilder();

        computeDigestKeyString( stringBuilder, digestKey );

        return stringBuilder.toString();
    }
}
