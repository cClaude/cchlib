package com.googlecode.cchlib.net.download;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import cx.ath.choisnet.io.SerializableHelper;

/**
 * TODO: Doc!
 * @since 4.1.5
 */
public class URLCache implements Serializable
{
    private static final long serialVersionUID = 2L;
    private CacheContent cache;
    private File cacheFile;

    /**
     * TODOC
     */
    public URLCache()
    {
        cache = new CacheContent();
    }

    /**
     * TODOC
     *
     * @param cacheFile
     */
    public URLCache( final File cacheFile )
    {
        setCacheFile( cacheFile );
        try {
            load();
            }
        catch( Exception e ) {
            cache = new CacheContent();
            }
    }

    /**
     * TODOC
     *
     * @param url
     * @return
     */
    public boolean isInCache( final URL url )
    {
        return cache.contains( url );
    }

    /**
     * TODOC
     *
     * @param url
     * @param filename
     */
    synchronized
    public void add( final URL url, final String filename )
    {
        cache.put( url, new EntryImpl( url, filename ) );
    }

    /**
     * TODOC
     *
     * @param url
     * @return
     */
    synchronized
    public Entry get( final URL url )
    {
        return cache.get( url );
    }

    /**
     * TODOC
     *
     * @return
     */
    synchronized
    public int size()
    {
        return cache.size();
    }

    /**
     * TODOC
     *
     * @param url
     * @return
     */
    public String getFilename( final URL url )
    {
        return get( url ).getFilename();
    }

    /**
     * TODOC
     */
    synchronized
    public void clear()
    {
        cache.clear();
    }

    /**
     * TODOC
     *
     * @param cacheFile
     */
    public void setCacheFile( File cacheFile )
    {
        this.cacheFile = cacheFile;
    }

    /**
     * TODOC
     *
     * @return
     */
    public File getCacheFile()
    {
        return this.cacheFile;
    }

    /**
     * TODOC
     *
     * @throws FileNotFoundException if cache does not exist
     * @throws IOException
     * @throws ClassNotFoundException
     */
    synchronized
    public void load() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        cache = SerializableHelper.loadObject( cacheFile, cache.getClass() );
    }

    /**
     * TODOC
     *
     * @throws IOException
     */
    synchronized
    public void store() throws IOException
    {
        SerializableHelper.toFile( cache, cacheFile );
    }

    // Workaround for generic warning...
    private class CacheContent implements Serializable
    {
        private static final long serialVersionUID = 3L;
        private HashMap<URL,Entry> cc = new HashMap<URL,Entry>();
        public void put( URL key, Entry value )
        {
            cc.put( key, value );
        }
        public int size()
        {
            return cc.size();
        }
        public boolean contains( URL url )
        {
            return cc.containsKey( url );
        }
        public void clear()
        {
            cc.clear();
        }
        public Entry get( URL url )
        {
            return cc.get( url );
        }
    }

    /**
     * Cache entry
     */
    public interface Entry extends Serializable
    {
        /**
         * Returns the {@link URL} of this entry
         * @return the {@link URL} of this entry
         */
        public URL getURL();
        /**
         * Returns the {@link Date} for this entry
         * @return the {@link Date} for this entry
         */
        public Date getDate();
        /**
         * Returns the filename of this entry
         * @return the filename of this entry
         */
        public String getFilename();
    }
}

final class EntryImpl implements URLCache.Entry
{
    private static final long serialVersionUID = 1L;
    private URL     url;
    private Date    date;
    private String  filename;

    public EntryImpl( final URL url, final String filename )
    {
        this( url, null, filename );
    }

    public EntryImpl( final URL url, final Date date, final String filename )
    {
        this.url = url;
        this.date = date == null ? new Date() : date;
        this.filename = filename;
    }

    @Override
    public URL getURL()
    {
        return url;
    }
//    public void setUrl( URL url )
//    {
//        this.url = url;
//    }
    @Override
    public Date getDate()
    {
        return date;
    }
//    public void setDate( Date date )
//    {
//        this.date = date;
//    }
    @Override
    public String getFilename()
    {
        return filename;
    }
//    public void setFilename( String filename )
//    {
//        this.filename = filename;
//    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result
                + ((filename == null) ? 0 : filename.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object obj )
    {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( !(obj instanceof EntryImpl) ) {
            return false;
        }
        EntryImpl other = (EntryImpl)obj;
        if( date == null ) {
            if( other.date != null ) {
                return false;
            }
        } else if( !date.equals( other.date ) ) {
            return false;
        }
        if( filename == null ) {
            if( other.filename != null ) {
                return false;
            }
        } else if( !filename.equals( other.filename ) ) {
            return false;
        }
        if( url == null ) {
            if( other.url != null ) {
                return false;
            }
        } else if( !url.equals( other.url ) ) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "EntryImpl [url=" + url + ", date=" + date + ", filename="
                + filename + "]";
    }
}
