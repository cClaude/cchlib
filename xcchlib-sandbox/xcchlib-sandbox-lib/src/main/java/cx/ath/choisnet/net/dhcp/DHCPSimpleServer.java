/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/net/dhcp/DHCPSimpleServer.java
** Description   :
**
**  3.02.040 2006.09.05 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.net.dhcp.DHCPSimpleServer
**
*/
package cx.ath.choisnet.net.dhcp;

import java.net.DatagramSocket;
import java.net.DatagramPacket;

/**
**
** @author Claude CHOISNET
** @since   3.02.040
** @version 3.02.040
*/
public class DHCPSimpleServer
    extends Thread
{
/**
**
*/
private boolean running;

/**
**
*/
private int port;

/**
**
*/
public DHCPSimpleServer( // -----------------------------------------------
    final String    threadName,
    final boolean   isDaemon
    )
{
 this( threadName, DHCPSocket.SERVER_PORT, isDaemon );
}
/**
**
*/
public DHCPSimpleServer( // -----------------------------------------------
    final String    threadName,
    final int       port,
    final boolean   isDaemon
    )
{
 this.running   = true;
 this.port      = port;

 // set thread name
 super.setName( threadName );
 super.setDaemon( isDaemon );
}

/**
**
*/
@Override
public void run() // ------------------------------------------------------
{
 DHCPTrace.println( "start" );

 byte[]         buffer  = new byte[ 1024 ];
 DatagramSocket socket;

 try {
    socket  = new DatagramSocket( port );
    }
 catch( java.net.SocketException e ) {
    throw new RuntimeException( e );
    }


 while( this.running ) {

    try {
        DatagramPacket data = new DatagramPacket( buffer, buffer.length );

        socket.receive( data );

        DHCPMessage aDHCPMessage = DHCPMessageFactory.newInstance( data );

//        DHCPMessage     aDHCPMessage    = new DHCPMessage();
//        DatagramPacket  data            = aDHCPMessage.toDatagramPacket();

        DHCPTrace.println( "@= " + data.getAddress() );
        DHCPTrace.println( "aDHCPMessage = ", aDHCPMessage );

//        socket.send( data );
        }
    catch( java.io.IOException e ) {
        DHCPTrace.println( "*** " + e );
        }

    DHCPTrace.println( "run: " + this.running );
    }
}


/**
** .java cx.ath.choisnet.net.dhcp.DHCPSimpleServer
*/
public final static void main( final String[] args ) // -------------------
{
 DHCPSimpleServer instance = new DHCPSimpleServer( "DHCPSimpleServer", false );

 instance.start();

 DHCPTrace.println( "fin" );
}

} // class Client


