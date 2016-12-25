package org.homedns.chez.jtools.webapptools;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.homedns.chez.jtools.lib.ContextInitializable;
import org.homedns.chez.jtools.lib.ContextLoader;

/**
 *
 * @since 1.01
 */
public class WEBAppToolsInitializator implements ContextInitializable<WEBAppToolsContext>
{
    /**
     * Nom du contexte pour l'application WEB
     *
     * @see #getServletContextName()
     * @see #newInstance(HttpServletRequest)
     */
    public static final String SERVLET_CONTEXT_NAME = "/Tools";

    /**
     * Nom de l'objet {@link WEBAppToolsContext} dans les attributs du context de servlet
     *
     * @see #getAttributeName()
     * @see #newInstance(HttpServletRequest)
     */
    public static final String ATTRIBUTE_NAME
        = WEBAppToolsContext.class.getName();

    public static final String SEPARATOR_REGEXP
        = "[\t\n\r ,;]+";

    public static final String PROXY_VIEW_URLS_PARAM_NAME = "proxyViewURLs";
    public static final String HOST_LIST_PARAM_NAME       = "hostList";

    protected WEBAppToolsInitializator()
    {
        // empty, just for somes init use !
    }

    @Override
    public String getServletContextName()
    {
        return SERVLET_CONTEXT_NAME;
    }

    @Override
    public String getAttributeName()
    {
        return ATTRIBUTE_NAME;
    }

    public static String getServletContextInitParameter(
        final ServletContext servletContext,
        final String         ctxtName
        ) throws WEBAppToolsInitializationException
    {
        try {
            final String value = servletContext.getInitParameter( ctxtName ).trim();

            if( value.length() > 0 ) {
                return value;
            }

            throw new WEBAppToolsInitializationException( "Value \"" + ctxtName + "\" is empty" );
        }
        catch( final Exception e ) {
            throw new WEBAppToolsInitializationException( "Value \"" + ctxtName + "\" not found in context (web.xml:" + servletContext + ")", e );
        }
    }

    @Override
    public WEBAppToolsContext newInstance( final HttpServletRequest request )
        throws WEBAppToolsInitializationException
    {
        final ServletContext sc = ContextLoader.getServletContext( request, this );
        final WEBAppToolsContext ctxt = new WEBAppToolsContext();

        String   value  = getServletContextInitParameter( sc, PROXY_VIEW_URLS_PARAM_NAME );
        String[] values = value.split( SEPARATOR_REGEXP );

        for( final String v : values ) {
            ctxt.addProxyViewURL( v );
        }

        value  = getServletContextInitParameter( sc, HOST_LIST_PARAM_NAME );
        values = value.split( SEPARATOR_REGEXP );

        for( final String v : values ) {
            ctxt.addHostName( v );
        }

        return ctxt;
    }
}
