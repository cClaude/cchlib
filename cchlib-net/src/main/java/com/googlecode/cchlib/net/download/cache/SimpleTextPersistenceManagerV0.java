package com.googlecode.cchlib.net.download.cache;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

/**
 *
 * @since 4.1.7
 */
// Not public
class SimpleTextPersistenceManagerV0
    implements URICachePersistenceManager
{
    private static final Logger LOGGER = Logger.getLogger( SimpleTextPersistenceManagerV0.class );

    /**
    * Create an URLCachePersistenceManager
    */
    public SimpleTextPersistenceManagerV0()
    {
    }

    @Override
    public void store( final File cacheFile, final CacheContent cache ) throws IOException
    {
        // store cache using simple text file.
        try( final Writer w = new BufferedWriter( new FileWriter( cacheFile ) ) ) {
            for( final Entry<URI,URIDataCacheEntry> entry : cache ) {
                final String contentHashCode = entry.getValue().getContentHashCode();

                if( contentHashCode != null ) {
                    w.append( contentHashCode.trim() ).append( '\n' );
                    }
                else {
                    w.append( '\n' );
                    }
                w.append( entry.getKey().toASCIIString() ).append( '\n' );
                w.append( Long.toString( entry.getValue().getDate().getTime() ) ).append( '\n' );
                w.append( entry.getValue().getRelativeFilename() ).append( '\n' );
                }
            w.flush();
        }
    }

    /* (non-Javadoc) */
    @Override
    public void load( final File cacheFile, final CacheContent cache ) // $codepro.audit.disable cyclomaticComplexity
            throws FileNotFoundException, IOException
    {
        try( final BufferedReader reader = new BufferedReader( new FileReader( cacheFile ) ) ) {
            for(;;) {
                String hashCode = reader.readLine();
                if( hashCode == null ) {
                    break; // EOF
                    }
                else if( hashCode.isEmpty() ) {
                    hashCode = null; // No hash code
                    }

                URI    uri;
                String line = reader.readLine();
                if( line == null ) {
                    throw new PersistenceFileBadFormatException( "Expected URI found EOF" );
                    }

                try {
                    uri = new URL( line ).toURI(); // $codepro.audit.disable avoidInstantiationInLoops
                    }
                catch( final MalformedURLException e ) {
                    LOGGER.error( "Bad URI format (ignore) in URLCache file : " + cacheFile
                            + " value = [" + line + "]",
                            e
                            );
                    uri = null;
                    }
                catch( final URISyntaxException e ) {
                    LOGGER.error( "Bad URI Syntax (ignore) in URLCache file : " + cacheFile
                            + " value = [" + line + "]",
                            e
                            );
                    uri = null;
                    }

                Date   date;
                line = reader.readLine();
                if( line == null ) {
                    throw new PersistenceFileBadFormatException( "Expected Date found EOF" );
                    }

                try {
                    date = new Date( Long.parseLong( line ) ); // $codepro.audit.disable avoidInstantiationInLoops
                    }
                catch( final NumberFormatException e ) {
                    LOGGER.error( "Bad DATE format (use 0) in URLCache file : " + cacheFile
                            + " value = [" + line + "]",
                            e
                            );

                    date = new Date( 0 );
                    }

                final String filename = reader.readLine();
                if( filename == null ) {
                    throw new PersistenceFileBadFormatException( "Expected file name found EOF" );
                    }

                if( uri != null ) {
                    // Skip entry with no URL !
                    cache.put( uri, new DefaultURICacheEntry( date, hashCode, filename ) ); // $codepro.audit.disable avoidInstantiationInLoops
                    }
                }
            }
    }


}
