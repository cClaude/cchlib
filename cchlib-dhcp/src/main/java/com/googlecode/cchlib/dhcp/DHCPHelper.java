package com.googlecode.cchlib.dhcp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import com.googlecode.cchlib.dhcp.client.DHCPSocketBuilder;

public class DHCPHelper
{
    private DHCPHelper()
    {
        // All static
    }

    public static DHCPParameters newDHCPParameters( final byte[] hwaddr )
    {
        final DHCPParameters dhcpParameters = new DHCPParameters();

        dhcpParameters.setOp   (  (byte)0 ); // -- init --
        dhcpParameters.setHType(  (byte)6 ); // IEEE802.3
        dhcpParameters.setHLen (  (byte)hwaddr.length ); // MAC address length (6)
        dhcpParameters.setHOps (  (byte)0 );
        dhcpParameters.setXId  (  (byte)0 ); // -- init -- should be a random int
        dhcpParameters.setSecs ( (short)0 );
        dhcpParameters.setFlags( (short)0 );

        // set globally defined hwaddr
        dhcpParameters.setChaddr( hwaddr );

        dhcpParameters.setOption(
                DHCPOptions.MESSAGE_TYPE,
                DHCPOptions.MESSAGE_TYPE_DHCPDISCOVER
                );

        return dhcpParameters;
    }

    public static DHCPParameters setClassID(
        final DHCPParameters dhcpParameters,
        final String         dhcpClassId
        )
    {
        dhcpParameters.setOption( DHCPOptions.CLASS_ID, dhcpClassId );

        return dhcpParameters;
    }

    public static DHCPParameters setBootp(
        final DHCPParameters dhcpParameters
        ) throws UnknownHostException
    {
        // To set host up as a bootp relay agent. Do this
        // if you are trying to send messages containing hardware addresses
        // other than your own.

        dhcpParameters.setGIAddr( InetAddress.getLocalHost().getAddress() );

        return dhcpParameters;
    }

    public static DHCPParameters newDHCPParameters( final String macAddress )
    {
        return newDHCPParameters( addrToByte( macAddress ) );
    }

    public static DHCPSocketBuilder newDHCPSocketBuilder( final int clientPort )
    {
        return () -> new DHCPSocket( clientPort );
    }

    public static DHCPSocketBuilder newDHCPSocketBuilder(
        final int         clientPort,
        final InetAddress localAddress
        )
    {
        return () -> new DHCPSocket( clientPort, localAddress );
    }

    /**
     * Converts the MAC Address string into a byte[]
     *
     * @param addr MAC Address to convert
     * @return equivalent MAC Address into a bytes array
     */
    public static final byte[] addrToByte( final String addr ) // -------------
    {
        final StringTokenizer token    = new StringTokenizer( addr, ":-" );
        final byte[]         outHwaddr = new byte[ 16 ];

        int i = 0;

        while( i < 6 ) {
            final int temp = Integer.parseInt( token.nextToken(), 16 );

            outHwaddr[ i++ ] = (byte)temp;
        }

        return outHwaddr;
    }
}
