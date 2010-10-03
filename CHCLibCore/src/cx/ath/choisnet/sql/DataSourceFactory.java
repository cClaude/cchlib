package cx.ath.choisnet.sql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import cx.ath.choisnet.ToDo;

/**
 *
 * @author Claude CHOISNET
 *
 */
@ToDo
public class DataSourceFactory
{

    private DataSourceFactory()
    {// All static
    }

    public static DataSource buildDataSource(
            final String driverClassName,
            final String url,
            final String username,
            final String password
            )
        throws ClassNotFoundException
    {
        Class.forName(driverClassName);

        return new DataSource() {
            int timeOut = 30;

            PrintWriter pw = new PrintWriter(System.out);

            public Connection getConnection()
                throws java.sql.SQLException
            {
                return getConnection(username, password);
            }

            public Connection getConnection(String username, String password)
                throws java.sql.SQLException
            {
                Connection conn = null;

                do {
                    if(conn != null && !conn.isClosed()) {
                        break;
                    }

                    conn = DriverManager.getConnection(url, username, password);

                    if(conn.isClosed() && pw != null) {
                        pw.println("*** Recuperation d'une connection fermee !");
                    }
                } while(true);

                return conn;
            }

            public int getLoginTimeout()
            {
                return timeOut;
            }

            public void setLoginTimeout(int seconds)
            {
                timeOut = seconds;
            }

            public java.io.PrintWriter getLogWriter()
            {
                return pw;
            }

            public void setLogWriter(java.io.PrintWriter out)
            {
                pw = out;
            }
            @Override
            public boolean isWrapperFor( Class<?> clazz )
                throws SQLException
            {
                return false;
            }
            @Override
            public <T> T unwrap( Class<T> clazz )
                throws SQLException
            {
                throw new SQLException();
            }
        };
    }

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
                .append(":")
                .append(dbPort)
                .append("/")
                .append(dbName)
                .append("?autoReconnect=true")
                .toString(),
            username,
            password
            );
    }
}
