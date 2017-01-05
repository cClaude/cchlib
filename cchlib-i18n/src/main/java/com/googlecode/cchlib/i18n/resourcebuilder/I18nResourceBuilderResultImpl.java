package com.googlecode.cchlib.i18n.resourcebuilder;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.resources.MissingResourceException;

class I18nResourceBuilderResultImpl implements I18nResourceBuilderResult, Serializable
{
    private static final long serialVersionUID = 1L;

    private final Map<String,Integer> localizedFieldMap    = new HashMap<>();
    private final Map<String,Integer> ignoredFieldMap      = new HashMap<>();
    private final Map<String,Integer> missingPropertiesMap = new HashMap<>();

    private final Map<String,String>  missingPropertiesKeyValues = new HashMap<>();
    private final Map<String,String>  unusedPropertiesKeyValues  = new HashMap<>();

    private static final void addField( final Map<String,Integer> map, final String key )
    {
        assert key != null : "Key is null";

        final Integer countInteger = map.get( key );
        final int     count;

        if( countInteger == null ) {
            count = 1;
            }
        else {
            count = countInteger.intValue() + 1;
            }

        map.put( key, Integer.valueOf( count ) );
    }

    synchronized void addLocalizedField( final String key )
    {
        addField( this.localizedFieldMap, key );
    }

    @Override
    public Map<String, Integer> getLocalizedFields()
    {
        return Collections.unmodifiableMap( this.localizedFieldMap );
    }

    synchronized void addIgnoredField( final String key )
    {
        addField( this.ignoredFieldMap, key );
    }

    @Override
    public Map<String, Integer> getIgnoredFields()
    {
        return Collections.unmodifiableMap( this.ignoredFieldMap );
    }

    synchronized void addMissingProperties( final String key, final String value )
    {
        addField( this.missingPropertiesMap, key );

        this.missingPropertiesKeyValues.put( key, value );
    }

    @Override
    public Map<String, Integer> getMissingProperties()
    {
        return Collections.unmodifiableMap( this.missingPropertiesMap );
    }

    public Map<String,String> getMissingKeyValues()
    {
        return Collections.unmodifiableMap( this.missingPropertiesKeyValues );
    }

    void computeUnused( final Set<String> i18nResourceKeys, final I18nResource i18nResource )
    {
        final Set<String> knonwKeys = new HashSet<>();

        knonwKeys.addAll( i18nResourceKeys );

        for( final String usedKey : this.localizedFieldMap.keySet() ) {
            final boolean found = knonwKeys.remove( usedKey );

            if( !found ) { /// inconsistent state
                Logger.getLogger( I18nResourceBuilderResultImpl.class )
                    .warn( "Inconsistent state - localized key=" + usedKey + " was not found in Resources" );
            }
        }

        this.unusedPropertiesKeyValues.clear();

        for( final String unusedkey : knonwKeys ) {
            final String value = getValue( i18nResource, unusedkey );

            this.unusedPropertiesKeyValues.put( unusedkey, value );
         }
    }

    private String getValue( final I18nResource i18nResource, final String key  )
    {
        try {
            return i18nResource.getString( key );
        }
        catch( final MissingResourceException cause ) {
            Logger.getLogger( I18nResourceBuilderResultImpl.class )
                .trace( "Can not find value for usedKey=" + key, cause );

            return cause.getMessage();
        }
    }

    @Override
    public Map<String, String> getUnusedProperties()
    {
        return Collections.unmodifiableMap( this.unusedPropertiesKeyValues );
    }
}
