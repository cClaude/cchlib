package com.googlecode.cchlib.util.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import com.googlecode.cchlib.lang.StringHelper;

/**
 * Extra tools for {@link Properties}.
 */
public final class PropertiesHelper
{
    private PropertiesHelper()
    { // All static
    }

    /**
     * Create a {@link Properties} from a file.
     *
     * @param propertiesFile File to load.
     * @return a {@link Properties} filled with value found on file.
     * @throws IOException - if an error occurred when reading from the input stream.
     * @throws IllegalArgumentException - if the input stream contains a malformed Unicode escape sequence.
     * @see Properties#load(InputStream)
    */
    public static Properties loadProperties(
        final File propertiesFile
        ) throws IOException, IllegalArgumentException
   {
       InputStream is = new FileInputStream( propertiesFile );

       try {
           return loadProperties( is );
           }
       finally {
           is.close();
           }
       }

    /**
    * Create a {@link Properties} from a <code>ressourceName</code>
      *
     * @param classLoader   {@link ClassLoader} to use to find resource
     * @param resourceName  Resource name
     * @return a {@link Properties} filled with value found on resource.
     * @throws IOException - if an error occurred when reading from the input stream.
     * @throws IllegalArgumentException - if the input stream contains a malformed Unicode escape sequence.
     */
    public static Properties getResourceAsProperties(
        final ClassLoader classLoader,
        final String      resourceName
        ) throws IOException, IllegalArgumentException
    {
        InputStream is = classLoader.getResourceAsStream( resourceName );

        try {
            return loadProperties( is );
            }
        finally {
            is.close();
            }
    }

    private static Properties loadProperties( InputStream is ) throws IOException, IllegalArgumentException
    {
        Properties  properties = new Properties();
        properties.load( is );
        return properties;
    }

    /**
     * Store properties content into a file.
     *
     * @param propertiesFile    File to use
     * @param properties        Properties to store.
     * @param comment           Extra comments for header (could be null)
     * @throws IOException if any I/O occur
     */
    public static void saveProperties(
        final File          propertiesFile,
        final Properties    properties,
        final String        comment
        ) throws IOException
    {
        OutputStream os = new FileOutputStream( propertiesFile );

        try {
            properties.store( os, (comment == null) ? StringHelper.EMPTY : comment );
            }
        finally {
            os.close();
            }
    }

    /**
     * Store properties content into a file.
     *
     * @param propertiesFile    File to use
     * @param properties        Properties to store.
     * @throws IOException if any I/O occur
     * @see #saveProperties(File, Properties, String)
     */
    public static void saveProperties(
        final File          propertiesFile,
        final Properties    properties
        ) throws IOException
    {
        saveProperties( propertiesFile, properties, null );
    }

    /**
     * Create a new {@link Properties} object with shadow copy of <code>propertiesMap</code>
     *
     * @param propertiesMap Map to clone
     * @return a new {@link Properties}
     */
    public static Properties cloneFrom( final Map<String,String> propertiesMap )
    {
        Properties properties = new Properties();

        for( Map.Entry<String,String> entry : propertiesMap.entrySet() ) {
            properties.put( entry.getKey(), entry.getValue() );
            }

        return properties;
    }

}
