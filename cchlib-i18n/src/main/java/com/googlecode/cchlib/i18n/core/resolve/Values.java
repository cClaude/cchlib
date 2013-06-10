package com.googlecode.cchlib.i18n.core.resolve;

public interface Values extends Iterable<String> 
{
    //@Deprecated public String getFirstValue();
    public String get( int i );
    public int size();
}
