/*
** cx/ath/choisnet/net/PseudoPing.java
**
** cx.ath.choisnet.net.PseudoPing
*/
package cx.ath.choisnet.net;

import java.io.PrintStream;
import java.net.Socket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
**
** @author Claude CHOISNET
** @version 2.02.020
*/
public class PseudoPing
{

/** */
private String ipAddress;

/**
**
*/
public PseudoPing( String ipAddress ) // ----------------------------------
{
 this.ipAddress = ipAddress;
}

/**
**
public String sendRequest() // --------------------------------------------
    throws
        java.net.UnknownHostException,
        java.io.IOException
{
 final Socket   socket = new Socket( this.ipAddress, 7 );
 String         result;

 try {
    final BufferedReader    br  = new BufferedReader(
                                    new InputStreamReader( socket.getInputStream() )
                                    );
    final PrintStream       ps  = new PrintStream( socket.getOutputStream() );

    ps.println( "Hello" );

    result = br.readLine();
    }
 finally {
    socket.close();
    }

 return result;
}
*/

/**
**
*/
public String sendRequest( // ---------------------------------------------
    final byte[]    bytes,
    final int       port
    )
    throws
        java.net.UnknownHostException,
        java.io.IOException
{
 final Socket           socket  = new Socket( this.ipAddress, port );
 final StringBuilder    result  = new StringBuilder();

 try {
    final BufferedReader br  = new BufferedReader(
                                    new InputStreamReader( socket.getInputStream() )
                                    );
    final BufferedOutputStream bos = new BufferedOutputStream( socket.getOutputStream() );

    bos.write( bytes, 0, bytes.length );

    String line;

    while( (line = br.readLine()) != null ) {
        result.append( line );
        }
    }
 finally {
    socket.close();
    }

 return result.toString();
}

/**
**
*/
public String sendRequestNoException( // ----------------------------------
    final byte[]    bytes,
    final int       port
    )
{
 try {
    return sendRequest( bytes, port );
    }
 catch( Exception e ) {
    e.printStackTrace( System.err );

    return "* " + e.getMessage();
    }
}

/**
**
*/
public boolean helloPing() // ---------------------------------------------
{
 try {
    String str = sendRequest( "Hello\n".getBytes(), 7 );

    if( str.equals( "Hello" ) ) {
        System.out.println( "Alive!" );

        return true;
        }
    else {
        System.out.println( "Dead or echo port not responding" );

        return false;
        }
    }
 catch( java.io.IOException e ) {
    System.out.println( "Dead or echo port not responding "  + e );

    return false;
    }
}

} // class
