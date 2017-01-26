package com.googlecode.cchlib.sql.mysql;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nonnull;

/**
 * A bean for {@link MySQLConfig}
 *
 * @since 4.2
 */
public class MySQLConfigBean implements MySQLConfig, Serializable
{
    private static final long serialVersionUID = 1L;

    private String               hostname;
    private int                  port = MySQL.DEFAULT_PORT;
    private Set<MySQLParameters> parameters;
    private String               username;
    private String               password;
    private int                  timeout = MySQL.DEFAULT_TIMEOUT;

    @Override
    public String getHostname()
    {
        return this.hostname;
    }

    public MySQLConfigBean setHostname( final String hostname )
    {
        this.hostname = hostname;
        return this;
   }

    @Override
    public int getPort()
    {
        return this.port;
    }

    public MySQLConfigBean setPort( final int port )
    {
        this.port = port;
        return this;
   }

    @Override
    @Nonnull
    public Set<MySQLParameters> getParameters()
    {
        return this.parameters;
    }

    public MySQLConfigBean setParameters( final Set<MySQLParameters> parameters )
    {
        if( parameters== null ) {
            this.parameters = EnumSet.noneOf( MySQLParameters.class );
        } else {
            this.parameters = EnumSet.copyOf( parameters );
        }
        return this;
   }

    @Override
    public String getUsername()
    {
        return this.username;
    }

    public MySQLConfigBean setUsername( final String username )
    {
        this.username = username;
        return this;
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    public MySQLConfigBean setPassword( final String password )
    {
        this.password = password;
        return this;
    }

    @Override
    public int getTimeout()
    {
        return this.timeout;
    }

    public MySQLConfigBean setTimeout( final int timeout )
    {
        this.timeout = timeout;
        return this;
    }
}
