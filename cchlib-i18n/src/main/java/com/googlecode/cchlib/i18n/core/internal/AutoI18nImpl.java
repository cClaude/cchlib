package com.googlecode.cchlib.i18n.core.internal;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18n;
import com.googlecode.cchlib.i18n.core.AutoI18nConfigSet;
import com.googlecode.cchlib.i18n.core.I18nApplyable;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilder;

public class AutoI18nImpl implements AutoI18n, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( AutoI18nImpl.class );

    private final Map<Class<?>,I18nClass<?>> map = new HashMap<>();
    private final I18nDelegator              i18nDelegator;

    public AutoI18nImpl( final I18nDelegator i18nDelegator )
    {
        this.i18nDelegator = i18nDelegator;
    }

    public AutoI18nConfigSet getConfig()
    {
        return this.i18nDelegator.getConfig();
    }

    /**
     * Returns the {@link I18nDelegator} for {@link I18nResourceBuilder} process
     * @return the {@link I18nDelegator}
     */
    public I18nDelegator getI18nDelegator()
    {
        return this.i18nDelegator;
    }

    @Override
    public <T> void performeI18n(
        @Nonnull final T                  objectToI18n,
        @Nonnull final Class<? extends T> clazz
        )
    {
        assert objectToI18n != null : "Object to I18n is null";
        assert clazz        != null : "Class of object to I18n is null";

        if( getConfig().getSafeConfig().contains( AutoI18nConfig.DISABLE ) ) {
            // Internalization is disabled.
            return;
            }

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "I18n found class \"" + clazz.getName() + "\" for " + objectToI18n );
            }

        final I18nClass<T>     i18nClass = getI18nClass( clazz );
        final I18nApplyable<T> applyer   = new I18nApplyableImpl<>( i18nClass, this.i18nDelegator );

        applyer.performeI18n( objectToI18n );

        handleAutoUpdatableField( objectToI18n, i18nClass );
    }

    private <T> void handleAutoUpdatableField( final T objectToI18n, final I18nClass<T> i18nClass )
    {
        for( final Field autoUpdatableField : i18nClass.getAutoUpdatableFields() ) {
            try {
                autoUpdatableField.setAccessible( true ); // TODO find a way to restore state.
                final I18nAutoUpdatable autoUpdatable = (I18nAutoUpdatable)autoUpdatableField.get( objectToI18n );

                if( autoUpdatable != null ) {
                    autoUpdatable.performeI18n( this );
                } else {
                    this.i18nDelegator.handleI18nNullPointer(
                            I18nFieldFactory.newI18nField( autoUpdatableField )
                            );
                }
            }
            catch( final IllegalArgumentException cause ) {
                this.i18nDelegator.handleIllegalArgumentException(
                        cause,
                        I18nFieldFactory.newI18nField( autoUpdatableField )
                        );
            }
            catch( final IllegalAccessException cause ) {
                this.i18nDelegator.handleIllegalAccessException(
                        cause,
                        I18nFieldFactory.newI18nField( autoUpdatableField )
                        );
            }
        }
    }

    private <T> I18nClass<T> getI18nClass( final Class<? extends T> clazz )
    {
        @SuppressWarnings("unchecked")
        I18nClass<T> i18nClass = (I18nClass<T>)this.map.get( clazz );

        if( i18nClass == null ) {
            i18nClass = new I18nClassImpl<>( clazz, this.i18nDelegator );

            this.map.put( clazz, i18nClass );
            }

        return i18nClass;
    }

    @Override
    public String toString()
    {
        return "AutoI18nImpl [map=" + this.map
            + ", i18nDelegator=" + this.i18nDelegator
            + "]";
    }
}
