/**
 * 
 */
package cx.ath.choisnet.i18n.logging;

import java.lang.reflect.Field;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import cx.ath.choisnet.i18n.AutoI18nEventHandler;

/**
 * {@link AutoI18nEventHandler} using PrintStream
 * to trace Localization events.
 * <p>
 * This object is not Serializable, you must use
 * this debugging your application only!
 * </p>
 *
 * @author Claude CHOISNET
 */
public class AutoI18nLog4JEventHandler 
    implements AutoI18nEventHandler
{
    private static final long serialVersionUID = 1L;
    private transient static final Logger slogger = Logger.getLogger( AutoI18nLog4JEventHandler.class );
    /** @serial */
    private Level level;

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
        this.level = level;
    }

    @Override
    public void ignoredField( Field f, Cause cause )
    {
        slogger.log( 
                level,
                String.format(
                    "Ignore field: %s (%s) [%s] - %s\n", 
                    f.getName(),
                    f.getType(),
                    cause,
                    f
                    )
                );
    }

    @Override//
    public void localizedField( Field f )
    {
        slogger.log( 
            level,
                String.format(
                    "Localized field: %s (%s) - %s\n",
                    f.getName(),
                    f.getType(),
                    f
                )
            );
    }
}