package com.googlecode.cchlib.util.iterable;

import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.NeedTestCases;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrapperException;
import com.googlecode.cchlib.util.iterator.Selectable;

/**
 * This class contains static utility methods that operate on or return objects of type {@link XIterable}.
 *
 * @since 1.4.8
 */
@NeedDoc
@NeedTestCases
public final class XIterables
{
    private XIterables() {}

    /**
     * Guava like function for {@link #wrap(Iterable, Wrappable)}
     */
    public static <F,T> XIterable<T> transform(Iterable<F> fromIterable, Wrappable<? super F,? extends T> function)
    {
        return wrap( fromIterable, function );
    }


    /**
     * Returns an {@link XIterable} that applies <code>wrapper</code> to each element of fromIterable.
     * <p>
     * The returned iterable's iterator supports remove() if the provided iterator does.
     * After a successful remove() call, <code>iterable</code> no longer contains the
     * corresponding element.
     * </p>
     * @param iterable
     * @param wrapper
     * @return an {@link XIterable} that applies <code>wrapper</code> to each element of fromIterable.
     * @throws WrapperException if any
     */
    public static <S,R> XIterable<R> wrap( Iterable<S> iterable, Wrappable<? super S,? extends R> wrapper )
        throws WrapperException
    {
        return new XIterableImpl<R>( iterable, wrapper );
    }

    /**
     *
     * @param iterable
     * @param filter
     * @return TODOC
     */
    public static <T> XIterable<T> filter( Iterable<T> iterable, Selectable<T> filter )
    {
        return new XIterableImpl<T>( iterable, filter );
    }

    /**
     * Create an {@link XIterable} from an {@link Iterator}
     * <P><B>Warn:</B>This {@link XIterable} object could be use only once</P>
     * @see XIterable#toList()
     */
    public static <T> XIterable<T> create( final Iterator<T> iterator )
    {
        return new XIterableImpl<T>( Iterables.create( iterator ) );
    }

    /**
     *
     * @param iterable
     * @return TODOC
     */
    public static <T> List<T> newList( Iterable<T> iterable )
    {
        return Iterables.newList( iterable );
    }

}
