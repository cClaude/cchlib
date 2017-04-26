package com.googlecode.cchlib.sql.mysql;


/**
 * Allow to create custom MySQL configuration
 *
 * @see MySQL#getURL(String, int, MySQLParameters)
 * @see MySQLParameters
 *
 * @since 4.2
 */
@SuppressWarnings("squid:S1609") // Not a functional interface
public interface MySQLStandardParametersConfig
{
    /**
     * @return Configuration String for one parameter of MySQL URL
     */
    String getParameterConfig();
}
