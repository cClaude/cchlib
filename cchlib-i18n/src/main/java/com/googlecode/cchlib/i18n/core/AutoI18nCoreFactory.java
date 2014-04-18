package com.googlecode.cchlib.i18n.core;

import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.AutoI18nTypeLookup;
import com.googlecode.cchlib.i18n.I18nInterface;
import com.googlecode.cchlib.i18n.logging.AutoI18nLog4JEventHandler;
import com.googlecode.cchlib.i18n.logging.AutoI18nLog4JExceptionHandler;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nSimpleResourceBundle;
import java.util.Locale;
import java.util.Set;

/**
 *
 */
public class AutoI18nCoreFactory
{
    private AutoI18nCoreFactory() {} // All static

    public static AutoI18nCore createAutoI18nCore(
        final Set<AutoI18nConfig> config,
        final I18nInterface       i18nInterface
        )
    {
        final AutoI18nTypeLookup  defaultAutoI18nTypes = null;

        return createAutoI18nCore( config, defaultAutoI18nTypes, i18nInterface );
    }

    public static AutoI18nCore createAutoI18nCore(
        final Set<AutoI18nConfig> config,
        final AutoI18nTypeLookup  defaultAutoI18nTypes,
        final I18nInterface       i18nInterface
        )
    {
        I18nDelegator i18nDelegator = new I18nDelegator( config, defaultAutoI18nTypes, i18nInterface );

        i18nDelegator.addAutoI18nExceptionHandler( new AutoI18nLog4JExceptionHandler( config ) );
        i18nDelegator.addAutoI18nEventHandler( new AutoI18nLog4JEventHandler() );

        return new AutoI18nCoreImpl( i18nDelegator );
    }

    public static AutoI18nCore createAutoI18nCore(
            final Set<AutoI18nConfig>     config,
            final I18nResourceBundleName  resourceBundleName,
            final Locale                  locale
            )
        {
             return createAutoI18nCore(
                     config,
                     new I18nSimpleResourceBundle( locale, resourceBundleName )
                     );
        }
}
