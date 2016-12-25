package org.homedns.chez.jtools.webapptools;

import org.homedns.chez.jtools.lib.ContextInitializationException;

/**
 *
 * @since 1.01
 */
public class WEBAppToolsInitializationException extends ContextInitializationException
{
    private static final long serialVersionUID = 1L;

    public WEBAppToolsInitializationException(
        final String message,
        final Throwable cause
        )
    {
        super( message, cause );
    }

    public WEBAppToolsInitializationException(
        final String message
        )
    {
        super( message );
    }
}
