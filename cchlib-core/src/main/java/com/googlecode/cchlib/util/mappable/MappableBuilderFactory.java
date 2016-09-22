package com.googlecode.cchlib.util.mappable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Factory for {@link MappableBuilder}
 *
 * @see Mappable
 */
public interface MappableBuilderFactory extends Serializable
{
    /**
     * Returns {@link String} representation of null
     * @return {@link String} representation of null
     */
    String getStringNullValue();

    /**
     * Returns {@link String} representation for an {@link Iterator}
     * @return {@link String} representation for an {@link Iterator}
     */
    String getMessageFormatIteratorEntry();

    /**
     * Returns {@link String} representation for an {@link Iterable}
     * @return {@link String} representation for an {@link Iterable}
     */
    String getMessageFormatIterableEntry();

    /**
     * Returns {@link String} representation for an {@link Enumeration}
     * @return {@link String} representation for an {@link Enumeration}
     */
    String getMessageFormatEnumerationEntry();

    /**
     * Returns {@link String} representation for an array
     * @return {@link String} representation for an array
     */
    String getMessageFormatArrayEntry();

    /**
     * Returns {@link String} representation for a method
     * @return {@link String} representation for a method
     */
    String getMessageFormatMethodName();

    /**
     * Return {@link Class} collection of expected return type for methods
     * @return a  {@link Class} collection of expected return type for methods
     */
    Collection<Class<?>> getClasses();

    /**
     * Returns an unmodifiable {@link Set} of {@link MappableItem}
     * describe how to select item to add in the final map.
     * @return an unmodifiable {@link Set} of {@link MappableItem}
     */
    Set<MappableItem> getMappableItemSet();

    /**
     * {@link Pattern} use to select method names to add into map result
     * @return a {@link Pattern} to select methods
     */
    Pattern getMethodesNamePattern();

}
