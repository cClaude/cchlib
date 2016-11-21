/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/sql/DataSourceFactory.java
** Description   :
** Encodage      : ANSI
**
**  1.00.000 2005.03.24 Claude CHOISNET - Version initiale
**  1.50.000 2005.05.19 Claude CHOISNET
**                      Adaptation e l'interface HTMLWritable
**  3.02.042 2007.01.08 Claude CHOISNET
**                      Modification de buildDataSource(String,String,String,String)
**                      afin  d'etre  compatible avec le java 1.6
**                      Deprecated: buildMySQLDataSource(String,String,String)
**                      Deprecated: buildMySQLDataSource(String,String,String,String)
**                      Deprecated: buildMySQLDataSource(String,int,String,String,String)
**                      Ajout de: buildMySQLDataSource(String,String,int,String,String,String)
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.sql.DataSourceFactory
**
*/
package cx.ath.choisnet.sql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
**
** @author Claude CHOISNET
** @version 3.02.042
*/
public class DataSourceFactory
{
/**
** Port par defaut pour MySQL
**
** @since 3.02.042
*/
public final static int MYSQL_DEFAULT_PORT = 3306;

/**
** Driver par defaut pour MySQL
**
** @since 3.02.042
*/
public final static String MYSQL_DEFAULT_DRIVERS = "org.gjt.mm.mysql.Driver";


/**
**
** NOTE: Les methodes relative e l'interface {@link java.sql.Wrapper} du
** java 1.6 ne sont pas supportee.
*/
public static DataSource buildDataSource( // ------------------------------
    final String driverClassName,
    final String url,
    final String username,
    final String password
    )
    throws ClassNotFoundException
{
 Class.forName( driverClassName );

 return new DataSource()
    {
        int         timeOut = 30;
        PrintWriter pw      = new PrintWriter( System.out );

        @Override
        public Connection getConnection() // ::::::::::::::::::::::::::::::
            throws java.sql.SQLException
        {
            return getConnection( username, password );
        }

        @Override
        public Connection getConnection( // :::::::::::::::::::::::::::::::
                String username,
                String password
                )
            throws java.sql.SQLException
        {
            Connection conn = null;

            //
            // Workaround pour mysql-connector-java-3.0.11-stable-bin.jar
            //
            while( conn == null || conn.isClosed() ) {
                conn = DriverManager.getConnection( url, username, password );

                if( conn.isClosed() ) {
                    if( pw != null ) {
                        pw.println( "*** Recuperation d'une connection fermee !" );
                        }
                    }
                }

            return conn;
        }

        @Override
        public int getLoginTimeout() // :::::::::::::::::::::::::::::::::::
        {
            return this.timeOut;
        }

        @Override
        public void setLoginTimeout( int seconds ) // :::::::::::::::::::::
        {
            this.timeOut = seconds;
        }

        @Override
        public PrintWriter getLogWriter() // ::::::::::::::::::::::::::::::
        {
            return this.pw;
        }

        @Override
        public void setLogWriter( PrintWriter out ) // ::::::::::::::::::::
        {
            this.pw = out;
        }

        @Override
        public <T> T unwrap( Class<T> iface ) // ::::::::::::::::::::::::::
            throws java.sql.SQLException
        {
            throw new SQLFeatureNotSupportedException();
        }

        @Override
        public boolean isWrapperFor( Class<?> iface ) // ::::::::::::::::::
            throws java.sql.SQLException
        {
            throw new SQLFeatureNotSupportedException();
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException
        {
            throw new SQLFeatureNotSupportedException();
        }


    };

}

/**
** Constructeur specifique MySQL, utilise le driver defini par {@link #MYSQL_DEFAULT_DRIVERS}
**
** @deprecated use {@link #buildMySQLDataSource(String,String,int,String,String,String)} instead.
*/
@Deprecated
public static DataSource buildMySQLDataSource( // -------------------------
    final String url,
    final String username,
    final String password
    )
    throws ClassNotFoundException
{
 return buildDataSource( MYSQL_DEFAULT_DRIVERS, url, username, password );
}

/**
** Constructeur specifique MySQL, utilise le port defini par {@link #MYSQL_DEFAULT_PORT}
** et le driver defini par {@link #MYSQL_DEFAULT_DRIVERS}
**
** @deprecated use {@link #buildMySQLDataSource(String,String,int,String,String,String)} instead.
*/
@Deprecated
public static DataSource buildMySQLDataSource( // -------------------------
    final String    dbHostName,
    final String    dbName,
    final String    username,
    final String    password
    )
    throws ClassNotFoundException
{
 return buildMySQLDataSource( dbHostName, MYSQL_DEFAULT_PORT, dbName, username, password );
}

/**
**
** @deprecated use {@link #buildMySQLDataSource(String,String,int,String,String,String)} instead.
*/
@Deprecated
public static DataSource buildMySQLDataSource( // -------------------------
    final String    dbHostName,
    final int       dbPort,
    final String    dbName,
    final String    username,
    final String    password
    )
    throws ClassNotFoundException
{
 return buildMySQLDataSource(
        "jdbc:mysql://" + dbHostName + ":" + dbPort + "/" + dbName + "?autoReconnect=true",
        username,
        password
        );
}

/**
**
** @since 3.02.042
*/
public static DataSource buildMySQLDataSource( // --------------------
    final String    driverClassName,
    final String    dbHostName,
    final int       dbPort,
    final String    dbName,
    final String    username,
    final String    password
    )
    throws ClassNotFoundException
{
 return buildDataSource(
            driverClassName,
            "jdbc:mysql://" + dbHostName + ":" + dbPort + "/" + dbName + "?autoReconnect=true",
            username,
            password
            );
}

} // class

