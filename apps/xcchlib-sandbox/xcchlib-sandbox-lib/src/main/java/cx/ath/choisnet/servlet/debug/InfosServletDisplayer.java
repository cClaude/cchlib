/*
** $VER: InfosServletDisplayer.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/debug/InfosServletDisplayer.java
** Description   :
** Encodage      : ANSI
**
**  2.01.032 2005.11.21 Claude CHOISNET - Version initiale
**                      inspirée de :
**                          cx.ath.choisnet.servlet.debug.DisplayServletInfos
**                      et basé sur :
**                          cx.ath.choisnet.lang.reflect.MappableHelper
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.debug.InfosServletDisplayer
**
*/
package cx.ath.choisnet.servlet.debug;

/**
**
** This class shows how a servlet is initialized,
** how it can retrieve info from the request and servlet context
** and how it is destroyed.
**
**
** @author Claude CHOISNET
** @since   2.01.032
** @version 2.01.032
*/
public interface InfosServletDisplayer
{

/**
**
*/
public void appendHTML( Appendable out ); // ------------------------------

} // class

