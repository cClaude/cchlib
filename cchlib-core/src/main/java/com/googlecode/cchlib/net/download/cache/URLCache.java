package com.googlecode.cchlib.net.download.cache;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Map.Entry;
import javax.swing.event.EventListenerList;
import org.apache.log4j.Logger;


/**
 * TODOC
 *
 * @since 4.1.7
 */
public class URLCache implements Closeable
{
    private static final transient Logger logger = Logger.getLogger( URLCache.class );

    /** The listeners waiting for object changes. */
    protected EventListenerList listenerList = new EventListenerList();
    /** Cache all access should be synchronized on this object */
    private final CacheContent _cache_;
    private final File cacheRootDirFile;
    /**
     * Cache file, use {@link #getCacheFile()} and {@link #setCacheFile(File)}
     * to access
     */
    private File __cacheFile__;
    private boolean autostore;
    private int autostoreThreshold = 50;
    private int modificationCount = 0;
    private URLCachePersistenceManager persistenceManager = new SimpleTextPersistenceManagerV1();

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
        this._cache_          = new CacheContent();
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
     * @param date
     * @param contentHashCode URL content Hash code, or null
     * @param filename        Local filename
     */
    public void add(
        final URL    url,
        final Date   date,
        final String contentHashCode,
        final String filename
        )
    {
        synchronized( _cache_ ) {
            _cache_.put( url, new DefaultURLCacheEntry( date, contentHashCode, filename ) );

            this.modificationCount++;
            }

        autoStore();
    }

    /**
     * Retrieve {@link URL} in cache
     *
     * @param url {@link URL} to retrieve
     * @return {@link URLDataCacheEntry} for giving url if in cache, null otherwise
     */
    public URLDataCacheEntry get( final URL url )
    {
    	synchronized( _cache_ ) {
            return _cache_.get( url );
    		}
    }

    /**
     * Returns {@link URL} for this hashcode if exist, null otherwise
     * @param hashCode
     * @return {@link URL} for this hashcode if exist, null otherwise
     */
    public URL findURL( final String hashCode )
    {
        synchronized( _cache_ ) {
            return _cache_.get( hashCode );
            }
    }

    /**
     * Returns cache size
     * @return cache size
     */
    public int size()
    {
        synchronized( _cache_ ) {
            return _cache_.size();
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
    protected void setCacheFilename( final String cacheFilename )
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
     * @throws PersistenceFileBadVersion
     * @see #getCacheFile()
     */
    public void load() throws FileNotFoundException, IOException
    {
        synchronized( _cache_ ) {
            try {
        	    persistenceManager.load( getCacheFile(), _cache_ );
        		}
            catch( PersistenceFileBadVersion retry ) {
                new SimpleTextPersistenceManagerV0().load( getCacheFile(), _cache_ );
                }
        	catch( IOException retry ) {
        		logger.warn( "Can load: " +  getCacheFile() + " trying " +  getBackupCacheFile(), retry );

        		try {
                    persistenceManager.load( getBackupCacheFile(), _cache_ );
                    }
                catch( PersistenceFileBadVersion e ) {
                    new SimpleTextPersistenceManagerV0().load( getBackupCacheFile(), _cache_ );
                    }
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
                logger.warn( "Can't rename cache file from [" + cacheFile + "] to [" + backupFile + "] : result=" + b );
                }
            }

        // store cache
        synchronized( _cache_ ) {
            //storeSimpleTxt( cacheFile, _cache );
            persistenceManager.store( cacheFile, _cache_ );
            }

        // Cache stored successfully
        this.modificationCount = 0;

        if( cacheFile.length() < backupFile.length() ) {
        	// Loose data in cache !
        	logger.warn( "Loose data append previous file" );

        	CacheContent tmpCache = new CacheContent();

        	try {
                persistenceManager.load( backupFile, tmpCache );
                }
            catch( PersistenceFileBadVersion e ) {
                // unexpected cache content
                throw new IOException( "Unexpected cache file", e );
                }

	    	synchronized( _cache_ ) {
				// Add in tmpCache current values
	            for( Entry<URL,URLDataCacheEntry> entry : _cache_ ) {
	            	tmpCache.put( entry.getKey(), entry.getValue() );
	            	}

	            // Update cache using tmpCache
	            for( Entry<URL,URLDataCacheEntry> entry : tmpCache ) {
	            	_cache_.put( entry.getKey(), entry.getValue() );
	            	}

	            tmpCache.clear();
	            //storeSimpleTxt( cacheFile, _cache );
	            persistenceManager.store( cacheFile, _cache_ );
	    		}
        	}

        if( logger.isTraceEnabled() ) {
            logger .trace( "Cache stored successfully " );
            }
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
     * {@link URLCacheListener#ioExceptionHandler(IOException)}
     * method.
     */
    protected void fireIOException(
            final IOException ioe
        )
    {
        Object[] listeners = listenerList.getListenerList();

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if( listeners[i] == URLCacheListener.class ) {
                ((URLCacheListener)listeners[i + 1]).ioExceptionHandler( ioe );
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
        builder.append( _cache_.size() );

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
