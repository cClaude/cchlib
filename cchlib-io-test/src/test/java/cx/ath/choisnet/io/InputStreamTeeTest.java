package cx.ath.choisnet.io;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import cx.ath.choisnet.util.ByteBuffer;

public class InputStreamTeeTest
{
    private static final Logger LOGGER = Logger.getLogger( InputStreamTeeTest.class );

    private byte[] data;

    @Before
    public void setup()
    {
        this.data  = new byte[ 26 ];

        for( int i = 0; i< this.data.length; i++ ) {
            this.data[ i ] = (byte)('a' + i);
        }
    }

    @Test
    public void testInputStreamTee() throws IOException
    {
       try( final InputStream realInputStream = new ByteArrayInputStream( this.data ) ) {
           try( final InputStreamTee input = new InputStreamTee( realInputStream ) ) {

               final String inputString = toString( input );
               LOGGER.info( "inputString = " + inputString );

               // Needed to obtain tee InputStream
               input.close();

               final String teeString   = toString( input.getTeeInputStream() );

               LOGGER.info( "teeString   = " + teeString );

               assertThat( teeString ).isEqualTo( inputString );
           }
       }
    }

    private static String toString( final InputStream inputStream ) throws IOException
    {
        final ByteBuffer bb = ByteBuffer.newByteBuffer( inputStream );

        return bb.toString();
    }

    public static final void main( final String[] args )
        throws FileNotFoundException, IOException
    {
        final File file = new File( args[ 0 ] );

        testOnFile( file );
    }

    private static final void testOnFile( final File file )
        throws FileNotFoundException, IOException
    {
        try( final InputStreamTee input = new InputStreamTee( new FileInputStream( file ) ) ) {
            System.out.println( "Reading = " + file );

            @SuppressWarnings("unused")
            int c;

            System.out.println( "input -----------------" );

            while( (c = input.read()) != -1 ) {
                System.out.print( "." );
            }

            // Needed to obtain tee InputStream
            input.close();

            System.out.println();

            final InputStream inputTee = input.getTeeInputStream();

            System.out.println( "inputTee -----------------" );

            while( (c = inputTee.read()) != -1 ) {
                System.out.print( "." );
            }

            System.out.println();
        }
    }
}
