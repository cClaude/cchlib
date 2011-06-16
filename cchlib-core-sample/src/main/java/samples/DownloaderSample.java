package samples;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import samples.DownloaderSample.DataTypeFinder.DataTypeDescription;
import cx.ath.choisnet.io.SerializableHelper;
import cx.ath.choisnet.net.URLHelper;
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
    private Object      lock = new Object();
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
        final List<String>      result  = new ArrayList<String>();
        BlockingQueue<Runnable> queue   = new LinkedBlockingDeque<Runnable>();

        for( URL u: this.htmlURLList ) {
            queue.add(  new DownloadURLToString( u, result ) );
            }

        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                0, // min thread
                downloadMaxThread, // max thread
                0, // keepAliveTime
                TimeUnit.MILLISECONDS,
                queue
                );
        pool.setCorePoolSize( downloadMaxThread );

        println( "downloadMaxThread = " + downloadMaxThread );

        pool.execute( new Runnable()
        {
            @Override
            public void run()
            {
                // Done
                synchronized( lock ) {
                    lock.notify();
                }
            }
        });

        // Wait until done
        synchronized( lock ) {
            try {
                lock.wait();
                }
            catch( InterruptedException e ) {
                e.printStackTrace();
                }
        }

        return result;
    }

    private class DownloadURLToString implements Runnable
    {
        private final URL             url;
        private final List<String>    postResult;

        DownloadURLToString( final URL url, final List<String> postResult)
        {
            this.url        = url;
            this.postResult = postResult;
        }

        @Override
        public void run()
        {
            CharArrayWriter buffer = new CharArrayWriter();

            println( "Start downloading: " + url );

            try {
                URLHelper.copy( url, buffer );

                postResult.add( buffer.toString() );
                }
            catch( IOException e ) {
                // TODO : try again ?
                e.printStackTrace();
                }
        }
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
        Collection<URL>         urls    = collectURL();
        BlockingQueue<Runnable> queue   = new LinkedBlockingDeque<Runnable>();

        for( URL u: urls ) {
            if( cache.isInCache( u ) ) {
                // skip this entry !
                println( "Already loaded: " + u );
                }
            else {
                queue.add( new DownloadToFile( u ) );
                cache.add( u );
                }
            }
        ThreadFactory threadFactory = new ThreadFactory()
        {
            @Override
            public Thread newThread( Runnable arg0 )
            {
                // TODO Auto-generated method stub
                return null;
            }
            
        };
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                downloadMaxThread, // min thread
                downloadMaxThread, // max thread
                0, // keepAliveTime
                TimeUnit.MILLISECONDS,
                queue,
                threadFactory
                );
        pool.setCorePoolSize( downloadMaxThread );

        println( "downloadMaxThread = " + downloadMaxThread );

        pool.execute( new Runnable()
        {
            @Override
            public void run()
            {
                synchronized( lock ) {
                    lock.notify();
                }
            }
        });

        synchronized( lock ) {
            try {
                lock.wait();
                }
            catch( InterruptedException e ) {
                e.printStackTrace();
                }
        }

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

    private class DownloadToFile implements Runnable
    {
        private final URL url;

        DownloadToFile( URL url )
        {
            this.url = url;
        }

        @Override
        public void run()
        {
            try {
                download();
                }
            catch( IOException e ) {
                // TODO : try again ?
                e.printStackTrace();
                }
        }

        public void download() throws IOException
        {
            final File file = getTmpFile();

            println( "Downloading " + url );

            URLHelper.copy( url, file );

            byte[] digestKey        = mdf.compute( file );
            String hashCodeString   = MessageDigestFile.computeDigestKeyString( digestKey );

            DataTypeDescription type = DataTypeFinder.findDataTypeDescription( file );
            String              extension = null;

            if( type != null ) {
                extension = type.getExtension();
                }
            else {
                extension = ".xxx";
            }

            File ffile = new File( destinationDirectoryFile, hashCodeString + extension );

            boolean isRename = file.renameTo( ffile );

            if( isRename ) {
                println( "new file > " + ffile );
                }
            else {
                println( "*** already exists ? " + ffile );
                }
        }

        public File getTmpFile() throws IOException
        {
            return File.createTempFile( "pic", null, tempDirectoryFile );
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

    public static class URLCache implements Serializable
    {
        private static final long serialVersionUID = 1L;
        // Workaround for generic warning...
        private class SetOfURL extends HashSet<URL>
        {
            private static final long serialVersionUID = 1L;
        };
        private SetOfURL cache;
        private File cacheFile;

        public URLCache()
        {
            cache = new SetOfURL();
        }

        public URLCache( File cacheFile )
        {
            setCacheFile( cacheFile );
            try {
                load();
                }
            catch( Exception e ) {
                cache = new SetOfURL();
                }
        }

        public boolean isInCache( URL url )
        {
            return cache.contains( url );
        }

        public void add( URL url )
        {
            cache.add( url );
        }

        public void clear()
        {
            cache.clear();
        }

        public void setCacheFile( File cacheFile )
        {
            this.cacheFile = cacheFile;
        }

        public File getCacheFile()
        {
            return this.cacheFile;
        }

        /**
         *
         * @throws FileNotFoundException if cache does not exist
         * @throws IOException
         * @throws ClassNotFoundException
         */
        public void load() throws FileNotFoundException, IOException, ClassNotFoundException
        {
            cache = SerializableHelper.loadObject( cacheFile, cache.getClass() );
        }

        public void store() throws IOException
        {
            SerializableHelper.toFile( cache, cacheFile );
        }

    }

    public static class DataTypeFinder
    {
        public enum DataType
        {
            JPEG, PNG, GIF
        }
        public interface DataTypeDescription
        {
            public String getExtension();
            public String getShortExtension();
            public DataType getDataType();
        }

        public static DataTypeDescription findDataTypeDescription( File file )
            throws FileNotFoundException, IOException
        {
            FileInputStream     fis = new FileInputStream( file );
            byte[]              b   = new byte[ 16 ];

            int len = fis.read( b );
            fis.close();

            if( len > 0 ) {
                if( b[ 6 ] == 0x4A && b[ 7 ] == 0x46 && b[ 8 ] == 0x49 && b[ 9 ] == 0x46 ) {
                    return new DataTypeDescription()
                    {
                        @Override
                        public String getExtension()
                        {
                            return ".jpeg";
                        }
                        @Override
                        public String getShortExtension()
                        {
                            return ".jpg";
                        }
                        @Override
                        public DataType getDataType()
                        {
                            return DataType.JPEG;
                        }
                    };
                }
                if( b[ 1 ] == 0x50 && b[ 2 ] == 0x4E && b[ 3] == 0x47 ) {
                    return new DataTypeDescription()
                    {
                        @Override
                        public String getExtension()
                        {
                            return ".png";
                        }
                        @Override
                        public String getShortExtension()
                        {
                            return getExtension();
                        }
                        @Override
                        public DataType getDataType()
                        {
                            return DataType.PNG;
                        }
                    };
                }
                if( b[ 0 ] == 0x47 && b[ 1 ] == 0x49 && b[ 2 ] == 0x46 ) {
                    return new DataTypeDescription()
                    {
                        @Override
                        public String getExtension()
                        {
                            return ".gif";
                        }
                        @Override
                        public String getShortExtension()
                        {
                            return getExtension();
                        }
                        @Override
                        public DataType getDataType()
                        {
                            return DataType.GIF;
                        }
                    };
                }
            }
            return null;

        }
    }
}

