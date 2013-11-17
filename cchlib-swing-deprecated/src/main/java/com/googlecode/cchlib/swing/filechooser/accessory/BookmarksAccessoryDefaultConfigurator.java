package com.googlecode.cchlib.swing.filechooser.accessory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;
import com.googlecode.cchlib.lang.StringHelper;

/**
 * @deprecated use {@link com.googlecode.cchlib.swing.filechooser.accessory#DefaultBookmarksAccessoryConfigurator} instead
 */
@Deprecated
public class BookmarksAccessoryDefaultConfigurator
    extends AbstractBookmarksAccessoryConfigurator
{
    private static final long serialVersionUID = 2L;
    private static final Logger LOGGER = Logger.getLogger( BookmarksAccessoryDefaultConfigurator.class.getName() );
    /** @serial */
    private File configFileProperties;
    /** @serial */
    private ArrayList<File> bookmarks;

    /**
     * Create a BookmarksAccessoryDefaultConfigurator using
     * default rules. Store a properties files under $user.home
     * with full name of this class.
     */
    public BookmarksAccessoryDefaultConfigurator()
    {
        this(
            new File(
                new File( System.getProperty("user.home") ),
                BookmarksAccessoryDefaultConfigurator.class.getName()
                )
            );
    }

    /**
     * Create a BookmarksAccessoryDefaultConfigurator based
     * on giving properties file.
     * 
     * @param configFileProperties File to use as input/output
     * properties
     */
    public BookmarksAccessoryDefaultConfigurator(
            final File configFileProperties
            )
    {
        //super( loadBookmarks(configFileProperties) );
        this.bookmarks = new ArrayList<File>();
        loadBookmarks(configFileProperties);
        
        this.configFileProperties = configFileProperties;
    }

    /**
     * Load bookmarks from a properties file.
     * @param configFileProperties Properties file
     * @return a sorted array of {@link File} objects
     */
    private void loadBookmarks(
            final File configFileProperties
            )
    {
        Properties      properties = new Properties();

        try {
            InputStream is = new FileInputStream(configFileProperties);
            properties.load( is );
            is.close();
            }
        catch( IOException e ) {
            LOGGER.warning( "File error : " + configFileProperties + " - " + e );
            }

        Set<String> keys = properties.stringPropertyNames();

        for(String key:keys) {
            String  value = properties.getProperty( key );
            File    file  = new File( value );

            add( file );
            }
        
        Collections.sort( bookmarks );
    }

    @Override
    protected void storeBookmarks(/*final ArrayList<File> list*/)
    {
        Properties  properties  = new Properties();
        int         i           = 0;

        for( File f : bookmarks ) {
            properties.put( Integer.toString( i++ ), f.getPath() );
            }

        try {
            Writer writer = new FileWriter(configFileProperties);
            properties.store( writer, StringHelper.EMPTY );
            writer.close();
            }
        catch( IOException e ) {
            LOGGER.warning( "File error : " + configFileProperties + " - " + e );
            }
    }

    @Override
    public Collection<File> getBookmarks()
    {
        return this.bookmarks;
    }

    @Override
    public boolean removeBookmark( File file )
    {
        boolean res = bookmarks.remove( file );
        
        if( res ) {
            storeBookmarks();
            }
        
        return res;
    }

    @Override
    protected boolean add( File file )
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
}
