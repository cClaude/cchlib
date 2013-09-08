package com.googlecode.cchlib.tools;

import java.awt.Image;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.resources.ResourcesLoader;
import com.googlecode.cchlib.resources.ResourcesLoaderException;

/**
 * Just here to get {@link Tools}.class
 */
public class Tools 
{
    private final static Logger logger = Logger.getLogger( Tools.class );
    private Tools() {} // All static

    public static Image getToolsIconImage()
    {
        try {
            return ResourcesLoader.getImage( Tools.class, "tools.png" );
            }
        catch( ResourcesLoaderException e ) {
            logger.error( "Can't load 'tools.png'", e );
            return null;
            }
   }
}
