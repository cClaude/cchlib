package com.googlecode.cchlib.i18n;
//package cx.ath.choisnet.i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.builder.I18nAutoUpdateInterface;

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
    private static final long serialVersionUID = 2L;
    // FIXME: need Log4J (fix it ?)
    private transient static Logger  logger = Logger.getLogger(I18nSimpleResourceBundle.class);
//    /** @serial */
//    private String         resourceBundleBaseName;
    /** @serial */
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
        super( resourceBundleBaseName );
//        this.resourceBundleBaseName = resourceBundleBaseName;

        setLocale( locale );
    }

    /**
     * Set current Locale
     *
     * @param language
     * @param country
     */
    public void setLocale(
            final String language,
            final String country
            )
    {
        setLocale( new Locale( language, country ) );
    }

    @Override // I18nAutoUpdateInterface
    public void setLocale( Locale locale )
    {
        this.currentLocale = locale;

        if( logger.isTraceEnabled() ) {
            logger.trace( "setLocale() - resourceBundleBaseName= " + resourceBundleBaseName );
            logger.trace( "setLocale() - currentLocale= " + currentLocale );
            logger.trace( "setLocale() - getLocale() = " + getLocale() );
            }

        try {
            super.resourceBundle
                = ResourceBundle.getBundle(
                    resourceBundleBaseName,
                    getLocale()
                    );
            }
        catch( MissingResourceException e ) {
            logger.error(
                "Error while trying to open default resource bundle for: "
                    + resourceBundleBaseName
                );
            throw e;
            }

        if( logger.isTraceEnabled() ) {
            logger.trace( "ResourceBundle.getLocale() = " + resourceBundle.getLocale() );
            logger.trace( "ResourceBundle = " + resourceBundle );
            }
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
        return resourceBundleBaseName;
    }
}
