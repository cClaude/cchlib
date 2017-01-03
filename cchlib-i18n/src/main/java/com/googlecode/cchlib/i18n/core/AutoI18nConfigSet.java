package com.googlecode.cchlib.i18n.core;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import javax.annotation.Nullable;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.util.EnumHelper;

/**
 * Internal class for I18n
 */
public class AutoI18nConfigSet implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** @serial */
    private final Set<AutoI18nConfig> config;

    public AutoI18nConfigSet( @Nullable final Set<AutoI18nConfig> config )
    {
        final Set<AutoI18nConfig> fixedConfig = EnumHelper.safeCopyOf( config, AutoI18nConfig.class );

        if( Boolean.getBoolean( AutoI18n.DISABLE_PROPERTIES )) {
            // Internalization is disabled.
            fixedConfig.add( AutoI18nConfig.DISABLE );
            }

        this.config = Collections.unmodifiableSet( fixedConfig );
    }

    public Set<AutoI18nConfig> getSafeConfig()
    {
        return this.config;
    }
}
