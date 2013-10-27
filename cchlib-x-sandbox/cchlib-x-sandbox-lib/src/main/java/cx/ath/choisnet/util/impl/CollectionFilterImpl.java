/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/impl/CollectionFilterImpl.java
** Description   :
**
**  3.02.008 2006.06.09 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.impl.CollectionFilterImpl
**
*/
package cx.ath.choisnet.util.impl;

import cx.ath.choisnet.util.Selectable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Iterator;

/**
** Objectif de l'interface traiter des algorithmes g�n�riques de filtrage.
**
** @author Claude CHOISNET
** @since   3.02.008
** @version 3.02.008
**
** @see cx.ath.choisnet.util.IteratorFilter
** @see cx.ath.choisnet.util.Selectable
*/
public class CollectionFilterImpl<T>
    implements
        cx.ath.choisnet.util.CollectionFilter<T>

{
/** */
private Selectable<T> selector;

/**
**
*/
public CollectionFilterImpl( final Selectable<T> selector ) // ------------
{
 this.selector = selector;
}

/**
** Filtre une collection d'�l�ment pour obtenir un sous ensemble de
** cette liste.
**
** @param elements Collections d'�l�ments, ce param�tre ne doit pas �tre
**        null, mais la collection peut �ventuellement �tre vide.
**
** @return un Collections d'�l�ments, sous ensemble de la collection don�e,
**         ne retourne jamais null, mais le r�sultat peut �ventullement
**         �tre une collection vide.
*/
@Override
public Collection<T> apply( final Collection<T> elements ) // -------------
{
 final LinkedList<T>  list = new LinkedList<T>();
 final Iterator<T>    iter = elements.iterator();

 while( iter.hasNext() ) {
    final T o = iter.next();

    if( selector.isSelected( o ) ) {
        list.add( o );
        }
    }

 return list;
}

/*
public Collection<T> apply2( final Collection<T> elements ) // -------------
{
 final LinkedList<T>  list = new LinkedList<T>();
 final Iterator<T>    iter = new IteratorFilter<T>( elements.iterator(), selector );

 while( iter.hasNext() ) {
    list.add( iter.next() );
    }

 return list;
}
*/
} // interface
