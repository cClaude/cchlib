/**
 * 
 */
package cx.ath.choisnet.i18n.builder;

import java.util.Locale;
import cx.ath.choisnet.i18n.I18nInterface;

/**
 * @see I18nPropertyResourceBundleAutoUpdate
 * @author Claude CHOISNET
 */
public interface I18nAutoUpdateInterface
    extends I18nInterface
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
     * @return a String that typically identify
     * Resource bundle properties file.
     */
    public String getResourceBundleBaseName();
}