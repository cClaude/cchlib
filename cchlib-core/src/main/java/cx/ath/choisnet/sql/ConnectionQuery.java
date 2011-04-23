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
 * @author Claude CHOISNET
 * @since 4.1.3
 */
public class ConnectionQuery implements Closeable
{
    private Connection connection; // must not close this
    private Statement  statement;

    /**
     *  Create a ConnectionQuery object from a valid {@link Connection}
     *
     * @param connection Connection to use.
     * @throws NullPointerException if connection is null
     */
    public ConnectionQuery( final Connection connection )
    {
        if( connection == null ) {
            throw new NullPointerException();
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
     * @throws SQLException if any
     */
    public int doUpdate(final String query)
        throws SQLException
    {
        createStatement();

        return statement.executeUpdate( query );
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
     * @throws IOException if an SQLException occurred
     */
    @Override
    public void close() throws IOException
    {
        try {
            closeStatement();
            }
        catch( SQLException e ) {
            throw new IOException( e );
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
    protected void finalize() throws Throwable
    {
        closeStatement();

        super.finalize();
    }
}
