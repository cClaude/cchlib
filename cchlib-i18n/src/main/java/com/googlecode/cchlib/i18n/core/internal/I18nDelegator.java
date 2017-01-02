package com.googlecode.cchlib.i18n.core.internal;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.AutoI18nEventHandler;
import com.googlecode.cchlib.i18n.AutoI18nExceptionHandler;
import com.googlecode.cchlib.i18n.AutoI18nTypeLookup;
import com.googlecode.cchlib.i18n.EventCause;
import com.googlecode.cchlib.i18n.I18nSyntaxException;
import com.googlecode.cchlib.i18n.MethodProviderSecurityException;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.core.I18nField;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.MissingKeyException;
import com.googlecode.cchlib.i18n.core.resolve.SetFieldException;
import com.googlecode.cchlib.i18n.resources.MissingResourceException;

/**
 *
 * @since 4.1.7
 */
public class I18nDelegator implements Serializable
{
    private static final long serialVersionUID = 2L;
    /** @serial */
    private final AutoI18nConfigSet config;
    /** @serial */
    private final List<AutoI18nExceptionHandler> exceptionHandlerList = new ArrayList<>();
    /** @serial */
    private final List<AutoI18nEventHandler> eventHandlerList = new ArrayList<>();
    /** @serial */
    private final AutoI18nTypeLookup defaultTypes;
    private final AutoI18nTypeLookup allTypes = new AllAutoI18nTypes();
    private final I18nResource i18nResource;

    public I18nDelegator(
        @Nonnull final AutoI18nConfigSet   config,
        @Nullable final AutoI18nTypeLookup defaultAutoI18nTypes,
        @Nonnull final I18nResource        i18nResource // TODO: use a factory
        )
    {
        if( config == null ) {
            throw new NullPointerException( "config is null" );
            }

        if( i18nResource == null ) {
            throw new NullPointerException( "i18nResource is null" );
            }

        this.config       = config;
        this.defaultTypes = (defaultAutoI18nTypes == null) ? new DefaultAutoI18nTypes() : defaultAutoI18nTypes;
        this.i18nResource = i18nResource;
    }

    public void addAutoI18nEventHandler( final AutoI18nEventHandler eventHandler )
    {
        this.eventHandlerList.add( eventHandler );
    }

    public void addAutoI18nExceptionHandler( final AutoI18nExceptionHandler exceptionHandler )
    {
        this.exceptionHandlerList.add( exceptionHandler );
    }

    public void fireIgnoreAnnotation(
        final Field      field,
        final Annotation i18n,
        final EventCause cause
        )
    {
        for( final AutoI18nEventHandler eventHandler : this.eventHandlerList ) {
            final String causeDescription = "cause by Annocation: " + i18n;

            eventHandler.ignoredField( field, null, cause, causeDescription );
            }
    }

    protected void fireIgnoreField(
        final Field      field,
        final String     key,
        final EventCause cause,
        final String     causeDescription
        )
    {
        for( final AutoI18nEventHandler eventHandler : this.eventHandlerList ) {
            eventHandler.ignoredField( field, key, cause, causeDescription );
            }
    }

    /**
     * @return a {@link AutoI18nTypeLookup} that look in all supported types.
     */
    public AutoI18nTypeLookup getAutoI18nTypes()
    {
        return this.allTypes;
    }

    public Set<AutoI18nConfig> getConfig()
    {
        return this.config.getSafeConfig();
    }

    /**
     * @return a {@link AutoI18nTypeLookup} that look only in default supported types.
     */
    public AutoI18nTypeLookup getDefaultAutoI18nTypes()
    {
        return this.defaultTypes;
    }

    public I18nResource getI18nInterface( final Locale locale )
    {
        return this.i18nResource;
    }

    public void handleI18nSyntaxeException(
        final I18nSyntaxException cause,
        final Field               field
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleI18nSyntaxException( cause, field );
            }
    }

    public void handleIllegalAccessException(
        final IllegalAccessException cause,
        final I18nField              i18nField
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleIllegalAccessException( cause, i18nField );
            }
    }

    public void handleIllegalArgumentException(
        final IllegalArgumentException cause,
        final I18nField                i18nField
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleIllegalArgumentException( cause, i18nField );
            }
    }

    public void handleInvocationTargetException(
        final InvocationTargetException cause,
        final I18nField                 i18nField
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleInvocationTargetException( cause, i18nField );
            }
    }

    public void handleMissingKeyException(
        final MissingKeyException   cause,
        final I18nField             i18nField,
        final I18nResolver          i18nResolver
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleMissingKeyException( cause, i18nField, i18nResolver );
            }
    }

    public <T> void handleMissingResourceException(
        final MissingResourceException cause,
        final I18nField                field,
        final T                        objectToI18n,
        final I18nResource            i18nInterface
       )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleMissingResourceException( cause, field, objectToI18n, i18nInterface );
            }
    }

    public void handleNoSuchMethodException(
        final NoSuchMethodException cause,
        final Field                 field
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleNoSuchMethodException( cause, field );
            }
    }

    public void handleNoSuchMethodException(
        final NoSuchMethodException cause,
        final I18nField             i18nField
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleNoSuchMethodException( cause, i18nField );
            }
    }

    public void handleSecurityException(
        final MethodProviderSecurityException cause,
        final Field                           field
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleSecurityException( cause, field );
            }
    }

    public void handleSecurityException(
        final SecurityException cause,
        final I18nField         i18nField
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleSecurityException( cause, i18nField );
            }
    }

    public void handleSetFieldException(
        final SetFieldException cause,
        final I18nField         i18nField,
        final I18nResolver      i18nResolver
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleSetFieldException( cause, i18nField, i18nResolver );
            }
    }
}
