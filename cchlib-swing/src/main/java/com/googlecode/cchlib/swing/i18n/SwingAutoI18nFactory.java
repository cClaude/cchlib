package com.googlecode.cchlib.swing.i18n;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18nFactory;
import com.googlecode.cchlib.i18n.resources.I18nResourceFactory;
import com.googlecode.cchlib.resources.ResourcesLoader;

/**
 * NEEDDOC
 *
 */
public class SwingAutoI18nFactory
{
    private static SwingAutoI18nFactory factory;
    private final Map<Locale,AutoI18n>  map = new HashMap<>();

    private SwingAutoI18nFactory()
    {
        // private
    }

    private AutoI18n newCurrentSwingAutoI18n()
    {
        final Locale locale  = Locale.getDefault();

        AutoI18n current = this.map.get( locale );

        if( current == null ) {
            current = AutoI18nFactory.newAutoI18n(
                    EnumSet.noneOf( AutoI18nConfig.class ),
                    I18nResourceFactory.newI18nResourceBundle(
                            ResourcesLoader.class.getPackage().getName() + ".i18n",
                            locale
                            )
                        );
            }

        return current;
    }

    public static AutoI18n getCurrentSwingAutoI18n()
    {
        if( factory == null ) {
            factory = new SwingAutoI18nFactory();
            }

        return factory.newCurrentSwingAutoI18n();
    }
}
