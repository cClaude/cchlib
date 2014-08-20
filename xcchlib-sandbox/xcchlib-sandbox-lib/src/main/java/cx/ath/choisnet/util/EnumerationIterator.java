/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/EnumerationIterator.java
** Description   :
**
**  2.00.001 2005.09.12 Claude CHOISNET - Version initiale
**  3.01.014 2006.03.31 Claude CHOISNET
**                      Prend en charge la conversion dans les 2 sens
**  3.02.008 2006.06.09 Claude CHOISNET
**                      Implemente Iterable<T>
**                      Implemente IterableIterator<T>
**  3.02.045 2007.01.16 Claude CHOISNET
**                      nouveau constructeur
**                          EnumerationIterator(Enumeration<?>,Class<T>)
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.EnumerationIterator
**
*/
package cx.ath.choisnet.util;

import java.util.Enumeration;
import java.util.Iterator;

/**
** <P>
** Construction d'un objet Iterator e partir d'un objet Enumeration ou
** vis et versa.
** </P>
**
** @author Claude CHOISNET
** @since   2.00.001
** @version 3.02.045
**
** @see java.util.Collections#enumeration(Collection)
** @see java.util.Collections#list(Enumeration)
*/
public class EnumerationIterator<T>
    implements
        Enumeration<T>,
        Iterator<T>,
        Iterable<T>,
        IterableIterator<T>
{
/** */
private Iterator<T> iterator;

/**
** Construction d'un objet Iterator equivalent e l'Enumeration donne
*/
public EnumerationIterator( final Enumeration<T> enumeration ) // ---------
{
 this.iterator = new Iterator<T>()
    {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public boolean hasNext() // - - - - - - - - - - - - - - - - - - - -
        {
            return enumeration.hasMoreElements();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public T next() //- - - - - - - - - - - - - - - - - - - - - - - - -
            throws java.util.NoSuchElementException
        {
            return enumeration.nextElement();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public void remove() // - - - - - - - - - - - - - - - - - - - - - -
            throws UnsupportedOperationException
        {
            throw new UnsupportedOperationException();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    };
}

/**
** Construction d'un objet Iterator equivalent e l'Enumeration donne (non
** typee pre java 1.5) et la classe de support de cette enumeration.
**
** @since 3.02.045
*/
public EnumerationIterator( // --------------------------------------------
     final Enumeration<?>   enumeration,
     final Class<T>         classT
     )
{
 this.iterator = new Iterator<T>()
    {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public boolean hasNext() // - - - - - - - - - - - - - - - - - - - -
        {
            return enumeration.hasMoreElements();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public T next() //- - - - - - - - - - - - - - - - - - - - - - - - -
            throws java.util.NoSuchElementException
        {
            return classT.cast( enumeration.nextElement() );
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public void remove() // - - - - - - - - - - - - - - - - - - - - - -
            throws UnsupportedOperationException
        {
            throw new UnsupportedOperationException();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    };
}

/**
** Construction d'un objet Enumeration equivalent e l'Iterator donne
*/
public EnumerationIterator( final Iterator<T> iterator ) // ---------------
{
 this.iterator = iterator;
}

/**
**
*/
@Override
public boolean hasNext() // -----------------------------------------------
{
    return this.iterator.hasNext();
}

/**
**
*/
@Override
public boolean hasMoreElements() // ---------------------------------------
{
    return this.iterator.hasNext();
}

/**
**
*/
@Override
public T next() // --------------------------------------------------------
    throws java.util.NoSuchElementException
{
    return this.iterator.next();
}

/**
**
*/
@Override
public T nextElement() // -------------------------------------------------
    throws java.util.NoSuchElementException
{
    return this.iterator.next();
}

/**
**
*/
@Override
public void remove() // ---------------------------------------------------
    throws UnsupportedOperationException, IllegalStateException
{
    this.iterator.remove();
}

/**
**
*/
@Override
public Iterator<T> iterator() // ------------------------------------------
{
 return this.iterator;
}

} // class
