package com.googlecode.cchlib;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Library Version object
 *
 * @since 4.1.7
 */
public final class Version
{
    private final String name;
    private final String version;
    private final Date date;

    /**
     * Create library Version object
     *
     * @throws IOException if any I/O occur
     * @throws ParseException if build.date is not a valid date
     *        according to maven.build.timestamp.format
     */
    public Version() throws IOException, ParseException
    {
        final String     filename = "/version.properties";
        final Properties prop     = new Properties();

        {
            final InputStream is = Version.class.getResourceAsStream( filename );

            if( is == null ) {
                throw new FileNotFoundException( filename );
                }
            else {
                try {
                    prop.load( is );
                    }
                finally {
                    is.close();
                    }
                }
        }

        this.name    = prop.getProperty( "project.name" );
        this.version = prop.getProperty( "project.version" );

        final String            buildDateFMT = prop.getProperty( "maven.build.timestamp.format" );
        final SimpleDateFormat  sdf          = new SimpleDateFormat( buildDateFMT );

        String buildDate = prop.getProperty( "build.date" );

        if( "${maven.build.timestamp}".equals( buildDate ) ) {
            //
            // Mock for development !
            //
            buildDate = sdf.format( new Date() );
            }

        this.date = sdf.parse( buildDate );
    }

    /**
     * @return API Name
     */
    public final String getName()
    {
        return name;
    }

    /**
     * @return API Version as a String
     */
    public final String getVersion()
    {
        return version;
    }

    /**
     * @return API build date
     */
    public final Date getDate()
    {
        return new Date( date.getTime() );
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("Version [name=");
        builder.append( name );
        builder.append(", version=");
        builder.append( version );
        builder.append(", date=");
        builder.append( date );
        builder.append(']');
        return builder.toString();
    }

    /**
     * Print to stdout version
     *
     * @param args CLI parameters
     */
    public static void main( final String[] args )
    {
        try {
            final Version instance = new Version();

            System.out.println( instance );
            }
        catch( IOException | ParseException e ) {
            e.printStackTrace( System.err );
            }
    }
}
