/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/ArrayCollection.java
** Description   :
**
**  3.02.018 2006.06.28 Claude CHOISNET - Version initiale
**  3.02.022 2006.07.04 Claude CHOISNET
**                      Ajout de: add(E)
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.ArrayIterator
**
*/
package cx.ath.choisnet.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
** <p>
** Cette classe à pour objectif de simplifier les échanges entre les
** tableaux natif et l'API des collections JAVA.
** </p>
**
** @author Claude CHOISNET
** @since   3.02.018
** @version 3.02.022
**
*/
public class ArrayCollection<E>
    extends java.util.AbstractCollection<E>
{
/** Tableau de reference */
private final List<E> list;

/**
**
*/
public ArrayCollection() // -----------------------------------------------
{
 this.list = new LinkedList<E>();
}

/**
** <p>
** Construction d'une {@link java.util.Collection} à partir d'un tableau d'objet
** </p>
**
** @param array Tableau de valeurs à ajouter dans la collection.
**
** <p>
** Ce constructeur accepte la valeur null pour le paramètre 'array', dans ce
** cas elle retourne une vide {@link java.util.Collection}, mais valide.
** </p>
*/
public ArrayCollection( final E ... array ) // ----------------------------
{
 if( array == null ) {
    this.list = new LinkedList<E>();
    }
  else {
    this.list = new ArrayList<E>( array.length );

    for( E item : array ) {
        this.add( item );
        }
    }
}

/**
** <p>
** Construction d'une {@link java.util.Collection} à partir d'une portion
** d'un tableau d'objet.
** </p>
**
** @param array     Tableau de valeurs à ajouter dans la collection.
** @param offset    Indice à partir duquel commencera la copie
** @param len       Indice de fin de la copie
**
** <p>
** Construction d'objet Iterator parcourant les objects du tableau à partir
** de l'indice offset et jusqu'à la position len - 1
** </p>
** <p>
** Cette méthode <b>n'accepte pas</b> la valeur null pour le paramètre 'array'
** </p>
*/
public ArrayCollection( // --------------------------------------------------
    final E[]   array,
    final int   offset,
    final int   len
    )
{
 this.list = new ArrayList<E>( array.length );

 for( int i = offset; i<len; i++ ) {
    this.add( array[ i ] );
    }
}

/**
** {@inheritDoc}
*/
public int size() // ------------------------------------------------------
{
 return this.list.size();
}

/**
** {@inheritDoc}
*/
public Iterator<E> iterator() // ------------------------------------------
{
 return this.list.iterator();
}

/**
** {@inheritDoc}
*/
public boolean add( final E element ) // ----------------------------------
{
 return this.list.add( element );
}

/**
**
** @see #add
*/
public ArrayCollection<E> append( final E element ) // --------------------
{
 this.add( element );

 return this;
}

} // class
