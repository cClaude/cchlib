package com.googlecode.cchlib.net.dhcp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;
import cx.ath.choisnet.ToDo;

/**
 *
 *
 */
@ToDo
public class DHCPMessage
{
    private final static Logger logger = Logger.getLogger( DHCPMessage.class );
    public final static String BROADCAST_IP_ADDR = "255.255.255.255";
    public final static InetAddress BROADCAST_ADDR;
    private int messagePort;
    private InetAddress messageInetAddress;
    private DHCPParameters dhcpParameters;

    static
    {
        try {
            BROADCAST_ADDR = InetAddress.getByName("255.255.255.255");
        }
        catch(UnknownHostException e) {
            e.printStackTrace( System.err );
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @param serverInetAddress
     * @param port
     * @param dhcpParameters
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
     * @param serverInetAddress DHCP Server address
     * @param port              DHCP Server port
     */
    public DHCPMessage(
            final InetAddress   serverInetAddress,
            final int           port
            )
    {
        this(serverInetAddress, port, new DHCPParameters());
    }

    /**
     * Create a {@link DHCPMessage} using broadcast and default port to
     * join DHCP
     *
     * @param dhcpParameters
     */
    public DHCPMessage(final DHCPParameters dhcpParameters)
    {
        this(BROADCAST_ADDR, DHCPSocket.SERVER_PORT, dhcpParameters);
    }

    /**
     * Create a {@link DHCPMessage} using broadcast, default port and
     * default {@link DHCPParameters} to join DHCP
     */
    public DHCPMessage()
    {
        this( new DHCPParameters() );
    }

    public DHCPMessage setPort(int inPortNum)
    {
        messagePort = inPortNum;

        return this;
    }

    public int getPort()
    {
        return messagePort;
    }

    public DHCPMessage setInetAddress(InetAddress messageInetAddress)
    {
        this.messageInetAddress = messageInetAddress;

        return this;
    }

    public InetAddress getInetAddress()
    {
        return messageInetAddress;
    }

    public void setDHCPParameters(DHCPParameters dhcpParameters)
    {
        this.dhcpParameters = dhcpParameters;
    }

    public DatagramPacket toDatagramPacket()
    {
        final byte[] data = getDHCPParameters().toByteArray();

        return new DatagramPacket(data, data.length, getInetAddress(), getPort());
    }

    public DHCPParameters getDHCPParameters()
    {
        return dhcpParameters;
    }

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

    public DHCPMessage getClone()
    {
        DHCPMessage copy = new DHCPMessage();

        copy.messageInetAddress = messageInetAddress;
        copy.messagePort = messagePort;
        copy.dhcpParameters = dhcpParameters.getClone();

        return copy;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append( super.toString() );
        sb.append( "\n" );
        sb.append(getDHCPParameters().toString());

        return sb.toString();
    }

    public String toHexString()
    {
        return getDHCPParameters().toHexString();
    }

}
