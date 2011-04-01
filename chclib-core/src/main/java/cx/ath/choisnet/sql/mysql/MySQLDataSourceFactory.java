package cx.ath.choisnet.sql.mysql;

import java.io.PrintWriter;
import javax.sql.DataSource;
import cx.ath.choisnet.ToDo;
import cx.ath.choisnet.sql.DataSourceFactory;

/**
 * TODO: Doc!
 *
 * @author Claude CHOISNET
 */
@ToDo
public class MySQLDataSourceFactory
{
    //private final static Logger slogger = Logger.getLogger( MySQLDataSourceFactory.class );

    private MySQLDataSourceFactory()
    {// All static
    }

    /**
     *
     * @param url
     * @param username
     * @param password
     * @param logger
     * @return
     * @throws ClassNotFoundException
     */
    public static DataSource buildMySQLDataSource(
            final String url,
            final String username,
            final String password,
            final PrintWriter logger
            )
        throws ClassNotFoundException
    {
        return DataSourceFactory.buildDataSource(
                "org.gjt.mm.mysql.Driver",
                url,
                username,
                password,
                logger
                );
    }

    /**
     *
     * @param dbHostName
     * @param dbName
     * @param username
     * @param password
     * @param logger
     * @return
     * @throws ClassNotFoundException
     */
    public static DataSource buildMySQLDataSource(
            final String dbHostName,
            final String dbName,
            final String username,
            final String password,
            final PrintWriter logger
            )
        throws ClassNotFoundException
    {
        return MySQLDataSourceFactory.buildMySQLDataSource(
                dbHostName,
                3306,
                dbName,
                username,
                password,
                logger
                );
    }

    /**
     *
     * @param dbHostName
     * @param dbPort
     * @param dbName
     * @param username
     * @param password
     * @param logger
     * @return
     * @throws ClassNotFoundException
     */
    public static DataSource buildMySQLDataSource(
            final String      dbHostName,
            final int         dbPort,
            final String      dbName,
            final String      username,
            final String      password,
            final PrintWriter logger
            )
        throws ClassNotFoundException
    {
        return MySQLDataSourceFactory.buildMySQLDataSource(
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
            password,
            logger
            );
    }
}
