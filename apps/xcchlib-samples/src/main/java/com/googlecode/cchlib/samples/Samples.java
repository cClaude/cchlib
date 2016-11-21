package com.googlecode.cchlib.samples;

import java.awt.Image;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.resources.ResourcesLoader;
import com.googlecode.cchlib.resources.ResourcesLoaderException;

/**
 * Default resources for samples
 */
public class Samples
{
    private final static Logger LOGGER = Logger.getLogger( Samples.class );

    private Samples() {} // All static

    public static Image getSampleIconImage()
    {
        try {
            return ResourcesLoader.getImage( Samples.class, "sample.png" );
            }
        catch( ResourcesLoaderException e ) {
            LOGGER.error( "Can't load 'sample.png'", e );
            return null;
            }
   }
}
