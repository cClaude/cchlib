package com.googlecode.cchlib.tools.downloader;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.filetype.FileDataTypeDescription;
import com.googlecode.cchlib.io.filetype.FileDataTypes;
import com.googlecode.cchlib.net.download.DownloadConfigurationException;
import com.googlecode.cchlib.net.download.DownloadEvent;
import com.googlecode.cchlib.net.download.DownloadExecutor;
import com.googlecode.cchlib.net.download.DownloadFileEvent;
import com.googlecode.cchlib.net.download.DownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadIOException;
import com.googlecode.cchlib.net.download.DownloadStringURL;
import com.googlecode.cchlib.net.download.DownloadURL;
import com.googlecode.cchlib.net.download.cache.URICache;
import com.googlecode.cchlib.net.download.fis.DefaultFilterInputStreamBuilder;

/**
 *
 *
 */
public class GenericDownloader
{
    private final Logger LOGGER = Logger.getLogger( GenericDownloader.class );

    private Object lock = new Object();
    private final URICache cache;
    private final File  destinationDirectoryFile;
    private final int   downloadMaxThread;
    private final LoggerListener loggerListener;
    private final GenericDownloaderAppInterface gdai;
    private final GenericDownloaderAppUIResults gdauir;

    /**
     *
     * @param gdai
     * @param gdauir
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public GenericDownloader(
            final GenericDownloaderAppInterface gdai,
            final GenericDownloaderAppUIResults gdauir
            )
        throws IOException, ClassNotFoundException
    {
        this.gdai   = gdai;
        this.gdauir = gdauir;

//logger.setLevel( Level.INFO ); // FIXME: remove this

        final File rootCacheDirectoryFile =
                new File(
                    new File(".").getAbsoluteFile(),
                    "output" // FIXME add parameter !
                    ).getCanonicalFile();

        rootCacheDirectoryFile.mkdirs();
        // TODO: verify if directory exist ?

        this.destinationDirectoryFile   = new File( rootCacheDirectoryFile, gdai.getCacheRelativeDirectoryCacheName() );
        this.downloadMaxThread          = gdauir.getDownloadThreadCount();
        this.loggerListener             = gdauir.getAbstractLogger();

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "destinationDirectoryFile = " + destinationDirectoryFile );
            }

        final File cacheIndexFile = new File( rootCacheDirectoryFile, ".cache" );

        this.cache = new URICache( destinationDirectoryFile, cacheIndexFile );
        this.cache.setAutoStorage( true );

        try  {
            this.cache.load();
            }
        catch( FileNotFoundException ignore ) {
            LOGGER.warn( "* warn: cache file not found - " + this.cache.getCacheFile() );
            }
        catch( Exception ignore ) {
            LOGGER.warn( "* warn: can't load cache file : " + ignore.getMessage() );
            }
    }

    /**
     * Returns an {@link Iterable} object of {@link DownloadFileURL}s to download,
     * must be implement by parent class.
     * @return an {@link Iterable} object of {@link DownloadFileURL}s to download
     * @throws IOException
     * @throws DownloadConfigurationException
     * @throws RejectedExecutionException
     * @throws URISyntaxException
     */
    final
    protected Collection<DownloadFileURL> collectDownloadURLs()
        throws  IOException,
                RejectedExecutionException,
                DownloadConfigurationException, URISyntaxException
    {
        final Collection<DownloadFileURL>   urls        = new HashSet<DownloadFileURL>();
        final List<DownloadStringURL>       contentList = loads( gdai.getURLDownloadAndParseCollection() );

        for( DownloadStringURL pageContent : contentList ) {
            urls.addAll(
                gdai.getURLToDownloadCollection( gdauir, pageContent )
                );
            }

        return urls;
    }

    /**
     * Return a list of String with content of all URLS download content.
     * @param urls {@link Iterable} object of {@link URL}s to parses
     * @return  a list of String with content of all URLS download content.
     * @throws IOException if any
     * @throws DownloadConfigurationException
     * @throws RejectedExecutionException
     */
    protected List<DownloadStringURL> loads( final Collection<DownloadStringURL> urls ) throws IOException, RejectedExecutionException, DownloadConfigurationException
    {
        //final List<String>      result              = new ArrayList<String>();
        final List<DownloadStringURL>   result              = new ArrayList<DownloadStringURL>();
        final DownloadExecutor          downloadExecutor    = new DownloadExecutor( downloadMaxThread, null );

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
                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "downloadDone: dURL= " + dURL );
                    }

                result.add( DownloadStringURL.class.cast( dURL ) );

                loggerListener.downloadDone( dURL );
            }
            @Override
            public void downloadFail( DownloadIOException e )
            {
                loggerListener.downloadFail( e );
            }
        };

        downloadExecutor.add( urls, eventStringHandler );
        downloadExecutor.waitClose();

        return result;
    }

    /**
     *
     * @throws IOException
     * @throws DownloadConfigurationException
     * @throws RejectedExecutionException
     * @throws URISyntaxException
     */
    public void onClickStartDownload() throws IOException, RejectedExecutionException, DownloadConfigurationException, URISyntaxException
    {
        final Collection<DownloadFileURL>   urls                = collectDownloadURLs();
        final DownloadExecutor              downloadExecutor    = new DownloadExecutor(
                downloadMaxThread,
//                new MD5FilterInputStreamBuilder()
                new DefaultFilterInputStreamBuilder()
                );

        final DownloadFileEvent eventHandler = new DownloadFileEvent()
        {
            int size = 0;

            private void updateDisplay()
            {
                synchronized( lock  ) {
                    size++;
                    }

                LOGGER.info(
                    "downloadExecutor.getPollActiveCount() = " + downloadExecutor.getPollActiveCount()
                    + " * downloadExecutor.getPoolQueueSize() = " + downloadExecutor.getPoolQueueSize()
                    + " * size = " + size
                    + " * size2 = " + (downloadExecutor.getPollActiveCount() + downloadExecutor.getPoolQueueSize() )
                    );

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
                final DownloadFileURL   dfURL       = DownloadFileURL.class.cast( dURL );
                final String            hashString  = (String)dfURL.getProperty(
                        DefaultFilterInputStreamBuilder.HASH_CODE
                        );

                URI uri = cache.findURI( hashString );

                if( uri != null ) {
                    // Already downloaded
                    alreadyDownloaded( dfURL );
                    }
                else {
                    // New file
                    newFileDownloaded( dfURL );
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
                    LOGGER.error(
                        "createTempFile Error: cache.getTempDirectoryFile() = " + cache.getTempDirectoryFile(),
                        e
                        );
                    throw e;
                    }
            }
        };

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Cache content: " + cache );
            }

        final int statsAskedDownload = urls.size();
        int statsLauchedDownload = 0;

        for( DownloadFileURL du: urls ) {
            if( cache.isInCacheIndex( du.getURL() ) ) {
                // skip this entry !
                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "Already in cache (Skip): " + du.getURL() );
                    }
                }
            else {
                downloadExecutor.addDownload( du, eventHandler );
                statsLauchedDownload++;
                }
            }

        downloadExecutor.waitClose();

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "statsAskedDownload = " + statsAskedDownload );
            LOGGER.debug( "statsLauchedDownload = " + statsLauchedDownload );
            }

        try {
            this.cache.store();
            }
        catch( IOException ioe ) {
            LOGGER.error( "Error while storing cache index", ioe );

            throw ioe;
            }
    }

    private void alreadyDownloaded( final DownloadFileURL dfURL )
    {
        final File file = dfURL.getResultAsFile();

        // Remove this file !
        file.delete();

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Already downloaded (deleted): " + file );
            }
    }

    private void newFileDownloaded( final DownloadFileURL dfURL )
    {
        final File file = dfURL.getResultAsFile();

        if( ! isFileValidAccordingToConstraints( dfURL ) ) {
            // out of constraints : remove this file
            file.delete();

            loggerListener.oufOfConstraints( dfURL );

            return;
            }
        final String    hashString  = (String)dfURL.getProperty( "HashCode" );

        // Identify file content to generate extension
        FileDataTypeDescription type;
        String                  extension;

        try {
            type = FileDataTypes.findDataTypeDescription( file );
            }
        catch( IOException e ) {
            type = null;
            }

        if( type != null ) {
            extension = type.getExtension();
            }
        else {
            extension = ".xxx";
            }

        // Create new file name
        File ffile = new File(
                destinationDirectoryFile,
                hashString + extension
                );

        // Rename file
        boolean isRename = file.renameTo( ffile );

        if( isRename ) {
            // Set new name for this file.
            dfURL.setResultAsFile( ffile );

            // Notify
            loggerListener.downloadStored( dfURL );

            // Add to cache
            Date date = new Date(); // TODO get date of end of download ?
            cache.add( dfURL.getURI(), date, hashString, ffile.getName() );
            }
        else {
            File file2;

            if( ffile.exists() ) {
                file2 = new File( destinationDirectoryFile, file.getName() + '.' + hashString + extension + ".exists" );
                }
            else {
                file2 = new File( destinationDirectoryFile, file.getName() + '.' + hashString + extension + "tmp" );
                }

            if( file.renameTo( file2 ) ) {
                // Rename to something better
                dfURL.setResultAsFile( file2 );

                loggerListener.downloadCantRename( dfURL, file, file2 );
                }
            else {
                // Can not rename at all
                loggerListener.downloadCantRename( dfURL, file, ffile );
                }
            }
    }

    // is file valid according to constrains
    private boolean isFileValidAccordingToConstraints(
        final DownloadFileURL dfURL
        )
    {
        final Dimension dimension   = (Dimension)dfURL.getProperty( DefaultFilterInputStreamBuilder.DIMENSION );
        final String    formatName  = (String)dfURL.getProperty( DefaultFilterInputStreamBuilder.FORMAT_NAME );

        if( dimension != null ) {
            if( dimension.width < 100 || dimension.height < 100 ) {
                return false;
                }

            if( (dimension.getWidth() * dimension.getHeight()) < (450 * 450) ) {
                return false;
                }
            }

        return true;
    }

//    private String computeHashString( final File file )
//        throws NoSuchAlgorithmException, IOException
//    {
//        // Compute MD5 hash code
//        MessageDigestFile   mdf         = new MessageDigestFile();
//        byte[]              digestKey   = mdf.compute( file );
//
//        return MessageDigestFile.computeDigestKeyString( digestKey );
//    }

    public void onClickStopDownload()
    {
        // TODO Auto-generated method stub
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
        LOGGER.info( "stopDownload() not implemented !" );
    }

}
