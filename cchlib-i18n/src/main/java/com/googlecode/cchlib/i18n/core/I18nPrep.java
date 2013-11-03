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

    private EnumSet<AutoI18nConfig> config;
    private AutoI18nTypeLookup      defaultAutoI18nTypes;
    private I18nResourceBundle      i18nResourceBundle;
    private I18nResourceBundleName  resourceBundleName;

    private Map<String,Integer> keyUsageCountMap     = new HashMap<String,Integer>();
    private Map<String,String>  missingPropertiesMap = new HashMap<String,String>();
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
        this.config                   = EnumSet.copyOf( config );
        this.defaultAutoI18nTypes     = defaultAutoI18nTypes;
        this.resourceBundleName       = resourceBundleName;
        this.i18nResourceBundle = new I18nSimpleResourceBundle( locale, resourceBundleName );
     }

    public I18nResourceBundleName getI18nResourceBundleName()
    {
        return this.resourceBundleName;
    }

    private I18nDelegator getI18nDelegator()
    {
        if( this.i18nDelegator == null ) {
            this.i18nDelegator = new I18nDelegator( config, defaultAutoI18nTypes, getI18nInterface() );
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
                        assert i18nField.getMethods() == null;

                        try {
                            I18nResolver resolver = i18nField.createI18nResolver( objectToI18n, i18nInterface );
                            Keys         keys     = resolver.getKeys();

                            I18nResolvedFieldGetter getter = resolver.getI18nResolvedFieldGetter();
                            Values values = getter.getValues( keys );

                            assert keys.size() == values.size();
                            assert keys.size() > 0;

                            for( int i = 0; i<keys.size(); i++ ) {
                                String k = keys.get( i );
                                String v = values.get( i );

                                missingPropertiesMap.put( k, v );
                                }
                            }
                        catch( MissingKeyException e ) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                             }
                        catch( GetFieldException e ) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            }

                        super.handleMissingResourceException( cause, i18nField, objectToI18n, i18nInterface );
                    }
                } );
            getI18nDelegator().addAutoI18nEventHandler( new AutoI18nLog4JEventHandler() );
            getI18nDelegator().addAutoI18nEventHandler( new AutoI18nEventHandler() {
                private static final long serialVersionUID = 1L;
                @Override
                public void ignoredField( Field f, String key, EventCause eventCause, String causeDecription )
                {
                    if( LOGGER.isDebugEnabled() ) {
                        LOGGER.debug( "ignoredField: " + key + " - field: " + f );
                        }
                    incForKey( key );
                }
                @Override
                public void localizedField( Field f, String key )
                {
                    if( LOGGER.isDebugEnabled() ) {
                        LOGGER.debug( "localizedField: " + key );
                        }
                    incForKey( key );
                }} );

            autoI18nCore = new AutoI18nCoreImpl( this.i18nDelegator );
            }

        return autoI18nCore;
    }

    public I18nResourceBundle getI18nResourceBundle()
    {
        return i18nResourceBundle;
    }

    private I18nInterface getI18nInterface()
    {
        return i18nResourceBundle;
    }

    public ResourceBundle getResourceBundle()
    {
        return i18nResourceBundle.getResourceBundle();
    }

    public void addAutoI18nEventHandler( AutoI18nEventHandler eventHandler )
    {
        getI18nDelegator().addAutoI18nEventHandler( eventHandler );
    }

    public void addAutoI18nExceptionHandler( AutoI18nExceptionHandler exceptionHandler )
    {
        getI18nDelegator().addAutoI18nExceptionHandler( exceptionHandler );
    }

    public Map<String,String> getResourceBundleMap()
    {
        ResourceBundle      rb  = getResourceBundle();
        Map<String,String>  map = new HashMap<String,String>();
        Enumeration<String> enu = rb.getKeys();

        while( enu.hasMoreElements() ) {
            String key   = enu.nextElement();
            String value = rb.getString( key );

            map.put( key, value );
            }

        return map;
    }

    public void openOutputFile( File outputFile )
    {
        this.resourceBundleOutputFile = outputFile;
    }

    public void closeOutputFile() throws IOException
    {
        Properties properties = new Properties();

        properties.putAll( missingPropertiesMap );

        OutputStream os = getResourceBundleOutputStream();

        LOGGER.info( "saveValues(): found (key,value) count  = " + keyUsageCountMap.size() );
        LOGGER.info( "saveValues(): know (key,value) count  = " + getResourceBundleMap().size() );
        LOGGER.info( "saveValues(): unknow (key,value) count = " + properties.size() );

        //properties.putAll( getProperties() );

        if( os == null ) {
            LOGGER.warn( "Can't open resource bundle for writing !" );
            }
        else {
            try {
                // Create by this class
                properties.store( os, "Create by :" + getClass().getName() );
                }
            finally {
                os.close();
                }
            }
    }

    private OutputStream getResourceBundleOutputStream() throws FileNotFoundException
    {
        return new FileOutputStream( resourceBundleOutputFile );
    }

    private void incForKey( String key )
    {
        final Integer countInteger = keyUsageCountMap.get( key );
        final int     count;

        if( countInteger == null ) {
            count = 1;
            }
        else {
            count = countInteger.intValue() + 1;
            }

        keyUsageCountMap.put( key, Integer.valueOf( count ) );
    }

    public Map<String,Integer> getUsageMap()
    {
        return Collections.unmodifiableMap( keyUsageCountMap );
    }
}
