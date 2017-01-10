package com.googlecode.cchlib.i18n.core;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import javax.annotation.Nullable;
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

        if( Boolean.getBoolean( AutoI18nConfig.DISABLE_PROPERTIES )) {
            // Internalization is disabled.
            fixedConfig.add( AutoI18nConfig.DISABLE );
            }

        this.config = Collections.unmodifiableSet( fixedConfig );
    }

    public Set<AutoI18nConfig> getSafeConfig()
    {
        return this.config;
    }

    public boolean contains( final AutoI18nConfig value )
    {
        return this.config.contains( value );
    }

    public boolean isPrintStacktraceInLogs()
    {
        return contains( AutoI18nConfig.PRINT_STACKTRACE_IN_LOGS );
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "AutoI18nConfigSet [config=" );
        builder.append( this.config );
        builder.append( "]" );

        return builder.toString();
    }
}
