package com.googlecode.cchlib.xutil.google.googlecontact;

import com.googlecode.cchlib.xutil.google.googlecontact.analyser.TypeInfo;

public interface HeaderCustomTypeMethodContener extends /*CustomTypeMethodContener,*/ HeaderMethodContener, Iterable<HeaderMethodContener> {
    String getHeader();

    String getPrefix();
    int getPosition();
    String getSuffix();
//    void setMaxEntriesDefineInHeader( int parseInt );
//    int getMaxEntriesDefineInHeader();

    void put( int index, HeaderMethodContener methodContener );
    HeaderMethodContener get( int index );
    int size();

    TypeInfo getTypeInfo();
}
