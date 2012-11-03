package com.googlecode.cchlib.net.dhcp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;
import cx.ath.choisnet.ToDo;

/**
 * TODOC
 *
 */
@ToDo
public class DHCPMessage implements Cloneable
{
    private final static Logger logger = Logger.getLogger( DHCPMessage.class );
    public final static String BROADCAST_IP_ADDR = "255.255.255.255";
    private int messagePort;
    private InetAddress messageInetAddress;
    private DHCPParameters dhcpParameters;

    /**
     * Create a {@link DHCPMessage}
     * 
     * @param serverInetAddress {@link InetAddress} for DHCP, basicaly
     *        this is a broadcast address.
     * @param port              Port number of DHCP service
     * @param dhcpParameters    DHCP parameters
     * 
     * @see DHCPSocket#SERVER_PORT
     */
    public DHCPMessage(
            final InetAddress       serverInetAddress,
            final int               port,
            final DHCPParameters    dhcpParameters
            )
    {
        this.messageInetAddress = serverInetAddress;
        this.messagePort        = port;
        this.dhcpParameters     = dhcpParameters;
    }

    /**
     * Create a {@link DHCPMessage} using default {@link DHCPParameters}
     *
     * @param serverInetAddress DHCP Server address (or broadcast)
     * @param port              DHCP Server port
     * 
     * @see DHCPSocket#SERVER_PORT
     */
    public DHCPMessage(
            final InetAddress   serverInetAddress,
            final int           port
            )
    {
        this( serverInetAddress, port, new DHCPParameters() );
    }

    /**
     * Create a {@link DHCPMessage} using broadcast and default port to
     * join DHCP
     *
     * @param dhcpParameters    DHCP parameters
     * 
     * @throws UnknownHostException if {@link InetAddress} build to
     *         join DHCP could not be created
     */
    public DHCPMessage( final DHCPParameters dhcpParameters ) throws UnknownHostException
    {
        this(   getDefaultBroadcastInetAddress(), 
                DHCPSocket.SERVER_PORT, 
                dhcpParameters
                );
    }

    /**
     * Create a {@link DHCPMessage} using broadcast, default port and
     * default {@link DHCPParameters} to join DHCP
     * 
     * @throws UnknownHostException if {@link InetAddress} build to
     *         join DHCP could not be created
     */
    public DHCPMessage() throws UnknownHostException
    {
        this( new DHCPParameters() );
    }
    
    /**
     * Returns broadcast IP address
     * @return broadcast IP address
     * @throws UnknownHostException if no IP address for the host could be found, or if a scope_id was specified for a global IPv6 address.
     * @throws SecurityException if a security manager exists and its checkConnect method doesn't allow the operation
     */
    private static InetAddress getDefaultBroadcastInetAddress() 
        throws UnknownHostException, SecurityException
    {
        return InetAddress.getByName( BROADCAST_IP_ADDR );
    }

    /**
     * Set message port number
     * @param messagePort Port number to set
     * 
     * @return Current {@link DHCPMessage} for initialization chaining
     */
    public DHCPMessage setPort( final int messagePort )
    {
        this.messagePort = messagePort;

        return this;
    }

    /**
     * TODOC
     * @return TODOC
     */
    public int getPort()
    {
        return this.messagePort;
    }

    /**
     * 
     * @param messageInetAddress
     * 
     * @return Current {@link DHCPMessage} for initialization chaining
     */
    public DHCPMessage setInetAddress(
        final InetAddress messageInetAddress
        )
    {
        this.messageInetAddress = messageInetAddress;

        return this;
    }

    /**
     * TODOC
     * @return TODOC
     */
    public InetAddress getInetAddress()
    {
        return messageInetAddress;
    }

    /**
     * TODOC
     * @param dhcpParameters
     */
    public void setDHCPParameters(DHCPParameters dhcpParameters)
    {
        this.dhcpParameters = dhcpParameters;
    }

    /**
     * TODOC
     * @return TODOC
     */
    public DHCPParameters getDHCPParameters()
    {
        return dhcpParameters;
    }
    
    /**
     * TODOC
     * @return TODOC
     */
    public DatagramPacket toDatagramPacket()
    {
        final byte[] data = getDHCPParameters().toByteArray();

        return new DatagramPacket(data, data.length, getInetAddress(), getPort());
    }

    /**
     * TODOC
     * @param aOtherDHCPMessage
     * @return TODOC
     */
    public boolean isSameThread(DHCPMessage aOtherDHCPMessage)
    {
        boolean b = getDHCPParameters().getXId() == aOtherDHCPMessage.getDHCPParameters().getXId();

        logger.warn(
            (new StringBuilder())
                .append("######## isSameThread() -> ")
                .append(b)
                .toString()
            );
        return b;
    }

    /**
     * Returns a new DHCPMessage with same configuration
     * @return a new DHCPMessage with same configuration
     */
    public DHCPMessage createClone()
    {
        return new DHCPMessage(
            this.messageInetAddress,
            this.messagePort,
            this.dhcpParameters.createClone()
            );
    }

    @Override 
    public Object clone()
    {
        return createClone();
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "DHCPMessage [messagePort=" );
        builder.append( messagePort );
        builder.append( ", messageInetAddress=" );
        builder.append( messageInetAddress );
        builder.append( ", dhcpParameters=" );
        builder.append( dhcpParameters );
        builder.append( ']' );
        return builder.toString();
    }
}
