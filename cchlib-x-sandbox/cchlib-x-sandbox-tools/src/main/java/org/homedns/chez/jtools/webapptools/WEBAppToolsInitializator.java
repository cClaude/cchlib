/*
** -----------------------------------------------------------------------
** Nom           : org/homedns/chez/jtools/webapptools/WEBAppToolsInitializator.java
** Description   :
** Encodage      : ANSI
**
**  1.01.001 2007.01.01 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** org.homedns.chez.jtools.webapptools.WEBAppToolsInitializator
**
*/
package org.homedns.chez.jtools.webapptools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import org.homedns.chez.jtools.lib.ContextInitializable;
import org.homedns.chez.jtools.lib.ContextLoader;

/**
**
** @author Claude CHOISNET
** @since   1.01.001
** @version 1.01.001
*/
public class WEBAppToolsInitializator
    implements ContextInitializable<WEBAppToolsContext>
{

/**
** <p>Nom du contexte pour l'application WEB</p>
**
** @see #getServletContextName()
** @see #newInstance(HttpServletRequest)
*/
public final static String SERVLET_CONTEXT_NAME = "/Tools" ;

/**
** <p>Nom de l'objet {@link WEBAppToolsContext} dans les attributs du context de servlet</p>
**
** @see #getAttributeName()
** @see #newInstance(HttpServletRequest)
*/
public final static String ATTRIBUTE_NAME
        = "org.homedns.chez.jtools.webapptools.WEBAppToolsContext";

/**
**
*/
public final static String SEPARATOR_REGEXP = "[\t\n\r ,;]+";

/**
**
*/
public final static String PROXY_VIEW_URLS_PARAM_NAME = "proxyViewURLs";

/**
**
*/
public final static String HOST_LIST_PARAM_NAME = "hostList";

/**
**
*/
protected WEBAppToolsInitializator() // -------------------------------------
{
 // empty, just for somes init use !
}

/**
**
*/
public String getServletContextName() // ----------------------------------
{
 return SERVLET_CONTEXT_NAME;
}

/**
**
*/
public String getAttributeName() // ---------------------------------------
{
 return ATTRIBUTE_NAME;
}

/**
**
*/
static
public String getServletContextInitParameter( // --------------------------
    final ServletContext    servletContext,
    final String            ctxtName
    )
    throws WEBAppToolsInitializationException
{
 try {
    String value = servletContext.getInitParameter( ctxtName ).trim();

    if( value.length() > 0 ) {
//        getLogger().info( "Ctxt \"" + ctxtName + "\" = \"" + value + "\"" );

        return value;
        }

    throw new WEBAppToolsInitializationException( "Value \"" + ctxtName + "\" is empty" );
    }
 catch( Exception e ) {
//    getLogger().debug( "getServletContextInitParameter(): servletContext = " + servletContext, e );

    throw new WEBAppToolsInitializationException(
        "Value \"" + ctxtName + "\" not found in context (web.xml:" + servletContext + ")",
        e
        );
    }
}


/**
**
*/
public WEBAppToolsContext newInstance( HttpServletRequest request ) // ----
    throws WEBAppToolsInitializationException
{
 final ServletContext       sc      = ContextLoader.getServletContext( request, this );
 final WEBAppToolsContext   ctxt    = new WEBAppToolsContext();

 String     value   = getServletContextInitParameter( sc, PROXY_VIEW_URLS_PARAM_NAME );
 String[]   values  = value.split( SEPARATOR_REGEXP );

 for( String v : values ) {
    ctxt.addProxyViewURL( v );
    }

 value   = getServletContextInitParameter( sc, HOST_LIST_PARAM_NAME );
 values  = value.split( SEPARATOR_REGEXP );

 for( String v : values ) {
    ctxt.addHostName( v );
    }

 return ctxt;
}

} // class
