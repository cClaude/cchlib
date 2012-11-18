package com.googlecode.cchlib.sql;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Easy way to obtain a {@link DataSource}
 */
public class SimpleDataSource
    implements Closeable
{
    private DataSource  ds;
    private String[]    userPass;

    /**
     *  Create a SimpleDataSource object from a valid {@link DataSource}
     *
     * @param ds DataSource to use.
     * @throws NullPointerException if ds is null.
     */
    public SimpleDataSource( final DataSource ds )
    {
        if( ds == null ) {
            throw new NullPointerException( "DataSource is null" );
            }

        this.ds         = ds;
        this.userPass   = null;
    }

    /**
     *  Create a SimpleDataSource object from a valid {@link DataSource}
     *  using specified user name and password
     *
     * @param ds DataSource to use.
     * @param username User name to use to access to database
     * @param password Password to use to access to database
     * @throws NullPointerException if ds is null.
     */
    public SimpleDataSource(
            final DataSource  ds,
            final String      username,
            final String      password
            )
    {
        this(ds);
        this.userPass = (new String[] {username, password});
    }

    /**
     * Returns {@link DataSource}
     * @return {@link DataSource}
     */
    protected DataSource getDataSource()
    {
        return ds;
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
            try { closeable.close(); } catch( IOException ignore ) { }
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
            Context context = new InitialContext();

            ressource   = context.lookup(resourceName);
            ds          = DataSource.class.cast(ressource);
            }
        catch( ClassCastException e ) {
            throw new SimpleDataSourceException(
                    "Bad ressource '" + resourceName + "' expecting DataSource, found : " + ressource,
                    e
                    );
            }
        catch( NamingException e ) {
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
     * Create a <b>new</b> Connection from DataSource
     *
     * @return a new Connection from DataSource
     * @throws SQLException if any
     */
    public Connection createConnectionFromDataSource()
        throws SQLException
    {
        Connection  conn    = null;
        int         count   = 0;

        while( conn == null || conn.isClosed() ) {
            if( userPass == null ) {
                conn = ds.getConnection();
                }
            else {
                conn = ds.getConnection(userPass[0], userPass[1]);
                }

            if( conn.isClosed() && ++count > 10 ) {
                throw new SQLException("can't getConnection() - connection is closed");
                }
            }

        return conn;
    }
}
