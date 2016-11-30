package com.googlecode.cchlib.util.zip;

import java.util.zip.ZipEntry;
import javax.annotation.Nonnull;
import org.apache.log4j.Logger;

final class LogZipListener implements ZipListener
{
    private static final Logger LOGGER = Logger.getLogger( LogZipListener.class );
    private long count;

    LogZipListener()
    {
        this.count = 0;
    }

    @Override
    public void entryPostProcessing(  @Nonnull final ZipEntry zipEntry )
    {
        final String name = zipEntry.getName();

        log( "Zip.entryPostProcessing: %s", name );
    }

    @Override
    public void entryAdded(  @Nonnull final ZipEntry zipEntry )
    {
        final String name = zipEntry.getName();

        this.count++;

        log( "Zip.entryAdded : %s (%5d)", name, this.count );
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
