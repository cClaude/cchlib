package org.homedns.chez.jtools.lib;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @since 1.01
 */
public interface ContextInitializable<T>
{
    /**
     * Nom du conteneur de servlet - contexte <br>
     * ex: "/mywebapp"
     *
     * @return Servlet context name
     */
    public String getServletContextName();

    /**
     * Nom de l'attribute sur le contexte
     *
     * @return Attribut name
     */
    public String getAttributeName();

    public T newInstance( final HttpServletRequest request )
        throws ContextInitializationException;
}
