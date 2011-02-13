package cx.ath.choisnet.sql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import cx.ath.choisnet.ToDo;

/**
 * TODO: Doc!
 * 
 * @author Claude CHOISNET
 */
@ToDo
public class DataSourceFactory
{
    private final static Logger slogger = Logger.getLogger( DataSourceFactory.class );
    
    private DataSourceFactory()
    {// All static
    }
    
    /**
     * 
     * @param driverClassName
     * @param url
     * @param username
     * @param password
     * @return
     * @throws ClassNotFoundException
     */
    public static DataSource buildDataSource(
            final String driverClassName,
            final String url,
            final String username,
            final String password
            )
        throws ClassNotFoundException
    {
        return buildDataSource(
                driverClassName,
                url,
                username,
                password,
                new PrintWriter( System.err ) // Mmm : TODO something better
                );
    } 
    
    /**
     * 
     * @param driverClassName
     * @param url
     * @param username
     * @param password
     * @param loggerPrintWriter
     * @return
     * @throws ClassNotFoundException
     */
    public static DataSource buildDataSource(
            final String        driverClassName,
            final String        url,
            final String        username,
            final String        password,
            final PrintWriter   loggerPrintWriter
            )
        throws ClassNotFoundException
    {
        Class.forName(driverClassName);

        return new DataSource() 
        {
            int         timeOut = 30;
            PrintWriter pw      = loggerPrintWriter;
            //PrintWriter pw = new PrintWriter(System.out);
            
            @Override
            public Connection getConnection()
                throws java.sql.SQLException
            {
                return getConnection(username, password);
            }
            @Override
            public Connection getConnection(String username, String password)
                throws java.sql.SQLException
            {
                Connection conn = null;

                do {
                    if(conn != null && !conn.isClosed()) {
                        break;
                    }

                    conn = DriverManager.getConnection(url, username, password);

                    if( conn.isClosed() /*&& pw != null*/) {
                        slogger.error( "*** Connection is closed !" );
                    }
                } while(true);

                return conn;
            }
            @Override
            public int getLoginTimeout()
            {
                return timeOut;
            }
            @Override
            public void setLoginTimeout(int seconds)
            {
                timeOut = seconds;
            }
            @Override
            public PrintWriter getLogWriter()
            {
                return pw;
            }
            @Override
            public void setLogWriter(final PrintWriter out)
            {
                pw = out;
            }
            @Override
            public boolean isWrapperFor( final Class<?> clazz )
                throws SQLException
            {
                return false;
            }
            @Override
            public <T> T unwrap( final Class<T> clazz )
                throws SQLException
            {
                throw new SQLException();
            }
        };
    }

    @Deprecated
    public static DataSource buildMySQLDataSource(
            String url,
            String username,
            String password
            )
        throws ClassNotFoundException
    {
        return DataSourceFactory.buildDataSource(
                "org.gjt.mm.mysql.Driver",
                url,
                username,
                password
                );
    }

    /**
     * 
     * @param dbHostName
     * @param dbName
     * @param username
     * @param password
     * @return
     * @throws ClassNotFoundException
     */
    @Deprecated
    public static DataSource buildMySQLDataSource(
            String dbHostName,
            String dbName,
            String username,
            String password
            )
        throws ClassNotFoundException
    {
        return DataSourceFactory.buildMySQLDataSource(
                dbHostName,
                3306,
                dbName,
                username,
                password
                );
    }

    /**
     * 
     * @param dbHostName
     * @param dbPort
     * @param dbName
     * @param username
     * @param password
     * @return
     * @throws ClassNotFoundException
     */
    @Deprecated
    public static DataSource buildMySQLDataSource(
            String      dbHostName,
            int         dbPort,
            String      dbName,
            String      username,
            String      password
            )
        throws ClassNotFoundException
    {
        return DataSourceFactory.buildMySQLDataSource(
            (new StringBuilder())
                .append("jdbc:mysql://")
                .append(dbHostName)
                .append(':')
                .append(dbPort)
                .append('/')
                .append(dbName)
                .append("?autoReconnect=true")
                .toString(),
            username,
            password
            );
    }
}
