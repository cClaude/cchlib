package com.googlecode.cchlib.i18n.core.resolve;

public interface Keys extends Iterable<String> 
{
    //@Deprecated public String getFirstKey();
    public String get( int i );
    public int size();
}
