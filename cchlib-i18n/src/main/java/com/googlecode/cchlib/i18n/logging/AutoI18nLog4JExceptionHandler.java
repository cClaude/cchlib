package com.googlecode.cchlib.i18n.logging;

import java.util.Set;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18nConfig;

/**
 * {@link com.googlecode.cchlib.i18n.AutoI18nExceptionHandler} using Log4J to trace
 * Localization exceptions.
 */
public class AutoI18nLog4JExceptionHandler
    extends AbstractAutoI18nLoggingExceptionHandler
{
    private static final long serialVersionUID = 1L;
    private final transient Logger LOGGER = Logger.getLogger(AutoI18nLog4JExceptionHandler.class);

    /** @serial */
    private Level level;

    /**
     * Create object using Logger based on current class
     * with a level define has {@link Level#WARN}
     * @param config Configuration
     */
    public AutoI18nLog4JExceptionHandler( Set<AutoI18nConfig> config )
    {
        this( Level.WARN, config );
    }

    /**
     * Create object using giving {@link Logger}
     *
     * @param level  Level to use for logging
     * @param config Configuration
     */
    public AutoI18nLog4JExceptionHandler(
            Level               level,
            Set<AutoI18nConfig> config
            )
    {
        super( config );

        this.level  = level;
    }

    @Override
    protected void doHandle( String msg, Throwable e )
    {
        if( getConfig().contains( AutoI18nConfig.PRINT_STACKTRACE_IN_LOGS ) ) {
            LOGGER.log( level, msg, e );
            }
        else {
            LOGGER.log( level, msg + " : " + e.getMessage() );
            }
    }
}
