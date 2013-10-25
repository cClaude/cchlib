/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/SingletonIterator.java
** Description   :
**
**  2.01.018 2005.10.14 Claude CHOISNET - Version initiale
**  3.02.018 2006.06.28 Claude CHOISNET
**                      Implemente IterableIterator<T>
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.SingletonIterator
**
*/
package cx.ath.choisnet.util;

import java.util.Iterator;

/**
** <P>
** Construction d'un {@link Iterator} contenant 1 seul object (cas limite)
** </P>
**
** @author Claude CHOISNET
** @since   2.01.018
** @version 3.02.018
**
** @see EmptyIterator
** @see FlattenIterator
** @see FlattenIterable
** @see Iterator
** @see java.util.Collections#singleton
** @see java.util.Collections#singletonList
*/
public class SingletonIterator<T>
    implements
        Iterator<T>,
        Iterable<T>,
        IterableIterator<T>,
        java.io.Serializable
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** */
private boolean hasNext;

/** */
private T item;

/**
** Construction d'un objet Iterator valide, contenant uniquement l'objet
** passé en paramètre.
**
*/
public SingletonIterator( T item ) // -------------------------------------
{
 this.item      = item;
 this.hasNext   = true;
}

/**
** {@inheritDoc}
*/
public boolean hasNext() // -----------------------------------------------
{
 return this.hasNext;
}

/**
** {@inheritDoc}
*/
public T next() // --------------------------------------------------------
    throws java.util.NoSuchElementException
{
 if( this.hasNext ) {
    this.hasNext = false;

    return item;
    }

 throw new java.util.NoSuchElementException();
}

/**
** {@inheritDoc}
*/
public void remove() // ---------------------------------------------------
    throws
        UnsupportedOperationException,
        IllegalStateException
{
 if( this.hasNext ) {
    this.hasNext = false;
    }
 else {
    throw new IllegalStateException();
    }
}

/**
**
*/
public Iterator<T> iterator() // ------------------------------------------
{
 return this;
}

} // class
