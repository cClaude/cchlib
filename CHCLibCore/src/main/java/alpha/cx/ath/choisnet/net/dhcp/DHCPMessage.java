package alpha.cx.ath.choisnet.net.dhcp;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class DHCPMessage
{
    public static final String BROADCAST_IP_ADDR = "255.255.255.255";
    public static final InetAddress BROADCAST_ADDR;
    private int messagePort;
    private java.net.InetAddress messageInetAddress;
    private DHCPParameters dhcpParameters;

    static
    {
        try {
            BROADCAST_ADDR = java.net.InetAddress.getByName("255.255.255.255");
        }
        catch(java.net.UnknownHostException e) {
            e.printStackTrace( System.err );
            throw new RuntimeException(e);
        }

    }

    public DHCPMessage(InetAddress serverInetAddress, int port, DHCPParameters dhcpParameters)
    {
        messageInetAddress = serverInetAddress;
        messagePort = port;
        this.dhcpParameters = dhcpParameters;
    }

    public DHCPMessage(InetAddress serverInetAddress, int port)
    {
        this(serverInetAddress, port, new DHCPParameters());
    }

    public DHCPMessage(DHCPParameters dhcpParameters)
    {
        this(BROADCAST_ADDR, 67, dhcpParameters);
    }

    public DHCPMessage()
    {
        this(new DHCPParameters());
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
        byte[] data = getDHCPParameters().toByteArray();
        return new DatagramPacket(data, data.length, getInetAddress(), getPort());

    }

    public DHCPParameters getDHCPParameters()
    {
        return dhcpParameters;

    }

    public boolean isSameThread(DHCPMessage aOtherDHCPMessage)
    {
        boolean b = getDHCPParameters().getXId() == aOtherDHCPMessage.getDHCPParameters().getXId();

        // TODO: use log !
        System.out.println(
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
