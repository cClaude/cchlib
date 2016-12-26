package com.googlecode.cchlib.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.NeedTestCases;

/**
 * WakeOnLan implementation
 */
@NeedTestCases
public class WakeOnLan
{
    /** DEFAULT_BROADCAST_ADDR : {@value} */
    @SuppressWarnings("squid:S1313") // This is a broad cast IP
    public static final String DEFAULT_BROADCAST_ADDR = "255.255.255.255";

    /** DEFAULT_PORT : {@value} */
    public static final int DEFAULT_PORT = 7;

    private final int port;

    /**
     * Create a {@link WakeOnLan} using {@link DEFAULT_PORT}
     */
    public WakeOnLan()
    {
        this( DEFAULT_PORT );
    }

    /**
     * Create a {@link WakeOnLan} using given {@code port}
     *
      * @param port port to use to send magic number
     */
    public WakeOnLan( final int port )
    {
        this.port = port;
    }

    /**
     * Notify {@code macAddress} using {@link DEFAULT_BROADCAST_ADDR}
     *
     * @param macAddress MAC address to use
     * @throws SocketException if any
     * @throws IllegalArgumentException if {@code macAddress} is null
     * @throws IOException if any I/O error occur
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    public void notify( @Nonnull final String macAddress )
        throws  SocketException,
                IllegalArgumentException,
                IOException
    {
        notify( null, macAddress );
    }

    /**
     * Notify {@code macAddress} using {@code broadcastAddress}
     *
     * @param broadcastAddress broadcast to use
     * @param macAddress MAC address to use
     * @throws SocketException if any
     * @throws IllegalArgumentException if {@code macAddress} is null
     * @throws IOException if any I/O error occur
      */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    public void notify(
        final String broadcastAddress,
        final String macAddress
        ) throws SocketException,
                 IllegalArgumentException,
                 IOException
    {
        final String safeBroadcastAddress = fixBroadcastAddress( broadcastAddress );
        final byte[] macBytes             = getMacAddressBytes( macAddress );
        final byte[] bytes                = new byte[6 + (16 * macBytes.length)];

        for( int i = 0; i < 6; i++ ) {
            bytes[ i ] = -1;
            }

        for( int i = 6; i < bytes.length; i += macBytes.length ) {
            System.arraycopy( macBytes, 0, bytes, i, macBytes.length );
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

    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    private static byte[] getMacAddressBytes( final String macAddress )
        throws IllegalArgumentException
    {
        final byte[]   bytes = new byte[ 6 ];
        final String[] hex   = macAddress.split( "(\\:|\\-)" );

        if( hex.length != 6 ) {
            throw new IllegalArgumentException(
                    "Invalid MAC address '" + macAddress + "'."
                    );
            }

        try {
            for( int i = 0; i < 6; i++ ) {
                bytes[ i ] = (byte)Integer.parseInt( hex[ i ], 16 );
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
