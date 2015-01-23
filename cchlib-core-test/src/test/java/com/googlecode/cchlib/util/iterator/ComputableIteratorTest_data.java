package com.googlecode.cchlib.util.iterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ComputableIteratorTest_data {
    protected static final int NULL_INDEX = 3;
    protected static final int MAX = 5;

    private static final List<Integer> createList()
    {
        final List<Integer> list = new ArrayList<>( MAX );

        for( int i = 0; i < MAX; i++ ) {
            list.add( Integer.valueOf( i ) );
        }

        return list;
    }

    private List<Integer> createListWithNull()
    {
        final List<Integer> list = createList();

        list.set( NULL_INDEX, null );

        return list;
    }

    protected static final Iterator<Integer> createListIterator()
    {
        return Collections.unmodifiableCollection( createList() ).iterator();
    }

    protected Iterator<Integer> createListIteratorWithNull()
    {
        return Collections.unmodifiableCollection( createListWithNull() ).iterator();
    }

    protected Iterator<Integer> createComputableIterator()
    {
        return new MyComputableIterator<Integer>( createList() );
    }

    protected Iterator<Integer> createComputableIteratorWithNull()
    {
        return new MyComputableIterator<Integer>( createListWithNull() );
    }

}
