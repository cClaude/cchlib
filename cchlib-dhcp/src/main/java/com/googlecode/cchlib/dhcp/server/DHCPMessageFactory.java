package com.googlecode.cchlib.dhcp.server;

import java.net.DatagramPacket;
import com.googlecode.cchlib.dhcp.DHCPMessage;
import com.googlecode.cchlib.dhcp.DHCPParameters;

/**
 *
 * @since 3.02
 */
public class DHCPMessageFactory
{
    private DHCPMessageFactory()
    {
        // Static
    }

    public static final DHCPMessage newInstance( final DatagramPacket data )
    {
        final DHCPParameters params = DHCPParameters.newInstance( data.getData() );

        return new DHCPMessage( params );
    }
}
