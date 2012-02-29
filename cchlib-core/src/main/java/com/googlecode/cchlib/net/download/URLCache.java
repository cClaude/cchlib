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
 * TODOC
 *
 * @since 4.1.5
 */
public class URLCache implements Serializable
{
    private static final long serialVersionUID = 2L;
    private CacheContent cache;
    private File cacheFile;
    private boolean autostore;
    private int autostoreThreshold = 50;
    private int modificationCount = 0;

    /**
     * Create a new URLCache
     */
    public URLCache()
    {
        this.cache = new CacheContent();
        this.autostore = false;
    }

    /**
     * Create a new URLCache using giving cacheFile
     *
     * @param cacheFile {@link File} to use has cache
     * @throws IllegalStateException if cache file exist but can not be read
     */
    public URLCache( final File cacheFile )
    {
        setCacheFile( cacheFile );

        try {
            load();
            }
        catch( FileNotFoundException e ) {
            this.cache = new CacheContent();
            }
        catch( Exception e ) {
            throw new IllegalStateException( e );
            }

        this.autostore = true;
    }

    /**
     * Check if an {@link URL} is in cache
     *
     * @param url {@link URL} to check
     * @return true if {@link URL} is in cache and if
     *   cached file exist in directory cache,
     *   false otherwise
     */
    public boolean isInCache( final URL url )
    {
        Entry entry = get( url );

        if( entry != null ) {
            // TODO: allow to define a relative filename
            // (add set/get cacheHomeDirectory File)
            File f = new File( entry.getFilename() );

            return f.isFile();
            }
        return false;
    }

    /**
     * Add a new ({@link URL},filename) couple in cache
     *
     * @param url       {@link URL} for this filename
     * @param filename  Local filename
     */
    synchronized
    public void add( final URL url, final String filename )
    {
        cache.put( url, new EntryImpl( url, filename ) );

        this.modificationCount++;
        autoStore();
    }

    /**
     * Retrieve {@link URL} in cache
     *
     * @param url {@link URL} to retrieve
     * @return {@link Entry} for giving url if in cache, null otherwise
     */
    synchronized
    public Entry get( final URL url )
    {
        return cache.get( url );
    }

    /**
     * Returns cache size
     * @return cache size
     */
    synchronized
    public int size()
    {
        return cache.size();
    }

    /**
     * Returns filename for giving {@link URL}
     *
     * @param url {@link URL} to retrieve
     * @return filename for giving {@link URL},
     *         or null if URL is not in cache
     */
    public String getFilename( final URL url )
    {
        Entry entry = get( url );

        if( entry != null ) {
            return entry.getFilename();
            }
        else {
            return null;
            }
    }

    /**
     * Clear cache in memory, file cache will be updated
     * according to auto-store status
     */
    synchronized
    public void clear()
    {
        cache.clear();

        autoStore();
    }

    /**
     * Set cacheFile for this cache
     * @param cacheFile {@link File} to set
     */
    public void setCacheFile( final File cacheFile )
    {
        this.cacheFile = cacheFile;
    }

    /**
     * Returns cache {@link File}
     * @return cacheFile for this cache
     */
    public File getCacheFile()
    {
        return this.cacheFile;
    }

    /**
     * Enable or disable auto storage off cache content.
     * @param enable if true enable auto storage
     */
    public void setAutoStorage( final boolean enable )
    {
        this.autostore = enable;
    }

    /**
     * Returns true if auto-storage is enable, false otherwise
     * @return true if auto-storage is enable, false otherwise
     */
    public boolean isAutoStorage()
    {
        return this.autostore;
    }

    /**
     * Set threshold for auto storage.
     *
     * @param threshold Count of modification between each save of cache file
     */
    public void setAutoStorageThreshold( final int threshold )
    {
        this.autostoreThreshold = threshold;
    }

    /**
     * Returns AutoStorage Threshold value
     * @return AutoStorage Threshold value
     */
    public int getAutoStorageThreshold()
    {
        return this.autostoreThreshold;
    }

    /**
     * TODOC
     *
     * @throws FileNotFoundException if cache does not exist
     * @throws IOException if any I/O error occur
     * @throws ClassNotFoundException
     */
    synchronized
    public void load() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        this.cache = SerializableHelper.loadObject( cacheFile, cache.getClass() );
    }

    /**
     * TODOC
     *
     * @throws IOException if any I/O error occur
     */
    synchronized
    public void store() throws IOException
    {
        SerializableHelper.toFile( cache, cacheFile );

        this.modificationCount = 0;
    }

    private void autoStore()
    {
        if( this.autostore && this.cacheFile != null ) {
            if( this.modificationCount > this.autostoreThreshold ) {
                try {
                    store();
                    }
                catch( IOException e ) {
                    // FIXME: Send a notification ?
                    e.printStackTrace();
                }
            }
        }
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
            }
        else if( !date.equals( other.date ) ) {
            return false;
            }
        if( filename == null ) {
            if( other.filename != null ) {
                return false;
                }
            }
        else if( !filename.equals( other.filename ) ) {
            return false;
            }
        if( url == null ) {
            if( other.url != null ) {
                return false;
                }
            }
        else if( !url.equals( other.url ) ) {
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
