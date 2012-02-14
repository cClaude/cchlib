package com.googlecode.cchlib.i18n.logging;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.MissingResourceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.googlecode.cchlib.i18n.AutoI18n;

/**
 * {@link com.googlecode.cchlib.i18n.AutoI18nExceptionHandler} using standard
 * java logging to trace Localization exceptions.
 *
 * @author Claude CHOISNET
 */
public class AutoI18nLoggingExceptionHandler
    extends AbstractAutoI18nLoggingExceptionHandler
{
    private static final long serialVersionUID = 1L;
    private transient static final Logger logger = Logger.getLogger(AutoI18nLoggingExceptionHandler.class.getName());
    /** @serial */
    private final Level  level;

    /**
     * Create object using Logger based on current class
     * with a level define has {@link Level#WARNING}
     */
    public AutoI18nLoggingExceptionHandler()
    {
        this(Level.WARNING);
    }

    /**
     * Create object using giving {@link Logger}
     *
     * @param level Level to use
     * @see Level
     */
    public AutoI18nLoggingExceptionHandler(
            Level   level
            )
    {
        this.level  = level;
    }

    /**
     * All exceptions use this method to log message,
     * use {@link Logger#log(Level, String, Object)}
     * to print message
     *
     * @param e Exception to log.
     */
    @Override
    public void defaultHandle(Exception e )
    {
        logger.log( level, "AutoI18n error", e );
    }

    @Override
    public void handleMissingResourceException(
            MissingResourceException    e,
            Field                       field,
            String                      key
            )
    {
        logger.log(
                level,
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
            AutoI18n.Key                key
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
        logger.log(
            level,
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
