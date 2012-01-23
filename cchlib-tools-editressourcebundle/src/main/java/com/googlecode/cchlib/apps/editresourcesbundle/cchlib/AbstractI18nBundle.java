package com.googlecode.cchlib.apps.editresourcesbundle.cchlib;

import java.util.EnumSet;
import org.apache.log4j.Level;
import cx.ath.choisnet.i18n.AutoI18n;
import cx.ath.choisnet.i18n.AutoI18nEventHandler;
import cx.ath.choisnet.i18n.AutoI18nExceptionHandler;
import cx.ath.choisnet.i18n.I18nSimpleResourceBundle;
import cx.ath.choisnet.i18n.I18nSimpleStatsResourceBundle;
import cx.ath.choisnet.i18n.builder.I18nAutoUpdateInterface;
import cx.ath.choisnet.i18n.builder.I18nPropertyResourceBundleAutoUpdate;
import cx.ath.choisnet.i18n.logging.AutoI18nLog4JEventHandler;
import cx.ath.choisnet.i18n.logging.AutoI18nLog4JExceptionHandler;

/**
 * TODO: Move this in cchlib-core after add some documentation.
 *
 */
public abstract class AbstractI18nBundle
{
    private AutoI18n autoI18n = null;
    private I18nSimpleStatsResourceBundle autoI18nSimpleStatsResourceBundle;

    protected AbstractI18nBundle()
    {
        // Abstract class
    }

    /**
     * Returns MessagesBundle as a String
     * @return MessagesBundle
     */
    public abstract String getMessagesBundle();

    /**
    *
    * @return
    */
   public AutoI18n getAutoI18n()
   {
       if( autoI18n == null ) {
           autoI18n = new AutoI18n(
                   new I18nSimpleResourceBundle(
                           getMessagesBundle()
                           ),
                   null,
                   getAutoI18nExceptionHandler(),
                   getAutoI18nEventHandler(),
                   getAutoI18nAttributes()
                   );
           }
       return autoI18n;
    }

    /**
     *
     * @return
     */
    public I18nSimpleStatsResourceBundle getAutoI18nSimpleStatsResourceBundle()
    {
        if( autoI18nSimpleStatsResourceBundle == null ) {
            autoI18nSimpleStatsResourceBundle
               = new I18nSimpleStatsResourceBundle(
                   getMessagesBundle()
                   );
        }
        return autoI18nSimpleStatsResourceBundle;
    }

   /**
    *
    * @return
    */
   public I18nPropertyResourceBundleAutoUpdate getI18nPropertyResourceBundleAutoUpdate()
   {
       return getI18nPropertyResourceBundleAutoUpdate(
               getAutoI18nSimpleStatsResourceBundle()
               );
   }

    /**
     *
     * @return
     */
    protected EnumSet<AutoI18n.Attribute> getAutoI18nAttributes()
    {
        return EnumSet.of(
                AutoI18n.Attribute.DO_DEEP_SCAN
                );
    }

    /**
     *
     * @return
     */
    protected AutoI18nEventHandler getAutoI18nEventHandler()
    {
        return new AutoI18nLog4JEventHandler(
                Level.TRACE,
                Level.TRACE // Level.DEBUG
                );
    }

    /**
     *
     * @return
     */
    protected AutoI18nExceptionHandler getAutoI18nExceptionHandler()
    {
        return new AutoI18nLog4JExceptionHandler();
    }

    /**
     *
     * @param i18Builder
     * @return
     */
    private I18nPropertyResourceBundleAutoUpdate getI18nPropertyResourceBundleAutoUpdate(
            I18nAutoUpdateInterface i18Builder
            )
    {
        //setOutputFile
        return new I18nPropertyResourceBundleAutoUpdate(
                i18Builder,
                null, // default AutoI18nTypes
                getAutoI18nExceptionHandler(),
                getAutoI18nEventHandler(),
                getAutoI18nAttributes(),
                EnumSet.of(
                        I18nPropertyResourceBundleAutoUpdate.Attribute.ADD_ONLY_NEEDED_KEY
                        )
                );
    }

}
