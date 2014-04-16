/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/sql/MySQLAdminFactory.java
** Description   :
** Encodage      : ANSI
**
**  1.00 2005.03.17 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.sql.MySQLAdminFactory
**
*/
package cx.ath.choisnet.servlet.sql;

//import cx.ath.choisnet.sql.mysql.MySQLAdmin;
import java.util.StringTokenizer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import cx.ath.choisnet.sql.mysql.MySQLAdmin;

/**
** <P>Classe prenant en charge l'excution des req�etes SQL vers
** une base de donn�e MySQL en utilisant les commandes
** d'administration de la base.
** </P>
**
**
**
** @author Claude CHOISNET
** @version 1.00
*/
public class MySQLAdminFactory
//    extends MySQLAdmin
{
/**
** Param�tre de la servlet
*/
public final static String MYSQL_EXE = "MYSQL_EXE";

/**
** Param�tre de la servlet
*/
public final static String MYSQL_EXE_PARAMS = "MYSQL_EXE_PARAMS";

/**
** Param�tre de la servlet
*/
public final static String MYSQL_DUMP_EXE = "MYSQL_DUMP_EXE";

/**
** Param�tre de la servlet
*/
public final static String MYSQL_DUMP_EXE_PARAMS = "MYSQL_DUMP_EXE_PARAMS";

/**
** @see #MYSQL_EXE
protected String mySQLExe;
*/

/**
** @see #MYSQL_EXE_PARAMS
protected String mySQLParams;
*/

/**
** @see #MYSQL_DUMP_EXE
protected String mySQLDumpExe;
*/

/**
** @see #MYSQL_DUMP_EXE_PARAMS
protected String mySQLDumpParams;
*/

/**
** Initialisation � partir de la servlet
public MySQLAdminFactory( ServletConfig servletConfig ) // ----------------
    throws ServletException
{
 super( null, null, null, null );
}
*/

/**
** Initialisation � partir de la servlet
*/
public static MySQLAdmin build( ServletConfig servletConfig ) // ----------
    throws ServletException
{
 //
 // MYSQL_EXE
 //
 final String mySQLExe = getRequiredInitParameter( servletConfig, MYSQL_EXE );

 //
 // MYSQL_EXE_PARAMS
 //
 String             params  = getRequiredInitParameter( servletConfig, MYSQL_EXE_PARAMS );
 StringTokenizer    st      = new StringTokenizer( params, ",; \t\n\r\f" );
 StringBuffer       sb      = new StringBuffer();

 while( st.hasMoreTokens() ) {
    sb.append( ' ' );
    sb.append( st.nextToken() );
    }

 final String mySQLParams = sb.toString();

 //
 // MYSQL_DUMP_EXE
 //
 final String mySQLDumpExe = getRequiredInitParameter( servletConfig, MYSQL_DUMP_EXE );

 //
 // MY_SQL_DUMP_EXE_PARAMS
 //
 params  = getRequiredInitParameter( servletConfig, MYSQL_DUMP_EXE_PARAMS );
 st      = new StringTokenizer( params, ",; \t\n\r\f" );
 sb      = new StringBuffer();

 while( st.hasMoreTokens() ) {
    sb.append( ' ' );
    sb.append( st.nextToken() );
    }

 final String mySQLDumpParams = sb.toString();

 return new MySQLAdmin( mySQLExe, mySQLParams, mySQLDumpExe, mySQLDumpParams );
}

/**
**
*/
public static String getRequiredInitParameter( // -------------------------
    ServletConfig   servletConfig,
    String          name
    )
    throws ServletException
{
 String value = servletConfig.getInitParameter( name );

 if( value == null ) {
    throw new ServletException( "parameter '" + name + "' not found" );
    }

 return value;
}


} // class

/**
**
**
** @param   outputStream
**
** @since 1.49
public void createSQLDumpFile( // -----------------------------------------
    OutputStream outputStream
    )
    throws MySQLAdminException
{
 final String command = mySQLDumpExe + mySQLDumpParams;

 try {
    ExternalApp.execute(
        command,
        outputStream,
        System.err
        );
    }
 catch( cx.ath.choisnet.util.ExternalAppException e ) {
    throw new MySQLAdminException( e );
    }
}
*/

/**
**
**
** @param outputFile
**
public void createSQLDumpFile( // -----------------------------------------
    File outputFile
    )
    throws MySQLAdminException
{
 OutputStream outputStream = null;

 try {
    outputStream = new BufferedOutputStream(
                        new FileOutputStream( outputFile )
                        );

    createSQLDumpFile( outputStream );
    }
 catch( java.io.IOException e ) {
    throw new MySQLAdminException( e );
    }
 finally {
    if( outputStream != null ) {
        try {
            outputStream.close();
            }
        catch( Exception ignore ) {
            // Ignore
            }
        }
    }
}

*/

/**
** <P>NOTE: Les paramt�res <code>servletOuput</code> et <code>outputFile</code>
** ne doivent pas �tre null en m�me temps.</P>
**
** @param   servletOuput    Flux de la servlet ou null
** @param   outputFile      Fichier de sortie ou null
**
public void createSQLDumpFile( // -----------------------------------------
    OutputStream    servletOuput,
    File            outputFile
    )
    throws MySQLAdminException
{
 if( servletOuput == null ) {
    if( outputFile == null ) {
        throw new MySQLAdminException( "servletOuput & outputFile nulls" );
        }
    else {
        createSQLDumpFile( outputFile );
        }
    }
 else {
    if( outputFile == null ) {
        createSQLDumpFile( servletOuput );
        }
    else {
        OutputStream fileOutputStream = null;

        try {
            fileOutputStream = new BufferedOutputStream(
                                new FileOutputStream( outputFile )
                                );

            createSQLDumpFile( servletOuput, fileOutputStream );
            }
        catch( java.io.IOException e ) {
            throw new MySQLAdminException( e );
            }
        finally {
            if( fileOutputStream != null ) {
                try {
                    fileOutputStream.close();
                    }
                catch( Exception ignore ) {
                    // Ignore
                    }
                }
            }
        }
    }

}
*/

/**
**
public void createSQLDumpFile( // -----------------------------------------
    OutputStream    servletOuput,
    OutputStream    fileOutputStream
    )
    throws MySQLAdminException
{
 // MultipleOutputStream multipleOutputStream = null;
 ParallelOutputStream multipleOutputStream = null;

 try {
    // multipleOutputStream = new MultipleOutputStream( servletOuput, fileOutputStream );
    multipleOutputStream = new ParallelOutputStream( servletOuput, fileOutputStream );

    createSQLDumpFile( multipleOutputStream );
    }
 catch( java.io.IOException e ) {
    throw new MySQLAdminException( e );
    }
}
*/

/**
**
public void applySQL( // --------------------------------------------------
    File inputfile
    )
    throws
        MySQLAdminException,
        java.io.FileNotFoundException
{
 applySQL(
    new BufferedInputStream(
        new FileInputStream( inputfile )
        )
    );
}
*/

/**
**
public void applySQL( // --------------------------------------------------
    InputStream sqlStream
    )
    throws MySQLAdminException
{
 final String command = mySQLExe + mySQLParams;

 ConcateInputStream fullSQLStream
    = new ConcateInputStream(
            sqlStream,
            "quit\n"
            );

 // Xystem.out.println( "fullSQLStream = " + fullSQLStream );

 try {
    ExternalApp.execute(
        command,
        fullSQLStream, // sqlStream,
        System.out,
        System.err
        );
    }
 catch( cx.ath.choisnet.util.ExternalAppException e ) {
    throw new MySQLAdminException( e );
    }
}
*/
