package com.googlecode.cchlib.i18n.config;

import java.util.Locale;

/**
 * TODOC
 *
 */
public class DefaultI18nBundleFactory
{
    private DefaultI18nBundleFactory()
    {
        //All static
    }

    /**
    *
    * @param clazz
    * @return
    */
    public final static AbstractI18nBundle createDefaultI18nBundle(
        final Locale    locale,
        final Class<?>  clazz
        )
    {
        return new AbstractI18nBundle( locale )
        {
            @Override
            public String getMessagesBundle()
            {
                return DefaultI18nBundleFactory.getMessagesBundle( clazz );
            }
        };
    }

    /**
     *
     * @param i18nPrepAutoUpdatable
     * @return
     */
    public final static AbstractI18nBundle createDefaultI18nBundle(
            final Locale                locale,
            final I18nPrepAutoUpdatable i18nPrepAutoUpdatable
            )
        {
            return new AbstractI18nBundle( locale )
            {
                @Override
                public String getMessagesBundle()
                {
                    return i18nPrepAutoUpdatable.getMessagesBundle();
                }
            };
        }

    /**
     *
     * @param clazz
     * @return
     */
    public final static String getMessagesBundle( Class<?> clazz )
    {
        return clazz.getPackage().getName() + ".MessagesBundle";
    }
}
