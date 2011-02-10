package cx.ath.choisnet.sql;

import java.io.Flushable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.sql.DataSource;
import cx.ath.choisnet.ToDo;

/**
 * TODO: Doc!
 * @author Claude CHOISNET
 *
 */
@ToDo(action=ToDo.Action.DOCUMENTATION)
public class SimpleQuery 
    extends SimpleDataSource
        implements Flushable
{
    private Connection conn;
    private Statement stmt;

    /**
     * TODO: Doc!
     * 
     * @param ds
     */
    public SimpleQuery(DataSource ds)
    {
        super(ds);

        conn = null;
        stmt = null;
    }

    /**
     * TODO: Doc!
     * 
     * @param resourceName
     * @throws SimpleDataSourceException
     */
    public SimpleQuery(String resourceName)
        throws SimpleDataSourceException
    {
        super(SimpleQuery.getDataSource(resourceName));

        conn = null;
        stmt = null;
    }

    /**
     * TODO: Doc!
     * 
     * @param query
     * @return
     * @throws java.sql.SQLException
     */
    public ResultSet executeQuery(String query)
        throws SQLException
    {
        ResultSet rset = null;

        if(conn == null) {
            openConnection();
        }

        try {
            if(conn != null) {
                stmt = conn.createStatement();
                rset = stmt.executeQuery(query);
            }
        }
        finally {
            if(rset == null) {
                flush();
            }
        }

        return rset;
    }

    /**
     * TODO: Doc!
     * 
     * @param rset
     * @return
     * @throws java.sql.SQLException
     */
    public static List<String> translateResultSetToStringList(ResultSet rset)
        throws SQLException
    {
        List<String> list = new ArrayList<String>();

        while( rset.next() ) {
            list.add( rset.getString(1) );
        }

        return list;
    }

    /**
     * TODO: Doc!
     * 
     * @param rset
     * @return
     * @throws java.sql.SQLException
     */
    public static String[] translateResultSetToStringArray(ResultSet rset)
        throws SQLException
    {
        List<String>    list    = SimpleQuery.translateResultSetToStringList(rset);
        String[]        strings = new String[list.size()];
        int             i       = 0;

        for(Iterator<String> iter = list.iterator(); iter.hasNext();) {
            strings[i++] = iter.next();
        }

        return strings;
    }

    /**
     * TODO: Doc!
     * 
     * @param query
     * @return
     * @throws java.sql.SQLException
     */
    public String[] translateQueryToStringArray(String query)
        throws SQLException
    {
        ResultSet   rst = null;
        String[]    str;

        try {
            rst = executeQuery(query);
            str = SimpleQuery.translateResultSetToStringArray(rst);
        }
        finally {
            if(rst != null) {
                try { rst.close(); } catch(Exception ignore) { }
            }
        }

        return str;
    }

    /**
     * TODO: Doc!
     * 
     * @param dataSourceName
     * @param query
     * @return
     * @throws SimpleDataSourceException
     * @throws SQLException
     */
    public static String[] translateQueryToStringArray(
            String  dataSourceName, 
            String  query
            )
        throws SimpleDataSourceException, SQLException
    {
        SimpleQuery squery = null;
        String[]    str;

        try {
            squery = new SimpleQuery(dataSourceName);
            str    = squery.translateQueryToStringArray(query);
        }
        finally {
            if(squery != null) {
                try { squery.close(); } catch(Exception ignore) { }
            }
        }

        return str;
    }

    /**
     * TODO: Doc!
     * 
     * @throws java.sql.SQLException
     */
    protected void openConnection() throws SQLException
    {
        if(conn != null) {
            throw new SQLException("SimpleQuery InvalidState [conn != null]");
        }
        else {
            conn = getConnectionFromDataSource();
        }
    }

    /**
     * TODO: Doc!
     * 
     * @return
     */
    protected Connection getConnection()
    {
        return conn;
    }

    /**
     * TODO: Doc!
     * 
     */
    protected void closeConnection()
    {
        if(stmt != null) {
            try { stmt.close(); } catch(SQLException ignore) {}
            stmt = null;
        }

        if(conn != null) {
            try { conn.close(); } catch(SQLException ignore) {}

            conn = null;
        }
    }

    @Override
    public void flush()
    {
        closeConnection();
    }

    @Override
    public void close() throws IOException
    {
        closeConnection();

        super.close();
    }

    @Override
    protected void finalize() throws Throwable
    {
        closeConnection();

        super.finalize();
    }
}
