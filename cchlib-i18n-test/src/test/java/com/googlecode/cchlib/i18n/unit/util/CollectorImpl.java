package com.googlecode.cchlib.i18n.unit.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.util.iterator.Iterators;

//not public
class CollectorImpl implements Collector, Serializable
{
    private static final long serialVersionUID = 1L;
    private List<Object[]> list = new ArrayList<Object[]>();

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
