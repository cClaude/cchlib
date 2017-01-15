package com.googlecode.cchlib.i18n.core.internal;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18nConfigSet;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilder;
import com.googlecode.cchlib.lang.reflect.AccessibleRestorer;

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

        final I18nClass<T>            i18nClass            = getI18nClass( clazz );
        final CurrentObjectInvoker<T> currentObjectInvoker = new CurrentObjectInvoker<>(
                objectToI18n,
                this.i18nDelegator
                );
        currentObjectInvoker.performeI18n( i18nClass );

        handleAutoUpdatableField( objectToI18n, i18nClass );
    }

    private <T> void handleAutoUpdatableField( final T objectToI18n, final I18nClass<T> i18nClass )
    {
        for( final Field autoUpdatableField : i18nClass.getAutoUpdatableFields() ) {
            handleAutoUpdatableField( objectToI18n, autoUpdatableField );
            }
    }

    private <T> void handleAutoUpdatableField( final T objectToI18n, final Field autoUpdatableField )
    {
        final AccessibleRestorer accessible = new AccessibleRestorer( autoUpdatableField );

        try {
            final I18nAutoUpdatable autoUpdatable =
                 (I18nAutoUpdatable)autoUpdatableField.get( objectToI18n );

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
        finally {
            accessible.restore();
        }
    }

    private <T> I18nClass<T> getI18nClass( final Class<? extends T> clazz )
    {
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Know class: " + this.map.keySet() );
        }

        @SuppressWarnings("unchecked")
        I18nClass<T> i18nClass = (I18nClass<T>)this.map.get( clazz );

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Class: " + clazz + " is " + (i18nClass == null ? "new" : "already in cache" ) );
        }

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
