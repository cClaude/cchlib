package com.googlecode.cchlib.dhcp;

/*
 ** -----------------------------------------------------------------------
 **  3.02.014 2006.06.21 Claude CHOISNET - Version initiale
 **                      Adapte du code de Jason Goldschmidt and Nick Stone
 **                      edu.bucknell.net.JDHCP.DHCPMessage
 **                      http://www.eg.bucknell.edu/~jgoldsch/dhcp/
 **                      et base sur les RFCs 1700, 2131 et 2132
 ** -----------------------------------------------------------------------
 */

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 ** This class represents a DHCP Message.
 **
 ** @author Jason Goldschmidt and Nick Stone
 ** @author Claude CHOISNET
 */
public class DHCPMessage
{
    @SuppressWarnings("squid:S1313") // Broadcast Address
    public static final String BROADCAST_IP_ADDR = "255.255.255.255";

    /**
     * @see #BROADCAST_IP_ADDR
     */
    public static final InetAddress BROADCAST_ADDR;

    static {
        try {
            //
            // broadcast address(by default)
            //
            BROADCAST_ADDR = InetAddress.getByName( BROADCAST_IP_ADDR );
        }
        catch( final UnknownHostException e ) {
            throw new RuntimeException( e );
        }
    }

    /** port of the server */
    private int                     messagePort;

    /** InetAddress of the server */
    private InetAddress             messageInetAddress;

    private DHCPParameters          dhcpParameters;

    /**
     * Creates a DHCPMessage object<br>
     * initializes the object, sets the host to a specified host name,
     * and binds to a specified port.
     *
     * @param serverInetAddress
     *            the host InetAddress
     * @param port
     *            the port number
     * @param dhcpParameters
     *            A DHCPParameters object
     */
    public DHCPMessage( // ----------------------------------------------------
        final InetAddress    serverInetAddress,
        final int            port,
        final DHCPParameters dhcpParameters
        )
    {
        this.messageInetAddress = serverInetAddress;
        this.messagePort        = port;
        this.dhcpParameters     = dhcpParameters;
    }

    /**
     * Creates a DHCPMessage object <br>
     * initializes the object, sets the host to a specified host name, and
     * binds to a specified port.
     *
     * @param serverInetAddress
     *            the host InetAddress
     * @param port
     *            the port number
     */
    public DHCPMessage( // ----------------------------------------------------
        final InetAddress serverInetAddress,
        final int         port
        )
    {
        this( serverInetAddress, port, new DHCPParameters() );
    }

    /**
     * Creates a DHCPMessage object <br>
     * initializes the object, sets the host to the broadcast address,
     * and binds to a specified port.
     *
     * @param dhcpParameters
     *            A DHCPParameters object
     */
    public DHCPMessage( final DHCPParameters dhcpParameters ) // --------------
    {
        this( BROADCAST_ADDR, DHCPSocket.SERVER_PORT, dhcpParameters );
    }

    /**
     * Creates a DHCPMessage object <br>
     * initializes the object, sets the host to the broadcast address, the
     * local subnet, binds to the default server port.
     */
    public DHCPMessage() // ---------------------------------------------------
    {
        this( new DHCPParameters() );
    }

    /**
     * Set message destination port.
     *
     * @param inPortNum
     *            port on message destination host
     *
     * @return current 'this' object
     */
    public DHCPMessage setPort( final int inPortNum ) // ----------------------
    {
        this.messagePort = inPortNum;

        return this;
    }

    /**
     * Get message destination port
     *
     * @return an integer representation of the message destination port
     */
    public int getPort() // ---------------------------------------------------
    {
        return this.messagePort;
    }

    /**
     * Set message InetAddress
     *
     * @param messageInetAddress Message address
     * @return current 'this' object
     */
    public DHCPMessage setInetAddress( final InetAddress messageInetAddress ) // -----
    {
        this.messageInetAddress = messageInetAddress;

        return this;
    }

    /**
     * Get message InetAddress
     *
     * @return a {@link InetAddress} representing the message destination server
     */
    public InetAddress getInetAddress() // ------------------------------------
    {
        return this.messageInetAddress;
    }

    public DatagramPacket toDatagramPacket() // -------------------------------
    {
        final byte[] data = this.getDHCPParameters().toByteArray();

        return new DatagramPacket( data, data.length, this.getInetAddress(), this.getPort() );
    }

    /**
     * Set message {@link DHCPParameters}
     *
     * @param dhcpParameters
     *            a valid {@link DHCPParameters} object
     */
    public void setDHCPParameters( final DHCPParameters dhcpParameters ) // ---
    {
        this.dhcpParameters = dhcpParameters;
    }

    /**
     * Set message {@link DHCPParameters} from an other {@link DHCPMessage}
     *
     * @param anOtherDHCPMessage
     *            an other {@link DHCPMessage}
     */
    public void setDHCPParameters( // -----------------------------------------
            final DHCPMessage anOtherDHCPMessage )
    {
        this.setDHCPParameters( anOtherDHCPMessage.getDHCPParameters().getClone() );
    }

    /**
     * Get message DHCPParameters
     *
     * @return a {@link DHCPParameters}
     */
    public DHCPParameters getDHCPParameters() // ------------------------------
    {
        return this.dhcpParameters;
    }

    /**
     * Find if messages have same xid value
     * @param aOtherDHCPMessage an other message
     * @return a true if boot message have same xid value
     */
    public boolean isSameThread( final DHCPMessage aOtherDHCPMessage ) // -----
    {
        return this.getDHCPParameters().getXId() == aOtherDHCPMessage.getDHCPParameters().getXId();
    }

    /**
     * Return message type value
     *
     * @return a byte with in value of message type
     */
    public byte getMessageType() // -------------------------------------------
    {
        return this.getDHCPParameters().getOptionAsByte( DHCPOptions.MESSAGE_TYPE );
    }

    public String toHexString() // --------------------------------------------
    {
        return this.getDHCPParameters().toHexString();
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( "DHCPMessage [messagePort=" );
        sb.append( this.messagePort );
        sb.append( ", messageInetAddress=" );
        sb.append( this.messageInetAddress );
        sb.append( ", dhcpParameters=\n" );
        sb.append( toHexString() );
        sb.append( "\n]" );

        return sb.toString();
    }
}
