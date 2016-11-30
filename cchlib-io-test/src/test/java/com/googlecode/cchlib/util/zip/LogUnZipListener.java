package com.googlecode.cchlib.util.zip;

import java.io.File;
import java.util.zip.ZipEntry;
import org.apache.log4j.Logger;

final class LogUnZipListener implements UnZipListener
{
    private static final Logger LOGGER = Logger.getLogger( LogUnZipListener.class );

    private long count;

    LogUnZipListener()
    {
        this.count = 0;
    }

    @Override
    public void entryPostProcessing( final UnZipEvent event )
    {
        final ZipEntry ze         = event.getZipEntry();
        final String   zename     = ze.getName();
        final File     outputFile = event.getFile();

        log( "UnZip.entryPostProcessing: %s -> %s",
            zename,
            outputFile
            );
    }

    @Override
    public void entryAdded( final UnZipEvent event )
    {
        this.count++;

        log( "UnZip.entryAdded: %s -> %s (%5d)",
            event.getZipEntry().getName(),
            event.getFile(),
            this.count
            );
    }

    private void log( final String format, final Object...args )
    {
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( String.format( format, args ) );
        }
    }

    public long getCount()
    {
        return this.count;
    }
}
