package com.googlecode.cchlib.util.iterable;

import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.util.iterator.CascadingIterator;
import java.util.Iterator;

/**
 *
 */
@NeedDoc
public class CascadingIterable<T> implements Iterable<T>
{
    private final Iterable<? extends T>[] iterables;

    public CascadingIterable( Iterable<? extends T>[] iterables )
    {
        this.iterables = iterables; // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.mutabilityOfArrays
    }

    //private static <X> X[] newArray( Class<X> clazz,  )

    /* (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<T> iterator()
    {
        return new CascadingIterator<>( iterables );
    }
}
