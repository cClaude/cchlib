package com.googlecode.cchlib.dhcp.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import com.googlecode.cchlib.dhcp.DHCPMessage;
import com.googlecode.cchlib.dhcp.DHCPOptions;
import com.googlecode.cchlib.dhcp.DHCPParameters;
import com.googlecode.cchlib.dhcp.DHCPSocket;
import com.googlecode.cchlib.dhcp.logger.DHCPLogger;
import com.googlecode.cchlib.lang.Threads;

public class DHCPSimpleClientRunner implements Runnable
{
    private static final String TASK_NAME = DHCPSimpleClientRunner.class.getSimpleName();

    private final DHCPSocketBuilder socketBuilder;
    private final DHCPParameters    dhcpParameters;
    private final DHCPLogger        logger;
    private final Random            ranXid;

    private DHCPMessage dhcpMessageReceived;
    private boolean     running;

    public DHCPSimpleClientRunner(
        final DHCPSocketBuilder socketBuilder,
        final DHCPParameters    dhcpParameters,
        final DHCPLogger        logger
        )
    {
        this.socketBuilder = socketBuilder;
        this.dhcpParameters = dhcpParameters;
        this.logger         = logger;

        this.ranXid = new Random();
    }

    public boolean isRunning()
    {
        return this.running;
    }

    public void setRunning( final boolean running )
    {
        this.running = running;
    }

    @Override
    public void run()
    {
        this.dhcpMessageReceived = new DHCPMessage();

        try {
            sendDHCPDISCOVER();

            while( this.running ) {
                final byte messageType = this.dhcpMessageReceived.getMessageType();

                this.logger.println( "<< receving <<", this.dhcpMessageReceived );

                switch( messageType ) {
                    case DHCPOptions.MESSAGE_TYPE_DHCPOFFER:
                        handleDHCPoffer();
                        break;

                    case DHCPOptions.MESSAGE_TYPE_DHCPACK:
                        handleDHCPack();
                        break;

                    case DHCPOptions.MESSAGE_TYPE_DHCPNAK:
                        handleDHCPnak();
                        break;

                    default:
                        break;
                }
            }
        }
        catch( final IOException e ) {
            this.logger.printStackTrace( e );
        }
    }

    private void handleDHCPnak() throws IOException
    {
        this.logger.println( TASK_NAME + " Revieded DHCPNAK... " );

        sendDHCPDISCOVER();
    }

    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    private void handleDHCPack() throws UnknownHostException, SocketException, IOException
    {
        final long t1 = DHCPParameters.byteToLong( this.dhcpMessageReceived.getDHCPParameters().getOption( DHCPOptions.T1_TIME ) );
        final long t2 = DHCPParameters.byteToLong( this.dhcpMessageReceived.getDHCPParameters().getOption( DHCPOptions.T2_TIME ) );
        final String ip = DHCPParameters.ip4AddrToString( this.dhcpMessageReceived.getDHCPParameters().getYIAddr() );

        this.logger.println( TASK_NAME + " received an DHCPACK and a leasetime." );
        this.logger.println( "Binding to IP address: " + ip );

        this.logger.println( "Goodnight for " + t1 + " seconds (t1/t2)=(" + t1 + "/" + t2 + ")" );

        Threads.sleep( t1, TimeUnit.SECONDS );

        this.logger.println( TASK_NAME + " sending ReNew Message to server..." );

        reNew();
    }

    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    private void handleDHCPoffer() throws UnknownHostException, IOException
    {
        final String ip = DHCPParameters.ip4AddrToString( this.dhcpMessageReceived.getDHCPParameters().getYIAddr() );

        this.logger.println( TASK_NAME + " received a DHCPOFFER for " + ip );

        sendRequest();
    }

    /*
     * DHCP Client message send/receive functions
     * <P>
     * Sends DHCP Discover Message and returns the received Offer Message
     */
    private void sendDHCPDISCOVER() throws IOException
    {
        //
        // Create a new DHCPMessage (form default parameters)
        //
        final DHCPMessage dhcpMessageSend = new DHCPMessage( this.dhcpParameters.getClone() );

        // setOp Method being used
        dhcpMessageSend.getDHCPParameters().setOp( DHCPParameters.OP_OPTION_BOOTREQUEST );

        // should be a random int
        dhcpMessageSend.getDHCPParameters().setXId( this.ranXid.nextInt() );

        this.logger.println( ">> Sending >> DHCPDISCOVER", dhcpMessageSend );

        // send DHCPDISCOVER
        send( dhcpMessageSend );
    }

    /*
     * DHCP Client message send/receive functions
     * <p>
     * Sends DHCP Message
     */
    private void send( final DHCPMessage dhcpMessageSend ) throws IOException
    {

        try( final DHCPSocket dhcpSocket = this.socketBuilder.newDHCPSocket() ) {
            dhcpSocket.send( dhcpMessageSend );

            boolean sentinal = true;

            while( sentinal ) {
                sentinal = internalSend( dhcpMessageSend, dhcpSocket );
            }
        }
    }

    private boolean internalSend(
        final DHCPMessage dhcpMessageSend,
        final DHCPSocket dhcpSocket
        ) throws IOException
    {
        if( dhcpSocket.receive( this.dhcpMessageReceived, this.logger ) ) {

            if( dhcpMessageSend.isSameThread( this.dhcpMessageReceived ) ) {
                //
                // C'est bien la réponse a notre message...
                //
                return false;
            } else {
                this.logger.println( "<< receving ERROR <<", this.dhcpMessageReceived );

                dhcpSocket.send( dhcpMessageSend );
            }
        } else {
            //
            // Time out
            //
            this.logger.println( ">> Time out >>", dhcpMessageSend );
            dhcpSocket.send( dhcpMessageSend );
        }

        return true;
    }

    /*
     * DHCP Client message send/receive functions
     * <p>
     * Sends DHCP Message
     */
    private boolean send(
        final DHCPMessage dhcpMessageSend,
        final long        t1,
        final long        t2
        ) throws IOException
    {
        try( final DHCPSocket dhcpSocket = this.socketBuilder.newDHCPSocket() ) {
            final int soTimeout = dhcpSocket.getSoTimeout() / 1000;

            int     elpstime = 1;
            boolean sentinal = true;

            // send DHCPREQUEST
            dhcpSocket.send( dhcpMessageSend );

            while( sentinal ) {
                if( ((elpstime * soTimeout) + t1) >= t2 ) {
                    return false; // TIME OUT for answer
                }

                elpstime = internalSent2( dhcpMessageSend, dhcpSocket, elpstime );

                if( elpstime == Integer.MAX_VALUE ) {
                    sentinal = false;
                }
            }

            return true;
        }
    }

    private int internalSent2(
        final DHCPMessage dhcpMessageSend,
        final DHCPSocket  dhcpSocket,
        final int         elpstime
        ) throws IOException
    {
        if( dhcpSocket.receive( this.dhcpMessageReceived, this.logger ) ) {

            if( dhcpMessageSend.isSameThread( this.dhcpMessageReceived ) ) {
                return Integer.MAX_VALUE; // sentinal = false
            } else {
                dhcpSocket.send( dhcpMessageSend );
                return elpstime + 1;
            }

        } else {
            //
            // Time out
            //
            dhcpSocket.send( dhcpMessageSend );

            return elpstime + 1;
        }
    }

    /*
     * Sends DHCPREQUEST Message and returns server message
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    private void sendRequest() throws UnknownHostException, IOException
    {
        //
        // Create a new DHCPMessage (from default parameters)
        //
        final DHCPMessage dhcpMessageSend = new DHCPMessage( this.dhcpParameters.getClone() );

        // setup message to send a DCHPREQUEST
        dhcpMessageSend.getDHCPParameters().setOp( DHCPParameters.OP_OPTION_BOOTREQUEST ); // 1

        // change message type
        dhcpMessageSend.getDHCPParameters().setOption( DHCPOptions.MESSAGE_TYPE, DHCPOptions.MESSAGE_TYPE_DHCPREQUEST );

        // set new IP value
        dhcpMessageSend.getDHCPParameters().setOption( DHCPOptions.REQUESTED_IP, this.dhcpMessageReceived.getDHCPParameters().getYIAddr() );

        this.logger.println( ">> DHCPOFFER >>", dhcpMessageSend );

        this.logger.println(
            TASK_NAME + " sending DHCPREQUEST for "
            + DHCPParameters.ip4AddrToString( dhcpMessageSend.getDHCPParameters().getOption( DHCPOptions.REQUESTED_IP ) )
            );

        send( dhcpMessageSend );
    }

    /*
     * Sends DHCPRENEW message and returns server message
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    private void reNew() throws UnknownHostException, SocketException, IOException
    {
        //
        // Create a new DHCPMessage (from default parameters)
        //
        final DHCPMessage dhcpMessageSend = new DHCPMessage( this.dhcpParameters.getClone() );

        // setup message to send a DCHPREQUEST
        dhcpMessageSend.getDHCPParameters().setOp( DHCPParameters.OP_OPTION_BOOTREQUEST );

        // change message type
        dhcpMessageSend.getDHCPParameters().setOption( DHCPOptions.MESSAGE_TYPE, DHCPOptions.MESSAGE_TYPE_DHCPREQUEST );

        // must set ciaddr
        dhcpMessageSend.getDHCPParameters().setCIAddr( this.dhcpMessageReceived.getDHCPParameters().getYIAddr() );

        final long t1 = DHCPParameters.byteToLong( this.dhcpMessageReceived.getDHCPParameters().getOption( DHCPOptions.T1_TIME ) );
        final long t2 = DHCPParameters.byteToLong( this.dhcpMessageReceived.getDHCPParameters().getOption( DHCPOptions.T2_TIME ) );

        final String dhcpServer = DHCPParameters.ip4AddrToString( this.dhcpMessageReceived.getDHCPParameters().getSIAddr() );

        // int soTimeout = dhcpSocket.getSoTimeout() / 1000; -
        // int elpstime = 1; -

        dhcpMessageSend.setInetAddress( InetAddress.getByName( dhcpServer ) );

        if( !send( dhcpMessageSend, t1, t2 ) ) {
            this.logger.println( TASK_NAME + " rebinding, T1 has ran out... " + dhcpServer );

            reBind();
        }
    }

    /*
     ** Sends DHCPREBIND message, returns server message
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    private void reBind() throws SocketException, IOException
    {
        //
        // Create a new DHCPMessage (form default parameters)
        //
        final DHCPMessage dhcpMessageSend = new DHCPMessage( this.dhcpParameters.getClone() );

        // setOp Method being used
        dhcpMessageSend.getDHCPParameters().setOp( DHCPParameters.OP_OPTION_BOOTREQUEST );

        // change message type
        dhcpMessageSend.getDHCPParameters().setOption( DHCPOptions.MESSAGE_TYPE, DHCPOptions.MESSAGE_TYPE_DHCPREQUEST );

        // must set ciaddr
        dhcpMessageSend.getDHCPParameters().setCIAddr( this.dhcpMessageReceived.getDHCPParameters().getYIAddr() );

        final long leaseTime = DHCPParameters.byteToLong( this.dhcpMessageReceived.getDHCPParameters().getOption( DHCPOptions.LEASE_TIME ) );
        final long t2 = DHCPParameters.byteToLong( this.dhcpMessageReceived.getDHCPParameters().getOption( DHCPOptions.T2_TIME ) );

        // int so_timeout = dhcpSocket.getSoTimeout() / 1000; -
        // int elpstime = 1; -

        if( !send( dhcpMessageSend, t2, leaseTime ) ) {
            this.logger.println( TASK_NAME + " is sending DHCPRELEASE, T2 has ran out " );
            this.logger.println( "shuttingdown." );

            sendRelease();
        }
    }

    /*
     ** Sends DHCPRELEASE message, returns nothing
     */
    private void sendRelease() throws IOException
    {
        //
        // Create a new DHCPMessage (form default parameters)
        //
        final DHCPMessage dhcpMessageSend = new DHCPMessage( this.dhcpParameters.getClone() );

        // setup message to send a DCHPREQUEST
        dhcpMessageSend.getDHCPParameters().setOp( DHCPParameters.OP_OPTION_BOOTREQUEST );

        // change message type
        dhcpMessageSend.getDHCPParameters().setOption( DHCPOptions.MESSAGE_TYPE, DHCPOptions.MESSAGE_TYPE_DHCPRELEASE );

        // send DHCPREQUEST
        try( final DHCPSocket dhcpSocket = this.socketBuilder.newDHCPSocket() ) {
            dhcpSocket.send( dhcpMessageSend );
        }

        this.running = false;
    }
}
