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
 ** @author Claude CHOISNET
 ** @since 3.02.040
 */
public class DHCPServerRunner implements Runnable {
    private boolean             running;
    private final int           port;
    private final DHCPLogger    logger;
    private final boolean       replyToClients;

    public DHCPServerRunner( // -----------------------------------------------
            final DHCPLogger    logger,
            final boolean       replyToClients
            )
    {
        this( DHCPSocket.SERVER_PORT, logger, replyToClients );
    }

    public DHCPServerRunner( // -----------------------------------------------
            final int           port,
            final DHCPLogger    logger,
            final boolean       replyToClients
            )
    {
        this.port           = port;
        this.logger         = logger;
        this.replyToClients = replyToClients;
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

                logger.println( "Waiting for request..." );
                socket.receive( data );

                final DHCPMessage aDHCPMessage = DHCPMessageFactory.newInstance( data );

                logger.println( "request from : " + data.getAddress() );
                logger.println( "-- request content --" );
                logger.println( aDHCPMessage.toString() );
                logger.println( "---------------------" );

                if( this.replyToClients  ) {
                    socket.send( data );
                }
            }
            catch( final IOException e ) {
                logger.println( "*** " + e );
            }

            logger.println( "run: " + this.running );
        }
    }

    public boolean isRunning()
    {
        return running;
    }

    public void setRunning( final boolean running )
    {
        this.running = running;
    }
}
