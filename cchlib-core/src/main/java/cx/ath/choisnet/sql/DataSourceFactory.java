package cx.ath.choisnet.sql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import cx.ath.choisnet.ToDo;
import cx.ath.choisnet.sql.mysql.MySQLDataSourceFactory;

/**
 * TODO: Doc!
 * 
 * @author Claude CHOISNET
 */
@ToDo
public class DataSourceFactory
{
    //private final static Logger _slogger = Logger.getLogger( DataSourceFactory.class );
    
    private DataSourceFactory()
    {// All static
    }
    
    @Deprecated
    public static DataSource buildDataSource(
            final String driverClassName,
            final String url,
            final String username,
            final String password
            )
        throws ClassNotFoundException
    {
        return buildDataSource(
                driverClassName,
                url,
                username,
                password,
                new PrintWriter( System.err ) // Mmm : TODO something better
                );
    } 
    
    /**
     * 
     * @param driverClassName
     * @param url
     * @param username
     * @param password
     * @param loggerPrintWriter
     * @return
     * @throws ClassNotFoundException
     * @throws NullPointerException if driverClassName or loggerPrintWriter is null
     */
    public static DataSource buildDataSource(
            final String        driverClassName,
            final String        url,
            final String        username,
            final String        password,
            final PrintWriter   loggerPrintWriter
            )
        throws ClassNotFoundException
    {
        if( loggerPrintWriter == null ) {
            throw new NullPointerException();
            }
        Class.forName( driverClassName );

        return new DataSource() 
        {
            int         timeOut = 30;
            PrintWriter pw      = loggerPrintWriter;

            @Override
            public Connection getConnection()
                throws java.sql.SQLException
            {
                return getConnection( username, password );
            }
            @Override
            public Connection getConnection( String username, String password )
                throws java.sql.SQLException
            {
                Connection conn = null;

                do {
                    if(conn != null && !conn.isClosed()) {
                        break;
                        }
                    conn = DriverManager.getConnection( url, username, password );

                    if( conn.isClosed() && pw != null ) {
                        pw.println( "*** Connection is closed !" );
                        }
                    } while(true);

                return conn;
            }
            @Override
            public int getLoginTimeout()
            {
                return timeOut;
            }
            @Override
            public void setLoginTimeout( int seconds )
            {
                timeOut = seconds;
            }
            @Override
            public PrintWriter getLogWriter()
            {
                return pw;
            }
            @Override
            public void setLogWriter( final PrintWriter out )
            {
                pw = out;
            }
            @Override
            public boolean isWrapperFor( final Class<?> clazz )
                throws SQLException
            {
                return false;
            }
            @Override
            public <T> T unwrap( final Class<T> clazz )
                throws SQLException
            {
                throw new SQLException( "unwrap() not supported" );
            }
        };
    }

    /**
     * @see MySQLDataSourceFactory#buildMySQLDataSource(String,String,String,PrintWriter)
     */
    @Deprecated
    public static DataSource buildMySQLDataSource(
            String url,
            String username,
            String password
            )
        throws ClassNotFoundException
    {
        return MySQLDataSourceFactory.buildMySQLDataSource(
                url,
                username,
                password,
                new PrintWriter( System.err )
                );
    }

    /**
     * @see MySQLDataSourceFactory#buildMySQLDataSource(String,String,String,String,PrintWriter)
     */
    @Deprecated
    public static DataSource buildMySQLDataSource(
            String dbHostName,
            String dbName,
            String username,
            String password
            )
        throws ClassNotFoundException
    {
        return MySQLDataSourceFactory.buildMySQLDataSource(
                dbHostName,
                dbName,
                username,
                password,
                new PrintWriter( System.err )
                );
    }

    /**
     * @see MySQLDataSourceFactory#buildMySQLDataSource(String,int,String,String,String,PrintWriter)
     */
    @Deprecated
    public static DataSource buildMySQLDataSource(
            String      dbHostName,
            int         dbPort,
            String      dbName,
            String      username,
            String      password
            )
        throws ClassNotFoundException
    {
        return MySQLDataSourceFactory.buildMySQLDataSource(
            dbHostName,
            dbPort,
            dbName,
            username,
            password,
            new PrintWriter( System.err )
            );
    }
}
