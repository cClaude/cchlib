/*
** $VER: InfosServletDisplayImpl2.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/debug/impl/InfosServletDisplayImpl2.java
** Description   :
** Encodage      : ANSI
**
**  2.01.032 2005.11.21 Claude CHOISNET - Version initiale
**  3.02.010 2006.06.16 Claude CHOISNET
**                      Ajout de: getMappableHelperFactory()
**                      Ajout de: toMap(MappableHelperFactory,T)
**                      Ajout de: toMap(MappableHelper,T)
**  3.02.047 2007.01.20 Claude CHOISNET
**                      Modification de: toMap(MappableHelper,T) qui accepte
**                      maintenant les valeurs "null" sur ces deux paramètres.
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl2
**
*/
package cx.ath.choisnet.servlet.debug.impl;

import cx.ath.choisnet.lang.reflect.MappableHelper;
import cx.ath.choisnet.lang.reflect.MappableHelperFactory;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
**
**
** @author Claude CHOISNET
** @since   2.01.032
** @version 3.02.047
*/
public class InfosServletDisplayImpl2<T>
    extends InfosServletDisplayImpl
{

/**
**
*/
public InfosServletDisplayImpl2( // ----------------------------------------
    final String    title,
    final String    anchorName,
    final T         object
    )
{
 super( title, anchorName, toMap( object ) );
}

/**
**
*/
public static MappableHelperFactory getMappableHelperFactory() // ---------
{
 return new MappableHelperFactory().setMethodesNamePattern(
            "(get|is).*"
            ).addClasses(
                Object.class
                ).addAttributes(
                    MappableHelper.Attributes.ALL_PRIMITIVE_TYPE,
                    MappableHelper.Attributes.DO_ARRAYS,
                    MappableHelper.Attributes.DO_ITERATOR,
                    MappableHelper.Attributes.DO_ITERABLE,
                    MappableHelper.Attributes.DO_ENUMERATION,
                    MappableHelper.Attributes.DO_RECURSIVE
                    );
}

/**
**
*/
private final static <T> Map<String,String> toMap( // ---------------------
    final T object
    )
{
 return toMap( getMappableHelperFactory(), object );
}

/**
**
** @since 3.02.009
*/
private final static <T> Map<String,String> toMap( // ---------------------
    final MappableHelperFactory factory,
    final T                     object
    )
{
 return toMap( factory.getInstance(), object );
}

/**
**
** @since 3.02.009
*/
private final static <T> Map<String,String> toMap( // ---------------------
    final MappableHelper    aMappableHelper,
    final T                 object
    )
{
 if( aMappableHelper == null || object == null ) {
    return new HashMap<String,String>();
    }
 else {
    return aMappableHelper.toMap( object );
    }
}


} // class
