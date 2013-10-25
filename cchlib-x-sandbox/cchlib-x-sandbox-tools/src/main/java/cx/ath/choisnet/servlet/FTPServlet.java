/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/FTPServlet.java
** Description   :
** Encodage      : ANSI
**
**  1.00 2005.08.14 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.FTPServlet
**
*/
package cx.ath.choisnet.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
** <P><B>Servlet :</B> </P>
**
**
** <B>Configuration :</B><BR>
**
** <XMP>
** </XMP>
**
**
** @author Claude CHOISNET
** @version 1.00
*/
public class FTPServlet
    extends HttpServlet
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
** Gestion des traces
*/
final org.apache.commons.logging.Log logger
    = org.apache.commons.logging.LogFactory.getLog( this.getClass() );

/**
**
*/
public static final String ROOT_FOLDER = "ROOT_FOLDER";

/**
**
*/
private File rootCanonicalFolderFile;

/**
**
*/
private String rootCanonicalFolder;

/**
**
*/
private int rootCanonicalFolderLength;

// final
// private String URLPrefix = "/Tools/FTP";

/**
**
public void init() throws ServletException // -----------------------------
{
 super.init();
}
*/

/**
**
*/
@Override
public void init( ServletConfig servletConfig ) // ------------------------
    throws javax.servlet.ServletException
{
 super.init( servletConfig );

 String value = servletConfig.getInitParameter( ROOT_FOLDER );

 try {
    File file = new File( value );

    this.rootCanonicalFolderFile = file.getCanonicalFile();

    if( ! this.rootCanonicalFolderFile.isDirectory() ) {
        final String msg = "init() - Not valid value for '" + ROOT_FOLDER
                                + "' : '" + this.rootCanonicalFolderFile + "'";

        logger.error( msg );

        throw new javax.servlet.ServletException( msg );
        }
    }
 catch( javax.servlet.ServletException e ) {
    throw e;
    }
 catch( Exception e ) {
    final String msg = "init() - Not valid parameter '" + ROOT_FOLDER
                                + "' : '" + value + "'";

    logger.fatal( msg, e );

    throw new javax.servlet.ServletException( msg, e );
    }

 this.rootCanonicalFolder       = this.rootCanonicalFolderFile.getPath();
 this.rootCanonicalFolderLength = this.rootCanonicalFolder.length();
}

/**
**
*/
@Override
public void service( // ---------------------------------------------------
    HttpServletRequest  request,
    HttpServletResponse response
    )
    throws
        javax.servlet.ServletException,
        java.io.IOException
{
 File file;

 try {
    file = getFile( request.getParameter( "FILE" ).toString() );
    }
 catch( Exception e ) {
    String params = getURLParams( request );

    file = decodeURL( params );
    }

 if( file.isDirectory() ) {
    this.directoryService( request, response, file );
    }
 else /* if( file.isFile() ) */ {

    request.setAttribute( "file.File", file );

    String extention = getExtension( file ).toLowerCase();

    // if( file.getName().toLowerCase().endsWith( ".html" ) )
    if( extention.equals( ".html" ) ) {
        request.setAttribute( "content-type.String", "text/html" );
        request.setAttribute( "filename.extension.String", "" );
        }
    else if( extention.equals( ".txt" ) || extention.equals( ".readme" ) ) {
        request.setAttribute( "content-type.String", "text/plain" );
        request.setAttribute( "filename.extension.String", "" );
        }
    else {
        request.setAttribute( "content-type.String", "application/octet-stream" );
        // request.setAttribute( "content-type.String", "image/octet-stream" );
        // request.setAttribute( "filename.extension.String", "%20" );
        request.setAttribute( "filename.extension.String", ".download" );
        }

    // nextURL = "/ftp/ftp-file.jsp";
    //
    // this.getServletContext().getRequestDispatcher( nextURL ).forward( request, response );
    this.fileServiceWithoutAttachement( request, response );
    }

}

/**
**
*/
protected String getURLPrefix( HttpServletRequest request ) // ------------
{
// private String URLPrefix = "/Tools/FTP";
 return request.getContextPath() + request.getServletPath();
}

/**
**
*/
protected String getURLParams( HttpServletRequest request ) // ------------
{
 return request.getRequestURI().substring( getURLPrefix( request ).length() );
}


/**
**
*/
public File getFile( String suffix ) // -----------------------------------
    throws javax.servlet.ServletException
{
 final File canonicalFile;

 try {
    canonicalFile = (new File( this.rootCanonicalFolderFile, suffix )).getCanonicalFile();
    }
 catch( Exception e ) {
    final String msg = "Not valid value '" + suffix + "'";

    logger.fatal( msg, e );

    throw new javax.servlet.ServletException( msg, e );
    }

/* if( ! canonicalFile.getPath().startsWith( this.rootCanonicalFolder ) ) */
 if( ! isPrefix( canonicalFile ) ) {
    final String msg = "Access not allowed '" + suffix + "'";

    logger.error( msg );

    throw new javax.servlet.ServletException( msg );
    }

 if( ! canonicalFile.exists() ) {
    final String msg = "Not found '" + suffix + "'";

    logger.error( msg );

    throw new javax.servlet.ServletException( msg );
    }

 if( ! canonicalFile.canRead() ) {
    final String msg = "Can't read '" + suffix + "'";

    logger.error( msg );

    throw new javax.servlet.ServletException( msg );
    }

 return canonicalFile;
}

/**
**
*/
protected boolean isPrefix( File canonicalFile ) // -----------------------
{
 return canonicalFile.getPath().startsWith( this.rootCanonicalFolder );
}

/**
**
*/
protected String getRelativePath( File canonicalFile ) // -----------------
{
 try {
    return canonicalFile.getPath().substring( this.rootCanonicalFolderLength );
    }
 catch( StringIndexOutOfBoundsException e ) {
    //
    // On tente d'acc�der au "dessus" de la racine...
    //
    return "";
    }
}

/**
**
*/
public static String getExtension( File file ) // -------------------------
{
 String name = file.getName();
 int    ext  = name.lastIndexOf( '.' );

 if( ext == -1 ) {
    return "";
    }

 return name.substring( ext );
}

/**
**
*/
public File decodeURL( String params ) // ---------------------------------
    throws javax.servlet.ServletException
{

 return getFile( params );
}

/**
**
*/
public String encodeURL( HttpServletRequest request, File file ) // -------
{
 String relativePath = getRelativePath( file ).replace( '\\', '/' );

 // return URLPrefix + "?FILE=" + relativePath;
 // return URLPrefix + relativePath;
 return getURLPrefix( request ) + relativePath;
}

/**
**
*/
public void directoryService( // ------------------------------------------
    HttpServletRequest  request,
    HttpServletResponse response,
    File                file
    )
    throws
        javax.servlet.ServletException,
        java.io.IOException
{
 //final String   currentDir = getRelativePath( file );
 final File[]   dirsFile;
 final File[]   filesFile;
 final String[] dirsURL;
 final String[] filesURL;

        {
        final LinkedList<File>  dirsList    = new LinkedList<File>();
        final LinkedList<File>  filesList   = new LinkedList<File>();
        final File[]            files       = file.listFiles();

        for( int i = 0; i<files.length; i++ ) {
            if( files[ i ].isDirectory() ) {
                dirsList.add( files[ i ] );
                }
            else {
                filesList.add( files[ i ] );
                }
            }

        dirsFile  = new File[ dirsList.size() ];
        dirsURL   = new String[ dirsFile.length ];

        for( int i = 0; i<dirsFile.length; i++  ) {
            dirsFile[ i ] = dirsList.get( i );
            dirsURL[ i ]  = encodeURL( request, dirsFile[ i ] );
            }

        filesFile = new File[ filesList.size() ];
        filesURL = new String[ filesFile.length ];

        for( int i = 0; i<filesFile.length; i++  ) {
            filesFile[ i ] = filesList.get( i );
            filesURL[ i ]  = encodeURL( request, filesFile[ i ] );
            }
        }

 final String parentURL = encodeURL( request, file.getParentFile() );
 final String title     = "title";

 request.setAttribute( "dirsFile.File[]"    , dirsFile );
 request.setAttribute( "dirsURL.String[]"   , dirsURL   );
 request.setAttribute( "filesFile.File[]"   , filesFile );
 request.setAttribute( "filesURL.String[]"  , filesURL  );
 request.setAttribute( "title.String"       , title     );
 request.setAttribute( "parentURL.String"   , parentURL );

 final String nextURL = "/ftp/ftp-dir.jsp";

 this.getServletContext().getRequestDispatcher( nextURL ).forward( request, response );
}

/**
**
*/
public void fileServiceWithAttachement( // --------------------------------
    HttpServletRequest  request,
    HttpServletResponse response
    )
    throws
        java.io.IOException
{
 final OutputStream out         = response.getOutputStream();
 final String       contentType = (String)request.getAttribute( "content-type.String" );
 final File         file        = (File)request.getAttribute( "file.File" );
 final String       filenameExt = (String)request.getAttribute( "filename.extension.String" );

 response.setContentType( contentType );

 response.setHeader( "Cache-Control","no-store" );
 response.setHeader( "Pragma","no-cache" );
 response.setHeader(
            "Content-Disposition",
            "attachement; filename=\"" + file.getName() + filenameExt + "\""
            );

 try {
    InputStream   is      = new BufferedInputStream( new FileInputStream( file ) );
    final byte[]  buffer  = new byte[ 4096 ];
    int           len;

    while( (len = is.read( buffer, 0, buffer.length )) != -1 ) {
        out.write( buffer, 0, len );
        }

    is.close();
    }
 catch( java.io.IOException e ) {
    logger.error( "file '" + file + "'", e  );

    throw e;
    }
}

/**
**
*/
public void fileServiceWithoutAttachement( // -----------------------------
    HttpServletRequest  request,
    HttpServletResponse response
    )
    throws
        java.io.IOException
{
 final OutputStream out         = response.getOutputStream();
 final String       contentType = (String)request.getAttribute( "content-type.String" );
 final File         file        = (File)request.getAttribute( "file.File" );
 final String       filenameExt = (String)request.getAttribute( "filename.extension.String" );

 response.setContentType( contentType );

 response.setHeader( "Cache-Control","no-store" );
 response.setHeader( "Pragma","no-cache" );
// response.setHeader( "Content-Description", file.getName() );
// response.setHeader(
//            "Content-Disposition",
//            "attachement; filename=\"" + file.getName() + filenameExt + "\""
//            );

 try {
    InputStream   is      = new BufferedInputStream( new FileInputStream( file ) );
    final byte[]  buffer  = new byte[ 4096 ];
    int           len;

    while( (len = is.read( buffer, 0, buffer.length )) != -1 ) {
        out.write( buffer, 0, len );
        }

    is.close();
    }
 catch( java.io.IOException e ) {
    logger.error( "file '" + file + "'", e  );

    throw e;
    }
}


} // class

/*

final File[]    dirsFile  = (String[])request.getAttribute( "dirsFile.File[]" );
final String[]  dirsURL   = (String[])request.getAttribute( "dirsURL.String[]" );
final File[]    filesFile = (String[])request.getAttribute( "filesFile.File[]" );
final String[]  filesURL  = (String[])request.getAttribute( "filesURL.String[]" );
final String    title     = (String)request.getAttribute( "title.String" );
final String    parentURL = (String)request.getAttribute( "parentURL.String" );

*/
