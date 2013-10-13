/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/sql/SimpleDataSource.java
** Description   :
** Encodage      : ANSI
**
**  2.02.009 2005.12.14 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.sql.SimpleDataSource
**
*/
package cx.ath.choisnet.sql;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
**
** @author Claude CHOISNET
** @since   2.02.009
** @version 2.02.009
*/
public class SimpleDataSource
    implements java.io.Closeable
{
/** Description de la source de donn�es */
private DataSource ds;

/**  */
private String[] userPass;

/**
** @param ds    a valid DataSource object
*/
public SimpleDataSource( final DataSource ds ) // -------------------------
{
 this.ds        = ds;
 this.userPass  = null;
}

/**
** @param ds    a valid DataSource object
*/
public SimpleDataSource( // -----------------------------------------------
    final DataSource    ds,
    final String        username,
    final String        password
    )
{
 this( ds );

 this.userPass = new String[] { username, password };
}

/**
**
*/
protected DataSource getDataSource() // -----------------------------------
{
 return ds;
}

/**
** <P>Lib�re les informations de la requ�te courante.</P>
*/
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 // pas de traitements pour le moment !
}

/**
** @param resourceName  ex: "java:comp/env/jdbc/resourceName"
*/
protected final static DataSource getDataSource( String resourceName ) // -
    throws SimpleDataSourceException
{
 DataSource ds;
 Object     ressource   = null;

 try {
    Context context = new InitialContext();

    ressource   = context.lookup( resourceName );
    ds          = DataSource.class.cast( ressource );
    }
 catch( ClassCastException e ) {
    throw new SimpleDataSourceException(
        "Bad ressource '" + resourceName + "' expecting DataSource, found : " + ressource,
        e
        );
    }
 catch( javax.naming.NamingException e ) {
    throw new SimpleDataSourceException(
        "Can't create SimpleQuery for '" + resourceName + "'",
        e
        );
    }

 if( ds == null ) {
    throw new SimpleDataSourceException(
        "Can't get DataSource for '" + resourceName + "'"
        );
    }

 return ds;
}

/**
**
*/
protected Connection getConnectionFromDataSource() // ---------------------
    throws java.sql.SQLException
{
 Connection conn    = null;
 int        count   = 0; // garde fou

 //
 // Workaround pour mysql-connector-java-3.0.11-stable-bin.jar
 //
 while( conn == null || conn.isClosed() ) {

    if( this.userPass == null ) {
        conn = this.ds.getConnection();
        }
    else {
        conn = this.ds.getConnection( this.userPass[ 0 ], this.userPass[ 1 ] );
        }

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

/**
**
protected final Connection getConnection( // ------------------------------
    final DataSource    ds,
    final String        username,
    final String        password
    )
    throws java.sql.SQLException
{
 Connection conn    = null;
 int        count = 0; // garde fou

 //
 // Workaround pour mysql-connector-java-3.0.11-stable-bin.jar
 //
 while( conn == null || conn.isClosed() ) {
    conn = ds.getConnection( username, password );

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

} // class
