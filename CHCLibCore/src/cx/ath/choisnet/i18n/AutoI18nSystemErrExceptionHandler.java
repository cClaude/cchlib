/**
 * 
 */
package cx.ath.choisnet.i18n;

import java.lang.reflect.Field;
import java.util.MissingResourceException;
import cx.ath.choisnet.i18n.AutoI18n.Key;

/**
 * {@link AutoI18nExceptionHandler} using {@link System#err} 
 * to trace Localization exceptions.
 * 
 * @author Claude CHOISNET
 */
public class AutoI18nSystemErrExceptionHandler 
    extends AbstractAutoI18nLoggingExceptionHandler
{
    private static final long serialVersionUID = 1L;

    /**
     * Create object using {@link System#err}
     */
    public AutoI18nSystemErrExceptionHandler()
    {
    }

    /**
     * All exceptions use this method to log message,
     * use {@link Exception#printStackTrace(java.io.PrintWriter)}
     * to print message
     * 
     * @param e Exception to log.
     */
    @Override
    public void defaultHandle(Exception e )
    {
        e.printStackTrace( System.err );
    }

    @Override
    public void handleMissingResourceException( MissingResourceException e,
            Field field, String key )
    {
        System.err.printf( 
                "* MissingResourceException for: %s - %s\n",
                key,
                e.getLocalizedMessage()
                );
        e.printStackTrace( System.err );
    }

    @Override
    public void handleMissingResourceException( 
            MissingResourceException    e,
            Field                       field, 
            Key                         key 
            )
    {
        handleMissingResourceException(e,field,key.getKey());
    }
}