package com.googlecode.cchlib.sandbox.guice.sample1;


public class Player
{
    private String name;

    public Player()
    {
         // bean
    }

    @Override
    public String toString()
    {
        return getName();
    }

    public String getName()
    {
        return this.name;
    }

    public void setName( final String name )
    {
        this.name = name;
    }
}
