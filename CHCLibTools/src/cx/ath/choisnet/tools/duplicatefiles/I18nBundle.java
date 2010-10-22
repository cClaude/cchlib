package cx.ath.choisnet.tools.duplicatefiles;

import java.util.EnumSet;
import cx.ath.choisnet.i18n.AutoI18n;
import cx.ath.choisnet.i18n.AutoI18nLog4JExceptionHandler;
import cx.ath.choisnet.i18n.I18nSimpleResourceBundle;
import cx.ath.choisnet.i18n.builder.I18nAutoUpdateInterface;
import cx.ath.choisnet.i18n.builder.I18nPropertyResourceBundleAutoUpdate;

public class I18nBundle 
{
    private static AutoI18n autoI18n = null;

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
    
    public static AutoI18n getAutoI18n()
    {
        if( autoI18n == null ) {
            autoI18n = new AutoI18n( 
                    new I18nSimpleResourceBundle(
                            getMessagesBundle()
                            ),
                    null,
                    new AutoI18nLog4JExceptionHandler(),
                    getAutoI18nAttributes()
                    );
            }
        return autoI18n;
    }
    

    public static I18nPropertyResourceBundleAutoUpdate getI18nPropertyResourceBundleAutoUpdate()
    {
        return getI18nPropertyResourceBundleAutoUpdate(
                new I18nSimpleResourceBundle(
                        getMessagesBundle()
                        )
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
                new AutoI18nLog4JExceptionHandler(),
                I18nBundle.getAutoI18nAttributes(),
                EnumSet.of( 
                        I18nPropertyResourceBundleAutoUpdate.Attribute.ADD_ONLY_NEEDED_KEY
                        )
                );
    }

}
