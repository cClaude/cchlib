package com.googlecode.cchlib.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.Test;

public class AlternateDataStreamTest
{
    public static final File TEMP_DIR_FILE = new File( System.getProperty("java.io.tmpdir" ) );

    @Test
    public void testXX() throws IOException
    {
        final File[] rootsFile = File.listRoots();

        for( final File r : rootsFile ) {
            System.out.printf( "Check: %s\n", r );
            final AlternateDataStream ads = new AlternateDataStream(r,"~ThisFileShouldNotExist.tmp","Test");

            System.out.printf( "AlternateDataStream supported for %s ? %b\n", r, Boolean.valueOf(ads.isSupported()) );

            if( ads.isSupported() ) {
                try( final FileOutputStream fos = new FileOutputStream(ads.getStreamFile()) ) {
                    fos.write( "test".getBytes() );
                }
                ads.getStreamSupportFile().delete();
                }
            }
    }
}

