package com.googlecode.cchlib.i18n.core;

import com.googlecode.cchlib.i18n.I18nInterface;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.Keys;
import com.googlecode.cchlib.i18n.core.resolve.MissingKeyException;
import com.googlecode.cchlib.i18n.core.resolve.SetFieldException;
import com.googlecode.cchlib.i18n.core.resolve.Values;
import com.googlecode.cchlib.i18n.core.resolve.ValuesFromKeys;
import com.googlecode.cchlib.i18n.resources.MissingResourceException;
import com.googlecode.cchlib.lang.Objects;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import org.apache.log4j.Logger;

/*not public*/ class I18nApplyableImpl<T> implements I18nApplyable<T>
{
    private static final Logger LOGGER = Logger.getLogger( I18nApplyableImpl.class );
    
    private final I18nClass<T> i18nClass;
    private final I18nDelegator i18nDelegator;

    public I18nApplyableImpl( I18nClass<T> i18nClass, I18nDelegator i18nDelegator )
    {
        this.i18nClass     = i18nClass;
        this.i18nDelegator = i18nDelegator;
    }

    @Override
    public void performeI18n( T objectToI18n, Locale locale )
    {
        if( objectToI18n == null ) {
            throw new NullPointerException( "objectToI18n is null" );
            }

        CurrentObject co = new CurrentObject( objectToI18n, i18nDelegator.getI18nInterface( locale ) );

        for( I18nField field : i18nClass ) {
            co.performeI18n( field );
            }
    }

    private class CurrentObject
    {
        private final T             objectToI18n;
        private final I18nInterface i18nInterface;

        public CurrentObject( T objectToI18n, I18nInterface i18nInterface )
        {
            this.objectToI18n  = objectToI18n;
            this.i18nInterface = i18nInterface;
        }

        public void performeI18n( final I18nField field )
        {
            if( field.getMethods() != null ) {
                invokeOnObjectToI18n( field );
                }
            else {
                useResolverOn( field );
                }
         }

        private void useResolverOn( final I18nField field )
        {
            final I18nResolver resolver = field.createI18nResolver( objectToI18n, i18nInterface );

            try {
                Keys keys = resolver.getKeys();
                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "keys = " + keys + " for " + field );
                    }

                Values values = new ValuesFromKeys( i18nInterface, keys );
                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "values = " + values );
                    }

                try {
                    if( LOGGER.isTraceEnabled() ) {
                        LOGGER.trace( "I18nResolver.getI18nResolvedFieldSetter() = " + resolver.getI18nResolvedFieldSetter() );
                        }
                    resolver.getI18nResolvedFieldSetter().setValues( keys, values );
                    }
                catch( SetFieldException e ) {
                    i18nDelegator.handleSetFieldException( e, field, resolver );
                    }
                }
            catch( MissingResourceException e ) {
                i18nDelegator.handleMissingResourceException( e, field, objectToI18n, i18nInterface );
                }
            catch( MissingKeyException e ) {
                i18nDelegator.handleMissingKeyException( e, field, resolver );
                }
        }

        private void invokeOnObjectToI18n( final I18nField field  )
        {
            try {
                final MethodContener methodContener    = field.getMethods();
                final Method         invoker           = methodContener.getInvokeMethod();
//                final boolean accessible = setter.isAccessible();
//
//                if( ! accessible ) {
//                    setter.setAccessible( true );
//                    }

                try { // $codepro.audit.disable emptyFinallyClause
                    invoker.invoke( objectToI18n, Objects.emptyArray() );
                    }
                catch( IllegalArgumentException e ) {
                    i18nDelegator.handleIllegalArgumentException( e, field );
                    }
                catch( IllegalAccessException e ) {
                    i18nDelegator.handleIllegalAccessException( e, field );
                    }
                catch( InvocationTargetException e ) {
                    i18nDelegator.handleInvocationTargetException( e, field );
                    }
                finally {
//                    if( ! accessible ) {
//                        setter.setAccessible( false );
//                        }
                    }
                }
            catch( SecurityException e ) {
                i18nDelegator.handleSecurityException( e, field );
                }
            catch( NoSuchMethodException e ) {
                i18nDelegator.handleNoSuchMethodException( e, field );
                }
        }
    }
}
