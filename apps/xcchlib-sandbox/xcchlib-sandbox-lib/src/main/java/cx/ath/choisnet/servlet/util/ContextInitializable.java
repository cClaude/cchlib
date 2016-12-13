/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/util/ContextInitializable.java
** Description   :
** Encodage      : ANSI
**
**  3.02.042 2007.01.08 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.util.ContextInitializable
**
*/
package cx.ath.choisnet.servlet.util;

import javax.servlet.http.HttpServletRequest;

/**
**
** @author Claude CHOISNET
** @since   3.02.042
** @version 3.02.042
*/
public interface ContextInitializable<T>
{

/**
** Nom du conteneur de servlet - context - ou null, dans ce dernier
** cas les traitements utiliserons uniquement le contexte lié au request.
** <br>
** ex: "/mywebapp"
*/
public String getServletContextName(); // ---------------------------------

/**
** Nom de l'attribute sur le contexte
*/
public String getAttributeName(); // --------------------------------------

/**
**
*/
public T newInstance( final HttpServletRequest request ) // ---------------
    throws ContextInitializationException;

}

