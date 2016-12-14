package com.googlecode.cchlib.dhcp.logger;

import com.googlecode.cchlib.dhcp.DHCPMessage;

public interface DHCPLogger {

    void println( String string );
    void println( String string, DHCPMessage dhcpMessageReceived );
    void printStackTrace( Exception cause );
}
