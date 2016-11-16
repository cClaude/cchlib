package com.googlecode.cchlib.swing.i18n;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.AutoI18nCoreFactory;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.resources.ResourcesLoader;

/**
 * NEEDDOC
 *
 */
public class SwingAutoI18nCoreFactory
{
    private static SwingAutoI18nCoreFactory factory;
    private final Map<Locale,AutoI18nCore> map = new HashMap<>();

    private SwingAutoI18nCoreFactory()
    {
    }

    private AutoI18nCore newCurrentSwingAutoI18nCore()
    {
        final Locale locale  = Locale.getDefault();
        AutoI18nCore current = this.map.get( locale );

        if( current == null ) {
            final EnumSet<AutoI18nConfig> config = EnumSet.noneOf( AutoI18nConfig.class );
            final I18nResourceBundleName  resourceBundleName = ( ) -> ResourcesLoader.class.getPackage().getName() + ".i18n";

            current = AutoI18nCoreFactory.createAutoI18nCore( config, resourceBundleName, locale );
            }
        return current;
    }

    public static AutoI18nCore getCurrentSwingAutoI18nCore()
    {
        if( factory == null ) {
            factory = new SwingAutoI18nCoreFactory();
            }

        return factory.newCurrentSwingAutoI18nCore();
    }
}
