package com.googlecode.cchlib.util.duplicate.digest;


/**
 * @see FileDigest
 * @since 4.2
 */
//NOT public
final class FileDigestTools
{
    private static final char[] HEX = {
        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
        };

    private FileDigestTools()
    {
        // All static
    }

    /**
     * Compute hex String value for digestKey and add value to the {@code stringBuilder}.
     *
     * @param stringBuilder
     *            {@link StringBuilder} within result will be appended
     * @param digestKey
     *            digestKey to convert
     */
    /*public*/ static void computeDigestKeyString(
        final StringBuilder stringBuilder,
        final byte[]        digestKey
        )
    {
        for( final byte b : digestKey ) {
            stringBuilder.append( HEX[(b & 0x00f0)>>4 ] );
            stringBuilder.append( HEX[ b & 0x000f ] );
            }
    }

    /**
     * Compute hex String value for digestKey and add value to the {@code stringBuilder}.
     *
     * @param stringBuilder
     *            {@link StringBuilder} within result will be appended
     * @param resetStringBuilder
     *            if true {@code stringBuilder} will be reset before adding hex String of digestKey.
     * @param digestKey
     *            digestKey to convert
     *
     * @return {@code stringBuilder} as a String.
     */
    /*public*/ static String computeDigestKeyString(
            final StringBuilder stringBuilder,
            final boolean       resetStringBuilder,
            final byte[]        digestKey
            )
    {
        if( resetStringBuilder ) {
            stringBuilder.setLength( 0 );
        }

        computeDigestKeyString( stringBuilder, digestKey );

        return stringBuilder.toString();
    }
}
