package com.googlecode.cchlib.sandbox.benchs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import com.googlecode.cchlib.io.checksum.MD5;
import com.googlecode.cchlib.sandbox.benchs.tools.Stats;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestHelper;
import com.googlecode.cchlib.util.duplicate.digest.MessageDigestAlgorithms;

@SuppressWarnings({"squid:S106"})
public class BenchMessageDigest
{
    private static final int NUMBER_OF_TESTS = 10;
    private static final int FILE_SIZE       = 10 * 1024 * 1024;

    private final byte[] aStream;

    private static final Stats<String> stats = new Stats<>();

    private BenchMessageDigest( final int fileSize )
    {
        this.aStream = new byte[ fileSize ];

        final Random rand = new Random( System.currentTimeMillis() );

        for( int i = 0; i<this.aStream.length; i++ ) {
            this.aStream[ i ] = (byte)(rand.nextInt() & 0x00FF);
        }
    }

    private void benchMessageDigests( final int bufferSize )
        throws NoSuchAlgorithmException, IOException
    {
        for( final MessageDigestAlgorithms entry : MessageDigestAlgorithms.values() ) {
            final MessageDigest messageDigest = entry.newMessageDigest();

            benchMessageDigest( messageDigest, bufferSize );
        }
    }

    private void benchMessageDigest(
        final MessageDigest messageDigest,
        final int           bufferSize
        ) throws IOException
    {
        final byte[] buffer = new byte[ bufferSize ];
        final byte[] digest;

        final String algorithm = messageDigest.getAlgorithm();

        try( final InputStream is = new ByteArrayInputStream( this.aStream ) ) {
            final long begin = System.nanoTime();

            int len;

            while( (len = is.read( buffer )) > 0 ) {
                messageDigest.update( buffer, 0, len );
            }

            digest = messageDigest.digest();

            final long end   = System.nanoTime();
            final long delay = end - begin;

            final String name = "MessageDigest[" + algorithm + ']';

            stats.get( name + ".buffer(" + bufferSize + ")" ).addDelay( delay );
            stats.get( name ).addDelay( delay );
        }

        if( algorithm == "MD5" ) {
            final long   begin = System.nanoTime();
            final String md5V2 = MD5.getHashString( new ByteArrayInputStream( this.aStream ) );
            final long end     = System.nanoTime();
            final long delay   = end - begin;

            stats.get( MD5.class.getName() ).addDelay( delay );

            final String md5V1 = FileDigestHelper.computeDigestKeyString( digest );

            System.out.println( "md5V1 = " + md5V1 );
            System.out.println( "md5V2 = " + md5V2 );
        }
    }

    public static void main( final String[] args ) throws Exception
    {
        for( int i = 0; i <NUMBER_OF_TESTS; i++ ) {
            final BenchMessageDigest instance = new BenchMessageDigest( FILE_SIZE );

            instance.benchMessageDigests( 1024 );
            instance.benchMessageDigests( 2048 );
            instance.benchMessageDigests( 4096 );
            instance.benchMessageDigests( 8192 );
            instance.benchMessageDigests( 16384 );
            instance.benchMessageDigests( 1024 * 1024 );
        }

        System.out.println( stats );
    }
}
