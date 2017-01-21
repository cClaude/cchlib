package com.googlecode.cchlib.util.populator.test2;


public class MyInterfaceImpl implements MyInterface
{
    private String schemaName;
    private String hostname;
    private int port;
    private String username;
    private String password;

    @Override
    public String getSchemaName()
    {
        return this.schemaName;
    }

    @Override
    public String getHostname()
    {
        return this.hostname;
    }

    @Override
    public int getPort()
    {
        return this.port;
    }

    @Override
    public String getUsername()
    {
        return this.username;
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    public void setSchemaName( final String schemaName )
    {
        this.schemaName = schemaName;
    }

    public void setHostname( final String hostname )
    {
        this.hostname = hostname;
    }

    public void setPort( final int port )
    {
        this.port = port;
    }

    public void setUsername( final String username )
    {
        this.username = username;
    }

    public void setPassword( final String password )
    {
        this.password = password;
    }

}
