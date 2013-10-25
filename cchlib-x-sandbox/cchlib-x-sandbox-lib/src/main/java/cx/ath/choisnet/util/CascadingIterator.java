/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/CascadingIterator.java
** Description   :
**
**  2.01.018 2005.10.14 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.CascadingIterator
**
*/
package cx.ath.choisnet.util;

import java.util.Iterator;

/**
** <P>
** Permet de construire un objet Iterator à partir d'un Iterateur {@link Iterator}
** d'objets iterables {@link Iterable}>
** </P>
**
** @author Claude CHOISNET
** @since   2.01.018
** @version 2.01.018
**
*/
public class CascadingIterator<T>
    extends ComputableIterator<T>
{
/**
**
*/
private Iterator<? extends Iterable<T>> mainIterator;

/**
**
*/
private Iterator<T> currentIterator;

/**
** <P>
** Permet de construire un objet à partir d'un Iterateur <Iterator>
** d'objet iterable <Iterable>.
** </P>
*/
public CascadingIterator( // ----------------------------------------------
    Iterator<? extends Iterable<T>> iterator
    )
{
 this.mainIterator      = iterator;
 this.currentIterator   = null;
}

/**
**
*/
public T computeNext() // -------------------------------------------------
    throws java.util.NoSuchElementException
{
 for(;;) {
    if( this.currentIterator != null ) {
        try {
            return this.currentIterator.next();
            }
        catch( java.util.NoSuchElementException ignore ) {
            //
            // On continu
            //
            }
        }

    this.currentIterator = mainIterator.next().iterator();
    }
}

} // class
