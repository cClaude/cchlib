package com.googlecode.cchlib.i18n.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.googlecode.cchlib.i18n.AutoI18nConfig;

/**
 *
 */
//NOT public
class AutoI18nCoreImpl implements AutoI18nCore, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( AutoI18nCoreImpl.class );

    private final Map<Class<?>,I18nClass<?>> map = new HashMap<>();
    /** @serial */
    private final I18nDelegator i18nDelegator;
    /** @serial */
    private final Locale locale;

    /**
     *
     */
    public AutoI18nCoreImpl( final I18nDelegator i18nDelegator )
    {
        this.i18nDelegator = i18nDelegator;
        this.locale        = Locale.getDefault();
    }

    @Override
    public <T> void performeI18n( final T objectToI18n, final Class<? extends T> clazz )
    {
        assert objectToI18n != null : "Object to I18n is null";
        assert clazz != null : "Class of object to I18n is null";

        if( this.i18nDelegator.getConfig().contains( AutoI18nConfig.DISABLE ) ) {
            // Internalization is disabled.
            return;
            }

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "I18n handle class " + clazz + " on " + objectToI18n );
            }

        @SuppressWarnings("unchecked")
        I18nClass<T> i18nClass = (I18nClass<T>)this.map.get( clazz ); // $codepro.audit.disable unnecessaryCast

        if( i18nClass == null ) {
            i18nClass = new I18nClassImpl<>( clazz, this.i18nDelegator );

            this.map.put( clazz, i18nClass );
            }

        final I18nApplyable<T> apply = new I18nApplyableImpl<>( i18nClass, this.i18nDelegator );

        apply.performeI18n( objectToI18n, locale );
    }
}
