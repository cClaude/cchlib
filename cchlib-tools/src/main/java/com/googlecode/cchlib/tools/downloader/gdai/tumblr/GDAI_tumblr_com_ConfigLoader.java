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
 * 
 *
 */
class GDAI_tumblr_com_ConfigLoader
{
    private final static Logger logger = Logger.getLogger( GDAI_tumblr_com_ConfigLoader.class );
    private PropertiesPopulator<GDAI_tumblr_com_ConfigLoader> pp = new PropertiesPopulator<GDAI_tumblr_com_ConfigLoader>( this.getClass() );
    
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
        catch( IOException e ) {
            logger.error( "Can't load config - ignore", e );
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
        return blogsNames.length;
    }

    public String getBlogsName( final int index )
    {
        return blogsNames[ index ];
    }
    
    public String getBlogsDescription( final int index )
    {
        try {
            return blogsDesc_[ index ];
            }
        catch( ArrayIndexOutOfBoundsException e ) {
            return null;
            }
    }
    
    private void loadConfig() throws IOException
    {
        try {
            Properties properties = PropertiesHelper.loadProperties( getConfigFile() );
            
            pp.populateBean( properties , this );
            }
        catch( IOException e ) {
            logger.warn( "Can't load config", e );
            throw e;
            }
        finally {
            if( blogsNames == null ) {
            	blogsNames = new String[ 0 ];
            	}
            if( blogsDesc_ == null ) {
            	blogsDesc_ = new String[ 0 ];
            	}
        	}
    }
    
    private void storeConfig() throws IOException
    {
        Properties properties = new Properties();

        pp.populateProperties( this, properties );

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