package com.googlecode.cchlib.dhcp.client.sample;

import com.googlecode.cchlib.dhcp.DHCPHelper;
import com.googlecode.cchlib.dhcp.DHCPParameters;
import com.googlecode.cchlib.dhcp.DHCPSocket;
import com.googlecode.cchlib.dhcp.client.DHCPSimpleClient;
import com.googlecode.cchlib.dhcp.client.DHCPSocketBuilder;
import com.googlecode.cchlib.dhcp.logger.DHCPLogger;
import com.googlecode.cchlib.dhcp.logger.SystemOutDHCPLogger;

/**
 ** dhcp client simulation program
 */
public class DHCPClientSample
{
    private DHCPClientSample()
    {
        // App
    }

    /**
     * Run using
     * <pre>
     *  java com.googlecode.cchlib.dhcp.client.sample.DHCPClientSample 00-0D-56-D7-2A-A5
     * </pre>
     *
     * @param args An array of one String within the MAC Address
     */
    public static void main__( final String[] args ) // -------------------------
    {
        final DHCPLogger logger = newDHCPLogger();

        if( args.length == 0 ) {
            logger.println(
                "Usage: dhcpclient <ethernet_addresss>\n"
                    + "\tie. dhcpclient 12:34:56:76:89:AB"
                );
            System.exit( 1 );
        }

        run( logger, args[ 0 ] );
    }

    public static void main( final String[] args )
    {
        final DHCPLogger logger = newDHCPLogger();

        run( logger, "00-0D-56-D7-FF-FF" );
    }

    public static void run(
        final DHCPLogger logger ,
        final String     macAddress
        )
    {
        final DHCPParameters params = DHCPHelper.newDHCPParameters( macAddress );

        // Put the hardware address of the computer you are using here.
        final DHCPSimpleClient aClient = new DHCPSimpleClient(
                newDHCPSocketBuilder(),
                params,
                logger
                );

        aClient.start(); // start the client. Sit back and enjoy the simulation fun
    }

    private static DHCPSocketBuilder newDHCPSocketBuilder()
    {
        return DHCPHelper.newDHCPSocketBuilder( DHCPSocket.CLIENT_PORT );
    }

    private static DHCPLogger newDHCPLogger()
    {
        return new SystemOutDHCPLogger();
    }

}

