package com.googlecode.cchlib.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.Collection;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.annotation.Nonnull;


/**
 *
 *
 */
public class PropertiesMap implements Map<String,String>, Serializable
{
    private static final long serialVersionUID = 2L;

    private final Properties properties;

    private transient Wrappable<Object,String> keyOrValueWrapper;
    private transient Wrappable<String,Object> keyOrValueUnwrapper;
    private transient Wrappable<Map.Entry<Object,Object>,Map.Entry<String,String>> entryWrapper;
    private transient Wrappable<Map.Entry<String,String>,Map.Entry<Object,Object>> entryUnwrapper;

    public PropertiesMap( final Properties properties )
    {
        this.properties = properties;

        initTransient();
    }

    public PropertiesMap()
    {
        this( new Properties() );
    }

    private void initTransient()
    {
        this.keyOrValueWrapper = obj -> (String)obj;
        this.keyOrValueUnwrapper  = obj -> obj;
        this.entryWrapper = obj -> new XEntry<String,String>( (String)obj.getKey(), (String)obj.getValue() );
        this.entryUnwrapper = obj -> new XEntry<Object,Object>( obj.getKey(), obj.getValue() );
    }

    @Override
    public int size()
    {
        return this.properties.size();
    }

    @Override
    public boolean isEmpty()
    {
        return this.properties.isEmpty();
    }

    @Override
    public boolean containsKey( final Object key )
    {
        return this.properties.containsKey( key );
    }

    @Override
    public boolean containsValue( final Object value )
    {
        return this.properties.containsValue( value );
    }

    @Override
    public String get( final Object key )
    {
        return (String)this.properties.get( key );
    }

    @Override
    public String put( final String key, final String value )
    {
        return (String)this.properties.put( key, value );
    }

    @Override
    public String remove( final Object key )
    {
        return (String)this.properties.remove( key );
    }

    @Override
    public void putAll( final Map<? extends String, ? extends String> m )
    {
        this.properties.putAll( m );
    }

    @Override
    public void clear()
    {
        this.properties.clear();
    }

    @Override
    public Set<String> keySet()
    {
        return new SetWrapper<Object,String>(this.properties.keySet(), this.keyOrValueWrapper, this.keyOrValueUnwrapper);
    }

    @Override
    public Collection<String> values()
    {
        return new CollectionWrapper<Object,String>(this.properties.values(), this.keyOrValueWrapper, this.keyOrValueUnwrapper);
    }

    @Override
    public Set<Map.Entry<String,String>> entrySet()
    {
        return new SetWrapper<Map.Entry<Object,Object>,Map.Entry<String,String>>( this.properties.entrySet(), this.entryWrapper, this.entryUnwrapper);
    }

    public void store(final Writer w, final String comment) throws IOException
    {
        this.properties.store( w, comment );
    }

    public void store(final OutputStream os, final String comment) throws IOException
    {
        this.properties.store( os, comment );
    }

    public void storeToXML(final OutputStream os, final String comment) throws IOException
    {
        this.properties.storeToXML( os, comment );
    }

    public void storeToXML(final OutputStream os, final String comment, final String encoding) throws IOException
    {
        this.properties.storeToXML( os, comment, encoding );
    }

    public void load(final Reader r) throws IOException
    {
        this.properties.load( r );
    }

    public void load(final InputStream inStream) throws IOException
    {
        this.properties.load( inStream );
    }

    public void loadFromXML(final InputStream in) throws InvalidPropertiesFormatException, IOException
    {
        this.properties.loadFromXML( in );
    }

    private static class XEntry<K,V> implements Map.Entry<K,V>, Serializable
    {
        private static final long serialVersionUID = 1L;
        private final K key;
        private final V value;

        public XEntry( final K key, final V value )
        {
            this.key   = key;
            this.value = value;
        }

        @Override
        public K getKey()
        {
            return this.key;
        }

        @Override
        public V getValue()
        {
            return this.value;
        }

        @Override
        public V setValue( final V value )
        {
            throw new UnsupportedOperationException();
        }
    }

    public static PropertiesMap load( final File confFile ) throws IOException
    {
        final PropertiesMap prop = new PropertiesMap();

        try( FileInputStream inStream = new FileInputStream( confFile ) ) {
            prop.load( inStream );
        }

        return prop;
    }

    private void writeObject( @Nonnull final ObjectOutputStream stream ) throws IOException
    {
        stream.defaultWriteObject();
    }

    private void readObject( @Nonnull final ObjectInputStream stream ) throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();

        initTransient();
    }
}
