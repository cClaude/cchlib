package com.googlecode.cchlib.tools.downloader;

import java.net.InetSocketAddress;
import java.net.Proxy;

class ProxyEntry
{
    private Proxy proxy;
    private String displayString;

    public ProxyEntry( String hostname, int port )
    {
        this( new Proxy( Proxy.Type.HTTP, new InetSocketAddress( hostname, port ) ) );
    }

    public ProxyEntry( final Proxy proxy )
    {
        this( proxy, proxy.toString() );
    }

    public ProxyEntry( Proxy proxy, String displayString )
    {
        this.proxy = proxy;
        this.displayString = displayString;
    }

    @Override
    public String toString()
    {
        return displayString;
    }

    public Proxy getProxy()
    {
        return proxy;
    }
}