package com.googlecode.cchlib.util.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A {@link Properties} with {@link File} support
 * 
 * @since 4.1.7
 */
public class PropertiesFile extends Properties
{
    private static final long serialVersionUID = 1L;
    private File file;

    /**
     * Creates an empty property list with no default values.
     * 
     * @param file File for this PropertiesFile
     */
    public PropertiesFile( final File file )
    {
        this( file, null );
    }

    /**
     * Creates an empty property list with the specified defaults.
     * 
     * @param defaults the defaults.
     */
    public PropertiesFile( final File file, final Properties defaults )
    {
        super(defaults);

        setFile( file );
    }

    /**
     * Get File for this PropertiesFile
     * 
     * @return File for this PropertiesFile
     * @see #save()
     * @see #save(String)
     * @see #load()
     */
    public File getFile()
    {
        return this.file;
    }

    /**
     * Set File for this PropertiesFile
     * 
     * @param file File for this PropertiesFile
     * @see #save()
     * @see #save(String)
     * @see #load()
     */
    public void setFile( final File file )
    {
        this.file = file;
    }

    /**
     * Create a {@link PropertiesFile} from file.
     *
     * @param file File to load.
     * @return initialized PropertiesFile
     * @throws IOException if any I/O occur
     * @see #load()
     * @see Properties#load(InputStream)
     */
    public static PropertiesFile createPropertiesFile( final File file ) throws IOException
    {
		return new PropertiesFile( file ).load();
    }

    /**
     * Load {@link Properties} from file.
     *
     * @return current object for initialization chaining
     * @throws IOException if any I/O occur
     * @see Properties#load(InputStream)
     * @see #getFile()
     */
    public PropertiesFile load() throws IOException
    {
        try (InputStream is = new FileInputStream( getFile() )) {
            load( is );
            }
        
		return this;
    }

    /**
     * Store properties content into a file.
     *
     * @param comment Extra comments for header (could be null)
     * @throws IOException if any I/O occur
     * @see #save()
     */
    public void save( final String comment ) throws IOException
    {
        PropertiesHelper.saveProperties( getFile(), this, comment );
    }

    /**
     * Store properties content into a file.
     *
     * @throws IOException if any I/O occur
     * @see #save(String)
     */
    public void save() throws IOException
    {
    	save( null );
    }
}
