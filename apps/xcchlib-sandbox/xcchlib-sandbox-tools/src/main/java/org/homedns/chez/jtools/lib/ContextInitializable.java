/*
** -----------------------------------------------------------------------
** Nom           : org/homedns/chez/jtools/lib/ContextInitializable.java
** Description   :
** Encodage      : ANSI
**
**  1.01.001 2007.01.01 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** org.homedns.chez.jtools.lib.ContextInitializable
**
*/
package org.homedns.chez.jtools.lib;

import javax.servlet.http.HttpServletRequest;

/**
**
** @author Claude CHOISNET
** @since   1.01.001
** @version 1.01.001
*/
public interface ContextInitializable<T>
{

/**
** Nom du conteneur de servlet - contexte
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
//public class getContextClass(); // ----------------------------------------

/**
**
*/
public T newInstance( final HttpServletRequest request ) // ---------------
    throws ContextInitializationException;

}

