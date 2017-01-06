package com.googlecode.cchlib.i18n.unit.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import com.googlecode.cchlib.util.iterator.Iterators;

//not public
@Deprecated
class CollectorImpl implements Collector, Serializable
{
    private static final long serialVersionUID = 1L;

    private final ArrayList<Object[]> list = new ArrayList<>();

    public void add( final Object... values )
    {
        this.list.add( values );
    }

    @Override
    public int size()
    {
        return this.list.size();
    }

    @Override
    public Iterator<Object[]> iterator()
    {
        return Iterators.unmodifiableIterator( this.list.iterator() );
    }
}
