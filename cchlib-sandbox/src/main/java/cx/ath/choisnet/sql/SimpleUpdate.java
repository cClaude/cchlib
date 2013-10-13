/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/sql/SimpleUpdate.java
** Description   :
** Encodage      : ANSI
**
**  1.00.___ 2005.03.15 Claude CHOISNET - Version initiale
**  1.01.___ 2005.03.24 Claude CHOISNET
**                      Add constructor form a valid DataSource
**  2.01.001 2005.10.02 Claude CHOISNET
**                      Ajout d'un garde fou dans la m�thode
**                      getConnection(DataSource)
**                      Implemente l'interface java.io.Closeable
**  2.02.009 2005.12.14 Claude CHOISNET
**                      La classe �tend maintenant SimpleDataSource
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.sql.SimpleUpdate
**
*/
package cx.ath.choisnet.sql;

import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;

/**
**
** @author Claude CHOISNET
** @version 2.02.009
*/
public class SimpleUpdate
    extends SimpleDataSource
{

/**
** @param ds    a valid DataSource object
*/
public SimpleUpdate( DataSource ds ) // -----------------------------------
{
 super( ds );
}

/**
** @param resourceName  "java:comp/env/jdbc/resourceName"
*/
public SimpleUpdate( String resourceName ) // -----------------------------
    throws SimpleDataSourceException
{
 super( getDataSource( resourceName ) );
}

/**
**
*/
public int doUpdate( final String query ) // ------------------------------
    throws
        java.sql.SQLException
{
 int rows = -1;

// if( ds != null ) {
    Connection  conn    = null;
    Statement   stmt    = null;

    try {
        conn = getConnectionFromDataSource(); // getConnection( ds ); // conn = ds.getConnection();

        if( conn != null ) {
            stmt = conn.createStatement();

            // Execute the Update
            rows = stmt.executeUpdate( query ) ;
            }
        }
    catch( java.sql.SQLException e ) {
//        logger.error( "doUpdate( \"" + query + "\" );", e );
        throw e;
        }
    finally {

        if( stmt != null ) {
            try {
                stmt.close();
                }
            catch( java.sql.SQLException e ) {
                /* ignore */
                }
            }

        if( conn != null ) {
            try {
                conn.close();
                }
            catch( java.sql.SQLException e ) {
                /* ignore */
                }
            }
        }
//    }

 return rows;
}

/**
** <P>Lib�re les informations de la requ�te courante.</P>
*/
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 // pas de traitements pour le moment !
}

} // class


/**
**
protected final Connection getConnection( final DataSource ds ) // --------
    throws java.sql.SQLException
{
 Connection conn    = null;
 int        count = 0; // garde fou

 //
 // Workaround pour mysql-connector-java-3.0.11-stable-bin.jar
 //
 while( conn == null || conn.isClosed() ) {
    conn = ds.getConnection();

    if( conn.isClosed() ) {
        // logger.warn( "recuperation d'une connection fermee !" );
        count++;

        if( count > 10 ) {
            throw new java.sql.SQLException( "can't getConnection() - connection is closed" );
            }
        }
    }

 return conn;
}
*/