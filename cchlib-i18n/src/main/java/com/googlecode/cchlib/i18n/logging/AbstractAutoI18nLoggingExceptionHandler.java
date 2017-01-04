package com.googlecode.cchlib.i18n.logging;

import javax.annotation.Nonnull;
import com.googlecode.cchlib.i18n.AutoI18nExceptionHandler;
import com.googlecode.cchlib.i18n.core.AutoI18nConfigSet;
import com.googlecode.cchlib.i18n.core.internal.AbstractAutoI18nExceptionHandler;

/**
 * {@link AbstractAutoI18nLoggingExceptionHandler} allow help
 * implementing an {@link AutoI18nExceptionHandler}
 */
@SuppressWarnings("ucd") // API
public abstract class AbstractAutoI18nLoggingExceptionHandler
    extends AbstractAutoI18nExceptionHandler
{
    private static final long serialVersionUID = 1L;

    private final AutoI18nConfigSet config;

    protected AbstractAutoI18nLoggingExceptionHandler(
        @Nonnull final AutoI18nConfigSet safeConfig
        )
    {
        if( safeConfig == null ) {
            throw new IllegalArgumentException();
        }

        this.config = safeConfig;
    }

    @Nonnull
    protected AutoI18nConfigSet getConfig()
    {
        return this.config;
    }
}
