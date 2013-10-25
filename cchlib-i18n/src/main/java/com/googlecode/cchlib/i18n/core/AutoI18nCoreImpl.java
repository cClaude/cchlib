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
    private static final Logger logger = Logger.getLogger( AutoI18nCoreImpl.class );

    private Map<Class<?>,I18nClass<?>> map = new HashMap<Class<?>,I18nClass<?>>();
    /** @serial */
    private I18nDelegator i18nDelegator;
    /** @serial */
    private Locale locale;

    /**
     *
     */
    public AutoI18nCoreImpl( final I18nDelegator i18nDelegator )
    {
        this.i18nDelegator = i18nDelegator;
        this.locale        = Locale.getDefault();
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.AutoI18n#performeI18n(java.lang.Object, java.lang.Class)
     */
    @Override
    public <T> void performeI18n( T objectToI18n, Class<? extends T> clazz )
    {
        if( this.i18nDelegator.getConfig().contains( AutoI18nConfig.DISABLE ) ) {
            // Internalization is disabled.
            return;
            }

        if( logger.isDebugEnabled() ) {
            logger.debug( "I18n handle class " + clazz + " on " + objectToI18n );
            }

        @SuppressWarnings("unchecked")
        I18nClass<T> i18nClass = (I18nClass<T>)this.map.get( clazz ); // $codepro.audit.disable unnecessaryCast

        if( i18nClass == null ) {
            i18nClass = new I18nClassImpl<T>( clazz, this.i18nDelegator );

            this.map.put( clazz, i18nClass );
            }

        I18nApplyable<T> apply = new I18nApplyableImpl<T>( i18nClass, this.i18nDelegator );

        apply.performeI18n( objectToI18n, locale );
    }
}
