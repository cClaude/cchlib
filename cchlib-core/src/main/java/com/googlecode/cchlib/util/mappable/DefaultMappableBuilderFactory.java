package com.googlecode.cchlib.util.mappable;

import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;

/**
 * Default implementation of {@link MappableBuilderFactory}
 */
public class DefaultMappableBuilderFactory // $codepro.audit.disable largeNumberOfFields
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

    /** @serial */
    private final HashSet<Class<?>> classes;
    /** @serial */
    private Pattern methodesNamePattern;
    /** @serial */
    private final EnumSet<MappableItem> attributesSet;
    /** @serial */
    private String defaultToStringNullValue;

    /**
     * Create a MappableBuilderDefaultFactory
     */
    public DefaultMappableBuilderFactory()
    {
        this.classes             = new HashSet<>();
        this.methodesNamePattern = Pattern.compile( DEFAULT_METHODS );
        this.attributesSet       = EnumSet.noneOf( MappableItem.class );
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
            this.attributesSet.add(attr);
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
        this.attributesSet.addAll( mappableItems );

        return this;
    }

    @Override
    public Set<MappableItem> getMappableItemSet()
    {
        return this.attributesSet;
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
     *
     * @param mappableTypes
     * @return
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
     * within value of {@link MappableTypes#CLASSES_SHOW_ALL}.
     */
    @Override
    public Collection<Class<?>> getClasses()
    {
        if( this.classes.isEmpty() ) {
            return MappableTypes.CLASSES_SHOW_ALL.getClasses();
            }

        return this.classes;
    }

    /**
     * Set value of {@link #getMethodesNamePattern()}
     *
     * @param pattern {@link Pattern} to select methods name.
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
     * @param pattern Regexp to select methods name.
     * @return caller for initialization chaining
     */
    public DefaultMappableBuilderFactory setMethodesNamePattern(
            @Nonnull final String pattern
            )
    {
        return setMethodesNamePattern( Pattern.compile( pattern ) );
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
        if( this.defaultToStringNullValue == null ) {
            return DEFAULT_TO_STRING_NULL_VALUE;
            }
        else {
            return this.defaultToStringNullValue;
            }
    }

    /**
     * Set value for {@link #getStringNullValue()}
     * @param defaultToStringNullValue Value to set
     * @return caller for initialization chaining
     */
    public DefaultMappableBuilderFactory setStringNullValue(
            final String defaultToStringNullValue
            )
    {
        this.defaultToStringNullValue = defaultToStringNullValue;
        return this;
    }

    @Override
    public String getMessageFormatIteratorEntry()
    {
        return DEFAULT_MESSAGE_FORMAT_ITERATOR_ENTRY;
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
        return DEFAULT_MESSAGE_FORMAT_METHOD_NAME;
    }
}
