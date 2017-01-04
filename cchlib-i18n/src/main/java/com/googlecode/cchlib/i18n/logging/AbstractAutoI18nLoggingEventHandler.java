package com.googlecode.cchlib.i18n.logging;

import java.lang.reflect.Field;
import com.googlecode.cchlib.i18n.AutoI18nEventHandler;
import com.googlecode.cchlib.i18n.EventCause;

/**
 * {@link AutoI18nEventHandler}
 * to trace Localization events.
 */
@SuppressWarnings("ucd") // API
public abstract class AbstractAutoI18nLoggingEventHandler
    implements AutoI18nEventHandler
{
    private static final long serialVersionUID = 1L;

    /**
     * Log formatted message from {@link #ignoredField(Field, String, EventCause, String)}
     *
     * @param msg
     *            message to log
     */
    protected abstract void logIgnoredField( String msg );

    /**
     * Log formatted message from {@link #localizedField(Field, String)}
     *
     * @param msg
     *            message to log
     */
    protected abstract void logLocalizedField( String msg );

    @Override
    public final void ignoredField(
        final Field      field,
        final String     key,
        final EventCause eventCause,
        final String     causeDecription
        )
    {
        logIgnoredField(String.format(
            "Ignore field: %s (%s) [%s]%s key=%s - %s",
            field.getName(),
            field.getType(),
            eventCause,
            LogFieldFormat.toString( causeDecription ),
            key,
            LogFieldFormat.toString( field )
            ));
    }

    @Override
    public final void localizedField( final Field field, final String key )
    {
        logLocalizedField(String.format(
            "Localized field: %s (%s) key=%s - %s",
            field.getName(),
            field.getType(),
            key,
            LogFieldFormat.toString( field )
            ));
    }
}
