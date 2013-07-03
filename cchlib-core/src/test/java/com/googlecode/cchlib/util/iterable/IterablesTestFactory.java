package com.googlecode.cchlib.util.iterable;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrapperException;
import com.googlecode.cchlib.util.iterator.Selectable;

class IterablesTestFactory
{
    private IterablesTestFactory() {}

    public static Collection<Integer> createIterable()
    {
        HashSet<Integer> iterable = new HashSet<Integer>();

        for( int i = 0; i<10000; i++ ) {
            iterable.add( Integer.valueOf( i ) );
            }

        return iterable;
    }

    public static Selectable<Integer> createFilter()
    {
        return new Selectable<Integer>() {
            @Override
            public boolean isSelected( Integer obj )
            {
                return (obj.intValue() % 2) == 0;
            }
        };
    }
    
    public static Selectable<String> createFilterRemoveEntryIfStringEndWithZero()
    {
        return new Selectable<String>() {
            @Override
            public boolean isSelected( String obj )
            {
                return obj.charAt( obj.length() - 1 ) != '0';
            }
        };
    }

    public static Wrappable<Integer,String> createWrappableIntegerToString()
    {
        return new Wrappable<Integer,String>() {
            @Override
            public String wrap( Integer obj ) throws WrapperException
            {
                return obj.toString();
            }};
    }

    public static Wrappable<String,Integer> createWrappableStringToInteger()
    {
        return new Wrappable<String,Integer>() {
            @Override
            public Integer wrap( String obj ) throws WrapperException
            {
                return new Integer( obj );
            }};
    }

    public static Comparator<String> createDescendingComparatorForString()
    {
        return new Comparator<String>() {
            @Override
            public int compare( String o1, String o2 )
            {
                return o2.compareTo( o1 ); // change natural order !
            }
        };
    }

    public static Comparator<Integer> createComparatorForInteger()
    {
        return new Comparator<Integer>() {
            @Override
            public int compare( Integer o1, Integer o2 )
            {
                return o1.compareTo( o2 );
            }
        };
    }
}
