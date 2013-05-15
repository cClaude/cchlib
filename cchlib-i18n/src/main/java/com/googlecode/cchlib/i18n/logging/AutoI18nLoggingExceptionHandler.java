package com.googlecode.cchlib.i18n.logging;

import java.lang.reflect.Field;
import java.util.MissingResourceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.googlecode.cchlib.i18n.missing.MissingForToolTipText;
import com.googlecode.cchlib.i18n.missing.MissingInfo;
import com.googlecode.cchlib.i18n.missing.MissingLateKey;
import com.googlecode.cchlib.i18n.missing.MissingMethodsResolution;
import com.googlecode.cchlib.i18n.missing.MissingSimpleKey;

/**
 * {@link com.googlecode.cchlib.i18n.AutoI18nExceptionHandler} using standard
 * java logging to trace Localization exceptions.
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
        logger.log(
                level,
                String.format(
                    "* MissingResourceException for: %s using [%s] - %s\n",
                    missingMethodsResolution.getKey(),
                    missingMethodsResolution.getGetter(),
                    e.getLocalizedMessage()
                    ),
                e
                );
    }

    private void _handleMissingResourceException(
            final MissingResourceException    e,
            final Field                       field,
            final MissingInfo                 missingInfo
            )
    {
        logger.log(
                level,
                "* MissingResourceException for:"
                    + missingInfo.getKey()
                    + " - "
                    + e.getLocalizedMessage(),
                e
                );
    }
}
