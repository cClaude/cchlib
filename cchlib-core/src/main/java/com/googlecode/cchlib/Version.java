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
    private static Version service;
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
    private Version() throws IOException, ParseException
    {
        final String     filename = "/version.properties";
        final Properties prop     = new Properties();

        {
            @SuppressWarnings("resource")
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
        return this.name;
    }

    /**
     * @return API Version as a String
     */
    public final String getVersion()
    {
        return this.version;
    }

    /**
     * @return API build date
     */
    public final Date getDate()
    {
        return new Date( this.date.getTime() );
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("Version [name=");
        builder.append( this.name );
        builder.append(", version=");
        builder.append( this.version );
        builder.append(", date=");
        builder.append( this.date );
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
            final Version instance = Version.getInstance();

            System.out.println( instance );
            }
        catch( final Exception e ) {
            e.printStackTrace( System.err );
            }
    }

    public static Version getInstance()
    {
        if( service == null ) {
            try {
                service = new Version();
            }
            catch( IOException | ParseException e ) {
                throw new RuntimeException( e );
            }
        }
        return service;
    }
}
