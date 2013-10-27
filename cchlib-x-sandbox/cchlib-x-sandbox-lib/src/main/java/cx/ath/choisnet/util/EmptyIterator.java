/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/EmptyIterator.java
** Description   :
**
**  2.00.001 2005.09.12 Claude CHOISNET - Version initiale
**  2.00.006 2005.09.30 Claude CHOISNET
**                      Implemente Iterable<T>
**  2.01.004 2005.10.03 Claude CHOISNET - Version initiale
**                      Impl�mente java.io.Serializable
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.EmptyIterator
**
*/
package cx.ath.choisnet.util;

import java.util.Iterator;

/**
** <P>
** Construction d'un {@link Iterator} ne contenant aucun object  (cas limite)
** </P>
**
** @author Claude CHOISNET
** @since   2.00.001
** @version 2.01.004
**
** @see Iterator
** @see FlattenIterator
** @see FlattenIterable
** @see SingletonIterator
** @see java.util.Collections#emptySet()
** @see java.util.Collections#emptyList()
*/
public class EmptyIterator<T>
    implements
        Iterator<T>,
        Iterable<T>,
        java.io.Serializable
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
** Construction d'un objet Iterator vide, mais valide.
**
*/
public EmptyIterator() // -------------------------------------------------
{
 // empty
}

/**
** {@inheritDoc}
*/
@Override
public boolean hasNext() // -----------------------------------------------
{
    return false;
}

/**
** {@inheritDoc}
*/
@Override
public T next() // --------------------------------------------------------
{
    throw new java.util.NoSuchElementException();
}

/**
** {@inheritDoc}
*/
@Override
public void remove() // ---------------------------------------------------
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
