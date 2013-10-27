/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/lang/reflect/MappableHelper.java
** Description   :
** Encodage      : ANSI
**
**  2.01.001 2005.10.24 Claude CHOISNET - version initiale
**  2.01.024 2005.11.03 Claude CHOISNET
**                      Traite r�cursivement les objets Mappable
**  2.01.025 2005.11.04 Claude CHOISNET
**                      Cette version traite les tableaux d'objets.
**  2.01.028 2005.11.09 Claude CHOISNET
**                      Prend en compte les m�thodes prot�g�es
**  2.02.008 2005.12.06 Claude CHOISNET
**                      Ajout de param�tres TRY_PRIVATE_METHODS,
**                      TRY_PROTECTED_METHODS et DO_ITERABLE
**  3.01.013 2006.03.27 Claude CHOISNET
**                      Ajout de toXML(Appendable,Class,Map)
**                      Ajout de l'attribut d'�num�rateur DO_ARRAYS
**                      Ajout de l'attribut d'�num�rateur DO_PARENT_CLASSES
**  3.01.015 2006.04.03 Claude CHOISNET
**                      Version objet.
**                      Suppression de la m�thode statique ;
**                          toString(T,EnumSet<Attributes>)
**                      Ajout de la m�thode statique :
**                          toString(EnumSet<Attributes>,T)
**                      Mise en place de nombreux test � l'aide de JUnit.
**  3.01.016 2006.04.04 Claude CHOISNET
**                      Adaptation du code afin de pouvoir mettre en place
**                      de formateur configurable.
**  3.01.020 2006.04.04 Claude CHOISNET
**                      Ajout d'un constructeur vide, afin de mettre en
**                      oeuvre la classe plus facilement.
**  3.01.021 2006.04.04 Claude CHOISNET
**                      L'objet membre returnTypeClasses n'est plus un
**                      tableau de classe, mais un Set<Class<?>>
**                      Ajout d'un constructeur en conformit�e avec
**                      cette �volution,
**                      Les m�thodes internes n'utilise plus que ce nouveau
**                      constructeur.
**  3.01.022 2006.04.13 Claude CHOISNET
**                      Evolution de la m�thode toXML(Appendable,Class,Map)
**                      qui supporte maintenant une valeur null pour le
**                      param�tre de type Map.
**                      Ajout des m�thodes:
**                          toXML(Appendable,Class,Mappable)
**                          toXML(Class,Mappable)
**                      Reprise des m�thodes :
**                          toXML(Appendable,Mappable)
**                          toXML(Mappable)
**  3.01.034 2006.05.05 Claude CHOISNET
**                      Utilisation de la nouvelle m�thode:
**                          MappableHelperFactory#getMessageFormatMethodName()
**                      Ajout de messageFormatMethodName
**                      Ajout de formatMethodName(String)
**                      La m�thode toString(EnumSet,Object) devient priv�e
**                      Toutes les representations de la valeur null sont
**                      maintenant remplac� par this.toStringNullValue
**  3.02.010 2006.06.16 Claude CHOISNET
**                      Ajout de: toMap(MappableHelperFactory,T)
**                      Les m�thodes statiques toMap(T,Pattern,Class,EnumSet)
**                      et toMap(T,Class) sont obsol�tes
**  3.02.026 2006.07.19 Claude CHOISNET
**                      Le constructeur est maintenant protected
**  3.02.047 2007.01.20 Claude CHOISNET
**                      Evolution mineur pour une Enumeration null dans
**                      Tomcat 5.5
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.lang.reflect.MappableHelper
**
** Cette classe devrait �tre abstraite, voir ce devrait �tre une interface !!!
**
** http://java.sun.com/developer/codesamples/refl.html
*/
package cx.ath.choisnet.lang.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.TreeMap;

/**
** <P>Permet d'avoir une vue synth�tique d'un objet</P>
**
**
** @author Claude CHOISNET
** @since   2.01.001
** @version 3.02.026
**
** @see Mappable
** @see AbstractMappable
*/
public class MappableHelper
{
/**
**
*/
public enum Attributes {

    /**
    ** Demande que de traiter tous les types primitifs (boolean, int, long, ...)
    ** ainsi que les tableaux de types primitifs.
    */
    ALL_PRIMITIVE_TYPE,

    /**
    ** Traite les objets Mappable r�cursivement.
    */
    DO_RECURSIVE,

    /**
    **
    */
    DO_ARRAYS,

    /**
    ** Parcours l'it�rateur et affiche les �l�ments s'ils
    ** appartiennent aux types demander.
    **
    ** @see java.util.Iterator
    */
    DO_ITERATOR,

    /**
    **
    **
    ** @see Iterable
    */
    DO_ITERABLE,

    /**
    ** Parcours l'it�rateur et affiche les �l�ments s'ils
    ** appartiennent aux types demander.
    **
    ** @see java.util.Enumeration
    */
    DO_ENUMERATION,

    /*
    **
    ** Parcours �galements les m�thodes des classes parentes
    **
    */
    DO_PARENT_CLASSES,

    /**
    ** Demande d'afficher le r�sultat des m�thodes priv�es 'private'
    ** <br/>
    ** Heu, c'est pas possible normalement.
    */
    TRY_PRIVATE_METHODS,

    /**
    ** Demande d'afficher le r�sultat des m�thodes prot�g�es 'protected'
    ** <br/>
    ** Possible uniquement si la classe h�rite MappableHelper
    */
    TRY_PROTECTED_METHODS

    };

/**
** EnumSet utilis� les traitements par d�faut.
**
** @see Attributes#ALL_PRIMITIVE_TYPE
** @see Attributes#DO_ARRAYS
*/
@Deprecated
public final static EnumSet<Attributes> DEFAULT_ATTRIBUTES =
    EnumSet.of(
        Attributes.ALL_PRIMITIVE_TYPE,
        Attributes.DO_ARRAYS
        );

/**
** EnumSet permettant une introspection maximum.
**
** @see Attributes#ALL_PRIMITIVE_TYPE
** @see Attributes#DO_ARRAYS
** @see Attributes#DO_ITERATOR
** @see Attributes#DO_ITERABLE
** @see Attributes#DO_ENUMERATION
** @see Attributes#DO_RECURSIVE
*/
@Deprecated
public final static EnumSet<Attributes> SHOW_ALL =
    EnumSet.of(
        Attributes.ALL_PRIMITIVE_TYPE,
        Attributes.DO_ARRAYS,
        Attributes.DO_ITERATOR,
        Attributes.DO_ITERABLE,
        Attributes.DO_ENUMERATION,
        Attributes.DO_RECURSIVE
        );

/** */
private final Pattern methodesNamePattern;

/**
** Liste des classes dont on souhaite l'affichage.
*/
private final Set<Class<?>> returnTypeClasses;

/** */
private final EnumSet<Attributes> attributesSet;

/**
** @see MappableHelperFactory#getStringNullValue()
*/
private final String toStringNullValue;

/**
** @see #formatIteratorEntry(String,int,int)
** @see MappableHelperFactory#getMessageFormatIteratorEntry()
*/
private final MessageFormat messageFormatIteratorEntry;

/** @see MappableHelperFactory#getMessageFormatIterableEntry() */
private final MessageFormat messageFormatIterableEntry;

/**
** @see #formatEnumerationEntry(String,int,int)
** @see MappableHelperFactory#getMessageFormatEnumerationEntry()
*/
private final MessageFormat messageFormatEnumerationEntry;

/**
** @see #formatArrayEntry(String,int,int)
** @see MappableHelperFactory#getMessageFormatArrayEntry()
*/
private final MessageFormat messageFormatArrayEntry;

/**
** @see #formatMethodName(String)
** @see MappableHelperFactory#getMessageFormatMethodName()
** @since 3.01.034
*/
private final MessageFormat messageFormatMethodName;

/**
** Cr�ation d'un nouvel objet {@link MappableHelper}
**
** @param factory   Objet {@link MappableHelperFactory} � partir duquel sera
**                  initialis� les informations de formattage.
**
** @see EnumSet#of
** @see EnumSet#noneOf( Class )
**
** @since 3.01.021
*/
protected MappableHelper( // ----------------------------------------------
    final MappableHelperFactory factory,
    boolean bidon
    )
{
 this.toStringNullValue             = factory.getStringNullValue();
 this.messageFormatIteratorEntry    = new MessageFormat( factory.getMessageFormatIteratorEntry() );
 this.messageFormatIterableEntry    = new MessageFormat( factory.getMessageFormatIterableEntry() );
 this.messageFormatEnumerationEntry = new MessageFormat( factory.getMessageFormatEnumerationEntry() );
 this.messageFormatArrayEntry       = new MessageFormat( factory.getMessageFormatArrayEntry() );
 this.messageFormatMethodName       = new MessageFormat( factory.getMessageFormatMethodName() );
 this.methodesNamePattern           = factory.getMethodesNamePattern();
 this.returnTypeClasses             = factory.getClasses();
 this.attributesSet                 = factory.getAttributes();
}

/**
** <p>
** Retourne des couples (nomDeMethode,valeur).
** </p>
**
** @param object    Objet � analyser par introsp�ction.
**
** @return un objet Map contenant un couple <String,String>, avec comme cl� le nom
**         de la m�thode et comme valeur la valeur retourn�e par la m�thode sur
**         lequel un toString() a �t� appliqu�.
**
** @see Object#toString()
**
** @since 3.01.015
*/
public <T> Map<String,String> toMap( // ---------------------------------------
    final T object
    )
{
 final HashMap<String,String>   hashMap = new HashMap<String,String>();
 final Class<?>                 clazz   = object.getClass();
 final Method[]                 methods = getDeclaredMethods( clazz );

 for( Method method : methods ) {

    if( method.getParameterTypes().length == 0 ) {

        if( methodesNamePattern.matcher( method.getName() ).matches() ) {
            final Class<?> returnType = method.getReturnType();

            if( isMappable( returnType ) ) {
                //
                // Implemente Mappable : on traite en profondeur
                //
                if( returnType.isArray() ) {
                    final Mappable[] result = invoke( object, method, hashMap, Mappable[].class );

                    if( result != null ) {
                        //
                        // Tableau d'objets Mappable
                        //
                        final int       len         = Array.getLength( result );
                        final String    methodName  = method.getName();

                        for( int i = 0; i<len; i++ ) {
                            final Mappable  value   = (Mappable)Array.get( result, i );
                            final String    name    = formatIterableEntry( methodName, i, len );

                            if( value == null ) {
                                hashMap.put( name, null );
                                }
                            else {
                                addRec( hashMap, name, value );
                                }
                            }
                        }
                    }
                else {
                    //
                    // Objet (seul) Mappable
                    //
                    Mappable result = invoke( object, method, hashMap, Mappable.class );

                    if( result != null ) {
                        addRec( hashMap, method.getName() + "().", result );
                        }
                    }
                }
            else {
                //
                // N'implemente pas l'interface Mappable
                //

                if( attributesSet.contains( Attributes.DO_ITERATOR )
                        && Iterator.class.isAssignableFrom( returnType )
                        ) {
                    //
                    // C'est un iterateur
                    //
                    Iterator<?>     iter    = invoke( object, method, hashMap, Iterator.class );
                    final String    name    = method.getName();
                    int             i       = 0;

                    hashMap.put( formatMethodName( name ), "" + iter );

                    while( iter.hasNext() ) {
                        hashMap.put(
                            formatIteratorEntry( name, i++, -1 ),
                            toString( iter.next() )
                            );
                        }
                    }
                else if( attributesSet.contains( Attributes.DO_ITERABLE )
                        && Iterable.class.isAssignableFrom( returnType )
                        ) {
                    //
                    // C'est un objet iterable
                    //
                    Iterable<?>     iterLst = invoke( object, method, hashMap, Iterable.class );
                    Iterator<?>     iter    = iterLst.iterator();
                    final String    name    = method.getName();
                    int             i       = 0;

                    hashMap.put( formatMethodName( method.getName() ), "" + iter );

                    while( iter.hasNext() ) {
                        hashMap.put(
                            formatIterableEntry( name, i++, -1 ),
                            toString( iter.next() )
                            );
                        }
                    }
                else if( attributesSet.contains( Attributes.DO_ENUMERATION )
                        && Enumeration.class.isAssignableFrom( returnType )
                        ) {
                    //
                    // C'est une �numeration
                    //
                    Enumeration<?>  enum0   = invoke( object, method, hashMap, Enumeration.class );
                    final String    name    = method.getName();
                    int             i       = 0;

                    hashMap.put( formatMethodName( method.getName() ), "" + enum0 );

                    if( enum0 != null ) { // BUG Tomcat 5.5

                        while( enum0.hasMoreElements() ) {
                            hashMap.put(
                                formatEnumerationEntry( name, i++, -1 ),
                                toString( enum0.nextElement() )
                                );
                            }
                        }
                    }
                else if( shouldEvaluate( returnType ) ) {
                    //
                    // On analyse l'objet telquel.
                    //
                    Object result = invoke( object, method, hashMap, Object.class );

                    if( result != null ) {
                        if( returnType.isArray() ) {
                            final int       len         = Array.getLength( result );
                            final String    methodName  = method.getName();

                            for( int i = 0; i<len; i++ ) {
                                final Object value = Array.get( result, i );

                                hashMap.put(
                                    formatArrayEntry( methodName, i, len ),
                                    toString( value )
                                    );
                                }
                            }
                        else {
                            //
                            // Ajoute la valeur.
                            //
                            hashMap.put( formatMethodName( method.getName() ), result.toString() );
                            }

                        } // result none null
                    // else { null : deja trait� }

                    }
                // else { On ne souhaite pas traiter ce type ! }
                }

            } //

        } // method without parameter

    } // for(method)

 return new TreeMap<String,String>( hashMap );
}

/**
**
*/
protected String formatIterableEntry( // ----------------------------------
    final String methodeName,
    final int    index,
    final int    max
    )
{
 final String[] params = {
            methodeName,
            Integer.toString( index ),
            Integer.toString( max )
            };

 return this.messageFormatIterableEntry.format( params );
}

/**
**
*/
protected String formatIteratorEntry( // ----------------------------------
    final String methodeName,
    final int    index,
    final int    max
    )
{
 final String[] params = {
            methodeName,
            Integer.toString( index ),
            Integer.toString( max )
            };

 return this.messageFormatIteratorEntry.format( params );
}

/**
**
*/
protected String formatEnumerationEntry( // -------------------------------
    final String methodeName,
    final int    index,
    final int    max
    )
{
 final String[] params = {
            methodeName,
            Integer.toString( index ),
            Integer.toString( max )
            };

 return this.messageFormatEnumerationEntry.format( params );
}

/**
**
*/
protected String formatArrayEntry( // -------------------------------------
    final String methodeName,
    final int    index,
    final int    max
    )
{
 final String[] params = {
            methodeName,
            Integer.toString( index ),
            Integer.toString( max )
            };

 return this.messageFormatArrayEntry.format( params );
}

/**
**
*/
protected String formatMethodName( // -------------------------------------
    final String methodeName
    )
{
 final String[] params = {
            methodeName
            };

 return this.messageFormatMethodName.format( params );
}

/**
** Permet de récupérer la liste des méthodes à traiter.
**
** @param clazz Objet Class de l'objet à analyser.
**
** @return un tableau des méthodes correspondants aux critères.
**
** @since 3.01.015
*/
private final Method[] getDeclaredMethods( final Class<?> clazz ) // ------
{
 if( ! attributesSet.contains( Attributes.DO_PARENT_CLASSES ) ) {
    return clazz.getDeclaredMethods();
    }

 //
 // Initialise une liste de m�thodes
 //
 final Set<Method> methodsSet = new HashSet<Method>();

 for( Method m : clazz.getDeclaredMethods() ) {
    methodsSet.add( m );
    }

 //
 // Construit une liste des classes parentes
 //
 Class<?> c = clazz.getSuperclass();

 while( c != null ) {
    for( Method m : c.getDeclaredMethods() ) {
       methodsSet.add( m );
       }

    c = c.getSuperclass();
    }

 final Method[] methods = new Method[ methodsSet.size() ];
 int            i       = 0;

 for( Method m : methodsSet ) {
    methods[ i++ ] = m;
    }

 return methods;
}

/**
** Permet d'identifier si la classe est Mappable.<br/>
** <br/>
**
** @param clazz Objet Class de l'objet � analyser.
**
** @return true si la classe donn�e (clazz) est une classe qui h�rite de
**         l'interface {@link Mappable}, false autrement
**
** @see Mappable
** @since 3.01.015
*/
protected final boolean isMappable( final Class<?> clazz ) // -------------
{
 if( ! attributesSet.contains( Attributes.DO_RECURSIVE ) ) {
    return false;
    }

 if( clazz.isArray() ) {
    return Mappable.class.isAssignableFrom( clazz.getComponentType() );
    }
 else {
    return Mappable.class.isAssignableFrom( clazz );
    }
}

/**
**
**
** @param returnType    class de l'objet � analyser.
**
** @return true si la classe donn�e (returnType) est a �valuer,
**         false autrement
**
** @since 3.01.015
*/
private final boolean shouldEvaluate( // ----------------------------------
    final Class<?> returnType
    )
{
 final int modifier = returnType.getModifiers();

 if( Modifier.isPrivate( modifier ) ) {
    //
    // M�thode "private"
    //
    if( ! attributesSet.contains( Attributes.TRY_PRIVATE_METHODS ) ) {
        //
        // M�thodes priv�es non demand�es
        //
        return false;
        }
    }

 if( Modifier.isProtected( modifier ) ) {
    //
    // M�thode "protected"
    //
    if( ! attributesSet.contains( Attributes.TRY_PROTECTED_METHODS ) ) {
        //
        // M�thodes prot�g�es non demand�es
        //
        return false;
        }
    }

 if( attributesSet.contains( Attributes.ALL_PRIMITIVE_TYPE ) ) {
    if( returnType.isPrimitive() ) {
        return true;
        }
    }

 if( attributesSet.contains( Attributes.DO_ARRAYS ) ) {
    if( returnType.isArray() ) {
        return true;
        }
    }
/* ?? ne fonctionne pas ???
 if( attributesSet.contains( Attributes.DO_ITERATOR ) ) {
    if( java.util.Iterator.class.isAssignableFrom( returnType ) ) {
        return true;
        }
    }

 if( attributesSet.contains( Attributes.DO_ENUMERATION ) ) {
    if( java.util.Enumeration.class.isAssignableFrom( returnType ) ) {
        return true;
        }
    }
*/
 for( Class<?> c : returnTypeClasses ) {
    if( c.isAssignableFrom( returnType ) ) {
        return true;
        }
    }

 return false;
}



/**
** <P>
** Transforme un object en une cha�ne
** </P>
**
** @param object    Objet � mettre sous forme de chaine.
**
** @return un representation de l'objet sous forme de cha�ne.
**
** @see #toString(EnumSet,Object)
** @since 3.01.015
*/
public <T> String toString( final T object ) // ---------------------------
{
 if( object == null ) {
    return this.toStringNullValue; // "NULL"
    }
 else {
    return toString( this.attributesSet, object );
    }
}

/**
** Relance l'analyse r�cursivement.
**
**
** @param hashMap       x
** @param methodName    x
** @param object        x
**
** @since 3.01.015
*/
private final static void addRec( // --------------------------------------
    final Map<String,String>    hashMap,
    final String                methodName,
    final Mappable              object
    )
{
 final Set<Map.Entry<String,String>> set = object.toMap().entrySet();

 for( Map.Entry<String,String> entry : set ) {
    hashMap.put( methodName + entry.getKey(), entry.getValue() );
    }
}

/**
** <P>
** Transforme un object en une cha�ne
** </P>
** @param attributesSet     EnumSet d'objet Attributes permettant de param�trer
**                          l'analyse ({@link MappableHelper.Attributes#DO_ITERATOR},
**                          {@link MappableHelper.Attributes#DO_ENUMERATION})
** @param object            Objet � mettre sous forme de chaine
**
** @return un representation de l'objet sous forme de cha�ne.
**
** @throw NullPointerException si object est null
*/
private final static <T> String toString( // ------------------------------
    final EnumSet<Attributes>   attributesSet,
    final T                     object
    )
{
 if( object.getClass().isArray() ) {
    final Object[]      array   = (Object[])object;
    final StringBuilder sb      = new StringBuilder();
    boolean             first   = true;

    for( Object o : array ) {
        if( first ) {
            first = false;
            }
        else {
            sb.append( ',' );
            }
        sb.append( toString( attributesSet, o ) );
        }

    return "[" + sb.toString() + "]";
    }
 else if( attributesSet.contains( Attributes.DO_ITERATOR ) ) {

    if( object instanceof Iterator ) {
        final Iterator<?>   iter   = (Iterator<?>)object;
        final StringBuilder sb      = new StringBuilder();
        boolean             first   = true;

        while( iter.hasNext() ) {
            if( first ) {
                first = false;
                }
            else {
                sb.append( ',' );
                }
            sb.append( toString( attributesSet, iter.next() ) );
            }

        return "Iterator[" + sb.toString() + "]";
        }

    }
 else if( attributesSet.contains( Attributes.DO_ENUMERATION ) ) {

    if( object instanceof Enumeration ) {
        final Enumeration<?>  enum0   = (Enumeration<?>)object;
        final StringBuilder   sb      = new StringBuilder();
        boolean               first   = true;

        while( enum0.hasMoreElements() ) {
            if( first ) {
                first = false;
                }
            else {
                sb.append( ',' );
                }

            sb.append( toString( attributesSet, enum0.nextElement() ) );
            }

        return "Enumeration[" + sb.toString() + "]";
        }

    }

 return object.toString();
}

/**
** Appel la m�thode donn�e, en cas de r�ponse null ou d'erreur alimente
** la hashMap
**
** @param object        Objet sur lequel doit �tre appliquer la m�thode
**                      � appeler.
** @param method        M�thode � appeler
** @param hashMap       Object {@link Map}<String,String> vers lequel sera copier
**                      le resultat (Utiliser de pr�f�rence un objet de
**                      type {@link HashMap}, afin d'�viter le doublonage.
** @param resultClass   Type resultat attendu.
**
** @return la valeur retourn�e par la m�thode ou null si le resultat a d�ja
**         �t� trait� (erreur).
*/
protected final <T,R> R invoke( // ----------------------------------------
    final T                     object,
    final Method                method,
    final Map<String,String>    hashMap,
    final Class<R>              resultClass
    )
{
 try {
    Object result = method.invoke( object, (Object[])null );

    if( result == null ) {
        //hashMap.put( formatMethodName( method.getName() ), "*null*" );
        hashMap.put( formatMethodName( method.getName() ), this.toStringNullValue );

        return null; // valeur trait�e: null !
        }
    else {
        try {
            return resultClass.cast( result );
            }
        catch( ClassCastException improbable ) {
            //
            // Erreur lors du cast de l'objet
            //
            throw new RuntimeException(
                        "method.getName() - ClassCastException: " + result,
                        improbable
                        );
            }
        }
    }
 catch( IllegalArgumentException e ) {
    //
    // Erreur interne
    //
    throw e;
    }
 catch( NullPointerException e ) {
    //
    // Erreur interne
    //
    throw e;
    }
 catch( ExceptionInInitializerError e ) {
    //
    // Erreur lors de l'initialisation d'une classe.
    //
    hashMap.put( formatMethodName( method.getName() ), "ExceptionInInitializerError: " + e.getCause() );

    return null; // indique une erreur trait�e.
    }
 catch( IllegalAccessException ignore ) {
    //
    // La m�thode est prot�g�e, on ignore sa valeur
    //
    return null; // valeur trait�e: on ignore la valeur
    }
 catch( java.lang.reflect.InvocationTargetException e ) {
    hashMap.put( formatMethodName( method.getName() ), "InvocationTargetException: " + e.getCause() );

    return null; // indique une erreur trait�e.
    }

}

/**
** <p>
** Retourne des couples (nomDeMethode,valeur).
** </p>
**
** @param factory   Objet {@link MappableHelperFactory} permettant de configurer
**                  l'objet {@link MappableHelper} pour le traitement.
** @param object    Objet � analyser par introsp�ction.
**
** @return un objet Map contenant un couple <String,String>, avec comme cl� le nom
**         de la m�thode et comme valeur la valeur retourn�e par la m�thode sur
**         lequel un toString() a �t� appliqu�.
**
** @since 3.02.009
**
** @see MappableHelperFactory
*/
public static <T> Map<String,String> toMap( // ----------------------------
    final MappableHelperFactory factory,
    final T                     object
    )
{
 return factory.getInstance().toMap( object );
}

/**
** <P>
** Permet d'obtenir un flux XML � partir d'un objet Map contenant
** des cha�nes.
** </P>
**
** @param out   objet Appendable vers lequel sera �mis le flux.
** @param clazz objet Class pour lequelle sera g�n�r� le flux XML
** @param map   objet Map<String,String> � partir duquel sera contruit
**              les valeurs. Cette valeur peut �tre null, dans ce cas
**              le flux XML l'indiquera.
**
** @since 3.01.012
** @since 3.01.022
*/
public static void toXML( // ----------------------------------------------
    final Appendable            out,
    final Class<?>              clazz,
    final Map<String,String>    map
    )
    throws java.io.IOException
{
 if( map == null ) {
    out.append( "<class name=\"" + clazz.getName() + "\" /><!-- NULL OBJECT -->\n" );
    }
 else if( map.size() == 0 ) {
    out.append( "<class name=\"" + clazz.getName() + "\" /><!-- EMPTY -->\n" );
    }
 else {
    out.append( "<class name=\"" + clazz.getName() + "\">\n" );

    for( String name : map.keySet() ) {
       out.append( "  <value name=\"" + name + "\">" + map.get( name ) + "</value>\n" );
        }

    out.append( "</class>\n" );
    }
}

/**
** <P>
** Permet d'obtenir un XML � partir d'un objet Mappable
** </P>
**
** @param out               objet Appendable vers lequel sera �mis le flux.
** @param clazz             objet Class pour lequelle sera g�n�r� le flux XML
** @param aMappableObject   objet Mappable utilis� pour construire le flux
**                          XML. Cette valeur peut �tre null, dans ce cas
**                          le flux XML l'indiquera.
**
** @since 3.01.022
**
** @see #toXML(Appendable,Class,Map)
*/
public static <T extends Mappable> void toXML( // -------------------------
    final Appendable            out,
    final Class<? extends T>    clazz,
    final T                     aMappableObject
    )
    throws java.io.IOException
{
 toXML( out, clazz, aMappableObject == null ? null : aMappableObject.toMap() );
}

/**
** <P>
** Permet d'obtenir un XML � partir d'un objet Mappable
** </P>
**
** @param out               objet Appendable vers lequel sera �mis le flux.
** @param aMappableObject   objet Mappable utilis� pour construire le flux
**                          XML. Ne doit pas �tre null.
**
** @see #toXML(Appendable,Class,Mappable)
*/
public static void toXML( // ----------------------------------------------
    final Appendable    out,
    final Mappable      aMappableObject
    )
    throws java.io.IOException
{
  toXML( out, aMappableObject.getClass(), aMappableObject );
}

/**
** <P>
** Permet d'obtenir un XML � partir d'un objet Mappable
** </P>
**
** @param clazz             objet Class pour lequelle sera g�n�r� le flux XML
** @param aMappableObject   objet Mappable utilis� pour construire le flux
**                          XML. Cette valeur peut �tre null, dans ce cas
**                          le flux XML l'indiquera.
**
** @return un cha�ne contenant le flux XML
**
** @since 3.01.022
**
** @see #toXML(Appendable,Class,Mappable)
** @see #toXML(Appendable,Class,Map)
*/
public static <T extends Mappable> String toXML( // -----------------------
    final Class<? extends T>    clazz,
    final T                     aMappableObject
    )
{
 final StringBuilder sb = new StringBuilder();

 try {
    MappableHelper.toXML( sb, clazz, aMappableObject );
    }
 catch( java.io.IOException improbable ) {
    throw new RuntimeException( improbable );
    }

 return sb.toString();
}

/**
** <P>
** Permet d'obtenir un XML � partir d'un objet Mappable
** </P>
**
** @param aMappableObject   objet Mappable utilis� pour construire le flux
**                          XML. Ne doit pas �tre null.
**
** @return un cha�ne contenant le flux XML
**
** @see #toXML(Class,Mappable)
*/
public static String toXML( final Mappable aMappableObject ) // -----------
{
 return toXML( aMappableObject.getClass(), aMappableObject ) ;
}

} // class

///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////

/**
** Cr�ation d'un nouvel objet MappableHelper, en utilisant l'objet
** {@link MappableHelperFactory} initialis� avec les param�tres
** par d�fauts.
**
** @see MappableHelperFactory
**
** @since 3.01.020
**
** @deprecated use {@link MappableHelperFactory#getInstance()}
@Deprecated
public MappableHelper() // ------------------------------------------------
{
 this( new MappableHelperFactory() );
}
*/

/**
** Cr�ation d'un nouvel objet MappableHelper
**
** @param factory               Objet MappableHelperFactory � partir duquel sera
**                              initialis� les informations de formattage.
** @param methodesNamePattern   Pattern permettant d'identifier les m�thodes � traiter
** @param returnTypeClasses     Collection des objets Class devant �tre analyser
**                              par introsp�ction.
** @param attributesSet         EnumSet d'objet Attributes permettant de param�trer
**                              l'analyse.
**
** @see EnumSet#of
** @see EnumSet#noneOf( Class )
**
** @since 3.01.021
**
** @deprecated use {@link #MappableHelper(MappableHelperFactory)}
@Deprecated
public MappableHelper( // -------------------------------------------------
    final MappableHelperFactory factory,
    final Pattern               methodesNamePattern,
    final Collection<Class<?>>  returnTypeClasses,
    final EnumSet<Attributes>   attributesSet
    )
{
 this.toStringNullValue             = factory.getStringNullValue();
 this.messageFormatIteratorEntry    = new MessageFormat( factory.getMessageFormatIteratorEntry() );
 this.messageFormatIterableEntry    = new MessageFormat( factory.getMessageFormatIterableEntry() );
 this.messageFormatEnumerationEntry = new MessageFormat( factory.getMessageFormatEnumerationEntry() );
 this.messageFormatArrayEntry       = new MessageFormat( factory.getMessageFormatArrayEntry() );
 this.messageFormatMethodName       = new MessageFormat( factory.getMessageFormatMethodName() );

 this.methodesNamePattern   = methodesNamePattern;
 this.returnTypeClasses     = new HashSet<Class<?>>( returnTypeClasses );
 this.attributesSet         = attributesSet;
}
*/

/**
** Cr�ation d'un nouvel objet MappableHelper
**
** @param factory               Objet MappableHelperFactory � partir duquel sera
**                              initialiser les informations de formattage.
** @param methodesNamePattern   Pattern permettant d'identifier les m�thodes � traiter
** @param returnTypeClasses     Liste des objets Class devant �tre analyser
**                              par introsp�ction.
** @param attributesSet         EnumSet d'objet Attributes permettant de param�trer
**                              l'analyse.
**
** @see EnumSet#of
** @see EnumSet#noneOf( Class )
**
** @since 3.01.016
**
** @deprecated use {@link #MappableHelper(MappableHelperFactory)}
@Deprecated
public MappableHelper( // -------------------------------------------------
    final MappableHelperFactory factory,
    final Pattern               methodesNamePattern,
    final Class<?>[]            returnTypeClasses,
    final EnumSet<Attributes>   attributesSet
    )
{
 this(
    factory.addClasses(
        returnTypeClasses
        ).setMethodesNamePattern(
            methodesNamePattern
            ).addAttributes(
                attributesSet
                )
    );
}
*/

/**
** Cr�ation d'un nouvel objet MappableHelper, en utilisant l'objet
** {@link MappableHelperFactory} par d�faut
**
** @param methodesNamePattern   Pattern permettant d'identifier les m�thodes � traiter
** @param returnTypeClasses     Liste des objets Class devant �tre analyser
**                              par introsp�ction.
** @param attributesSet         EnumSet d'objet Attributes permettant de param�trer
**                              l'analyse.
**
** @see EnumSet#of
** @see EnumSet#noneOf( Class )
**
** @since 3.01.021
**
** @deprecated use {@link #MappableHelper(MappableHelperFactory)}
@Deprecated
public MappableHelper( // -------------------------------------------------
    final Pattern               methodesNamePattern,
    final Collection<Class<?>>  returnTypeClasses,
    final EnumSet<Attributes>   attributesSet
    )
{
 this(
    new MappableHelperFactory().addClasses(
        returnTypeClasses
        ).setMethodesNamePattern(
            methodesNamePattern
            ).addAttributes(
                attributesSet
                )
    );
}
*/

/**
** Cr�ation d'un nouvel objet MappableHelper, en utilisant l'objet
** {@link MappableHelperFactory} par d�faut
**
** @param methodesNamePattern   Pattern permettant d'identifier les m�thodes � traiter
** @param returnTypeClasses     Liste des objets Class devant �tre analyser
**                              par introsp�ction.
** @param attributesSet         EnumSet d'objet Attributes permettant de param�trer
**                              l'analyse.
**
** @see EnumSet#of
** @see EnumSet#noneOf( Class )
**
** @since 3.01.015
**
** @deprecated use {@link #MappableHelper(MappableHelperFactory)}
@Deprecated
public MappableHelper( // -------------------------------------------------
    final Pattern               methodesNamePattern,
    final Class<?>[]            returnTypeClasses,
    final EnumSet<Attributes>   attributesSet
    )
{
 this(
    new MappableHelperFactory().addClasses(
        returnTypeClasses
        ).setMethodesNamePattern(
            methodesNamePattern
            ).addAttributes(
                attributesSet
                )
    );
}
*/

/**
** Cr�ation d'un nouvel objet MappableHelper, en utilisant l'objet
** {@link MappableHelperFactory} par d�faut.
**
** @param methodesNamePattern   Pattern permettant d'identifier les m�thodes � traiter
** @param returnTypeClasses     Liste des objets Class devant �tre analyser
**                              par introsp�ction.
**
** @see #DEFAULT_ATTRIBUTES
**
** @since 3.01.021
**
** @deprecated use {@link #MappableHelper(MappableHelperFactory)}
@Deprecated
public MappableHelper( // -------------------------------------------------
    final Pattern               methodesNamePattern,
    final Collection<Class<?>>  returnTypeClasses
    )
{
 this(
    new MappableHelperFactory().addClasses(
        returnTypeClasses
        ).setMethodesNamePattern(
            methodesNamePattern
            ).addAttributes(
                DEFAULT_ATTRIBUTES
                )
    );
}
*/

/**
** Cr�ation d'un nouvel objet MappableHelper, en utilisant l'objet
** {@link MappableHelperFactory} par d�faut.
**
** @param methodesNamePattern   Pattern permettant d'identifier les m�thodes � traiter
** @param returnTypeClasses     Liste des objets Class devant �tre analyser
**                              par introsp�ction.
**
** @see #DEFAULT_ATTRIBUTES
**
** @since 3.01.015
**
** @deprecated use {@link #MappableHelper(MappableHelperFactory)}
@Deprecated
public MappableHelper( // -------------------------------------------------
    final Pattern       methodesNamePattern,
    final Class<?> ...  returnTypeClasses
    )
{
 this(
    new MappableHelperFactory().addClasses(
        returnTypeClasses
        ).setMethodesNamePattern(
            methodesNamePattern
            ).addAttributes(
                DEFAULT_ATTRIBUTES
                )
    );
}
*/

/**
** <p>
** Retourne des couples (nomDeMethode,valeur).
** </p>
**
** @param object                Objet � analyser par introsp�ction.
** @param methodesNamePattern   Pattern permettant d'identifier les m�thodes � traiter
** @param returnTypeClasses     Liste des ClassObjet � analyser par introsp�ction.
** @param attributesSet         EnumSet d'objet Attributes permettant de param�trer
**                              l'analyse.
**
** @return un objet Map contenant un couple <String,String>, avec comme cl� le nom
**         de la m�thode et comme valeur la valeur retourn�e par la m�thode sur
**         lequel un toString() a �t� appliqu�.
**
** @see EnumSet#of
** @see EnumSet#noneOf( Class )
** @see Object#toString()
**
** @deprecated use {@link #toMap(MappableHelperFactory,Object)}
@Deprecated
public static <T> Map<String,String> toMap( // ----------------------------
    final T                     object,
    final Pattern               methodesNamePattern,
    final Class<?>[]            returnTypeClasses,
    final EnumSet<Attributes>   attributesSet
    )
{
 MappableHelper instance =
    new MappableHelper(
            new MappableHelperFactory(),
            methodesNamePattern,
            Arrays.asList( returnTypeClasses ),
            attributesSet
            );

 return instance.toMap( object );
}
*/


/**
** <p>
** Retourne des couples (nomDeMethode,valeur).
** </p>
**
** <p>
** Correspond � l'appel :
** <code>
**  MappableHelper instance
**         = new MappableHelper(
**                 Pattern.compile( "get.*" ),
**                 returnTypeClasses
**                 );
**
**  return instance.toMap( object );
** </code>
** </p>
**
** <p>
** Usage typique :<br/>
** <pre>
**  public abstract class AMappableClass implements Mappable
**  {
**  ...
**      public Map<String,String> toMap()
**      {
**          return MappableHelper.toMap( this, String.class, java.io.File.class, java.net.URL.class );
**      }
**  }
** </pre>
** </P>
** ou encore<br/>
** <pre>
**  public abstract class AMappableClass implements Mappable
**  {
**  ...
**      public Map<String,String> toMap()
**      {
**          return MappableHelper.toMap( this, Object.class );
**      }
**  }
** </pre>
** </p>
**
**
** @param object            Objet � analyser par introsp�ction.
** @param returnTypeClasses Liste des ClassObjet � analyser par introsp�ction.
**
** @return un objet Map contenant un couple <String,String>, avec comme cl� le nom
**         de la m�thode et comme valeur la valeur retourn�e par la m�thode sur
**         lequel un toString() a �t� appliqu�.
**
**
** @see #toMap( Object )
** @see #toMap( Object, Pattern, Class[], EnumSet )
** @see #DEFAULT_METHODS
** @see #DEFAULT_ATTRIBUTES
**
** @deprecated use {@link #toMap(MappableHelperFactory,Object)}
@Deprecated
public static <T> Map<String,String> toMap( // ----------------------------
    final T             object,
    final Class<?> ...  returnTypeClasses
    )
{
 MappableHelper instance
        = new MappableHelper(
                new MappableHelperFactory(),
                DEFAULT_METHODS, // Pattern.compile( "get.*" ),
                Arrays.asList( returnTypeClasses ),
                DEFAULT_ATTRIBUTES
                );

 return instance.toMap( object );
}
*/

/**
** Indique que toutes les classes sont �ligibles.
**
** @deprecated use {@link MappableHelperFactory#ALL_CLASS}
@Deprecated
public final static Class<?>[] ALL_CLASS = { Object.class };
*/

/**
** Permet de voir les r�sultats des types standards.
**
** @deprecated use {@link MappableHelperFactory#STANDARDS_TYPES_CLASS}
@Deprecated
public final static Class<?>[] STANDARDS_TYPES_CLASS = {
    Boolean.class,
    Character.class,
    Enum.class,
    Number.class,
    String.class
    };
*/

/**
** Filtre les observateurs classiques : isXxx(), getXxx()
**
** @deprecated use {@link MappableHelperFactory#DEFAULT_METHODS}
@Deprecated
public final static Pattern DEFAULT_METHODS =
        java.util.regex.Pattern.compile( "(is|get).*" );

*/
