package com.googlecode.cchlib.sql.mysql;

import java.util.Collection;

/**
 * Allow to create custom MySQL configuration
 *
 * @see MySQL#getURL(String, int, Collection)
 * @see MySQLParameters
 */
public interface MySQLParametersConfig
{
    /**
     * @return Configuration String for one parameter of MySQL URL
     */
    String getParameterConfig();

}
