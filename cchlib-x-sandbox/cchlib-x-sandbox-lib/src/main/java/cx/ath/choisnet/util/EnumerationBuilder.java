/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/EnumerationBuilder.java
** Description   :
**
**  1.50.___ 2005.05.23 Claude CHOISNET - Version initiale
**  2.00.003 2005.09.17 Claude CHOISNET
**                      Adapation JDK1.5 et generics
**                      Ajout de toEnumerationString(Enumeration)
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.EnumerationBuilder
**
*/
package cx.ath.choisnet.util;

import java.util.Enumeration;

/**
**
**
** @author Claude CHOISNET
** @since   1.50
** @version 2.00.003
**
*/
public class EnumerationBuilder
{

/**
** @return  une {@link Enumeration} ne contenant aucun object
*/
public static <T> Enumeration<T> toEnumeration() // -----------------------
{
 return new Enumeration<T>()
    {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public boolean hasMoreElements() // - - - - - - - - - - - - - - - -
        {
            return false;
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public T nextElement() // - - - - - - - - - - - - - - - - - - - - -
            throws java.util.NoSuchElementException
        {
            throw new java.util.NoSuchElementException();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    };
}

/**
** @return une {@link Enumeration} sur l'ensemble des objects du tableau
*/
public static <T> Enumeration<T> toEnumeration( final T[] array ) // ------
{
 if( array == null ) {
    return toEnumeration();
    }
 else {
    return toEnumeration( array, 0, array.length );
    }
}

/**
** @return  un {@link Enumeration} sur les objets du tableau de offset et jusqu'à la
**          position len - 1
*/
public static <T> Enumeration<T> toEnumeration( // ------------------------
    final T[]   array,
    final int   offset,
    final int   len
    )
{
 return new Enumeration<T>()
    {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private int index = offset;
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public boolean hasMoreElements() // - - - - - - - - - - - - - - - -
        {
            return index < len;
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public T nextElement() // - - - - - - - - - - - - - - - - - - - - -
            throws java.util.NoSuchElementException
        {
            if( index < len ) {
                try {
                    return array[ index ++ ];
                    }
                catch( IndexOutOfBoundsException e ) {
                    // empty
                    }
                }

            throw new java.util.NoSuchElementException( "index = " + index );
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    };
}

/**
** @return  un {@link Enumeration} correspondant à l'Iterator donné
*/
public static <T> Enumeration<T> toEnumeration( // ------------------------
    final java.util.Iterator<T> iterator
    )
{
 return new Enumeration<T>()
    {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public boolean hasMoreElements() // - - - - - - - - - - - - - - - -
        {
            return iterator.hasNext();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public T nextElement() // - - - - - - - - - - - - - - - - - - - - -
            throws java.util.NoSuchElementException
        {
            return iterator.next();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    };
}

/**
** @return un {@link Enumeration}<String> à partir d'un {@link Enumeration}<?>
*/
public static Enumeration<String> toEnumerationString( // -----------------
    final Enumeration<?> enumeration
    )
{
 return new Enumeration<String>()
    {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public boolean hasMoreElements() // - - - - - - - - - - - - - - - -
        {
            return enumeration.hasMoreElements();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public String nextElement() //- - - - - - - - - - - - - - - - - - -
            throws java.util.NoSuchElementException
        {
            return enumeration.nextElement().toString();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    };
}

/**
** Transforme une énumération non typée en énumération typée.
**
** @return un {@link Enumeration}<T> à partir d'un {@link Enumeration}<?> et de la classe
**         de support.
*/
public static <T> Enumeration<? extends T> toEnumeration( // --------------
    final Enumeration<?>        enumeration,
    final Class<? extends T>    clazz
    )
{
 if( enumeration == null ) {
    return toEnumeration();
    }

 return new Enumeration<T>()
    {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public boolean hasMoreElements() // - - - - - - - - - - - - - - - -
        {
            return enumeration.hasMoreElements();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public T nextElement() // - - - - - - - - - - - - - - - - - - - - -
            throws java.util.NoSuchElementException
        {
            return clazz.cast( enumeration.nextElement() );
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    };
}

/**
** @deprecated use toEnumeration() instead
@Deprecated
public static Enumeration getEmptyEnumeration() // ------------------------
{
 return toEnumeration();
}
*/

/**
** @deprecated use toEnumeration(Object[]) instead
@Deprecated
public static Enumeration getEnumerationFromArray( Object[] array ) // ----
{
 return toEnumeration( array );
}
*/

/**
** @deprecated use toEnumeration(Object[],int,int) instead
@Deprecated
public static Enumeration getEnumerationFromArray( // ---------------------
    final Object[]  array,
    final int       offset,
    final int       len
    )
{
 return toEnumeration( array );
}
*/

} // class
