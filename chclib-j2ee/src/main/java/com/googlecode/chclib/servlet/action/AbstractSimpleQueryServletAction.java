package com.googlecode.chclib.servlet.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.sql.DataSource;
import com.googlecode.chclib.servlet.ActionServlet;
import com.googlecode.chclib.servlet.exception.ServletActionException;
import cx.ath.choisnet.sql.SimpleQuery;
import cx.ath.choisnet.sql.SimpleUpdate;

/**
 * {@link ServletAction} ready to use {@link SimpleQuery} and {@link SimpleUpdate}
 * 
 * @author Claude CHOISNET
 */
public abstract class AbstractSimpleQueryServletAction
    extends AbstractServletAction
{
    private SimpleQuery simpleQuery = null;
    private SimpleUpdate simpleUpdate = null;

    /**
     * 
     * @return
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
     * <br/>
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
            SimpleQuery.quietClose( simpleQuery );
            SimpleUpdate.quietClose( simpleUpdate );
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
        if( simpleQuery == null ) {
            simpleQuery = new SimpleQuery( getDataSource() );
            }
        return simpleQuery;
    }
    
    /**
     * Returns a valid SimpleUpdate
     * @return a valid SimpleUpdate
     * @throws NamingException if an error occurred while getting data source
     */
    public SimpleUpdate getSimpleUpdate()
        throws NamingException
    {
        if( simpleUpdate == null ) {
            simpleUpdate = new SimpleUpdate( getDataSource() );
            }
        return simpleUpdate;
    }

    /**
     * 
     * @param sql
     * @return
     * @throws SQLException
     * @throws NamingException
     * @see SimpleUpdate#doUpdate(String)
     */
    public int doUpdate( final String sql ) 
        throws SQLException, NamingException
    {
        log( "doUpdate: " + sql );

        return getSimpleUpdate().doUpdate( sql );
    }

    /**
     * 
     * @param sql
     * @return
     * @throws SQLException
     * @throws NamingException
     * @see SimpleQuery#executeQuery(String)
     */
    public ResultSet executeQuery( final String sql ) 
        throws SQLException, NamingException
    {
        log( "executeQuery: " + sql );

        return getSimpleQuery().executeQuery( sql );
    }
}

