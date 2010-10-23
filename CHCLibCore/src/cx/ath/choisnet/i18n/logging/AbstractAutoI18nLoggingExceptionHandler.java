/**
 * 
 */
package cx.ath.choisnet.i18n.logging;

import java.lang.reflect.InvocationTargetException;
import cx.ath.choisnet.i18n.AutoI18nExceptionHandler;

/**
 * {@link AutoI18nExceptionHandler} using logging
 * to trace Localization exceptions.
 * 
 * @author Claude CHOISNET
 */
public abstract class AbstractAutoI18nLoggingExceptionHandler 
    implements AutoI18nExceptionHandler
{
    private static final long serialVersionUID = 1L;

    /**
     * All exceptions implements methods 
     * use this method to log message,
     * 
     * @param e Exception to log.
     */
    public abstract void defaultHandle(Exception e );

    @Override
    public void handleInvocationTargetException( InvocationTargetException e )
    {
        defaultHandle(e);
    }

    @Override
    public void handleIllegalAccessException( IllegalAccessException e )
    {
        defaultHandle(e);
    }

    @Override
    public void handleIllegalArgumentException( IllegalArgumentException e )
    {
        defaultHandle(e);
    }

    @Override
    public void handleNoSuchMethodException( NoSuchMethodException e )
    {
        defaultHandle(e);
    }

    @Override
    public void handleSecurityException( SecurityException e )
    {
        defaultHandle(e);
    }
}