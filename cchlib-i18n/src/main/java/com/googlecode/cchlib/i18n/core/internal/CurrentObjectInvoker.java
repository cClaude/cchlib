package com.googlecode.cchlib.i18n.core.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.annotation.Nonnull;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.core.I18nField;
import com.googlecode.cchlib.i18n.core.MethodContener;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.Keys;
import com.googlecode.cchlib.i18n.core.resolve.MissingKeyException;
import com.googlecode.cchlib.i18n.core.resolve.SetFieldException;
import com.googlecode.cchlib.i18n.core.resolve.Values;
import com.googlecode.cchlib.i18n.core.resolve.ValuesFromKeys;
import com.googlecode.cchlib.i18n.resources.MissingResourceException;
import com.googlecode.cchlib.lang.Objects;

class CurrentObjectInvoker<T>
{
    private static final Logger LOGGER = Logger.getLogger( CurrentObjectInvoker.class );

    private final T             objectToI18n;
    private final I18nDelegator i18nDelegator;

    public CurrentObjectInvoker(
        @Nonnull final T             objectToI18n,
        @Nonnull final I18nDelegator i18nDelegator
        )
    {
        if( objectToI18n == null ) {
            throw new NullPointerException( "objectToI18n is null" );
            }

        this.objectToI18n  = objectToI18n;
        this.i18nDelegator = i18nDelegator;
    }

    private void performeI18n( final I18nField i18nField )
    {
        if( i18nField.getMethodContener() != null ) {
            invokeOnObjectToI18n( i18nField );
            }
        else {
            useResolverOn( i18nField );
            }
     }

    private void useResolverOn( final I18nField i18nField )
    {
        final I18nResource i18nResource = this.i18nDelegator.getI18nResource();
        final I18nResolver resolver
            = i18nField.createI18nResolver( this.objectToI18n, i18nResource );

        try {
            final Keys keys = resolver.getKeys();

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "keys = " + keys + " for " + i18nField );
                }

            final Values values = new ValuesFromKeys( i18nResource, keys );

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "values = " + values );
                }

            this.i18nDelegator.fireLocalizedField( i18nField, keys );

            useResolverOn( i18nField, resolver, keys, values );
            }
        catch( final MissingResourceException cause ) {
            this.i18nDelegator.handleMissingResourceException(
                    cause,
                    i18nField,
                    this.objectToI18n,
                    i18nResource
                    );
            }
        catch( final MissingKeyException cause ) {
            this.i18nDelegator.handleMissingKeyException( cause, i18nField, resolver );
            }
    }

    private void useResolverOn(
        final I18nField    i18nField,
        final I18nResolver resolver,
        final Keys         keys,
        final Values       values
        )
    {
        try {
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace(
                    "I18nResolver.getI18nResolvedFieldSetter() = "
                        + resolver.getI18nResolvedFieldSetter()
                    );
                }

            resolver.getI18nResolvedFieldSetter().setValues( keys, values );
            }
        catch( final SetFieldException cause ) {
            this.i18nDelegator.handleSetFieldException(
                cause,
                i18nField,
                resolver
                );
            }
    }

    private void invokeOnObjectToI18n( final I18nField field  )
    {
        try {
            final MethodContener methodContener = field.getMethodContener();
            final Method         invoker        = methodContener.getMethod();

            invokeOnObjectToI18n( field, invoker );
        }
        catch( final SecurityException cause ) {
            this.i18nDelegator.handleSecurityException( cause, field );
        }
        catch( final NoSuchMethodException cause ) {
            this.i18nDelegator.handleNoSuchMethodException( cause, field );
        }
    }

    private void invokeOnObjectToI18n( final I18nField field, final Method invoker )
    {
        try {
            invoker.invoke( this.objectToI18n, Objects.emptyArray() );
        }
        catch( final IllegalArgumentException cause ) {
            this.i18nDelegator.handleIllegalArgumentException( cause, field );
        }
        catch( final IllegalAccessException cause ) {
            this.i18nDelegator.handleIllegalAccessException( cause, field );
        }
        catch( final InvocationTargetException cause ) {
            this.i18nDelegator.handleInvocationTargetException( cause, field );
        }
    }

    public void performeI18n( final I18nClass<T> i18nClass )
    {
        for( final I18nField i18nField : i18nClass ) {
            performeI18n( i18nField );
            }
    }
}
