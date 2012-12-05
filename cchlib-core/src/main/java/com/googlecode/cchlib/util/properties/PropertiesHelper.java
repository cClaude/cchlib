package com.googlecode.cchlib.util.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import com.googlecode.cchlib.Const;

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
     * Create a {@link Properties} from a file.
     *
     * @param propertiesFile File to load.
     * @throws IOException if any I/O occur
     * @see Properties#load(InputStream)
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
            properties.store( os, comment == null ? Const.EMPTY_STRING : comment );
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
}
