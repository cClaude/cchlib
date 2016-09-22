package com.googlecode.cchlib.sql;

import java.io.Flushable;
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
        implements Flushable
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
     * @see SimpleDataSource#createDataSource(String)
     */
    public SimpleQuery( final String resourceName )
        throws SimpleDataSourceException
    {
        super( SimpleDataSource.createDataSource( resourceName ) );

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

        try {
            if(this.conn != null) {
                this.stmt = this.conn.createStatement();
                rset = this.stmt.executeQuery( query );
                }
            }
        finally {
            if( rset == null ) {
                try {
                    flush();
                    }
                catch( final IOException e ) {
                    // This is probably an SQLException
                    throw new SQLException( e );
                    }
                }
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
    protected void closeConnection() throws SQLException
    {
        try {
            privateCloseStatement();
            }
        finally {
            privateCloseConnection();
            }
    }

    private void privateCloseStatement() throws SQLException
    {
        if( this.stmt != null ) {
            try {
                this.stmt.close();
                }
            finally {
                this.stmt = null;
                }
            }
    }

    private void privateCloseConnection() throws SQLException
    {
        if( this.conn != null ) {
            try {
                this.conn.close();
                }
            finally {
                this.conn = null;
                }
            }
    }

    @Override
    public void flush() throws IOException
    {
        try {
            closeConnection();
            }
        catch( final SQLException e ) {
            throw new SQLCloseException( e );
            }
    }

    @Override
    public void close() throws IOException
    {
        try {
            closeConnection();
            }
        catch( final SQLException e ) {
            throw new SQLCloseException( e );
            }

        super.close();
    }

    @Override
    protected void finalize() throws Throwable // NOSONAR $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.avoidFinalizers.avoidFinalizers
    {
        closeConnection();

        super.finalize();
    }
}
