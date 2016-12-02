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
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.net.download.DownloadConfigurationException;
import com.googlecode.cchlib.net.download.DownloadEvent;
import com.googlecode.cchlib.net.download.DownloadExecutor;
import com.googlecode.cchlib.net.download.DownloadFileEvent;
import com.googlecode.cchlib.net.download.DownloadIOException;
import com.googlecode.cchlib.net.download.DownloadURI;
import com.googlecode.cchlib.net.download.cache.URICache;
import com.googlecode.cchlib.net.download.fis.DefaultFilterInputStreamBuilder;

/**
 *
 *
 */
public class GenericDownloader
{
    private static final Logger LOGGER = Logger.getLogger( GenericDownloader.class );

    private final Object lock = new Object();
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
            LOGGER.debug( "destinationDirectoryFile = " + this.destinationDirectoryFile );
            }

        final File cacheIndexFile = new File( rootCacheDirectoryFile, ".cache" );

        this.cache = new URICache( this.destinationDirectoryFile, cacheIndexFile );
        this.cache.setAutoStorage( true );

        try  {
            this.cache.load();
            }
        catch( final FileNotFoundException ignore ) {
            LOGGER.warn( "* warn: cache file not found - " + this.cache.getCacheFile() );
            }
        catch( final Exception ignore ) {
            LOGGER.warn( "* warn: can't load cache file : " + ignore.getMessage() );
            }
    }

    /**
     * Returns an {@link Iterable} object of {@link ContentDownloadURI}s to download,
     * must be implement by parent class.
     * @return an {@link Iterable} object of {@link ContentDownloadURI}s to download
     * @throws IOException
     * @throws DownloadConfigurationException
     * @throws RejectedExecutionException
     * @throws URISyntaxException
     */
    protected Collection<ContentDownloadURI<File>> collectDownloadURLs()
        throws  IOException,
                RejectedExecutionException,
                DownloadConfigurationException, URISyntaxException
    {
        final Collection<ContentDownloadURI<File>>   urls        = new HashSet<>();
        final List<ContentDownloadURI<String>>       contentList = loads( this.gdai.getURLDownloadAndParseCollection() );

        for( final ContentDownloadURI<String> pageContent : contentList ) {
            urls.addAll(
                this.gdai.getURLToDownloadCollection( this.gdauir, pageContent )
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
    protected List<ContentDownloadURI<String>> loads(
        final Collection<ContentDownloadURI<String>> urls
        ) throws IOException, RejectedExecutionException, DownloadConfigurationException
    {
        final List<ContentDownloadURI<String>> result           = new ArrayList<>();
        final DownloadExecutor                 downloadExecutor = new DownloadExecutor( this.downloadMaxThread, null );

        this.loggerListener.downloadStateInit( () -> urls.size() );

        final DownloadEvent eventStringHandler = new DownloadEvent()
        {
            @Override
            public void downloadStart( final DownloadURI dURL )
            {
                GenericDownloader.this.loggerListener.downloadStart( dURL );
            }
            @Override
            public void downloadDone( final DownloadURI dURL )
            {
                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "downloadDone: dURL= " + dURL );
                    }

                @SuppressWarnings("unchecked")
                final ContentDownloadURI<String> downloader = (ContentDownloadURI<String>)dURL;

                result.add( downloader );

                GenericDownloader.this.loggerListener.downloadDone( dURL );
            }
            @Override
            public void downloadFail( final DownloadIOException e )
            {
                GenericDownloader.this.loggerListener.downloadFail( e );
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
        final Collection<ContentDownloadURI<File>> urls             = collectDownloadURLs();
        final DownloadExecutor                     downloadExecutor = new DownloadExecutor(
                this.downloadMaxThread,
//                new MD5FilterInputStreamBuilder()
                new DefaultFilterInputStreamBuilder<File>()
                );

        final DownloadFileEvent eventFileHandler = new DownloadFileEvent()
        {
            int size = 0;

            private void updateDisplay()
            {
                synchronized( GenericDownloader.this.lock  ) {
                    this.size++;
                    }

                LOGGER.info(
                    "downloadExecutor.getPollActiveCount() = " + downloadExecutor.getPollActiveCount()
                    + " * downloadExecutor.getPoolQueueSize() = " + downloadExecutor.getPoolQueueSize()
                    + " * size = " + this.size
                    + " * size2 = " + (downloadExecutor.getPollActiveCount() + downloadExecutor.getPoolQueueSize() )
                    );

                GenericDownloader.this.loggerListener.downloadStateChange( ( ) -> this.size);
            }
            @Override
            public void downloadStart( final DownloadURI dURL )
            {
                GenericDownloader.this.loggerListener.downloadStart( dURL );
            }
            @Override
            public void downloadFail( final DownloadIOException e )
            {
                final File file = e.getFile();

                GenericDownloader.this.loggerListener.downloadFail( e );

                if( file != null ) {
                    file.delete();
                    }

                updateDisplay();
            }
            @Override
            public void downloadDone( final DownloadURI dURL )
            {
                final ContentDownloadURI<File> downloader = DownloadFileEvent.getDownloader( dURL );
                final String                   hashString = downloader.getStringProperty(
                        DefaultFilterInputStreamBuilder.HASH_CODE
                        );

                final URI uri = GenericDownloader.this.cache.findURI( hashString );

                if( uri != null ) {
                    // Already downloaded
                    alreadyDownloaded( downloader );
                    }
                else {
                    // New file
                    newFileDownloaded( downloader );
                    }

                updateDisplay();
            }

            @Override
            public File createDownloadTmpFile() throws IOException
            {
                try {
                    return File.createTempFile( "download", null, GenericDownloader.this.cache.getTempDirectoryFile() );
                    }
                catch( final IOException e ) {
                    LOGGER.error(
                        "createTempFile Error: cache.getTempDirectoryFile() = " + GenericDownloader.this.cache.getTempDirectoryFile(),
                        e
                        );
                    throw e;
                    }
            }
        };

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Cache content: " + this.cache );
            }

        final int statsAskedDownload = urls.size();
        int statsLauchedDownload = 0;

        for( final ContentDownloadURI<File> du: urls ) {
            if( this.cache.isInCacheIndex( du.getURI() ) ) {
                // skip this entry !
                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "Already in cache (Skip): " + du.getURL() );
                    }
                }
            else {
                downloadExecutor.addDownload( du, eventFileHandler );
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
        catch( final IOException ioe ) {
            LOGGER.error( "Error while storing cache index", ioe );

            throw ioe;
            }
    }

    private void alreadyDownloaded( final ContentDownloadURI<File> dfURL )
    {
        final File file = dfURL.getResult();

        // Remove this file !
        file.delete();

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Already downloaded (deleted): " + file );
            }
    }

    private void newFileDownloaded( final ContentDownloadURI<File> dfURL )
    {
        final File file = dfURL.getResult();

        if( ! isFileValidAccordingToConstraints( dfURL ) ) {
            // out of constraints : remove this file
            file.delete();

            this.loggerListener.oufOfConstraints( dfURL );

            return;
            }
        final String    hashString  = (String)dfURL.getProperty( "HashCode" );

        // Identify file content to generate extension
        FileDataTypeDescription type;
        String                  extension;

        try {
            type = FileDataTypes.findDataTypeDescription( file );
            }
        catch( final IOException e ) {
            type = null;
            }

        if( type != null ) {
            extension = type.getExtension();
            }
        else {
            extension = ".xxx";
            }

        // Create new file name
        final File ffile = new File(
                this.destinationDirectoryFile,
                hashString + extension
                );

        // Rename file
        final boolean isRename = file.renameTo( ffile );

        if( isRename ) {
            // Set new name for this file.
            dfURL.setResult( ffile );

            // Notify
            this.loggerListener.downloadStored( dfURL );

            // Add to cache
            final Date date = new Date(); // TODO get date of end of download ?
            this.cache.add( dfURL.getURI(), date, hashString, ffile.getName() );
            }
        else {
            File file2;

            if( ffile.exists() ) {
                file2 = new File( this.destinationDirectoryFile, file.getName() + '.' + hashString + extension + ".exists" );
                }
            else {
                file2 = new File( this.destinationDirectoryFile, file.getName() + '.' + hashString + extension + "tmp" );
                }

            if( file.renameTo( file2 ) ) {
                // Rename to something better
                dfURL.setResult( file2 );

                this.loggerListener.downloadCantRename( dfURL, file, file2 );
                }
            else {
                // Can not rename at all
                this.loggerListener.downloadCantRename( dfURL, file, ffile );
                }
            }
    }

    // is file valid according to constrains
    private boolean isFileValidAccordingToConstraints(
        final ContentDownloadURI<File> dfURL
        )
    {
        final Dimension dimension   = (Dimension)dfURL.getProperty( DefaultFilterInputStreamBuilder.DIMENSION );
        final String    formatName  = (String)dfURL.getProperty( DefaultFilterInputStreamBuilder.FORMAT_NAME );

        if( dimension != null ) {
            if( (dimension.width < 100) || (dimension.height < 100) ) {
                return false;
                }

            if( (dimension.getWidth() * dimension.getHeight()) < (450 * 450) ) {
                return false;
                }
            }

        return true;
    }

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
