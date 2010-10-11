/**
 * 
 */
package cx.ath.choisnet.i18n;

import java.util.ResourceBundle;

/**
 * Provide a default implementation based on {@link ResourceBundle}
 * for {@link I18nInterface}
 * 
 * @author Claude CHOISNET
 */
public class I18nResourceBundle implements I18nInterface
{
    protected ResourceBundle resourceBundle;

    /**
     * Provide a non initialized object for inherit class
     * that must initialize ResourceBundle object.
     */
    protected I18nResourceBundle()
    { //Empty
    }
    
    /**
     * Create I18nResourceBundle using giving
     * ResourceBundle
     * 
     * @param resourceBundle ResourceBundle to use 
     */
    public I18nResourceBundle(
            ResourceBundle resourceBundle
            )
    {
        this.resourceBundle = resourceBundle;
    }

    @Override // I18nInterface
    final
    public String getString(String key)
        throws java.util.MissingResourceException
    {
        return resourceBundle.getString( key );
    }

    /**
     * @return current ResourceBundle
     */
    public ResourceBundle getResourceBundle()
    {
        return this.resourceBundle;
    }
}