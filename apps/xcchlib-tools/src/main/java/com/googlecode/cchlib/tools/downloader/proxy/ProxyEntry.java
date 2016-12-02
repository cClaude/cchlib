package com.googlecode.cchlib.tools.downloader.proxy;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxyEntry
{
    private final Proxy proxy;
    private final String displayString;

    public ProxyEntry( final String hostname, final int port )
    {
        this( new Proxy( Proxy.Type.HTTP, new InetSocketAddress( hostname, port ) ) );
    }

    public ProxyEntry( final Proxy proxy )
    {
        this( proxy, proxy.toString() );
    }

    public ProxyEntry( final Proxy proxy, final String displayString )
    {
        this.proxy = proxy;
        this.displayString = displayString;
    }

    @Override
    public String toString()
    {
        return this.displayString;
    }

    public Proxy getProxy()
    {
        return this.proxy;
    }
}
