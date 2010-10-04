/**
 * 
 */
package cx.ath.choisnet.i18n;

import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

/**
 * Provide a default implementation for {@link I18nInterface}
 * 
 * @author Claude CHOISNET
 */
public class I18nDefaultImpl implements I18nInterface
{
    private static Logger  slogger = Logger.getLogger(I18nInterface.class);
    private static String  DEFAULT_MESSAGE_BUNDLE = "jMiiEditor.i18n.MessagesBundle"; //$$$ TODO change this !!
    private String         resourceBundleBaseName;
    private Locale         currentLocale;
    private ResourceBundle messages;

    /**
     * I18nDefaultImpl 
     */
    public I18nDefaultImpl()
    {
        this(null,null);
    }
    
    public I18nDefaultImpl( 
            Locale locale,
            String resourceBundleBaseName
            )
    {
        if( resourceBundleBaseName == null ) {
            this.resourceBundleBaseName = DEFAULT_MESSAGE_BUNDLE;
        }
        else {
            this.resourceBundleBaseName = resourceBundleBaseName;
        }
        
        setLocale( locale );
    }

    /**
     * Set current Locale
     * 
     * @param language
     * @param country
     */
    public void setLocale( String language, String country )
    {
        setLocale( new Locale( language, country ) );
    }

    @Override
    public void setLocale( Locale locale )
    {
        this.currentLocale = locale;

        slogger.info( "setLocale() - resourceBundleBaseName= " + resourceBundleBaseName );
        slogger.info( "setLocale() - currentLocale= " + currentLocale );
        slogger.info( "setLocale() - getCurrentLocale() = " + getCurrentLocale() );
        
        this.messages = ResourceBundle.getBundle( 
                            resourceBundleBaseName,
                            getCurrentLocale() 
                            );
        slogger.info( "ResourceBundle.getLocale() = " + messages.getLocale() );
        slogger.info( "ResourceBundle = " + messages );
    }

    @Override
    public String getString(String key)
        throws java.util.MissingResourceException
    {
        return messages.getString( key );
    }
    
    @Override
    public Locale getCurrentLocale()
    {
        if( this.currentLocale == null ) {
            return Locale.getDefault();
        }
        else {
            return this.currentLocale;
        }
    }
    
    public String getResourceBundleBaseName()
    {
        return this.resourceBundleBaseName;
    }
}