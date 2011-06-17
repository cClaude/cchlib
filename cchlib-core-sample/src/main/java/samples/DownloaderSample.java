package samples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.googlecode.cchlib.io.filetype.FileDataTypeDescription;
import com.googlecode.cchlib.io.filetype.FileDataTypes;
import com.googlecode.cchlib.net.download.DownloadExecutor;
import com.googlecode.cchlib.net.download.DownloadFileEvent;
import com.googlecode.cchlib.net.download.DownloadStringEvent;
import com.googlecode.cchlib.net.download.DownloadToFile;
import com.googlecode.cchlib.net.download.DownloadToString;
import com.googlecode.cchlib.net.download.URLCache;
import cx.ath.choisnet.util.checksum.MessageDigestFile;

/**
 *
 *
 *
 */
public class DownloaderSample
{
    private final static String serverRootURLString = "http://www.bloggif.com";
    private final static String htmlURLBase         = serverRootURLString + "/creations?page=";

    private List<URL>   htmlURLList = new ArrayList<URL>();
    private MessageDigestFile mdf;
    private File        tempDirectoryFile;
    private File        destinationDirectoryFile;

    private URLCache    cache = new URLCache();
    private int         downloadMaxThread = 5;

    /**
     *
     * @param destinationFolderFile
     * @throws NoSuchAlgorithmException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public DownloaderSample( final File destinationFolderFile )
        throws NoSuchAlgorithmException, IOException, ClassNotFoundException
    {
        this.mdf = new MessageDigestFile();

        this.cache.setCacheFile( ( new File( destinationFolderFile, ".cache" ) ) );
        try  {
            this.cache.load();
            }
        catch( FileNotFoundException ignore ) {
            println( "* warn: cache file not found - " + this.cache.getCacheFile() );
            }

        for( int i=1; i<23; i++ ) {
            this.htmlURLList.add( new URL( htmlURLBase + i ) );
            }

        destinationDirectoryFile    = destinationFolderFile;
        tempDirectoryFile           = destinationDirectoryFile;
    }

    private List<String> loads() throws IOException
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
                // TODO retry ? add count ?
                Runnable command = new DownloadToString( this, url );
                //pool.execute( command );
                downloadExecutor.execute( command );
            }
        };

        downloadExecutor.add( eventHandler, htmlURLList );
        downloadExecutor.waitCompleted();

        return result;
    }

    private Collection<URL> collectURL() throws IOException
    {
        String allContent;
        {
            List<String>    contentList = loads();
            StringBuilder   sb          = new StringBuilder();

            for( String s: contentList ) {
                sb.append( s );
                }

            allContent = sb.toString();
            contentList.clear();
            sb.setLength( 0 );
        }

        final String[] regexps = {
            "<img class=\"img_progress ...\" src=\"",
            "<img class=\"img_progress ....\" src=\""
            };

        Set<URL> imagesURLCollection = new HashSet<URL>();

        for( String regexp : regexps ) {
            String[] strs = allContent.toString().split( regexp );
            println( "> img founds = " + (strs.length - 1));

            for( int i=1; i<strs.length; i++ ) {
                String  s   = strs[ i ];
                int     end = s.indexOf( '"' );
                String  src = s.substring( 0, end );

                imagesURLCollection.add( new URL( serverRootURLString + src ) );
                }
            }

        println( "> URL founds = " + imagesURLCollection.size() );

        return imagesURLCollection;
    }

    void downloadAll() throws IOException
    {
        final Collection<URL>   urls                = collectURL();
        final DownloadExecutor  downloadExecutor    = new DownloadExecutor( downloadMaxThread );

        DownloadFileEvent eventHandler = new DownloadFileEvent()
        {
            @Override
            public void downloadStart( URL url )
            {
                println( "Start downloading: " + url );
            }
            @Override
            public void downloadFail( URL url, Throwable cause )
            {
                // TODO retry ? add count ?
                Runnable command = new DownloadToFile( this, url );
                downloadExecutor.execute( command );
            }
            @Override
            public File createTempFile( URL url ) throws IOException
            {
                return File.createTempFile( "pic", null, tempDirectoryFile );
            }
            @Override
            public void downloadDone( URL url, File file )
            {
                try {
                    // Compute MD5 hashc ode
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

        downloadExecutor.waitCompleted();
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

    protected void println( String s )
    {
        System.out.println( s );
    }

    /**
     * Start Sample here !
     */
    public static void main( String...args )
        throws IOException, NoSuchAlgorithmException, ClassNotFoundException
    {
        File destinationFolderFile = new File( new File(".").getAbsoluteFile(), "output" ).getCanonicalFile();
        destinationFolderFile.mkdirs();

        DownloaderSample instance = new DownloaderSample( destinationFolderFile );

        instance.println( "destinationFolderFile = " + destinationFolderFile );
        instance.downloadAll();
        instance.println( "done" );
    }
}
