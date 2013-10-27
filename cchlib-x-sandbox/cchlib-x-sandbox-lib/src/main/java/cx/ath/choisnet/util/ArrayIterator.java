/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/ArrayIterator.java
** Description   :
**
**  2.00.001 2005.09.12 Claude CHOISNET - Version initiale
**  2.00.006 2005.09.30 Claude CHOISNET
**                      Implemente Iterable<T>
**  3.02.018 2006.06.28 Claude CHOISNET
**                      Implemente IterableIterator<T>
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.ArrayIterator
**
*/
package cx.ath.choisnet.util;

import java.util.Iterator;

/**
** <P>
** Classe permettant de construire des objets {@link Iterator} sans pour cela
** avoir besoin de cr�er de nouvelle collection.
** </P>
** <P>
** Cette classe tente d'optimiser les traitements afin de limiter les
** parcours et les allocations d'objets.
** </P>
**
** @author Claude CHOISNET
** @since   2.00.001
** @version 3.02.018
**
*/
public class ArrayIterator<T>
    implements Iterator<T>, Iterable<T>, IterableIterator<T>
{
/** Tableau de reference */
private final T[] array;

/** Taille du tableau ou position de fin de l'iteration */
private final int len;

/** Position courante dans le tableau */
private int index;

/**
** <P>
** Construction d'objet Iterator parcourant l'ensemble des objects du tableau.
** </P>
** <br />
** Ce constructeur accepte la valeur null pour le param�tre 'array', dans ce
** cas elle retourne un objet {@link Iterator} vide, mais valide.
** <br />
*/
public ArrayIterator( final T[] array ) // --------------------------------
{
 this.array = array;
 this.index = 0;

 if( array == null ) {
    this.len = 0;
    }
 else {
    this.len = this.array.length;
    }
}

/**
** <P>
** Construction d'objet Iterator parcourant les objects du tableau � partir
** de l'indice offset et jusqu'� la position len - 1
** </P>
** <br />
** Cette m�thode <b>n'accepte pas</b> la valeur null pour le param�tre 'array'
** <br />
*/
public ArrayIterator( // --------------------------------------------------
    final T[]   array,
    final int   offset,
    final int   len
    )
{
 this.array  = array;
 this.index  = offset;
 this.len    = len;
}

/**
** {@inheritDoc}
*/
@Override
public boolean hasNext() // -----------------------------------------------
{
    return index < len;
}

/**
** {@inheritDoc}
*/
@Override
public T next() // --------------------------------------------------------
{
    try {
        return array[ index++ ];
        }
    catch( IndexOutOfBoundsException e ) {
        throw new java.util.NoSuchElementException();
        }
    catch( NullPointerException e ) {
        throw new java.util.NoSuchElementException();
        }
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
