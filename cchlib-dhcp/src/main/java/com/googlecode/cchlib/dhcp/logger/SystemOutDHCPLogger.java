package com.googlecode.cchlib.dhcp.logger;

/**
 * Simple implementation for DHCPLogger
 */
public class SystemOutDHCPLogger extends AbstractDHCPLogger
{
    @Override
    @SuppressWarnings("squid:S106")
    public final void println( final String message )
    {
        System.out.println( message );
    }
}
