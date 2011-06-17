package samples.downloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private final MessageDigestFile mdf;
    private final URLCache cache = new URLCache();

    private final File  tempDirectoryFile;
    private final File  destinationDirectoryFile;
    private final int   downloadMaxThread;

    /**
     *
     * @param tempDirectoryFile
     * @param destinationDirectoryFile
     * @param downloadMaxThread
     * @throws NoSuchAlgorithmException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public GenericDownloader(
            final File  tempDirectoryFile,
            final File  destinationDirectoryFile,
            final int   downloadMaxThread
            )
        throws NoSuchAlgorithmException, IOException, ClassNotFoundException
    {
        this.tempDirectoryFile = tempDirectoryFile;
        this.destinationDirectoryFile = destinationDirectoryFile;
        this.downloadMaxThread = downloadMaxThread;

        this.cache.setCacheFile( ( new File( destinationDirectoryFile, ".cache" ) ) );

        this.mdf = new MessageDigestFile();

        try  {
            this.cache.load();
            }
        catch( FileNotFoundException ignore ) {
            println( "* warn: cache file not found - " + this.cache.getCacheFile() );
            }
    }

    protected abstract void println( String msg );
    protected abstract Collection<URL> collectURL() throws IOException;

    protected List<String> loads( Collection<URL> urls ) throws IOException
    {
        final List<String>      result              = new ArrayList<String>();
        final DownloadExecutor  downloadExecutor    = new DownloadExecutor( downloadMaxThread );

        DownloadStringEvent eventHandler = new DownloadStringEvent()
        {
            @Override
            public void downloadStart( final URL url )
            {
                println( "Start downloading: " + url );
            }
            @Override
            public void downloadDone( final URL url, final String content )
            {
                result.add( content );
            }
            @Override
            public void downloadFail( final URL url, final Throwable cause )
            {
                // Improve: retry ? add count ?
                // Runnable command = new DownloadToString( this, url );
                // downloadExecutor.execute( command );
            }
        };

        downloadExecutor.add( eventHandler, urls  );
        downloadExecutor.waitClose();

        return result;
    }

    void downloadAll() throws IOException
    {
        final Collection<URL>   urls                = collectURL();
        final DownloadExecutor  downloadExecutor    = new DownloadExecutor( downloadMaxThread );

        DownloadFileEvent eventHandler = new DownloadFileEvent()
        {
            @Override
            public File downloadStart( URL url ) throws IOException
            {
                println( "Start downloading: " + url );

                return File.createTempFile( "pic", null, tempDirectoryFile );
            }
            @Override
            public void downloadFail( URL url, Throwable cause )
            {
                // Improve: retry ? add count ?
                // Runnable command = new DownloadToFile( this, url );
                // downloadExecutor.execute( command );
            }
            @Override
            public void downloadDone( URL url, File file )
            {
                try {
                    // Compute MD5 hash ode
                    byte[] digestKey        = mdf.compute( file );
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
                        println( "new file > " + ffile );
                        cache.add( url, ffile.getName() );
                        }
                    else {
                        println( "*** already exists ? " + ffile );
                        }                    }
                catch( FileNotFoundException e ) {
                    // Should not occur
                    e.printStackTrace();
                    }
                catch( IOException e ) {
                    // Should not occur
                    e.printStackTrace();
                    }
            }
        };

        for( URL u: urls ) {
            if( cache.isInCache( u ) ) {
                // skip this entry !
                println( "Already loaded: " + u );
                }
            else {
                downloadExecutor.addDownload( eventHandler, u );
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