package com.googlecode.cchlib.util.zip;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.io.IOHelper;
import com.googlecode.cchlib.io.IOTestHelper;

/**
 * Test {@link SimpleZip} and {@link SimpleUnZip}
 */
public class SimpleUnZipTest
{
    private static final Logger LOGGER = Logger.getLogger( SimpleUnZipTest.class );

    // This value should be updated when number for files change in source :)
    private static final int EXPECTED_FILE_COUNT = 58;

    public File getUnzipDestDirFile() throws IOException
    {
        return IOTestHelper.createTempDirectory( SimpleUnZipTest.class );
    }

    @Test
    public void test_SimpleSimpleUnZip() throws IOException
    {
        final LogUnZipListener listener         = new LogUnZipListener();
        final File             unzipDestDirFile = getUnzipDestDirFile();
        final long             countAtBeginning;
        final long             countAtEnd;

        try( final InputStream is = IOTestHelper.createZipInputFile() ) {
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

        assertThat( countAtBeginning ).isEqualTo( 0 );
        assertThat( countAtEnd ).isEqualTo( listener.getCount() );
        assertThat( countAtEnd ).isEqualTo( EXPECTED_FILE_COUNT );

        final long count = SimpleUnZip.computeFilesCount( IOTestHelper.createZipInputFile() );

        assertThat( countAtEnd ).isEqualTo( count );
    }

    @Test(expected=FileNotFoundException.class)
    public void test_SimpleSimpleUnZip_computeFilesCount() throws IOException
    {
        final File fileNotExist = getUnzipDestDirFile();

        SimpleUnZip.computeFilesCount( fileNotExist );

        Assert.fail();
    }
}
