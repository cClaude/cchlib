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
     * @param clazz     {@link Class} to use to build message bundle path
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
     * class to build message bundle base name
     * (see {@link #getMessagesBundle(Package)})
     * 
     * @param locale    {@link Locale} to use
     * @param pakkage   {@link Package} to use to build message bundle path
     * @param base      Begin of message bundle file name
     * @return an AbstractI18nBundle
     * @see #getMessagesBundle(Class)
     * @since 4.1.7
     */
    public final static AbstractI18nBundle createDefaultI18nBundle(
        final Locale  locale,
        final Package pakkage,
        final String  base
        )
    {
        return new AbstractI18nBundle( locale )
        {
            @Override
            public String getMessagesBundle()
            {
                return DefaultI18nBundleFactory.getMessagesBundle( pakkage, base );
            }
        };
    }

//    @Deprecated
//    public static AbstractI18nBundle createDefaultI18nBundle( 
//            final Locale                locale,
//            final I18nPrepAutoUpdatable i18nPrepAutoUpdatable
//            )
//    {
//        return createDefaultI18nBundle( locale, new I18nPrepHelperAutoUpdatable() {
//            @Override
//            public void performeI18n( AutoI18n autoI18n )
//            {
//                i18nPrepAutoUpdatable.performeI18n( autoI18n );
//            }
//            @Override
//            public String getMessagesBundleForI18nPrepHelper()
//            {
//                return i18nPrepAutoUpdatable.getMessagesBundle();
//            }} );
//    }

    /**
     * Create AbstractI18nBundle for giving locale and giving
     * i18nPrepAutoUpdatable to build message bundle base name
     * (see {@link I18nPrepAutoUpdatable#getMessagesBundle()})
     * 
     * @param locale                      {@link Locale} to use
     * @param i18nPrepHelperAutoUpdatable {@link I18nPrepHelperAutoUpdatable} to use
     * @return an AbstractI18nBundle
     * @see I18nPrepAutoUpdatable#getMessagesBundle()
     */
    public final static AbstractI18nBundle createDefaultI18nBundle(
            final Locale                      locale,
            final I18nPrepHelperAutoUpdatable i18nPrepHelperAutoUpdatable 
            )
        {
            return new AbstractI18nBundle( locale )
            {
                @Override
                public String getMessagesBundle()
                {
                    return i18nPrepHelperAutoUpdatable.getMessagesBundleForI18nPrepHelper();
                }
            };
        }

    /**
     * Returns Message Bundle base name based on the package name
     * of giving class (add ".MessagesBundle" as extension).
     * 
     * @param clazz Class to use to build Message Bundle base name
     * @return Message Bundle base name based on giving class name
     * @see #getMessagesBundle(Package, String)
     */
    public final static String getMessagesBundle( Class<?> clazz )
    {
        //return clazz.getPackage().getName() + ".MessagesBundle";
        return getMessagesBundle( clazz.getPackage(), "MessagesBundle" );
    }
    
    /**
     * Returns Message Bundle base name based on the giving package name
     * 
     * @param pakkage Package to use to build Message Bundle base name (use to build path)
     * @param base    Base name for the file
     * @return Message Bundle base name based on giving class name
     * @since 4.1.7
     */
    public final static String getMessagesBundle( Package pakkage, String base )
    {
        return pakkage.getName() + '.' + base;
    }

}
