/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/HTMLObjectInterface.java
** Description   :
**
** -----------------------------------------------------------------------
**  3.02.030 2006.07.21 Claude CHOISNET - Version initial
**                      Nouveau nom: cx.ath.choisnet.html.HTMLObjectInterface
**  3.02.037 2006.08.07 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.util.HTMLObjectInterface
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.HTMLObjectInterface
**
*/
package cx.ath.choisnet.html.util;

/**
** <p>
**
** </p>
**
** @author Claude CHOISNET
** @since   3.02.030
** @version 3.02.037
*/
public interface HTMLObjectInterface<T extends HTMLObjectInterface>
{
/**
**
*/
public T setCSSClass( final String cssClass ); // -------------------------

/**
**
*/
public T setId( final String id ); // -------------------------------------

} // class

