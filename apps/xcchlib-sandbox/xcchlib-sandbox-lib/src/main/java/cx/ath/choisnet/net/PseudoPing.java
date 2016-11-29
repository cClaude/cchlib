package cx.ath.choisnet.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 **
 ** @version 2.02.020
 */
public class PseudoPing
{
    private final String ipAddress;

    public PseudoPing( final String ipAddress ) // ----------------------------------
    {
        this.ipAddress = ipAddress;
    }

    public String sendRequest( // ---------------------------------------------
            final byte[] bytes,
            final int    port
            ) throws java.net.UnknownHostException, java.io.IOException
    {
        final StringBuilder result = new StringBuilder();

        try(  final Socket socket = new Socket( this.ipAddress, port ) ) {
            final BufferedReader br = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
            final BufferedOutputStream bos = new BufferedOutputStream( socket.getOutputStream() );

            bos.write( bytes, 0, bytes.length );

            String line;

            while( (line = br.readLine()) != null ) {
                result.append( line );
            }
        }

        return result.toString();
    }

    public String sendRequestNoException( // ----------------------------------
            final byte[] bytes,
            final int    port
            )
    {
        try {
            return sendRequest( bytes, port );
        }
        catch( final Exception e ) {
            e.printStackTrace( System.err );

            return "* " + e.getMessage();
        }
    }

    public boolean helloPing() // ---------------------------------------------
    {
        try {
            final String str = sendRequest( "Hello\n".getBytes(), 7 );

            if( str.equals( "Hello" ) ) {
                System.out.println( "Alive!" );

                return true;
            } else {
                System.out.println( "Dead or echo port not responding" );

                return false;
            }
        }
        catch( final java.io.IOException e ) {
            System.out.println( "Dead or echo port not responding " + e );

            return false;
        }
    }
}
