package com.googlecode.cchlib.servlet.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.sql.DataSource;
import com.googlecode.cchlib.servlet.ActionServlet;
import com.googlecode.cchlib.servlet.exception.ServletActionException;
import com.googlecode.cchlib.sql.SimpleDataSource;
import com.googlecode.cchlib.sql.SimpleQuery;
import com.googlecode.cchlib.sql.SimpleUpdate;

/**
 * {@link ServletAction} ready to use {@link SimpleQuery} and {@link SimpleUpdate}
 *
 * @deprecated no replacement
 */
@Deprecated
public abstract class AbstractSimpleQueryServletAction
    extends AbstractServletAction
{
    private com.googlecode.cchlib.sql.SimpleQuery  simpleQuery  = null;
    private com.googlecode.cchlib.sql.SimpleUpdate simpleUpdate = null;

    public abstract ActionServlet.Action doSQL() // NOSONAR
        throws  ServletActionException,
                NamingException,
                SQLException;

    /**
     * Returns DataSource
     * @return DataSource
     * @throws NamingException
     */
    protected abstract DataSource getDataSource()
        throws NamingException;

    /**
     * Initialize environment and call {@link #doSQL()}
     * <BR>
     * Free resources allocated by SimpleQuery or SimpleUpdate if needed
     */
    @Override
    public ActionServlet.Action doAction() throws ServletActionException // NOSONAR
    {
        ActionServlet.Action nextAction;

        try {
            nextAction= doSQL();
            }
        catch( final NamingException | SQLException e ) {
            throw new ServletActionException( e );
            }
        finally {
            SimpleDataSource.quietClose( this.simpleQuery );
            SimpleDataSource.quietClose( this.simpleUpdate );
            }

        return nextAction;
    }

    /**
     * Returns a valid SimpleQuery
     * @return a valid SimpleQuery
     * @throws NamingException if an error occurred while getting data source
     */
    public com.googlecode.cchlib.sql.SimpleQuery getSimpleQuery()
        throws NamingException
    {
        if( this.simpleQuery == null ) {
            this.simpleQuery = new com.googlecode.cchlib.sql.SimpleQuery( getDataSource() );
            }
        return this.simpleQuery;
    }

    /**
     * Returns a valid SimpleUpdate
     * @return a valid SimpleUpdate
     * @throws NamingException if an error occurred while getting data source
     */
    public com.googlecode.cchlib.sql.SimpleUpdate getSimpleUpdate()
        throws NamingException
    {
        if( this.simpleUpdate == null ) {
            this.simpleUpdate = new com.googlecode.cchlib.sql.SimpleUpdate( getDataSource() );
            }
        return this.simpleUpdate;
    }

    /**
     * Execute an SQL query
     * @param sql SQL query to execute
     * @return count of modified rows
     * @throws SQLException if any
     * @throws NamingException if any
     * @see SimpleUpdate#doUpdate(String)
     */
    public int doUpdate( final String sql ) // NOSONAR
        throws SQLException, NamingException
    {
        log( "doUpdate: " + sql );

        return getSimpleUpdate().doUpdate( sql );
    }

    /**
     * Execute a SQL query and return corresponding ResultSet.
     * @param sql query SQL Query to send to database
     * @return {@link ResultSet} from SQL query statement
     * @throws SQLException if any
     * @throws NamingException if any
     * @see SimpleQuery#executeQuery(String)
     */
    public ResultSet executeQuery( final String sql ) // NOSONAR
        throws SQLException, NamingException
    {
        log( "executeQuery: " + sql );

        return getSimpleQuery().executeQuery( sql );
    }
}

