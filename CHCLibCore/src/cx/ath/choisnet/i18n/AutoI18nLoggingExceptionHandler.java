/**
 * 
 */
package cx.ath.choisnet.i18n;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link AutoI18nExceptionHandler} using standard
 * java logging to trace Localization exceptions.
 * 
 * @author Claude CHOISNET
 */
public class AutoI18nLoggingExceptionHandler 
    extends AbstractAutoI18nLoggingExceptionHandler
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AutoI18nLoggingExceptionHandler.class.getName());
    private final Level  level;

    /**
     * Create object using Logger based on current class
     * with a level define has {@link Level#WARNING}
     */
    public AutoI18nLoggingExceptionHandler()
    {
        this(Level.WARNING);
    }
        
    /**
     * Create object using giving {@link Logger}
     * 
     * @param level Level to use
     * @see Level
     */
    public AutoI18nLoggingExceptionHandler(
            Level   level
            )
    {
        this.level  = level;
    }
    
    /**
     * All exceptions use this method to log message,
     * use {@link Logger#log(Level, String, Object)} 
     * to print message
     * 
     * @param e Exception to log.
     */
    public void defaultHandle(Exception e )
    {
        logger.log( level, "AutoI18n error", e );
    }
}