/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/HTMLParameterHelper.java
** Description   :
** Encodage      : ANSI
**
**  3.02.006 2006.06.06 Claude CHOISNET - Version initiale
**  3.02.009 2006.06.12 Claude CHOISNET
**                      Ajout de getEntry(Iterable<T>,String)
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.HTMLParameterHelper
**
*/
package cx.ath.choisnet.servlet;

import cx.ath.choisnet.util.IterableIterator;
import cx.ath.choisnet.util.IteratorFilter;
import java.util.Iterator;

/**
** <p>
** Outils permettant de cr�er des identificateurs HTML, CSS ou JavaScript
** � partir du hashcode ({@link Object#hashCode()}) de l'objet Java
** concern� ({@link #getHTMLID(Object)})
** et de r�cup�rer le hashcode � partir de l'ID ({@link #getHashCode(String)})
** </p>
**
** @author Claude CHOISNET
** @since   3.02.006
** @version 3.02.009
**
** @see javax.servlet.http.HttpServletRequest
** @see ParameterValue
** @see SimpleServletRequest
*/
public class HTMLParameterHelper<T>
{

/**
** Encode le hashcode de l'objet en un cha�ne qui peut permettre
** d'identifier un objet HTML, CSS ou JavaScript.
**
** @see #hashCode()
** @see #getHashCode(String)
*/
public final static String getHTMLID( // ----------------------------------
    final Object object
    )
{
 return "ID" + Integer.toHexString( object.hashCode() ).toUpperCase();
}

/**
** Permet de retrouver le hashcode encod� � l'aide de la m�thode
** {@link #getHTMLID(Object)}
**
** @see #getHTMLID(Object)
** @see #getHashCodes(ParameterValue)
** @see #getHashCodes(String[])
*/
public final static int getHashCode( // -----------------------------------
    final String htmlID
    )
{
 if( htmlID.startsWith( "ID" ) ) {
    return Integer.parseInt( htmlID.substring( 2 ), 16 );
    }

 return 0;
}

/**
**
** @see #getHashCode(String)
** @see #getHashCodes(ParameterValue)
*/
public final static int[] getHashCodes( // --------------------------------
    final String[] htmlIDs
    )
{
 final int[]    hashCodes   = new int[ htmlIDs.length ];
 int            i           = 0;

 for( String htmlID : htmlIDs ) {
    hashCodes[ i++ ] = getHashCode( htmlID );
    }

 return hashCodes;
}

/**
**
** @see #getHashCode(String)
** @see #getHashCodes(String[])
*/
public final static int[] getHashCodes( // --------------------------------
    final ParameterValue aParameterValue
    )
{
 return getHashCodes( aParameterValue.toArray() );
}

/**
**
** @see #getHashCode(String)
** @see #getHashCodes(String[])
**
** @return l'objet trouv� ou null sinon
**
** @since 3.02.009
*/
public final static <T> T getEntry( // ------------------------------------
    final Iterable<T>   collection,
    final String        htmlID
    )
{
 final int hashCode = getHashCode( htmlID );

 for( T item : collection ) {
    if( item.hashCode() == hashCode ) {
        return item;
        }
    }

 return null;
}

/**
**
** @see #getHashCode(String)
** @see #getHashCodes(String[])
*/
public final static <T> IterableIterator<T> select( // ----------------------------
    final Iterator<T>   iter,
    final String[]      htmlIDs
    )
{
 return new IteratorFilter<T>(
                iter,
                new HTMLParameterHelper.Selectable<T>( htmlIDs )
                );
}

/**
**
** @see #getHashCode(String)
** @see #getHashCodes(String[])
*/
public final static <T> IterableIterator<T> select( // ----------------------------
    final Iterator<T>   iter,
    final int[]         hashCodes
    )
{
 return new IteratorFilter<T>(
                iter,
                new HTMLParameterHelper.Selectable<T>( hashCodes )
                );
}

    /**
    ** N'est pas s�rializable
    */
    public static class Selectable<T>
        implements cx.ath.choisnet.util.Selectable<T>
    {
        private final int[] hashCodes;

        public Selectable( final int[] hashCodes )
        {
            this.hashCodes = hashCodes;
        }

        public Selectable( final String[] htmlIDs )
        {
         this( getHashCodes( htmlIDs ) );
        }

        @Override
        public boolean isSelected( final T o )
        {
            final int oHashCode = o.hashCode();

            for( int hashCode : hashCodes ) {
                if(  hashCode == oHashCode ) {
                    return true;
                    }
                }

            return false;
        }
    }


} // class
