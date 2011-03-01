package cx.ath.choisnet.sql;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import cx.ath.choisnet.ToDo;

/**
 * TODO: Doc!
 * @author Claude CHOISNET
 *
 */
@ToDo(action=ToDo.Action.DOCUMENTATION)
public class SimpleDataSource
    implements Closeable
{
    private DataSource  ds;
    private String[]    userPass;

    /**
     *  Create a SimpleDataSource object from a valid {@link DataSource}
     * 
     * @param ds DataSource to use.
     * @throws NullPointerException if ds is null.
     */
    public SimpleDataSource( final DataSource ds )
    {
        if( ds == null ) {
            throw new NullPointerException( "DataSource is null" );
            }
        
        this.ds         = ds;
        this.userPass   = null;
    }

    /**
     * TODO: Doc!
     * 
     * @param ds
     * @param username
     * @param password
     */
    public SimpleDataSource(
            final DataSource  ds, 
            final String      username, 
            final String      password
            )
    {
        this(ds);
        this.userPass = (new String[] {username, password});
    }

    /**
     * TODO: Doc!
     * 
     * @return
     */
    protected DataSource getDataSource()
    {
        return ds;
    }
    
    @Override
    public void close() throws IOException
    {
        // empty
    }
    
    /**
     * Call {@link #close()} but hide {@link IOException}
     */
    public void quietClose()
    {
        try { close(); } catch( IOException ignore ) { }
    }

    /**
     * Call {@link #close()} but hide {@link IOException} if
     * closeable is not null.
     * 
     * @param closeable Closeable object to close (could be null)
     */
    public static void quietClose( final Closeable closeable )
    {
        if( closeable != null ) {
            try { closeable.close(); } catch( IOException ignore ) { }
            }
    }

    /**
     * TODO: Doc!
     * 
     * @param resourceName
     * @return
     * @throws SimpleDataSourceException
     */
    protected static final DataSource getDataSource(final String resourceName)
        throws SimpleDataSourceException
    {
        Object      ressource = null;
        DataSource  ds;

        try {
            Context context = new InitialContext();
            
            ressource   = context.lookup(resourceName);
            ds          = DataSource.class.cast(ressource);
            }
        catch( ClassCastException e ) {
            throw new SimpleDataSourceException(
                    "Bad ressource '" + resourceName + "' expecting DataSource, found : " + ressource,
                    e
                    );
            }
        catch( NamingException e ) {
            throw new SimpleDataSourceException(
                    "Can't create SimpleQuery for '" + resourceName + '\'', 
                    e
                    );
            }

        if( ds == null ) {
            throw new SimpleDataSourceException(
                    "Can't get DataSource for '" + resourceName + '\'' 
                    );
            }
        else {
            return ds;
            }
    }

    /**
     * TODO: Doc!
     * 
     * @return
     * @throws SQLException
     */
    protected Connection getConnectionFromDataSource()
        throws SQLException
    {
        Connection  conn    = null;
        int         count   = 0;
        
        while( conn == null || conn.isClosed() ) {
            if( userPass == null ) {
                conn = ds.getConnection();
                }
            else {
                conn = ds.getConnection(userPass[0], userPass[1]);
                }

            if( conn.isClosed() && ++count > 10 ) {
                throw new SQLException("can't getConnection() - connection is closed");
                }
            }

        return conn;
    }
}
