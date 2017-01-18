package com.googlecode.cchlib.util.mappable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;

/**
 * Default implementation of {@link MappableBuilderFactory}
 */
public class DefaultMappableBuilderFactory
    implements MappableBuilderFactory, Serializable
{
    private static final long serialVersionUID = 1L;

    /** value: {@value} */
    public static final String DEFAULT_TO_STRING_NULL_VALUE = "NULL";
    /** value: {@value} */
    public static final String DEFAULT_MESSAGE_FORMAT_ITERATOR_ENTRY = "{0}().ITERATOR({1})";
    /** value: {@value} */
    public static final String DEFAULT_MESSAGE_FORMAT_ITERABLE_ENTRY = "{0}().ITERABLE({1})";
    /** value: {@value} */
    public static final String DEFAULT_MESSAGE_FORMAT_ENUMERATION_ENTRY = "{0}().ENUMERATION({1})";
    /** value: {@value} */
    public static final String DEFAULT_MESSAGE_FORMAT_ARRAY_ENTRY = "{0}()[{1}/{2}]";
    /** value: {@value} */
    public static final String DEFAULT_MESSAGE_FORMAT_METHOD_NAME = "{0}()";

    /** Default regexp for {@link #getMethodesNamePattern()} - value: {@value} */
    public static final String DEFAULT_METHODS = "(is|get).*";

    private final HashSet<Class<?>>     classes;
    private Pattern                     methodesNamePattern;
    private final EnumSet<MappableItem> attributes;

    private String stringNullValue;
    private String messageFormatIteratorEntry;
    private String messageFormatMethodName;

    /**
     * Create a MappableBuilderDefaultFactory
     */
    public DefaultMappableBuilderFactory()
    {
        this.classes             = new HashSet<>();
        this.methodesNamePattern = Pattern.compile( DEFAULT_METHODS );
        this.attributes          = EnumSet.noneOf( MappableItem.class );
    }

    /**
     * Add {@link MappableItem} value(s) to internal set
     *
     * @param items One or more{@link MappableItem} to add
     * @return caller for initialization chaining
     */
    public DefaultMappableBuilderFactory add(
        @Nonnull final MappableItem...items
        )
    {
        for( final MappableItem attr : items ) {
            this.attributes.add( attr );
            }

        return this;
    }

    /**
      * Add a set of {@link MappableItem} values to internal set
    *
     * @param mappableItems A set of {@link MappableItem}
      * @return caller for initialization chaining
     */
    public DefaultMappableBuilderFactory add(
        @Nonnull final Set<MappableItem> mappableItems
        )
    {
        this.attributes.addAll( mappableItems );

        return this;
    }

    @Override
    public Set<MappableItem> getMappableItemSet()
    {
        return Collections.unmodifiableSet( this.attributes );
    }

    /**
     * Add the giving {@link Class} to the internal collection
     *
     * @param clazz {@link Class} to add
     * @return caller for initialization chaining
     */
    public DefaultMappableBuilderFactory add( @Nonnull final Class<?> clazz )
    {
        this.classes.add( clazz );
        return this;
    }

    /**
     * Add the giving {@link Class} list to the internal collection
     *
     * @param classes {@link Class} list to add
     * @return caller for initialization chaining
     */
    public DefaultMappableBuilderFactory add(
        @Nonnull final Class<?>...classes
        )
    {
        for( final Class<?> clazz : classes ) {
            this.classes.add( clazz );
            }

        return this;
    }

    /**
     * NEEDDOC
     *
     * @param mappableTypes NEEDDOC
     * @return NEEDDOC
     */
    public DefaultMappableBuilderFactory add(
        @Nonnull final MappableTypes mappableTypes
        )
    {
        this.classes.addAll( mappableTypes.getClasses() );

        return this;
    }

    /**
     * If internal set is empty the return a unmodifiable set
     * within value of {@link MappableTypes#STANDARD_TYPES}.
     */
    @Override
    public Collection<Class<?>> getClasses()
    {
        if( this.classes.isEmpty() ) {
            return MappableTypes.STANDARD_TYPES.getClasses();
            }

        return this.classes;
    }


    @Override
    public Pattern getMethodesNamePattern()
    {
        return this.methodesNamePattern;
    }

    /**
     * If internal value is not define (null) return value
     * of {@link #DEFAULT_TO_STRING_NULL_VALUE}
     * @see #setStringNullValue(String)
     */
    @Override
    public String getStringNullValue()
    {
        if( this.stringNullValue == null ) {
            return DEFAULT_TO_STRING_NULL_VALUE;
            }
        else {
            return this.stringNullValue;
            }
    }

    @Override
    public String getMessageFormatIteratorEntry()
    {
        if( this.messageFormatIteratorEntry == null ) {
            return DEFAULT_MESSAGE_FORMAT_ITERATOR_ENTRY;
            }
        else {
            return this.messageFormatIteratorEntry;
            }
    }

    @Override
    public String getMessageFormatIterableEntry()
    {
        return DEFAULT_MESSAGE_FORMAT_ITERABLE_ENTRY;
    }

    @Override
    public String getMessageFormatEnumerationEntry()
    {
        return DEFAULT_MESSAGE_FORMAT_ENUMERATION_ENTRY;
    }

    @Override
    public String getMessageFormatArrayEntry()
    {
        return DEFAULT_MESSAGE_FORMAT_ARRAY_ENTRY;
    }

    @Override
    public String getMessageFormatMethodName()
    {
        if( this.messageFormatMethodName != null ) {
            return this.messageFormatMethodName;
        }

        return DEFAULT_MESSAGE_FORMAT_METHOD_NAME;
    }

    /**
     * Set value of {@link #getMessageFormatIteratorEntry()}
     *
     * @param messageFormatIteratorEntry
     *            Value to use to format entries of iterators.
     * @return caller for initialization chaining
     */
    public DefaultMappableBuilderFactory setMessageFormatIteratorEntry(
        @Nonnull final String messageFormatIteratorEntry
        )
    {
        this.messageFormatIteratorEntry = messageFormatIteratorEntry;

        return this;
    }

    /**
     * Set value of {@link #getMessageFormatMethodName()}}
     *
     * @param messageFormatMethodName
     *            Value to use to format method name.
     * @return caller for initialization chaining
     */
    public DefaultMappableBuilderFactory setMessageFormatMethodName(
        final String messageFormatMethodName
        )
    {
        this.messageFormatMethodName = messageFormatMethodName;

        return this;
    }

    /**
     * Set value of {@link #getMethodesNamePattern()}
     *
     * @param pattern
     *            {@link Pattern} to select methods name.
     * @return caller for initialization chaining
     */
    public DefaultMappableBuilderFactory setMethodesNamePattern(
        @Nonnull final Pattern pattern
        )
    {
        this.methodesNamePattern = pattern;
        return this;
    }

    /**
     * Set value of {@link #getMethodesNamePattern()}
     *
     * @param pattern
     *            regex to select methods name.
     * @return caller for initialization chaining
     *
     * @see Pattern#compile(String)
     */
    public DefaultMappableBuilderFactory setMethodesNamePattern(
        @Nonnull final String pattern
        )
    {
        return setMethodesNamePattern( Pattern.compile( pattern ) );
    }

    /**
     * Set value for {@link #getStringNullValue()}
     *
     * @param stringNullValue
     *            Result to use for null String
     * @return caller for initialization chaining
     */
    public DefaultMappableBuilderFactory setStringNullValue(
        final String stringNullValue
        )
    {
        this.stringNullValue = stringNullValue;
        return this;
    }

}
