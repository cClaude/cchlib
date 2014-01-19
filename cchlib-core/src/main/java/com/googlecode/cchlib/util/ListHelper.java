package com.googlecode.cchlib.util;

import java.util.AbstractList;
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
            list = new ListFromCollection<T>(collection);
            }

        return Collections.unmodifiableList( list );
     }

    private static final class ListFromCollection<T> extends AbstractList<T>
    {
        private T[] array;

        public ListFromCollection( final Collection<T> collection )
        {
            @SuppressWarnings("unchecked")
            final T[] genArray = (T[])new Object[collection.size()];
            this.array = collection.toArray( genArray  );
        }

        @Override
        public T get( final int index )
        {
            return array[ index ];
        }

        @Override
        public int size()
        {
            return array.length;
        }

    }
}
