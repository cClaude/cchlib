package com.googlecode.cchlib.tools.downloader.common;

import java.io.Serializable;
import java.net.Proxy;
import javax.annotation.Nonnegative;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DownloaderDataImpl
    implements DownloaderData, Serializable
{
    private static final long serialVersionUID = 1L;

    private String            cacheRelativeDirectoryCacheName;
    @Nonnegative private int  maxPageCount;
    @Nonnegative private int  numberOfPicturesByPage;
    @Nonnegative private int  pageCount;
    private SerializableProxy serializableProxy;
    private String            siteName;

    @Nonnegative
    private static int getNonnegative( final int number )
    {
        if( number > 0 ) {
            return number;
        } else {
            return 0;
        }
    }

    @Override
    public String getCacheRelativeDirectoryCacheName()
    {
        return this.cacheRelativeDirectoryCacheName;
    }

    @Override
    @Nonnegative
    public int getMaxPageCount()
    {
        return this.maxPageCount;
    }

    @Override
    @Nonnegative
    public int getNumberOfPicturesByPage()
    {
        return this.numberOfPicturesByPage;
    }

    @Override
    @Nonnegative
    public int getPageCount()
    {
        return this.pageCount;
    }

    @Override
    @JsonIgnore
    public final Proxy getProxy()
    {
        if( this.serializableProxy != null ) {
            return this.serializableProxy.getProxy();
        } else {
            return null;
        }
    }

    // Serialization Swing and JSON
    public SerializableProxy getSerializableProxy()
    {
        return this.serializableProxy;
    }

    @Override
    public String getSiteName()
    {
        return this.siteName;
    }

    public void setCacheRelativeDirectoryCacheName( final String cacheRelativeDirectoryCacheName )
    {
        this.cacheRelativeDirectoryCacheName = cacheRelativeDirectoryCacheName;
    }

    public void setMaxPageCount( @Nonnegative final int maxPageCount )
    {
        this.maxPageCount = getNonnegative( maxPageCount );
    }

    public void setNumberOfPicturesByPage( @Nonnegative final int numberOfPicturesByPage )
    {
        this.numberOfPicturesByPage = getNonnegative( numberOfPicturesByPage );
    }

    @Override
    public void setPageCount( @Nonnegative final int pageCount )
    {
        this.pageCount = getNonnegative( pageCount );
    }

    @Override
    @JsonIgnore
    public void setProxy( final Proxy proxy )
    {
        setSerializableProxy(
                SerializableProxy.newSerializableProxy( proxy )
                );
    }

    // Serialization Swing and JSON
    public void setSerializableProxy( final SerializableProxy serializableProxy )
    {
        this.serializableProxy = serializableProxy;
    }

    public void setSiteName( final String siteName )
    {
        this.siteName = siteName;
    }
}
