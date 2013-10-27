/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/IteratorFilter.java
** Description   :
**
**  2.00.001 2005.09.12 Claude CHOISNET - Version initiale
**                          cx.ath.choisnet.util.IteratorWrapper
**  2.00.006 2005.09.30 Claude CHOISNET
**                      Implemente Iterable<T>
**  2.01.015 2005.10.14 Claude CHOISNET - Nouveau nom
**                          cx.ath.choisnet.util.IteratorFilter
**  3.02.007 2006.06.07 Claude CHOISNET
**                      Impl�mente IterableIterator<T>
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.IteratorFilter
**
*/
package cx.ath.choisnet.util;

import java.io.File;
import java.util.Iterator;

/**
** <P>
** {@link Iterator} bas� sur un {@link Iterator} de base et sur le filtre donn�.
** </P>
** <P>
** Cette classe tente d'optimiser les traitements afin de limiter les
** parcours et les allocations d'objets.
** </P>
**
** @author Claude CHOISNET
** @since   2.00.001
** @version 3.02.007
**
** @see IteratorWrapper
** @see EnumerationIterator
*/
public class IteratorFilter<T>
    extends ComputableIterator<T>
        implements
            Iterable<T>,
            IterableIterator<T>
{
/** Iterator de r�f�rence */
private Iterator<T> iterator;

/** Filtre associer */
private Selectable<T> filter;

/**
** Iterator bas� sur un Iterator de base et sur le filtre donn�.
*/
public IteratorFilter( // -------------------------------------------------
    final Iterator<T>   iterator,
    final Selectable<T> filter
    )
{
 this.iterator  = iterator;
 this.filter    = filter;
}

/**
**
*/
@Override
public T computeNext() // -------------------------------------------------
    throws java.util.NoSuchElementException
{
 while( this.iterator.hasNext() ) {
    T currentObject = this.iterator.next();

    if( this.filter.isSelected( currentObject ) ) {
        //
        // Trouv� ! On sort avec le prochain object valide
        //
        return currentObject;
        }
    }

 //
 // Not found
 //
 throw new java.util.NoSuchElementException();
}

/**
**
*/
@Override
public Iterator<T> iterator() // ------------------------------------------
{
 return this;
}

/**
**
*/
public static Selectable<File> wrappe( // ---------------------------------
    final java.io.FileFilter fileFilter
    )
{
 return new Selectable<File>()
    {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public boolean isSelected( File file ) // - - - - - - - - - - - - -
        {
            return fileFilter.accept( file );
        }
    };
}

/**
** <p>
** Transforme un {@link Iterator} en une cha�ne en utilisant la m�thode {@link Object#toString()}.
** </p>
**
** @param iterator  Iterator Object to use
** @param separator a String
**
** @return un String correspondant � la concat�nation des valeurs {@link Object#toString()}
**         de l'iterateur donn�e. Chaque valeur �tant s�par�e par le contenu
**         de la cha�ne 'separator'
**
** @since 1.53.002
*/
public static String toString( Iterator iterator, String separator ) // ---
{
 final StringBuilder sb = new StringBuilder();

 if( iterator.hasNext() ) {
    sb.append( iterator.next().toString() );
    }

 while( iterator.hasNext() ) {
    sb.append( separator );
    sb.append( iterator.next().toString() );
    }

 return sb.toString();
}

} // class
