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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
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
    private final static Logger logger = Logger.getLogger( PropertiesPopulator.class );
    private Map<Field,PropertiesPopulatorAnnotation<E>> keyFieldMap;

    private interface PropertiesPopulatorAnnotation<E>
    {
        /**
         * Return annotation value
         */
        public boolean defaultValueIsNull();
        /**
         * Return annotation value
         */
        public String defaultValue();
        /**
         * Return object value has a String
         */
        public String toString( Object o ) throws PropertiesPopulatorException;
        /**
         *
         * @param f
         * @param bean
         * @param strValue
         * @param type
         * @throws IllegalArgumentException
         * @throws IllegalAccessException
         * @throws ConvertCantNotHandleTypeException
         * @throws PropertiesPopulatorException
         */
        public void setValue( Field f, E bean, String strValue, Class<?> type)
            throws IllegalArgumentException,
                   IllegalAccessException,
                   ConvertCantNotHandleTypeException,
                   PropertiesPopulatorException;
        /**
         *
         * @param f
         * @param array
         * @param index
         * @param strValue
         * @param type
         * @throws ArrayIndexOutOfBoundsException
         * @throws IllegalArgumentException
         * @throws ConvertCantNotHandleTypeException
         * @throws PropertiesPopulatorException
         */
        public void setArrayEntry( Field f, Object array, int index, String strValue, Class<?> type )
            throws ArrayIndexOutOfBoundsException,
                   IllegalArgumentException,
                   ConvertCantNotHandleTypeException,
                   PropertiesPopulatorException;
    }

    private class PopulatorAnnotation implements PropertiesPopulatorAnnotation<E>
    {
        private Populator populator;

        PopulatorAnnotation( Populator populator )
        {
            this.populator = populator;
        }
        @Override
        public boolean defaultValueIsNull()
        {
            return populator.defaultValueIsNull();
        }
        @Override
        public String defaultValue()
        {
            return populator.defaultValue();
        }
        @Override
        public String toString( final Object o )
        {
            return o.toString();
        }
        @Override
        public void setValue( final Field f, final E bean, final String strValue, final Class<?> type )
            throws IllegalArgumentException,
                   IllegalAccessException,
                   ConvertCantNotHandleTypeException,
                   PropertiesPopulatorException
        {
            f.set( bean, private_convertStringToObject( strValue, type ) );
        }
        @Override
        public void setArrayEntry( final Field f, final Object array, final int index, final String strValue, final Class<?> type)
            throws ArrayIndexOutOfBoundsException,
                   IllegalArgumentException,
                   ConvertCantNotHandleTypeException,
                   PropertiesPopulatorException
        {
            Array.set( array, index, private_convertStringToObject( strValue, type ) );
        }
    }

    private class PersistentAnnotation implements PropertiesPopulatorAnnotation<E>
    {
        private Persistent persistent;

        PersistentAnnotation( Persistent persistent )
        {
            this.persistent = persistent;
        }
        @Override
        public boolean defaultValueIsNull()
        {
            return false;
        }
        @Override
        public String defaultValue()
        {
            return persistent.defaultValue();
        }
        @Override
        public String toString( final Object o ) throws PropertiesPopulatorException
        {
            if( o instanceof JTextField ) {
                return JTextField.class.cast( o ).getText();
                }
            else if( o instanceof JCheckBox ) {
                return Boolean.toString( JCheckBox.class.cast( o ).isSelected() );
                }
            else if( o instanceof JComboBox ) {
                JComboBox jc    = JComboBox.class.cast( o );
                int       index = jc.getSelectedIndex(); // Store only selected index

                return Integer.toString( index );
                }
            else {
                throw new PersistentException( "@Persistent does not handle type " + o.getClass() );
                }
        }
        @Override
        public void setValue( final Field f, final E bean, final String strValue, final Class<?> type )
            throws IllegalArgumentException,
                   IllegalAccessException,
                   ConvertCantNotHandleTypeException,
                   PropertiesPopulatorException
        {
            Object o = f.get( bean );

            if( o instanceof JTextField ) {
                JTextField.class.cast( o ).setText( strValue );
                }
            else if( o instanceof JCheckBox ) {
                JCheckBox.class.cast( o ).setSelected( Boolean.parseBoolean( strValue ) );
                }
            else if( o instanceof JComboBox ) {
                final JComboBox jc    = JComboBox.class.cast( o );
                final int       index = Integer.parseInt( strValue ); 
                
                jc.setSelectedIndex( index );
                }
            else {
                throw new PersistentException( "@Persistent does not handle type " + o.getClass() );
                }
        }
        @Override
        public void setArrayEntry( final Field f, final Object array, final int index, final String strValue, final Class<?> type)
            throws ArrayIndexOutOfBoundsException,
                   IllegalArgumentException,
                   ConvertCantNotHandleTypeException,
                   PropertiesPopulatorException
        {
            throw new PersistentException( "@Persistent does not handle array" );
        }
    }

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
                this.keyFieldMap.put( f, new PopulatorAnnotation( populator ) );
                }

            Persistent persistent = f.getAnnotation( Persistent.class );

            if( persistent != null ) {
                this.keyFieldMap.put( f, new PersistentAnnotation( persistent ) );
                }
            }

        if( logger.isTraceEnabled() ) {
            logger.trace( "Found " + this.keyFieldMap.size() + " fields." );
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
        populateProperties( bean, properties, null );
    }

    /**
     * Store fields annotate with {@link Populator} from bean
     * to properties.<br/>
     * Null entries are not stored in properties, but null entries
     * from arrays are stored.
     *
     * @param bean       Object to use to get values
     * @param properties {@link Properties} to use to store values
     * @param propertiesPrefix TODOC
     * @throws PropertiesPopulatorException
     */
    public void populateProperties(
        final E          bean,
        final Properties properties,
        final String     propertiesPrefix
        ) throws PropertiesPopulatorException
    {
        final StringBuilder prefix;
        final int           prefixLength;

        if( propertiesPrefix == null || propertiesPrefix.isEmpty() ) {
            prefix       = new StringBuilder();
            prefixLength = 0;
            }
        else {
            prefix       = new StringBuilder( propertiesPrefix );
            prefixLength = prefix.length();
            }

        for( Entry<Field,PropertiesPopulatorAnnotation<E>> entry : this.keyFieldMap.entrySet() ) {
            final Field f = entry.getKey();

            f.setAccessible( true );
            try {
                final Object o = f.get( bean );

                if( o != null ) {
                    if( f.getType().isArray() ) {
                        // Handle Arrays
                        final int length = Array.getLength( o );

                        for( int i = 0; i < length; i ++ ) {
                            Object arrayElement = Array.get( o, i );

                            prefix.setLength( prefixLength );
                            prefix.append( f.getName() );
                            prefix.append( '.' );
                            prefix.append( i );

                            if( arrayElement == null ) {
                                properties.put( prefix.toString(), "" );
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
                    else if( PopulatorContener.class.isAssignableFrom( f.getType() ) ) {
                        String strValue = PopulatorContener.class.cast( o ).getConvertToString();

                        if( prefixLength == 0 ) {
                            properties.put( f.getName(), strValue );
                            }
                        else {
                            prefix.setLength( prefixLength );
                            prefix.append( f.getName() );
                            properties.put( prefix.toString(), strValue );
                            }
                    }
                    else {
                        // Handle non arrays
                        if( prefixLength == 0 ) {
                            //properties.put( f.getName(), o.toString() );
                            properties.put( f.getName(), entry.getValue().toString( o ) );
                            }
                        else {
                            prefix.setLength( prefixLength );
                            prefix.append( f.getName() );
                            //properties.put( prefix.toString(), o.toString() );
                            properties.put( prefix.toString(), entry.getValue().toString( o ) );
                            }
                        }
                    }
                else {
                    // Ignore null entries
                    if( logger.isTraceEnabled() ) {
                        logger.trace( "Ignore null value from field " + f );
                        }
                    }
                }
            catch( IllegalArgumentException e ) {
                // ignore !
                logger .warn( "Cannot read field:" + f, e );
                }
            catch( IllegalAccessException e ) {
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
     * @throws PropertiesPopulatorException
     */
    public void populateBean(
        final Properties properties,
        final E          bean
        ) throws PropertiesPopulatorException
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
     * @throws PropertiesPopulatorException
     */
    public void populateBean(
        final Properties properties,
        final String     propertiesPrefix,
        final E          bean
        ) throws PropertiesPopulatorException
    {
        new PopulateBean(properties, propertiesPrefix, bean).populate();
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
            final Properties properties,
            final String     propertiesPrefix,
            final E          bean
            )
        {
            this.properties = properties;
            this.bean       = bean;

            if( propertiesPrefix == null || propertiesPrefix.isEmpty() ) {
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
                final Field     f    = entry.getKey();
                final Class<?>  type = f.getType();

                //logger.info( "Populate field: " + f + " - " + type );

                if( type.isArray() ) {
                    //handleArray( f, type, prefix, prefixLength );
                    handleArray( entry, prefix, prefixLength );
                    }
                else {
                    final String strValue;
                    final String defaultValue;

                    if( entry.getValue().defaultValueIsNull() ) {
                        defaultValue = null;
                        }
                    else {
                        defaultValue = entry.getValue().defaultValue();
                        }

                    if( prefixLength == 0 ) {
                        strValue = properties.getProperty( f.getName(), defaultValue );
                        }
                    else {
                        prefix.setLength( prefixLength );
                        prefix.append( f.getName() );
                        strValue = properties.getProperty( prefix.toString(), defaultValue );
                        }

                    //logger.info( "VALUE field: " + f + " is " + strValue + " default=" + defaultValue );

                    f.setAccessible( true );

                    try {
                        //logger .trace( "F:" + f.getName() + " * getType()=" + f.getType() );
                        //logger .trace( "F:" + f.getName() + " * toGenericString()=" + f.toGenericString() );

                        if( PopulatorContener.class.isAssignableFrom( type ) ) {
                            Object o = f.get( bean );

                            if( o == null ) {
                                throw new PopulatorException( "Can't handle null for PopulatorContener field", f, f.getType() );
                                }
                            PopulatorContener contener = PopulatorContener.class.cast( o );

                            contener.setConvertToString( strValue );
                            }
                        else {
                            try {
                                //Object o = convertStringToObject( strValue, type );
                                //f.set( bean, o );
                                entry.getValue().setValue( f, bean, strValue, type );
                                //logger.info( "_SET_ field: " + f + " to [" + o + ']' );
                                }
                            catch( ConvertCantNotHandleTypeException e ) {
                                throw new PopulatorException( "Bad type for field", f, f.getType() );
                                }
                            }
                        }
                    catch( NumberFormatException e ) {
                        logger.warn( "Cannot set field:" + f, e );
                        }
                    catch( IllegalArgumentException e ) {
                        // ignore !
                        logger.warn( "Cannot set field:" + f, e );
                        }
                    catch( IllegalAccessException e ) {
                        // ignore !
                        logger.error( "Cannot set field:" + f, e );
                        }
                    finally {
                        f.setAccessible( false );
                        }
                    }
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

                if( o == null || length != Array.getLength( o ) ) {
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
                logger.warn( "Cannot set field:" + f, e );
                }
            catch( IllegalAccessException e ) {
                // ignore !
                logger.warn( "Cannot set field:" + f, e );
                }
            finally {
                f.setAccessible( false );
                }
        }

    }//class PopulateBean


    private static Object private_convertStringToObject(
        final String    strValue,
        final Class<?>  type
        ) throws ConvertCantNotHandleTypeException
    {
        if( String.class.isAssignableFrom( type ) ) {
            return strValue;
            }
        else if( boolean.class.isAssignableFrom( type ) ) {
            return Boolean.valueOf( strValue );
            }
        else if( Boolean.class.isAssignableFrom( type ) ) {
            return Boolean.valueOf( strValue );
            }
        else if( int.class.isAssignableFrom( type ) ) {
            return Integer.valueOf( strValue );
            }
        else if( Integer.class.isAssignableFrom( type ) ) {
            return Integer.valueOf( strValue );
            }
        else if( short.class.isAssignableFrom( type ) ) {
            return Short.valueOf( strValue );
            }
        else if( Short.class.isAssignableFrom( type ) ) {
            return Short.valueOf( strValue );
            }
        else if( byte.class.isAssignableFrom( type ) ) {
            return Byte.valueOf( strValue );
            }
        else if( Byte.class.isAssignableFrom( type ) ) {
            return Byte.valueOf( strValue );
            }
        else if( long.class.isAssignableFrom( type ) ) {
            return Long.valueOf( strValue );
            }
        else if( Long.class.isAssignableFrom( type ) ) {
            return Long.valueOf( strValue );
            }
        else if( float.class.isAssignableFrom( type ) ) {
            return Float.valueOf( strValue );
            }
        else if( Float.class.isAssignableFrom( type ) ) {
            return Float.valueOf( strValue );
            }
        else {
            throw new ConvertCantNotHandleTypeException();
            }
    }

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
