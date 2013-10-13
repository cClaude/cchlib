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

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;
import cx.ath.choisnet.servlet.util.SimpleServletConfig;
import cx.ath.choisnet.sql.mysql.MySQLAdminException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
** <P>Servlet prenant en charge la sauvegarde SQL de la base de donn�e (DUMP),
** ainsi que le t�l�chargement et l'execution de requ�tes SQL.
** </P>
**
** <PRE>
** Configuration: ****
** </PRE>
**
**
** @author Claude CHOISNET
** @version 1.00
*/
public class LoadSQLServlet
    extends HttpServlet
{
/** serialVersionUID */
private static final long serialVersionUID = 2L;

/**
** Gestion des traces
final org.apache.commons.logging.Log logger
    = org.apache.commons.logging.LogFactory.getLog( this.getClass() );
*/

/**
** Param�tre de la servlet
*/
public final static String LOAD_DIRECTORY = "LOAD_DIRECTORY";

/**
** Param�tre de la servlet - Anti DOS taille maximum pour les uploads
** <BR />
** <code>1073741824 = 1 Go </code>
*/
public final static String MAX_POST_SIZE = "MAX_POST_SIZE";

/**
** @see #LOAD_DIRECTORY
*/
protected File loadDirectory;

/**
** @see #MAX_POST_SIZE
*/
protected int maxPostSize;


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
 this.loadDirectory = new File( ssc.getRequiredParameter( LOAD_DIRECTORY ) );

 if( ! loadDirectory.isDirectory() ) {
    throw new javax.servlet.ServletException(
        "parameter '" + LOAD_DIRECTORY + "' not valid : '" + loadDirectory + "'"
        );
    }

 //
 // MAX_POST_SIZE
 //
 this.maxPostSize  = ssc.getRequiredParameterAsInt( MAX_POST_SIZE );
}



/**
** Initialisation de la servlet
*/
@Override
public void init( ServletConfig servletConfig ) // ------------------------
    throws javax.servlet.ServletException
{
 super.init( servletConfig );
}

/**
**
*/
@Override
protected void doPost( // ----------------------------------------------------
    HttpServletRequest  request,
    HttpServletResponse response
    )
    throws
        java.io.IOException,
        javax.servlet.ServletException
{
 InputStream    inputStream = null;
 String         nextURL     = null;

 MultipartParser    aParser = new MultipartParser( request, maxPostSize );
 Part               aPart;

 while( (aPart = aParser.readNextPart()) != null ) {
    final String partName = aPart.getName();

    if( "X_DUMPFILE".equals( partName ) ) {
        FilePart aFilePart  = (FilePart)aPart;
        String   filename   = aFilePart.getFileName();

        if( filename == null ) {
            //
            // La valeur n'est pas d�finie, on passe � la suite
            //
            continue;
            }

        if( inputStream != null ) {
            //
            // Quelqu'un qui fait joujou avec les param�tres,
            // on ignore !
            //
            continue;
            }

        //
        // On charge le fichier
        //
        File newFile = new File(
                loadDirectory,
                SaveSQLServlet.getCurrentDateForFileName() + "_UPLOAD_" + filename
                ) ;

        aFilePart.writeTo( newFile );

        inputStream = new FileInputStream( newFile );
        }
    else if( "X_NEXTURL".equals( partName ) ) {
        //
        // Le param�tre NEXTURL est obligatoire.
        //
        ParamPart aParamPart = (ParamPart)aPart;

        nextURL = aParamPart.getStringValue();
        }
    else {
        // ParamPart aParamPart = (ParamPart)aPart;

        getLogger().warn( "Found param '" + partName + "'" );
        }
    }

 if( nextURL == null ) {
    response.sendError( HttpServletResponse.SC_BAD_REQUEST, "Parameter X_NEXTURL requiered" );

    return;
    }

 actionLoadDumpFile( new BufferedInputStream( inputStream ) );

 inputStream.close();

 ServletContext     sc = getServletContext();
 RequestDispatcher  rd = sc.getRequestDispatcher( nextURL );

 rd.forward( request, response );
}

/**
**
*/
@Override
protected void doGet( // -----------------------------------------------------
    HttpServletRequest  request,
    HttpServletResponse response
    )
    throws
        java.io.IOException,
        javax.servlet.ServletException
{
 doPost( request, response );
}


/**
**
protected String actionLoadLocalDumpFile( // ------------------------------
    HttpServletRequest  request,
    HttpServletResponse response
    )
    throws
        java.io.IOException,
        cx.ath.choisnet.sql.mysql.MySQLAdminException,
        javax.servlet.ServletException
{
 String nextURL;
 int fileNumber;

 try {
    fileNumber = Integer.parseInt( request.getParameter( "X_FILENUMBER" ) );
    }
 catch( Exception e ) {
    fileNumber = -1;
    }

 if( fileNumber >= 0 ) {
    HttpSession session     = request.getSession( false );
    List        listOfFiles = (List)session.getAttribute( "LIST_OF_FILES" );
    File        fileToLoad = (File)listOfFiles.get( fileNumber );

    actionLoadDumpFile( fileToLoad );

    String msg =
        "<TABLE><TR><TD CLASS=\"LABEL\">"
            + "Le fichier suivant a �t� charg�"
            + "</TD><TD CLASS=\"VALUE\">"
            + fileToLoad
            + "</TD></TR></TABLE>";

    nextURL = "/dbrestore/db_restore_result.jsp?RESULT_MESSAGE="
        + java.net.URLEncoder.encode( msg, ENCODING );
    }
 else {
    String msg = "Parameter 'X_FILENUMBER' not valid.";

    logger.fatal( "* " + msg );

    throw new javax.servlet.ServletException( msg );
    }

 return nextURL;
}
*/

/**
**
public void actionLoadDumpFile( // ----------------------------------------
    File inputfile
    )
    throws
        cx.ath.choisnet.sql.mysql.MySQLAdminException,
        java.io.FileNotFoundException
{
 mySQLAdmin.applySQL( inputfile );
}
*/

/**
 * @throws MySQLAdminException 
**
*/
public void actionLoadDumpFile( // ----------------------------------------
    InputStream sqlStream
    ) throws MySQLAdminException
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


} // class

