package com.googlecode.cchlib.dhcp.server.sample;

import com.googlecode.cchlib.dhcp.logger.DHCPLogger;
import com.googlecode.cchlib.dhcp.logger.SystemOutDHCPLogger;
import com.googlecode.cchlib.dhcp.server.DHCPSimpleServer;

/**
 *
 */
public class DHCPServerSample
{
    private DHCPServerSample()
    {
        // App
    }

    public static final void main( final String[] args )
    {
        final DHCPLogger       logger   = new SystemOutDHCPLogger();
        final DHCPSimpleServer instance = new DHCPSimpleServer( "DHCPSimpleServer", logger, false, false );

        instance.start();

        logger.println( "done" );
    }
}
