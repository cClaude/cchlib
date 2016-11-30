package com.googlecode.cchlib.util.zip;

import java.io.File;
import java.util.zip.ZipEntry;
import org.apache.log4j.Logger;

final class LogUnZipListener implements UnZipListener
{
    private static final Logger LOGGER = Logger.getLogger( LogUnZipListener.class );

    @Override
    public void entryPostProcessing( final UnZipEvent event )
    {
        final ZipEntry ze         = event.getZipEntry();
        final String   zename     = ze.getName();
        final File     outputFile = event.getFile();

        LOGGER.info(
            "UnZipListener>entryPostProcessing: "
                + zename
                + " -> "
                + outputFile
            );
    }

    @Override
    public void entryAdded( final UnZipEvent event )
    {
        LOGGER.info(
                "UnZipListener>entryAdded: "
                    + event.getZipEntry().getName()
                    + " -> "
                    + event.getFile()
                );
    }
}
