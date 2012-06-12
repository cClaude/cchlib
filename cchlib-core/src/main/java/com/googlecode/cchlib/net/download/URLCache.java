package com.googlecode.cchlib.net.download;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.event.EventListenerList;

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
    private final File cacheRootDirFile;
    private File cacheFile;
    private boolean autostore;
    private int autostoreThreshold = 50;
    private int modificationCount = 0;

    /**
     * Create a new URLCache
     */
    public URLCache(  final File cacheRootDirFile )
    {
        this.cacheRootDirFile = cacheRootDirFile;
        this.cache            = new CacheContent();
        this.autostore        = false;
    }

//    /**
//     * Create a new URLCache using giving cacheFile
//     *
//     * @param cacheFile {@link File} to use has cache
//     * @throws IllegalStateException if cache file exist but can not be read
//     */
//    public URLCache( final File cacheRootDirFile, final File cacheFile )
//    {
//        this.cacheRootDirFile = cacheRootDirFile;
//
//        setCacheFile( cacheFile );
//
//        try {
//            load();
//            }
//        catch( FileNotFoundException e ) {
//            this.cache = new CacheContent();
//            }
//        catch( Exception e ) {
//            throw new IllegalStateException( e );
//            }
//
//        this.autostore = true;
//    }

    /**
     * Returns temporary directory {@link File} for this cache.
     * @return temporary directory {@link File} for this cache.
     */
    public File getTempDirectoryFile()
    {
        return this.cacheRootDirFile;
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
        URLDataCacheEntry entry = get( url );

        if( entry != null ) {
            // TODO: allow to define a relative filename
            // (add set/get cacheHomeDirectory File)
            File f = new File( cacheRootDirFile, entry.getRelativeFilename() );

            return f.isFile();
            }
        return false;
    }

    /**
     * Add a new ({@link URL},filename) couple in cache
     *
     * @param url             {@link URL} for this filename
     * @param contentHashCode URL content Hash code, or null
     * @param filename        Local filename
     */
    synchronized
    public void add( final URL url, final String contentHashCode, final String filename )
    {
        cache.put( url, new DefaultURLCacheEntry( contentHashCode, filename ) );

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
    public URLDataCacheEntry get( final URL url )
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
    public String getRelativeFilename( final URL url )
    {
        URLDataCacheEntry entry = get( url );

        if( entry != null ) {
            return entry.getRelativeFilename();
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
        //this.cache = SerializableHelper.loadObject( getCacheFile(), cache.getClass() );

        //CacheContent cache2 = new CacheContent();
        BufferedReader r = new BufferedReader( new FileReader( getCacheFile() ) );

        for(;;) {
            String hashCode = r.readLine(); // ignored FIXME
            if( hashCode == null ) {
                break; // EOF
                }
            else if( hashCode.isEmpty() ) {
                hashCode = null;
                }
            URL    url      = new URL( r.readLine() );
            Date   date     = new Date( Long.parseLong( r.readLine() ) );
            String filename = r.readLine();

            cache.put( url, new DefaultURLCacheEntry( hashCode, date, filename ) );
            }
        r.close();

        //Logger.getLogger( this.getClass() ).info( "cache " + cache.size() );
        //Logger.getLogger( this.getClass() ).info( "cache2 " + cache2.size() );
    }

    /**
     * TODOC
     *
     * @throws IOException if any I/O error occur
     */
    synchronized
    public void store() throws IOException
    {
        //SerializableHelper.toFile( cache, getCacheFile() );

        // store using simple text file.
        Writer w = new BufferedWriter( new FileWriter( getCacheFile() ) );

        for( URLFullCacheEntry entry : cache ) {
            final String contentHashCode = entry.getContentHashCode();

            if( contentHashCode != null ) {
                w.append( contentHashCode.trim() ).append( '\n' );
                }
            else {
                w.append( '\n' );
                }
            w.append( entry.getURL().toExternalForm() ).append( '\n' );
            w.append( Long.toString( entry.getDate().getTime() ) ).append( '\n' );
            w.append( entry.getRelativeFilename() ).append( '\n' );
            }
        w.flush();
        w.close();

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

    // Workaround for generic warning when restore object using standard serialization
    private class CacheContent implements Serializable, Iterable<URLFullCacheEntry>
    {
        private static final long serialVersionUID = 3L;
        private HashMap<URL,URLDataCacheEntry> cc = new HashMap<URL,URLDataCacheEntry>();
        public void put( URL key, URLDataCacheEntry value )
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
        public URLDataCacheEntry get( URL url )
        {
            return cc.get( url );
        }
        @Override
        public Iterator<URLFullCacheEntry> iterator()
        {
            return new Iterator<URLFullCacheEntry>()
            {
                final Iterator<Map.Entry<URL,URLDataCacheEntry>> parent = cc.entrySet().iterator();

                @Override
                public boolean hasNext()
                {
                    return parent.hasNext();
                }
                @Override
                public URLFullCacheEntry next()
                {
                    final Map.Entry<URL,URLDataCacheEntry> entry = parent.next();
                    final URL               url   =  entry.getKey();
                    final URLDataCacheEntry value = entry.getValue();

                    return new URLFullCacheEntry()
                    {
                        @Override
                        public URL getURL()
                        {
                            return url;
                        }
                        @Override
                        public Date getDate()
                        {
                            return value.getDate();
                        }
                        @Override
                        public String getRelativeFilename()
                        {
                            return value.getRelativeFilename();
                        }
                        @Override
                        public String getContentHashCode()
                        {
                            return value.getContentHashCode();
                        }
                    };
                }
                @Override
                public void remove()
                {
                    throw new UnsupportedOperationException();
                }
            };
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
