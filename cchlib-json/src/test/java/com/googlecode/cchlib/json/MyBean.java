package com.googlecode.cchlib.json;

import java.util.Collection;

class MyBean
{
    private String                       string;
    private int                          interger;
    private Collection<BeanInCollection> collection;

    public String getString()
    {
        return this.string;
    }

    public MyBean setString( final String string )
    {
        this.string = string;
        return this;
    }

    public int getInterger()
    {
        return this.interger;
    }

    public MyBean setInterger( final int interger )
    {
        this.interger = interger;
        return this;
    }

    public Collection<BeanInCollection> getCollection()
    {
        return this.collection;
    }

    public MyBean setCollection( final Collection<BeanInCollection> collection )
    {
        this.collection = collection;
        return this;
    }
}
