package com.googlecode.cchlib.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.Collection;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import com.googlecode.cchlib.util.CollectionWrapper;
import com.googlecode.cchlib.util.SetWrapper;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrapperException;

public class PropertiesMap implements Map<String,String>, Serializable
{
    private static final long serialVersionUID = 1L;
    private Properties properties;
    private Wrappable<Object,String> keyOrValueWrapper = new Wrappable<Object,String>() {
        @Override
        public String wrap( Object obj ) throws WrapperException
        {
            return (String)obj;
        }
    };
    private Wrappable<String,Object> keyOrValueUnwrapper  = new Wrappable<String,Object>() {
        @Override
        public Object wrap( String obj ) throws WrapperException
        {
            return obj;
        }
    };
    private Wrappable<Map.Entry<Object,Object>,Map.Entry<String,String>> entryWrapper = new Wrappable<Map.Entry<Object,Object>,Map.Entry<String,String>>() {
        @Override
        public Map.Entry<String,String> wrap( Map.Entry<Object,Object> obj )
            throws WrapperException
        {
            return new XEntry<String,String>( (String)obj.getKey(), (String)obj.getValue() );
        }
    };
    private Wrappable<Map.Entry<String,String>,Map.Entry<Object,Object>> entryUnwrapper = new Wrappable<Map.Entry<String,String>,Map.Entry<Object,Object>>() {
        @Override
        public Map.Entry<Object,Object> wrap( Map.Entry<String,String> obj )
            throws WrapperException
        {
            return new XEntry<Object,Object>( obj.getKey(), obj.getValue() );
        }
    };

    public PropertiesMap( Properties properties )
    {
        this.properties = properties;
    }
    
    public PropertiesMap()
    {
        this( new Properties() );
    }

    @Override
    public int size()
    {
        return properties.size();
    }

    @Override
    public boolean isEmpty()
    {
        return properties.isEmpty();
    }

    @Override
    public boolean containsKey( Object key )
    {
        return properties.containsKey( key );
    }

    @Override
    public boolean containsValue( Object value )
    {
        return properties.containsValue( value );
    }

    @Override
    public String get( Object key )
    {
        return (String)properties.get( key );
    }

    @Override
    public String put( String key, String value )
    {
        return (String)properties.put( key, value );
    }

    @Override
    public String remove( Object key )
    {
        return (String)properties.remove( key );
    }

    @Override
    public void putAll( Map<? extends String, ? extends String> m )
    {
        properties.putAll( m );
    }

    @Override
    public void clear()
    {
        properties.clear();
    }

    @Override
    public Set<String> keySet()
    {
        return new SetWrapper<Object,String>(properties.keySet(), keyOrValueWrapper, keyOrValueUnwrapper);
    }

    @Override
    public Collection<String> values()
    {
        return new CollectionWrapper<Object,String>(properties.values(), keyOrValueWrapper, keyOrValueUnwrapper);
    }

    @Override
    public Set<Map.Entry<String,String>> entrySet()
    {
        return new SetWrapper<Map.Entry<Object,Object>,Map.Entry<String,String>>( properties.entrySet(), entryWrapper, entryUnwrapper);
    }

    public void store(Writer w, String comment) throws IOException
    {
        this.properties.store( w, comment );
    }

    public void store(OutputStream os, String comment) throws IOException
    {
        this.properties.store( os, comment );
    }

    public void storeToXML(OutputStream os, String comment) throws IOException
    {
        this.properties.storeToXML( os, comment );
    }

    public void storeToXML(OutputStream os, String comment, String encoding) throws IOException
    {
        this.properties.storeToXML( os, comment, encoding );
    }

    public void load(Reader r) throws IOException
    {
        this.properties.load( r );
    }

    public void load(InputStream inStream) throws IOException
    {
        this.properties.load( inStream );
    }

    public void loadFromXML(InputStream in) throws InvalidPropertiesFormatException, IOException
    {
        this.properties.loadFromXML( in );
    }

    private static class XEntry<K,V> implements Map.Entry<K,V>, Serializable
    {
        private static final long serialVersionUID = 1L;
        private K key;
        private V value;

        public XEntry( K key, V value )
        {
            this.key   = key;
            this.value = value;
        }

        @Override
        public K getKey()
        {
            return key;
        }

        @Override
        public V getValue()
        {
            return value;
        }

        @Override
        public V setValue( V value )
        {
            throw new UnsupportedOperationException();
        }
    }

    public static PropertiesMap load( File confFile ) throws IOException
    {
        PropertiesMap prop = new PropertiesMap();
        
        try( FileInputStream inStream = new FileInputStream( confFile ) ) {
            prop.load( inStream );
        }
        
        return prop;
    }
}
