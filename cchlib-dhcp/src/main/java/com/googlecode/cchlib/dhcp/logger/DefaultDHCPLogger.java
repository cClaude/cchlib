package com.googlecode.cchlib.dhcp.logger;

/**
 * Simple implementation for DHCPLogger
 */
public class DefaultDHCPLogger extends AbstractDHCPLogger {

    @Override
    public final void println( final String message )
    {
        System.out.println( message );
    }

}
