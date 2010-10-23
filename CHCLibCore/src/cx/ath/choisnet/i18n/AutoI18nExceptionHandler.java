/**
 * 
 */
package cx.ath.choisnet.i18n;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.MissingResourceException;

/**
 * TODO: doc
 * 
 * @author Claude CHOISNET
 */
public interface AutoI18nExceptionHandler 
    extends Serializable
{
    /**
     * TODO: doc
     * 
     * @param e
     */
    public void handleInvocationTargetException( InvocationTargetException e );

    /**
     * TODO: doc
     * 
     * @param e
     */
    public void handleIllegalAccessException( IllegalAccessException e );

    /**
     * TODO: doc
     * 
     * @param e
     */
    public void handleIllegalArgumentException( IllegalArgumentException e );

    /**
     * TODO: doc
     * 
     * @param e
     */
    public void handleNoSuchMethodException( NoSuchMethodException e );

    /**
     * TODO: doc
     * 
     * @param e
     */
    public void handleSecurityException( SecurityException e );

    /**
     * TODO: doc
     * 
     * @param e
     * @param field 
     * @param key 
     * @param methods 
     */
    public void handleMissingResourceException( MissingResourceException e, Field field, String key, Method[] methods);

    /**
     * TODO: doc
     * 
     * @param e
     * @param field 
     * @param key 
     */
    public void handleMissingResourceException( MissingResourceException e, Field field, String key);
    
    /**
     * TODO: doc
     * 
     * @param e
     * @param field 
     * @param key 
     */
    public void handleMissingResourceException( MissingResourceException e, Field field, AutoI18n.Key key);

}