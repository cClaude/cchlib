/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/sql/mysql/MySQLAdmin.java
** Description   :
** Encodage      : ANSI
**
**  1.30.010 2005.05.15 Claude CHOISNET
**                      Nettoyage du code,
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.sql.mysql.MySQLAdmin
**
*/
package cx.ath.choisnet.sql.mysql;

import cx.ath.choisnet.io.ConcateInputStream;
import cx.ath.choisnet.io.ParallelOutputStream;
import cx.ath.choisnet.util.ExternalApp;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
** <P>Classe prenant en charge l'excution des reqûetes SQL vers
** une base de donnée MySQL en utilisant les commandes
** d'administration de la base.
** </P>
**
**
**
** @author Claude CHOISNET
** @version 1.30.010
*/
public class MySQLAdmin
{
/**
** Paramètre de la servlet
public final static String MYSQL_EXE = "MYSQL_EXE";
*/

/**
** Paramètre de la servlet
public final static String MYSQL_EXE_PARAMS = "MYSQL_EXE_PARAMS";
*/

/**
** Paramètre de la servlet
public final static String MYSQL_DUMP_EXE = "MYSQL_DUMP_EXE";
*/

/**
** Paramètre de la servlet
public final static String MYSQL_DUMP_EXE_PARAMS = "MYSQL_DUMP_EXE_PARAMS";
*/

/**
**
*/
private String mySQLExe;

/**
**
*/
private String mySQLParams;

/**
**
*/
private String mySQLDumpExe;

/**
**
*/
private String mySQLDumpParams;

/**
**
** @since 1.30.010
*/
public MySQLAdmin( // -----------------------------------------------------
    String mySQLExe,
    String mySQLParams,
    String mySQLDumpExe,
    String mySQLDumpParams
    )
{
 this.mySQLExe          = mySQLExe;
 this.mySQLParams       = mySQLParams;
 this.mySQLDumpExe      = mySQLDumpExe;
 this.mySQLDumpParams   = mySQLDumpParams;
}

/**
** Initialisation à partir de la servlet
public void init( ServletConfig servletConfig ) // ------------------------
    throws ServletException
{
 //
 // MYSQL_EXE
 //
 mySQLExe = getRequiredInitParameter( servletConfig, MYSQL_EXE );

 //
 // MYSQL_EXE_PARAMS
 //
 String             params  = getRequiredInitParameter( servletConfig, MYSQL_EXE_PARAMS );
 StringTokenizer    st      = new StringTokenizer( params, ",; \t\n\r\f" );
 String Buffer       sb      = new String Buffer();

 while( st.hasMoreTokens() ) {
    sb.append( " " );
    sb.append( st.nextToken() );
    }

 mySQLParams = sb.toString();

 //
 // MYSQL_DUMP_EXE
 //
 mySQLDumpExe = getRequiredInitParameter( servletConfig, MYSQL_DUMP_EXE );

 //
 // MY_SQL_DUMP_EXE_PARAMS
 //
 params  = getRequiredInitParameter( servletConfig, MYSQL_DUMP_EXE_PARAMS );
 st      = new StringTokenizer( params, ",; \t\n\r\f" );
 sb      = new StringBuffer();

 while( st.hasMoreTokens() ) {
    sb.append( " " );
    sb.append( st.nextToken() );
    }

 mySQLDumpParams = sb.toString();
}
*/

/**
**
public String getRequiredInitParameter( // --------------------------------
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
*/

/**
**
**
** @param   outputStream
**
** @since 1.49
*/
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

/**
**
**
** @param outputFile
**
*/
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


/**
** <P>NOTE: Les paramtères <code>servletOuput</code> et <code>outputFile</code>
** ne doivent pas être null en même temps.</P>
**
** @param   servletOuput    Flux de la servlet ou null
** @param   outputFile      Fichier de sortie ou null
**
*/
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

/**
**
*/
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

/**
**
*/
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

/**
**
*/
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

} // class
