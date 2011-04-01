/**
 * 
 */
package cx.ath.choisnet.i18n.logging;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.MissingResourceException;
import cx.ath.choisnet.i18n.AutoI18n.Key;
import cx.ath.choisnet.i18n.AutoI18nExceptionHandler;

/**
 * {@link AutoI18nExceptionHandler} using {@link System#err} 
 * to trace Localization exceptions.
 * <p>
 * This object is not Serializable, you must use
 * this debugging your application only!
 * </p>
 * 
 * @author Claude CHOISNET
 */
public class AutoI18nPrintStreamLoggingExceptionHandler 
    extends AbstractAutoI18nLoggingExceptionHandler
{
    private static final long serialVersionUID = 1L;
    private transient PrintStream out; // TODO: NOT SERIALIZABLE !!
    
    /**
     * Create object using {@link System#err}
     */
    public AutoI18nPrintStreamLoggingExceptionHandler()
    {
        this(System.err);
    }

    /**
     * Create object using giving PrintStream
     * 
     * @param output output to use for logging 
     */
    public AutoI18nPrintStreamLoggingExceptionHandler(
            PrintStream output
            )
    {
        this.out = output;
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
        e.printStackTrace( out );
    }

    @Override
    public void handleMissingResourceException( MissingResourceException e,
            Field field, String key )
    {
        out.printf( 
                "* MissingResourceException for: %s - %s\n",
                key,
                e.getLocalizedMessage()
                );
        defaultHandle( e );
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

    @Override
    public void handleMissingResourceException( 
            MissingResourceException    e,
            Field                       field, 
            String                      key, 
            Method[]                    methods
            )
    {
        out.printf( 
                "* MissingResourceException for: %s using [%s] - %s\n",
                key,
                methods[0],
                e.getLocalizedMessage()
                );
        defaultHandle( e );
    }
}