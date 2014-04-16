/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/net/URLHelper.java
** Description   :
**
**  2.02.040 2006.01.06 Claude CHOISNET - Version initiale
**                      Bas� sur cx.ath.choisnet.net.NetTools
**  3.02.021 2006.01.06 Claude CHOISNET - Version initiale
**                      Ajout des m�thodes: setConnectRetryCount(int),
**                      setDelaisBeforeRetry(long), sleep(long),
**                      download(URL,OutputStream), download(URL,File),
**  3.02.022 2006.07.04 Claude CHOISNET - Version initiale
**                      Ajout de la m�thode: setProxy(Proxy)
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.net.URLHelper
**
*/
package cx.ath.choisnet.net;

import cx.ath.choisnet.io.InputStreamHelper;
import cx.ath.choisnet.io.ReaderHelper;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

/**
**
** @author Claude CHOISNET
** @since   2.02.040
** @version 3.02.022
*/
public class URLHelper
{
/** */
private int connectRetryCount;

/** */
private long delaisBeforeRetry ;

/** */
private Proxy proxy;

/** */
private Object lock = new Object();

    /**
    **
    */
    public interface Status
    {
        /**
        **
        */
        public String getContentType();

    } // interface

    /**
    **
    */
    private class StatusImpl implements Status
    {
        /** */
        private String contentType;

        /** */
        private Integer contentLength;

        /** */
        private Long date;

        /**
        **
        */
        public StatusImpl() //- - - - - - - - - - - - - - - - - - - - - - -
        {
            // empty
        }

        /**
        **
        */
        @Override
        public String getContentType() // - - - - - - - - - - - - - - - - -
        {
            return this.contentType;
        }

        /**
        **
        */
        public void setContentType( final String contentType ) // - - - - -
        {
            this.contentType = contentType;
        }

        /**
        **
        */
        public int getContentLength() //- - - - - - - - - - - - - - - - - -
        {
            return this.contentLength != null ? this.contentLength.intValue() : -1;
        }

        /**
        **
        */
        public void setContentLength( final int contentLength ) //- - - - -
        {
            this.contentLength = new Integer( contentLength );
        }

        /**
        **
        */
        public long getDate() //- - - - - - - - - - - - - - - - - - - - - -
        {
            return this.contentLength != null ? this.contentLength.intValue() : -1;
        }

        /**
        **
        */
        public void setDate( final long date ) // - - - - - - - - - - - - -
        {
            this.date = new Long( date );
        }

        /**
        **
        */
        public void init( //- - - - - - - - - - - - - - - - - - - - - - - -
            final URLConnection urlConnection
            )
        {
            setContentType( urlConnection.getContentType() );
            setContentLength( urlConnection.getContentLength() );
            setDate( urlConnection.getDate() );
        }

        /**
        **
        */
        @Override
        public String toString() // - - - - - - - - - - - - - - - - - - - -
        {
            return "("
                + this.contentType
                + "," + this.contentLength
                + "," + new java.util.Date( this.date )
                + ")";
        }


    } // class

/**
**
** @since 3.02.021
*/
public URLHelper() // -----------------------------------------------------
{
 this.connectRetryCount = 0;
 this.delaisBeforeRetry = 0;
 this.proxy             = null;
}

/**
**
** @since 3.02.021
*/
public URLHelper setConnectRetryCount( final int connectRetryCount ) // ---
{
 this.connectRetryCount = connectRetryCount > 0 ? connectRetryCount : 1;

 return this;
}

/**
**
** @since 3.02.021
*/
public URLHelper setDelaisBeforeRetry( final long delaisBeforeRetry ) // --
{
 this.delaisBeforeRetry = delaisBeforeRetry;

 return this;
}

/**
**
** @since 3.02.022
*/
public URLHelper setProxy( final Proxy proxy ) // -------------------------
{
 this.proxy = proxy;

 return this;
}

/**
** @param timeout the maximum time to wait in milliseconds.
**
** @since 3.02.021
*/
private void sleep( final long timeout ) // -------------------------------
{
 synchronized( this.lock ) {
    try {
        this.lock.wait( timeout );
        }
    catch( InterruptedException ignore ) {
        // ignore
        }
    }
}

/**
**
**
**
**
** @since 3.02.021
*/
public Status download( // ------------------------------------------------
    final URL          url,
    final OutputStream output
    )
    throws java.io.IOException
{
 final StatusImpl   status  = new StatusImpl();
 int                count   = this.connectRetryCount;
 InputStream        input   = null;

 for(;;) {
    try {
        //
        // input = url.openStream();
        //
        final URLConnection conn;

        if( this.proxy == null ) {
            conn = url.openConnection();
            }
        else {
            conn = url.openConnection( proxy );
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
    catch( java.net.ConnectException e ) {
        if( --count < 0 ) {
            throw e;
            }
        }

    sleep( this.delaisBeforeRetry );
    }

 try {
    InputStreamHelper.copy( input, output );
    }
 finally {
    input.close();
    }

 return status;
}

/**
** Copy url content to file,
**
** @param url           URL to copy
** @param outputFile    File to create with url content, if URL is found.
**
** @since 3.02.021
*/
public Status download( // ------------------------------------------------
    final URL   url,
    final File  outputFile
    )
    throws
        java.io.FileNotFoundException,
        java.io.IOException
{
 final OutputStream output = new BufferedOutputStream(
                                new FileOutputStream( outputFile )
                                );

 Status status;

 try {
    status = download( url, output );
    }
 catch( java.io.FileNotFoundException urlNotFound ) {

    try {
        output.close();
        }
    catch( Exception ignore ) {
        // Just ignore it !
        }

    outputFile.delete();

    throw urlNotFound;
    }
 finally {
    output.close();
    }

 return status;
}

/**
** Copy url content to file,
**
** @param url           URL to copy
** @param outputFile    File to create with url content, if URL is found.
**
**
*/
public static void copy( // -----------------------------------------------
    final URL   url,
    final File  outputFile
    )
    throws
        java.io.FileNotFoundException,
        java.io.IOException
{
 final OutputStream output = new BufferedOutputStream(
                                new FileOutputStream( outputFile )
                                );

 try {
    copy( url, output );
    }
 catch( java.io.FileNotFoundException urlNotFound ) {

    try {
        output.close();
        }
    catch( Exception ignore ) {
        // Just ignore it !
        }

    outputFile.delete();

    throw urlNotFound;
    }
 finally {
    output.close();
    }
}

/**
** Transforme une URL en son contenu
**
** @param url where datas will be read.
**
** @return a String filled with datas received from the giving URL
**
*/
public static String toString( final URL url ) // -------------------------
    throws java.io.IOException
{
 return InputStreamHelper.toString( url.openStream() );
}

/**
**
**
**
*/
public static void copy( // -----------------------------------------------
    final  URL          url,
    final  OutputStream output
    )
    throws java.io.IOException
{
 final InputStream input = url.openStream();

 try {
    InputStreamHelper.copy( input, output );
    }
 finally {
    input.close();
    }
}


/**
**
**
**
*/
public static void copy( // -----------------------------------------------
    final URL       url,
    final Writer    output
    )
    throws java.io.IOException
{
 final Reader input = new BufferedReader(
                    new InputStreamReader( url.openStream() )
                    );

 try {
    ReaderHelper.copy( input, output );
    }
 finally {
    input.close();
    }
}

/**
**
**
**
*/
public static void copy( // -----------------------------------------------
    final URL       url,
    final Writer    output,
    final String    charsetName
    ) // --------------------
    throws
        java.io.UnsupportedEncodingException,
        java.io.IOException
{
 final Reader input = new BufferedReader(
                    new InputStreamReader( url.openStream(), charsetName )
                    );

 try {
    ReaderHelper.copy( input, output );
    }
 finally {
    input.close();
    }
}



/**
**
**
** .javaProxy cx.ath.choisnet.net.URLHelper
*/
public final static void main( final String[] argS ) // -------------------
    throws
        java.net.MalformedURLException,
        java.io.FileNotFoundException,
        java.io.IOException
{
//
// http://www.humourhumour.com/galerie/49/image_2450.jpg
// http://www.humourhumour.com/galerie/87/image_2446.jpg
// http://www.humourhumour.com/galerie/85/image_2445.jpg
// http://www.humourhumour.com/galerie/40/image_2444.jpg
//
//
//

 final String[] args = {
    // "http://video.zone-humour.com/videos_serie1/{1}.{0}",
    "http://www.humourhumour.com/galerie/49/image_{1,number,####}.{0}",
    "C:/news",
    "1000",
    "9999",
    "1"
    };
 final int        pictures          = 0;
 final int        videos            = 1;
 final String[][] commonsExtentions = {
            { "gif", "png", "jpg", "jpeg", "jpe", "tif", "bmp" }, // pictures
            { "asf", "avi", "mov", "mpeg", "mpe", "mpg", "wmv" }  // videos
            };
 final String[] extentions = commonsExtentions[ pictures ];

 final java.text.MessageFormat  msgfmt  = new java.text.MessageFormat( args[ 0 ] );
 final File                     dirFile = new File( args[ 1 ] );
 final int                      from    = Integer.parseInt( args[ 2 ] );
 final int                      to      = Integer.parseInt( args[ 3 ] );
 final int                      step    = Integer.parseInt( args[ 4 ] );
 final Object[]                 params  = new Object[ 2 ];

 final URLHelper urlHelper = new URLHelper()
                                .setConnectRetryCount( 3 )
                                .setDelaisBeforeRetry( 5000 );

 for( int i = from; i<=to; i += step ) {
    params[ 1 ] = new Integer( i );

    for( String ext : extentions ) {
        params[ 0 ] = ext;

        final String  str   = msgfmt.format( params );
        final File    file  = new File( dirFile, str.substring( str.lastIndexOf( '/' ) + 1 ) );

        if( file.exists() ) {
            System.out.println( "*** file '" + file + "' existe ! skipped..." );
            }
        else {
            final URL url = new URL( str );

            System.out.println( "url =" + url );

            try {
                Status status = urlHelper.download( url, file );

                System.out.println( "Status = " + status );

                boolean deleteFile = "text/html".equals( status.getContentType() );

//
//                boolean deleteFile = file.length() == 0;
//
//                if( "text/html".equals( status.getContentType() ) ) {
//                    deleteFile = true;
//                    }

                if( deleteFile ) {
                    file.delete();

                    System.out.println( "*** file '" + file + "' BAD STATUS ! deleted..." );
                    }
                else {
                    System.out.println( "file '" + file + "' OK" );
                    }
                }
            catch( java.io.FileNotFoundException e ) {
                System.out.println( "### url '" + url + "' NotFound - skipped..." );
                }
            }
        }
    }

}


} // class
