package cx.ath.choisnet.sql;

import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class SimpleUpdate extends SimpleDataSource
{

    public SimpleUpdate(DataSource ds)
    {
        super(ds);
    }

    public SimpleUpdate(String resourceName)
        throws cx.ath.choisnet.sql.SimpleDataSourceException
    {
        super(SimpleUpdate.getDataSource(resourceName));
    }

    public int doUpdate(String query)
        throws java.sql.SQLException
    {
        int rows;
        Connection conn;
        Statement stmt;
        rows = -1;
        conn = null;
        stmt = null;

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

        if(stmt != null) {
            try {
                stmt.close();
            }
            catch(java.sql.SQLException e) { }
        }

        if(conn != null) {
            try {
                conn.close();
            }
            catch(java.sql.SQLException e) { }
        }
//        break MISSING_BLOCK_LABEL_110;
//        Exception exception;
//        exception;
    //   34   73:astore          6
        if(stmt != null) {
            try {
                stmt.close();
    //   37   80:aload           4
    //   38   82:invokeinterface #6   <Method void java.sql.Statement.close()>
            }
    //*  39   87:goto            92
            catch(java.sql.SQLException e) { }
    //   40   90:astore          7
        }
        if(conn != null) {
            try {
                conn.close();
    //   43   96:aload_3
    //   44   97:invokeinterface #8   <Method void java.sql.Connection.close()>
            }
    //*  45  102:goto            107
            catch(java.sql.SQLException e) { }
    //   46  105:astore          7
        }
//        throw exception;
    //   47  107:aload           6
    //   48  109:athrow
        return rows;
    //   49  110:iload_2
    //   50  111:ireturn
    }

    public void close()
        throws java.io.IOException
    {

    }
}
