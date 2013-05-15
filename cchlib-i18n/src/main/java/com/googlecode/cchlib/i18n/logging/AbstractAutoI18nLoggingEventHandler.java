package com.googlecode.cchlib.i18n.logging;

import java.lang.reflect.Field;
import com.googlecode.cchlib.i18n.AutoI18nEventHandler;

/**
 * {@link AutoI18nEventHandler}
 * to trace Localization events.
 */
public abstract class AbstractAutoI18nLoggingEventHandler
    implements AutoI18nEventHandler
{
    private static final long serialVersionUID = 1L;

    /**
     * Log formatted message from {@link #ignoredField(Field, com.googlecode.cchlib.i18n.AutoI18nEventHandler.Cause)}
     * @param msg message to log
     */
    protected abstract void logIgnoredField(String msg);

    /**
     * Log formatted message from {@link #localizedField(Field)}
     * @param msg message to log
     */
    protected abstract void logLocalizedField(String msg);

    @Override
    final
    public void ignoredField( Field f, Cause cause )
    {
        logIgnoredField(String.format(
            "Ignore field: %s (%s) [%s] - %s",
            f.getName(),
            f.getType(),
            cause,
            LogFieldFormat.toString( f )
            ));
        //new Exception().printStackTrace();
    }

    @Override
    final
    public void localizedField( Field f )
    {
        logLocalizedField(String.format(
            "Localized field: %s (%s) - %s",
            f.getName(),
            f.getType(),
            LogFieldFormat.toString( f )
            ));
    }
}
