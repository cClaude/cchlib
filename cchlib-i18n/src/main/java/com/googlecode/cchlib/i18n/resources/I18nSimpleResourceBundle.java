package com.googlecode.cchlib.i18n.resources;

import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.I18nInterface;

/**
 * Provide a default implementation based on {@link ResourceBundle}
 * for {@link I18nInterface}
 */
public class I18nSimpleResourceBundle
    extends I18nResourceBundle
{
    private static final long serialVersionUID = 2L;
    private static transient Logger LOGGER = Logger.getLogger(I18nSimpleResourceBundle.class);

    /** @serial */
    private Locale currentLocale;

    /**
     *
     * @param locale
     * @param resourceBundleName
     */
    public I18nSimpleResourceBundle(
       final Locale                 locale,
       final I18nResourceBundleName resourceBundleName
       )
    {
        this( locale, resourceBundleName.getName() );
    }

    /**
     *
     * @param locale
     * @param resourceBundleFullBaseName
     */
    public I18nSimpleResourceBundle(
            final Locale locale,
            final String resourceBundleFullBaseName
            )
    {
        super( resourceBundleFullBaseName );

        if( locale == null ) {
            throw new NullPointerException( "Locale is null" );
            }

        setLocale( locale );
    }

    private void setLocale( Locale locale )
    {
      this.currentLocale = locale;

      if( LOGGER.isTraceEnabled() ) {
          LOGGER.trace( "setLocale() - resourceBundleFullBaseName= " + getResourceBundleFullBaseName() );
          LOGGER.trace( "setLocale() - currentLocale= " + currentLocale );
          LOGGER.trace( "setLocale() - getLocale() = " + getLocale() );

          if( ! getLocale().equals(currentLocale) ) {
              LOGGER.error( "getLocale() != currentLocale" );
              }
          }

      try {
          super.setResourceBundle( ResourceBundle.getBundle(
              getResourceBundleFullBaseName(),
              getLocale()
              ) );
          }
      catch( java.util.MissingResourceException e ) {
          LOGGER.error(
              "Error while trying to open default resource bundle for: "
                  + getResourceBundleFullBaseName()
              );
          throw e; // FIXME : throw new MissingResourceBundleException !!!
          }

      if( LOGGER.isTraceEnabled() ) {
          if( ! getResourceBundle().getLocale().equals(currentLocale) ) {
              LOGGER.error( "resourceBundle.getLocale() != currentLocale" );
              }
          LOGGER.trace( "resourceBundle.getLocale() = " + getResourceBundle().getLocale() );
          LOGGER.trace( "ResourceBundle = " + getResourceBundle() );
          }
    }

    private Locale getLocale()
    {
        return this.currentLocale;
    }
}
