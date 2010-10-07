/**
 * 
 */
package cx.ath.choisnet.i18n;

import java.lang.reflect.InvocationTargetException;

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
     * All exceptions use this method to log message,
     * 
     * @param e Exception to log.
     */
    public abstract void defaultHandle(Exception e );

    @Override
    final
    public void handleInvocationTargetException( InvocationTargetException e )
    {
        defaultHandle(e);
    }

    @Override
    final
    public void handleIllegalAccessException( IllegalAccessException e )
    {
        defaultHandle(e);
    }

    @Override
    final
    public void handleIllegalArgumentException( IllegalArgumentException e )
    {
        defaultHandle(e);
    }

    @Override
    final
    public void handleNoSuchMethodException( NoSuchMethodException e )
    {
        defaultHandle(e);
    }

    @Override
    final
    public void handleSecurityException( SecurityException e )
    {
        defaultHandle(e);
    }
}