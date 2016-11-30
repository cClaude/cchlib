package com.googlecode.cchlib.util.zip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.io.IO;
import com.googlecode.cchlib.io.IOHelper;

/**
 * Test {@link SimpleZip} and {@link SimpleUnZip}
 */
public class SimpleUnZipTest
{
    private static final int EXPECTED_FILE_COUNT = 58;

    private static final Logger LOGGER = Logger.getLogger( SimpleUnZipTest.class );

    private static final File UNZIP_DEST_DIR_FILE = IO.createTempDirectory( SimpleUnZipTest.class/*, ".registry.jar"*/ );

    @Test
    public void test_SimpleSimpleUnZip() throws IOException
    {
        final LogUnZipListener listener         = new LogUnZipListener();
        final File             unzipDestDirFile = UNZIP_DEST_DIR_FILE;
        final long             countAtBeginning;
        final long             countAtEnd;

        try( final InputStream is = IO.createZipInputFile() ) {
            try( final SimpleUnZip instance = new SimpleUnZip( is ) ) {
                countAtBeginning = instance.getUnZippedFilesCount();

                instance.addZipListener( listener );
                instance.saveAll( unzipDestDirFile );
                instance.removeZipListener( listener );

                countAtEnd = instance.getUnZippedFilesCount();
                }
            }

        LOGGER.info( "Unzip.countAtBeginning=" + countAtBeginning );
        LOGGER.info( "Unzip.countAtEnd=" + countAtEnd );

        IOHelper.deleteTree( unzipDestDirFile );

        Assert.assertFalse(
            "Can't delete: " + unzipDestDirFile,
            unzipDestDirFile.exists()
            );

        Assertions.assertThat( countAtBeginning ).isEqualTo( 0 );
        Assertions.assertThat( countAtEnd ).isEqualTo( listener.getCount() );
        Assertions.assertThat( countAtEnd ).isEqualTo( EXPECTED_FILE_COUNT );

        final long count = SimpleUnZip.computeFilesCount( IO.createZipInputFile() );

        Assertions.assertThat( countAtEnd ).isEqualTo( count );
    }

    @Test(expected=FileNotFoundException.class)
    public void test_SimpleSimpleUnZip_computeFilesCount() throws IOException
    {
        final File fileNotExist = UNZIP_DEST_DIR_FILE;

        SimpleUnZip.computeFilesCount( fileNotExist );

        Assert.fail();
    }
}
