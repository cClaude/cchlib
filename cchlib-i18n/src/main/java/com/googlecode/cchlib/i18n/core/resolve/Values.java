package com.googlecode.cchlib.i18n.core.resolve;

public interface Values extends Iterable<String>
{
    String get( int i );
    int size();
    String[] toArray();
}
