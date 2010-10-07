package cx.ath.choisnet.sql;

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
@ToDo
public class SimpleQuery extends SimpleDataSource
{
    private Connection conn;
    private Statement stmt;

    public SimpleQuery(DataSource ds)
    {
        super(ds);

        conn = null;
        stmt = null;
    }

    public SimpleQuery(String resourceName)
        throws cx.ath.choisnet.sql.SimpleDataSourceException
    {
        super(SimpleQuery.getDataSource(resourceName));

        conn = null;
        stmt = null;
    }

    public ResultSet executeQuery(String query)
        throws java.sql.SQLException
    {
        ResultSet rset = null;

        if(conn == null) {
            openConnection();
        }

        if(conn != null) {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(query);
        }

        if(rset == null) {
            flush();
        }
//        break MISSING_BLOCK_LABEL_66;
//    //   24   52:goto            66
//        Exception exception;
//        exception;
    //   25   55:astore_3
//        if(rset == null) {
//            flush();
//        }
//        throw exception;
    //   30   64:aload_3
    //   31   65:athrow
        return rset;
    }

    public static List<String> translateResultSetToStringList(ResultSet rset)
        throws java.sql.SQLException
    {
        List<String> list = new ArrayList<String>();

        while( rset.next() ) {
            list.add( rset.getString(1) );
        }

        return list;
    }

    public static String[] translateResultSetToStringArray(ResultSet rset)
        throws java.sql.SQLException
    {
        List<String>    list    = SimpleQuery.translateResultSetToStringList(rset);
        String[]        strings = new String[list.size()];
        int             i       = 0;

        for(Iterator<String> iter = list.iterator(); iter.hasNext();) {
            strings[i++] = iter.next();
        }

        return strings;
    }

    public String[] translateQueryToStringArray(String query)
        throws java.sql.SQLException
    {
        ResultSet   rst = null;
        String[]    str;

        rst = executeQuery(query);
        str = SimpleQuery.translateResultSetToStringArray(rst);

        if(rst != null) {
            try {
                rst.close();
            }
            catch(Exception ignore) { }
        }

//        break MISSING_BLOCK_LABEL_51;
//    //   15   28:goto            51
//        Exception exception;
//        exception;
    //   16   31:astore          5
        if(rst != null) {
            try {
                rst.close();
            }
            catch(Exception ignore) { }
        }

//        throw exception;
        return str;
    }

    public static String[] translateQueryToStringArray(String dataSourceName, String query)
        throws SimpleDataSourceException, SQLException
    {
        SimpleQuery squery = null;
        String[]    str;

        squery = new SimpleQuery(dataSourceName);
        str    = squery.translateQueryToStringArray(query);

        if(squery != null) {
            try {
                squery.close();
            }
            catch(Exception ignore) { }
        }
//        break MISSING_BLOCK_LABEL_51;
//    //   17   30:goto            51
//        Exception exception;
////        exception;
    //   18   33:astore          5

//        if(squery != null){
//            try {
//                squery.close();
//            }
//            catch(Exception ignore) { }
//        }
//        throw exception;
    //   25   48:aload           5
    //   26   50:athrow
        return str;
    }

    protected void openConnection()
        throws java.sql.SQLException
    {
        if(conn != null) {
            throw new SQLException("SimpleQuery InvalidState [conn != null]");
        }
        else {
            conn = getConnectionFromDataSource();
        }
    }

    protected java.sql.Connection getConnection()
    {
        return conn;
    }

    protected void closeConnection()
    {
        if(stmt != null) {
            try {
                stmt.close();
            }
            catch(java.sql.SQLException ignore) {
            }
            stmt = null;
        }

        if(conn != null) {
            try {
                conn.close();
            }
            catch(java.sql.SQLException ignore) {
            }

            conn = null;
        }
    }

    public void flush()
    {
        closeConnection();
    }

    public void close()
        throws java.io.IOException
    {
        closeConnection();

        super.close();
    }

    protected void finalize()
        throws Throwable
    {
        closeConnection();

        super.finalize();
    }
}
