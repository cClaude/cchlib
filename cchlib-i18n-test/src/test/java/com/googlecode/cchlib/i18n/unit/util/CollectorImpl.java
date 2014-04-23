package com.googlecode.cchlib.i18n.unit.util;

import com.googlecode.cchlib.util.iterator.Iterators;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//not public
class CollectorImpl implements Collector, Serializable
{
    private static final long serialVersionUID = 1L;
    private final List<Object[]> list = new ArrayList<>();

    public void add( Object... values )
    {
        list.add( values );
    }

    @Override
    public int size()
    {
        return list.size();
    }

    @Override
    public Iterator<Object[]> iterator()
    {
        return Iterators.unmodifiableIterator( list.iterator() );
    }
}
