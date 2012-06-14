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
import com.googlecode.cchlib.io.filetype.FileDataTypeDescription;
import com.googlecode.cchlib.io.filetype.FileDataTypes;
import com.googlecode.cchlib.net.download.DownloadExecutor;
import com.googlecode.cchlib.net.download.DownloadFileEvent;
import com.googlecode.cchlib.net.download.DownloadStringEvent;
import com.googlecode.cchlib.net.download.URLCache;
import cx.ath.choisnet.util.checksum.MessageDigestFile;

/**
 *
 *
 */
public abstract class GenericDownloader
{
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

        DownloadStringEvent eventHandler = new DownloadStringEvent()
        {
            @Override
            public void downloadStart( final URL url )
            {
                loggerListener.info( "downloading: " + url );
            }
            @Override
            public void downloadDone( final URL url, final String content )
            {
                result.add( content );
            }
            @Override
            public void downloadFail( final URL url, final Throwable cause )
            {
                loggerListener.error( url, null, cause ); // No file, just put download into string !
            }
        };

        downloadExecutor.add( eventHandler, proxy, urls );
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

        DownloadFileEvent eventHandler = new DownloadFileEvent()
        {
            Integer size = urls.size();

            @Override
            public File downloadStart( URL url ) throws IOException
            {
                loggerListener.info( "Start downloading: " + url );

                return File.createTempFile( "pic", null, cache.getTempDirectoryFile() );
            }
/*
            @Override
            public void downloadFail( URL url, Throwable cause )
            {
                loggerListener.error( url, cause );
            }
*/
            @Override
            public void downloadFail( URL url, File file, Throwable cause )
            {
                loggerListener.error( url, file, cause );

                if( file != null ) {
                    file.delete();
                    }

                updateDisplay();
            }
            @Override
            public void downloadDone( URL url, File file )
            {
                try {
                    // Compute MD5 hash ode
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

                updateDisplay();
            }
            private void updateDisplay()
            {
                synchronized( size ) {
                    size = size - 1;
                    }

                loggerListener.downloadStateChange( new DownloadStateEvent() {
                    @Override
                    public int getDownloadListSize()
                    {
                        return size;
                    }
                });
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
