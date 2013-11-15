package com.googlecode.cchlib.util.mappable;

import java.io.Serializable;
import java.util.Collection;
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
     * TODOC
     */
    String getMessageFormatIteratorEntry();

    /**
     * TODOC
     */
    String getMessageFormatIterableEntry();

    /**
     * TODOC
     */
    String getMessageFormatEnumerationEntry();

    /**
     * TODOC
     */
    String getMessageFormatArrayEntry();

    /**
     * TODOC
     */
    String getMessageFormatMethodName();

    /**
     * TODOC
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
     */
    Pattern getMethodesNamePattern();

}
