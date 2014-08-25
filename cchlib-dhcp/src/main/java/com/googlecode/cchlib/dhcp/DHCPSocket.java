package com.googlecode.cchlib.dhcp;

/*
 ** -----------------------------------------------------------------------
 **  3.02.014 2006.06.21 Base on code of Jason Goldschmidt and Nick Stone
 **                      edu.bucknell.net.JDHCP.DHCPSocket
 **                      http://www.eg.bucknell.edu/~jgoldsch/dhcp/
 **                      and RFCs 1700, 2131, 2132
 ** -----------------------------------------------------------------------
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 ** This class represents a Socket for sending DHCP Messages
 **
 ** @author Jason Goldschmidt
 ** @author Claude CHOISNET
 */
public class DHCPSocket extends DatagramSocket {
    /**
     ** Default DHCP client port (68)
     */
    public static final int CLIENT_PORT         = 68;

    /**
     ** Default DHCP server port (67)
     */
    public static final int SERVER_PORT         = 67;

    /**
     ** Default MTU for ethernet (1500 bytes)
     */
    public final static int DEFAULT_PACKET_SIZE = 1500;

    /**
     ** Default socket timeout (3 second)
     */
    public final static int DEFAULT_SOTIME_OUT  = 3000;

    private int             packetSize;

    /**
     ** Constructor for creating DHCPSocket bound to the specified local address, on a specific port.
     **
     ** @param port
     *            local port to use
     ** @param laddr
     *            local address to bind
     */
    public DHCPSocket( final int port, final InetAddress laddr ) throws SocketException
    {
        super( port, laddr );

        init();
    }

    /**
     ** Constructor for creating DHCPSocket on a specific port on the local machine.
     **
     ** @param port
     *            local port to use
     */
    public DHCPSocket( final int port ) throws SocketException
    {
        super( port );

        init();
    }

    /**
**
*/
    private void init() throws SocketException
    {
        // set default time out
        super.setSoTimeout( DEFAULT_SOTIME_OUT );

        this.packetSize = DEFAULT_PACKET_SIZE;
    }

    /**
     ** Sets the Maximum Transfer Unit for the UDP DHCP Packets to be set. Default is 1500, MTU for Ethernet
     **
     ** @param packetSize
     *            integer representing desired MTU
     */
    public void setMTU( final int packetSize ) // -----------------------------
    {
        this.packetSize = packetSize;
    }

    /**
     ** Returns the set MTU for this socket
     **
     ** @return the Maximum Transfer Unit set for this socket
     **/
    public int getMTU() // ----------------------------------------------------
    {
        return this.packetSize;
    }

    /**
     ** Sends a DHCPMessage object to a predifined host.
     **
     ** @param inMessage
     *            well-formed DHCPMessage to be sent to a server
     */
    public synchronized void send( final DHCPMessage inMessage ) throws IOException
    {
        super.send( inMessage.toDatagramPacket() ); // send outgoing message
    }

    /**
     ** Receives a datagram packet containing a DHCP Message into a DHCPMessage object.
     **
     ** @param message
     *            DHCPMessage object to receive new message into
     **
     ** @return true if message is received, false if timeout occurs.
     **
     ** @return a valid DHCPMessage, if message is received or null if timeout occurs.
     */
    public synchronized boolean receive( final DHCPMessage message ) throws IOException
    {
        final DHCPParameters params = receive();

        if( params != null ) {
            message.setDHCPParameters( params );

            return true;
        }

        return false;
    }

    /**
     ** Receives a datagram packet containing a DHCP Message into a DHCPMessage object.
     **
     ** @return a valid DHCPMessage, if message is received or null if timeout occurs.
     */
    public synchronized DHCPParameters receive() throws IOException
    {
        try {
            final DatagramPacket incoming = new DatagramPacket( new byte[this.packetSize], this.packetSize );

            // block on receive for SO_TIMEOUT
            super.receive( incoming );

            return DHCPParameters.newInstance( incoming.getData() );
        }
        catch( final SocketTimeoutException e ) {
            System.err.println( "SocketTimeoutException: " + e );
            e.printStackTrace( System.err );

            return null;
        }

    }

}
