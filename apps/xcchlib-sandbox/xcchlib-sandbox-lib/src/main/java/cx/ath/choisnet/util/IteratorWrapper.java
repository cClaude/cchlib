/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/IteratorWrapper.java
** Description   :
**
**  2.01.015 2005.10.14 Claude CHOISNET - Version initiale
**                      Implemente Iterable<T>
**  2.01.020 2005.10.20 Claude CHOISNET
**                      Ajout du constructeur :
**                          IteratorWrapper(Collection<T>,Wrappable<T,U>)
**  3.02.007 2006.06.07 Claude CHOISNET
**                      Implemente IterableIterator<U>
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.IteratorWrapper
**
*/
package cx.ath.choisnet.util;

import java.util.Collection;
import java.util.Iterator;

/**
** Permet de construire des {@link Iterator} e partir d'un autre Iterateur
** en changeant de type e la volee.
**
** @author Claude CHOISNET
** @since   2.01.015
** @version 3.02.007
**
** @see IteratorFilter
** @see EnumerationIterator
*/
public class IteratorWrapper<T,U>
    implements
        Iterator<U>,
        Iterable<U>,
        IterableIterator<U>
{
/**
**
*/
private Iterator<T> iterator;

/**
**
*/
private Wrappable<T,U> wrapper;

/**
**
*/
public IteratorWrapper( // ------------------------------------------------
    Iterator<T>     iterator,
    Wrappable<T,U>  wrapper
    )
{
 this.iterator  = iterator;
 this.wrapper   = wrapper;
}

/**
**
*/
public IteratorWrapper( // ------------------------------------------------
    Collection<T>   c,
    Wrappable<T,U>  wrapper
    )
{
 this( c.iterator(), wrapper );
}

/**
** {@inheritDoc}
*/
@Override
public boolean hasNext() // -----------------------------------------------
{
 return this.iterator.hasNext();
}

/**
** {@inheritDoc}
**
** @throws java.util.NoSuchElementException
*/
@Override
public U next() // --------------------------------------------------------
    throws java.util.NoSuchElementException
{
 return wrapper.wrappe( this.iterator.next() );
}

/**
** {@inheritDoc}
*/
@Override
public void remove() // ---------------------------------------------------
    throws UnsupportedOperationException
{
 this.iterator.remove();
}

/**
**
*/
@Override
public Iterator<U> iterator() // ------------------------------------------
{
 return this;
}

} // class
