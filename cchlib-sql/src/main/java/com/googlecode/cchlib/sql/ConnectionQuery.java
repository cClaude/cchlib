package com.googlecode.cchlib.sql;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

/**
 * Execute SQL statement using {@link Connection}
 *
 * @since 4.1.3
 * @deprecated use {@link SimpleSQL} instead
 */
@Deprecated
public class ConnectionQuery implements Closeable
{
    private static final Logger LOGGER = Logger.getLogger( ConnectionQuery.class );

    private final Connection connection; // must not close this
    private Statement        statement;

    /**
     *  Create a ConnectionQuery object from a valid {@link Connection}
     *  <BR>
     *  ConnectionQuery never close Connection
     *
     * @param connection Connection to use.
     * @throws NullPointerException if connection is null
     */
    public ConnectionQuery( final Connection connection )
    {
        if( connection == null ) {
            throw new NullPointerException( "Connection is null" );
            }

        this.connection = connection;
        this.statement  = null;
    }

    /**
     * Execute an SQL query (underlying statement is reuse if possible)
     *
     * @param query SQL query to execute
     * @return {@link ResultSet} from query execution
     * @throws SQLException if any
     */
    public ResultSet executeQuery( final String query )
        throws SQLException
    {
        createStatement();

        return this.statement.executeQuery( query );
    }

    /**
     *  Execute an SQL update query (underlying statement is reuse if possible)
     *
     * @param query SQL query to execute
     * @return count of modified rows
     * @throws ExtendedSQLException if any
     */
    public int doUpdate(final String query)
        throws ExtendedSQLException
    {
        try {
            createStatement();

            return this.statement.executeUpdate( query );
            }
        catch( final SQLException e ) {
            throw new ExtendedSQLException( e, query );
            }
    }

    /**
     * Returns {@link Connection} (must not be close)
     * @return {@link Connection}
     */
    protected Connection getConnection()
    {
        return this.connection;
    }

    private void createStatement() throws SQLException
    {
        if( this.statement != null ) {
            // FIX org.apache.tomcat.dbcp.dbcp.DelegatingStatement
            try {
                // Note was protected prior to JDBC 4
                if( this.statement.isClosed() ) {
                    this.statement = null;
                }
            }
            catch( final IllegalAccessError ignore ) {
                this.statement = null;

                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "Ignore IllegalAccessError", ignore );
                }
            }
        }
        if( this.statement == null ) {
            this.statement = this.connection.createStatement();
        }
    }

    /**
     * Close internal Statement
     * @throws SQLException if any
     */
    protected void closeStatement() throws SQLException
    {
        if( this.statement != null ) {
            try {
                this.statement.close();
                }
            finally {
                this.statement = null;
                }
            }
    }

    /**
     * Close internal Statement
     *
     * @throws SQLCloseException if an SQLException occurred
     */
    @Override
    public void close() throws SQLCloseException
    {
        try {
            closeStatement();
            }
        catch( final SQLException e ) {
            throw new SQLCloseException( e );
            }
    }

    /**
     * Call {@link #close()} but hide {@link IOException},
     * by creating a {@link RuntimeException} if any {@link IOException} occur
     */
    public void quietClose()
    {
        try {
            close();
            }
        catch( final IOException ignore ) {
            throw new SQLCloseRuntimeException( ignore );
            }
    }

    @Override
    protected void finalize() throws Throwable
    {
        closeStatement();

        super.finalize();
    }
}
