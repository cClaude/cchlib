package com.googlecode.cchlib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class ListHelper
{
    private ListHelper() {}

    public static <T> List<T> unmodifiableList(final Collection<T> collection )
    {
        List<T> list;

        if( collection instanceof List ) {
            list = (List<T>)collection;
            }
        else {
            list = new ArrayList<T>(collection);
            }

        return Collections.unmodifiableList( list );
     }

}
