package com.googlecode.cchlib.dhcp.client;

import java.net.SocketException;
import com.googlecode.cchlib.dhcp.DHCPSocket;

@FunctionalInterface
public interface DHCPSocketBuilder
{
    DHCPSocket newDHCPSocket() throws SocketException;
}
