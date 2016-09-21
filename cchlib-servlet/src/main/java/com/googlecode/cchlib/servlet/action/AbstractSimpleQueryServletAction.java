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
    private SimpleQuery simpleQuery = null;
    private SimpleUpdate simpleUpdate = null;

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
    final
    public ActionServlet.Action doAction() throws ServletActionException
    {
        ActionServlet.Action nextAction;

        try {
            nextAction= doSQL();
            }
        catch( final NamingException e ) {
            throw new ServletActionException( e );
            }
        catch( final SQLException e ) {
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
    public SimpleQuery getSimpleQuery()
        throws NamingException
    {
        if( this.simpleQuery == null ) {
            this.simpleQuery = new SimpleQuery( getDataSource() );
            }
        return this.simpleQuery;
    }

    /**
     * Returns a valid SimpleUpdate
     * @return a valid SimpleUpdate
     * @throws NamingException if an error occurred while getting data source
     */
    public SimpleUpdate getSimpleUpdate()
        throws NamingException
    {
        if( this.simpleUpdate == null ) {
            this.simpleUpdate = new SimpleUpdate( getDataSource() );
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
    public int doUpdate( final String sql )
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
    public ResultSet executeQuery( final String sql )
        throws SQLException, NamingException
    {
        log( "executeQuery: " + sql );

        return getSimpleQuery().executeQuery( sql );
    }
}

