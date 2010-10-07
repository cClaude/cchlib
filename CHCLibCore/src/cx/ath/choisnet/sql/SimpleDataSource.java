package cx.ath.choisnet.sql;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * <p style="border:groove;">
 * <b>Warning:</b>
 * Insofar the code of this class comes from decompiling
 * my own code following the loss of source code, the use
 * of this class must do so under protest until I have
 * check its stability, it could be subject to significant
 * change.
 * <br/>
 * <br/>
 * <b>Attention:</b>
 * Dans la mesure où le code de cette classe est issue de
 * la décompilation de mon propre code, suite à la perte
 * du code source, l'utilisation de cette classe doit ce
 * faire sous toute réserve tant que je n'ai pas vérifier
 * sa stabilité, elle est donc sujette à des changements 
 * importants.
 * </p>
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
        this.ds         = ds;
        this.userPass   = null;
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
        Object      ressource = null;
        DataSource  ds;

        try {
            Context context = new InitialContext();
            
            ressource   = context.lookup(resourceName);
            ds          = DataSource.class.cast(ressource);
        }
        catch(ClassCastException e) {
            throw new SimpleDataSourceException(
                    "Bad ressource '" + resourceName + "' expecting DataSource, found : " + ressource,
                    e
                    );
        }
        catch(javax.naming.NamingException e) {
            throw new SimpleDataSourceException(
                    "Can't create SimpleQuery for '" + resourceName + '\'', 
                    e
                    );
        }

        if(ds == null) {
            throw new SimpleDataSourceException(
                    "Can't get DataSource for '" + resourceName + '\'' 
                    );
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
