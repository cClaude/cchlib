package com.googlecode.cchlib.i18n.resources;

import java.util.Locale;
import java.util.ResourceBundle;
import com.googlecode.cchlib.i18n.api.I18nResource;

/**
 * Provide a default implementation based on {@link ResourceBundle}
 * for {@link I18nResource}
 */
public class I18nSimpleResourceBundle extends I18nResourceBundle
{
    private static final long serialVersionUID = 3L;

    public I18nSimpleResourceBundle(
       final Locale                 locale,
       final I18nResourceBundleName resourceBundleName
       )
    {
        super( resourceBundleName.getName(), locale );
    }
}
