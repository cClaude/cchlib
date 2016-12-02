package com.googlecode.cchlib.tools.downloader.common;

import java.io.Serializable;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketAddress;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SerializableProxy implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Nullable
    private Type          type;
    @Nullable
    private SocketAddress address;

    @Nullable
    private transient Proxy proxy;

    public SerializableProxy(
        @Nullable final Type          type,
        @Nullable final SocketAddress address
        )
    {
        this.type    = type;
        this.address = address;
    }

    public SerializableProxy( @Nullable final Proxy proxy )
    {
        if( (proxy != null) && (proxy != Proxy.NO_PROXY) ) {
            this.proxy   = proxy;
            this.type    = proxy.type();
            this.address = proxy.address();
        }
    }

    public void setType( @Nullable final Type type )
    {
        this.proxy = null; // Invalidate cached proxy
        this.type  = type;
    }

    @Nullable
    public Type getType()
    {
        return this.type;
    }

    public void setAddress( @Nullable final SocketAddress address )
    {
        this.proxy   = null; // Invalidate cached proxy
        this.address = address;
    }

    @Nullable
    public SocketAddress getAddress()
    {
        return this.address;
    }

    @JsonIgnore
    @Nonnull
    public Proxy getProxy()
    {
        if( this.proxy == null ) {
            // This to create a proxy (transient)
            if( (this.type != null) && (this.address != null) ) {
                this.proxy = new Proxy( this.type, this.address );
            } else {
                return Proxy.NO_PROXY;
            }
        }

        return this.proxy;
    }

    @Nonnull
    public static SerializableProxy newSerializableProxy( final Proxy proxy )
    {
        return new SerializableProxy( proxy );
    }

    @Nullable
    public static SerializableProxy createSerializableProxyIfRequired( final Proxy proxy )
    {
        if( (proxy == null) || (proxy == Proxy.NO_PROXY) ) {
            return null;
        } else {
            return newSerializableProxy( proxy );
        }
    }
}
