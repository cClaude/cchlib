package com.googlecode.cchlib.sql;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import com.googlecode.cchlib.sql.queries.QueryHandler;
import com.googlecode.cchlib.sql.queries.QueryHandlerException;

/**
 * Execute SQL statement using {@link DataSource}
 * <p>
 * This class is not indented to use by application outside a J2E
 * or for none concurrent access to the database.
 * </p>
 * @since 4.2
 */
public class SimpleSQL implements Closeable
{
    private final Connection connection;
    private Statement        statement;

    /**
     * Create a SimpleQuery from a valid {@link DataSource}
     *
     * @param connection Valid Connection to use.
     * @throws SQLException if a database access error occurs
     */
    public SimpleSQL( final Connection connection ) throws SQLException
    {
        if( connection.isClosed() ) {
            throw new SQLException( "Connection is not open" );
            }

        this.connection = connection;
        this.statement  = null;
    }

    /**
     * Create a SimpleQuery from a valid {@link DataSource}
     *
     * @param dataSource DataSource to use.
     * @throws NullPointerException if dataSource is null.
     * @throws SQLException if a database access error occurs
     */
    public SimpleSQL( final SimpleDataSource dataSource ) throws SQLException
    {
        this( dataSource.createConnectionFromDataSource() );
    }

    /**
     * Create a SimpleQuery resource name to lookup on {@link InitialContext}
     *
     * @param resourceName Resource to retrieve on {@link InitialContext}
     * @throws SimpleDataSourceException if DataSource can not be create
     * @throws SQLException if a database access error occurs
     * @see DataSourceHelper
     */
    public SimpleSQL( final String resourceName )
        throws SimpleDataSourceException, SQLException
    {
        this( DataSourceHelper.newSimpleDataSource( resourceName ) );
    }

    /**
     * Execute a SQL query and return corresponding {@link ResultSet}.
     *
     * @param query SQL Query to send to database
     * @return {@link ResultSet} from SQL query statement
     * @throws SQLException if a database access error occurs
     */
    public ResultSet executeQuery( final String query )
        throws SQLException
    {
        ResultSet rset = null;

        if( this.connection == null ) {
            throw new SQLException( "Connection close" );
        }

        try {
            this.statement = this.connection.createStatement();
            rset           = this.statement.executeQuery( query );
            }
        finally {
            if( rset == null ) {
                }
            }

        return rset;
    }

    /**
     * Execute a SQL query and send corresponding {@link ResultSet}
     * to <code>queryHandler</code> to handle result.
     *
     * @param query SQL Query to send to database
     * @param queryHandler SQL Query to send to database
     * @return <code>T</code> result from {@link QueryHandler#handle(ResultSet)}
     * @throws SQLException if a database access error occurs
     * @throws QueryHandlerException
     *
     * @see QueryHandler#handle(ResultSet)
     */
    public final <T> T executeQuery(
        final String          query,
        final QueryHandler<T> queryHandler
        )
        throws SQLException, QueryHandlerException
    {
        return queryHandler.handle( executeQuery( query ) );
    }

    /**
     *  Execute an SQL query
     *
     * @param query SQL query to execute
     * @return count of modified rows
     * @throws SQLException if a database access error occurs
     */
    public int doUpdate( final String query )
        throws SQLException
    {
        int rows = -1;

        try( final Statement stmt = this.connection.createStatement() ) {
            rows = stmt.executeUpdate( query );
            }

        return rows;
    }

    @Override
    public void close()
    {
        DataSourceHelper.quietClose( this.statement );
        DataSourceHelper.quietClose( this.connection );
    }
}
