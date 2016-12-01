package com.googlecode.cchlib.net.download.cache;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Map.Entry;
import javax.swing.event.EventListenerList;
import org.apache.log4j.Logger;

/**
 * NEEDDOC
 *
 * @since 4.1.8
 */
public class URICache implements Closeable
{
    private static final Logger LOGGER = Logger.getLogger( URICache.class );
    private static final int AUTOSTORE_DEFAULT_THRESHOLD = 50;

    /** The listeners waiting for object changes. */
    private final EventListenerList listenerList = new EventListenerList();

    /** Cache all access should be synchronized on this object */
    private final CacheContent theCache;

    private final File cacheRootDirFile;

    /**
     * Cache file, use {@link #getCacheFile()} and {@link #setCacheFile(File)}
     * to access
     */
    private File theCacheFile;
    private boolean autostore;
    private int autostoreThreshold = AUTOSTORE_DEFAULT_THRESHOLD;
    private int modificationCount = 0;
    private final URICachePersistenceManager persistenceManager = new SimpleTextPersistenceManagerV2();

    /**
     * Create a new URLCache using a default cache {@link File} stored
     * in cacheRootDirFile
     *
     * @param cacheRootDirFile Root {@link File} directory.
     * @throws NullPointerException if cacheRootDirFile is null
     * @throws IOException if cacheRootDirFile is not a valid directory
     */
    public URICache( final File cacheRootDirFile ) throws IOException
    {
        this.cacheRootDirFile = cacheRootDirFile;
        this.theCache         = new URICacheContent();
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
    public URICache( final File cacheRootDirFile, final String cacheFilename )
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
    public URICache( final File cacheRootDirFile, final File cacheFile )
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
     * Check if an {@link URI} is in cache, but don't care about
     * data.
     *
     * @param uri {@link URI} to check
     * @return true if {@link URI} is in cache, false otherwise
     */
    public boolean isInCacheIndex( final URI uri )
    {
        return get( uri ) != null;
    }

    /**
     * Check if an {@link URI} is in cache and if file is
     * already in cache directory.
     *
     * @param uri {@link URI} to check
     * @return true if {@link URI} is in cache and if
     *   cached file exist in directory cache,
     *   false otherwise
     */
    public boolean isInCache( final URI uri )
    {
        final URIDataCacheEntry entry = get( uri );

        if( entry != null ) {
            // TODO: allow to define a relative filename
            // (add set/get cacheHomeDirectory File)
            final File f = new File( this.cacheRootDirFile, entry.getRelativeFilename() );

            return f.isFile();
            }

        return false;
    }

    /**
     * Check if an {@link URL} is in cache and if file is
     * already in cache directory.
     *
     * @param url {@link URL} to check
     * @return true if {@link URL} is in cache and if
     *   cached file exist in directory cache,
     *   false otherwise
     * @throws URISyntaxException
     */
    public boolean isInCacheIndex( final URL url ) throws URISyntaxException
    {
        return isInCache( url.toURI() );
    }

    /**
     * Add a new ({@link URL},filename) couple in cache
     *
     * @param url             {@link URL} for this filename
     * @param date
     * @param contentHashCode URL content Hash code, or null
     * @param filename        Local filename
     * @throws URISyntaxException
     */
    public void add(
        final URL    url,
        final Date   date,
        final String contentHashCode,
        final String filename
        ) throws URISyntaxException
    {
        add( url.toURI(), date, contentHashCode, filename );
    }

    /**
     * Add a new ({@link URI},filename) couple in cache
     *
     * @param uri             {@link URI} for this filename
     * @param date
     * @param contentHashCode URL content Hash code, or null
     * @param filename        Local filename
     */
    public void add(
        final URI    uri,
        final Date   date,
        final String contentHashCode,
        final String filename
        )
    {
        synchronized( this.theCache ) {
            this.theCache.put( uri, new DefaultURICacheEntry( date, contentHashCode, filename ) );

            this.modificationCount++;
            }

        autoStore();
    }

    /**
     * Retrieve {@link URI} in cache
     *
     * @param uri {@link URI} to retrieve
     * @return {@link URIDataCacheEntry} for giving <code>uri</code> if in cache,
     *         null otherwise
     */
    public URIDataCacheEntry get( final URI uri )
    {
        synchronized( this.theCache ) {
            return this.theCache.getDataCacheEntry( uri );
            }
    }

    /**
     * Returns {@link URI} for this hashcode if exist, null otherwise
     * @param hashCode
     * @return {@link URI} for this hashcode if exist, null otherwise
     */
    public URI findURI( final String hashCode )
    {
        synchronized( this.theCache ) {
            return this.theCache.getURI( hashCode );
            }
    }

    /**
     * Returns cache size
     * @return cache size
     */
    public int size()
    {
        synchronized( this.theCache ) {
            return this.theCache.size();
            }
    }

    /**
     * Returns filename for giving {@link URL}
     *
     * @param url {@link URL} to retrieve
     * @return filename for giving {@link URL},
     *         or null if URL is not in cache
     * @throws URISyntaxException
     */
    public String getRelativeFilename( final URL url ) throws URISyntaxException
    {
        return getRelativeFilename( url.toURI() );
    }

    /**
     * NEEDDOC
     *
     * @param uri
     * @return NEEDDOC
     */
    public String getRelativeFilename( final URI uri )
    {
        final URIDataCacheEntry entry = get( uri );

        if( entry != null ) {
            return entry.getRelativeFilename();
            }
        else {
            return null;
            }
    }

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

        this.theCacheFile = cacheFile;
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

        setCacheFile( new File( this.cacheRootDirFile, cacheFilename ) ) ;
    }

    /**
     * Returns cache {@link File}
     * @return cacheFile for this cache
     */
    public File getCacheFile()
    {
        if( this.theCacheFile == null ) {
            // provide a default cache file if not set.
            this.theCacheFile = new File( this.cacheRootDirFile, ".cache-index" );
            }

        return this.theCacheFile;
    }

    /**
     * Returns cache backup {@link File} based on {@link #getCacheFile()}
     * @return cache backup {@link File}
     */
    protected File getBackupCacheFile()
    {
        final File cacheFile = getCacheFile();

        return new File( cacheFile.getParentFile(), cacheFile.getName() + "~" );
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
     * @throws PersistenceFileBadVersionException
     * @see #getCacheFile()
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    public void load() throws FileNotFoundException, IOException
    {
        synchronized( this.theCache ) {
            try {
                this.persistenceManager.load( getCacheFile(), this.theCache );
                }
            catch( final PersistenceFileBadVersionException retry ) {
                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "Migratation", retry );
                }

                new SimpleTextPersistenceManagerV0().load( getCacheFile(), this.theCache );
                }
            catch( final IOException retry ) {
                LOGGER.warn( "Can load: " +  getCacheFile() + " trying " +  getBackupCacheFile(), retry );

                try {
                    this.persistenceManager.load( getBackupCacheFile(), this.theCache );
                    }
                catch( final PersistenceFileBadVersionException e ) {
                    if( LOGGER.isTraceEnabled() ) {
                        LOGGER.trace( "Migratation", e );
                    }

                    new SimpleTextPersistenceManagerV0().load( getBackupCacheFile(), this.theCache );
                    }
                }
            }
    }

    /**
     * Save cache file
     *
     * @throws IOException if any I/O error occur
     * @throws URISyntaxException
     * @see #getCacheFile()
     */
    //synchronized
    public void store() throws IOException
    {
        final File cacheFile     = getCacheFile();
        final File backupFile    = getBackupCacheFile();

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Try to store: " + cacheFile );
            }

        if( cacheFile.isFile() ) {
            deletePreviousBackupFile( cacheFile, backupFile );
            }

        // store cache
        synchronized( this.theCache ) {
            this.persistenceManager.store( cacheFile, this.theCache );
            }

        // Cache stored successfully
        this.modificationCount = 0;

        if( cacheFile.length() < backupFile.length() ) {
            fixCache( cacheFile, backupFile );
            }

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Cache stored successfully " );
            }
    }

    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    private void fixCache( final File cacheFile, final File backupFile )
            throws FileNotFoundException, IOException
    {
        // Loose data in cache !
        LOGGER.warn( "Loose data append previous file" );

        final CacheContent tmpCache = new URICacheContent();

        try {
            this.persistenceManager.load( backupFile, tmpCache );
            }
        catch( final PersistenceFileBadVersionException e ) {
            // unexpected cache content
            throw new IOException( "Unexpected cache file", e );
            }

        synchronized( this.theCache ) {
            // Add in tmpCache current values
            for( final Entry<URI,URIDataCacheEntry> entry : this.theCache ) {
                tmpCache.put( entry.getKey(), entry.getValue() );
                }

            // Update cache using tmpCache
            for( final Entry<URI,URIDataCacheEntry> entry : tmpCache ) {
                this.theCache.put( entry.getKey(), entry.getValue() );
                }

            tmpCache.clear();
            this.persistenceManager.store( cacheFile, this.theCache );
            }
    }

    private void deletePreviousBackupFile( final File cacheFile, final File backupFile )
    {
        // Delete previous version of backup file.
        final boolean isDeleted = backupFile.delete();

        if( isDeleted && LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Can not delete file: " + cacheFile );
        }

        // Rename previous version of cache file to backup file.
        final boolean b = cacheFile.renameTo( backupFile );

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace(
                "Rename cache file from [" + cacheFile + "] to ["
                    + backupFile + "] : result=" + b
                );
            }

        if( ! b ) {
            LOGGER.warn(
                "Can't rename cache file from [" + cacheFile + "] to ["
                    + backupFile + "] : result=" + b
                );
            }
    }

    @SuppressWarnings("squid:S1066")
    private void autoStore()
    {
        if( this.autostore ) {
            if( this.modificationCount > this.autostoreThreshold ) {
                try {
                    store();
                    fireAutoStore();
                    }
                catch( final IOException e ) {
                    fireIOException( e );
                    }
                }
            }
    }

    /**
     * Adds a {@link URICacheListener} to the
     * {@link URICache}'s listener list.
     *
     * @param listener the {@link URICacheListener} to add
     */
    public void addURICacheListener( final URICacheListener listener )
    {
        this.listenerList.add( URICacheListener.class, listener );
    }

    /**
     * Removes a {@link URICacheListener} from the
     *  {@link URICache}'s listener list.
     *
     * @param listener the {@link URICacheListener} to remove
     */
    public void removeURICacheListener( final URICacheListener listener )
    {
        this.listenerList.remove( URICacheListener.class, listener );
    }

    /**
     * Runs each {@link URICacheListener}'s
     * {@link URICacheListener#ioExceptionHandler(IOException)}
     * method.
     *
     * @param ioCause Error cause
     */
    protected void fireIOException( final IOException ioCause )
    {
        final Object[] listeners = this.listenerList.getListenerList();

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if( listeners[i] == URICacheListener.class ) {
                ((URICacheListener)listeners[i + 1]).ioExceptionHandler( ioCause );
                }
            }
    }

    /**
     * Runs each {@link URICacheListener}'s
     * {@link URICacheListener#autoStoreDone()}
     * method.
     */
    protected void fireAutoStore()
    {
        final Object[] listeners = this.listenerList.getListenerList();

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) { // $codepro.audit.disable numericLiterals
            if( listeners[i] == URICacheListener.class ) { // $codepro.audit.disable useEquals
                ((URICacheListener)listeners[i + 1]).autoStoreDone();
                }
            }
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "URLCache [cacheRootDirFile=" );
        builder.append( this.cacheRootDirFile );
        builder.append( ", cacheFile=" );
        builder.append( getCacheFile() );
        builder.append( ", autostore=" );
        builder.append( this.autostore );
        builder.append( ", autostoreThreshold=" );
        builder.append( this.autostoreThreshold );
        builder.append( ", modificationCount=" );
        builder.append( this.modificationCount );
        builder.append( ", cache size=" );
        builder.append( this.theCache.size() );
        builder.append( ']' );

        return builder.toString();
    }

    @Override
    public void close() throws IOException
    {
        if( this.getCacheFile() != null ) {
            store();
            }
    }
}
