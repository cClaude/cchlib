package com.googlecode.cchlib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
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
     * @param enumeration the enumeration whose elements are to be placed into this list
     * @return an unmodifiable List
     * @since 4.2
     */
    @NeedTestCases
    @Nonnull
    public static <T> List<T> unmodifiableList(
        @Nullable final Enumeration<T> enumeration
        )
    {
        return Collections.unmodifiableList( toList( enumeration ) );
    }

    /**
     * Convert result of an {@link Enumeration} to a {@link List}
     * @param enumeration the enumeration whose elements are to be placed into this list
     * @return a List
     * @since 4.2
     */
    @NeedTestCases
    @Nonnull
    public static <T> List<T> toList( @Nullable final Enumeration<T> enumeration )
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
     * Test if a {@link Collection} is empty
     * @param collection Collection to test
     * @return true if collection is empty or null
     * @since 4.2
     */
    public static <T> boolean isEmpty( @Nullable  final Collection<T> collection )
    {
        return (collection == null) || collection.isEmpty();
    }
}
