package com.googlecode.cchlib.dhcp.server.sample;

import com.googlecode.cchlib.dhcp.logger.DefaultDHCPLogger;
import com.googlecode.cchlib.dhcp.server.DHCPSimpleServer;

/**
 *
 */
public class DHCPServerSample {

    public final static void main( final String[] args )
    {
        final DefaultDHCPLogger logger = new DefaultDHCPLogger();
        final DHCPSimpleServer instance = new DHCPSimpleServer( "DHCPSimpleServer", logger, false );

        instance.start();

        logger.println( "done" );
    }
}
