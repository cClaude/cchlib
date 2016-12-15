package com.googlecode.cchlib.util.properties;

import java.util.Properties;
import org.apache.log4j.Logger;

//NOT public
final class Tools
{
    private Tools()
    {
        // All static
    }

    public static void logProperties( final Logger logger, final Properties properties )
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( "Properties" );
        for( final String s : properties.stringPropertyNames() ) {
            sb.append("\n\t").append(s).append('=').append(properties.getProperty( s )).append(';');
            }
        logger.info( sb.toString() );
    }
}
