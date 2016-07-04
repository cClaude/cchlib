package com.googlecode.cchlib.i18n.core;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.AutoI18nEventHandler;
import com.googlecode.cchlib.i18n.AutoI18nExceptionHandler;
import com.googlecode.cchlib.i18n.AutoI18nTypeLookup;
import com.googlecode.cchlib.i18n.EventCause;
import com.googlecode.cchlib.i18n.I18nInterface;
import com.googlecode.cchlib.i18n.I18nSyntaxeException;
import com.googlecode.cchlib.i18n.MethodProviderSecurityException;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.MissingKeyException;
import com.googlecode.cchlib.i18n.core.resolve.SetFieldException;
import com.googlecode.cchlib.i18n.resources.MissingResourceException;

/**
 *
 * @since 4.1.7
 */
//not public
class I18nDelegator implements Serializable
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private final EnumSet<AutoI18nConfig> config;
    /** @serial */
    private final List<AutoI18nExceptionHandler> exceptionHandlerList = new ArrayList<>();
    /** @serial */
    private final List<AutoI18nEventHandler> eventHandlerList = new ArrayList<>();
    /** @serial */
    private final AutoI18nTypeLookup defaultTypes;
    private final AutoI18nTypeLookup allTypes = new AllAutoI18nTypes();
    private final I18nInterface i18nInterface;

    public I18nDelegator(
        final Set<AutoI18nConfig>   userConfig,
        final AutoI18nTypeLookup    defaultAutoI18nTypes,
        final I18nInterface         i18nInterface // TODO: use a factory
        )
    {
        this.config       = (userConfig == null) ? EnumSet.noneOf( AutoI18nConfig.class ) : EnumSet.copyOf( userConfig );
        this.defaultTypes = (defaultAutoI18nTypes == null) ? new DefaultAutoI18nTypes() : defaultAutoI18nTypes;

        if( Boolean.getBoolean( AutoI18n.DISABLE_PROPERTIES )) {
            // Internalization is disabled.
            this.config.add( AutoI18nConfig.DISABLE );
            }

        if( i18nInterface == null ) {
            throw new NullPointerException( "i18nInterface is null" );
            }
        this.i18nInterface = i18nInterface;
    }

    protected void addAutoI18nEventHandler( final AutoI18nEventHandler eventHandler )
    {
        this.eventHandlerList.add( eventHandler );
    }

    protected void addAutoI18nExceptionHandler( final AutoI18nExceptionHandler exceptionHandler )
    {
        this.exceptionHandlerList.add( exceptionHandler );
    }

    public void fireIgnoreAnnotation(
        final Field      f,
        final Annotation i18n,
        final EventCause cause
        )
    {
        for( final AutoI18nEventHandler eventHandler : this.eventHandlerList ) {
            final String causeDescription = "cause by Annocation: " + i18n;

            eventHandler.ignoredField( f, null, cause, causeDescription );
            }
    }

    protected void fireIgnoreField( final Field f, final String k, final EventCause cause, final String causeDescription )
    {
        for( final AutoI18nEventHandler eventHandler : this.eventHandlerList ) {
            eventHandler.ignoredField( f, k, cause, causeDescription );
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
        return this.config;
    }

    /**
     * @return a {@link AutoI18nTypeLookup} that look only in default supported types.
     */
    public AutoI18nTypeLookup getDefaultAutoI18nTypes()
    {
        return this.defaultTypes;
    }

    public I18nInterface getI18nInterface(final Locale locale)
    {
        return this.i18nInterface;
    }

    public void handleI18nSyntaxeException( final I18nSyntaxeException e, final Field field )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleI18nSyntaxeException( e, field );
            }
    }

    public void handleIllegalAccessException(
        final IllegalAccessException e,
        final I18nField              i18nField
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleIllegalAccessException( e, i18nField );
            }
    }

    public void handleIllegalArgumentException(
        final IllegalArgumentException e,
        final I18nField                i18nField
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleIllegalArgumentException( e, i18nField );
            }
    }

    public void handleInvocationTargetException(
        final InvocationTargetException e,
        final I18nField                 i18nField
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleInvocationTargetException( e, i18nField );
            }
    }

    public void handleMissingKeyException(
            final MissingKeyException   e,
            final I18nField             i18nField,
            final I18nResolver          i18nResolver
            )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleMissingKeyException( e, i18nField, i18nResolver );
            }
    }

    public <T> void handleMissingResourceException(
        final MissingResourceException e,
        final I18nField                field,
        final T                        objectToI18n,
        final I18nInterface            i18nInterface
       )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleMissingResourceException( e, field, objectToI18n, i18nInterface );
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
        final NoSuchMethodException e,
        final I18nField             i18nField
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleNoSuchMethodException( e, i18nField );
            }
    }

    public void handleSecurityException(
        final MethodProviderSecurityException e,
        final Field                           field
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleSecurityException( e, field );
            }
    }

    public void handleSecurityException(
        final SecurityException e,
        final I18nField         i18nField
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleSecurityException( e, i18nField );
            }
    }

    public void handleSetFieldException(
        final SetFieldException e,
        final I18nField         i18nField,
        final I18nResolver      i18nResolver
        )
    {
        for( final AutoI18nExceptionHandler exceptionHandler : this.exceptionHandlerList ) {
            exceptionHandler.handleSetFieldException( e, i18nField, i18nResolver );
            }
    }
}
