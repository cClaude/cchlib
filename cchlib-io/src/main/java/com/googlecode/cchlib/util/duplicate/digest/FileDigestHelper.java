package com.googlecode.cchlib.util.duplicate.digest;

public class FileDigestHelper
{
    private FileDigestHelper()
    {
        // All static
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

        FileDigestTools.computeDigestKeyString( stringBuilder, digestKey );

        return stringBuilder.toString();
    }

}
