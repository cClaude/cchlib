package com.googlecode.cchlib.sql.mysql;

import java.util.Set;
import javax.annotation.Nonnull;
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
     * Default driver name : {@value}
     */
    public static final String MYSQL_JDBC_DRIVER_CLASS = "com.mysql.jdbc.Driver";

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

    public static String getURL( final String host )
    {
        return getURL( host, MySQL.DEFAULT_PORT, null );
    }

    public static String getURL(
        @Nonnull final String      host,
        final int                  port,
        final Set<MySQLParameters> parameters
        )
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( "jdbc:mysql://" ).append( host ).append( ':' ).append( port ).append( '/' );

        if( (parameters != null) && !parameters.isEmpty() ) {
            sb.append( '?' );

            if( parameters.contains( MySQLParameters.HANDLE_0000_00_00_00_00_00_TIMESTAMP ) ) {
                sb.append( "zeroDateTimeBehavior=convertToNull" );
            }
        }
        return sb.toString();
    }

    private static String getURL( final MySQLConfig mysql )
    {
        return getURL( mysql.getHostname(), mysql.getPort(), mysql.getParameters() );
    }

    /**
     * Create a {@link DataSource} based on values givens by {@link MySQLConfig}
     * and using {@link #MYSQL_JDBC_DRIVER_CLASS}
     *
     * @param mysql Configuration
     * @return a {@link DataSource}
     * @throws DataSourceFactoryClassNotFoundException
     */
    public static DataSource newDataSource( //
        @Nonnull final MySQLConfig mysql
        ) throws DataSourceFactoryClassNotFoundException
    {
        return newDataSource(
                MySQL.MYSQL_JDBC_DRIVER_CLASS,
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
     *            String connection (see {@link #getURL(String, int, Set)})
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
