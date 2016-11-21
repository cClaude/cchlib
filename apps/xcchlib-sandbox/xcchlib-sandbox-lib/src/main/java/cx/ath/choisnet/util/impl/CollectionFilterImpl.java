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
** Objectif de l'interface traiter des algorithmes generiques de filtrage.
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
** Filtre une collection d'element pour obtenir un sous ensemble de
** cette liste.
**
** @param elements Collections d'elements, ce parametre ne doit pas etre
**        null, mais la collection peut eventuellement etre vide.
**
** @return un Collections d'elements, sous ensemble de la collection donee,
**         ne retourne jamais null, mais le resultat peut eventullement
**         etre une collection vide.
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
