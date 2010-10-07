/**
 * 
 */
package cx.ath.choisnet.i18n;

/**
 * {@link AutoI18nExceptionHandler} using {@link System#err} 
 * to trace Localization exceptions.
 * 
 * @author Claude CHOISNET
 */
public class AutoI18nSystemErrExceptionHandler 
    extends AbstractAutoI18nLoggingExceptionHandler
{
    private static final long serialVersionUID = 1L;

    /**
     * Create object using {@link System#err}
     */
    public AutoI18nSystemErrExceptionHandler()
    {
    }

    /**
     * All exceptions use this method to log message,
     * use {@link Exception#printStackTrace(java.io.PrintWriter)}
     * to print message
     * 
     * @param e Exception to log.
     */
    public void defaultHandle(Exception e )
    {
        e.printStackTrace( System.err );
    }
}