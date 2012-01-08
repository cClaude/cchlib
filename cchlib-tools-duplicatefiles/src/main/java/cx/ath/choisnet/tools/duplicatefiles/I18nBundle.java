package cx.ath.choisnet.tools.duplicatefiles;

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

public class I18nBundle
{
    private static AutoI18n autoI18n = null;
    private static I18nSimpleStatsResourceBundle autoI18nSimpleStatsResourceBundle;

    public static String getMessagesBundle()
    {
        return I18nBundle.class.getPackage().getName()
                + ".MessagesBundle";
    }

    public static EnumSet<AutoI18n.Attribute> getAutoI18nAttributes()
    {
        return EnumSet.of(
                AutoI18n.Attribute.DO_DEEP_SCAN
                );
    }

    private static AutoI18nEventHandler getAutoI18nEventHandler()
    {
        return new AutoI18nLog4JEventHandler(
                Level.TRACE,
                Level.TRACE // Level.DEBUG
                );
    }

    public static AutoI18nExceptionHandler getAutoI18nExceptionHandler()
    {
        return new AutoI18nLog4JExceptionHandler();
    }

    public static AutoI18n getAutoI18n()
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

    public static I18nSimpleStatsResourceBundle getAutoI18nSimpleStatsResourceBundle()
    {
        if( autoI18nSimpleStatsResourceBundle == null ) {
            autoI18nSimpleStatsResourceBundle
                = new I18nSimpleStatsResourceBundle(
                    getMessagesBundle()
                    );
        }
        return autoI18nSimpleStatsResourceBundle;
    }

    public static I18nPropertyResourceBundleAutoUpdate getI18nPropertyResourceBundleAutoUpdate()
    {
        return getI18nPropertyResourceBundleAutoUpdate(
                getAutoI18nSimpleStatsResourceBundle()
                );
    }

    public static I18nPropertyResourceBundleAutoUpdate getI18nPropertyResourceBundleAutoUpdate(
            I18nAutoUpdateInterface i18Builder
            )
    {
        //setOutputFile
        return new I18nPropertyResourceBundleAutoUpdate(
                i18Builder,
                null, // default AutoI18nTypes
                getAutoI18nExceptionHandler(),
                getAutoI18nEventHandler(),
                I18nBundle.getAutoI18nAttributes(),
                EnumSet.of(
                        I18nPropertyResourceBundleAutoUpdate.Attribute.ADD_ONLY_NEEDED_KEY
                        )
                );
    }

}
