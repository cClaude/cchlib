package com.googlecode.cchlib.swing.filechooser.accessory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import com.googlecode.cchlib.lang.StringHelper;

/**
 * Provide a basic implementation for
 * {@link com.googlecode.cchlib.swing.filechooser.accessory.BookmarksAccessory.LastSelectedFilesAccessoryConfigurator}
 * based on {@link Properties}
 */
public class DefaultBookmarksAccessoryConfigurator
    extends AbstractBookmarksAccessoryConfigurator
{
    private static final long serialVersionUID = 2L;
    private transient static final Logger logger = Logger.getLogger( DefaultBookmarksAccessoryConfigurator.class.getName() );
    /** @serial */
    private File configFileProperties;
    /** @serial */
    private List<File> bookmarks;
    /** @serial */
    private String keysPropertyPrefix;

    /**
     * Create a BookmarksAccessoryDefaultConfigurator using
     * default rules. Store a properties files under $user.home
     * with full name of this class.
     */
    public DefaultBookmarksAccessoryConfigurator()
    {
        this( getDefaultConfigFilePropertiesFile(),
            DefaultBookmarksAccessoryConfigurator.class.getSimpleName() + "."
            );
    }

    private static File getDefaultConfigFilePropertiesFile() {
        final File userHomeDirFile = new File( System.getProperty("user.home") );
        final File defaultConfigFilePropertiesFile = new File(
                userHomeDirFile,
                '.' + DefaultBookmarksAccessoryConfigurator.class.getName()
                );
        final File obsoleteConfigFilePropertiesFile = new File(
                userHomeDirFile,
                DefaultBookmarksAccessoryConfigurator.class.getName()
                );

        if( obsoleteConfigFilePropertiesFile.isFile() ) {
            if( defaultConfigFilePropertiesFile.isFile() ) {
                // Both exist, delete obsolete version
                obsoleteConfigFilePropertiesFile.delete();
                }
            else {
                // Just rename
                obsoleteConfigFilePropertiesFile.renameTo(defaultConfigFilePropertiesFile);
                }
            }
        
        return defaultConfigFilePropertiesFile;
    }

    /**
     * Create a BookmarksAccessoryDefaultConfigurator based
     * on giving properties file.
     *
     * @param configFileProperties File to use as input/output
     * properties
     */
    public DefaultBookmarksAccessoryConfigurator(
            final File      configFileProperties,
            final String    keysPropertyPrefix
            )
    {
        this.bookmarks              = new ArrayList<>();
        this.configFileProperties   = configFileProperties;
        this.keysPropertyPrefix     = keysPropertyPrefix;

        loadBookmarks();
    }

    private Properties loadProperties()
    {
        Properties properties = new Properties();

        try {
            InputStream is = new FileInputStream( configFileProperties );

            try {
                properties.load( is );
                }
            finally {
                is.close();
                }
            }
        catch( FileNotFoundException e ) {
            logger.warning( "Config file not found : " + configFileProperties + " - " + e );
            }
        catch( IOException e ) {
            logger.warning( "File error : " + configFileProperties + " - " + e );
            }

        return properties;
    }

    /**
     * Load bookmarks from a properties file.
     */
    private void loadBookmarks()
    {
        Properties  properties  = loadProperties();
        Set<String> keys        = properties.stringPropertyNames();

        for( String key : keys ) {
            if( key.startsWith( this.keysPropertyPrefix ) ) {
                String  value = properties.getProperty( key );
                File    file  = new File( value );

                add( file );
                }
            }

        Collections.sort( bookmarks );
    }

    @Override
    protected void storeBookmarks()
    {
        Properties      properties  = loadProperties();
        StringBuilder   sb          = new StringBuilder();
        int             i           = 0;

        for( File f : bookmarks ) {
            sb.setLength( 0 );
            sb.append( this.keysPropertyPrefix );
            sb.append( i++ );
            properties.put( sb.toString(), f.getPath() );
            }

        try {
            Writer writer = new FileWriter(configFileProperties);

            try {
                properties.store( writer, StringHelper.EMPTY );
                }
            finally {
                writer.close();
                }
            }
        catch( IOException e ) {
            logger.warning( "File error : " + configFileProperties + " - " + e );
            }
    }

    /**
     * {@inheritDoc}
     *
     * @return a sorted array of {@link File} objects
     */
    @Override
    public Collection<File> getBookmarks()
    {
        // be sure content is sorted
        Collections.sort( bookmarks );

        return bookmarks;
    }

    /**
     * Add file into bookmarks list if not already in this list
     * @param file File to add
     * @return true is file has been added, false otherwise
     */
    @Override
    protected boolean add( final File file )
    {
        if( file.isDirectory() ) {
            boolean found = false;

            for( File f : bookmarks ) {
                if( f.getPath().equals( file.getPath() ) ) {
                    found = true;
                    }
                }

            if( !found ) {
                bookmarks.add( file );
                return true;
                }
            }
        return false;
    }

    @Override
    public boolean removeBookmark( File file )
    {
        bookmarks.remove( file );

        storeBookmarks();

        return true;
    }
}
