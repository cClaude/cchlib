/**
 * 
 */
package cx.ath.choisnet.i18n;

import java.lang.reflect.InvocationTargetException;
import org.apache.log4j.Logger;

/**
 * TODO: doc
 * @author Claude CHOISNET
 */
public class AutoI18nDefaultExceptionHandler 
    implements AutoI18nExceptionHandler
{
    private static Logger slogger = Logger.getLogger(AutoI18nDefaultExceptionHandler.class);

    public void defaultHandle(Exception e )
    {
        slogger.warn( e );
    }

    public void handleInvocationTargetException( InvocationTargetException e )
    {
        defaultHandle(e);
    }

    public void handleIllegalAccessException( IllegalAccessException e )
    {
        defaultHandle(e);
    }

    public void handleIllegalArgumentException( IllegalArgumentException e )
    {
        defaultHandle(e);
    }

    public void handleNoSuchMethodException( NoSuchMethodException e )
    {
        defaultHandle(e);
    }

    public void handleSecurityException( SecurityException e )
    {
        defaultHandle(e);
    }
}