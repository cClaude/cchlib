package com.googlecode.cchlib.sql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;

abstract class AbstractDataSource implements DataSource {

    private final String url;
    private final String username;
    private final String password;
    private final Logger parentLogger;

    private int loginTimeout;
    private PrintWriter logWriter;

    AbstractDataSource( //
            final String      url, //
            final String      username, //
            final String      password, //
            final Logger      parentLogger, //
            final int         loginTimeout, //
            final PrintWriter logWriter //
            )
    {
        this.url          = url;
        this.username     = username;
        this.password     = password;
        this.parentLogger = parentLogger;
        this.loginTimeout = loginTimeout;
        this.logWriter    = logWriter;
    }

    protected String getUrl()
    {
        return this.url;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException
    {
        return this.logWriter;
    }

    @Override
    public void setLogWriter( final PrintWriter out ) throws SQLException
    {
        this.logWriter = out;
    }

    @Override
    public void setLoginTimeout( final int seconds ) throws SQLException
    {
        this.loginTimeout = seconds;
    }

    @Override
    public int getLoginTimeout() throws SQLException
    {
        return this.loginTimeout;
    }

    @Override
    public Connection getConnection() throws SQLException
    {
        return getConnection( this.username, this.password );
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
        throw new SQLException( "unwrap() not supported" );
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException
    {
        if( this.parentLogger == null ) {
            throw new SQLFeatureNotSupportedException( "getParentLogger() not supported");
        }

        return this.parentLogger;
    }
}
