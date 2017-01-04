package com.googlecode.cchlib.i18n.core.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.core.I18nApplyable;
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

//NOT public
class I18nApplyableImpl<T> implements I18nApplyable<T>
{
    private class CurrentObjectInvoker
    {
        private final T            objectToI18n;
        private final I18nResource i18nResource;

        public CurrentObjectInvoker( final T objectToI18n, final I18nResource i18nResource )
        {
            this.objectToI18n = objectToI18n;
            this.i18nResource = i18nResource;
        }

        public void performeI18n( final I18nField i18nField )
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
            final I18nResolver resolver
                = i18nField.createI18nResolver( this.objectToI18n, this.i18nResource );

            try {
                final Keys keys = resolver.getKeys();

                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "keys = " + keys + " for " + i18nField );
                    }

                final Values values = new ValuesFromKeys( this.i18nResource, keys );

                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "values = " + values );
                    }

                I18nApplyableImpl.this.i18nDelegator.fireLocalizedField( i18nField, keys );

                useResolverOn( i18nField, resolver, keys, values );
                }
            catch( final MissingResourceException cause ) {
                I18nApplyableImpl.this.i18nDelegator.handleMissingResourceException( cause, i18nField, this.objectToI18n, this.i18nResource );
                }
            catch( final MissingKeyException cause ) {
                I18nApplyableImpl.this.i18nDelegator.handleMissingKeyException( cause, i18nField, resolver );
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
                I18nApplyableImpl.this.i18nDelegator.handleSetFieldException(
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
            catch( final SecurityException e ) {
                I18nApplyableImpl.this.i18nDelegator.handleSecurityException( e, field );
            }
            catch( final NoSuchMethodException e ) {
                I18nApplyableImpl.this.i18nDelegator.handleNoSuchMethodException( e, field );
            }
        }

        private void invokeOnObjectToI18n( final I18nField field, final Method invoker )
        {
            try {
                invoker.invoke( this.objectToI18n, Objects.emptyArray() );
            }
            catch( final IllegalArgumentException e ) {
                I18nApplyableImpl.this.i18nDelegator.handleIllegalArgumentException( e, field );
            }
            catch( final IllegalAccessException e ) {
                I18nApplyableImpl.this.i18nDelegator.handleIllegalAccessException( e, field );
            }
            catch( final InvocationTargetException e ) {
                I18nApplyableImpl.this.i18nDelegator.handleInvocationTargetException( e, field );
            }
        }
    }

    private static final Logger LOGGER = Logger.getLogger( I18nApplyableImpl.class );

    private final I18nClass<T>  i18nClass;
    private final I18nDelegator i18nDelegator;

    public I18nApplyableImpl( final I18nClass<T> i18nClass, final I18nDelegator i18nDelegator )
    {
        this.i18nClass     = i18nClass;
        this.i18nDelegator = i18nDelegator;
    }

    @Override
    public void performeI18n( final T objectToI18n )
    {
        if( objectToI18n == null ) {
            throw new NullPointerException( "objectToI18n is null" );
            }

        final CurrentObjectInvoker co = new CurrentObjectInvoker(
                objectToI18n,
                this.i18nDelegator.getI18nResource()
                );

        for( final I18nField i18nField : this.i18nClass ) {
            co.performeI18n( i18nField );
            }
    }
}
