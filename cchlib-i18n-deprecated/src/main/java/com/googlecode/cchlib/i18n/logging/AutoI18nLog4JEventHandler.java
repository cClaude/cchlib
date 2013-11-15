package com.googlecode.cchlib.i18n.logging;

import java.lang.reflect.Field;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18nEventHandler;

/**
 * {@link AutoI18nEventHandler} using PrintStream
 * to trace Localization events.
 * <p>
 * This object is not Serializable, you must use
 * this debugging your application only!
 * </p>
 */
public class AutoI18nLog4JEventHandler
    implements AutoI18nEventHandler
{
    private static final long serialVersionUID = 1L;
    private static final transient Logger slogger = Logger.getLogger( AutoI18nLog4JEventHandler.class );
    /** @serial */
    private Level levelIgnoredField;
    /** @serial */
    private Level levelLocalizedField;

    /**
     * Create object using Logger based on current class
     * with a level define has {@link Level#WARN}
     */
    public AutoI18nLog4JEventHandler()
    {
        this(Level.WARN);
    }

    /**
     * Create object using giving {@link Logger}
     *
     * @param level Level to use for logging
     */
    public AutoI18nLog4JEventHandler(
            Level level
            )
    {
        this.levelIgnoredField = level;
        this.levelLocalizedField = level;
    }

    /**
     * Create object using giving {@link Logger}
     *
     * @param levelIgnoredField Level to use for logging
     *        {@link #ignoredField(Field, com.googlecode.cchlib.i18n.AutoI18nEventHandler.Cause)} informations
     * @param levelLocalizedField Level to use for logging
     *        {@link #localizedField(Field)} informations
     */
    public AutoI18nLog4JEventHandler(
            Level levelIgnoredField,
            Level levelLocalizedField
            )
    {
        this.levelIgnoredField = levelIgnoredField;
        this.levelLocalizedField = levelLocalizedField;
    }

    @Override
    public void ignoredField( Field f, Cause cause )
    {
        slogger.log(
                levelIgnoredField,
                String.format(
                    "Ignore field: %s (%s) [%s] - %s",
                    f.getName(),
                    f.getType(),
                    cause,
                    f
                    )
                );
    }

    @Override
    public void localizedField( Field f )
    {
        slogger.log(
                levelLocalizedField,
                String.format(
                    "Localized field: %s (%s) - %s",
                    f.getName(),
                    f.getType(),
                    f
                )
            );
    }
}
