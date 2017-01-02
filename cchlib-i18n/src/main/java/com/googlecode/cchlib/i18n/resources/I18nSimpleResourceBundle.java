package com.googlecode.cchlib.i18n.resources;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.i18n.api.I18nResource;

/**
 * Provide a default implementation based on {@link ResourceBundle}
 * for {@link I18nResource}
 */
public class I18nSimpleResourceBundle extends I18nResourceBundle
{
    private static final long serialVersionUID = 3L;

    public I18nSimpleResourceBundle(
       @Nonnull final I18nResourceBundleName resourceBundleName,
       @Nonnull final Locale                 locale
       )
    {
        super( resourceBundleName.getName(), locale );
    }
}
