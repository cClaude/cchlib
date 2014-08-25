package com.googlecode.cchlib.dhcp.client.sample;

import java.net.BindException;
import java.net.SocketException;
import com.googlecode.cchlib.dhcp.DHCPOptions;
import com.googlecode.cchlib.dhcp.DHCPParameters;
import com.googlecode.cchlib.dhcp.DHCPSocket;
import com.googlecode.cchlib.dhcp.client.DHCPSimpleClient;
import com.googlecode.cchlib.dhcp.logger.DefaultDHCPLogger;

/**
 ** dhcp client simulation program
 */
public class DHCPClientSample {

    /**
     ** .java com.googlecode.cchlib.dhcp.client.sample.DHCPClientSample 00-0D-56-D7-2A-A5
     */
    public static void main( final String[] args ) // -------------------------
    {
        if( args.length == 0 ) {
            System.out.println( "Usage: dhcpclient <ethernet_addresss>\n" + "\tie. dhcpclient 12:34:56:76:89:AB" );
            System.exit( 1 );
        }

        final String hwaddr = args[ 0 ];

        // create socket
        // Use port 67 if you are configuring as a bootp relay agent
        try( final DHCPSocket mySocket = new DHCPSocket( DHCPSocket.CLIENT_PORT ) ) {

            //
            //
            //
            final DHCPParameters params = getDefaultDHCPParameters( DHCPSimpleClient.addrToByte( hwaddr ) );

            // Put the hardware address of the computer you are using here.
            final DHCPSimpleClient aClient = new DHCPSimpleClient( mySocket, params, hwaddr, new DefaultDHCPLogger() );

            aClient.start(); // start the client. Sit back and enjoy the simulation fun
        }
        catch( final BindException e ) {
            System.err.println( "Socket Bind Error: " );
            System.err.print( "Another process is bound to this port ("
                    +  DHCPSocket.CLIENT_PORT
                    + ") or you do not have access to bind a process to this port" );

            System.exit( 1 );
        }
        catch( final SocketException e ) {
            System.err.println( "SocketException: " + e );

            e.printStackTrace( System.err );

            System.exit( 1 );
        }
    }

    private static DHCPParameters getDefaultDHCPParameters( final byte[] hwaddr )
    {
        final DHCPParameters dhcpParameters = new DHCPParameters();

        dhcpParameters.setOp( (byte)0 ); // -- init --
        dhcpParameters.setHType( (byte)6 ); // IEEE802.3
        dhcpParameters.setHLen( (byte)6 ); // Taille adresse MAC
        dhcpParameters.setHOps( (byte)0 );
        dhcpParameters.setXId( (byte)0 ); // -- init -- should be a random int
        dhcpParameters.setSecs( (short)0 );
        dhcpParameters.setFlags( (short)0 );

        // set globaly defined hwaddr
        dhcpParameters.setChaddr( hwaddr );

        // Uncomment below to set host up as a bootp relay agent. Do this
        // if you are trying to send messages containing hardware adresses
        // other than your own.

        // try {
        // dhcpParameters.setGIAddr( java.net.InetAddress.getLocalHost().getAddress() );
        // }
        // catch( java.net.UnknownHostException e ) {
        // throw new RuntimeException( e );
        // }

        dhcpParameters.setOption( DHCPOptions.MESSAGE_TYPE, DHCPOptions.MESSAGE_TYPE_DHCPDISCOVER );

        dhcpParameters.setOption( DHCPOptions.CLASS_ID, "xp_etu" );
        // dhcpParameters.setOption( DHCPOptions.CLIENT_ID, "usine" );

        return dhcpParameters;
    }

}

