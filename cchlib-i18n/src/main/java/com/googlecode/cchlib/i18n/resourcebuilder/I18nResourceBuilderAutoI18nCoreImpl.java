package com.googlecode.cchlib.i18n.resourcebuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.AutoI18nEventHandler;
import com.googlecode.cchlib.i18n.AutoI18nExceptionHandler;
import com.googlecode.cchlib.i18n.EventCause;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.core.AutoI18nConfigSet;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.core.I18nField;
import com.googlecode.cchlib.i18n.core.internal.AbstractAutoI18nExceptionHandler;
import com.googlecode.cchlib.i18n.core.internal.AutoI18nCoreImpl;
import com.googlecode.cchlib.i18n.core.internal.I18nDelegator;
import com.googlecode.cchlib.i18n.core.resolve.GetFieldException;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolvedFieldGetter;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.Keys;
import com.googlecode.cchlib.i18n.core.resolve.MissingKeyException;
import com.googlecode.cchlib.i18n.core.resolve.Values;
import com.googlecode.cchlib.i18n.logging.AutoI18nLog4JEventHandler;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundle;
import com.googlecode.cchlib.i18n.resources.MissingResourceException;
import com.googlecode.cchlib.util.EnumHelper;

final class I18nResourceBuilderAutoI18nCoreImpl
    extends AbstractAutoI18nExceptionHandler // for AutoI18nExceptionHandler
        implements I18nResourceBuilder,
                   AutoI18nEventHandler,
                   AutoI18nExceptionHandler
{
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger( I18nResourceBuilderAutoI18nCoreImpl.class );

    private final AutoI18nConfigSet config;
    private final AutoI18nCoreImpl  builderAutoI18nCore;
    private final I18nResource            i18nResource;
    private final ResourceBundle    resourceBundle;

    private final I18nResourceBuilderResultImpl result = new I18nResourceBuilderResultImpl();


    public I18nResourceBuilderAutoI18nCoreImpl(
        @Nonnull final AutoI18nCoreImpl originalAutoI18nCore,
        @Nullable final Locale          locale,
        @Nonnull final AutoI18nConfig[] configExtension
        )
    {
        this.config               = newAutoI18nConfigSet( originalAutoI18nCore, configExtension );
        this.builderAutoI18nCore  = newBuilderAutoI18nCore(
                                        originalAutoI18nCore,
                                        this.config
                                        );

        this.i18nResource = originalAutoI18nCore.getI18nDelegator().getI18nResource();

        if( ! (this.i18nResource instanceof I18nResourceBundle) ) {
            throw new UnsupportedOperationException(
                    "Dont kwnon how to handle: " + this.i18nResource.getClass()
                        + " expected " + I18nResourceBundle.class
                    );
        }

        final I18nResourceBundle i18nResourceBundle = (I18nResourceBundle)this.i18nResource;

        this.resourceBundle = i18nResourceBundle.getResourceBundle();

        if( locale != null ) {
            Locale.setDefault( locale );
        }
   }

    private static AutoI18nConfigSet newAutoI18nConfigSet(
        final AutoI18nCoreImpl originalAutoI18nCore,
        final AutoI18nConfig[] configExtension
        )
    {
        final EnumSet<AutoI18nConfig> configCopy = EnumHelper.safeCopyOf(
                originalAutoI18nCore.getConfig().getSafeConfig(),
                AutoI18nConfig.class
                );
        final EnumSet<AutoI18nConfig> extension = EnumHelper.safeCopyOf(
                configExtension,
                AutoI18nConfig.class
                );

        configCopy.addAll( extension );

        return new AutoI18nConfigSet( configCopy );
    }

    private AutoI18nCoreImpl newBuilderAutoI18nCore(
        final AutoI18nCoreImpl  originalAutoI18nCore,
        final AutoI18nConfigSet autoI18nConfigSet
        )
    {
        final I18nDelegator originalDelegator = originalAutoI18nCore.getI18nDelegator();
        final I18nDelegator delegator         = new I18nDelegator(
                autoI18nConfigSet,
                originalDelegator.getAutoI18nTypeLookup(),
                originalDelegator.getI18nResource()
                );

        delegator.addAutoI18nExceptionHandler( this );

        delegator.addAutoI18nEventHandler( new AutoI18nLog4JEventHandler() );
        delegator.addAutoI18nEventHandler( this );

        return new AutoI18nCoreImpl( delegator );
    }

    @Override // I18nResourceBuilder
    @SuppressWarnings("ucd") // API
    public void append( final I18nAutoUpdatable i18nContener )
    {
        i18nContener.performeI18n( this.builderAutoI18nCore );
    }

    @Override // I18nResourceBuilder
    @SuppressWarnings("ucd") // API
    public I18nResourceBuilderResult getResult()
    {
        this.result.computeUnused( this.resourceBundle.keySet(), this.i18nResource );

        return this.result;
    }

    @Override // I18nResourceBuilder
    @SuppressWarnings("ucd") // API
    public void saveMissingResourceBundle( final File outputFile ) throws IOException
    {
        try( final Writer os = new BufferedWriter( new FileWriter( outputFile ) ) ) {
            saveMissingResourceBundle( os, outputFile.toString() );
        }

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "saveMissingResourceBundle: " + outputFile );
        }
    }

    @Override // I18nResourceBuilder
    public void saveMissingResourceBundle( final Writer writer, final String comments )
        throws IOException
    {
        final Map<String,String> missingKeyValues = this.result.getMissingKeyValues();
        final Properties         properties       = new Properties();

        properties.putAll( missingKeyValues );
        properties.store( writer, comments );
    }

    @Override // AutoI18nEventHandler
    public void localizedField( final Field field, final String key )
    {
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "localizedField key =\"" + key + "\" : " + field );
            }

        this.result.addLocalizedField( key );
    }

    @Override // AutoI18nEventHandler
    public void ignoredField(
        final Field      field,
        final String     key,
        final EventCause eventCause,
        final String     causeDecription
        )
    {
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "ignoredField: " + key + " - field: " + field );
            }

        final String noNullKey;

        if( key == null ) {
            noNullKey = field.toGenericString();
        } else {
            noNullKey = key;
        }

        this.result.addIgnoredField( noNullKey );
    }

    @Override // AutoI18nExceptionHandler
    protected void doHandle( final String msg, final Throwable cause )
    {
        if( this.config.isPrintStacktraceInLogs() ) {
            LOGGER.log( Level.DEBUG, msg, cause );
            }
        else {
            LOGGER.log( Level.DEBUG, msg + " : " + cause.getMessage() );
            }
    }

    @Override
    @SuppressWarnings("squid:S3346") // assert not produce side effects
    public void handleMissingResourceException( // AutoI18nExceptionHandler
        final MissingResourceException cause,
        final I18nField                i18nField,
        final Object                   objectToI18n,
        final I18nResource             i18nResource
        )
    {
        assert i18nField.getMethodContener() == null;

        try {
            // TODO investigate : i18nResource seems never use there, and there is
            // no reason to keep this information.
            // We already know that related key is missing.
            final I18nResolver resolver = i18nField.createI18nResolver( objectToI18n, i18nResource );
            final Keys         keys     = resolver.getKeys();

            final I18nResolvedFieldGetter getter = resolver.getI18nResolvedFieldGetter();
            final Values                  values = getter.getValues( keys );

            assert keys.size() == values.size();
            assert keys.size() > 0;

            for( int i = 0; i < keys.size(); i++ ) {
                final String key   = keys.get( i );
                final String value = values.get( i );

                this.result.addMissingProperties( key, value );
            }
        }
        catch( MissingKeyException | GetFieldException e ) {
            LOGGER.error( i18nField, e );
        }

        super.handleMissingResourceException( cause, i18nField, objectToI18n, i18nResource );
    }
}
