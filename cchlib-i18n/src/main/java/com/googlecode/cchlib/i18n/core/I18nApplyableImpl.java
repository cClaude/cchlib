package com.googlecode.cchlib.i18n.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.I18nInterface;
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
    private static final Logger LOGGER = Logger.getLogger( I18nApplyableImpl.class );

    private final I18nClass<T>  i18nClass;
    private final I18nDelegator i18nDelegator;

    public I18nApplyableImpl( final I18nClass<T> i18nClass, final I18nDelegator i18nDelegator )
    {
        this.i18nClass     = i18nClass;
        this.i18nDelegator = i18nDelegator;
    }

    @Override
    public void performeI18n( final T objectToI18n, final Locale locale )
    {
        if( objectToI18n == null ) {
            throw new NullPointerException( "objectToI18n is null" );
            }

        final CurrentObject co = new CurrentObject( objectToI18n, i18nDelegator.getI18nInterface( locale ) );

        for( final I18nField i18nField : i18nClass ) {
            co.performeI18n( i18nField );
            }
    }

    private class CurrentObject
    {
        private final T             objectToI18n;
        private final I18nInterface i18nInterface;

        public CurrentObject( final T objectToI18n, final I18nInterface i18nInterface )
        {
            this.objectToI18n  = objectToI18n;
            this.i18nInterface = i18nInterface;
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
            final I18nResolver resolver = i18nField.createI18nResolver( objectToI18n, i18nInterface );

            try {
                final Keys keys = resolver.getKeys();
                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "keys = " + keys + " for " + i18nField );
                    }

                final Values values = new ValuesFromKeys( i18nInterface, keys );
                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "values = " + values );
                    }

                try {
                    if( LOGGER.isTraceEnabled() ) {
                        LOGGER.trace( "I18nResolver.getI18nResolvedFieldSetter() = " + resolver.getI18nResolvedFieldSetter() );
                        }
                    resolver.getI18nResolvedFieldSetter().setValues( keys, values );
                    }
                catch( final SetFieldException e ) {
                    i18nDelegator.handleSetFieldException( e, i18nField, resolver );
                    }
                }
            catch( final MissingResourceException e ) {
                i18nDelegator.handleMissingResourceException( e, i18nField, objectToI18n, i18nInterface );
                }
            catch( final MissingKeyException e ) {
                i18nDelegator.handleMissingKeyException( e, i18nField, resolver );
                }
        }

        private void invokeOnObjectToI18n( final I18nField field  )
        {
            try {
                final MethodContener methodContener    = field.getMethodContener();
                final Method         invoker           = methodContener.getMethod();

                try { // $codepro.audit.disable emptyFinallyClause
                    invoker.invoke( objectToI18n, Objects.emptyArray() );
                    }
                catch( final IllegalArgumentException e ) {
                    i18nDelegator.handleIllegalArgumentException( e, field );
                    }
                catch( final IllegalAccessException e ) {
                    i18nDelegator.handleIllegalAccessException( e, field );
                    }
                catch( final InvocationTargetException e ) {
                    i18nDelegator.handleInvocationTargetException( e, field );
                    }
                }
            catch( final SecurityException e ) {
                i18nDelegator.handleSecurityException( e, field );
                }
            catch( final NoSuchMethodException e ) {
                i18nDelegator.handleNoSuchMethodException( e, field );
                }
        }
    }
}
