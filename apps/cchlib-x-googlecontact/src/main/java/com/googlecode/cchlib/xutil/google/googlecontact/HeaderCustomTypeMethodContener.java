package com.googlecode.cchlib.xutil.google.googlecontact;

import com.googlecode.cchlib.xutil.google.googlecontact.analyser.TypeInfo;

public interface HeaderCustomTypeMethodContener extends HeaderMethodContener, Iterable<HeaderMethodContener>
{
    String getHeader();

    String getPrefix();
    int getPosition();
    String getSuffix();

    void put( int index, HeaderMethodContener methodContener );
    HeaderMethodContener get( int index );
    int size();

    TypeInfo getTypeInfo();
}
