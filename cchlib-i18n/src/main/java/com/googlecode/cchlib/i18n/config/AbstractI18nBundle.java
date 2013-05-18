package com.googlecode.cchlib.i18n.config;

import java.util.EnumSet;
import java.util.Locale;
import org.apache.log4j.Level;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.AutoI18nEventHandler;
import com.googlecode.cchlib.i18n.AutoI18nExceptionHandler;
import com.googlecode.cchlib.i18n.I18nSimpleResourceBundle;
import com.googlecode.cchlib.i18n.I18nSimpleStatsResourceBundle;
import com.googlecode.cchlib.i18n.builder.I18nAutoUpdateInterface;
import com.googlecode.cchlib.i18n.builder.I18nPropertyResourceBundleAutoUpdate;
import com.googlecode.cchlib.i18n.hidden.AutoI18nImpl;
import com.googlecode.cchlib.i18n.logging.AutoI18nLog4JEventHandler;
import com.googlecode.cchlib.i18n.logging.AutoI18nLog4JExceptionHandler;

/**
 * This class is design to be use has a common class
 * for building first resource bundle of the application
 * has a properties file and also to be use to apply
 * internationalization of the application.
 */
public abstract class AbstractI18nBundle
{
    private AutoI18n autoI18n = null;
    private I18nSimpleStatsResourceBundle autoI18nSimpleStatsResourceBundle;
    private Locale locale;

    protected AbstractI18nBundle( Locale locale )
    {
        this.locale = locale;
    }

    /**
     * Returns base name of properties file that should
     * receive keys and values for the internationalization
     * process
     * @return Messages bundle base name
     */
    public abstract String getMessagesBundle();

    /**
     * Returns an {@link AutoI18nImpl} for production version
     * of the application.
     * @return an {@link AutoI18nImpl} able to do the I18n.
     */
    public AutoI18n getAutoI18n()
    {
       if( autoI18n == null ) {
           autoI18n = new AutoI18nImpl(
                   new I18nSimpleResourceBundle(
                           locale,
                           getMessagesBundle()
                           ),
                   //null,null,
                   getAutoI18nExceptionHandler(),
                   getAutoI18nEventHandler(),
                   getAutoI18nAttributes()
                   );
           }
       return autoI18n;
    }

    /**
     * Returns an {@link AutoI18nImpl} for a development
     * version of the application. This version must
     * open all frames an build all object that should
     * be internationalized using same object structure
     * (key are based on object structure).
     * @return an {@link AutoI18nImpl} able to identify
     * keys that should be translated, able to collection
     * how many times keys are used.
     */
    public I18nSimpleStatsResourceBundle getAutoI18nSimpleStatsResourceBundle()
    {
        if( autoI18nSimpleStatsResourceBundle == null ) {
            autoI18nSimpleStatsResourceBundle
                = new I18nSimpleStatsResourceBundle(
                   locale,
                   getMessagesBundle()
                   );
            }
        return autoI18nSimpleStatsResourceBundle;
    }

   /**
    *
    * @return I18nPropertyResourceBundleAutoUpdate for this
    *         AbstractI18nBundle
    */
   public I18nPropertyResourceBundleAutoUpdate getI18nPropertyResourceBundleAutoUpdate()
   {
       return getI18nPropertyResourceBundleAutoUpdate(
               getAutoI18nSimpleStatsResourceBundle()
               );
   }

    /**
     * Returns default configuration
     * @see com.googlecode.cchlib.i18n.hidden.AutoI18nImpl.Attribute#DO_DEEP_SCAN
     * @return default configuration
     */
    protected EnumSet<AutoI18nImpl.Attribute> getAutoI18nAttributes()
    {
        return EnumSet.of(
                AutoI18n.Attribute.DO_DEEP_SCAN
                );
    }

    /**
     * Returns a AutoI18nEventHandler (In this implementation
     * it's a {@link AutoI18nLog4JEventHandler})
     * @return a new AutoI18nEventHandler
     */
    protected AutoI18nEventHandler getAutoI18nEventHandler()
    {
        return new AutoI18nLog4JEventHandler(
                Level.TRACE,
                Level.TRACE // Level.DEBUG
                );
    }

    /**
     * Returns a AutoI18nExceptionHandler (In this implementation
     * it's a {@link AutoI18nLog4JExceptionHandler})
     * @return a new AutoI18nExceptionHandler
     */
    protected AutoI18nExceptionHandler getAutoI18nExceptionHandler()
    {
        return new AutoI18nLog4JExceptionHandler();
    }

    /**
     *
     * @param i18Builder
     * @return TODOC
     */
    private I18nPropertyResourceBundleAutoUpdate getI18nPropertyResourceBundleAutoUpdate(
            I18nAutoUpdateInterface i18Builder
            )
    {
        //setOutputFile
        return new I18nPropertyResourceBundleAutoUpdate(
                i18Builder,
                null, // default AutoI18nTypes
                null, // force AutoI18nTypes
                getAutoI18nExceptionHandler(),
                getAutoI18nEventHandler(),
                getAutoI18nAttributes(),
                EnumSet.of(
                        I18nPropertyResourceBundleAutoUpdate.Attribute.ADD_ONLY_NEEDED_KEY
                        )
                );
    }

}
