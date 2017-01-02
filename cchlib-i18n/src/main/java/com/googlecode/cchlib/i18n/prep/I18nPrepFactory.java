package com.googlecode.cchlib.i18n.prep;

import java.util.Locale;
import java.util.Set;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.AutoI18nTypeLookup;
import com.googlecode.cchlib.i18n.core.AutoI18nCoreFactory;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;

/**
 *
 * @see I18nPrep
 * @see I18nPrepHelper
 */
public class I18nPrepFactory {
    private I18nPrepFactory()
    {
        // All static
    }

    /**
     * NEEDDOC
     *
     * @param config
     *            NEEDDOC
     * @param messageBundleName
     *            NEEDDOC
     * @param locale
     *            NEEDDOC
     * @return NEEDDOC
     */
    public static I18nPrep newI18nPrep( final Set<AutoI18nConfig> config, final I18nResourceBundleName messageBundleName, final Locale locale )
    {
        return newI18nPrep( config, AutoI18nCoreFactory.DEFAULT_AUTO_I18N_TYPES, messageBundleName, locale );
    }

    /**
     * NEEDDOC
     *
     * @param config
     *            NEEDDOC
     * @param defaultAutoI18nTypes
     *            NEEDDOC
     * @param messageBundleName
     *            NEEDDOC
     * @param locale
     *            NEEDDOC
     * @return NEEDDOC
     */
    public static I18nPrep newI18nPrep( final Set<AutoI18nConfig> config, final AutoI18nTypeLookup defaultAutoI18nTypes,
            final I18nResourceBundleName messageBundleName, final Locale locale )
    {
        return new I18nPrepImpl( config, defaultAutoI18nTypes, locale, messageBundleName );
    }

}
