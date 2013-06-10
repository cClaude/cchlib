package com.googlecode.cchlib.i18n.logging;

import java.lang.reflect.Field;
import com.googlecode.cchlib.i18n.AutoI18nEventHandler;
import com.googlecode.cchlib.i18n.EventCause;

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
    public void ignoredField( Field f, String key, EventCause eventCause, String causeDecription )
    {
        logIgnoredField(String.format(
            "Ignore field: %s (%s) [%s]%s key=%s - %s",
            f.getName(),
            f.getType(),
            eventCause,
            LogFieldFormat.toString( causeDecription ),
            key,
            LogFieldFormat.toString( f )
            ));
        //new Exception().printStackTrace();
    }

    @Override
    final
    public void localizedField( Field f, String key )
    {
        logLocalizedField(String.format(
            "Localized field: %s (%s) key=%s - %s",
            f.getName(),
            f.getType(),
            key,
            LogFieldFormat.toString( f )
            ));
    }
}
