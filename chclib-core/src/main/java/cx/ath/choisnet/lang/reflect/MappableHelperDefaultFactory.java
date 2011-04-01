package cx.ath.choisnet.lang.reflect;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import cx.ath.choisnet.ToDo;
import cx.ath.choisnet.lang.reflect.MappableHelper.Attributes;

/**
 * <p style="border:groove;">
 * <b>Warning:</b>
 * Insofar the code of this class comes from decompiling
 * my own code following the loss of source code, the use
 * of this class must do so under protest until I have
 * check its stability, it could be subject to significant
 * change.
 * <br/>
 * <br/>
 * <b>Attention:</b>
 * Dans la mesure où le code de cette classe est issue de
 * la décompilation de mon propre code, suite à la perte
 * du code source, l'utilisation de cette classe doit ce
 * faire sous toute réserve tant que je n'ai pas vérifier
 * sa stabilité, elle est donc sujette à des changements 
 * importants.
 * </p>
 *
 * @author Claude CHOISNET
 */
@ToDo
public class MappableHelperDefaultFactory
    implements MappableHelperFactory, java.io.Serializable
{
    private static final long serialVersionUID = 2L;
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
    /** TODO: Doc! */
    public static final Class<?> ALL_CLASS = Object.class;
    /** TODO: Doc! */
    public static final Class<?> STANDARDS_TYPES_CLASS[] = {
        Boolean.class, Character.class, Enum.class, Number.class, String.class
        };
    /** value: {@value} */
    public static final String DEFAULT_METHODS = "(is|get).*";
    /** @serial */
    private HashSet<Class<?>> classes;
    /** @serial */
    private Pattern methodesNamePattern;
    /** @serial */
    private EnumSet<MappableHelper.Attributes> attributesSet;

    /** 
     * TODO: Doc! 
     */
    public MappableHelperDefaultFactory()
    {
        classes = new HashSet<Class<?>>();

        methodesNamePattern = Pattern.compile(DEFAULT_METHODS);
        attributesSet = EnumSet.noneOf(MappableHelper.Attributes.class);
    }
    
    /** 
     * TODO: Doc! 
     * 
     * @param attribute
     */
    public MappableHelperDefaultFactory addAttribute(
            MappableHelper.Attributes attribute
            )
    {
        attributesSet.add(attribute);

        return this;
    }

    /** 
     * TODO: Doc! 
     * 
     * @param attributes
     */
    public MappableHelperDefaultFactory addAttributes(
            MappableHelper.Attributes...attributes
            )
    {
        for( MappableHelper.Attributes attr : attributes ) {
            attributesSet.add(attr);
            }

        return this;
    }

    /** 
     * TODO: Doc! 
     * 
     * @param attributes
     */
    public MappableHelperDefaultFactory addAttributes(
            Collection<MappableHelper.Attributes> attributes
            )
    {
        attributesSet.addAll(attributes);

        return this;
    }

    /** 
     * TODO: Doc! 
     * 
     * @param clazz
     */
    public MappableHelperDefaultFactory addClass(Class<?> clazz)
    {
        classes.add(clazz);

        return this;
    }

    /** 
     * TODO: Doc! 
     */
    public MappableHelperDefaultFactory addClasses(Class<?>...classes)
    {
        for(Class<?> clazz : classes) {
            this.classes.add(clazz);
        }

        return this;
    }

    /** 
     * TODO: Doc! 
     */
    public MappableHelperDefaultFactory addClasses(Collection<Class<?>> classes)
    {
        for(Class<?> clazz : classes) {
            this.classes.add(clazz);
        }

        return this;
    }

    /** 
     * TODO: Doc! 
     */
    public MappableHelperDefaultFactory setMethodesNamePattern(Pattern pattern)
    {
        methodesNamePattern = pattern;

        return this;
    }

    /** 
     * TODO: Doc! 
     */
    public MappableHelperDefaultFactory setMethodesNamePattern(String pattern)
    {
        methodesNamePattern = Pattern.compile(pattern);

        return this;
    }
    
    @Override
    public String getStringNullValue()
    {
        return DEFAULT_TO_STRING_NULL_VALUE;
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

    @Override
    public Set<Class<?>> getClasses()
    {
        if( classes.size() == 0) {
            classes.add(ALL_CLASS);
            }

        return classes;
    }

    @Override
    public EnumSet<Attributes> getAttributes()
    {
        return attributesSet;
    }

    @Override
    public Pattern getMethodesNamePattern()
    {
        return methodesNamePattern;
    }

}
