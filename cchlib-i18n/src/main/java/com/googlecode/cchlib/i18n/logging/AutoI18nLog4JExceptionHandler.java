package com.googlecode.cchlib.i18n.logging;

import java.lang.reflect.Field;
import java.util.MissingResourceException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import com.googlecode.cchlib.i18n.missing.MissingForToolTipText;
import com.googlecode.cchlib.i18n.missing.MissingInfo;
import com.googlecode.cchlib.i18n.missing.MissingMethodsResolution;
import com.googlecode.cchlib.i18n.missing.MissingSimpleKey;
import com.googlecode.cchlib.i18n.missing.MissingLateKey;

/**
 * {@link com.googlecode.cchlib.i18n.AutoI18nExceptionHandler} using Log4J to trace
 * Localization exceptions.
 */
public class AutoI18nLog4JExceptionHandler
    extends AbstractAutoI18nLoggingExceptionHandler
{
    private static final long serialVersionUID = 1L;
    private transient static final Logger logger = Logger.getLogger(AutoI18nLog4JExceptionHandler.class);
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
        final MissingResourceException e,
        final Field                    field, 
        final MissingInfo              missingInfo 
        )
    {
        switch( missingInfo.getType() ) {
            case SIMPLE_KEY :
                _handleMissingResourceException( e, field, MissingSimpleKey.class.cast( missingInfo ) );
                break;
            case LATE_KEY :
                _handleMissingResourceException( e, field, MissingLateKey.class.cast( missingInfo ) );
                break;
            case METHODS_RESOLUTION :
                _handleMissingResourceException_MissingMethodsResolution( e, field, MissingMethodsResolution.class.cast( missingInfo ) );
                break;
            case JCOMPONENT_TOOLTIPTEXT:
                _handleMissingResourceException( e, field, MissingForToolTipText.class.cast( missingInfo ) );
                break;
            }
    }

    private void _handleMissingResourceException_MissingMethodsResolution(
            MissingResourceException    e,
            Field                       field,
            MissingMethodsResolution    missingMethodsResolution
            )
    {
        final String msg = String.format(
                    "* MissingResourceException for: %s using [%s] - %s\n",
                    missingMethodsResolution.getKey(),
                    //methods[0],
                    missingMethodsResolution.getGetter(),
                    e.getLocalizedMessage()
                    );

        if( logger.isDebugEnabled() ) {
            logger.error( msg, e );
            }
        else {
            logger.warn( msg );
            }
    }

    private void _handleMissingResourceException(
        final MissingResourceException    e,
        final Field                       field,
        final MissingInfo                 missingInfo
        )
    {
        final String msg = "* MissingResourceException for:"
                    + missingInfo.getKey()
                    + " - "
                    + e.getLocalizedMessage();

        if( logger.isDebugEnabled() ) {
            logger.error( msg, e );
            }
        else {
            logger.warn( msg );
            }
    }
}
