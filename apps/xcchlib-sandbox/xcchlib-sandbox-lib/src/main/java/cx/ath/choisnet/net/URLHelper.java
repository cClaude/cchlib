package cx.ath.choisnet.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import com.googlecode.cchlib.io.IOHelper;

/**
 *
 * @since 2.02
 */
@SuppressWarnings({"null","resource"})
public class URLHelper
{
    private int          connectRetryCount;
    private long         delaisBeforeRetry;
    private Proxy        proxy;
    private final Object lock = new Object();

    public interface Status
    {
        public String getContentType();
    }

    private class StatusImpl implements Status
    {
        private String  contentType;
        private Integer contentLength;
        private Long    date;

        public StatusImpl()
        {
            // empty
        }

        @Override
        public String getContentType()
        {
            return this.contentType;
        }

        public void setContentType( final String contentType )
        {
            this.contentType = contentType;
        }

//         public int getContentLength() //- - - - - - - - - - - - - - - - - -
//         {
//         return this.contentLength != null ? this.contentLength.intValue() : -1;
//         }

        public void setContentLength( final int contentLength ) // - - - - -
        {
            this.contentLength = Integer.valueOf( contentLength );
        }

//        public long getDate() // - - - - - - - - - - - - - - - - - - - - - -
//        {
//            return this.contentLength != null ? this.contentLength.intValue() : -1;
//        }

        public void setDate( final long date )
        {
            this.date = Long.valueOf( date );
        }

        public void init( final URLConnection urlConnection )
        {
            setContentType( urlConnection.getContentType() );
            setContentLength( urlConnection.getContentLength() );
            setDate( urlConnection.getDate() );
        }

        @Override
        public String toString()
        {
            return "("
                    + this.contentType + ","
                    + this.contentLength
                    + "," + new Date( this.date.longValue() )
                    + ")";
        }
    }

    public URLHelper()
    {
        this.connectRetryCount = 0;
        this.delaisBeforeRetry = 0;
        this.proxy             = null;
    }

    public URLHelper setConnectRetryCount( final int connectRetryCount )
    {
        this.connectRetryCount = connectRetryCount > 0 ? connectRetryCount : 1;

        return this;
    }

    public URLHelper setDelaisBeforeRetry( final long delaisBeforeRetry )
    {
        this.delaisBeforeRetry = delaisBeforeRetry;

        return this;
    }

    public URLHelper setProxy( final Proxy proxy )
    {
        this.proxy = proxy;

        return this;
    }

    /**
     * @param timeout
     *            the maximum time to wait in milliseconds.
     *
     * @since 3.02.021
     */
    private void sleep( final long timeout )
    {
        synchronized( this.lock ) {
            try {
                this.lock.wait( timeout );
            }
            catch( final InterruptedException ignore ) {
                // ignore
            }
        }
    }

    public Status download(
        final URL          url,
        final OutputStream output
        ) throws IOException
    {
        final StatusImpl status = new StatusImpl();

        int         count = this.connectRetryCount;
        InputStream input = null;

        for( ;; ) {
            try {
                //
                // input = url.openStream();
                //
                final URLConnection conn;

                if( this.proxy == null ) {
                    conn = url.openConnection();
                } else {
                    conn = url.openConnection( this.proxy );
                }

                // System.out.println( "getContentEncoding() = " + conn.getContentEncoding() );
                // System.out.println( " getHeaderFields() = " + conn.getHeaderFields() );
                // System.out.println( "getContentLength() = " + conn.getContentLength() );
                // System.out.println( "  getContentType() = " + conn.getContentType() );
                // System.out.println( "         getDate() = " + conn.getDate() );

                input = conn.getInputStream();

                status.init( conn );

                break;
            }
            catch( final ConnectException e ) {
                if( --count < 0 ) {
                    throw e;
                }
            }

            sleep( this.delaisBeforeRetry );
        }

        try {
            IOHelper.copy( input, output );
        }
        finally {
            input.close();
        }

        return status;
    }

    /**
     * Copy {@code url} content to file,
     *
     * @param url
     *            URL to copy
     * @param outputFile
     *            File to create with {@code url} content, if URL is found.
     *
     * @since 3.02.021
     */
    public Status download(
        final URL  url,
        final File outputFile
        ) throws FileNotFoundException, IOException
    {
        Status status;

        try( final OutputStream output = IOHelper.newBufferedOutputStream( outputFile ) ) {
            status = download( url, output );
        }
        catch( final FileNotFoundException urlNotFound ) {
            outputFile.delete();

            throw urlNotFound;
        }

        return status;
    }
}
