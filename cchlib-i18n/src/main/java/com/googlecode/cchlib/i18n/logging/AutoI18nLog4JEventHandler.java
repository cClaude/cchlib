package com.googlecode.cchlib.i18n.logging;

import java.lang.reflect.Field;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18nEventHandler;

/**
 * {@link AutoI18nEventHandler} using PrintStream
 * to trace Localization events.
 */
public class AutoI18nLog4JEventHandler
    extends AbstractAutoI18nLoggingEventHandler
        implements AutoI18nEventHandler
{
    private static final long serialVersionUID = 2L;
    private static final Logger LOGGER = Logger.getLogger( AutoI18nLog4JEventHandler.class );

    private final Level levelIgnoredField;
    private final Level levelLocalizedField;

    /**
     * Create object using Logger based on current class
     * with a level define has {@link Level#WARN}
     */
    public AutoI18nLog4JEventHandler()
    {
        this(Level.TRACE,Level.WARN);
    }

    /**
     * Create object using giving {@link Logger}
     *
     * @param level Level to use for logging
     */
    public AutoI18nLog4JEventHandler(
            final Level level
            )
    {
        this.levelIgnoredField = level;
        this.levelLocalizedField = level;
    }

    /**
     * Create object using giving {@link Logger}
     *
     * @param levelIgnoredField Level to use for logging
     *        {@link #ignoredField(Field, String, com.googlecode.cchlib.i18n.EventCause, String)} informations
     * @param levelLocalizedField Level to use for logging
     *        {@link #localizedField(Field, String)} informations
     */
    public AutoI18nLog4JEventHandler(
            final Level levelIgnoredField,
            final Level levelLocalizedField
            )
    {
        this.levelIgnoredField = levelIgnoredField;
        this.levelLocalizedField = levelLocalizedField;
    }

    @Override
    protected void logIgnoredField( final String msg )
    {
        LOGGER.log( levelIgnoredField, msg );
    }

    @Override
    protected void logLocalizedField( final String msg )
    {
        LOGGER.log( levelLocalizedField, msg );
    }
}
