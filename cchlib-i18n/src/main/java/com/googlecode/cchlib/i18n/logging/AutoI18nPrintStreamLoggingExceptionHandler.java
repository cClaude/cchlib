package com.googlecode.cchlib.i18n.logging;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.MissingResourceException;
import com.googlecode.cchlib.i18n.missing.MissingForToolTipText;
import com.googlecode.cchlib.i18n.missing.MissingInfo;
import com.googlecode.cchlib.i18n.missing.MissingLateKey;
import com.googlecode.cchlib.i18n.missing.MissingMethodsResolution;
import com.googlecode.cchlib.i18n.missing.MissingSimpleKey;

/**
 * {@link com.googlecode.cchlib.i18n.AutoI18nExceptionHandler}
 * using {@link System#err} or any custom {@link PrintStream}
 * to trace Localization exceptions.
 * <p>
 * This object is not Serializable, you must use
 * this debugging your application only!
 * </p>
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
        out.printf(
                "* MissingResourceException for: %s using [%s] - %s\n",
                missingMethodsResolution.getKey(),
                missingMethodsResolution.getGetter(),
                e.getLocalizedMessage()
                );
        defaultHandle( e );
    }

    private void _handleMissingResourceException(
            final MissingResourceException    e,
            final Field                       field,
            final MissingInfo                 missingInfo
            )
    {
        out.printf(
                "* MissingResourceException for: %s - %s\n",
                missingInfo.getKey(),
                e.getLocalizedMessage()
                );
        defaultHandle( e );
    }
}
