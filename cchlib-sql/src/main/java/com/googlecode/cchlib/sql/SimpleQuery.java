package com.googlecode.cchlib.sql;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Execute SQL statement using {@link DataSource}
 * <p>
 * This class is not indented to use for application
 * that use many SQL query. otherwise you must consider
 * to cache SimpleQuery object.
 * </p>
 *
 * @see SimpleUpdate
 * @see ConnectionQuery
 * @deprecated use {@link SimpleSQL} instead
 */
@Deprecated
public class SimpleQuery
    extends SimpleDataSource
        implements Closeable
{
    private Connection conn;
    private Statement stmt;

    /**
     *  Create a SimpleQuery from a valid {@link DataSource}
     *
     * @param ds DataSource to use.
     * @throws NullPointerException if ds is null.
     */
    public SimpleQuery( final DataSource ds )
    {
        super(ds);

        this.conn = null;
        this.stmt = null;
    }

    /**
     *  Create a SimpleQuery resource name to lookup on {@link InitialContext}
     *
     * @param resourceName Resource to retrieve on {@link InitialContext}
     * @throws SimpleDataSourceException if any
     * @see DataSourceHelper#createDataSource(String)
     */
    public SimpleQuery( final String resourceName )
        throws SimpleDataSourceException
    {
        super( DataSourceHelper.createDataSource( resourceName ) );

        this.conn = null;
        this.stmt = null;
    }

    /**
     * Execute a SQL query and return corresponding {@link ResultSet}.
     *
     * @param query SQL Query to send to database
     * @return {@link ResultSet} from SQL query statement
     * @throws SQLException if any
     */
    public ResultSet executeQuery( final String query )
        throws SQLException
    {
        ResultSet rset = null;

        if( this.conn == null ) {
            openConnection();
            }

        if(this.conn != null) {
            this.stmt = this.conn.createStatement();

            rset = this.stmt.executeQuery( query );
            }

        return rset;
    }

    /**
     * Get a new {@link Connection} from data source
     *
     * @throws SQLException if any (should NOT be called if {@link #getConnection()} has
     * return a valid {@link Connection})
     */
    protected void openConnection() throws SQLException
    {
        if( this.conn != null ) {
            throw new SQLException("SimpleQuery Invalid State [conn != null]");
            }
        else {
            this.conn = createConnectionFromDataSource();
            }
    }

    /**
     * Returns internal {@link Connection}
     * @return Internal {@link Connection}, could be null
     * @see #openConnection()
     */
    protected Connection getConnection()
    {
        return this.conn;
    }

    /**
     * Free all resources
     * @throws SQLException
     */
    protected void closeConnection() throws SQLCloseException
    {
        try {
            privateCloseStatement();
            }
        finally {
            privateCloseConnection();
            }
    }

    private void privateCloseStatement() throws SQLCloseException
    {
        if( this.stmt != null ) {
            try {
                this.stmt.close();
            }
            catch( final SQLException e ) {
                throw new SQLCloseException( e );
            }
            finally {
                this.stmt = null;
            }
        }
    }

    private void privateCloseConnection() throws SQLCloseException
    {
        if( this.conn != null ) {
            try {
                this.conn.close();
            }
            catch( final SQLException e ) {
                throw new SQLCloseException( e );
            }
            finally {
                this.conn = null;
            }
        }
    }

    @Override
    public void close() throws IOException
    {
        closeConnection();
    }
}
