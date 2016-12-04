package com.googlecode.cchlib.sandbox.java.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileCheckSumExample
{
    private FileCheckSumExample()
    {
        // Sample
    }

    @SuppressWarnings("squid:S106")
    public static void main( final String[] args )
        throws NoSuchAlgorithmException, IOException
    {
        try( InputStream is = new FileInputStream( "pom.xml" ) ) {

            final String checkSum = getCheckSum( is );

            System.out.println("Checksum for the File: " + checkSum );
        }
    }

    private static String getCheckSum( final InputStream is )
        throws NoSuchAlgorithmException, IOException
    {
        final MessageDigest messageDigest = MessageDigest.getInstance( "SHA1" );

        final byte[] dataBytes = new byte[ 1024 ];
        int          bytesRead;

        while( (bytesRead = is.read( dataBytes )) != -1 ) {
            messageDigest.update( dataBytes, 0, bytesRead );
        }

        final byte[]        digestBytes = messageDigest.digest();
        final StringBuilder sb          = new StringBuilder();

        for( int i = 0; i < digestBytes.length; i++ ) {
            sb.append(
                Integer.toString(
                    (digestBytes[ i ] & 0xff) + 0x100,
                    16
                    ).substring( 1 )
                );
        }

        return sb.toString();
    }
}
