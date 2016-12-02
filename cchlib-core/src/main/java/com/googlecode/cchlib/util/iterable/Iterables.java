package com.googlecode.cchlib.util.iterable;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.NeedTestCases;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrapperException;
import com.googlecode.cchlib.util.iterator.EnumerationIterator;
import com.googlecode.cchlib.util.iterator.IteratorFilter;
import com.googlecode.cchlib.util.iterator.IteratorWrapper;
import com.googlecode.cchlib.util.iterator.Selectable;

/**
 * This class contains static utility methods that operate on or return objects of type {@link Iterable}.
 *
 * @see XIterables
 * @since 1.4.8
 */
@NeedDoc
@NeedTestCases
public class Iterables
{
    private Iterables() {}

    /**
     * Create an {@link Iterable} from an {@link Enumeration}
     *
     * @param <T>
     *            Type of the Enumeration
     * @param enumeration
     *            {@link Enumeration} to use
     * @return a new {@link Iterable}
     *         <P>
     *         <B>Warn:</B>This {@link Iterable} object could be use only once
     *         </P>
     */
    public static <T> Iterable<T> create( //
        @Nonnull final Enumeration<T> enumeration //
        )
    {
        return new OnlyOnceIterable<>( new EnumerationIterator<>( enumeration ) );
    }

    /**
     * Create an {@link Iterable} from an {@link Iterator}
     *
     * @param <T>
     *            Type of the Iterator
     * @param iterator
     *            {@link Iterable} to use
     * @return a new {@link Iterable}
     *         <P>
     *         <B>Warn:</B>This {@link Iterable} object could be use only once
     *         </P>
     */
    public static <T> Iterable<T> create( @Nonnull final Iterator<T> iterator )
    {
        return new OnlyOnceIterable<>( iterator );
    }

    /**
     * Create an {@link Iterable} from an arrays of values
     *
     * @param <T>
     *            Type of the Iterator
     * @param type
     *            Type of content
     * @param values
     *            Values for the {@link Iterable}
     * @return a new {@link Iterable}
     *
     * @since 4.2
     */
    @SafeVarargs
    public static <T> Iterable<T> create( final T...values )
    {
        return new ArrayIterable<T>( values );
    }

    /**
     * {@link #wrap(Iterable, Wrappable)} for {@link Iterator} objects
     *
     * @param <S>
     *            Type of the source Iterator
     * @param <R>
     *            Type of the result Iterator
     * @param iterator
     *            {@link Iterable} to use
     * @param wrapper
     *            Wrapper to use
     * @return a new {@link Iterable}
     * @throws WrapperException
     *             if any
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public static <S,R> Iterable<R> wrap(
            @Nonnull final Iterator<S> iterator,
            @Nonnull final Wrappable<? super S,? extends R> wrapper
            )
        throws WrapperException
    {
        return wrap( Iterables.create( iterator ), wrapper );
    }

    /**
     * {@link #wrap(Iterable, Wrappable)} for {@link Enumeration} objects
     *
     * @param <S>
     *            Type of the source Iterator
     * @param <R>
     *            Type of the result Iterator
     * @param enumeration
     *            {@link Enumeration} to use
     * @param wrapper
     *            Wrapper to use
     * @return a new {@link Iterable}
     * @throws WrapperException
     *             if any
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public static <S,R> Iterable<R> wrap( //
        @Nonnull final Enumeration<S>                   enumeration, //
        @Nonnull final Wrappable<? super S,? extends R> wrapper //
        ) throws WrapperException
    {
        return wrap( Iterables.create( enumeration ), wrapper );
    }

    /**
     * Guava like function for {@link #wrap(Iterable, Wrappable)}
     *
     * @param <F>
     *            Type of the source Iterator
     * @param <T>
     *            Type of the result Iterator
     * @param fromIterable
     *            {@link Iterable} to use
     * @param function
     *            Wrapper to use
     * @return a new {@link Iterable}
     */
    public static <F,T> Iterable<T> transform( //
        @Nonnull final Iterable<F>                      fromIterable, //
        @Nonnull final Wrappable<? super F,? extends T> function //
        )
    {
        return wrap( fromIterable, function );
    }

    /**
     * Returns an {@link Iterable} that applies <code>wrapper</code> to each element of fromIterable.
     * <p>
     * The returned iterable's iterator supports remove() if the provided iterator does. After a successful remove()
     * call, <code>iterable</code> no longer contains the corresponding element.
     * </p>
     *
     * @param <S>
     *            Type of the source Iterator
     * @param <R>
     *            Type of the result Iterator
     * @param iterable
     *            Original {@link Iterable}
     * @param wrapper
     *            Wrapper to use
     * @return an {@link Iterable} that applies <code>wrapper</code> to each element of fromIterable.
     * @throws WrapperException
     *             if any
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public static <S,R> Iterable<R> wrap(
        @Nonnull final Iterable<S>                      iterable,
        @Nonnull final Wrappable<? super S,? extends R> wrapper
        ) throws WrapperException
    {
        return () -> new IteratorWrapper<>(iterable.iterator(), wrapper);
    }

    /**
     * {@link #filter(Iterable, Selectable)} for {@link Iterator} objects
     *
     * @param <T>
     *            Type of the Iterator
     * @param iterator
     *            Original {@link Iterator}
     * @param filter
     *            Filter to use
     * @return a new {@link Iterable}
     */
    public static <T> Iterable<T> filter( //
        @Nonnull final Iterator<T>   iterator, //
        @Nonnull final Selectable<T> filter //
        )
    {
        return filter( Iterables.create( iterator ), filter );
    }

    /**
     * {@link #filter(Iterable, Selectable)} for {@link Enumeration} objects
     *
     * @param <T>
     *            Type of the Enumeration
     * @param enumeration
     *            Original {@link Enumeration}
     * @param filter
     *            Filter to use
     * @return a new {@link Iterable}
     */
    public static <T> Iterable<T> filter( //
        @Nonnull final Enumeration<T> enumeration, //
        @Nonnull final Selectable<T>  filter //
        )
    {
        return filter( Iterables.create( enumeration ), filter );
    }

    /**
     * Returns the elements of <code>unfiltered</code> that satisfy a filter.
     * The resulting iterable's iterator does not support remove().
     *
     * @param <T>
     *            Type of the Iterable
     * @param unfiltered
     *            Original {@link Iterable}
     * @param filter
     *            Filter to use
     * @return a new {@link Iterable}
     */
    public static <T> Iterable<T> filter(
        @Nonnull final Iterable<T>           unfiltered,
        @Nonnull final Selectable<? super T> filter
        )
    {
        return () -> new IteratorFilter<>(unfiltered.iterator(), filter);
    }

    @NeedDoc
    public static <T> List<T> newList( @Nonnull final Iterable<T> iterable )
    {
        final ArrayList<T> list = new ArrayList<>();

        for( final T entry : iterable ) {
            list.add( entry );
            }

        return list;
    }

    @NeedDoc
    @SuppressWarnings("null")
    @Nullable
    public static <T> T find( //
        @Nonnull final Iterable<T>   iterable, //
        @Nonnull final Selectable<T> filter //
        )
    {
        for( final T element : iterable ) {
            if( filter.isSelected( element ) ) {
                return element;
            }
        }

        return null;
    }
}
