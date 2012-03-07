package com.googlecode.cchlib.net.download;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import javax.swing.event.EventListenerList;
import cx.ath.choisnet.io.SerializableHelper;

/**
 * TODOC
 *
 * @since 4.1.5
 */
public class URLCache implements Serializable
{
    /** The listeners waiting for object changes. */
    protected EventListenerList listenerList = new EventListenerList();
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
        URLCacheEntry entry = get( url );

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
        cache.put( url, new DefaultURLCacheEntry( url, filename ) );

        this.modificationCount++;
        autoStore();
    }

    /**
     * Retrieve {@link URL} in cache
     *
     * @param url {@link URL} to retrieve
     * @return {@link URLCacheEntry} for giving url if in cache, null otherwise
     */
    synchronized
    public URLCacheEntry get( final URL url )
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
        URLCacheEntry entry = get( url );

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
                    fireIOException( e );
                    }
                }
            }
    }

    // Workaround for generic warning...
    private class CacheContent implements Serializable
    {
        private static final long serialVersionUID = 3L;
        private HashMap<URL,URLCacheEntry> cc = new HashMap<URL,URLCacheEntry>();
        public void put( URL key, URLCacheEntry value )
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
        public URLCacheEntry get( URL url )
        {
            return cc.get( url );
        }
    }

    /**
     * Adds a {@link URLCacheListener} to the
     * {@link URLCache}'s listener list.
     *
     * @param l the {@link URLCacheListener} to add
     */
    public void addZipListener( URLCacheListener l )
    {
        listenerList.add( URLCacheListener.class, l );
    }

    /**
     * Removes a {@link URLCacheListener} from the
     *  {@link URLCache}'s listener list.
     *
     * @param l the {@link URLCacheListener} to remove
     */
    public void removeZipListener( URLCacheListener l )
    {
        listenerList.remove( URLCacheListener.class, l );
    }

    /**
     * Runs each {@link URLCacheListener}'s
     * {@link URLCacheListener#errorHandler(IOException)}
     * method.
     */
    protected void fireIOException(
            final IOException ioe
        )
    {
        Object[] listeners = listenerList.getListenerList();

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if( listeners[i] == URLCacheListener.class ) {
                ((URLCacheListener)listeners[i + 1]).errorHandler( ioe );
                }
            }
    }

}
