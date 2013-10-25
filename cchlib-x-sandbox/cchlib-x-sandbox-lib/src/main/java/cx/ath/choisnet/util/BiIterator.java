/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/BiIterator.java
** Description   :
**
**  1.30.010 2005.05.15 Claude CHOISNET - Version initiale
**  2.00.003 2005.09.17 Claude CHOISNET
**                      Migration generics jdk1.5
**  2.01.000 2005.09.30 Claude CHOISNET
**                      Implemente Iterable<T>
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.BiIterator
**
*/
package cx.ath.choisnet.util;

import java.util.Iterator;

/**
**
** @author  Claude CHOISNET
** @version 2.01.000
**
** @see IteratorBuilder
** @see FlattenIterator
** @see FlattenIterable
*/
public abstract class BiIterator<T>
    implements Iterator<T>, Iterable<T>
{
/** */
private Iterator<T> firstIter;

/** */
private Iterator<T> secondIter;

/**
**
*/
public BiIterator(// ------------------------------------------------------
    Iterator<T> firstIter,
    Iterator<T> secondIter
    )
{
 this.firstIter     = firstIter;
 this.secondIter    = secondIter;
}

/**
**
*/
public boolean hasNext() // -----------------------------------------------
{
 if( firstIter.hasNext() ) {
    return true;
    }
 else {
    return secondIter.hasNext();
    }
}

/**
**
*/
public T next() // ---------------------------------------------------------
    throws java.util.NoSuchElementException
{
 if( firstIter.hasNext() ) {
    return firstIter.next();
    }
 else {
    return secondIter.next();
    }
}

/**
**
*/
public void remove() // ---------------------------------------------------
{
 throw new UnsupportedOperationException();
}

/**
**
*/
public Iterator<T> iterator() // ------------------------------------------
{
 return this;
}

} // class
