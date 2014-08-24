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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 ** <P>
 * <B>Servlet :</B>
 * </P>
 **
 **
 ** <B>Configuration :</B><BR>
 **
 ** <XMP> </XMP>
 **
 **
 ** @author Claude CHOISNET
 ** @version 1.00
 */
public class FTPServlet extends HttpServlet {
    private static final long  serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( FTPServlet.class );

    public static final String           ROOT_FOLDER      = "ROOT_FOLDER";
    private File                         rootCanonicalFolderFile;
    private String                       rootCanonicalFolder;
    private int                          rootCanonicalFolderLength;

    @Override
    public void init( final ServletConfig servletConfig ) // ------------------------
            throws javax.servlet.ServletException
    {
        super.init( servletConfig );

        final String value = servletConfig.getInitParameter( ROOT_FOLDER );

        try {
            final File file = new File( value );

            this.rootCanonicalFolderFile = file.getCanonicalFile();

            if( !this.rootCanonicalFolderFile.isDirectory() ) {
                final String msg = "init() - Not valid value for '"
                        + ROOT_FOLDER + "' : '" + this.rootCanonicalFolderFile
                        + "'";

                LOGGER.error( msg );

                throw new javax.servlet.ServletException( msg );
            }
        }
        catch( final javax.servlet.ServletException e ) {
            throw e;
        }
        catch( final Exception e ) {
            final String msg = "init() - Not valid parameter '" + ROOT_FOLDER
                    + "' : '" + value + "'";

            LOGGER.fatal( msg, e );

            throw new javax.servlet.ServletException( msg, e );
        }

        this.rootCanonicalFolder = this.rootCanonicalFolderFile.getPath();
        this.rootCanonicalFolderLength = this.rootCanonicalFolder.length();
    }

    @Override
    public void service( // ---------------------------------------------------
            final HttpServletRequest request, final HttpServletResponse response )
            throws javax.servlet.ServletException, java.io.IOException
    {
        File file;

        try {
            file = getFile( request.getParameter( "FILE" ).toString() );
        }
        catch( final Exception e ) {
            final String params = getURLParams( request );

            file = decodeURL( params );
        }

        if( file.isDirectory() ) {
            this.directoryService( request, response, file );
        } else /* if( file.isFile() ) */{

            request.setAttribute( "file.File", file );

            final String extention = getExtension( file ).toLowerCase();

            // if( file.getName().toLowerCase().endsWith( ".html" ) )
            if( extention.equals( ".html" ) ) {
                request.setAttribute( "content-type.String", "text/html" );
                request.setAttribute( "filename.extension.String", "" );
            } else if( extention.equals( ".txt" )
                    || extention.equals( ".readme" ) ) {
                request.setAttribute( "content-type.String", "text/plain" );
                request.setAttribute( "filename.extension.String", "" );
            } else {
                request.setAttribute( "content-type.String",
                        "application/octet-stream" );
                // request.setAttribute( "content-type.String",
                // "image/octet-stream" );
                // request.setAttribute( "filename.extension.String", "%20" );
                request.setAttribute( "filename.extension.String", ".download" );
            }

            // nextURL = "/ftp/ftp-file.jsp";
            //
            // this.getServletContext().getRequestDispatcher( nextURL ).forward(
            // request, response );
            this.fileServiceWithoutAttachement( request, response );
        }

    }

    protected String getURLPrefix( final HttpServletRequest request ) // ------------
    {
        // private String URLPrefix = "/Tools/FTP";
        return request.getContextPath() + request.getServletPath();
    }

    protected String getURLParams( final HttpServletRequest request ) // ------------
    {
        return request.getRequestURI().substring(
                getURLPrefix( request ).length() );
    }

    public File getFile( final String suffix ) // -----------------------------------
            throws javax.servlet.ServletException
    {
        final File canonicalFile;

        try {
            canonicalFile = (new File( this.rootCanonicalFolderFile, suffix ))
                    .getCanonicalFile();
        }
        catch( final Exception e ) {
            final String msg = "Not valid value '" + suffix + "'";

            LOGGER.fatal( msg, e );

            throw new javax.servlet.ServletException( msg, e );
        }

        /* if( ! canonicalFile.getPath().startsWith( this.rootCanonicalFolder )
         * ) */
        if( !isPrefix( canonicalFile ) ) {
            final String msg = "Access not allowed '" + suffix + "'";

            LOGGER.error( msg );

            throw new javax.servlet.ServletException( msg );
        }

        if( !canonicalFile.exists() ) {
            final String msg = "Not found '" + suffix + "'";

            LOGGER.error( msg );

            throw new javax.servlet.ServletException( msg );
        }

        if( !canonicalFile.canRead() ) {
            final String msg = "Can't read '" + suffix + "'";

            LOGGER.error( msg );

            throw new javax.servlet.ServletException( msg );
        }

        return canonicalFile;
    }

    protected boolean isPrefix( final File canonicalFile ) // -----------------------
    {
        return canonicalFile.getPath().startsWith( this.rootCanonicalFolder );
    }

    protected String getRelativePath( final File canonicalFile ) // -----------------
    {
        try {
            return canonicalFile.getPath().substring(
                    this.rootCanonicalFolderLength );
        }
        catch( final StringIndexOutOfBoundsException e ) {
            //
            // On tente d'acceder au "dessus" de la racine...
            //
            return "";
        }
    }

    public static String getExtension( final File file ) // -------------------------
    {
        final String name = file.getName();
        final int ext = name.lastIndexOf( '.' );

        if( ext == -1 ) {
            return "";
        }

        return name.substring( ext );
    }

    public File decodeURL( final String params ) // ---------------------------------
            throws javax.servlet.ServletException
    {

        return getFile( params );
    }

    public String encodeURL( final HttpServletRequest request, final File file ) // -------
    {
        final String relativePath = getRelativePath( file ).replace( '\\', '/' );

        return getURLPrefix( request ) + relativePath;
    }

    public void directoryService( // ------------------------------------------
            final HttpServletRequest request, final HttpServletResponse response, final File file //
            ) throws ServletException, IOException
    {
        final File[]   dirsFile;
        final File[]   filesFile;
        final String[] dirsURL;
        final String[] filesURL;

        {
            final LinkedList<File> dirsList = new LinkedList<File>();
            final LinkedList<File> filesList = new LinkedList<File>();
            final File[] files = file.listFiles();

            for( int i = 0; i < files.length; i++ ) {
                if( files[ i ].isDirectory() ) {
                    dirsList.add( files[ i ] );
                } else {
                    filesList.add( files[ i ] );
                }
            }

            dirsFile = new File[dirsList.size()];
            dirsURL = new String[dirsFile.length];

            for( int i = 0; i < dirsFile.length; i++ ) {
                dirsFile[ i ] = dirsList.get( i );
                dirsURL[ i ] = encodeURL( request, dirsFile[ i ] );
            }

            filesFile = new File[filesList.size()];
            filesURL = new String[filesFile.length];

            for( int i = 0; i < filesFile.length; i++ ) {
                filesFile[ i ] = filesList.get( i );
                filesURL[ i ] = encodeURL( request, filesFile[ i ] );
            }
        }

        final String parentURL = encodeURL( request, file.getParentFile() );
        final String title = "title";

        request.setAttribute( "dirsFile.File[]", dirsFile );
        request.setAttribute( "dirsURL.String[]", dirsURL );
        request.setAttribute( "filesFile.File[]", filesFile );
        request.setAttribute( "filesURL.String[]", filesURL );
        request.setAttribute( "title.String", title );
        request.setAttribute( "parentURL.String", parentURL );

        final String nextURL = "/ftp/ftp-dir.jsp";

        this.getServletContext().getRequestDispatcher( nextURL )
                .forward( request, response );
    }

    public void fileServiceWithAttachement(
            final HttpServletRequest request,
            final HttpServletResponse response
            ) throws IOException
    {
        try( final OutputStream out = response.getOutputStream() ) {
            final String contentType = (String)request
                    .getAttribute( "content-type.String" );
            final File file = (File)request.getAttribute( "file.File" );
            final String filenameExt = (String)request
                    .getAttribute( "filename.extension.String" );

            response.setContentType( contentType );

            response.setHeader( "Cache-Control", "no-store" );
            response.setHeader( "Pragma", "no-cache" );
            response.setHeader( "Content-Disposition", "attachement; filename=\""
                    + file.getName() + filenameExt + "\"" );

            try {
                final InputStream is = new BufferedInputStream(
                        new FileInputStream( file ) );
                final byte[] buffer = new byte[4096];
                int len;

                while( (len = is.read( buffer, 0, buffer.length )) != -1 ) {
                    out.write( buffer, 0, len );
                }

                is.close();
            }
            catch( final IOException e ) {
                LOGGER.error( "file '" + file + "'", e );

                throw e;
            }
        }
    }

    public void fileServiceWithoutAttachement( // -----------------------------
            final HttpServletRequest request, final HttpServletResponse response )
            throws IOException
    {
        try( final OutputStream out = response.getOutputStream() ) {
            final String contentType = (String)request
                    .getAttribute( "content-type.String" );
            final File file = (File)request.getAttribute( "file.File" );
            final String filenameExt = (String)request
                    .getAttribute( "filename.extension.String" );

            response.setContentType( contentType );

            response.setHeader( "Cache-Control", "no-store" );
            response.setHeader( "Pragma", "no-cache" );
            // response.setHeader( "Content-Description", file.getName() );
            // response.setHeader(
            // "Content-Disposition",
            // "attachement; filename=\"" + file.getName() + filenameExt + "\""
            // );

            try {
                final InputStream is = new BufferedInputStream(
                        new FileInputStream( file ) );
                final byte[] buffer = new byte[4096];
                int len;

                while( (len = is.read( buffer, 0, buffer.length )) != -1 ) {
                    out.write( buffer, 0, len );
                }

                is.close();
            }
            catch(  final IOException e ) {
                LOGGER.error( "file '" + file + "'", e );

                throw e;
            }
        }
    }
}
