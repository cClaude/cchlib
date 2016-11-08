package com.googlecode.cchlib.util.iterable;

import java.util.Iterator;
import com.googlecode.cchlib.util.iterator.IteratorFilter;
import com.googlecode.cchlib.util.iterator.Selectable;

/*public*/
class IterableFilter<T> implements Iterable<T>
{
    private final Iterable<T>           iterable;
    private final Selectable<? super T> filter;

    public IterableFilter( final Iterable<T> iterable, final Selectable<? super T> filter )
    {
        this.iterable = iterable;
        this.filter   = filter;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new IteratorFilter<>( iterable.iterator(), filter );
    }
}
