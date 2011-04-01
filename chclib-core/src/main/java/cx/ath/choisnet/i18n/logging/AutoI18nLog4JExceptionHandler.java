/**
 *
 */
package cx.ath.choisnet.i18n.logging;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.MissingResourceException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import cx.ath.choisnet.i18n.AutoI18n.Key;
import cx.ath.choisnet.i18n.AutoI18nExceptionHandler;

/**
 * {@link AutoI18nExceptionHandler} using Log4J to trace
 * Localization exceptions.
 *
 * @author Claude CHOISNET
 */
public class AutoI18nLog4JExceptionHandler
    extends AbstractAutoI18nLoggingExceptionHandler
{
    private static final long serialVersionUID = 1L;
    private transient static final Logger slogger = Logger.getLogger(AutoI18nLog4JExceptionHandler.class);
    /** @serial */
    private Level level;

    /**
     * Create object using Logger based on current class
     * with a level define has {@link Level#WARN}
     */
    public AutoI18nLog4JExceptionHandler()
    {
        this(Level.WARN);
    }

    /**
     * Create object using giving {@link Logger}
     *
     * @param level Level to use for logging
     */
    public AutoI18nLog4JExceptionHandler(
            Level level
            )
    {
        this.level = level;
    }

    /**
     * All exceptions use this method to log message,
     * use {@link Logger#log(Priority, Object, Throwable)}
     * to print message
     *
     * @param e Exception to log.
     */
    @Override
    public void defaultHandle(Exception e )
    {
        slogger.log( level, "AutoI18n error", e );
    }

    @Override
    public void handleMissingResourceException( 
            MissingResourceException    e,
            Field                       field, 
            String                      key
            )
    {
        slogger.warn( 
          "* MissingResourceException for:" 
              + key 
              + " - " 
              + e.getLocalizedMessage(),
          e
          );
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
        slogger.warn( 
            String.format( 
                "* MissingResourceException for: %s using [%s] - %s\n",
                key,
                methods[0],
                e.getLocalizedMessage()
                ),
            e
            );
    }
}