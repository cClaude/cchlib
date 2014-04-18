package com.googlecode.cchlib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.annotation.Nullable;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.NeedTestCases;

@NeedDoc
public final class ListHelper
{
    private ListHelper() {}

    @NeedDoc
    @NeedTestCases
    public static <T> List<T> unmodifiableList( @Nullable final Collection<T> collection )
    {
        if( collection == null ) {
            return Collections.emptyList();
            }
        else {
            final List<T> list;

            if( collection instanceof List ) {
                list = (List<T>)collection;
                }
            else {
                list = new ArrayList<>(collection);
                }

            return Collections.unmodifiableList( list );
        }
     }

    @NeedDoc
    @NeedTestCases
    public static <T> List<T> unmodifiableList(final Enumeration<T> enumeration )
    {
        return Collections.unmodifiableList( toList( enumeration ) );
    }

    @NeedDoc
    @NeedTestCases
    public static <T> List<T> toList( @Nullable final Enumeration<T> enumeration )
    {
        final List<T> list = new ArrayList<>();

        if( enumeration != null ) {
            while( enumeration.hasMoreElements() ) {
                list.add( enumeration.nextElement() );
            }
        }

        return list;
     }
}
