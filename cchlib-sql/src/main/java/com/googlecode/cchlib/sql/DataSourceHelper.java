package com.googlecode.cchlib.sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 * Easy way to obtain a {@link DataSource}
 */
public class DataSourceHelper
{
    public static final Logger LOGGER = Logger.getLogger( DataSourceHelper.class );
    public static final int DEFAULT_MAX_RETRY = 10;

    private DataSourceHelper()
    {
        // All static
    }

    /**
     * Call {@link AutoCloseable#close()} but hide {@link IOException} if
     * {@code closeable} is not null.
     *
     * @param closeable AutoCloseable object to close (could be null)
     */
    public static void quietClose( final AutoCloseable closeable )
    {
        if( closeable != null ) {
            try {
                closeable.close();
            }
            catch( final Exception ioe ) {
                LOGGER.warn( "quietClose", ioe );
            }
        }
    }

    /**
     * Retrieve DataSource on {@link InitialContext}
     *
     * @param resourceName DataSource name
     * @return DataSource if resource found
     * @throws SimpleDataSourceException if any error occurred
     */
    public static final DataSource createDataSource(
            final String resourceName
            )
        throws SimpleDataSourceException
    {
        Object      ressource = null;
        DataSource  ds;

        try {
            final Context context = new InitialContext();

            ressource   = context.lookup(resourceName);
            ds          = DataSource.class.cast(ressource);
            }
        catch( final ClassCastException e ) {
            throw new SimpleDataSourceException(
                    "Bad ressource '" + resourceName + "' expecting DataSource, found : " + ressource,
                    e
                    );
            }
        catch( final NamingException e ) {
            throw new SimpleDataSourceException(
                    "Can't create SimpleQuery for '" + resourceName + '\'',
                    e
                    );
            }

        if( ds == null ) {
            throw new SimpleDataSourceException(
                    "Can't get DataSource for '" + resourceName + '\''
                    );
            }
        else {
            return ds;
            }
    }

    /**
     * Create a <b>new</b> Connection to the data source
     *
     * @param dataSource Data source name
     * @param userPass array of 2 String within user and password
     * @param maxRetry max retry
     * @return a connection to the data source
     * @throws SQLException  if a database access error occurs
     */
    @SuppressWarnings("resource")
    public static Connection createConnectionFromDataSource(
             final DataSource  dataSource,
             final String[]    userPass,
             final int         maxRetry
             )
        throws SQLException
    {
        Connection connection = null;
        int        count      = 0;

        while( (connection == null) || connection.isClosed() ) {
            if( userPass == null ) {
                connection = dataSource.getConnection();
                }
            else {
                connection = dataSource.getConnection( userPass[0], userPass[1] );
                }

            if( connection.isClosed() && (++count > maxRetry ) ) {
                throw new SQLException("can't getConnection() - connection is closed");
                }
            }

        return connection;
    }

    /**
     * NEEDDOC
     *
     * @param dataSource NEEDDOC
     * @return NEEDDOC
     */
    public static SimpleDataSource newSimpleDataSource( final DataSource dataSource )
    {
        return new SimpleDataSource( dataSource );
    }

    /**
     * NEEDDOC
     *
     * @param resourceName NEEDDOC
     * @return NEEDDOC
     * @throws SimpleDataSourceException if any
     */
    public static SimpleDataSource newSimpleDataSource( final String resourceName )
            throws SimpleDataSourceException
    {
        return newSimpleDataSource( createDataSource( resourceName ) );
    }
}
