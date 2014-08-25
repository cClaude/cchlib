package com.googlecode.cchlib.dhcp.logger;

import com.googlecode.cchlib.dhcp.DHCPMessage;

public interface DHCPLogger {

    void println( String string, DHCPMessage dhcpMessageReceived );
    void println( String string );

}
