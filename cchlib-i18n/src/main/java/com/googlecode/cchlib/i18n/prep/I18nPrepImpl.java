package com.googlecode.cchlib.i18n.prep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.AutoI18nEventHandler;
import com.googlecode.cchlib.i18n.AutoI18nExceptionHandler;
import com.googlecode.cchlib.i18n.AutoI18nTypeLookup;
import com.googlecode.cchlib.i18n.EventCause;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.core.AutoI18nConfigSet;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nField;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.core.internal.AutoI18nCoreImpl;
import com.googlecode.cchlib.i18n.core.internal.I18nDelegator;
import com.googlecode.cchlib.i18n.core.resolve.GetFieldException;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolvedFieldGetter;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.Keys;
import com.googlecode.cchlib.i18n.core.resolve.MissingKeyException;
import com.googlecode.cchlib.i18n.core.resolve.Values;
import com.googlecode.cchlib.i18n.logging.AutoI18nLog4JEventHandler;
import com.googlecode.cchlib.i18n.logging.AutoI18nLog4JExceptionHandler;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundle;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nSimpleResourceBundle;
import com.googlecode.cchlib.i18n.resources.MissingResourceException;

/**
 *
 * @see I18nPrepFactory
 * @see I18nPrepHelper
 */
//Not public
class I18nPrepImpl implements I18nPrep
{
    private final class AutoI18nEventHandlerImpl implements AutoI18nEventHandler
    {
        private static final long serialVersionUID = 1L;

        @Override
        public void ignoredField(
            final Field      field,
            final String     key,
            final EventCause eventCause,
            final String     causeDecription
            )
        {
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "I18nPrep.ignoredField: " + key + " - field: " + field );
                }
            // note: incForKey( key ); - not use, should not increment value !
        }

        @Override
        public void localizedField( final Field f, final String key )
        {
            if( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "I18nPrep.localizedField: " + key );
                }

            incForKey( key );
        }

        private void incForKey( final String key )
        {
            assert key != null : "Key is null";

            final Integer countInteger = I18nPrepImpl.this.keyUsageCountMap.get( key );
            final int     count;

            if( countInteger == null ) {
                count = 1;
                }
            else {
                count = countInteger.intValue() + 1;
                }

            I18nPrepImpl.this.keyUsageCountMap.put( key, Integer.valueOf( count ) );
        }
    }

    private final class AutoI18nLog4JExceptionHandlerImpl extends AutoI18nLog4JExceptionHandler
    {
        private static final long serialVersionUID = 1L;

        private AutoI18nLog4JExceptionHandlerImpl(
            @Nullable final Level            level,
            @Nonnull final AutoI18nConfigSet config
            )
        {
            super( level, config );
        }

        @Override
        @SuppressWarnings("squid:S3346") // assert not produce side effects
        public void handleMissingResourceException(
            final MissingResourceException cause,
            final I18nField                i18nField,
            final Object                   objectToI18n,
            final I18nResource             i18nResource
            )
        {
            assert i18nField.getMethodContener() == null;

            try {
                // TODO investigate : i18nResource seems never use there
                final I18nResolver resolver = i18nField.createI18nResolver( objectToI18n, i18nResource );
                final Keys         keys     = resolver.getKeys();

                final I18nResolvedFieldGetter getter = resolver.getI18nResolvedFieldGetter();
                final Values                  values = getter.getValues( keys );

                assert keys.size() == values.size();
                assert keys.size() > 0;

                for( int i = 0; i < keys.size(); i++ ) {
                    final String k = keys.get( i );
                    final String v = values.get( i );

                    I18nPrepImpl.this.missingPropertiesMap.put( k, v );
                }
            }
            catch( MissingKeyException | GetFieldException e ) {
                LOGGER.error( i18nField, e );
            }

            super.handleMissingResourceException( cause, i18nField, objectToI18n, i18nResource );
        }
    }

    private static final Logger LOGGER = Logger.getLogger( I18nPrepImpl.class );

    private final AutoI18nConfigSet       config;
    private final AutoI18nTypeLookup      defaultAutoI18nTypes;
    private final I18nResourceBundle      i18nResourceBundle;
    private final I18nResourceBundleName  resourceBundleName;

    private final Map<String,Integer> keyUsageCountMap     = new HashMap<>();
    private final Map<String,String>  missingPropertiesMap = new HashMap<>();

    private AutoI18nCore       autoI18nCore;
    private I18nDelegator      i18nDelegator;
    private File               resourceBundleOutputFile;
    private I18nPrepStatResult i18nPrepStatResult;

    public I18nPrepImpl(
        final Set<AutoI18nConfig>     config,
        final AutoI18nTypeLookup      defaultAutoI18nTypes,
        final Locale                  locale,
        final I18nResourceBundleName  resourceBundleName
        )
    {
        this.config                 = new AutoI18nConfigSet( config );
        this.defaultAutoI18nTypes   = defaultAutoI18nTypes;
        this.resourceBundleName     = resourceBundleName;
        this.i18nResourceBundle     = new I18nSimpleResourceBundle( resourceBundleName, locale );
     }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.core.I18nPrepInterface#getI18nResourceBundleName()
     */
    @Override
    public I18nResourceBundleName getI18nResourceBundleName()
    {
        return this.resourceBundleName;
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.core.I18nPrepInterface#getI18nPrepStatResult()
     */
    @Override
    public I18nPrepStatResult getI18nPrepStatResult()
    {
        return this.i18nPrepStatResult;
    }

    private I18nDelegator getI18nDelegator()
    {
        if( this.i18nDelegator == null ) {
            this.i18nDelegator = new I18nDelegator( this.config, this.defaultAutoI18nTypes, getI18nInterface() );
            }

        return this.i18nDelegator;
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.core.I18nPrepInterface#getAutoI18nCore()
     */
    @Override
    public AutoI18nCore getAutoI18nCore()
    {
        if( this.autoI18nCore == null ) {
            final I18nDelegator delegator = getI18nDelegator();

            delegator.addAutoI18nExceptionHandler(
                new AutoI18nLog4JExceptionHandlerImpl( Level.TRACE, this.config )
                );
            delegator.addAutoI18nEventHandler( new AutoI18nLog4JEventHandler() );
            delegator.addAutoI18nEventHandler( new AutoI18nEventHandlerImpl() );

            this.autoI18nCore = new AutoI18nCoreImpl( delegator );
            }

        return this.autoI18nCore;
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.core.I18nPrepInterface#getI18nResourceBundle()
     */
    @Override
    public I18nResourceBundle getI18nResourceBundle()
    {
        return this.i18nResourceBundle;
    }

    private I18nResource getI18nInterface()
    {
        return this.i18nResourceBundle;
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.core.I18nPrepInterface#getResourceBundle()
     */
    @Override
    public ResourceBundle getResourceBundle()
    {
        return this.i18nResourceBundle.getResourceBundle();
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.core.I18nPrepInterface#addAutoI18nEventHandler(com.googlecode.cchlib.i18n.AutoI18nEventHandler)
     */
    @Override
    public void addAutoI18nEventHandler( final AutoI18nEventHandler eventHandler )
    {
        getI18nDelegator().addAutoI18nEventHandler( eventHandler );
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.core.I18nPrepInterface#addAutoI18nExceptionHandler(com.googlecode.cchlib.i18n.AutoI18nExceptionHandler)
     */
    @Override
    public void addAutoI18nExceptionHandler( final AutoI18nExceptionHandler exceptionHandler )
    {
        getI18nDelegator().addAutoI18nExceptionHandler( exceptionHandler );
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.core.I18nPrepInterface#getResourceBundleMap()
     */
    @Override
    public Map<String,String> getResourceBundleMap()
    {
        final ResourceBundle      rb   = getResourceBundle();
        final Map<String,String>  map  = new HashMap<>();
        final Enumeration<String> keys = rb.getKeys();

        while( keys.hasMoreElements() ) {
            final String key   = keys.nextElement();
            final String value = rb.getString( key );

            map.put( key, value );
            }

        return map;
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.core.I18nPrepInterface#openOutputFile(java.io.File)
     */
    @Override
    public void openOutputFile( final File outputFile )
    {
        this.resourceBundleOutputFile = outputFile;
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.core.I18nPrepInterface#closeOutputFile()
     */
    @Override
    public void closeOutputFile() throws IOException
    {
        final Properties properties = new Properties();

        properties.putAll( this.missingPropertiesMap );

        final int found  = this.keyUsageCountMap.size();
        final int know   = getResourceBundleMap().size();
        final int unknow = properties.size();

        this.i18nPrepStatResult = new I18nPrepStatResult() {
            @Override
            public int getFound()
            {
                return found;
            }

            @Override
            public int getKnow()
            {
                return know;
            }

            @Override
            public int getUnknow()
            {
                return unknow;
            }
        };

        LOGGER.info( "closeOutputFile(): found (key,value) count  = " + found );
        LOGGER.info( "closeOutputFile(): know (key,value) count  = " + know );
        LOGGER.info( "closeOutputFile(): unknow (key,value) count = " + unknow );

        try( final OutputStream os = newResourceBundleOutputStream() ) {
            // Create by this class
            properties.store( os, "Create by :" + getClass().getName() );
        }
        catch( final IOException ioe ) {
            LOGGER.warn( "Can't open resource bundle for writing !", ioe );

            throw ioe;
        }
    }

    private OutputStream newResourceBundleOutputStream() throws FileNotFoundException
    {
        return new FileOutputStream( this.resourceBundleOutputFile );
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.core.I18nPrepInterface#getUsageMap()
     */
    @Override
    public Map<String,Integer> getUsageMap()
    {
        return Collections.unmodifiableMap( this.keyUsageCountMap );
    }
}
