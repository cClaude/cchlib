package cx.ath.choisnet.sql;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Execute SQL statement using {@link Connection}
 *
 * @since 4.1.3
 */
public class ConnectionQuery implements Closeable
{
    private Connection connection; // must not close this
    private Statement  statement;

    /**
     *  Create a ConnectionQuery object from a valid {@link Connection}
     *  <br/>
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

        return statement.executeQuery( query );
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

            return statement.executeUpdate( query );
            }
        catch( SQLException e ) {
            throw new ExtendedSQLException( e, query );
            }
    }

    /**
     * Returns {@link Connection} (must not be close)
     * @return {@link Connection}
     */
    protected Connection getConnection()
    {
        return connection;
    }

    private void createStatement() throws SQLException
    {
        if( statement != null ) {
            // FIX org.apache.tomcat.dbcp.dbcp.DelegatingStatement
            try {
                // Note was protected prior to JDBC 4
                if( statement.isClosed() ) {
                    statement = null;
                    }
                }
            catch( IllegalAccessError ignore ) {
                statement = null;
                }
            }
        if( statement == null ) {
            statement = connection.createStatement();
            }
    }

    /**
     * Close internal Statement
     * @throws SQLException if any
     */
    protected void closeStatement() throws SQLException
    {
        if( statement != null ) {
            try {
                statement.close();
                }
            finally {
                statement = null;
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
        catch( SQLException e ) {
            throw new SQLCloseException( e );
            }
    }

    /**
     * Call {@link #close()} but hide {@link IOException}
     */
    public void quietClose()
    {
        try { close(); } catch( IOException ignore ) { }
    }

    @Override
    protected void finalize() throws Throwable // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.avoidFinalizers.avoidFinalizers
    {
        closeStatement();

        super.finalize();
    }
}
