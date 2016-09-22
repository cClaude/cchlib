// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.NeedTestCases;

/**
 * TODOC
 * <p style="border:groove;">
 * <b>Warning:</b>
 * Insofar the code of this class comes from decompiling
 * my own code following the loss of source code, the use
 * of this class must do so under protest until I have
 * check its stability, it could be subject to significant
 * change.
 * </p>
 *
 */
@NeedDoc
@NeedTestCases
public class WakeOnLan
{
    public static final String DEFAULT_BROADCAST_ADDR = "255.255.255.255"; // NOSONAR
    public static final int    DEFAULT_PORT           = 7;

    private final int port;

    /**
     * TODOC
     */
    public WakeOnLan()
    {
        this( DEFAULT_PORT );
    }

    /**
     * TODOC
     * @param port
     */
    public WakeOnLan( final int port )
    {
        this.port = port;
    }

    /**
     * TODOC
     * @param macAddress
     * @throws UnknownHostException
     * @throws SocketException
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public void notify( final String macAddress ) // NOSONAR
        throws  UnknownHostException,
                SocketException,
                IllegalArgumentException,
                IOException
    {
        notify( null, macAddress );
    }

    /**
     * TODOC
     * @param broadcastAddress
     * @param macAddress
     * @throws UnknownHostException
     * @throws SocketException
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public void notify( // NOSONAR
        final String broadcastAddress,
        final String macAddress
        )
        throws  UnknownHostException,
                SocketException,
                IllegalArgumentException,
                IOException
    {
        final String safeBroadcastAddress = fixBroadcastAddress( broadcastAddress );
        final byte[] macBytes             = WakeOnLan.getMacAddressBytes(macAddress);
        final byte[] bytes                = new byte[6 + (16 * macBytes.length)];

        for( int i = 0; i < 6; i++ ) {
            bytes[i] = -1;
            }

        for( int i = 6; i < bytes.length; i += macBytes.length ) {
            System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
            }

        final InetAddress     address = InetAddress.getByName( safeBroadcastAddress );
        final DatagramPacket packet   = new DatagramPacket(bytes, bytes.length, address, this.port);

        try( final DatagramSocket socket = new DatagramSocket() ) {
            socket.send(packet);
        }
    }

    private String fixBroadcastAddress( final String broadcastAddressToFix )
    {
        final String broadcastAddress;

        if( broadcastAddressToFix != null ) {
            final String trim = broadcastAddressToFix.trim();

            if( trim.isEmpty() ) {
                broadcastAddress = DEFAULT_BROADCAST_ADDR;
            } else {
                broadcastAddress = trim;
            }
        } else {
            broadcastAddress = DEFAULT_BROADCAST_ADDR;
        }

        return broadcastAddress;
    }

    /**
     * TODOC
     * @param macAddress
     * @return TODOC
     * @throws IllegalArgumentException
     */
    private static byte[] getMacAddressBytes(
            final String macAddress
            )
        throws IllegalArgumentException
    {
        final byte[]      bytes   = new byte[6];
        final String[]    hex     = macAddress.split("(\\:|\\-)");

        if( hex.length != 6 ) {
            throw new IllegalArgumentException(
                    "Invalid MAC address '" + macAddress + "'."
                    );
            }

        try {
            for( int i = 0; i < 6; i++ ) {
                bytes[i] = (byte)Integer.parseInt(hex[i], 16);
                }
            }
        catch( final NumberFormatException e ) {
            throw new IllegalArgumentException(
                    "Invalid hex digit in MAC address '" + macAddress + "'."
                    );
            }

        return bytes;
    }
}
