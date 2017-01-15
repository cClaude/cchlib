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
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public abstract class AbstractSimpleTextPersistenceManagerV1 implements URICachePersistenceManager
{
    private static final Logger LOGGER = Logger.getLogger( AbstractSimpleTextPersistenceManagerV1.class );

    private final String version;

    public AbstractSimpleTextPersistenceManagerV1( final String version )
    {
        this.version = version;
    }

    protected abstract String convertEntryURL_URI_ToString( //
            final Entry<URI, URIDataCacheEntry> entry
            );

    protected abstract URI convertLineToURI( String line ) //
            throws URISyntaxException, MalformedURLException;

    @Override
    public void store(
        final File         cacheFile,
        final CacheContent cache
        ) throws IOException
    {
        // store cache using simple text file.
        try( final Writer w = new BufferedWriter( new FileWriter( cacheFile ) ) ) {
            w.append( this.version ).append( '\n' );

            for( final Entry<URI,URIDataCacheEntry> entry : cache ) {
                storeEntry( w, entry );
                }

            w.flush();
            }
    }

    protected void storeEntry(
        final Writer                           w,
        final Map.Entry<URI,URIDataCacheEntry> entry
        ) throws IOException
    {
        w.append( convertEntryURL_URI_ToString( entry ) ).append( '\n' );

        final URIDataCacheEntry cacheEntry = entry.getValue();

        // Second line must be a valid Long
        // Can not be null (use of getTime())
        w.append( Long.toString( cacheEntry.getDate().getTime() ) ).append( '\n' );

        // Third line is MD5 HashCode of download content
        // Can not be null (use of trim())
        w.append( cacheEntry.getContentHashCode().trim() ).append( '\n' );

        // Fourth line is the relative filename (could be empty)
        // Can not be null (use of getRelativeFilename())
        final String filename = cacheEntry.getRelativeFilename();

        if( filename != null ) {
            w.append( filename );
            }

        w.append( '\n' );
    }


    @Override
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public void load( final File cacheFile, final CacheContent cache )
        throws FileNotFoundException, IOException, PersistenceFileBadVersionException
    {
        try( final BufferedReader reader = new BufferedReader( new FileReader( cacheFile ) ) ) {
            checkVersion( reader );

            loadContent( cacheFile, cache, reader );
            }
    }

    protected void checkVersion( final BufferedReader reader ) //
            throws IOException, PersistenceFileBadVersionException
    {
        final String checkVersion = reader.readLine();

        if( ! this.version.equals( checkVersion ) ) {
            throw new PersistenceFileBadVersionException( checkVersion );
            }
    }

    protected void loadContent(
        final File           cacheFile,
        final CacheContent   cache,
        final BufferedReader reader
        ) throws IOException,
                 PersistenceFileBadVersionException
    {
        for(;;) {
            // First line
            final URI  uri;
            String     line = reader.readLine();
            if( line == null ) {
                // EOF
                break;
                }

            try {
                uri = convertLineToURI( line );
                }
            catch( final MalformedURLException e ) {
                LOGGER.error( "Bad URL/URI format (try next line) in cache file : " + cacheFile
                        + " value = [" + line + "]",
                        e
                        );
                continue;
                }
            catch( final URISyntaxException e ) {
                LOGGER.error( "Bad URL/URI syntaxe (try next line) in cache file : " + cacheFile
                        + " value = [" + line + "]",
                        e
                        );
                continue;
                }

            // Second line
            Date date;
            line = reader.readLine();
            if( line == null ) {
                // EOF (ignore entry)
                break;
                }

            try {
                date = new Date( Long.parseLong( line ) );
                }
            catch( final NumberFormatException e ) {
                LOGGER.error( "Bad DATE format (use 0) in cache file : " + cacheFile
                        + " value = [" + line + "]",
                        e
                        );

                date = new Date( 0 );
                }

            // Third line
            String hashCode = reader.readLine();
            if( hashCode == null ) {
                break; // EOF (ignore entry)
                }
            else if( hashCode.isEmpty() ) {
                hashCode = null; // No hash code
                }

            // Fourth line
            final String filename = reader.readLine();
            if( filename == null ) {
                break; // EOF (ignore entry)
                }

            // Add entry !
            cache.put( uri, new DefaultURICacheEntry( date, hashCode, filename ) );
            }
    }
}
