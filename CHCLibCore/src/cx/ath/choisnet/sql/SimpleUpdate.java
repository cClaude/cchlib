package cx.ath.choisnet.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import cx.ath.choisnet.ToDo;

/**
 * TODO: Doc!
 * 
 * @author Claude CHOISNET
 *
 */
@ToDo(action=ToDo.Action.DOCUMENTATION)
public class SimpleUpdate extends SimpleDataSource
{

    /**
     *  Create a SimpleUpdate object from a valid {@link DataSource}
     * 
     * @param ds DataSource to use.
     * @throws NullPointerException if ds is null.
     */
    public SimpleUpdate(final DataSource ds)
    {
        super(ds);
    }

    /**
     *  TODO: Doc!
     *     
     * @param resourceName
     * @throws SimpleDataSourceException
     */
    public SimpleUpdate(final String resourceName)
        throws SimpleDataSourceException
    {
        super( SimpleUpdate.getDataSource(resourceName) );
    }

    /**
     *  Execute an SQL query
     * 
     * @param query SQL query to execute
     * @return count of modified rows 
     * @throws SQLException
     */
    public int doUpdate(final String query)
        throws SQLException
    {
        int rows = -1;
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnectionFromDataSource();

            if(conn != null) {
                stmt = conn.createStatement();
                rows = stmt.executeUpdate(query);
             }
        }
        catch(java.sql.SQLException e) {
            throw e;
         }
        finally {
            if(stmt != null) {
                try { stmt.close(); } catch(SQLException ignore) {}
                }
            if(conn != null) {
                try { conn.close(); } catch(java.sql.SQLException e) { }
                }
        }

        return rows;
    }
}
