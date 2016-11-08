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
     *
     * @param <F>           the class of the elements of the source {@link Iterable}
     * @param <T>           the class of the elements of the expected {@link XIterable}
     * @param fromIterable  Source {@link Iterable}
     * @param function      Wrapper to use
     * @return an {@link XIterable} that applies <code>function</code> to each element of fromIterable.
     * @throws WrapperException if any error occurs while wrapping an element
     */
    public static <F,T> XIterable<T> transform( final Iterable<F> fromIterable, final Wrappable<? super F,? extends T> function)
        throws WrapperException
    {
        return wrap( fromIterable, function );
    }

    /**
     * Create an {@link XIterable} that applies <code>wrapper</code> to each element of <code>fromIterable</code>.
     * <p>
     * The returned iterable's iterator supports remove() if the provided iterator does.
     * After a successful remove() call, <code>fromIterable</code> no longer contains the
     * corresponding element.
     * </p>
     *
     * @param <S>           the class of the elements of the source {@link Iterable}
     * @param <R>           the class of the elements of the expected {@link XIterable}
     * @param fromIterable  Iterable object to wrap
     * @param wrapper       Wrapper to use
     * @return an {@link XIterable} that applies <code>wrapper</code> to each element of fromIterable.
     * @throws WrapperException if any error occurs while wrapping an element
     */
    public static <S,R> XIterable<R> wrap( final Iterable<S> fromIterable, final Wrappable<? super S,? extends R> wrapper )
        throws WrapperException
    {
        return new XIterableImpl<>( fromIterable, wrapper );
    }

    /**
     * Create an {@link XIterable} that applies <code>filter</code> to each element of <code>fromIterable</code>.
     *
     * @param <T>           the class of the elements of the {@link Iterable}
     * @param fromIterable  {@link Iterable} object to wrap
     * @param filter        Filter to use
     * @return an {@link XIterable} that applies <code>filter</code> to each element of <code>fromIterable</code>.
     */
    public static <T> XIterable<T> filter( final Iterable<T> fromIterable, final Selectable<T> filter )
    {
        return new XIterableImpl<>( fromIterable, filter );
    }

    /**
     * Create an {@link XIterable} from an {@link Iterator}
     * <P><B>Warn:</B>This {@link XIterable} object could be use only once</P>
     *
     * @param <T>           the class of the elements of the {@link Iterable}
     * @return a new XIterable
     * @see XIterable#toList()
     */
    public static <T> XIterable<T> create( final Iterator<T> iterator )
    {
        return new XIterableImpl<>( Iterables.create( iterator ) );
    }

    /**
     * Create an {@link XIterable} from an {@link Iterable}
     *
     * @param <T>           the class of the elements of the {@link Iterable}
     * @param iterable      Original {@link Iterable}
     * @return a new List
     */
    public static <T> List<T> newList( final Iterable<T> iterable )
    {
        return Iterables.newList( iterable );
    }

}
