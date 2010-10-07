package cx.ath.choisnet.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import cx.ath.choisnet.ToDo;

/**
 * <p style="border:groove;">
 * <b>Warning:</b>
 * Insofar the code of this class comes from decompiling
 * my own code following the loss of source code, the use
 * of this class must do so under protest until I have
 * check its stability, it could be subject to significant
 * change.
 * <br/>
 * <br/>
 * <b>Attention:</b>
 * Dans la mesure où le code de cette classe est issue de
 * la décompilation de mon propre code, suite à la perte
 * du code source, l'utilisation de cette classe doit ce
 * faire sous toute réserve tant que je n'ai pas vérifier
 * sa stabilité, elle est donc sujette à des changements 
 * importants.
 * </p>
 *
 * @author Claude CHOISNET
 *
 */
@ToDo
public class WakeOnLan
{
    public static final int PORT = 7;
    private final int port;

    public WakeOnLan()
    {
        this(PORT);
    }

    public WakeOnLan(int port)
    {
        this.port = port;
    }

    public void notify(String macAddress)
        throws  java.net.UnknownHostException,
                java.net.SocketException,
                IllegalArgumentException,
                java.io.IOException
    {
        notify(null, macAddress);
    }

    public void notify(String broadcastAddress, String macAddress)
        throws  java.net.UnknownHostException,
                java.net.SocketException,
                IllegalArgumentException,
                java.io.IOException
    {
        if(broadcastAddress != null) {
            broadcastAddress = broadcastAddress.trim();
        }

        if(broadcastAddress == null || broadcastAddress.length() == 0) {
            broadcastAddress = "255.255.255.255";
        }

        byte[] macBytes = WakeOnLan.getMacAddressBytes(macAddress);
        byte[] bytes = new byte[6 + 16 * macBytes.length];

        for(int i = 0; i < 6; i++) {
            bytes[i] = -1;
        }

        for(int i = 6; i < bytes.length; i += macBytes.length) {
            System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
        }

        InetAddress     address = InetAddress.getByName(broadcastAddress);
        DatagramPacket packet   = new DatagramPacket(bytes, bytes.length, address, port);
        DatagramSocket socket   = new DatagramSocket();

        socket.send(packet);
        socket.close();
    }

    protected static byte[] getMacAddressBytes(String macAddress)
        throws java.lang.IllegalArgumentException
    {
        byte[]      bytes   = new byte[6];
        String[]    hex     = macAddress.split("(\\:|\\-)");

        if(hex.length != 6) {
            throw new IllegalArgumentException(
                    (new StringBuilder())
                        .append("Invalid MAC address '")
                        .append(macAddress)
                        .append("'.")
                        .toString()
                        );
        }

        try {
            for(int i = 0; i < 6; i++) {
                bytes[i] = (byte)Integer.parseInt(hex[i], 16);
            }
        }
        catch(java.lang.NumberFormatException e) {
            throw new IllegalArgumentException(
                    (new StringBuilder())
                        .append("Invalid hex digit in MAC address '")
                        .append(macAddress)
                        .append("'.")
                        .toString()
                        );
        }

        return bytes;
    }
}
