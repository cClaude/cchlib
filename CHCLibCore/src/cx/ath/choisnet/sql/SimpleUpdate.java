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
     *  TODO: Doc!
     *  
     * @param ds
     */
    public SimpleUpdate(DataSource ds)
    {
        super(ds);
    }

    /**
     *  TODO: Doc!
     *     
     * @param resourceName
     * @throws cx.ath.choisnet.sql.SimpleDataSourceException
     */
    public SimpleUpdate(String resourceName)
        throws cx.ath.choisnet.sql.SimpleDataSourceException
    {
        super(SimpleUpdate.getDataSource(resourceName));
    }

    /**
     *  Execute an SQL query
     * 
     * @param query SQL query to execute
     * @return count of modified rows 
     * @throws java.sql.SQLException
     */
    public int doUpdate(String query)
        throws java.sql.SQLException
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
