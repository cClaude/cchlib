package com.googlecode.cchlib.sql.mysql;


/**
 * Configuration interface for {@link MySQL}
 *
 * @since 4.2
 *
 * @see MySQL#newDataSource(MySQLConfig)
 * @see MySQL#newDataSource(String, String, String, String, int)
 */
public interface MySQLConfig
{
    /**
     * @return MySQL Host name (or IP)
     */
    String getHostname();

    /**
     * @return MySQL port (see {@link MySQL#DEFAULT_PORT})
     */
    int getPort();

    MySQLParameters getMySQLParameters();

    /**
     * @return user name for MySQL database
     */
    String getUsername();

    /**
     * @return related password for {@link #getUsername()}
     */
    String getPassword();

    /**
     * @return timeout value for MySQL (see {@link MySQL#DEFAULT_TIMEOUT})
     */
    int getTimeout();
}
