package com.googlecode.cchlib.util.iterable;

import java.util.Iterator;
import com.googlecode.cchlib.util.iterator.IteratorFilter;
import com.googlecode.cchlib.util.iterator.Selectable;

/*public*/ 
class IterableFilter<T> implements Iterable<T>
{
    private Iterable<T>           iterable;
    private Selectable<? super T> filter;

    public IterableFilter( Iterable<T> iterable, Selectable<? super T> filter )
    {
        this.iterable = iterable;
        this.filter   = filter;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new IteratorFilter<T>( iterable.iterator(), filter );
    }

}
