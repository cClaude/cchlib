package com.googlecode.cchlib.dhcp.logger;

import com.googlecode.cchlib.dhcp.DHCPMessage;

public abstract class AbstractDHCPLogger implements DHCPLogger {

    @Override
    public final void println( final String messageName, final DHCPMessage dhcpMessage )
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( " --- " ).append( messageName ).append( " : --- BEGIN ---" );
        sb.append( dhcpMessage.toString() );
        // sb.append(  "--- " ).append( messageName + " ).append( --- HEXA! ---" );
        // sb.append( dhcpMessage.toHexString() );
        sb.append( " --- " ).append( messageName ).append( " : --- E N D ---" );

        println( sb.toString() );
    }
}
