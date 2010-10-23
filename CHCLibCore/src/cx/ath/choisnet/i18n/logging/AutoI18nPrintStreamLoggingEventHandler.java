/**
 * 
 */
package cx.ath.choisnet.i18n.logging;

import java.io.PrintStream;
import java.lang.reflect.Field;
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
public class AutoI18nPrintStreamLoggingEventHandler 
    extends AbstractAutoI18nLoggingEventHandler
{
    private static final long serialVersionUID = 1L;
    private transient PrintStream out; // TODO: NOT SERIALIZABLE !!

    /**
     * Create object using {@link System#err}
     */
    public AutoI18nPrintStreamLoggingEventHandler()
    {
        this(System.err);
    }

    /**
     * Create object using giving PrintStream
     * 
     * @param output output to use for logging 
     */
    public AutoI18nPrintStreamLoggingEventHandler(
            PrintStream output
            )
    {
        this.out = output;
    }

    @Override
    public void ignoredField( Field f, Cause cause )
    {
        out.printf( 
            "Ignore field: %s (%s) [%s] - %s\n", 
            f.getName(),
            f.getType(),
            cause,
            f
            );
    }

    @Override//
    public void localizedField( Field f )
    {
        out.printf(
            "Localized field: %s (%s) - %s\n",
            f.getName(),
            f.getType(),
            f
            );
    }

    @Override
    protected void logIgnoredField( String msg )
    {
        out.println( msg );
    }

    @Override
    protected void logLocalizedField( String msg )
    {
        out.println( msg );
    }
}