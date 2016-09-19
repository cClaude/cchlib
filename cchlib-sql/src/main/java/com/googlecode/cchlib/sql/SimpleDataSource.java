package com.googlecode.cchlib.sql;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Easy way to obtain a {@link DataSource}
 */
public class SimpleDataSource
    implements Closeable
{
    private final DataSource  dataSource;
    private String[]          userPass;
    private final int         maxRetry;

    /**
     *  Create a SimpleDataSource object from a valid {@link DataSource}
     *
     * @param dataSource Data source to use.
     * @throws NullPointerException if ds is null.
     */
    public SimpleDataSource( final DataSource dataSource )
    {
        this( dataSource, null, null, DataSourceHelper.DEFAULT_MAX_RETRY );
    }

    /**
     *  Create a SimpleDataSource object from a valid {@link DataSource}
     *  using specified user name and password
     *
     * @param dataSource Data source to use.
     * @param username User name to use to access to database
     * @param password Password to use to access to database
     * @throws NullPointerException if dataSource is null.
     */
    public SimpleDataSource(
            final DataSource  dataSource,
            final String      username,
            final String      password
            )
    {
        this( dataSource, username, password, DataSourceHelper.DEFAULT_MAX_RETRY );
    }

    /**
     *  Create a SimpleDataSource object from a valid {@link DataSource}
     *  using specified user name and password
     *
     * @param dataSource Data source to use.
     * @param username User name to use to access to database
     * @param password Password to use to access to database
     * @param maxRetry Number of retry for db connection
     *
     * @throws NullPointerException if ds is null.
     */
    public SimpleDataSource(
            final DataSource  dataSource,
            final String      username,
            final String      password,
            final int         maxRetry
            )
    {
        if( dataSource == null ) {
            throw new NullPointerException( "DataSource is null" );
            }

       this.dataSource = dataSource;

       if( (username == null) && (password == null) ) {
           this.userPass = null;
       } else {
           this.userPass = new String[] {username, password};
       }

       this.maxRetry = maxRetry;
    }

    /**
     * Returns {@link DataSource}
     * @return {@link DataSource}
     */
    protected DataSource getDataSource()
    {
        return this.dataSource;
    }

    @Override
    public void close() throws IOException
    {
        // empty
    }

    /**
     * Call {@link #close()} but hide {@link IOException}
     */
    public void quietClose()
    {
        quietClose( this );
    }

    /**
     * Call {@link #close()} but hide {@link IOException} if
     * closeable is not null.
     *
     * @param closeable Closeable object to close (could be null)
     */
    public static void quietClose( final Closeable closeable )
    {
        if( closeable != null ) {
            try {
                closeable.close();
                } catch( final IOException ioe ) {
                    throw new SQLCloseRuntimeException( ioe );
                }
            }
    }

    /**
     * Retrieve DataSource on {@link InitialContext}
     *
     * @param resourceName DataSource name
     * @return DataSource if resource found
     * @throws SimpleDataSourceException if any error occurred
     * @see DataSourceHelper#createDataSource(String)
     */
    public static final DataSource createDataSource(
            final String resourceName
            )
        throws SimpleDataSourceException
    {
        return DataSourceHelper.createDataSource( resourceName );
    }

    /**
     * Create a <b>new</b> Connection from DataSource
     *
     * @return a new Connection from DataSource
     * @throws SQLException if any
     */
    public Connection createConnectionFromDataSource()
        throws SQLException
    {
        return DataSourceHelper.createConnectionFromDataSource( this.dataSource, this.userPass, this.maxRetry );
    }
}
