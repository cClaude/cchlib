package com.googlecode.cchlib.net.download;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
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
import java.util.Map.Entry;

import javax.swing.event.EventListenerList;
import org.apache.log4j.Logger;

/**
 * TODOC
 *
 * @since 4.1.5
 */
public class URLCache implements Closeable
{
    private static final transient Logger logger = Logger.getLogger( URLCache.class );

    /** The listeners waiting for object changes. */
    protected EventListenerList listenerList = new EventListenerList();
    private final CacheContent _cache;
    private final File cacheRootDirFile;
    private File __cacheFile__;
    private boolean autostore;
    private int autostoreThreshold = 50;
    private int modificationCount = 0;

    /**
     * Create a new URLCache using a default cache {@link File} stored
     * in cacheRootDirFile
     *
     * @param cacheRootDirFile Root {@link File} directory.
     * @throws NullPointerException if cacheRootDirFile is null
     * @throws IOException if cacheRootDirFile is not a valid directory
     */
    public URLCache( final File cacheRootDirFile ) throws IOException
    {
        this.cacheRootDirFile = cacheRootDirFile;
        this._cache           = new CacheContent();
        this.autostore        = false;

        if( cacheRootDirFile == null ) {
            throw new NullPointerException( "cacheRootDirFile is null" );
            }

        if( !cacheRootDirFile.isDirectory() ) {
            // Build directory folder if needed.
            cacheRootDirFile.mkdirs();

            if( !cacheRootDirFile.isDirectory() ) {
                throw new IOException( "Can't create directory: " + cacheRootDirFile );
                }
            }
    }

    /**
     * Create a new URLCache
     *
     * @param cacheRootDirFile Root {@link File} directory.
     * @param cacheFilename    Cache index filename
     * @throws NullPointerException if cacheFilename is null
     * @throws NullPointerException if cacheRootDirFile is null
     * @throws IOException if cacheRootDirFile is not a valid directory
     */
    public URLCache( final File cacheRootDirFile, final String cacheFilename )
        throws IOException
    {
        this( cacheRootDirFile );

        setCacheFilename( cacheFilename );
    }

    /**
     * Create a new URLCache
     *
     * @param cacheRootDirFile Root {@link File} directory.
     * @param cacheFile        Cache index {@link File}
     * @throws NullPointerException if cacheFilename is null
     * @throws NullPointerException if cacheRootDirFile is null
     * @throws IOException if cacheRootDirFile is not a valid directory
     */
    public URLCache( final File cacheRootDirFile, final File cacheFile )
            throws IOException
    {
        this( cacheRootDirFile );

        setCacheFile( cacheFile );
    }

    /**
     * Returns temporary directory {@link File} for this cache.
     * @return temporary directory {@link File} for this cache.
     */
    public File getTempDirectoryFile()
    {
        return this.cacheRootDirFile;
    }

    /**
     * Check if an {@link URL} is in cache, but don't care about
     * data.
     *
     * @param url {@link URL} to check
     * @return true if {@link URL} is in cache, false otherwise
     */
    public boolean isInCacheIndex( final URL url )
    {
        return get( url ) != null;
    }

    /**
     * Check if an {@link URL} is in cache and if file is
     * already in cache directory.
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
    public void add( final URL url, final String contentHashCode, final String filename )
    {
    	synchronized( _cache ) {
    		_cache.put( url, new DefaultURLCacheEntry( contentHashCode, filename ) );

            this.modificationCount++;
    		}
    	
        autoStore();
    }

    /**
     * Retrieve {@link URL} in cache
     *
     * @param url {@link URL} to retrieve
     * @return {@link URLCacheEntry} for giving url if in cache, null otherwise
     */
    public URLDataCacheEntry get( final URL url )
    {
    	synchronized( _cache ) {
            return _cache.get( url );
    		}
    }

    /**
     * 
     * @param hashCodeString
     * @return
     */
    public URL findURL( final String hashCodeString )
    {
    	synchronized( _cache ) {
            // TODO: build direct access ???
            for( Entry<URL,URLDataCacheEntry> entry : _cache ) {
                if( hashCodeString.equals( entry.getValue().getContentHashCode() ) ) {
                    return entry.getKey();
                    }
                }
            
            return null;
    		}
    }

    /**
     * Returns cache size
     * @return cache size
     */
    //synchronized
    public int size()
    {
    	synchronized( _cache ) {
            return _cache.size();
    		}
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

    /* *
     * Clear cache in memory, file cache will be updated
     * according to auto-store status
     * /
    synchronized
    public void clear()
    {
        cache.clear();

        autoStore();
    }*/

    /**
     * Set cacheFile for this cache
     * @param cacheFile {@link File} to set
     * @throws NullPointerException if cacheFile is null
     */
    protected void setCacheFile( final File cacheFile )
    {
        if( cacheFile == null ) {
            throw new NullPointerException( "cacheFile is null" );
            }

        this.__cacheFile__ = cacheFile;
    }

    /**
     * Set cacheFile for this cache
     * @param cacheFilename Cache filename to set
     * @throws NullPointerException if cacheFilename is null
     */
    protected void setCacheFilename( String cacheFilename )
    {
        if( cacheFilename == null ) {
            throw new NullPointerException( "cacheFilename is null" );
            }

        setCacheFile( new File( cacheRootDirFile, cacheFilename ) ) ;
    }

    /**
     * Returns cache {@link File}
     * @return cacheFile for this cache
     */
    public File getCacheFile()
    {
        if( this.__cacheFile__ == null ) {
        	// provide a default cache file if not set.
            this.__cacheFile__ = new File( this.cacheRootDirFile, ".cache-index" );
            }

        return this.__cacheFile__;
    }

    /**
     * Returns cache backup {@link File} based on {@link #getCacheFile()}
     * @return cache backup {@link File}
     */
    protected File getBackupCacheFile()
    {
        final File cacheFile = getCacheFile();

        return new File( cacheFile .getParentFile(), cacheFile.getName() + "~" );
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
     * Load cache file
     *
     * @throws FileNotFoundException if cache does not exist
     * @throws IOException if any I/O error occur
     * @see #getCacheFile()
     */
    //synchronized
    public void load() throws FileNotFoundException, IOException
    {
    	synchronized( _cache ) {
        	try {
            	load( getCacheFile(), _cache );
        		}
        	catch( IOException retry ) {
        		logger.warn( "Can load: " +  getCacheFile() + " trying " +  getBackupCacheFile(), retry );
        		
            	load( getBackupCacheFile(), _cache );
        		}
    		}
    }

    private static void load( final File cacheFile, final CacheContent cache )
    	throws FileNotFoundException, IOException
    {
        BufferedReader r = null;

        try {
            r = new BufferedReader( new FileReader( cacheFile ) );

            for(;;) {
                String hashCode = r.readLine();
                if( hashCode == null ) {
                    break; // EOF
                    }
                else if( hashCode.isEmpty() ) {
                    hashCode = null; // No hash code
                    }
                URL    url      = new URL( r.readLine() );
                Date   date     = new Date( Long.parseLong( r.readLine() ) );
                String filename = r.readLine();

                cache.put( url, new DefaultURLCacheEntry( hashCode, date, filename ) );
                }
            }
        finally {
            if( r != null ) {
                r.close();
                }
            }
    }
    /**
     * Save cache file
     *
     * @throws IOException if any I/O error occur
     * @see #getCacheFile()
     */
    //synchronized
    public void store() throws IOException
    {
        final File cacheFile 	= getCacheFile();
        final File backupFile	= getBackupCacheFile();

        if( logger.isTraceEnabled() ) {
            logger .trace( "Try to store: " + cacheFile );
            }

        if( cacheFile.isFile() ) {

            // Delete previous version of backup file.
            backupFile.delete();

            // Rename previous version of cache file to backup file.
            boolean b = cacheFile.renameTo( backupFile );

            if( logger.isTraceEnabled() ) {
                logger .trace( "Rename cache file from [" + cacheFile + "] to [" + backupFile + "] : result=" + b );
                }

            if( ! b ) {
                logger.warn( "Can't rename cache file to: " + backupFile );
                }
            }

        // store cache
    	synchronized( _cache ) {
    		storeSimpleTxt( cacheFile, _cache );
    		}
        
        // Cache stored successfully
        this.modificationCount = 0;

        if( cacheFile.length() < backupFile.length() ) {
        	// Loose data in cache !
        	logger.warn( "Loose data append previous file" );
        
        	CacheContent tmpCache = new CacheContent();
        	
			load( backupFile, tmpCache );
			
	    	synchronized( _cache ) {
				// Add in tmpCache current values 
	            for( Entry<URL,URLDataCacheEntry> entry : _cache ) {
	            	tmpCache.put( entry.getKey(), entry.getValue() );
	            	}
	            
	            // Update cache using tmpCache
	            for( Entry<URL,URLDataCacheEntry> entry : tmpCache ) {
	            	_cache.put( entry.getKey(), entry.getValue() );
	            	}
	            
	            tmpCache.clear();
	            storeSimpleTxt( cacheFile, _cache );
	    		}
        	}
        
        if( logger.isTraceEnabled() ) {
            logger .trace( "Cache stored successfully " );
            }
    }

    private static void storeSimpleTxt( File cacheFile, CacheContent cache ) 
    	throws IOException
    {
        // store cache using simple text file.
        Writer w = new BufferedWriter( new FileWriter( cacheFile ) );

        for( Entry<URL,URLDataCacheEntry> entry : cache ) {
            final String contentHashCode = entry.getValue().getContentHashCode();

            if( contentHashCode != null ) {
                w.append( contentHashCode.trim() ).append( '\n' );
                }
            else {
                w.append( '\n' );
                }
            w.append( entry.getKey().toExternalForm() ).append( '\n' );
            w.append( Long.toString( entry.getValue().getDate().getTime() ) ).append( '\n' );
            w.append( entry.getValue().getRelativeFilename() ).append( '\n' );
            }
        w.flush();
        w.close();
    }
    
    private void autoStore()
    {
        if( this.autostore ) {
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
    private final class CacheContent implements Serializable, Iterable<Map.Entry<URL,URLDataCacheEntry>>
    {
        private static final long serialVersionUID = 3L;
        private HashMap<URL,URLDataCacheEntry> cc = new HashMap<URL,URLDataCacheEntry>();
        public void put( URL key, URLDataCacheEntry value )
        {
            cc.put( key, value );
        }
//        public void put( final URLFullCacheEntry entry )
//        {
//            put( entry.getURL(), entry );
//        }
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
        public Iterator<Entry<URL,URLDataCacheEntry>> iterator()
        {
            return cc.entrySet().iterator();
        }
//        public Iterable<URLFullCacheEntry> getURLFullCacheEntries()
//        {
//        	return new Iterable<URLFullCacheEntry>()
//        	{
//                @Override
//                public Iterator<URLFullCacheEntry> iterator()
//                {
//                    return new Iterator<URLFullCacheEntry>()
//                    {
//                        final Iterator<Map.Entry<URL,URLDataCacheEntry>> parent = cc.entrySet().iterator();
//
//                        @Override
//                        public boolean hasNext()
//                        {
//                            return parent.hasNext();
//                        }
//                        @Override
//                        public URLFullCacheEntry next()
//                        {
//                            final Map.Entry<URL,URLDataCacheEntry> entry = parent.next();
//                            final URL               url   =  entry.getKey();
//                            final URLDataCacheEntry value = entry.getValue();
//
//                            return new URLFullCacheEntry()
//                            {
//                                @Override
//                                public URL getURL()
//                                {
//                                    return url;
//                                }
//                                @Override
//                                public Date getDate()
//                                {
//                                    return value.getDate();
//                                }
//                                @Override
//                                public String getRelativeFilename()
//                                {
//                                    return value.getRelativeFilename();
//                                }
//                                @Override
//                                public String getContentHashCode()
//                                {
//                                    return value.getContentHashCode();
//                                }
//                            };
//                        }
//                        @Override
//                        public void remove()
//                        {
//                            throw new UnsupportedOperationException();
//                        }
//                    };
//                }
//        		
//        	};
//        }
    }

    /**
     * Adds a {@link URLCacheListener} to the
     * {@link URLCache}'s listener list.
     *
     * @param l the {@link URLCacheListener} to add
     */
    public void addURLCacheListener( URLCacheListener l )
    {
        listenerList.add( URLCacheListener.class, l );
    }

    /**
     * Removes a {@link URLCacheListener} from the
     *  {@link URLCache}'s listener list.
     *
     * @param l the {@link URLCacheListener} to remove
     */
    public void removeURLCacheListener( URLCacheListener l )
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

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "URLCache [cacheRootDirFile=" );
        builder.append( cacheRootDirFile );
        builder.append( ", cacheFile=" );
        builder.append( getCacheFile() );
        builder.append( ", autostore=" );
        builder.append( autostore );
        builder.append( ", autostoreThreshold=" );
        builder.append( autostoreThreshold );
        builder.append( ", modificationCount=" );
        builder.append( modificationCount );
        builder.append( ", cache size=" );
        builder.append( _cache.size() );

//        builder.append( ", cache content URLs=[\n" );
//        for( URLFullCacheEntry u : cache ) {
//            builder.append( u.getURL() );
//            builder.append( "\n" );
//            }
//        builder.append( "]" );

        builder.append( "]" );

        return builder.toString();
    }

    @Override
    public void close() throws IOException
    {
        if( this.getCacheFile() != null ) {
            store();
            }
    }

    @Override
    protected void finalize() throws Throwable
    {
        close();

        super.finalize();
    }
}
