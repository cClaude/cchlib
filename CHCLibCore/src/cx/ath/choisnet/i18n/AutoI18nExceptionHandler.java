/**
 * 
 */
package cx.ath.choisnet.i18n;

import java.lang.reflect.InvocationTargetException;

/**
 * TODO: doc
 * @author Claude CHOISNET
 */
public interface AutoI18nExceptionHandler 
{
    public void handleInvocationTargetException( InvocationTargetException e );
    public void handleIllegalAccessException( IllegalAccessException e );
    public void handleIllegalArgumentException( IllegalArgumentException e );
    public void handleNoSuchMethodException( NoSuchMethodException e );
    public void handleSecurityException( SecurityException e );
}