package com.googlecode.cchlib.i18n.resourcebuilder;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class I18nResourceBuilderResultImpl implements I18nResourceBuilderResult, Serializable
{
    private static final long serialVersionUID = 1L;

    private final Map<String,Integer> localizedFieldMap    = new HashMap<>();
    private final Map<String,Integer> ignoredFieldMap      = new HashMap<>();
    private final Map<String,Integer> missingPropertiesMap = new HashMap<>();

    private final Map<String,String>  missingPropertiesKeyValues = new HashMap<>();

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

    synchronized void addIgnoredField( final String key )
    {
        addField( this.ignoredFieldMap, key );
    }

    synchronized void addMissingProperties( final String key, final String value )
    {
        addField( this.missingPropertiesMap, key );

        this.missingPropertiesKeyValues.put( key, value );
    }

    @Override
    public Map<String, Integer> getLocalizedFields()
    {
        return Collections.unmodifiableMap( this.localizedFieldMap );
    }

    @Override
    public Map<String, Integer> getIgnoredFields()
    {
        return Collections.unmodifiableMap( this.ignoredFieldMap );
    }

    @Override
    public Map<String, Integer> getMissingProperties()
    {
        return Collections.unmodifiableMap( this.missingPropertiesMap );
    }
}
