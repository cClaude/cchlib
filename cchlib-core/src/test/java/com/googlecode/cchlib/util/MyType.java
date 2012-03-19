package com.googlecode.cchlib.util;

class MyType
{
    private int content;

    MyType( int content )
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
}