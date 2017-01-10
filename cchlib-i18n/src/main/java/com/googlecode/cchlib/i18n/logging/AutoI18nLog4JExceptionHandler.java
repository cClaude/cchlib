package com.googlecode.cchlib.i18n.logging;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.core.AutoI18nConfigSet;

/**
 * {@link com.googlecode.cchlib.i18n.AutoI18nExceptionHandler} using Log4J to trace
 * Localization exceptions.
 */
public class AutoI18nLog4JExceptionHandler
    extends AbstractAutoI18nLoggingExceptionHandler
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( AutoI18nLog4JExceptionHandler.class );

    private static final Level DEFAULT_LEVEL = Level.WARN;

    /** @serial */
    private final Level level;

    /**
     * Create object using Logger based on current class
     * with a level define has {@link Level#WARN}
     *
     * @param safeConfig Configuration
     */
    public AutoI18nLog4JExceptionHandler( @Nonnull final AutoI18nConfigSet safeConfig )
    {
        this( DEFAULT_LEVEL, safeConfig );
    }

    /**
     * Create object using giving {@link Logger}
     *
     * @param level  Level to use for logging
     * @param safeConfig Configuration
     */
    public AutoI18nLog4JExceptionHandler(
        @Nullable final Level            level,
        @Nonnull final AutoI18nConfigSet safeConfig
        )
    {
        super( safeConfig );

        this.level = level == null ? DEFAULT_LEVEL : level;
    }

    @Override
    protected void doHandle( final String msg, final Throwable cause )
    {
        if( getConfig().isPrintStacktraceInLogs() ) {
            LOGGER.log( this.level, msg, cause );
            }
        else {
            if( cause != null ) {
                LOGGER.log( this.level, msg + " : " + cause.getMessage() );
            } else {
                LOGGER.log( this.level, msg );
            }
            }
    }
}
