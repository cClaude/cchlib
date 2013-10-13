/*
** -----------------------------------------------------------------------
** Nom           : org/homedns/chez/jtools/webapptools/WEBAppToolsContext.java
** Description   :
** Encodage      : ANSI
**
**  1.01.001 2007.01.01 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** org.homedns.chez.jtools.webapptools.WEBAppToolsContext
**
*/
package org.homedns.chez.jtools.webapptools;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import org.homedns.chez.jtools.lib.ContextInitializable;
import org.homedns.chez.jtools.lib.ContextInitializationException;
import org.homedns.chez.jtools.lib.ContextLoader;

/**
**
** @author Claude CHOISNET
** @since   1.01.001
** @version 1.01.001
*/
public class WEBAppToolsContext
    implements java.io.Serializable
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
private final static WEBAppToolsInitializator contextInitializator
               = new WEBAppToolsInitializator();

/**
**
*/
private ArrayList<String> proxyViewURLs = new ArrayList<String>();

/**
**
*/
private ArrayList<String> hostNameList = new ArrayList<String>();

/**
**
*/
protected WEBAppToolsContext() // -----------------------------------------
{
 // empty
}

/**
** Recherche de objet {@link WEBAppToolsContext} à partir du request
** courant, si l'objet n'a pas été trouvé on le crée.
**
** @param request   Objet {@link javax.servlet.http.HttpServletRequest} valide
**                  de la requête en cours.
**
*/
public static WEBAppToolsContext getContext( // ---------------------------
    final HttpServletRequest request
    )
    throws ContextInitializationException
{
 return ContextLoader.getContext( request, WEBAppToolsContext.class, contextInitializator );
}

/**
**
*/
protected WEBAppToolsContext addProxyViewURL( String url ) // -------------
{
 this.proxyViewURLs.add( url );

 return this;
}

/**
**
*/
public List<String> getProxyViewURLs() // ---------------------------------
{
 return this.proxyViewURLs;
}

/**
**
*/
protected WEBAppToolsContext addHostName( String url ) // -----------------
{
 this.hostNameList.add( url );

 return this;
}

/**
**
*/
public List<String> getHostNames() // -------------------------------------
{
 return this.hostNameList;
}

} // class
