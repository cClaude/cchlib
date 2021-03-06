package org.homedns.chez.jtools.lib;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @since 1.01.001
 */
public class ContextLoader
{
    private ContextLoader()
    {
        // All methods statics
    }

    /**
     * Recherche de objet T é partir du request courant sur le contexte, si
     * l'objet n'a pas été trouvé on le crée.
     *
     * @param <T>
     *            Expected type
     * @param request
     *            Objet {@link javax.servlet.http.HttpServletRequest} valide
     *            de la requéte en cours.
     * @param aContextClass
     *            Class de l'object attendu.
     * @param aContextInitializator
     *            Initialisateur de l'objet.
     * @return Context of type {@code T}
     * @throws ContextInitializationException
     *             if any
     *
     * @see ContextInitializable
     */
    public static <T> T getContext(
        final HttpServletRequest      request,
        final Class<T>                aContextClass,
        final ContextInitializable<T> aContextInitializator
        ) throws ContextInitializationException
    {
        final String         attributeName  = aContextInitializator.getAttributeName();
        final ServletContext servletContext = getServletContext( request, aContextInitializator );

        try {
            final T context = aContextClass.cast( servletContext.getAttribute( attributeName ) );

            if( context != null ) {
                return context;
            }
        }
        catch( final ClassCastException ignore ) {
            // ignore
        }

        final T context = aContextInitializator.newInstance( request );

        servletContext.setAttribute( aContextInitializator.getAttributeName(), context );

        return context;
    }

    /**
     * Recherche du context de l'application
     * {@link ContextInitializable#getServletContextName()} é partir sur la liste
     * des contextes disponibles trouvé é l'aide du request donnée.
     * <p>
     * Si le contexte n'est pas trouvé, c'est le contexte lié au request qui
     * sera utilisé.
     *
     * @param request
     *            Objet {@link javax.servlet.http.HttpServletRequest} valide de la
     *            requéte en cours.
     * @param aContextInitializator
     *            Initialisateur de l'objet.
     * @return the ServletContext
     *
     * @see ServletContext
     * @see ServletContext#getContext(String)
     * @see ContextInitializable#getServletContextName()
     */
    public static ServletContext getServletContext(
        final HttpServletRequest      request,
        final ContextInitializable<?> aContextInitializator
        )
    {
        final ServletContext thisServletContext = request.getSession().getServletContext().getContext( aContextInitializator.getServletContextName() );

        if( thisServletContext != null ) {
            return thisServletContext;
        }

        //
        // On n'a pas récupérer le contexte é partir de son nom, on prend celui
        // directement lié au request
        //
        return request.getSession().getServletContext();
    }
}
