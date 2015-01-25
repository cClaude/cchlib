/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/servlet/sql/LoadSQLServlet.java
 ** Description   :
 ** Encodage      : ANSI
 **
 **  1.00.2005.03.17 Claude CHOISNET
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.servlet.sql.SQLServlet
 **
 */
package cx.ath.choisnet.servlet.sql;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;
import cx.ath.choisnet.servlet.util.SimpleServletConfig;
import cx.ath.choisnet.sql.mysql.MySQLAdminException;

/**
 ** <P>
 * Servlet prenant en charge la sauvegarde SQL de la base de donnee (DUMP),
 * ainsi que le telechargement et l'execution de requetes SQL.
 * </P>
 **
 ** <PRE>
 * * Configuration: ****
 ** </PRE>
 **
 **
 ** @author Claude CHOISNET
 ** @version 1.00
 */
public class LoadSQLServlet extends HttpServlet {
    /** serialVersionUID */
    private static final long  serialVersionUID = 2L;

    /**
     ** Parametre de la servlet
     */
    public final static String LOAD_DIRECTORY   = "LOAD_DIRECTORY";

    /**
     ** Parametre de la servlet - Anti DOS taille maximum pour les uploads <BR />
     ** <code>1073741824 = 1 Go </code>
     */
    public final static String MAX_POST_SIZE    = "MAX_POST_SIZE";

    /**
     ** @see #LOAD_DIRECTORY
     */
    protected File             loadDirectory;

    /**
     ** @see #MAX_POST_SIZE
     */
    protected int              maxPostSize;

    /**
     ** Initialisation de la servlet
     */
    @Override
    public void init() throws javax.servlet.ServletException // ---------------
    {
        final SimpleServletConfig ssc = new SimpleServletConfig( this );

        //
        // DUMP_DIRECTORY
        //
        this.loadDirectory = new File(
                ssc.getRequiredParameter( LOAD_DIRECTORY ) );

        if( !this.loadDirectory.isDirectory() ) {
            throw new javax.servlet.ServletException( "parameter '"
                    + LOAD_DIRECTORY + "' not valid : '" + this.loadDirectory + "'" );
        }

        //
        // MAX_POST_SIZE
        //
        this.maxPostSize = ssc.getRequiredParameterAsInt( MAX_POST_SIZE );
    }

    /**
     ** Initialisation de la servlet
     */
    @Override
    public void init( final ServletConfig servletConfig ) // ------------------------
            throws javax.servlet.ServletException
    {
        super.init( servletConfig );
    }

    @SuppressWarnings({ "resource", "null" })
    @Deprecated
    @Override
    protected void doPost( // ----------------------------------------------------
            final HttpServletRequest request, final HttpServletResponse response )
            throws java.io.IOException, javax.servlet.ServletException
    {
        InputStream inputStream = null;
        String nextURL = null;

        final MultipartParser aParser = new MultipartParser( request, this.maxPostSize );
        Part aPart;

        while( (aPart = aParser.readNextPart()) != null ) {
            final String partName = aPart.getName();

            if( "X_DUMPFILE".equals( partName ) ) {
                final FilePart aFilePart = (FilePart)aPart;
                final String filename = aFilePart.getFileName();

                if( filename == null ) {
                    //
                    // La valeur n'est pas definie, on passe e la suite
                    //
                    continue;
                }

                if( inputStream != null ) {
                    //
                    // Quelqu'un qui fait joujou avec les parametres,
                    // on ignore !
                    //
                    continue;
                }

                //
                // On charge le fichier
                //
                final File newFile = new File( this.loadDirectory,
                        SaveSQLServlet.getCurrentDateForFileName() + "_UPLOAD_"
                                + filename );

                aFilePart.writeTo( newFile );

                inputStream = new FileInputStream( newFile );
            } else if( "X_NEXTURL".equals( partName ) ) {
                //
                // Le parametre NEXTURL est obligatoire.
                //
                final ParamPart aParamPart = (ParamPart)aPart;

                nextURL = aParamPart.getStringValue();
            } else {
                // ParamPart aParamPart = (ParamPart)aPart;

                getLogger().warn( "Found param '" + partName + "'" );
            }
        }

        if( nextURL == null ) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,
                    "Parameter X_NEXTURL requiered" );

            return;
        }

        actionLoadDumpFile( new BufferedInputStream( inputStream ) );

        inputStream.close();

        final ServletContext sc = getServletContext();
        final RequestDispatcher rd = sc.getRequestDispatcher( nextURL );

        rd.forward( request, response );
    }

    /**
     **
     */
    @Override
    protected void doGet( // -----------------------------------------------------
            final HttpServletRequest request, final HttpServletResponse response )
            throws java.io.IOException, javax.servlet.ServletException
    {
        doPost( request, response );
    }

    /**
     * @throws MySQLAdminException
     **
     */
    public void actionLoadDumpFile( // ----------------------------------------
            final InputStream sqlStream ) throws MySQLAdminException
    {
        SaveSQLServlet.getMySQLAdmin().applySQL( sqlStream );
    }

    /**
     ** Gestion des traces
     */
    final protected org.apache.log4j.Logger getLogger() // --------------------
    {
        return org.apache.log4j.Logger.getLogger( this.getClass() );
    }

}

