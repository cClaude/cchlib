package samples.downloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.filetype.FileDataTypeDescription;
import com.googlecode.cchlib.io.filetype.FileDataTypes;
import com.googlecode.cchlib.net.download.DownloadEvent;
import com.googlecode.cchlib.net.download.DownloadExecutor;
import com.googlecode.cchlib.net.download.DownloadFileEvent;
import com.googlecode.cchlib.net.download.DownloadIOException;
import com.googlecode.cchlib.net.download.DownloadURL;
import com.googlecode.cchlib.net.download.FileDownloadURL;
import com.googlecode.cchlib.net.download.MD5FilterInputStreamBuilder;
import com.googlecode.cchlib.net.download.StringDownloadURL;
import com.googlecode.cchlib.net.download.cache.URLCache;

/**
 *
 *
 */
public /*abstract*/ class GenericDownloader
{
    private final Logger logger = Logger.getLogger( GenericDownloader.class );
    private final URLCache cache;
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

logger.setLevel( Level.INFO ); // FIXME: remove this

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

        gdauir.getAbstractLogger().info( "destinationDirectoryFile = " + destinationDirectoryFile );

        final File cacheIndexFile = new File( rootCacheDirectoryFile, ".cache" );

        this.cache = new URLCache( destinationDirectoryFile, cacheIndexFile );
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
     * Returns an {@link Iterable} object of {@link FileDownloadURL}s to download,
     * must be implement by parent class.
     * @return an {@link Iterable} object of {@link FileDownloadURL}s to download
     * @throws IOException
     */
    final
    protected Collection<FileDownloadURL> collectDownloadURLs() throws IOException
    {
        final Collection<FileDownloadURL>   urls        = new HashSet<FileDownloadURL>();
        final List<String>                  contentList = loads( gdai.getURLDownloadAndParseCollection() );

        for( String pageContent : contentList ) {
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
     */
    protected List<String> loads( final Collection<StringDownloadURL> urls ) throws IOException
    {
        final List<String>      result              = new ArrayList<String>();
        final DownloadExecutor  downloadExecutor    = new DownloadExecutor( downloadMaxThread, null );

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

        downloadExecutor.add( urls, eventStringHandler );
        downloadExecutor.waitClose();

        return result;
    }

    /**
     *
     * @throws IOException
     */
    void startDownload() throws IOException
    {
        final Collection<FileDownloadURL>   urls                = collectDownloadURLs();
        final DownloadExecutor              downloadExecutor    = new DownloadExecutor( 
                downloadMaxThread, 
                new MD5FilterInputStreamBuilder() 
                );

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
                final File      file        = dURL.getResultAsFile();
                final String    hashString  = dURL.getContentHashCode();
                
//                try {
//                    // Compute MD5 hash code
//                    MessageDigestFile   mdf         = new MessageDigestFile();
//                    byte[]              digestKey   = mdf.compute( file );
//
//                    String hashCodeString = MessageDigestFile.computeDigestKeyString( digestKey );
//
//                    Assert.assertEquals( hashString, hashCodeString );
                    URL u = cache.findURL( hashString );
                    
                    if( u != null ) {
                        // Already downloaded
                        alreadyDownloaded( file, dURL );
                        }
                    else {
                        // New file
                        newFileDownloaded( file, dURL, hashString );
                        }
//                    }
//                catch( FileNotFoundException e ) {
//                    // Should not occur
//                    logger.error( "downloadDone (Should not occur):", e );
//                    }
//                catch( IOException e ) {
//                    // Should not occur
//                    logger.error( "downloadDone (Should not occur):", e );
//                    }
//                catch( NoSuchAlgorithmException e ) {
//                    // Should not occur
//                    logger.error( "downloadDone (Should not occur):", e );
//                    }
//                finally {
//                    if( file != null && file.isFile() ) {
//                        // Occur when file is load twice, using same URL or not.
//                        // FIXME
//                        // Delete ? rename ??
//                        logger.info( "downloadDone: file not deleted ! ? : " + file );
//                        }
//                    }

                updateDisplay();
            }
            
            private void alreadyDownloaded( File file, DownloadURL dURL )
            {
            	// Remove this file !
                file.delete();
                
                if( logger.isTraceEnabled() ) {
                    logger.trace( "Already downloaded (deleted): " + file );
                    }
            }
            
            private void newFileDownloaded(File file, DownloadURL dURL, String hashCodeString ) 
                        //throws FileNotFoundException, IOException
            {
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
                File ffile = new File( destinationDirectoryFile, hashCodeString + extension );

                // Rename file
                boolean isRename = file.renameTo( ffile );

                if( isRename ) {
                    dURL.setResultAsFile( ffile );
                    loggerListener.downloadStored( dURL );
                    Date date = new Date(); // TODO get date of end of download ?
                    cache.add( dURL.getURL(), date, hashCodeString, ffile.getName() );
                    }
                else {
                    File file2;

                    if( ffile.exists() ) {
                        file2 = new File( destinationDirectoryFile, file.getName() + '.' + hashCodeString + extension + ".exists" );
                        }
                    else {
                        file2 = new File( destinationDirectoryFile, file.getName() + '.' + hashCodeString + extension + "tmp" );
                        }

                    if( file.renameTo( file2 ) ) {
                        loggerListener.downloadCantRename( dURL, file, file2 );
                        }
                    else {
                        loggerListener.downloadCantRename( dURL, file, ffile );
                        }
                    }
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

        for( FileDownloadURL du: urls ) {
            if( cache.isInCacheIndex( du.getURL() ) ) {
            //if( cache.isInCache( u ) ) {
                // skip this entry !
                loggerListener.info( "Already in cache (Skip): " + du.getURL() );
                }
            else {
                //DownloadURL du = new FileDownloadURL( u, requestPropertyMap, proxy );
                downloadExecutor.addDownload( du, eventHandler );
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

    public void stopDownload()
    {
        // TODO Auto-generated method stub
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
        logger.info( "stopDownload() not implemented !" );
    }



}
