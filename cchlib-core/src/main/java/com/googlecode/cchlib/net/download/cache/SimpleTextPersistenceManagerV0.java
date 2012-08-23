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
    implements URLCachePersistenceManager 
{
	private final static Logger logger = Logger.getLogger( SimpleTextPersistenceManagerV0.class );

    /**
	 * Create an URLCachePersistenceManager
	 */
	public SimpleTextPersistenceManagerV0()
	{
	}

    /* (non-Javadoc) */
    @Override
    public void store( final File cacheFile, final CacheContent cache )
    	throws IOException
    {
        // store cache using simple text file.
        Writer w = new BufferedWriter( new FileWriter( cacheFile ) );

        for( Entry<URL,URLDataCacheEntry> entry : cache ) {
            final String contentHashCode = entry.getValue().getContentHashCode();

            if( contentHashCode != null ) {
                w.append( contentHashCode.trim() ).append( '\n' );
                }
            else {
                w.append( '\n' );
                }
            w.append( entry.getKey().toExternalForm() ).append( '\n' );
            w.append( Long.toString( entry.getValue().getDate().getTime() ) ).append( '\n' );
            w.append( entry.getValue().getRelativeFilename() ).append( '\n' );
            }
        w.flush();
        w.close();
    }

    /* (non-Javadoc) */
    @Override
    public void load( File cacheFile, CacheContent cache )
            throws FileNotFoundException, IOException
    {
        BufferedReader r = null;

        try {
            r = new BufferedReader( new FileReader( cacheFile ) );

            for(;;) {
                String hashCode = r.readLine();
                if( hashCode == null ) {
                    break; // EOF
                    }
                else if( hashCode.isEmpty() ) {
                    hashCode = null; // No hash code
                    }
                
                URL    url;
                String line = r.readLine();
                
                try {
                    url = new URL( line );
                    }
                catch ( MalformedURLException e ) {
                    logger .error( "Bad URL format (ignore) in URLCache file : " + cacheFile
                            + " value = [" + line + "]", 
                            e 
                            );
                    url = null;
                    }
                
                Date   date;
                line = r.readLine();
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
                String filename = r.readLine();

                if( url != null ) {
                    // Skip entry with no URL !
                    cache.put( url, new DefaultURLCacheEntry( date, hashCode, filename ) );
                    }
                }
            }
        finally {
            if( r != null ) {
                r.close();
                }
            }
    }
}
