/**
 *
 */
package cx.ath.choisnet.i18n;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * {@link AutoI18nExceptionHandler} using Log4J to trace
 * Localization exceptions.
 *
 * @author Claude CHOISNET
 */
public class AutoI18nLog4JExceptionHandler
    extends AbstractAutoI18nLoggingExceptionHandler
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AutoI18nLog4JExceptionHandler.class);
    /** @serial */
    private Level level;

    /**
     * Create object using Logger based on current class
     * with a level define has {@link Level#WARN}
     */
    public AutoI18nLog4JExceptionHandler()
    {
        this(Level.WARN);
    }

    /**
     * Create object using giving {@link Logger}
     *
     * @param level Level to use for logging
     */
    public AutoI18nLog4JExceptionHandler(
            Level level
            )
    {
        this.level = level;
    }

    /**
     * All exceptions use this method to log message,
     * use {@link Logger#log(Priority, Object, Throwable)}
     * to print message
     *
     * @param e Exception to log.
     */
    public void defaultHandle(Exception e )
    {
        logger.log( level, "AutoI18n error", e );
    }
}