/**
 * 
 */
package cx.ath.choisnet.i18n;

import java.util.Locale;

/**
 * @author Claude CHOISNET
 * @see I18nDefaultImpl
 * @see AutoI18n
 */
public interface I18nInterface 
{
    /**
     * Must accept null to be able to use default system {@link Locale}
     *
     * @param locale a valid {@link Locale} or null
     */
    public void setLocale( Locale locale );
    
    /**
     * Return {@link Locale} to use for I18n
     * 
     * @return always return a valid {@link Locale}
     */
    public Locale getCurrentLocale();
    
    /**
     * Resolve key according to current {@link Locale}
     * 
     * @param key Key to lookup for localization
     * @return String for giving key
     * @throws java.util.MissingResourceException if key not found
     */
    public String getString(String key)         
        throws java.util.MissingResourceException;
}