package com.googlecode.cchlib.dhcp.client;

import com.googlecode.cchlib.dhcp.DHCPMessage;

public class DefaultDHCPLogger implements DHCPLogger {

    @Override
    public final void println( final String message )
    {
        System.out.println( message );
    }

    @Override
    public final void println( final String messageName, final DHCPMessage dhcpMessage )
    {
        System.out.println( " --- " + messageName + " : --- BEGIN ---" );
        System.out.println( dhcpMessage.toString() );
        // System.out.println( " --- " + messageName + " : --- HEXA! ---" );
        // System.out.println( dhcpMessage.toHexString() );
        System.out.println( " --- " + messageName + " : --- E N D ---" );
    }

}
