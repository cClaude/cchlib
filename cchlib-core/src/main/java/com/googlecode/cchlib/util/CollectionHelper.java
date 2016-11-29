package com.googlecode.cchlib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.googlecode.cchlib.NeedTestCases;

/**
 * Give some missing feature in {@link Collections}
 *
 * @since 4.2
 */
public final class CollectionHelper
{
    private CollectionHelper() {}

    /**
     * Create an unmodifiable {@link List} from a {@link Collection}
     *
     * @param <T> the class of the objects in the list
     * @param collection the collection whose elements are to be placed into this list
     * @return an unmodifiable List
     * @since 4.2
     */
    @NeedTestCases
    @Nonnull
    public static <T> List<T> unmodifiableList(
        @Nullable final Collection<T> collection
        )
    {
        if( collection == null ) {
            return Collections.emptyList();
            }
        else {
            final List<T> list;

            if( collection instanceof List ) {
                list = (List<T>)collection;
                }
            else {
                list = new ArrayList<>(collection);
                }

            return Collections.unmodifiableList( list );
        }
     }

    /**
     * Convert result of an {@link Enumeration} to an unmodifiable {@link List}
     * @param <T> the class of the objects in the list
     * @param enumeration the enumeration whose elements are to be placed into this list
     * @return an unmodifiable List
     * @since 4.2
     */
    @Nonnull
    public static <T> List<T> unmodifiableList(
        @Nullable final Enumeration<T> enumeration
        )
    {
        return Collections.unmodifiableList( newList( enumeration ) );
    }

    /**
     * @param <T> deprecated
     * @param enumeration deprecated
     * @return deprecated
     * @deprecated use {@link #newList(Enumeration)} instead
     */
    @Deprecated
    public static <T> List<T> toList( @Nullable final Enumeration<T> enumeration )
    {
        return newList( enumeration );
    }

    /**
     * Create a {@link List} from an {@link Enumeration}
     * @param <T> the class of the objects in the list
     * @param enumeration the enumeration whose elements are to be placed into this list
     * @return a List
     * @since 4.2
     */
    @Nonnull
    public static <T> List<T> newList( @Nullable final Enumeration<T> enumeration )
    {
        final List<T> list = new ArrayList<>();

        if( enumeration != null ) {
            while( enumeration.hasMoreElements() ) {
                list.add( enumeration.nextElement() );
            }
        }

        return list;
     }

    /**
     * Create a {@link List} from an {@link Iterator}
     * @param <T> the class of the objects in the list
     * @param iterator the iterator whose elements are to be placed into this list
     * @return a List
     * @since 4.2
     */
    public static <T> List<T> newList( @Nullable final Iterator<T> iterator )
    {
        final List<T> list = new ArrayList<>();

        if( iterator != null ) {
            while( iterator.hasNext() ) {
                list.add( iterator.next() );
            }
        }

        return list;
    }

    /**
     * Test if a {@link Collection} is empty
     * @param <T> the class of the objects in the list
     * @param collection Collection to test
     * @return true if collection is empty or null
     * @since 4.2
     */
    public static <T> boolean isEmpty( @Nullable  final Collection<T> collection )
    {
        return (collection == null) || collection.isEmpty();
    }
}
