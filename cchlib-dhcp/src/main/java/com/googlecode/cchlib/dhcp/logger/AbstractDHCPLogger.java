package com.googlecode.cchlib.dhcp.logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import com.googlecode.cchlib.dhcp.DHCPMessage;

public abstract class AbstractDHCPLogger implements DHCPLogger
{
    @Override
    public final void println( final String messageName, final DHCPMessage dhcpMessage )
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( " --- " ).append( messageName ).append( " : --- BEGIN ---\n" );
        sb.append( dhcpMessage.toString() );
        // sb.append(  "--- " ).append( messageName + " ).append( --- HEXA! ---" );
        // sb.append( dhcpMessage.toHexString() );
        sb.append( " --- " ).append( messageName ).append( " : --- E N D ---" );

        println( sb.toString() );
    }

    @Override
    public void printStackTrace( final Exception cause )
    {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try( PrintStream ps = new PrintStream( baos ) ) {
            cause.printStackTrace( ps );
        }

        println( baos.toString() );
    }
}
