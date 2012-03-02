package com.googlecode.cchlib.util.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Properties;

/**
 * Extra tools for {@link Properties}.
 */
public class PropertiesHelper
{
    private PropertiesHelper()
    {
        // All static
    }

    /**
     * TODOC
     *
     * @param propertiesFile
     * @throws IOException if any
    */
    public static Properties loadProperties(
        final File propertiesFile
        ) throws IOException
   {
       Properties  properties = new Properties();
       InputStream is         = new FileInputStream( propertiesFile );

       try {
           properties.load( is );
           }
       finally {
           is.close();
           }

       return properties;
       }

    /**
     * TODOC
     *
     * @param propertiesFile
     * @param properties
     * @param comment
     * @throws IOException if any
     */
    public static void saveProperties(
        final File          propertiesFile,
        final Properties    properties,
        final String        comment
        ) throws IOException
    {
        Writer writer = new FileWriter( propertiesFile );

        try {
            properties.store( writer, comment );
            }
        finally {
            writer.close();
            }
    }

    /**
     * TODOC
     *
     * @param propertiesFile
     * @param properties
     * @throws IOException if any I/O occur
     */
    public static void saveProperties(
        final File          propertiesFile,
        final Properties    properties
        ) throws IOException
    {
        saveProperties( propertiesFile, properties, "" );
    }
}
