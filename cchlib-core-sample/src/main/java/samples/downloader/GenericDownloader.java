package samples.downloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.Proxy;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.filetype.FileDataTypeDescription;
import com.googlecode.cchlib.io.filetype.FileDataTypes;
import com.googlecode.cchlib.net.download.DownloadEvent;
import com.googlecode.cchlib.net.download.DownloadExecutor;
import com.googlecode.cchlib.net.download.DownloadFileEvent;
import com.googlecode.cchlib.net.download.DownloadIOException;
import com.googlecode.cchlib.net.download.DownloadURL;
import com.googlecode.cchlib.net.download.FileDownloadURL;
import com.googlecode.cchlib.net.download.StringDownloadURL;
import com.googlecode.cchlib.net.download.URLCache;
import cx.ath.choisnet.util.checksum.MessageDigestFile;

/**
 *
 *
 */
public abstract class GenericDownloader
{
    private final Logger logger = Logger.getLogger( GenericDownloader.class );
    private final URLCache cache;
    private final File  destinationDirectoryFile;
    private final int   downloadMaxThread;
    private final Proxy proxy;
    private final LoggerListener loggerListener;

    /**
     * Returns an {@link Iterable} object of {@link URL}s to download,
     * must be implement by parent class.
     * @return an {@link Iterable} object of {@link URL}s to download
     * @throws IOException
     */
    protected abstract Collection<URL> collectURLs() throws IOException;


    /**
     *
     * @param rootCacheDirectoryFile
     * @param cacheDirectoryName
     * @param downloadMaxThread
     * @param proxy
     * @param cookieHandler
     * @param logger
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public GenericDownloader(
            final File                              rootCacheDirectoryFile,
            final String                            cacheDirectoryName,
            final int                               downloadMaxThread,
            final Proxy                             proxy,
            //final Map<URI,Map<String,List<String>>> cookieHandlerMap,
            final CookieHandler                     cookieHandler,
            final LoggerListener                    logger
            )
        throws IOException, ClassNotFoundException
    {
        this.destinationDirectoryFile   = new File( rootCacheDirectoryFile, cacheDirectoryName );
        this.downloadMaxThread          = downloadMaxThread;
        this.proxy                      = proxy;
        this.loggerListener             = logger;

        final File cacheIndexFile = new File( rootCacheDirectoryFile, ".cache" );

        this.cache = new URLCache( destinationDirectoryFile, cacheIndexFile );
        this.cache.setAutoStorage( true );

        //if( cookieHandlerMap != null ) {
        if( cookieHandler != null ) {
//            final CookieHandler cookieHandler = CookieManager.getDefault();
//
//            for( Map.Entry<URI,Map<String, List<String>>> entry : cookieHandlerMap.entrySet() ) {
//                final URI                       uri             = entry.getKey();
//                final Map<String, List<String>> responseHeaders = entry.getValue();
//
//                cookieHandler.put( uri, responseHeaders );
//                }
            CookieHandler.setDefault( cookieHandler );
            }

        try  {
            this.cache.load();
            }
        catch( FileNotFoundException ignore ) {
            this.loggerListener.warn( "* warn: cache file not found - " + this.cache.getCacheFile() );
            }
        catch( Exception ignore ) {
            this.loggerListener.warn( "* warn: can't load cache file : " + ignore.getMessage() );
            }
    }

    /**
     * Return a list of String with content of all URLS download content.
     * @param urls {@link Iterable} object of {@link URL}s to parses
     * @return  a list of String with content of all URLS download content.
     * @throws IOException if any
     */
    protected List<String> loads( final Collection<StringDownloadURL> urls ) throws IOException
    {
        final List<String>      result              = new ArrayList<String>();
        final DownloadExecutor  downloadExecutor    = new DownloadExecutor( downloadMaxThread );

        loggerListener.downloadStateInit( new DownloadStateEvent() {
            @Override
            public int getDownloadListSize()
            {
                return urls.size();
            }
        });

        final DownloadEvent eventStringHandler = new DownloadEvent()
        {
            @Override
            public void downloadStart( final DownloadURL dURL )
            {
                loggerListener.downloadStart( dURL );
            }
            @Override
            public void downloadDone( DownloadURL dURL )
            {
                if( logger.isDebugEnabled() ) {
                    logger.debug( "downloadDone: dURL= " + dURL );
                    }

                result.add( dURL.getResultAsString() );

                loggerListener.downloadDone( dURL );
            }
            @Override
            public void downloadFail( DownloadIOException e )
            {
                loggerListener.downloadFail( e );
            }
        };

        downloadExecutor.add( eventStringHandler, proxy, urls );
        downloadExecutor.waitClose();

        return result;
    }

    /**
     *
     * @throws IOException
     */
    void downloadAll() throws IOException
    {
        final Collection<URL>   urls                = collectURLs();
        final DownloadExecutor  downloadExecutor    = new DownloadExecutor( downloadMaxThread );

        final DownloadFileEvent eventHandler = new DownloadFileEvent()
        {
            Integer size = urls.size();

            private void updateDisplay()
            {
                synchronized( size ) {
                    size = size - 1;
                    }

//                logger.info( "downloadExecutor.getPollActiveCount() = " + downloadExecutor.getPollActiveCount() );
//                logger.info( "downloadExecutor.getPoolQueueSize() = " + downloadExecutor.getPoolQueueSize() );
//                logger.info( "size = " + size );
//                logger.info( "size2 = " + (downloadExecutor.getPollActiveCount() + downloadExecutor.getPoolQueueSize() ) );

                loggerListener.downloadStateChange( new DownloadStateEvent() {
                    @Override
                    public int getDownloadListSize()
                    {
                        return size;
                    }
                });
            }
            @Override
            public void downloadStart( DownloadURL dURL )
            {
                loggerListener.downloadStart( dURL );
            }
            @Override
            public void downloadFail( DownloadIOException e )
            {
                final File file = e.getFile();

                loggerListener.downloadFail( e );

                if( file != null ) {
                    file.delete();
                    }

                updateDisplay();
            }
            @Override
            public void downloadDone( DownloadURL dURL )
            {
                final File file = dURL.getResultAsFile();

                try {
                    // Compute MD5 hash code
                    MessageDigestFile   mdf         = new MessageDigestFile();
                    byte[]              digestKey   = mdf.compute( file );

                    String hashCodeString   = MessageDigestFile.computeDigestKeyString( digestKey );

                    // Identify file content to generate extension
                    FileDataTypeDescription type        = FileDataTypes.findDataTypeDescription( file );
                    String                  extension   = null;

                    if( type != null ) {
                        extension = type.getExtension();
                        }
                    else {
                        extension = ".xxx";
                        }

                    // Create new file name
                    File ffile = new File( destinationDirectoryFile, hashCodeString + extension );

                    // Rename file
                    boolean isRename = file.renameTo( ffile );

                    if( isRename ) {
                        dURL.setResultAsFile( ffile );
                        loggerListener.downloadStored( dURL );
                        cache.add( dURL.getURL(), hashCodeString, ffile.getName() );
                        }
                    else {
                        loggerListener.downloadCantRename( dURL, file, ffile );
                        }
                    }
                catch( FileNotFoundException e ) {
                    // Should not occur
                    logger.error( "downloadDone (Should not occur):", e );
                    }
                catch( IOException e ) {
                    // Should not occur
                    logger.error( "downloadDone (Should not occur):", e );
                    }
                catch( NoSuchAlgorithmException e ) {
                    // Should not occur
                    logger.error( "downloadDone (Should not occur):", e );
                    }
                finally {
                    if( file != null && file.isFile() ) {
                        // Occur when file is load twice, using same URL or not.
                        // FIXME
                        // Delete ? rename ??
                        logger.info( "downloadDone: file not deleted ! ? : " + file );
                        }
                    }

                updateDisplay();
            }
            @Override
            public File createDownloadTmpFile() throws IOException
            {
                try {
                    return File.createTempFile( "download", null, cache.getTempDirectoryFile() );
                    }
                catch( IOException e ) {
                    logger.error(
                        "createTempFile Error: cache.getTempDirectoryFile() = " + cache.getTempDirectoryFile(),
                        e
                        );
                    throw e;
                    }
            }
        };

        if( logger.isTraceEnabled() ) {
            logger.trace( "Cache content: " + cache );
            }

        final int statsAskedDownload = urls.size();
        int statsLauchedDownload = 0;

        for( URL u: urls ) {
            if( cache.isInCacheIndex( u ) ) {
            //if( cache.isInCache( u ) ) {
                // skip this entry !
                loggerListener.info( "Already in cache (Skip): " + u );
                }
            else {
                DownloadURL du = new FileDownloadURL( u );

                downloadExecutor.addDownload( eventHandler, proxy, du );
                statsLauchedDownload++;
                }
            }

        downloadExecutor.waitClose();

        if( logger.isDebugEnabled() ) {
            logger.debug( "statsAskedDownload = " + statsAskedDownload );
            logger.debug( "statsLauchedDownload = " + statsLauchedDownload );
            }

        try {
            this.cache.store();
            }
        catch( IOException ioe ) {
            logger.error( "Error while storing cache index", ioe );

            throw ioe;
            }
    }
}
