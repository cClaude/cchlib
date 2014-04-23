package com.googlecode.cchlib.i18n.core;

<<<<<<< HEAD
=======
import com.googlecode.cchlib.i18n.AutoI18nConfig;
>>>>>>> cchlib-pre4-1-8
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.log4j.Logger;
<<<<<<< HEAD
import com.googlecode.cchlib.i18n.AutoI18nConfig;
=======
>>>>>>> cchlib-pre4-1-8

/**
 *
 */
//NOT public
class AutoI18nCoreImpl implements AutoI18nCore, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( AutoI18nCoreImpl.class );

<<<<<<< HEAD
    private Map<Class<?>,I18nClass<?>> map = new HashMap<Class<?>,I18nClass<?>>();
    /** @serial */
    private I18nDelegator i18nDelegator;
    /** @serial */
    private Locale locale;
=======
    private final Map<Class<?>,I18nClass<?>> map = new HashMap<>();
    /** @serial */
    private final I18nDelegator i18nDelegator;
    /** @serial */
    private final Locale locale;
>>>>>>> cchlib-pre4-1-8

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
<<<<<<< HEAD
            i18nClass = new I18nClassImpl<T>( clazz, this.i18nDelegator );
=======
            i18nClass = new I18nClassImpl<>( clazz, this.i18nDelegator );
>>>>>>> cchlib-pre4-1-8

            this.map.put( clazz, i18nClass );
            }

<<<<<<< HEAD
        final I18nApplyable<T> apply = new I18nApplyableImpl<T>( i18nClass, this.i18nDelegator );
=======
        final I18nApplyable<T> apply = new I18nApplyableImpl<>( i18nClass, this.i18nDelegator );
>>>>>>> cchlib-pre4-1-8

        apply.performeI18n( objectToI18n, locale );
    }
}
