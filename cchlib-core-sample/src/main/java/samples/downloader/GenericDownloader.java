package samples.downloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import com.googlecode.cchlib.net.download.DownloadResult;
import com.googlecode.cchlib.net.download.DownloadResultType;
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
     * @param tempDirectoryFile
     * @param destinationDirectoryFile
     * @param downloadMaxThread
     * @param proxy
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public GenericDownloader(
            final File              destinationDirectoryFile,
            final int               downloadMaxThread,
            final Proxy             proxy,
            final LoggerListener    logger
            )
        throws IOException, ClassNotFoundException
    {
        this.destinationDirectoryFile   = destinationDirectoryFile;
        this.downloadMaxThread          = downloadMaxThread;
        this.proxy                      = proxy;
        this.loggerListener             = logger;

        this.cache = new URLCache( destinationDirectoryFile );
        this.cache.setCacheFile( ( new File( destinationDirectoryFile, ".cache" ) ) );
        this.cache.setAutoStorage( true );

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
    protected List<String> loads( final Collection<URL> urls ) throws IOException
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
            public DownloadResultType getDownloadResultType()
            {
                return DownloadResultType.STRING;
            }
            @Override
            public void downloadStart( final URL url )
            {
                loggerListener.info( "downloading as a String: " + url );
            }
            @Override
            public void downloadDone( URL url, DownloadResult downloadResult )
            {
                if( logger.isDebugEnabled() ) {
                    logger.debug( url.toExternalForm() + " -> " + downloadResult.getString().length() );
                    }

                result.add( downloadResult.getString() );
            }
            @Override
            public void downloadFail( DownloadIOException e )
            {
                loggerListener.error( e.getUrl(), e.getFile(), e.getCause() ); // No file, just put download into string !
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

                logger.info( "downloadExecutor.getPollActiveCount() = " + downloadExecutor.getPollActiveCount() );
                logger.info( "downloadExecutor.getPoolQueueSize() = " + downloadExecutor.getPoolQueueSize() );
                logger.info( "size = " + size );
                logger.info( "size2 = " + (downloadExecutor.getPollActiveCount() + downloadExecutor.getPoolQueueSize() ) );

                loggerListener.downloadStateChange( new DownloadStateEvent() {
                    @Override
                    public int getDownloadListSize()
                    {
                        return size;
                    }
                });
            }
            @Override
            public DownloadResultType getDownloadResultType()
            {
                return DownloadResultType.FILE;
            }
            @Override
            public void downloadStart( URL url )
            {
                loggerListener.info( "Start downloading: " + url );
            }
            @Override
            public void downloadFail( DownloadIOException e )
            {
                final File file = e.getFile();

                loggerListener.error( e.getUrl(), file, e.getCause() );

                if( file != null ) {
                    file.delete();
                    }

                updateDisplay();
            }
            @Override
            public void downloadDone( URL url, DownloadResult result )
            {
                final File file = result.getFile();

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
                        loggerListener.info( "new file > " + ffile );
                        cache.add( url, hashCodeString, ffile.getName() );
                        }
                    else {
                        loggerListener.info( "*** already exists ? " + ffile );
                        }
                    }
                catch( FileNotFoundException e ) {
                    // Should not occur
                    e.printStackTrace();
                    }
                catch( IOException e ) {
                    // Should not occur
                    e.printStackTrace();
                    }
                catch( NoSuchAlgorithmException e ) {
                    // Should not occur
                    e.printStackTrace();
                    }
                finally {
                    if( file != null && file.isFile() ) {
                        // FIXME
                        // Delete ? rename ??
                        logger.info( "delete file ? : " + file );
                        }
                    }

                updateDisplay();
            }
            @Override
            public File getDownloadTmpFile() throws IOException
            {
                return File.createTempFile( "download", null, cache.getTempDirectoryFile() );
            }
        };

        for( URL u: urls ) {
            if( cache.isInCache( u ) ) {
                // skip this entry !
                loggerListener.info( "Already loaded: " + u );
                }
            else {
                downloadExecutor.addDownload( eventHandler, proxy, u );
                }
            }

        downloadExecutor.waitClose();
        storeCache();
    }

    private void storeCache()
    {
        try {
            this.cache.store();
            }
        catch( IOException logOnly ) {
            logOnly.printStackTrace();
            }
    }
}
