package org.homedns.chez.jtools.webapptools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.homedns.chez.jtools.lib.ContextInitializationException;
import org.homedns.chez.jtools.lib.ContextLoader;

/**
 *
 * @since 1.01
 */
public class WEBAppToolsContext implements Serializable
{
    private static final long serialVersionUID = 1L;

    private static final WEBAppToolsInitializator contextInitializator
        = new WEBAppToolsInitializator();
    private final ArrayList<String>                     proxyViewURLs
        = new ArrayList<>();
    private final ArrayList<String>                     hostNameList
        = new ArrayList<>();

    protected WEBAppToolsContext() // -----------------------------------------
    {
        // empty
    }

    /**
     * Recherche de objet {@link WEBAppToolsContext} é partir du request courant,
     * si l'objet n'a pas été trouvé on le crée.
     *
     * @param request
     *            Objet {@link javax.servlet.http.HttpServletRequest} valide de la
     *            requéte en cours.
     * @return a WEBAppToolsContext
     * @throws ContextInitializationException if any
     */
    public static WEBAppToolsContext getContext(
        final HttpServletRequest request
        ) throws ContextInitializationException
    {
        return ContextLoader.getContext(
                request,
                WEBAppToolsContext.class,
                contextInitializator
                );
    }

    protected WEBAppToolsContext addProxyViewURL( final String url )
    {
        this.proxyViewURLs.add( url );

        return this;
    }

    public List<String> getProxyViewURLs()
    {
        return this.proxyViewURLs;
    }

    protected WEBAppToolsContext addHostName( final String url )
    {
        this.hostNameList.add( url );

        return this;
    }

    public List<String> getHostNames()
    {
        return this.hostNameList;
    }
}
