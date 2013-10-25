/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/CollectionFilter.java
** Description   :
**
**  3.01.032 2006.04.25 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.CollectionFilter
**
*/
package cx.ath.choisnet.util;

import java.util.Collection;

/**
** Objectif de l'interface traiter des algorithmes génériques de filtrage.
**
** @author Claude CHOISNET
** @since   3.01.032
** @version 3.01.032
**
** @see cx.ath.choisnet.util.duplicate.DuplicateLayer
** @see cx.ath.choisnet.util.impl.CollectionFilterImpl
** @see cx.ath.choisnet.util.IteratorFilter
** @see cx.ath.choisnet.util.Selectable
*/
public interface CollectionFilter<T>
{

/**
** Filtre une collection d'élément pour obtenir un sous ensemble de
** cette liste.
**
** @param elements Collections d'éléments, ce paramètre ne doit pas être
**        null, mais la collection peut éventuellement être vide.
**
** @return un Collections d'éléments, sous ensemble de la collection donée,
**         ne retourne jamais null, mais le résultat peut éventullement
**         être une collection vide.
*/
public Collection<T> apply( Collection<T> elements ); // ------------------

} // interface
