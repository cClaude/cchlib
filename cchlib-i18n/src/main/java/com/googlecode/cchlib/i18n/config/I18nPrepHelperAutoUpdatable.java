package com.googlecode.cchlib.i18n.config;

import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;

/**
 * use {@link I18nAutoUpdatable} instead
 */
@Deprecated
@SuppressWarnings("ucd") // Just do keep documentation
public interface I18nPrepHelperAutoUpdatable extends I18nAutoUpdatable
{
    @Deprecated
    String getMessagesBundleForI18nPrepHelper();
}
