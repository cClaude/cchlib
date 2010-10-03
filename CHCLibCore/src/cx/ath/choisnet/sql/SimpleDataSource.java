package cx.ath.choisnet.sql;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class SimpleDataSource
    implements Closeable
{
    private DataSource  ds;
    private String[]    userPass;

    public SimpleDataSource(DataSource ds)
    {
        this.ds = ds;
        this.userPass = null;
    }

    public SimpleDataSource(
            DataSource  ds, 
            String      username, 
            String      password
            )
    {
        this(ds);
        this.userPass = (new String[] {username, password});
    }

    protected DataSource getDataSource()
    {
        return ds;
    }

    public void close()
        throws java.io.IOException
    {

    }

    protected static final DataSource getDataSource(String resourceName)
        throws SimpleDataSourceException
    {
        Object ressource = null;
        javax.sql.DataSource ds;

        try {
            Context context = new InitialContext();
            
            ressource   = context.lookup(resourceName);
            ds          = DataSource.class.cast(ressource);
        }
        catch(ClassCastException e) {
            throw new SimpleDataSourceException((new StringBuilder()).append("Bad ressource '").append(resourceName).append("' expecting DataSource, found : ").append(ressource).toString(), e);
        }
        catch(javax.naming.NamingException e) {
            throw new SimpleDataSourceException((new StringBuilder()).append("Can't create SimpleQuery for '").append(resourceName).append("'").toString(), e);
        }

        if(ds == null) {
            throw new SimpleDataSourceException((new StringBuilder()).append("Can't get DataSource for '").append(resourceName).append("'").toString());
        }
        else {
            return ds;
        }
    }

    protected Connection getConnectionFromDataSource()
        throws SQLException
    {
        Connection  conn    = null;
        int         count   = 0;
        
        while( conn == null || conn.isClosed() ) {
            if(userPass == null) {
                conn = ds.getConnection();
            }
            else {
                conn = ds.getConnection(userPass[0], userPass[1]);
            }

            if(conn.isClosed() && ++count > 10) {
                throw new SQLException("can't getConnection() - connection is closed");
            }
        }

        return conn;
    }
}
