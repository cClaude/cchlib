/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/lang/reflect/MappableHelperFactory.java
** Description   :
** Encodage      : ANSI
**
**  3.01.016 2006.04.04 Claude CHOISNET - Version initiale.
**  3.01.034 2006.05.05 Claude CHOISNET
**                      Ajout de getMessageFormatMethodName()
**                      Ajout de DEFAULT_MESSAGE_FORMAT_METHOD_NAME
**  3.02.004 2006.06.02 Claude CHOISNET
**                      Ajout de: setMethodesNamePattern(Pattern)
**                      Ajout de: setMethodesNamePattern(String)
**                      Ajout de: getMethodesNamePattern()
**                      Ajout de: addClasses(Class<?> ...)
**                      Ajout de: addClass(Class<?>)
**                      Ajout de: addClasses(Collection<Class<?>>)
**                      Ajout de: addAttribute(MappableHelper.Attributes)
**                      Ajout de: addAttributes(MappableHelper.Attributes ...)
**                      Ajout de: addAttributes(Collection<Class<?>>)
**                      La classe est maintenant Serializable
**  3.02.021 2006.07.03 Claude CHOISNET
**                      Ajout de: setStringNullValue(String)
**                      Ajout de: setMessageFormatMethodName(String)
**  3.02.022 2006.07.04 Claude CHOISNET
**                      Ajout de: setMessageFormatIteratorEntry(String)
**                      Ajout de: setMessageFormatEnumerationEntry(String)
**                      Ajout de: setMessageFormatIterableEntry(String)
**  3.02.026 2006.07.19 Claude CHOISNET
**                      Ajout de: getInstance()
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.lang.reflect.MappableHelperFactory
*/
package cx.ath.choisnet.lang.reflect;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.Set;

/**
** <P>
** Permettant de configurer le fonctionnement de la
** classe {@link MappableHelper}
** </P>
**
**
** @author Claude CHOISNET
** @since   3.01.016
** @version 3.02.026
**
** @see MappableHelper
*/
final
public class MappableHelperFactory
    implements java.io.Serializable
{
/** serialVersionUID */
private static final long serialVersionUID = 2L;

/**
** valeur par defaut pour {@link #getStringNullValue()}
*/
public final static String DEFAULT_TO_STRING_NULL_VALUE
    = "NULL";

/**
** @since 3.02.021
*/
private String stringNullValue;

/**
** valeur par defaut pour {@link #getMessageFormatIteratorEntry()}
*/
public final static String DEFAULT_MESSAGE_FORMAT_ITERATOR_ENTRY
    = "{0}().ITERATOR({1})";

/**
** @since 3.02.022
*/
private String messageFormatIteratorEntry;

/**
** valeur par defaut pour {@link #getMessageFormatIterableEntry()}
*/
public final static String DEFAULT_MESSAGE_FORMAT_ITERABLE_ENTRY
    = "{0}().ITERABLE({1})";

/**
** @since 3.02.022
*/
private String messageFormatIterableEntry;

/**
** valeur par defaut pour {@link #getMessageFormatEnumerationEntry()}
*/
public final static String DEFAULT_MESSAGE_FORMAT_ENUMERATION_ENTRY
    = "{0}().ENUMERATION({1})";

/**
** @since 3.02.022
*/
private String messageFormatEnumerationEntry;

/**
** valeur par defaut pour {@link #getMessageFormatArrayEntry()}
*/
public final static String DEFAULT_MESSAGE_FORMAT_ARRAY_ENTRY
    = "{0}()[{1}/{2}]";

/**
** @since 3.02.022
*/
private String messageFormatArrayEntry;

/**
** valeur par defaut pour {@link #getMessageFormatMethodName()}
**
** @since 3.01.034
*/
public final static String DEFAULT_MESSAGE_FORMAT_METHOD_NAME
    = "{0}()";

/**
**
** @since 3.02.021
*/
private String messageFormatMethodName;

/**
** Indique que toutes les classes sont eligibles.
**
** @see #addClass(Class)
*/
public final static Class<?> ALL_CLASS = Object.class;

/**
** Permet de voir les resultats des types standards<br/>
** {@link Boolean},
** {@link Character},
** {@link Enum},
** {@link Number},
** {@link String}
**
** @see #addClasses(Class[])
*/
public final static Class<?>[] STANDARDS_TYPES_CLASS = {
    Boolean.class,
    Character.class,
    Enum.class,
    Number.class,
    String.class
    };

/**
** Filtre les observateurs classiques : isXxx(), getXxx()
**
** @see #setMethodesNamePattern(String)
*/
public final static String DEFAULT_METHODS = "(is|get).*";

/**
**
*/
private HashSet<Class<?>> classes;

/**
**
*/
private Pattern methodesNamePattern;

/**
**
*/
private EnumSet<MappableHelper.Attributes> attributesSet;

/**
** Constructeur par default pour MappableHelperFactory
*/
public MappableHelperFactory() // -----------------------------------------
{
 this.classes               = new HashSet<Class<?>>();
 this.methodesNamePattern   = Pattern.compile( DEFAULT_METHODS );
 this.attributesSet         = EnumSet.noneOf( MappableHelper.Attributes.class );
}

/**
** Permet de definir la chaene retournee pour une valeur nulle.
**
** @return la chaene retournee pour une valeur "null".
**
** @see #setStringNullValue(String)
*/
public String getStringNullValue() // -------------------------------------
{
 if( this.stringNullValue != null ) {
    return this.stringNullValue;
    }
 else {
    return DEFAULT_TO_STRING_NULL_VALUE;
    }
}

/**
**
** @see #getStringNullValue()
** @since 3.02.021
*/
public MappableHelperFactory setStringNullValue( // -----------------------
    final String stringNullValue
    )
{
 this.stringNullValue = stringNullValue;

 return this;
}

/**
** Permet de definir la chaene de formattage pour la construction du nom
** de d'une valeur issue d'un {@link java.util.Iterator}.
**
** @return la chaene de formattage pour la construction du nom de d'une
**         valeur issue d'un {@link java.util.Iterator}.
**
** @see java.text.MessageFormat
*/
public String getMessageFormatIteratorEntry() // --------------------------
{
 if( this.messageFormatIteratorEntry != null ) {
    return this.messageFormatIteratorEntry;
    }
 else {
    return DEFAULT_MESSAGE_FORMAT_ITERATOR_ENTRY;
    }
}

/**
**
** @since 3.02.022
** @see #getMessageFormatIteratorEntry()
*/
public MappableHelperFactory setMessageFormatIteratorEntry( // ------------
    final String messageFormatIteratorEntry
    )
{
 this.messageFormatIteratorEntry = messageFormatIteratorEntry;

 return this;
}

/**
** Permet de definir la chaene de formattage pour la construction du nom
** de d'une valeur issue d'un {@link Iterable}.
**
** @return la chaene de formattage pour la construction du nom de d'une
**         valeur issue d'un object {@link Iterable}.
**
** @see java.text.MessageFormat
** @see #setMessageFormatIterableEntry(String)
*/
public String getMessageFormatIterableEntry() // --------------------------
{
 if( this.messageFormatIterableEntry != null ) {
    return this.messageFormatIterableEntry;
    }
 else {
    return DEFAULT_MESSAGE_FORMAT_ITERABLE_ENTRY;
    }
}

/**
**
** @since 3.02.022
** @see #getMessageFormatIterableEntry()
*/
public MappableHelperFactory setMessageFormatIterableEntry( // ------------
    final String messageFormatIterableEntry
    )
{
 this.messageFormatIterableEntry = messageFormatIterableEntry;

 return this;
}

/**
** Permet de definir la chaene de formattage pour la construction du nom
** de d'une valeur issue d'un {@link java.util.Enumeration}.
**
** @return la chaene de formattage pour la construction du nom de d'une
**         valeur issue d'un {@link java.util.Enumeration}.
**
** @see java.text.MessageFormat
** @see #setMessageFormatEnumerationEntry(String)
*/
public String getMessageFormatEnumerationEntry() // -----------------------
{
 if( this.messageFormatEnumerationEntry != null ) {
    return this.messageFormatEnumerationEntry;
    }
 else {
    return DEFAULT_MESSAGE_FORMAT_ENUMERATION_ENTRY;
    }
}

/**
**
** @since 3.02.022
** @see #getMessageFormatEnumerationEntry()
*/
public MappableHelperFactory setMessageFormatEnumerationEntry( // ---------
    final String messageFormatEnumerationEntry
    )
{
 this.messageFormatEnumerationEntry = messageFormatEnumerationEntry;

 return this;
}

/**
** Permet de definir la chaene de formattage pour la construction du nom
** de d'une valeur issue d'un tableau.
**
** @return la chaene de formattage pour la construction du nom de d'une
**         valeur issue d'un tableau
**
** @see java.text.MessageFormat
** @see #setMessageFormatArrayEntry(String)
*/
public String getMessageFormatArrayEntry() // -----------------------------
{
 if( this.messageFormatArrayEntry != null ) {
    return this.messageFormatArrayEntry;
    }
 else {
    return DEFAULT_MESSAGE_FORMAT_ARRAY_ENTRY;
    }
}

/**
**
** @since 3.02.022
** @see #getMessageFormatArrayEntry()
*/
public MappableHelperFactory setMessageFormatArrayEntry( // ---------------
    final String messageFormatArrayEntry
    )
{
 this.messageFormatArrayEntry = messageFormatArrayEntry;

 return this;
}

/**
** Permet de definir la chaene de formattage pour la construction du nom
** d'une methode retournant un element simple.
**
** @return la chaene de formattage pour la construction du nom de
**         la methode.
**
** @see java.text.MessageFormat
**
** @see #setMessageFormatMethodName(String)
** @since 3.01.034
*/
public String getMessageFormatMethodName() // -----------------------------
{
 if( this.messageFormatMethodName != null ) {
    return this.messageFormatMethodName;
    }
 else {
    return DEFAULT_MESSAGE_FORMAT_METHOD_NAME;
    }
}

/**
**
** @see #getMessageFormatMethodName()
** @since 3.02.021
*/
public MappableHelperFactory setMessageFormatMethodName( // ---------------*
    final String messageFormatMethodName
    )
{
 this.messageFormatMethodName = messageFormatMethodName;

 return this;
}

/**
**
**
** @see #addClass(Class)
** @see #addClasses(Class[])
**
** @since 3.02.004
*/
public Set<Class<?>> getClasses() // --------------------------------------
{
 if( this.classes.size() == 0 ) {
    this.classes.add( ALL_CLASS );
    }

 return this.classes;
}

/**
**
**
** @since 3.02.004
*/
public MappableHelperFactory addClass( final Class<?> clazz ) // ----------
{
 this.classes.add( clazz );

 return this;
}

/**
**
**
** @since 3.02.004
*/
public MappableHelperFactory addClasses( // -------------------------------
    final Class<?> ... classes
    )
{
 for( Class<?> clazz : classes ) {
    this.classes.add( clazz );
    }

 return this;
}

/**
**
**
** @since 3.02.004
*/
public MappableHelperFactory addClasses( // -------------------------------
    final Collection<Class<?>> classes
    )
{
 for( Class<?> clazz : classes ) {
    this.classes.add( clazz );
    }

 return this;
}

/**
**
**
** @since 3.02.004
*/
public EnumSet<MappableHelper.Attributes> getAttributes() // --------------
{
 return this.attributesSet;
}

/**
**
**
** @since 3.02.004
*/
public MappableHelperFactory addAttribute( // -----------------------------
    final MappableHelper.Attributes attrib
    )
{
 this.attributesSet.add( attrib );

 return this;
}

/**
**
**
** @since 3.02.004
*/
public MappableHelperFactory addAttributes( // ----------------------------
    final MappableHelper.Attributes ... attrib
    )
{
 for( MappableHelper.Attributes att : attrib ) {
    this.attributesSet.add( att );
    }

 return this;
}

/**
**
**
** @since 3.02.004
*/
public MappableHelperFactory addAttributes( // ----------------------------
    final Collection<MappableHelper.Attributes> attrib
    )
{
 this.attributesSet.addAll( attrib );

 return this;
}

/**
**
**
** @since 3.02.004
*/
public Pattern getMethodesNamePattern() // --------------------------------
{
 return this.methodesNamePattern;
}

/**
**
** @since 3.02.004
*/
public MappableHelperFactory setMethodesNamePattern( // -------------------
    final Pattern pattern
    )
{
 this.methodesNamePattern = pattern;

 return this;
}

/**
**
** @since 3.02.004
*/
public MappableHelperFactory setMethodesNamePattern( // -------------------
    final String pattern
    )
{
 this.methodesNamePattern = Pattern.compile( pattern );

 return this;
}

/**
**
**
** @since 3.02.026
*/
public MappableHelper getInstance() // ------------------------------------
{
 return new MappableHelper( this, true );
}

} // class

