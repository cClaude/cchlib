package com.googlecode.cchlib.i18n.logging;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.MissingResourceException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import com.googlecode.cchlib.i18n.AutoI18n;

/**
 * {@link com.googlecode.cchlib.i18n.AutoI18nExceptionHandler} using Log4J to trace
 * Localization exceptions.
 */
public class AutoI18nLog4JExceptionHandler
    extends AbstractAutoI18nLoggingExceptionHandler
{
    private static final long serialVersionUID = 1L;
    private static final transient Logger logger = Logger.getLogger(AutoI18nLog4JExceptionHandler.class);
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
        logger.log( level, "AutoI18n error", e );
    }

    @Override
    public void handleMissingResourceException(
            MissingResourceException    e,
            Field                       field,
            String                      key
            )
    {
        final String msg = "* MissingResourceException for:"
                    + key
                    + " - "
                    + e.getLocalizedMessage();

        if( logger.isDebugEnabled() ) {
            logger.error( msg, e );
            }
        else {
            logger.warn( msg );
            }
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
        final String msg = String.format(
                    "* MissingResourceException for: %s using [%s] - %s\n",
                    key,
                    methods[0],
                    e.getLocalizedMessage()
                    );

        if( logger.isDebugEnabled() ) {
            logger.error( msg, e );
            }
        else {
            logger.warn( msg );
            }
    }
}
