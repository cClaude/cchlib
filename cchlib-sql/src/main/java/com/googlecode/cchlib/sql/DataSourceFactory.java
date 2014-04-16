package com.googlecode.cchlib.sql;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.NeedTestCases;

/**
 * Create a simple {@link DataSource} based on a standard driver class object.
 */
@NeedDoc
@NeedTestCases
public class DataSourceFactory
{
    private DataSourceFactory()
    {// All static
    }

    /**
     * Create a simple {@link DataSource} based on a standard driver class object.
     *
     * @param driverClassName Driver class name
     * @param url Database URL according to driver specifications
     * @param username Default username for connection use by {@link DataSource#getConnection()}
     * @param password Default password for connection use by {@link DataSource#getConnection()}
     * @param logger Valid {@link PrintWriter} that will use as define by {@link DataSource#getLogWriter()}
     * @return a simple {@link DataSource}
     * @throws ClassNotFoundException if driver class not found
     * @throws NullPointerException if driverClassName or logger is null
     */
    public static DataSource buildDataSource(
            final String        driverClassName,
            final String        url,
            final String        username,
            final String        password,
            final PrintWriter   logger
            )
        throws ClassNotFoundException, NullPointerException
    {
        return buildDataSource(
                driverClassName,
                url,
                username,
                password,
                30,
                logger
                );
    }

    /**
     * Create a simple {@link DataSource} based on a standard driver class object.
     *
     * @param driverClassName Driver class name
     * @param url Database URL according to driver specifications
     * @param username Default username for connection use by {@link DataSource#getConnection()}
     * @param password Default password for connection use by {@link DataSource#getConnection()}
     * @param timeout default login timeout in second for {@link DataSource}, see {@link DataSource#getLoginTimeout()}
     * @param logger Valid {@link Logger} that will use as define by {@link DataSource#getLogWriter()}
     * @return a simple {@link DataSource}
     * @throws ClassNotFoundException if driver class not found
     * @throws NullPointerException if driverClassName or logger is null
     */
    public static DataSource buildDataSource(
            final String        driverClassName,
            final String        url,
            final String        username,
            final String        password,
            final int           timeout,
            final Logger        logger
            )
        throws ClassNotFoundException, NullPointerException
    {
        if( logger == null ) {
            throw new NullPointerException();
            }
        if( driverClassName == null ) {
            throw new NullPointerException();
            }

        Class.forName( driverClassName );

        return new DataSource()
        {
            final Writer wLogger = new Writer()
            {
                @Override
                public void close() throws IOException
                {
                }
                @Override
                public void flush() throws IOException
                {
                }
                @Override
                public void write(final char[] cbuf, final int off, final int len)
                        throws IOException
                {
                    dsLogger.log(
                        Level.WARNING,
                        new String( cbuf, off, len )
                        );
                }
            };

            PrintWriter pwLogger    = new PrintWriter( wLogger );
            int         timeOut     = timeout;
            Logger      dsLogger    = logger;

            @Override
            public Connection getConnection() throws SQLException
            {
                return getConnection( username, password );
            }
            @Override
            public Connection getConnection( final String username, final String password )
                throws SQLException
            {
                Connection conn = null;

                do {
                    if((conn != null) && !conn.isClosed()) {
                        break;
                        }
                    conn = getDriverManagerConnection( url, username, password );

                    if( conn.isClosed() && (dsLogger != null) ) {
                        dsLogger.log(
                            Level.WARNING,
                            "*** Connection is closed !"
                            );
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
            public void setLoginTimeout( final int seconds )
            {
                timeOut = seconds;
            }
            @Override
            public PrintWriter getLogWriter()
            {
                return pwLogger;
            }
            @Override
            public void setLogWriter( final PrintWriter out )
            {
                pwLogger = out;
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
            @Override
            public Logger getParentLogger() throws SQLFeatureNotSupportedException
            {
                return dsLogger;
            }
        };
    }

    /**
     * Create a simple {@link DataSource} based on a standard driver class object.
     *
     * @param driverClassName Driver class name
     * @param url Database URL according to driver specifications
     * @param username Default username for connection use by {@link DataSource#getConnection()}
     * @param password Default password for connection use by {@link DataSource#getConnection()}
     * @param timeout default login timeout in second for {@link DataSource}, see {@link DataSource#getLoginTimeout()}
     * @param logger Valid {@link PrintWriter} that will use as define by {@link DataSource#getLogWriter()}
     * @return a simple {@link DataSource}
     * @throws ClassNotFoundException if driver class not found
     * @throws NullPointerException if driverClassName or logger is null
     */
    public static DataSource buildDataSource(
            final String        driverClassName,
            final String        url,
            final String        username,
            final String        password,
            final int           timeout,
            final PrintWriter   logger
            )
        throws ClassNotFoundException, NullPointerException
    {
        if( logger == null ) {
            throw new NullPointerException();
            }
        if( driverClassName == null ) {
            throw new NullPointerException();
            }

        Class.forName( driverClassName );

        return new DataSource()
        {
            int         timeOut = timeout;
            PrintWriter pw      = logger;

            @Override
            public Connection getConnection() throws SQLException
            {
                return getConnection( username, password );
            }
            @Override
            public Connection getConnection( final String username, final String password )
                throws SQLException
            {
                Connection conn = null;

                do {
                    if((conn != null) && !conn.isClosed()) {
                        break;
                        }
                    conn = getDriverManagerConnection( url, username, password );

                    if( conn.isClosed() && (pw != null) ) {
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
            public void setLoginTimeout( final int seconds )
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
            @Override
            public Logger getParentLogger()
                    throws SQLFeatureNotSupportedException
            {
                throw new SQLFeatureNotSupportedException( "getParentLogger() not supported");
            }
        };
    }

    private static Connection getDriverManagerConnection( //
        final String url, //
        final String username, //
        final String password ) throws SQLException
    {
        return DriverManager.getConnection( url, username, password );
    }
}
