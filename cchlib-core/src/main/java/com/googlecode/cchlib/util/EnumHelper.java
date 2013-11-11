package com.googlecode.cchlib.util;

import java.util.Collection;
import java.util.EnumSet;
import javax.annotation.Nullable;

/**
 *
 * @since.1.4.8
 */
public final class EnumHelper
{
    private EnumHelper(){}

    /**
     * @param values a {@link Collection} of values or null
     * @param valuesClass Enum class
     * @return a none null {@link EnumSet} of <code>valuesClass</code>.
     */
    public static  <T extends Enum<T>> EnumSet<T> getSafeEnumSet(
        @Nullable final Collection<T> values,
        final Class<T>                valuesClass
        )
    {
        if( values == null ) {
            return EnumSet.noneOf( valuesClass );
            }
        else {
            return EnumSet.copyOf( values );
            }
    }

    /**
     * TODOC
     *
     * @param enumValue
     * @param stringValue
     * @return
     */
    public static <T extends Enum<T>> String getSuffix(
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

    private static <T extends Enum<T>> boolean isPrefixOf(
        final T      enumValue,
        final String stringValue
        )
    {
        return stringValue.startsWith( enumValue.name() );
    }

    public static <T extends Enum<T>> Integer getSuffixInteger(
        final T      enumValue,
        final String stringValue
        )
    {
        final String suffix = getSuffix( enumValue, stringValue );

        if( suffix != null ) {
            return Integer.valueOf( suffix );
            }
        else {
            return null;
            }
    }

}
