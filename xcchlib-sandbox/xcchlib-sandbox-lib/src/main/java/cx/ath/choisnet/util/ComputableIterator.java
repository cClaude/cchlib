/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/ComputableIterator.java
** Description   :
**
**  2.00.005 2005.09.29 Claude CHOISNET - Version initiale
**  2.01.015 2005.10.14 Claude CHOISNET
**                      Implemente Iterable<T>
**  3.02.006 2006.06.06 Claude CHOISNET
**                      Cette version implemente la nouvelle interface
**                      cx.ath.choisnet.util.IterableIterator
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.ComputableIterator
**
*/
package cx.ath.choisnet.util;

import java.util.Iterator;

/**
** Permet de construire des {@link Iterator} uniquement e l'aide de la methode
** computeNext()
**
** @author Claude CHOISNET
** @since   2.00.005
** @version 3.02.006
**
** @see #computeNext()
*/
public abstract class ComputableIterator<T>
    implements
        Iterator<T>,
        Iterable<T>,
        IterableIterator<T>
{
/**
**
*/
private T nextObject = null;

/**
** Compute next item, and return it.
**
** @return the next item of this Iterator
**
** @throws java.util.NoSuchElementException if there is not next item.
*/
public abstract T computeNext() // ----------------------------------------
    throws java.util.NoSuchElementException;

/**
** {@inheritDoc}
*/
@Override
public boolean hasNext() // -----------------------------------------------
{
 if( nextObject == null ) {
    //
    // L'objet suivant n'est pas pret, on le prepare
    //
    try {
        nextObject = computeNext();
        }
    catch( java.util.NoSuchElementException e ) {
        //
        // Il n'existe pas !
        //
        return false;
        }
    }

 //
 // L'object suivant est pret et valide !
 //
 return true;
}

/**
** {@inheritDoc}
**
** @throws java.util.NoSuchElementException
*/
@Override
public T next() // --------------------------------------------------------
    throws java.util.NoSuchElementException
{
 if( nextObject == null ) {
    //
    // L'objet suivant n'est pas pret, on le prepare
    //
    //  Note: on sort en NoSuchElementException s'il n'existe pas
    //
    nextObject = computeNext();
    }

 //
 // On sauvegarde l'element courant
 //
 T returnObject = nextObject;

 //
 // On cacul le prochain
 //
 try {
    nextObject = computeNext();
    }
 catch( java.util.NoSuchElementException e ) {
    nextObject = null; // pas de prochain
    }

    return returnObject;
}

/**
** This method is not supported. Be careful to supporte this function
** you probably need to overwrite next() to keep in memory last value.
**
** @throws UnsupportedOperationException
*/
@Override
public void remove() // ---------------------------------------------------
    throws UnsupportedOperationException
{
    throw new UnsupportedOperationException();
}

/**
**
*/
@Override
public Iterator<T> iterator() // ------------------------------------------
{
 return this;
}

} // class
