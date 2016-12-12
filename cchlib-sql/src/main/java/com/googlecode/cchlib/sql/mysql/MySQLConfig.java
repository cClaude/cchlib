package com.googlecode.cchlib.sql.mysql;

import java.util.Set;

/**
 * Configuration interface for {@link MySQL}
 *
 * @since 4.2
 * @see MySQL#newConnection(MySQLConfig)
 * @see MySQL#newDataSource(MySQLConfig)
 */
public interface MySQLConfig
{
    String getHostname();
    int getPort();
    Set<MySQLParameters> getParameters();
    String getUsername();
    String getPassword();
    int getTimeout();
}
