package com.googlecode.cchlib.dhcp.server;

/*
 ** -----------------------------------------------------------------------
 **  3.02.040 2006.09.05 Claude CHOISNET - Version initiale
 ** -----------------------------------------------------------------------
 */

import java.net.DatagramPacket;
import com.googlecode.cchlib.dhcp.DHCPMessage;
import com.googlecode.cchlib.dhcp.DHCPParameters;

/**
 ** @author Claude CHOISNET
 ** @since 3.02.040
 */
public class DHCPMessageFactory {

    public static final DHCPMessage newInstance(
            final DatagramPacket data )
    {
        final DHCPParameters params = DHCPParameters.newInstance( data.getData() );
        final DHCPMessage msg = new DHCPMessage( params );

        return msg;
    }

}
