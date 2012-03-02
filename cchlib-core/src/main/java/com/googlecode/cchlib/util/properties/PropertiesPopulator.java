package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * PropertiesPopulator is a simple way to store values in a properties
 * with a minimum work.
 * <br>
 * PropertiesPopulator is able to set or get {@link Fields} values
 * annotates with {@link Populator}.
 * <br>
 * Warn: Only primitive type are supported (and standard derived object)
 * see {@link PopulatorBuilder} if you need to support extra type.
 */
public class PropertiesPopulator<E>
{
    private final static Logger logger = Logger.getLogger( PropertiesPopulator.class );
    private Set<Field> keyFieldSet;

    /**
     * Create a {@link PropertiesPopulator} object for giving class.
     *
     * @param clazz {@link Class} to use to create this {@link PropertiesPopulator}.
     */
    public PropertiesPopulator( Class<? extends E> clazz )
    {
        this.keyFieldSet     = new HashSet<>();
        Field[] fields  = clazz.getDeclaredFields();

        for( Field f : fields ) {
            if( f.getAnnotation( Populator.class ) != null ) {
                this.keyFieldSet.add( f );
                }
            }

        if( logger.isTraceEnabled() ) {
            logger.trace( "Found " + this.keyFieldSet.size() + " fields." );
            }
    }
    /**
     * Store fields annotate with {@link Populator} from bean
     * to properties
     *
     * @param bean       Object to use to get values
     * @param properties {@link Properties} to use to store values
     */
    public void populateProperties(
        final E          bean,
        final Properties properties
        )
    {
        populateProperties( bean, properties, null );
    }

    /**
     * Store fields annotate with {@link Populator} from bean
     * to properties
     *
     * @param bean       Object to use to get values
     * @param properties {@link Properties} to use to store values
     * @param propertiesPrefix TODOC
     */
    public void populateProperties(
        final E          bean,
        final Properties properties,
        final String     propertiesPrefix
        )
    {
        StringBuilder prefix;
        int           prefixLength;

        if( propertiesPrefix == null || propertiesPrefix.isEmpty() ) {
            prefix = null;
            prefixLength = 0;
            }
        else {
            prefix = new StringBuilder( propertiesPrefix );
            prefixLength = prefix.length();
            }

        for( Field f : this.keyFieldSet ) {
            f.setAccessible( true );
            try {
                final Object o = f.get( bean );

                if( o != null ) {
                    if( prefixLength == 0 ) {
                        properties.put( f.getName(), o.toString() );
                        }
                    else {
                        prefix.setLength( prefixLength );
                        prefix.append( f.getName() );
                        properties.put( prefix.toString(), o.toString() );
                        }
                    }
                }
            catch( IllegalArgumentException | IllegalAccessException e ) {
                // ignore !
                logger .warn( "Cannot read field:" + f, e );
                }
            finally {
                f.setAccessible( false );
                }
            }
    }

    /**
     * Set fields annotate with {@link Populator} from properties
     * on bean
     *
     * @param bean       Object to use to store values
     * @param properties {@link Properties} to use to get values
     * @throws PopulatorException
     */
    public void populateBean(
        final Properties properties,
        final E          bean
        ) throws PopulatorException
    {
        populateBean( properties, null, bean );
    }

    /**
     * Set fields annotate with {@link Populator} from properties
     * on bean
     *
     * @param bean       Object to use to store values
     * @param propertiesPrefix TODOC
     * @param properties {@link Properties} to use to get values
     * @throws PopulatorException
     */
    public void populateBean(
        final Properties properties,
        final String     propertiesPrefix,
        final E          bean
        ) throws PopulatorException
    {
        StringBuilder prefix;
        int           prefixLength;

        if( propertiesPrefix == null || propertiesPrefix.isEmpty() ) {
            prefix = null;
            prefixLength = 0;
            }
        else {
            prefix = new StringBuilder( propertiesPrefix );
            prefixLength = prefix.length();
            }

        for( Field f : this.keyFieldSet ) {
            final String strValue;

            if( prefixLength == 0 ) {
                strValue = properties.getProperty( f.getName() );
                }
            else {
                prefix.setLength( prefixLength );
                prefix.append( f.getName() );
                strValue = properties.getProperty( prefix.toString() );
                }

            f.setAccessible( true );
            try {

                //logger .trace( "F:" + f.getName() + " * getType()=" + f.getType() );
                //logger .trace( "F:" + f.getName() + " * toGenericString()=" + f.toGenericString() );
                final Class<?> type = f.getType();

                if( String.class.isAssignableFrom( type ) ) {
                    f.set( bean, strValue );
                    }
                else if( boolean.class.isAssignableFrom( type ) ) {
                    f.setBoolean( bean, Boolean.valueOf( strValue ).booleanValue() );
                    }
                else if( Boolean.class.isAssignableFrom( type ) ) {
                    f.set( bean, Boolean.valueOf( strValue ) );
                    }
                else if( int.class.isAssignableFrom( type ) ) {
                    f.setInt( bean, Integer.valueOf( strValue ).intValue() );
                    }
                else if( Integer.class.isAssignableFrom( type ) ) {
                    f.set( bean, Integer.valueOf( strValue ) );
                    }
                else if( short.class.isAssignableFrom( type ) ) {
                    f.setShort( bean, Short.valueOf( strValue ).shortValue() );
                    }
                else if( Short.class.isAssignableFrom( type ) ) {
                    f.set( bean, Short.valueOf( strValue ) );
                    }
                else if( byte.class.isAssignableFrom( type ) ) {
                    f.setByte( bean, Byte.valueOf( strValue ).byteValue() );
                    }
                else if( Byte.class.isAssignableFrom( type ) ) {
                    f.set( bean, Byte.valueOf( strValue ) );
                    }
                else if( long.class.isAssignableFrom( type ) ) {
                    f.setLong( bean, Long.valueOf( strValue ).longValue() );
                    }
                else if( Long.class.isAssignableFrom( type ) ) {
                    f.set( bean, Long.valueOf( strValue ) );
                    }
                else if( float.class.isAssignableFrom( type ) ) {
                    f.setFloat( bean, Float.valueOf( strValue ).floatValue() );
                    }
                else if( Float.class.isAssignableFrom( type ) ) {
                    f.set( bean, Float.valueOf( strValue ) );
                    }
                else if( PopulatorContener.class.isAssignableFrom( type ) ) {
                    logger.warn( "NOT YET IMPLEMENTED:" + f );

                    Object o = f.get( bean );
                    PopulatorContener<?> contener = PopulatorContener.class.cast( o );

                    contener.init( strValue );
                    }
                else {
                    //logger .error( "Bad type for field:" + f + " * class=" + f.getType() );
                    throw new PopulatorException( "Bad type for field", f, f.getType() );
                    }
                }
            catch( NumberFormatException e ) {
                logger.warn( "Cannot set field:" + f );
                }
            catch( IllegalArgumentException | IllegalAccessException e ) {
                // ignore !
                logger.error( "Cannot set field:" + f, e );
                }
            finally {
                f.setAccessible( false );
                }
            }
    }
}
