package com.googlecode.cchlib.i18n.core.resolve;

import com.googlecode.cchlib.NeedDoc;

@NeedDoc
public interface Keys extends Iterable<String>
{
    @NeedDoc
    String get( int i );

    @NeedDoc
    int size();
}
