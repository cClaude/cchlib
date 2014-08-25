package com.googlecode.cchlib.dhcp.server;

/*
 ** -----------------------------------------------------------------------
 **  3.02.040 2006.09.05 Claude CHOISNET - Version initiale
 ** -----------------------------------------------------------------------
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import com.googlecode.cchlib.dhcp.DHCPMessage;
import com.googlecode.cchlib.dhcp.DHCPSocket;
import com.googlecode.cchlib.dhcp.logger.DHCPLogger;

/**
 **
 ** @author Claude CHOISNET
 ** @since 3.02.040
 */
public class DHCPSimpleServer extends Thread {
    private boolean             running;
    private final int           port;
    private final DHCPLogger    logger;

    public DHCPSimpleServer( // -----------------------------------------------
            final String threadName,
            final DHCPLogger logger,
            final boolean isDaemon
            )
    {
        this( threadName, DHCPSocket.SERVER_PORT, logger, isDaemon );
    }

    public DHCPSimpleServer( // -----------------------------------------------
            final String threadName,
            final int port,
            final DHCPLogger logger,
            final boolean isDaemon
            )
    {
        this.port       = port;
        this.logger  = logger;

        // set thread name
        super.setName( threadName );
        super.setDaemon( isDaemon );
    }

    @Override
    public void run() // ------------------------------------------------------
    {
        logger.println( "start server" );

        this.running = true;

        try( DatagramSocket socket = new DatagramSocket( port ) ) {
            run( socket );
        }
        catch( final java.net.SocketException e ) {
            this.running = false;
            throw new RuntimeException( e );
        }
    }

    private void run( final DatagramSocket socket )
    {
        final byte[] buffer = new byte[1024];;

        while( this.running ) {

            try {
                final DatagramPacket data = new DatagramPacket( buffer, buffer.length );

                socket.receive( data );

                final DHCPMessage aDHCPMessage = DHCPMessageFactory.newInstance( data );

                // DHCPMessage aDHCPMessage = new DHCPMessage();
                // DatagramPacket data = aDHCPMessage.toDatagramPacket();

                logger.println( "@= " + data.getAddress() );
                logger.println( "aDHCPMessage = ", aDHCPMessage );

                // socket.send( data );
            }
            catch( final IOException e ) {
                logger.println( "*** " + e );
            }

            logger.println( "run: " + this.running );
        }
    }

}
