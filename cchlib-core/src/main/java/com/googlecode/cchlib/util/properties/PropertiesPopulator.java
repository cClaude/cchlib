package com.googlecode.cchlib.util.properties;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * <p>PropertiesPopulator is a simple way to store values in a properties
 * with a minimum work.</p>
 * <p>
 * PropertiesPopulator is able to set or get {@link Field} values
 * annotates with {@link Populator} or {@link Persistent}.
 * </p>
 * <p>
 * <i>Warn:</i>Only primitive type are supported (and standard derived object) by
 * {@link Populator}.<br/>
 * See {@link PopulatorContener} if you need to support extra type.
 * </p>
 * <p>
 * Swing components supported by {@link Persistent}
 * <ul>
 *   <li>{@link javax.swing.JTextField JTextField}</li>
 *   <li>{@link javax.swing.JCheckBox JCheckBox}</li>
 *   <li>{@link javax.swing.JComboBox JComboBox} (Store only selected index)</li>
 * </ul>
 * </p>
 */
public class PropertiesPopulator<E>
{
    private static final Logger LOGGER = Logger.getLogger( PropertiesPopulator.class );
    private Map<Field,PropertiesPopulatorAnnotation<E>> keyFieldMap;

    /**
     * Create a {@link PropertiesPopulator} object for giving class.
     *
     * @param clazz {@link Class} to use to create this {@link PropertiesPopulator}.
     */
    public PropertiesPopulator( final Class<? extends E> clazz )
    {
        this.keyFieldMap = new HashMap<Field,PropertiesPopulatorAnnotation<E>>();

        Field[] fields  = clazz.getDeclaredFields();

        for( Field f : fields ) {
            Populator populator = f.getAnnotation( Populator.class );

            if( populator != null ) {
                this.keyFieldMap.put( f, new PopulatorAnnotation<E>( populator ) );
                }

            Persistent persistent = f.getAnnotation( Persistent.class );

            if( persistent != null ) {
                this.keyFieldMap.put( f, new PersistentAnnotation<E>( persistent ) );
                }
            }

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Found " + this.keyFieldMap.size() + " fields on " + clazz );

            for( Map.Entry<Field,PropertiesPopulatorAnnotation<E>> entry : this.keyFieldMap.entrySet() ) {
                LOGGER.trace( "Key[" + entry.getKey() + "]=" + entry.getValue() );
                }
            }
    }

    /**
     * Store fields annotate with {@link Populator} from bean
     * to properties
     *
     * @param bean       Object to use to get values
     * @param properties {@link Properties} to use to store values
     * @throws PropertiesPopulatorException
     */
    public void populateProperties(
        final E          bean,
        final Properties properties
        ) throws PropertiesPopulatorException
    {
        populateProperties( null, bean, properties );
    }

    /**
     * Store fields annotate with {@link Populator} from bean
     * to properties.<br/>
     * Null entries are not stored in properties, but null entries
     * from arrays are stored.
     *
     * @param propertiesPrefix Prefix for properties names, if null or empty ignored.
     * @param bean       Object to use to get values
     * @param properties {@link Properties} to use to store values
     * @throws PropertiesPopulatorException
     */
    public void populateProperties(
        final String     propertiesPrefix,
        final E          bean,
        final Properties properties
        ) throws PropertiesPopulatorException
    {
        final PropertiesPopulatorLoader<E> loader //
        = new PropertiesPopulatorLoader<E>( keyFieldMap, bean, properties, propertiesPrefix );

        loader.load();
    }

    /**
     * Set fields annotate with {@link Populator} from properties
     * on bean
     *
     * @param bean       Object to use to store values
     * @param properties {@link Properties} to use to get values
     * @throws PropertiesPopulatorException
     */
    public void populateBean(
        final Properties properties,
        final E          bean
        ) throws PropertiesPopulatorException
    {
        populateBean( null, properties, bean );
    }

    /**
     * Set fields annotate with {@link Populator} from properties
     * on bean
     *
     * @param propertiesPrefix Prefix for properties names, if null or empty ignored.
     * @param bean       Object to use to store values
     * @param properties {@link Properties} to use to get values
     * @throws PropertiesPopulatorException
     */
    public void populateBean(
        final String     propertiesPrefix,
        final Properties properties,
        final E          bean
        ) throws PropertiesPopulatorException
    {
        new PopulateBean(propertiesPrefix, properties, bean).populate();
    }

    /**
     * Populate bean from Properties
     */
    class PopulateBean
    {
        private Properties      properties;
        private E bean;
        private StringBuilder   prefix;
        private final int       prefixLength;

        /**
         *
         */
        public PopulateBean(
            final String     propertiesPrefix,
            final Properties properties,
            final E          bean
            )
        {
            this.properties = properties;
            this.bean       = bean;

            if( (propertiesPrefix == null) || propertiesPrefix.isEmpty() ) {
                prefix = null;
                prefixLength = 0;
                }
            else {
                prefix = new StringBuilder( propertiesPrefix );
                prefixLength = prefix.length();
                }
         }

        public void populate() throws PropertiesPopulatorException
        {
            for( Entry<Field,PropertiesPopulatorAnnotation<E>> entry : keyFieldMap.entrySet() ) {
                final Field     field = entry.getKey();
                final Class<?>  type  = field.getType();

                if( type.isArray() ) {
                    handleArray( entry, prefix, prefixLength );
                    }
                else {
                    handleNonArray( entry, field, type );
                    }
                }
        }

        private void handleNonArray(
            final Entry<Field,PropertiesPopulatorAnnotation<E>> entry,
            final Field                                         field,
            final Class<?>                                      type
            )
        {
            final String strValue;
            final String defaultValue;

            if( entry.getValue().isDefaultValueNull() ) {
                defaultValue = null;
                }
            else {
                defaultValue = entry.getValue().defaultValue();
                }

            if( prefixLength == 0 ) {
                strValue = properties.getProperty( field.getName(), defaultValue );
                }
            else {
                prefix.setLength( prefixLength );
                prefix.append( field.getName() );
                strValue = properties.getProperty( prefix.toString(), defaultValue );
                }

            field.setAccessible( true );

            try {
                if( PopulatorContener.class.isAssignableFrom( type ) ) {
                    Object o = field.get( bean );

                    if( o == null ) {
                        throw new PopulatorException( "Can't handle null for PopulatorContener field", field, field.getType() );
                        }
                    PopulatorContener contener = PopulatorContener.class.cast( o );

                    contener.setConvertToString( strValue );
                    }
                else {
                    try {
                        //Object o = convertStringToObject( strValue, type );
                        //f.set( bean, o );
                        entry.getValue().setValue( field, bean, strValue, type );
                        }
                    catch( ConvertCantNotHandleTypeException e ) {
                        throw new PopulatorException( "Bad type for field", field, field.getType() );
                        }
                    }
                }
            catch( NumberFormatException e ) {
                LOGGER.warn( "Cannot set field:" + field, e );
                }
            catch( IllegalArgumentException e ) {
                // ignore !
                LOGGER.warn( "Cannot set field:" + field, e );
                }
            catch( IllegalAccessException e ) {
                // ignore !
                LOGGER.error( "Cannot set field:" + field, e );
                }
            finally {
                field.setAccessible( false );
                }
        }

        /*
         * Handle arrays
         *
         * @param f
         * @param type
         */
        private void handleArray(
            final Entry<Field,PropertiesPopulatorAnnotation<E>> entry,
            StringBuilder                                       prefix,
            final int                                           prefixLength
            ) throws ArrayIndexOutOfBoundsException, PropertiesPopulatorException
        {
            final Field         f         = entry.getKey();
            final Class<?>      arrayType = f.getType();
            final Class<?>      type      = arrayType.getComponentType();
            final List<String>  values    = new ArrayList<String>();

            // TODO: handle default values ???

            if( prefix == null ) {
                prefix = new StringBuilder();
                }

            // Put arrays values in a list of strings
            for(int i=0;;i++ ) {
                prefix.setLength( prefixLength );
                prefix.append( f.getName() );
                prefix.append( '.' );
                prefix.append( i );

                final String strValue = properties.getProperty( prefix.toString() );

                if( strValue == null ) {
                    break;
                    }
                else {
                    values.add( strValue );
                    }
                }

            // Compute array size
            final int length = values.size();

            try {
                f.setAccessible( true );

                Object o = f.get( bean );

                if( (o == null) || (length != Array.getLength( o )) ) {
                    o = Array.newInstance( type, length );
                    f.set( bean, o );
                    }

                for( int i = 0; i < length; i ++ ) {
                    //Array.set( o, i, convert( f, values.get( i ), type ) );
                    try {
                        //Array.set( o, i, convertStringToObject( values.get( i ), type ) );

                        entry.getValue().setArrayEntry( f, o, i, values.get( i ), type );
                        }
                    catch( ConvertCantNotHandleTypeException e ) {
                        throw new PopulatorException( "Bad type for field", f, f.getType() );
                        }
                    }
                }
            catch( IllegalArgumentException e ) {
                // ignore !
                LOGGER.warn( "Cannot set field:" + f, e );
                }
            catch( IllegalAccessException e ) {
                // ignore !
                LOGGER.warn( "Cannot set field:" + f, e );
                }
            finally {
                f.setAccessible( false );
                }
        }

    } //class PopulateBean

    /**
     * Initialize a bean from a properties file.
     *
     * @param propertiesFile    File to load
     * @param bean              Bean initialize
     * @param clazz             Class of bean
     * @return giving bean for initialization chaining.
     * @throws IOException if any I/O occur
     * @throws PropertiesPopulatorException
     * @since 4.1.7
     */
    public static <E> E loadProperties(
        final File      propertiesFile,
        final E         bean,
        final Class<E>  clazz
        ) throws IOException, PropertiesPopulatorException
    {
        final Properties properties = PropertiesHelper.loadProperties( propertiesFile );
        new PropertiesPopulator<E>( clazz ).populateBean( properties, bean );
        return bean;
    }

    /**
     * Save a bean to a properties file.
     *
     * @param propertiesFile File to create
     * @param bean           Bean to save
     * @param clazz          Class of bean
     * @throws IOException if any I/O occur
     * @throws PropertiesPopulatorException
     * @since 4.1.7
     */
    public static <E> void saveProperties(
        final File      propertiesFile,
        final E         bean,
        final Class<E>  clazz
        ) throws IOException, PropertiesPopulatorException
    {
        final Properties properties = new Properties();
        new PropertiesPopulator<E>( clazz ).populateProperties( bean, properties );
        PropertiesHelper.saveProperties( propertiesFile, properties );
    }
}//class PropertiesPopulator
