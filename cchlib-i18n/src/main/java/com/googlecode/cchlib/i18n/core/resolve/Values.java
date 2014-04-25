package com.googlecode.cchlib.i18n.core.resolve;

import com.googlecode.cchlib.NeedDoc;

@NeedDoc
public interface Values extends Iterable<String>
{
    @NeedDoc
    String get( int i );

    @NeedDoc
    int size();

    @NeedDoc
    String[] toArray();
}
