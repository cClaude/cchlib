package com.googlecode.cchlib.util.zip;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.io.IO;
import com.googlecode.cchlib.io.IOHelper;

/**
 * Test {@link SimpleZip} and {@link SimpleUnZip}
 */
public class SimpleUnZipTest
{
    private static final Logger LOGGER = Logger.getLogger( SimpleUnZipTest.class );
    private static final File UNZIP_DEST_DIR_FILE = new File( SimpleZipTest.TEMP_DIR_FILE, "registry.jar" );

    @Test
    public void test_SimpleSimpleUnZip() throws IOException
    {
        final UnZipListener listener = new LogUnZipListener();
        final int count;

        try( final InputStream is = IO.createZipInputFile() ) {
            try( final SimpleUnZip instance = new SimpleUnZip( is ) ) {
                instance.addZipListener( listener );
                instance.saveAll( UNZIP_DEST_DIR_FILE );
                instance.removeZipListener( listener );

                count = instance.getFileCount();
            }

            LOGGER.info( "Unzip file count:" + count );

            Assert.assertTrue( "No file to unzip: ", count > 0 );
            }

        IOHelper.deleteTree( UNZIP_DEST_DIR_FILE );

        Assert.assertFalse(
            "Can't delete: " + UNZIP_DEST_DIR_FILE,
            UNZIP_DEST_DIR_FILE.exists()
            );
    }
}
