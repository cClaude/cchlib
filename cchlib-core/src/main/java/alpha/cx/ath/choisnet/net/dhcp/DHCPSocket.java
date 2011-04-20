package alpha.cx.ath.choisnet.net.dhcp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class DHCPSocket extends java.net.DatagramSocket
{

    public static final int CLIENT_PORT = 68;
    public static final int SERVER_PORT = 67;
    public static final int DEFAULT_PACKET_SIZE = 1500;
    public static final int DEFAULT_SOTIME_OUT = 3000;
    private int packetSize;

    public DHCPSocket(int port, InetAddress laddr)
        throws java.net.SocketException
    {
        super(port, laddr);
        init();

    }

    public DHCPSocket(int port)
        throws java.net.SocketException
    {
        super(port);

        init();

    }

    private void init()
        throws java.net.SocketException
    {
        super.setSoTimeout(3000);

        packetSize = 1500;
    }

    public void setMTU(int packetSize)
    {
        this.packetSize = packetSize;
    }

    public int getMTU()
    {
        return packetSize;
    }

    public synchronized void send(DHCPMessage inMessage)
        throws java.io.IOException
    {
        super.send(inMessage.toDatagramPacket());
    }

    public synchronized boolean receive(DHCPMessage message)
        throws java.io.IOException
    {
        DHCPParameters params = receive();

        if(params != null) {
            message.setDHCPParameters(params);

            return true;
        }
        else {
            return false;
        }
    }

    public synchronized DHCPParameters receive()
        throws java.io.IOException
    {
        try {
            DatagramPacket incoming = new DatagramPacket(new byte[packetSize], packetSize);

            super.receive(incoming);

            return DHCPParameters.newInstance(incoming.getData());
        }
        catch(SocketTimeoutException e) {
            // TODO: use logs !
            System.err.println((new StringBuilder()).append("java.net.SocketTimeoutException: ").append(e).toString());
        }

        return null;
    }
}
