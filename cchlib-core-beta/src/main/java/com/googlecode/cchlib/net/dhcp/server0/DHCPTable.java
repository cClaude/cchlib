package com.googlecode.cchlib.net.dhcp.server0;

import com.googlecode.cchlib.net.dhcp.util0.DHCPUtils;

public class DHCPTable {
    private DHCPTableEntry[] dhcp = new DHCPTableEntry[254]; // 0,255 reserved(1-254 assignable)

    public DHCPTableEntry findMac( byte[] chAddr )
    {
        return findMac( DHCPUtils.ip4ToString( chAddr ) );
    }

    public DHCPTableEntry findMac( String addr )
    {
//        /*
//         * byte[] ip = assignIP(request.getGIAddr()); addAssignedIP(ip); */
//        int row = -1;
//        for( int i = 0; i < macTable.length; i++ ) {
//            if( DHCPUtility.isEqual( request.getCHAddr(), macTable[ i ] ) ) {
//                row = i;
//            }
//        }

        for( DHCPTableEntry entry : dhcp ) {
            if( entry.getMACAddress().equals( addr ) ) {
                return entry;
                }
            }

        return null;
    }

}
