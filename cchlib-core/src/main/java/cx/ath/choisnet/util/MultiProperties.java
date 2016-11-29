package cx.ath.choisnet.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 * MultiProperties provided a solution to synchronize
 * properties, the purpose of this class is mainly to
 * help you edit the properties.
 * <p>
 * The object is based on a <b>rootProperties</b>
 * which is the reference for all other properties
 * (called <b>childProperties</b>).
 * </p>
 * <u>Rules:</u>
 * <p>
 * - According to {@link Properties} object specifications,
 *  Properties whose key or value is not of type
 *  String are omitted.
 * </p>
 * <p>
 * - Guarantee that a key is not present in rootProperties
 * can not exist on any of the dependent properties
 * (childProperties).
 * </p>
 */
public class MultiProperties implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** @serial */
    private final Properties rootProperties;
    /** @serial */
    private final HashMap<String,Properties> childProperties;

    /**
     * Create MultiProperties base on this Properties.
     * <p>
     * This properties (called rootProperties) will be the reference for all
     * others properties (called "child properties").
     * </p>
     * @param rootProperties define root Properties
     */
    public MultiProperties( final Properties rootProperties )
    {
        this.childProperties = new HashMap<>();
        this.rootProperties  = rootProperties;
    }

    /**
     * <p>
     * Properties whose key or value is not of type
     * String are omitted.
     * </p>
     *
     * @param name          properties name
     * @param properties    NEEDDOC
     * @throws RootPropertiesDoesNotContainsKeyException NEEDDOC
     */
    public synchronized void addProperties(
        final String      name,
        final Properties  properties
        ) throws RootPropertiesDoesNotContainsKeyException
    {
        check(properties);
        this.childProperties.put( name, properties );
    }

    /**
     * Test if properties does not contain key
     * that not exist in rootProperties.
     * <p>
     * According to {@link Properties} object specifications
     * Properties whose key or value is not of type
     * String are omitted.
     * </p>
     *
     * @param properties Properties to check
     * @throws RootPropertiesDoesNotContainsKeyException if a String key
     *         with a String value if found, but does match to
     *         same key with a String value on rootProperties
     * @see Properties#stringPropertyNames()
     * @see Properties#getProperty(String)
     */
    public synchronized void check( final Properties properties )
            throws RootPropertiesDoesNotContainsKeyException
    {
        final Set<String> keys = properties.stringPropertyNames();

        for( final String key : keys ) {
            if( this.rootProperties.getProperty( key ) == null ) {
                throw new RootPropertiesDoesNotContainsKeyException( key );
                }
            }
    }

    /**
     * Return a Set of String that contains Property names.
     * <p>
     * Returns an set of keys in rootProperties property
     * list where the key and its corresponding value are
     * strings, including distinct keys in the default
     * property list if a key of the same name has not
     * already been found from the main properties list.
     * </p>
     * <p>
     * Properties whose key or value is not of type
     * String are omitted.
     * </p>
     * <p>
     * The returned set is <b>not backed</b> by the Properties
     * object. Changes to this Properties are not
     * reflected in the set, or vice versa.
     * </p>
     * @return a set of keys in this property list where
     *         the key and its corresponding value are
     *         strings, including the keys in the default
     *         property list.
     * @see Properties#stringPropertyNames()
     */
    public synchronized Set<String> stringPropertyNames()
    {
        return this.rootProperties.stringPropertyNames();
    }

    /**
     * Searches for the property with the specified key in
     * the root property list. If the key is not found
     * in this property list, the default property list,
     * and its defaults, recursively, are then checked.
     * The method returns null if the property is not found.
     *
     * @param key the property key.
     * @return the value in this property list with the specified key value.
     */
    public synchronized String getProperty( final String key )
    {
        return this.rootProperties.getProperty( key );
    }

    /**
     * First resolve properties from name, if properties
     * name's is not found.
     * <br>
     * Then searches for the property with the specified key
     * in this property list
     * @param name  child properties name
     * @param key   the property key.
     * @return the value in this property list with the specified key value.
     * @throws PropertiesDoesNotExistException if properties
     *         name's is not found
     * @throws RootPropertiesDoesNotContainsKeyException NEEDDOC
     */
    @SuppressWarnings("squid:S1160")
    public synchronized String getPropertyFrom( final String name, final String key )
        throws PropertiesDoesNotExistException,
               RootPropertiesDoesNotContainsKeyException
    {
        final Properties properties = this.childProperties.get( name );

        if( properties == null ) {
            throw new PropertiesDoesNotExistException(name);
            }

        final String value = properties.getProperty( key );

        if( value != null ) {
            if( this.rootProperties.getProperty( key ) == null ) {
                throw new RootPropertiesDoesNotContainsKeyException( key );
                }
            }

        return null;
    }

    /**
     * Calls the rootProperties method setProperty.
     * <p>
     * Enforces use of strings for property keys and values.
     * </p>
     *
     * @param key   the key to be placed into rootProperties property list.
     * @param value the value corresponding to key
     * @return the previous value of the specified key in
     *         rootProperties property list, or null if it did not have one.
     */
    public synchronized Object setProperty( final String key, final String value )
    {
        return this.rootProperties.setProperty( key, value );
    }

    /**
     * First resolve properties from name, if properties
     * name's is not found
     *
     * @param name  child properties name
     * @param key   the key to be placed into child property list.
     * @param value the value corresponding to key
     * @return the previous value of the specified key in
     *         child list, or null if it did not have one.
     * @throws PropertiesDoesNotExistException if properties
     *         name's is not found
     * @throws RootPropertiesDoesNotContainsKeyException NEEDDOC
     */
    @SuppressWarnings("squid:S1160")
    public synchronized Object setPropertyTo(
        final String name,
        final String key,
        final String value
        ) throws RootPropertiesDoesNotContainsKeyException,
                 PropertiesDoesNotExistException
    {
        if( this.rootProperties.getProperty( key ) == null ) {
            throw new RootPropertiesDoesNotContainsKeyException( key );
            }

        final Properties properties = this.childProperties.get( name );

        if( properties == null ) {
            throw new PropertiesDoesNotExistException(name);
            }

        return properties.setProperty( key, value );
    }

    /**
     * Removes the key (and its corresponding value) from
     * all Properties.
     *
     * @param key the key that needs to be removed
     * @throws NullPointerException if the key is null
     */
    synchronized void removeAll( final Object key )
    {
        for( final Properties prop : this.childProperties.values() ) {
            prop.remove( key );
            }

        this.rootProperties.remove( key );
    }
}
