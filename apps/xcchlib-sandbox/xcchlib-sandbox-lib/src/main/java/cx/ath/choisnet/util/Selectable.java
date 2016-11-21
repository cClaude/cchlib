/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/Selectable.java
** Description   :
**
**  1.30.010 2005.05.15 Claude CHOISNET - Version initiale
**                          cx.ath.choisnet.util.IteratorFilter
**  2.01.015 2005.10.14 Claude CHOISNET - Nouveau nom
**                          cx.ath.choisnet.util.Selectable
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.Selectable
**
*/
package cx.ath.choisnet.util;

/**
**
** @author Claude CHOISNET
** @since   2.01.015
** @version 2.01.015
**
** @see cx.ath.choisnet.util.IteratorFilter
*/
public interface Selectable<T>
{

/**
**
*/
public boolean isSelected( T o ); // ----------------------------------------

} // interface

