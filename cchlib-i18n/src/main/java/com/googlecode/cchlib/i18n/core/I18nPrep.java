package com.googlecode.cchlib.i18n.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.AutoI18nEventHandler;
import com.googlecode.cchlib.i18n.AutoI18nExceptionHandler;
import com.googlecode.cchlib.i18n.AutoI18nTypeLookup;
import com.googlecode.cchlib.i18n.EventCause;
import com.googlecode.cchlib.i18n.I18nInterface;
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


public class I18nPrep
{
    private static final Logger LOGGER = Logger.getLogger( I18nPrep.class );

    private final EnumSet<AutoI18nConfig> config;
    private final AutoI18nTypeLookup      defaultAutoI18nTypes;
    private final I18nResourceBundle      i18nResourceBundle;
    private final I18nResourceBundleName  resourceBundleName;

    private final Map<String,Integer> keyUsageCountMap     = new HashMap<>();
    private final Map<String,String>  missingPropertiesMap = new HashMap<>();
    private AutoI18nCore        autoI18nCore;
    private I18nDelegator       i18nDelegator;

    private File resourceBundleOutputFile;

    public I18nPrep(
        final Set<AutoI18nConfig>     config,
        final AutoI18nTypeLookup      defaultAutoI18nTypes,
        final Locale                  locale,
        final I18nResourceBundleName  resourceBundleName
        )
    {
        this.config                 = EnumSet.copyOf( config );
        this.defaultAutoI18nTypes   = defaultAutoI18nTypes;
        this.resourceBundleName     = resourceBundleName;
        this.i18nResourceBundle     = new I18nSimpleResourceBundle( locale, resourceBundleName );
     }

    public I18nResourceBundleName getI18nResourceBundleName()
    {
        return this.resourceBundleName;
    }

    private I18nDelegator getI18nDelegator()
    {
        if( this.i18nDelegator == null ) {
            this.i18nDelegator = new I18nDelegator( this.config, this.defaultAutoI18nTypes, getI18nInterface() );
            }
        return this.i18nDelegator;
    }

    public AutoI18nCore getAutoI18nCore()
    {
        if( this.autoI18nCore == null ) {
            getI18nDelegator().addAutoI18nExceptionHandler(
                new AutoI18nLog4JExceptionHandler( Level.TRACE, this.config ) {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public void handleMissingResourceException(
                        final MissingResourceException cause,
                        final I18nField                i18nField,
                        final Object                   objectToI18n,
                        final I18nInterface            i18nInterface
                        )
                    {
                        assert i18nField.getMethodContener() == null;

                        try {
                            final I18nResolver resolver = i18nField.createI18nResolver( objectToI18n, i18nInterface );
                            final Keys         keys     = resolver.getKeys();

                            final I18nResolvedFieldGetter getter = resolver.getI18nResolvedFieldGetter();
                            final Values values = getter.getValues( keys );

                            assert keys.size() == values.size();
                            assert keys.size() > 0;

                            for( int i = 0; i<keys.size(); i++ ) {
                                final String k = keys.get( i );
                                final String v = values.get( i );

                                I18nPrep.this.missingPropertiesMap.put( k, v );
                                }
                            }
                        catch( MissingKeyException | GetFieldException e ) {
                            // TODO Auto-generated catch block : improve this
                            e.printStackTrace();
                             }

                        super.handleMissingResourceException( cause, i18nField, objectToI18n, i18nInterface );
                    }
                } );
            getI18nDelegator().addAutoI18nEventHandler( new AutoI18nLog4JEventHandler() );
            getI18nDelegator().addAutoI18nEventHandler( new AutoI18nEventHandler() {
                private static final long serialVersionUID = 1L;
                @Override
                public void ignoredField( final Field f, final String key, final EventCause eventCause, final String causeDecription )
                {
                    if( LOGGER.isTraceEnabled() ) {
                        LOGGER.trace( "I18nPrep.ignoredField: " + key + " - field: " + f );
                        }
                    // incForKey( key ); // not use, should not increment value !
                }
                @Override
                public void localizedField( final Field f, final String key )
                {
                    if( LOGGER.isDebugEnabled() ) {
                        LOGGER.debug( "I18nPrep.localizedField: " + key );
                        }
                    incForKey( key );
                }} );

            this.autoI18nCore = new AutoI18nCoreImpl( this.i18nDelegator );
            }

        return this.autoI18nCore;
    }

    public I18nResourceBundle getI18nResourceBundle()
    {
        return this.i18nResourceBundle;
    }

    private I18nInterface getI18nInterface()
    {
        return this.i18nResourceBundle;
    }

    public ResourceBundle getResourceBundle()
    {
        return this.i18nResourceBundle.getResourceBundle();
    }

    public void addAutoI18nEventHandler( final AutoI18nEventHandler eventHandler )
    {
        getI18nDelegator().addAutoI18nEventHandler( eventHandler );
    }

    public void addAutoI18nExceptionHandler( final AutoI18nExceptionHandler exceptionHandler )
    {
        getI18nDelegator().addAutoI18nExceptionHandler( exceptionHandler );
    }

    public Map<String,String> getResourceBundleMap()
    {
        final ResourceBundle      rb  = getResourceBundle();
        final Map<String,String>  map = new HashMap<>();
        final Enumeration<String> enu = rb.getKeys();

        while( enu.hasMoreElements() ) {
            final String key   = enu.nextElement();
            final String value = rb.getString( key );

            map.put( key, value );
            }

        return map;
    }

    public void openOutputFile( final File outputFile )
    {
        this.resourceBundleOutputFile = outputFile;
    }

    public void closeOutputFile() throws IOException
    {
        final Properties properties = new Properties();

        properties.putAll( this.missingPropertiesMap );

        LOGGER.info( "closeOutputFile(): found (key,value) count  = " + this.keyUsageCountMap.size() );
        LOGGER.info( "closeOutputFile(): know (key,value) count  = " + getResourceBundleMap().size() );
        LOGGER.info( "closeOutputFile(): unknow (key,value) count = " + properties.size() );

        //properties.putAll( getProperties() );
        try( final OutputStream os = getResourceBundleOutputStream() ) {
            // Create by this class
            properties.store( os, "Create by :" + getClass().getName() );
        }
        catch( final IOException ioe ) {
            LOGGER.warn( "Can't open resource bundle for writing !", ioe );

            throw ioe;
        }
    }

    private OutputStream getResourceBundleOutputStream() throws FileNotFoundException
    {
        return new FileOutputStream( this.resourceBundleOutputFile );
    }

    private void incForKey( final String key )
    {
        assert key != null : "Key is null";

        final Integer countInteger = this.keyUsageCountMap.get( key );
        final int     count;

        if( countInteger == null ) {
            count = 1;
            }
        else {
            count = countInteger.intValue() + 1;
            }

        this.keyUsageCountMap.put( key, Integer.valueOf( count ) );
    }

    public Map<String,Integer> getUsageMap()
    {
        return Collections.unmodifiableMap( this.keyUsageCountMap );
    }
}
