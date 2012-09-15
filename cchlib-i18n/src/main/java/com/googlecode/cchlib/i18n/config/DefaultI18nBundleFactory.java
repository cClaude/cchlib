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
     * Create AbstractI18nBundle for giving locale and giving
     * class to build message bundle base name
     * (see {@link #getMessagesBundle(Class)})
     * 
     * @param locale    {@link Locale} to use
     * @param clazz     {@link Class} to use
     * @return an AbstractI18nBundle
     * @see #getMessagesBundle(Class)
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
     * Create AbstractI18nBundle for giving locale and giving
     * i18nPrepAutoUpdatable to build message bundle base name
     * (see {@link I18nPrepAutoUpdatable#getMessagesBundle()})
     * 
     * @param locale                {@link Locale} to use
     * @param i18nPrepAutoUpdatable {@link I18nPrepAutoUpdatable} to use
     * @return an AbstractI18nBundle
     * @see I18nPrepAutoUpdatable#getMessagesBundle()
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
     * Returns Message Bundle base name based on giving class name
     * and ".MessagesBundle" as extension.
     * 
     * @param clazz Class to use to build Message Bundle base name
     * @return Message Bundle base name based on giving class name
     */
    public final static String getMessagesBundle( Class<?> clazz )
    {
        return clazz.getPackage().getName() + ".MessagesBundle";
    }
}
