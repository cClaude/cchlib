package com.googlecode.cchlib.util;

import java.util.Collection;
import java.util.EnumSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @since 1.4.8
 */
public final class EnumHelper
{
    private EnumHelper(){}

    /**
     * Build a valid {@link EnumSet} based on a {@link Collection}
     * of {@link Enum} values.
     *
     * @param <T>
     *            Type of the {@link Enum}
     * @param values
     *            a {@link Collection} of values or null (null will be handle
     *            in the same way than an empty collection)
     * @param valuesType
     *            Enum class
     * @return a none null {@link EnumSet} of {@code valuesClass}.
     */
    @SuppressWarnings("squid:S1319") // return an EnumSet (not a Set) to have same erasure than JDK
    @Nonnull
    public static <T extends Enum<T>> EnumSet<T> safeCopyOf(
        @Nullable final Collection<T> values,
        @Nonnull final Class<T>       valuesType
        )
    {
        if( (values == null) || values.isEmpty() ) {
            return EnumSet.noneOf( valuesType );
            }
        else {
            return EnumSet.copyOf( values );
            }
    }

    /**
     * Build a valid {@link EnumSet} based on an array
     * of {@link Enum} values.
     *
     * @param <T>
     *            Type of the {@link Enum}
     * @param values
     *            an array of values or null (null will be handle
     *            in the same way than an empty array)
     * @param valuesType
     *            Enum class
     * @return a none null {@link EnumSet} of {@code valuesClass}.
     */
    @SuppressWarnings("squid:S1319") // return an EnumSet (not a Set) to have same erasure than JDK
    @Nonnull
    public static <T extends Enum<T>> EnumSet<T> safeCopyOf(
        @Nullable final T[]     values,
        @Nonnull final Class<T> valuesType
        )
    {
        final EnumSet<T> enumSet = EnumSet.noneOf( valuesType );

        if( (values != null) && (values.length > 0) ) {
            for( final T value : values ) {
                enumSet.add( value );
            }
        }

        return enumSet;
    }

    private static <T extends Enum<T>> boolean isPrefixOf(
        final T      enumValue,
        final String stringValue
        )
    {
        return stringValue.startsWith( enumValue.name() );
    }

    /**
     * Extract {@link Integer} value found immediately after {@link Enum} name
     * in {@code stringValue}
     *
     * @param <T>
     *            an {@link Enum}
     * @param enumValue
     *            Value
     * @param stringValue
     *            String that should begin with the {@link Enum#name()}
     * @return {@link Integer} value found immediately after {@link Enum} name or null
     * @throws NumberFormatException
     *             if the value cannot be parsed as an integer.
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public static <T extends Enum<T>> Integer getSuffixInteger(
        final T      enumValue,
        final String stringValue
        ) throws NumberFormatException
    {
        final String suffix = getSuffix( enumValue, stringValue );

        if( suffix != null ) {
            return Integer.valueOf( suffix );
            }
        else {
            return null;
            }
    }

    private static <T extends Enum<T>> String getSuffix(
        final T      enumValue,
        final String stringValue
        )
    {
        if( isPrefixOf( enumValue, stringValue ) ) {
            return stringValue.substring( enumValue.name().length() );
            }
        else {
            return null;
            }
    }
}
