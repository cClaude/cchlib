package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.lang.StringHelper;

/*not public*/
class PropertiesPopulatorLoader<E>
{
    private static final Logger LOGGER = Logger.getLogger( PropertiesPopulatorLoader.class );

    private final Map<Field, PropertiesPopulatorAnnotation<E>> keyFieldMap;
    private final E bean;
    private final Properties properties;

    private final StringBuilder prefix;
    private final int prefixLength;

    /* package */ PropertiesPopulatorLoader(
        final Map<Field, PropertiesPopulatorAnnotation<E>> keyFieldMap,
        final E                                            bean,
        final Properties                                   properties,
        final String                                       propertiesPrefix
        )
    {
        this.keyFieldMap = keyFieldMap;
        this.bean        = bean;
        this.properties  = properties;

        if( (propertiesPrefix == null) || propertiesPrefix.isEmpty() ) {
            prefix       = new StringBuilder();
            prefixLength = 0;
            }
        else {
            prefix       = new StringBuilder( propertiesPrefix );
            prefixLength = prefix.length();
            }
    }

    /* package */ void load() throws PropertiesPopulatorException
    {
        for( final Map.Entry<Field,PropertiesPopulatorAnnotation<E>> entry : getKeyFieldEntrySet() ) {
            final Field field = entry.getKey();

            loadDataForField( entry, field );
            }
    }

    private void loadDataForField(
        final Map.Entry<Field, PropertiesPopulatorAnnotation<E>> entry,
        final Field field
        )
    {
        field.setAccessible( true );

        try {
            final Object o = field.get( bean );

            if( o != null ) {
                if( field.getType().isArray() ) {
                    handleArray( entry, field, o );
                    }
                else if( PopulatorContener.class.isAssignableFrom( field.getType() ) ) {
                    handlePopulatorContener( field, o );
                    }
                else {
                    handleNonArray( entry, field, o );
                    }
                }
            else {
                // Ignore null entries
                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "Ignore null value from field " + field );
                    }
                }
            }
        catch( IllegalArgumentException e ) {
            // ignore !
            LOGGER.warn( "Cannot read field:" + field, e );
            }
        catch( IllegalAccessException e ) {
            // ignore !
            LOGGER.warn( "Cannot read field:" + field, e );
            }
        finally {
            field.setAccessible( false );
            }
    }

    private void handleNonArray(
        final Map.Entry<Field,PropertiesPopulatorAnnotation<E>> entry,
        final Field  field,
        final Object o
        )
    {
        // Handle non arrays
        if( prefixLength == 0 ) {
            //properties.put( f.getName(), o.toString() );
            properties.put( field.getName(), entry.getValue().toString( o ) );
            }
        else {
            prefix.setLength( prefixLength );
            prefix.append( field.getName() );
            //properties.put( prefix.toString(), o.toString() );
            properties.put( prefix.toString(), entry.getValue().toString( o ) );
            }
    }

    private void handlePopulatorContener(
        final Field field,
        final Object o
        )
    {
        final String strValue = PopulatorContener.class.cast( o ).getConvertToString();

        if( prefixLength == 0 ) {
            properties.put( field.getName(), strValue );
            }
        else {
            prefix.setLength( prefixLength );
            prefix.append( field.getName() );
            properties.put( prefix.toString(), strValue );
            }
    }

    private void handleArray(
        final Map.Entry<Field,PropertiesPopulatorAnnotation<E>> entry,
        final Field  field,
        final Object o
        )
    {
        // Handle Arrays
        final int length = Array.getLength( o );

        for( int i = 0; i < length; i ++ ) {
            Object arrayElement = Array.get( o, i );

            prefix.setLength( prefixLength );
            prefix.append( field.getName() );
            prefix.append( '.' );
            prefix.append( i );

            if( arrayElement == null ) {
                properties.put( prefix.toString(), StringHelper.EMPTY );
                }
            else {
                // FIXME for Persistent !!!
                properties.put(
                    prefix.toString(),
                    // arrayElement.toString()
                    entry.getValue().toString( arrayElement )
                    );
                }
            }
    }

    private Set<Map.Entry<Field, PropertiesPopulatorAnnotation<E>>> getKeyFieldEntrySet()
    {
        return keyFieldMap.entrySet();
    }
}

