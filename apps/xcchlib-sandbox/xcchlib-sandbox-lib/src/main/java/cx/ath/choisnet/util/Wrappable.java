/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/Wrappable.java
** Description   :
**
**  2.01.015 2005.10.14 Claude CHOISNET - Version initiale
**  3.01.032 2006.04.25 Claude CHOISNET
**                      Documentation.
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.Wrappable
**
*/
package cx.ath.choisnet.util;

/**
** Interface ayant pour objectif d'avoir une "autre" vue d'un objet ou
** d'une collection d'objets.
**
** @author Claude CHOISNET
** @since   2.01.015
** @version 2.01.015
**
** @see cx.ath.choisnet.util.CollectionFilter
** @see cx.ath.choisnet.util.IteratorWrapper
** @see cx.ath.choisnet.util.WrapperHelper
*/
public interface Wrappable<T,U>
{

/**
** Methode permettant d'optenir une nouvelle vue d'objet.
**
** @param o Objet sous sa forme d'origine.
**
** @return un objet sous la forme attendue.
*/
public U wrappe( T o ); // ------------------------------------------------

} // interface

