package com.googlecode.cchlib;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Library Version object
 *
 * @since 4.1.7
 */
@SuppressWarnings(
    "squid:S1700" // A field should not duplicate the name of its containing class ???
    )
public final class Version
{
    private static final Logger LOGGER = Logger.getLogger( Version.class );
    private static volatile Version service;

    private final String name;
    private final String version;
    private final Date   date;

    /**
     * Create library Version object
     *
     * @throws IOException if any I/O occur
     * @throws ParseException if build.date is not a valid date
     *        according to maven.build.timestamp.format
     */
    private Version() throws IOException, ParseException
    {
        final Properties prop = load();

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

    private static Properties load() throws IOException
    {
        final Properties prop = new Properties();

        try( final InputStream is = getVersionResourceAsStream() ) {
            prop.load( is );
            }

        return prop;
    }

    private static InputStream getVersionResourceAsStream() throws FileNotFoundException
    {
        final InputStream is = Version.class.getResourceAsStream( "/version.properties" );

        if( is == null ) {
            throw new FileNotFoundException( "/version.properties" );
            }

        return is;
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
     * @param args CLI parameters (ignored)
     */
    @SuppressWarnings({"squid:S1166","squid:S106"})
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
            synchronized( Version.class ) {
                if( service == null ) {
                    try {
                        service = new Version();
                    }
                    catch( IOException | ParseException e ) {
                        final String message = "Can't get version";

                        LOGGER.warn( message , e );

                        throw new VersionRuntimeException( message, e );
                    }
                }
            }
        }

        return service;
    }
}
