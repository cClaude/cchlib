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
 *
 */
@ToDo
public class MappableHelperFactory
    implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    public static final String DEFAULT_TO_STRING_NULL_VALUE = "NULL";
    public static final String DEFAULT_MESSAGE_FORMAT_ITERATOR_ENTRY = "{0}().ITERATOR({1})";
    public static final String DEFAULT_MESSAGE_FORMAT_ITERABLE_ENTRY = "{0}().ITERABLE({1})";
    public static final String DEFAULT_MESSAGE_FORMAT_ENUMERATION_ENTRY = "{0}().ENUMERATION({1})";
    public static final String DEFAULT_MESSAGE_FORMAT_ARRAY_ENTRY = "{0}()[{1}/{2}]";
    public static final String DEFAULT_MESSAGE_FORMAT_METHOD_NAME = "{0}()";
    public static final Class<?> ALL_CLASS = Object.class;
    public static final Class<?> STANDARDS_TYPES_CLASS[] = {
        Boolean.class, Character.class, Enum.class, Number.class, String.class
    };
    public static final String DEFAULT_METHODS = "(is|get).*";
    /** @serial */
    private HashSet<Class<?>> classes;
    /** @serial */
    private Pattern methodesNamePattern;
    /** @serial */
    private EnumSet<MappableHelper.Attributes> attributesSet;

    public MappableHelperFactory()
    {
        classes = new HashSet<Class<?>>();

        methodesNamePattern = Pattern.compile("(is|get).*");
        attributesSet = EnumSet.noneOf(MappableHelper.Attributes.class);

    }

    public String getStringNullValue()
    {
        return DEFAULT_TO_STRING_NULL_VALUE;
    }

    public String getMessageFormatIteratorEntry()
    {
        return DEFAULT_MESSAGE_FORMAT_ITERATOR_ENTRY;
    }

    public String getMessageFormatIterableEntry()
    {
        return DEFAULT_MESSAGE_FORMAT_ITERABLE_ENTRY;
    }

    public String getMessageFormatEnumerationEntry()
    {
        return DEFAULT_MESSAGE_FORMAT_ENUMERATION_ENTRY;
    }

    public String getMessageFormatArrayEntry()
    {
        return DEFAULT_MESSAGE_FORMAT_ARRAY_ENTRY;
    }

    public String getMessageFormatMethodName()
    {
        return DEFAULT_MESSAGE_FORMAT_METHOD_NAME;
    }

    public Set<Class<?>> getClasses()
    {
        if(classes.size() == 0)
        {
            classes.add(ALL_CLASS);

        }
        return classes;

    }

    public MappableHelperFactory addClass(Class<?> clazz)
    {
        classes.add(clazz);

        return this;
    }

    public MappableHelperFactory addClasses(Class<?>...classes)
    {
        for(Class<?> clazz : classes) {
            this.classes.add(clazz);
        }

        return this;
    }

    public MappableHelperFactory addClasses(Collection<Class<?>> classes)
    {
        for(Class<?> clazz : classes) {
            this.classes.add(clazz);
        }

        return this;
    }

    public EnumSet<Attributes> getAttributes()
    {
        return attributesSet;
    }

    public MappableHelperFactory addAttribute(MappableHelper.Attributes attrib)
    {
        attributesSet.add(attrib);

        return this;
    }

    public MappableHelperFactory addAttributes(MappableHelper.Attributes...attrib)
    {
        for( MappableHelper.Attributes attr : attrib ) {
            attributesSet.add(attr);
        }

        return this;
    }

    public MappableHelperFactory addAttributes(Collection<MappableHelper.Attributes> attrib)
    {
        attributesSet.addAll(attrib);

        return this;
    }

    public Pattern getMethodesNamePattern()
    {
        return methodesNamePattern;
    }

    public MappableHelperFactory setMethodesNamePattern(Pattern pattern)
    {
        methodesNamePattern = pattern;

        return this;
    }

    public MappableHelperFactory setMethodesNamePattern(String pattern)
    {
        methodesNamePattern = Pattern.compile(pattern);

        return this;
    }
}
