package com.googlecode.cchlib.i18n.core.resolve;

public interface Values extends Iterable<String> 
{
    public String get( int i );
    public int size();
    public String[] toArray();
}
