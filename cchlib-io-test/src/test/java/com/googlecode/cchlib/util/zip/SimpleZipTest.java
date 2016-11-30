package com.googlecode.cchlib.util.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test {@link SimpleZip} and {@link SimpleUnZip}
 */
public class SimpleZipTest
{
    static final File TEMP_DIR_FILE = new File( System.getProperty("java.io.tmpdir" ) );

    private static final File ZIP_SOURCE_DIR_FILE = new File( new File("."), "src" );
    private static final File ZIP_DESTINATION_ZIP = new File( TEMP_DIR_FILE, "mysrc.zip" );

    @Test
    public void test_SimpleZip() throws IOException
    {
       final ZipListener listener = new LogZipListener();

       try( final FileOutputStream os = new FileOutputStream( ZIP_DESTINATION_ZIP ) ) {
            try( final SimpleZip instance = new SimpleZip( os ) ) {
                instance.addZipListener( listener );
                instance.addFolder(
                        ZIP_SOURCE_DIR_FILE,
                        new DefaultSimpleZipWrapper( ZIP_SOURCE_DIR_FILE )
                        );
                instance.removeZipListener( listener );
                }
            }

        final boolean del = ZIP_DESTINATION_ZIP.delete();

        Assert.assertTrue(
            "Can't delete: " + ZIP_DESTINATION_ZIP,
            del
            );
    }
}
