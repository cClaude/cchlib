package com.googlecode.cchlib.util.zip;

import java.util.zip.ZipEntry;
import org.apache.log4j.Logger;

final class LogZipListener implements ZipListener
{
    private static final Logger LOGGER = Logger.getLogger( LogZipListener.class );

    @Override
    public void entryPostProcessing( final ZipEntry zipEntry )
    {
        LOGGER.info("ZipListener>entryPostProcessing: " + zipEntry.getName() );
    }

    @Override
    public void entryAdded( final ZipEntry zipentry )
    {
        LOGGER.info("ZipListener>entryAdded: " + zipentry.getName() );
    }
}