package com.googlecode.cchlib.util;

import java.io.Serializable;
import java.util.Comparator;

class MyType implements Comparable<MyType>, Serializable
{
    private static final long serialVersionUID = 1L;
    private static Comparator<MyType> COMPARATOR = new MyTypeComparator();
    
    private int content;

    MyType( final int content )
    {
        this.content = content;
    }

    @Override
    public String toString()
    {
        return "MyType [content=" + content + "]";
    }

    public int getContent()
    {
        return content;
    }

    @Override
    public int compareTo( final MyType o )
    {
    	return COMPARATOR.compare( this, o );
    }
}

class MyTypeComparator implements Comparator<MyType>
{
	@Override
	public int compare( final MyType o1, final MyType o2 )
	{
        return o1.getContent() - o2.getContent();
	}
	
}