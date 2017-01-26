package com.googlecode.cchlib.sql.mysql;

import java.util.Collection;
import java.util.logging.Level;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.DataSource;
import com.googlecode.cchlib.sql.DataSourceFactory;
import com.googlecode.cchlib.sql.DataSourceFactoryClassNotFoundException;

/**
 * Handle MySQL data base
 *
 * @since 4.2
 */
public final class MySQL
{
    private static final String SQL_LOGGER_NAME = "SQLLOGGER";

    private static final java.util.logging.Logger SQL_LOGGER
        = java.util.logging.Logger.getLogger( SQL_LOGGER_NAME );

    /**
     * Driver class name for version 1.x to 5.x : {@value}
     */
    public static final String MYSQL_JDBC_DRIVER_CLASS = "com.mysql.jdbc.Driver";

    /**
     * Driver class name since version 6 : {@value}
     */
    public static final String MYSQL_JDBC_DRIVER_6_CLASS = "com.mysql.cj.jdbc.Driver";

    /**
     * Default port : {@value}
     */
    public static final int DEFAULT_PORT = 3306;

    /**
     * Default timeout : {@value}
     */
    public static final int DEFAULT_TIMEOUT = 30;

    private MySQL()
    {
        // All static
    }

    /**
     * Create URL connection to MySQL using default
     *
     * @param host
     *            Host name for the database
     * @return a connection URL (has a String)
     */
    public static String getURL( final String host )
    {
        return getURL( host, MySQL.DEFAULT_PORT, null );
    }

    /**
     * Create URL connection to MySQL
     *
     * @param host
     *            Host name for the database
     * @param port
     *            Port of the database (default is {@link #DEFAULT_PORT}
     * @param parameters
     *            Extras parameters for special need (see {@link MySQLParameters})
     * @return a connection URL (has a String)
     */
    public static String getURL(
        @Nonnull final String host,
        final int             port,
        @Nullable
        final Collection<? extends MySQLParametersConfig> parameters
        )
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( "jdbc:mysql://" ).append( host ).append( ':' ).append( port ).append( '/' );

        if( (parameters != null) && !parameters.isEmpty() ) {
            sb.append( '?' );

            boolean first = true;

            for( final MySQLParametersConfig mySQLParameters : parameters ) {
                if( first ) {
                    first = false;
                } else {
                    sb.append( "&amp;" );
                }
                sb.append( mySQLParameters.getParameterConfig() );
            }
        }
        return sb.toString();
    }

    private static String getURL( final MySQLConfig mysql )
    {
        return getURL(
                mysql.getHostname(),
                mysql.getPort(),
                mysql.getParameters()
                );
    }

    /**
     * Create a {@link DataSource} based on values givens by {@link MySQLConfig}
     * and using {@link #MYSQL_JDBC_DRIVER_CLASS}
     *
     * @param mysql Configuration
     * @return a {@link DataSource}
     * @throws DataSourceFactoryClassNotFoundException if any
     */
    public static DataSource newDataSource( //
        @Nonnull final MySQLConfig mysql
        ) throws DataSourceFactoryClassNotFoundException
    {
        String driverClassName;

        try {
            driverClassName = MySQL.MYSQL_JDBC_DRIVER_6_CLASS;
            Class.forName( driverClassName );
        }
        catch( final ClassNotFoundException e ) {
            if( SQL_LOGGER.isLoggable( Level.FINE ) ) {
                SQL_LOGGER.log(
                    Level.FINE,
                    "Warning MySQL JDBC driver version 6 or more not found",
                    e
                    );
            }
            driverClassName = MySQL.MYSQL_JDBC_DRIVER_CLASS;
        }

        return newDataSource(
                driverClassName,
                getURL( mysql ),
                mysql.getUsername(),
                mysql.getPassword(),
                mysql.getTimeout()
                );
    }

    /**
     * Create a {@link DataSource} with default logger.
     *
     * @param driverClassName
     *            Driver class
     * @param dbURL
     *            String connection (see {@link #getURL(String, int, Collection)})
     * @param username
     *            Database user name
     * @param password
     *            Database password
     * @param timeout
     *            Time out for connection (see {@link #DEFAULT_TIMEOUT})
     * @return a {@link DataSource}
     * @throws DataSourceFactoryClassNotFoundException
     *             if driver not found
     */
    public static DataSource newDataSource( //
        final String driverClassName,
        final String dbURL,
        final String username,
        final String password,
        final int    timeout
        ) throws DataSourceFactoryClassNotFoundException
    {
        return DataSourceFactory.buildDataSource(
                driverClassName,
                dbURL,
                username,
                password,
                timeout,
                SQL_LOGGER
                );
    }
}
