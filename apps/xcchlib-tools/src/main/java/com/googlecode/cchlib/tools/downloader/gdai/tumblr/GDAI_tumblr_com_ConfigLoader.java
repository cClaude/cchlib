package com.googlecode.cchlib.tools.downloader.gdai.tumblr;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.util.properties.Populator;
import com.googlecode.cchlib.util.properties.PropertiesHelper;
import com.googlecode.cchlib.util.properties.PropertiesPopulator;

/**
 * Configuration for tumblr like blogs to download
 * <br>
 * File must be a propertie within :
 * <pre>
 * blogsNames.0=
 * blogsDesc_.0=
 * blogsNames.1=
 * blogsDesc_.1=
 * </pre>
 */
class GDAI_tumblr_com_ConfigLoader
{
    private static final Logger LOGGER = Logger.getLogger( GDAI_tumblr_com_ConfigLoader.class );

    private final PropertiesPopulator<GDAI_tumblr_com_ConfigLoader> pp = new PropertiesPopulator<GDAI_tumblr_com_ConfigLoader>( this.getClass() );

    @Populator
    private String[] blogsNames;
    @Populator
    private String[] blogsDesc_;

    /** load config
     * @throws IOException */
    public GDAI_tumblr_com_ConfigLoader()
    {
        try {
            loadConfig();
            }
        catch( final IOException e ) {
            LOGGER.error( "Can't load config - ignore", e );
            }
    }

    /**
     * For saver only
     */
    private GDAI_tumblr_com_ConfigLoader(
        final String[] blogsNames,
        final String[] blogsDescriptions
        )
    {
        this.blogsNames = blogsNames;
        this.blogsDesc_ = blogsDescriptions;

        if( blogsNames == null ) {
            throw new IllegalArgumentException( "blogsNames" );
            }
        if( blogsDescriptions == null ) {
            throw new IllegalArgumentException( "blogsDescriptions" );
            }
        if( blogsNames.length != blogsDescriptions.length ) {
            throw new IllegalArgumentException(
                "blogsNames and blogsDescriptions not same length !"
                );
            }
    }

    private static File getConfigFile()
    {
        return FileHelper.getUserHomeDirFile(
            "." + GDAI_tumblr_com.class.getSimpleName() + ".properties"
            );
    }

    public int getBlogsSize()
    {
        return this.blogsNames.length;
    }

    public String getBlogsName( final int index )
    {
        return this.blogsNames[ index ];
    }

    public String getBlogsDescription( final int index )
    {
        try {
            return this.blogsDesc_[ index ];
            }
        catch( final ArrayIndexOutOfBoundsException e ) {
            return null;
            }
    }

    private void loadConfig() throws IOException
    {
        try {
            final Properties properties = PropertiesHelper.loadProperties( getConfigFile() );

            this.pp.populateBean( properties , this );
            }
        catch( final IOException e ) {
            LOGGER.warn( "Can't load config", e );
            throw e;
            }
        finally {
            if( this.blogsNames == null ) {
            	this.blogsNames = new String[ 0 ];
            	}
            if( this.blogsDesc_ == null ) {
            	this.blogsDesc_ = new String[ 0 ];
            	}
        	}
    }

    private void storeConfig() throws IOException
    {
        final Properties properties = new Properties();

        this.pp.populateProperties( this, properties );

        PropertiesHelper.saveProperties( getConfigFile(), properties );
    }

    /**
     * Save config
     *
     * @throws IOException
     */
    public static void save(
        final String[] blogsNames,
        final String[] blogsDescriptions
        ) throws IOException
    {
        final GDAI_tumblr_com_ConfigLoader saver
            = new GDAI_tumblr_com_ConfigLoader( blogsNames, blogsDescriptions );

       saver.storeConfig();
    }
}