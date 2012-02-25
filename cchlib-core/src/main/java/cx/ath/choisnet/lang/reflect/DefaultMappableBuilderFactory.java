package cx.ath.choisnet.lang.reflect;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

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

    private static final Set<Class<?>> ALL_CLASS_SET = getAllClasses();

    /** @serial */
    private HashSet<Class<?>> classes;
    /** @serial */
    private Pattern methodesNamePattern;
    /** @serial */
    private EnumSet<MappableItem> attributesSet;
    /** @serial */
    private String defaultToStringNullValue;

    /**
     * Create a MappableBuilderDefaultFactory
     */
    public DefaultMappableBuilderFactory()
    {
        this.classes             = new HashSet<Class<?>>();
        this.methodesNamePattern = Pattern.compile( DEFAULT_METHODS );
        this.attributesSet       = EnumSet.noneOf( MappableItem.class );
    }

    private static Set<Class<?>> getAllClasses()
    {
        HashSet<Class<?>> set = new HashSet<Class<?>>();

        for( Class<?> c : MappableBuilder.CLASSES_SHOW_ALL ) {
            set.add( c );
            }

        return Collections.unmodifiableSet( set );
    }

    /**
     * Add {@link MappableItem} to internal set
     *
     * @param item A {@link MappableItem} to add
     * @return caller for initialization chaining
     */
    public DefaultMappableBuilderFactory add(
            final MappableItem item
            )
    {
        attributesSet.add( item );
        return this;
    }

    /**
     * Add {@link MappableItem} list to internal set
     *
     * @param items A list of {@link MappableItem} to add
     * @return caller for initialization chaining
     */
    public DefaultMappableBuilderFactory add(
            final MappableItem...items
            )
    {
        for( MappableItem attr : items ) {
            attributesSet.add(attr);
            }

        return this;
    }

//    /**
//     * Add {@link MappableItem} list to internal set
//     *
//     * @param items A list of {@link MappableItem} to add
//     * @return caller for initialization chaining
//     */
//    public MappableBuilderDefaultFactory addItems(
//            final Collection<MappableItem> items
//            )
//    {
//        attributesSet.addAll( items );
//        return this;
//    }

    @Override
    public Set<MappableItem> getMappableItemSet()
    {
        return attributesSet;
    }


    /**
     * Add the giving {@link Class} to the internal collection
     *
     * @param clazz {@link Class} to add
     * @return caller for initialization chaining
     */
    public DefaultMappableBuilderFactory add( final Class<?> clazz )
    {
        classes.add( clazz );
        return this;
    }

    /**
     * Add the giving {@link Class} list to the internal collection
     *
     * @param classes {@link Class} list to add
     * @return caller for initialization chaining
     */
    public DefaultMappableBuilderFactory add( final Class<?>...classes)
    {
        for( Class<?> clazz : classes ) {
            this.classes.add(clazz);
            }

        return this;
    }

//    /**
//     * Add the giving {@link Class} list to the internal collection
//     *
//     * @param classes {@link Class} list to add
//     * @return caller for initialization chaining
//     */
//    public MappableBuilderDefaultFactory addClasses(
//            final Collection<Class<?>> classes
//            )
//    {
//        for( Class<?> clazz : classes ) {
//            this.classes.add( clazz );
//            }
//
//        return this;
//    }

    /**
     * If internal set is empty the return a unmodifiable set
     * within value of {@link #ALL_CLASS_SET}.
     */
    @Override
    public Set<Class<?>> getClasses()
    {
        if( classes.size() == 0 ) {
            return ALL_CLASS_SET;
            }

        return classes;
    }

    /**
     * Set value of {@link #getMethodesNamePattern()}
     *
     * @param pattern {@link Pattern} to select methods name.
     * @return caller for initialization chaining
     */
    public DefaultMappableBuilderFactory setMethodesNamePattern(
            final Pattern pattern
            )
    {
        methodesNamePattern = pattern;
        return this;
    }

    /**
     * Set value of {@link #getMethodesNamePattern()}
     *
     * @param pattern Regexp to select methods name.
     * @return caller for initialization chaining
     */
    public DefaultMappableBuilderFactory setMethodesNamePattern(
            final String pattern
            )
    {
        return setMethodesNamePattern( Pattern.compile( pattern ) );
    }

    @Override
    public Pattern getMethodesNamePattern()
    {
        return methodesNamePattern;
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
