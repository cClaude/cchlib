package com.googlecode.cchlib.servlet.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.NamingException;
import javax.sql.DataSource;
import com.googlecode.cchlib.servlet.ActionServlet;
import com.googlecode.cchlib.servlet.exception.ServletActionException;

/**
 * {@link ServletAction} ready to use SQL {@link Connection}
 */
public abstract class AbstractSQLServletAction
    extends AbstractServletAction
{
    private Connection connection = null;
    private Statement statement = null;

    /**
     * TODOC
     * @return TODOC
     * @throws ServletActionException
     * @throws NamingException
     * @throws SQLException
     */
    public abstract ActionServlet.Action doSQL()
        throws  ServletActionException,
                NamingException,
                SQLException;

    /**
     * Returns the DataSource
     * @return the DataSource
     * @throws NamingException
     */
    public abstract DataSource getDataSource()
        throws NamingException;

    /**
     * Returns the connection
     * @return the connection
     * @throws NamingException
     * @throws SQLException
     */
    public Connection getConnection()
        throws SQLException, NamingException
    {
        if( connection == null ) {
            connection = getDataSource().getConnection();
            }
        return connection;
    }

    /**
     * Returns the statement
     * @return the statement
     * @throws NamingException if any
     * @throws SQLException if any
     */
    public Statement getStatement()
        throws SQLException, NamingException
    {
        if( statement == null ) {
            statement = getConnection().createStatement();
            }
        return statement;
    }

    /**
     * Initialize environment and call {@link #doSQL()}
     * <BR>
     * Free resources allocated by SimpleQuery or SimpleUpdate if needed
     */
    @Override
    final
    public ActionServlet.Action doAction() throws ServletActionException
    {
        ActionServlet.Action nextAction;

        try {
            nextAction= doSQL();
            }
        catch( NamingException e ) {
            throw new ServletActionException( e );
            }
        catch( SQLException e ) {
            throw new ServletActionException( e );
            }
        finally {
            if( statement  != null ) { try{ statement.close();  } catch(Exception ignore) {}}
            if( connection != null ) { try{ connection.close(); } catch(Exception ignore) {}}
            }

        return nextAction;
    }

    /**
     * Executes the given SQL statement, which returns a single
     * ResultSet object.
     *
     * @param sql an SQL statement to be sent to the database,
     *        typically a static SQL SELECT statement .
     * @return a ResultSet object that contains the data produced
     *         by the given query; never null
     * @throws SQLException if a database access error occurs,
     *         this method is called on a closed Statement or the
     *         given SQL statement produces anything other than
     *         a single ResultSet object
     * @throws NamingException if any
     * @see Statement#executeUpdate(String)
     */
    public ResultSet executeQuery( final String sql )
        throws SQLException, NamingException
    {
        log( "executeQuery: " + sql );

        return getStatement().executeQuery( sql );
    }

    /**
     * Executes the given SQL statement, which may return multiple results.
     *
     * @param sql any SQL statement
     * @return true if the first result is a ResultSet object;
     *         false if it is an update count or there are no results
     * @throws SQLException if a database access error occurs or this
     *         method is called on a closed Statement
     * @throws NamingException
     * @see Statement#execute(String)
     */
    public boolean execute( final String sql )
        throws SQLException, NamingException
    {
        log( "execute: " + sql );

        return getStatement().execute( sql );
    }

    /**
     * Executes the given SQL statement, which may be an INSERT, UPDATE,
     * or DELETE statement or an SQL statement that returns nothing,
     * such as an SQL DDL statement.
     *
     * @param sql an SQL Data Manipulation Language (DML) statement,
     *        such as INSERT, UPDATE or DELETE; or an SQL statement
     *        that returns nothing, such as a DDL statement.
     * @return either (1) the row count for SQL Data Manipulation
     *         Language (DML) statements or (2) 0 for SQL statements
     *         that return nothing
     * @throws SQLException if a database access error occurs or this
     *         method is called on a closed Statement
     * @throws NamingException  if any
     * @see Statement#execute(String)
     */
    public int executeUpdate( final String sql )
        throws SQLException, NamingException
    {
        log( "executeUpdate: " + sql );

        return getStatement().executeUpdate( sql );
    }
}

