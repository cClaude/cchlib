/**
 * 
 */
package cx.ath.choisnet.i18n;

import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import cx.ath.choisnet.i18n.builder.I18nAutoUpdateInterface;

/**
 * Provide a default implementation based on {@link ResourceBundle}
 * for {@link I18nInterface}
 * 
 * @author Claude CHOISNET
 */
public class I18nSimpleResourceBundle
    extends I18nResourceBundle
        implements I18nAutoUpdateInterface
{
    private static final long serialVersionUID = 1L;
    private transient static Logger  slogger = Logger.getLogger(I18nSimpleResourceBundle.class);
    private String         resourceBundleBaseName;
    private Locale         currentLocale;

    /**
     * @param resourceBundleBaseName 
     */
    public I18nSimpleResourceBundle(
            String resourceBundleBaseName
            )
    {
        this(null,resourceBundleBaseName);
    }
    
    /**
     * 
     * @param locale
     * @param resourceBundleBaseName
     */
    public I18nSimpleResourceBundle( 
            Locale locale,
            String resourceBundleBaseName
            )
    {
        this.resourceBundleBaseName = resourceBundleBaseName;

        setLocale( locale );
    }

    /**
     * Set current Locale
     * 
     * @param language
     * @param country
     */
    public void setLocale( 
            String language, 
            String country 
            )
    {
        setLocale( new Locale( language, country ) );
    }

    @Override // I18nAutoUpdateInterface
    public void setLocale( Locale locale )
    {
        this.currentLocale = locale;

        slogger.info( "setLocale() - resourceBundleBaseName= " + resourceBundleBaseName );
        slogger.info( "setLocale() - currentLocale= " + currentLocale );
        slogger.info( "setLocale() - getLocale() = " + getLocale() );

        super.resourceBundle 
            = ResourceBundle.getBundle( 
                    resourceBundleBaseName,
                    getLocale() 
                    );
        slogger.info( "ResourceBundle.getLocale() = " + resourceBundle.getLocale() );
        slogger.info( "ResourceBundle = " + resourceBundle );
    }

    
    @Override // I18nAutoUpdateInterface
    public Locale getLocale()
    {
        if( this.currentLocale == null ) {
            return Locale.getDefault();
        }
        else {
            return this.currentLocale;
        }
    }
    
    @Override // I18nAutoUpdateInterface
    public String getResourceBundleBaseName()
    {
        return this.resourceBundleBaseName;
    }
}