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
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

/**
 *
 * @since 4.1.7
 */
//Not public
class SimpleTextPersistenceManagerV1
    implements URICachePersistenceManager
{
    private static final Logger logger = Logger.getLogger( SimpleTextPersistenceManagerV1.class );
    private static final String VERSION_STR = "V:1";

    /* (non-Javadoc) */
    @Override
    public void store( File cacheFile, CacheContent cache ) throws IOException
    {
        // store cache using simple text file.
        Writer w = new BufferedWriter( new FileWriter( cacheFile ) ); // $codepro.audit.disable questionableName

        try {
            w.append( VERSION_STR ).append( '\n' );

            for( Entry<URI,URIDataCacheEntry> entry : cache ) {
                storeEntry( w, entry );
                }

            w.flush();
            }
        finally {
            w.close();
            }
    }

    private void storeEntry(
            final Writer                           w,
            final Map.Entry<URI,URIDataCacheEntry> entry
            ) throws IOException
    {
        // First line is a valid URL
        // Can not be null (use of toExternalForm())
        w.append( entry.getKey().toASCIIString() ).append( '\n' );

        final URIDataCacheEntry cacheEntry = entry.getValue();

        // Second line must be a valid Long
        // Can not be null (use of getTime())
        w.append( Long.toString( cacheEntry.getDate().getTime() ) ).append( '\n' );

        // Third line is MD5 HashCode of download content
        // Can not be null (use of trim())
        w.append( cacheEntry.getContentHashCode().trim() ).append( '\n' );

        // Fourth line is the relative filename (could be empty)
        // Can not be null (use of getRelativeFilename())
        String filename = cacheEntry.getRelativeFilename();
        if( filename != null ) {
            w.append( filename );
            }
        w.append( '\n' );
    }

    /* (non-Javadoc) */
    @Override
    public void load( File cacheFile, CacheContent cache )
            throws FileNotFoundException, IOException, PersistenceFileBadVersionException
    {
        BufferedReader r = null; // $codepro.audit.disable questionableName

        try {
            r = new BufferedReader( new FileReader( cacheFile ) );

            {
                final String checkVersion = r.readLine();

                if( ! VERSION_STR.equals( checkVersion ) ) {
                    r.close();
                    r = null;
                    throw new PersistenceFileBadVersionException( checkVersion );
                    }
            }

            for(;;) {
                // First line
                URI    uri;
                String line = r.readLine();
                if( line == null ) {
                    // EOF
                    break;
                    }

                try {
                    uri = new URL( line ).toURI();
                    }
                catch( MalformedURLException e ) {
                    logger.error( "Bad URI format (ignore) in URLCache file : " + cacheFile
                            + " value = [" + line + "]",
                            e
                            );
                    uri = null;
                    }
                catch( URISyntaxException e ) {
                    logger.error( "Bad URI Syntax (ignore) in URLCache file : " + cacheFile
                            + " value = [" + line + "]",
                            e
                            );
                    uri = null;
                    }


                // Second line
                Date date;
                line = r.readLine();
                if( line == null ) {
                    // EOF (ignore entry)
                    break;
                    }

                try {
                    date = new Date( Long.parseLong( line ) );
                    }
                catch( NumberFormatException e ) {
                    logger.error( "Bad DATE format (use 0) in URLCache file : " + cacheFile
                            + " value = [" + line + "]",
                            e
                            );

                    date = new Date( 0 );
                    }

                // Third line
                String hashCode = r.readLine();
                if( hashCode == null ) {
                    break; // EOF (ignore entry)
                    }
                else if( hashCode.isEmpty() ) {
                    hashCode = null; // No hash code
                    }

                // Fourth line
                String filename = r.readLine();
                if( filename == null ) {
                    break; // EOF (ignore entry)
                    }
                // Add entry !
                cache.put( uri, new DefaultURICacheEntry( date, hashCode, filename ) );
                }
            }
        finally {
            if( r != null ) {
                r.close();
                }
            }
    }

}
