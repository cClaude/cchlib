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
 * Provide a basic implementation for{@link LastSelectedFilesAccessoryConfigurator}
 * based on {@link Properties}
 */
public class DefaultBookmarksAccessoryConfigurator
    extends AbstractBookmarksAccessoryConfigurator
{
    private static final long serialVersionUID = 2L;
    private static final Logger LOGGER = Logger.getLogger( DefaultBookmarksAccessoryConfigurator.class.getName() );

    /** @serial */
    private final File configFileProperties;
    /** @serial */
    private final List<File> bookmarks;
    /** @serial */
    private final String keysPropertyPrefix;

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

    /**
     * Create a BookmarksAccessoryDefaultConfigurator based on
     * giving properties file.
     *
     * @param configFileProperties
     *            File to use as input/output properties
     * @param keysPropertyPrefix
     *            Key prefix for properties names
     */
    public DefaultBookmarksAccessoryConfigurator(
        final File   configFileProperties,
        final String keysPropertyPrefix
        )
    {
        this.bookmarks              = new ArrayList<>();
        this.configFileProperties   = configFileProperties;
        this.keysPropertyPrefix     = keysPropertyPrefix;

        loadBookmarks();
    }

    private static File getDefaultConfigFilePropertiesFile()
    {
        final File userHomeDirFile = new File( System.getProperty( "user.home" ) );
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
                delete( obsoleteConfigFilePropertiesFile );
                }
            else {
                // Just rename
                renameTo(
                    obsoleteConfigFilePropertiesFile,
                    defaultConfigFilePropertiesFile
                    );
                }
            }

        return defaultConfigFilePropertiesFile;
    }

    private static void delete( final File file )
    {
        @SuppressWarnings("squid:S1854")
        final boolean res = file.delete();

        assert res : "can not delete " + file;
    }

    private static void renameTo( final File fromFile, final File toFile )
    {
        @SuppressWarnings("squid:S1854")
        final boolean res = fromFile.renameTo( toFile );

        assert res : "can not rename " + fromFile + " to " + toFile;
    }

    private Properties loadProperties()
    {
        final Properties properties = new Properties();

        try( final InputStream is = new FileInputStream( this.configFileProperties ) ) {
            properties.load( is );
            }
        catch( final FileNotFoundException e ) {
            LOGGER.warning( "Config file not found : " + this.configFileProperties + " - " + e );
            }
        catch( final IOException e ) {
            LOGGER.warning( "File error : " + this.configFileProperties + " - " + e );
            }

        return properties;
    }

    /**
     * Load bookmarks from a properties file.
     */
    private void loadBookmarks()
    {
        final Properties  properties  = loadProperties();
        final Set<String> keys        = properties.stringPropertyNames();

        for( final String key : keys ) {
            if( key.startsWith( this.keysPropertyPrefix ) ) {
                final String  value = properties.getProperty( key );
                final File    file  = new File( value );

                add( file );
                }
            }

        Collections.sort( this.bookmarks );
    }

    @Override
    protected void storeBookmarks()
    {
        final Properties      properties  = loadProperties();
        final StringBuilder   sb          = new StringBuilder();
        int                   i           = 0;

        for( final File f : this.bookmarks ) {
            sb.setLength( 0 );
            sb.append( this.keysPropertyPrefix );
            sb.append( i++ );
            properties.put( sb.toString(), f.getPath() );
            }

        try( final Writer writer = new FileWriter( this.configFileProperties ) ) {
            properties.store( writer, StringHelper.EMPTY );
            }
        catch( final IOException e ) {
            LOGGER.warning( "File error : " + this.configFileProperties + " - " + e );
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
        Collections.sort( this.bookmarks );

        return this.bookmarks;
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

            for( final File f : this.bookmarks ) {
                if( f.getPath().equals( file.getPath() ) ) {
                    found = true;
                    }
                }

            if( !found ) {
                this.bookmarks.add( file );
                return true;
                }
            }
        return false;
    }

    @Override
    public boolean removeBookmark( final File file )
    {
        this.bookmarks.remove( file );

        storeBookmarks();

        return true;
    }
}
