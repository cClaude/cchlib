package com.googlecode.cchlib.net.dhcp.client0;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import com.googlecode.cchlib.net.dhcp.util0.DHCP;
import com.googlecode.cchlib.net.dhcp.util0.DHCPMessage;
import com.googlecode.cchlib.net.dhcp.util0.DHCPUtility;

public class DHCPClient {
    private static final int      MAX_BUFFER_SIZE = 1024;       // 1024 bytes
    private final int                 listenPort;//      = 68;
    private static String         serverIP        = "127.0.0.1";
    private final int            serverPort;//      = 67;

    private static DatagramSocket socket          = null;
    private static boolean        assignedIP;
    private static byte[]         clientIP;

    public DHCPClient()
    {
        this( DHCP.SERVER_PORT, DHCP.CLIENT_PORT );
    }

    public DHCPClient( final int serverPort, final int listenPort )
    {
        this.serverPort  = serverPort;
        this.listenPort  = listenPort;

        System.out.println( "Connecting to DHCPServer at " + serverIP
                + " on port " + serverPort + "..." );
        try {
            socket = new DatagramSocket( listenPort ); // ipaddress? throws
                                                       // socket exception
            assignedIP = false; // ip not assigned when client starts

            // sendTestPacket();

        }
        catch( final SocketException e ) {
            // TODO Auto-generated catch block !
            e.printStackTrace();
        }

    }

    private void sendTestPacket()
    {
        // byte[] payload = new byte[MAX_BUFFER_SIZE];
        final int length = 6;
        final byte[] payload = new byte[length];
        payload[ 0 ] = 'h';
        payload[ 1 ] = '3';
        payload[ 2 ] = 'l';
        payload[ 3 ] = 'l';
        payload[ 4 ] = 'o';
        payload[ 5 ] = '!';
        DatagramPacket p;
        try {
            p = new DatagramPacket( payload, length,
                    InetAddress.getByName( serverIP ), serverPort );
            // System.out.println("Connection Established Successfully!");
            System.out.println( "Sending data: "
                    + Arrays.toString( p.getData() ) );
            socket.send( p ); // throws i/o exception
            socket.send( p );
        }
        catch( final UnknownHostException e ) {
            // TODO Auto-generated catch block !
            e.printStackTrace();
        }
        catch( final IOException e ) {
            // TODO Auto-generated catch block !
            e.printStackTrace();
        }
    }

    public /*static*/ void sendPacket( final byte[] payload )
    {
        assert (payload.length <= MAX_BUFFER_SIZE);

        try {
            // bind socket to correct source ip
            if( assignedIP ) {
                InetSocketAddress assigned;
                assigned = new InetSocketAddress(
                        InetAddress.getByAddress( clientIP ), listenPort );
                socket.bind( assigned );
            } else { // source ip is 0.0.0.0 when requesting ip
                final InetSocketAddress broadcast = new InetSocketAddress(
                        InetAddress.getByName( "0.0.0.0" ), listenPort );
                socket.close();
                socket = new DatagramSocket( null );
                socket.bind( broadcast );
            }

            final DatagramPacket p = new DatagramPacket( payload, payload.length,
                    InetAddress.getByName( serverIP ), serverPort );
            System.out.println( "Sending data: "
                    + Arrays.toString( p.getData() ) );
            socket.send( p ); // throws i/o exception
        }
        catch( final UnknownHostException e ) {
            // TODO Auto-generated catch block !
            e.printStackTrace();
        }
        catch( final SocketException e ) {
            // TODO Auto-generated catch block !
            e.printStackTrace();
        }
        catch( final IOException e ) {
            // TODO Auto-generated catch block !
            e.printStackTrace();
        }

    }

    public /*static*/ void broadcastPacket( final byte[] payload )
    {
        final String temp = serverIP;
        serverIP = "255.255.255.255";
        sendPacket( payload );
        serverIP = temp;
    }

    /**
     * @param args
     * @throws SocketException
     * @throws UnknownHostException
     */
    public static void main( final String[] args ) throws UnknownHostException, SocketException
    {
        DHCPClient client;
        /* if (args.length >= 1) { server = new
         * DHCPClient(Integer.parseInt(args[0])); } else { */
        client = new DHCPClient();
        final DHCPMessage msgTest = new DHCPMessage();
        // msgTest.discoverMsg(getMacAddress());
        DHCPUtility.printMacAddress();
        // sendPacket(msgTest.externalize());
        msgTest.requestMsg( DHCPUtility.getMacAddress(), new byte[] {
                (byte)192, (byte)168, 1, 9 } );
        client.sendPacket( msgTest.externalize() );
        // }

    }

}