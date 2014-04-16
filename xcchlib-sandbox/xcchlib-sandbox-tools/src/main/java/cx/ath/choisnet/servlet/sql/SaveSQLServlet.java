/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/servlet/sql/SaveSQLServlet.java
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

import cx.ath.choisnet.servlet.util.SimpleServletConfig;
import cx.ath.choisnet.sql.mysql.MySQLAdmin;
import cx.ath.choisnet.sql.mysql.MySQLAdminException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 ** <P>
 * Servlet prenant en charge la sauvegarde SQL de la base de donn�e (DUMP),
 * ainsi que le t�l�chargement et l'execution de requ�tes SQL.
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
public class SaveSQLServlet extends HttpServlet {
    private static final long serialVersionUID = 3L;

    /**
     ** Param�tre de la servlet
     */
    public final static String  DUMP_DIRECTORY   = "DUMP_DIRECTORY";

    /**
     ** @see #DUMP_DIRECTORY
     */
    protected File dumpDirectory;

    /** */
    protected static MySQLAdmin mySQLAdmin;

    /** Definition du motif de date pour les noms de fichier */
    protected final static String DATE_FORMAT = "yyyy.MM.dd_HH-mm-ss";

    /** Formateur pour les noms de fichier */
    protected final static java.text.SimpleDateFormat FORMATTER  //
        = new java.text.SimpleDateFormat( DATE_FORMAT );

    /**
     ** Initialisation de la servlet
     */
    @Override
    public void init() throws javax.servlet.ServletException // ---------------
    {
        SimpleServletConfig ssc = new SimpleServletConfig( this );

        //
        // DUMP_DIRECTORY
        //
        this.dumpDirectory = new File(
                ssc.getRequiredParameter( DUMP_DIRECTORY ) );

        if( !dumpDirectory.isDirectory() ) {
            throw new javax.servlet.ServletException( "parameter '"
                    + DUMP_DIRECTORY + "' not valid : '" + dumpDirectory + "'" );
        }
    }

    /**
     ** Initialisation de la servlet
     */
    @Override
    public void init( ServletConfig servletConfig ) // ------------------------
            throws javax.servlet.ServletException
    {
        super.init( servletConfig );

        synchronized( this ) {
            mySQLAdmin = MySQLAdminFactory.build( servletConfig );
        }
    }

    /**
**
*/
    @Override
    protected void doPost( // ----------------------------------------------------
            HttpServletRequest request, HttpServletResponse response )
            throws java.io.IOException, javax.servlet.ServletException
    {
        doGet( request, response );
    }

    /**
**
*/
    @Override
    protected void doGet( // -----------------------------------------------------
            HttpServletRequest request, HttpServletResponse response )
            throws java.io.IOException, javax.servlet.ServletException
    {
        final String nextURL = actionCreateDumpFile( request, response );

        if( nextURL != null ) {
            try {
                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher( nextURL );

                rd.forward( request, response );
            }
            catch( java.io.IOException e ) {
                getLogger().error( "FAIL :", e );

                throw e;
            }
            catch( javax.servlet.ServletException e ) {
                getLogger().error( "FAIL :", e );

                throw e;
            }
        }
    }

    /**
     ** 
     ** @return un objet String contenant l'URL pour le RequestDispatcher
     */
    public String actionCreateDumpFile( // ------------------------------------
            HttpServletRequest request, HttpServletResponse response //
            ) throws IOException, ServletException
    {
        final String outputMode = request.getParameter( "X_OUTPUTMODE" );
        final String nextURL = request.getParameter( "X_NEXTURL" );
        final String filename = request.getParameter( "X_FILENAME" );
        final File outputFile;

        if( filename != null ) {
            outputFile = new File( dumpDirectory, getCurrentDateForFileName()
                    + "_" + filename + ".sql" );
        } else {
            outputFile = null;
        }

        if( "FILE".equals( outputMode ) ) {
            actionCreateDumpFile( request, null, outputFile );

            return nextURL;
        } else if( "TEXT".equals( outputMode ) ) {
            //
            // Envoi du r�sultat au format TEXT vers le demandeur.
            //
            response.setContentType( "text/plain" );

            actionCreateDumpFile( request, response.getOutputStream(),
                    outputFile );

            return null;
        } else if( "SQL".equals( outputMode ) ) {
            //
            // Envoi du r�sultat au format SQL vers le demandeur.
            //
            response.setContentType( "text/sql" );

            actionCreateDumpFile( request, response.getOutputStream(),
                    outputFile );

            return null;
        } else {
            String msg = "Parameter X_OUTPUTMODE not valid.";

            getLogger().fatal( "* " + msg );

            throw new javax.servlet.ServletException( msg );
        }
    }

    /**
     * @throws MySQLAdminException
     ** 
     */
    protected void actionCreateDumpFile(
            // -----------------------------------
            HttpServletRequest request, OutputStream servletOuput,
            File outputFile ) throws MySQLAdminException
    {
        mySQLAdmin.createSQLDumpFile( servletOuput, outputFile );
    }

    /**
**
*/
    public static MySQLAdmin getMySQLAdmin() // -------------------------------
    {
        return mySQLAdmin;
    }

    /**
     ** Retourne une cha�ne formatt�e, compatible avec un nom de fichier
     * contenant les date et heure courantes.
     ** 
     ** @return un objet String non null.
     */
    public final static String getCurrentDateForFileName() // -----------------
    {
        return FORMATTER.format( new java.util.Date() );
    }

    /**
     ** Gestion des traces
     */
    final protected org.apache.log4j.Logger getLogger() // --------------------
    {
        return org.apache.log4j.Logger.getLogger( this.getClass() );
    }
} 

