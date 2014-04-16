/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/BiEnumeration.java
** Description   :
**
**  1.30.010 2005.05.15 Claude CHOISNET - Version initiale
**  2.00.003 2005.09.17 Claude CHOISNET
**                      Migration generics jdk1.5
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.BiEnumeration
**
*/
package cx.ath.choisnet.util;

import java.util.Enumeration;

/**
**
** @author  Claude CHOISNET
** @version 2.00.003
**
** @see IteratorBuilder
** @see FlattenIterator
** @see FlattenIterable
*/
public abstract class BiEnumeration<T>
    implements Enumeration<T>
{
/** */
private Enumeration<T> firstEnum;

/** */
private Enumeration<T> secondEnum;

/**
**
*/
public BiEnumeration(// ------------------------------------------------------
    Enumeration<T> firstEnum,
    Enumeration<T> secondEnum
    )
{
 this.firstEnum     = firstEnum;
 this.secondEnum    = secondEnum;
}

/**
**
*/
@Override
public boolean hasMoreElements() // -----------------------------------------------
{
 if( firstEnum.hasMoreElements() ) {
    return true;
    }
 else {
    return secondEnum.hasMoreElements();
    }
}

/**
**
*/
public T next() // --------------------------------------------------------
    throws java.util.NoSuchElementException
{
 if( firstEnum.hasMoreElements() ) {
    return firstEnum.nextElement();
    }
 else {
    return secondEnum.nextElement();
    }
}

} // class
