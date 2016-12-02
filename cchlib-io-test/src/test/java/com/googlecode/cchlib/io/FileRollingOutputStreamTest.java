package com.googlecode.cchlib.io;

import static org.fest.assertions.Assertions.assertThat;
import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.lang.StringHelper;

@SuppressWarnings("resource")
public class FileRollingOutputStreamTest
{
    private static final Logger LOGGER = Logger.getLogger( FileRollingOutputStreamTest.class );

    static final byte[] BUFFER          = new byte[ 30 ];
    static final int    BUFFER_OFFSET   = BUFFER.length / 3;
    static final int    LEN             = BUFFER.length / 6;

    static {
        for( int i = 0; i<BUFFER.length; i++ ) {
            BUFFER[ i ] = (byte)(0x30 + i);
            }
        }

    @Test
    public void test1FileRollingOutputStream() throws IOException
    {
        testFileRollingOutputStream( 4096, 1 );
    }

    @Test
    public void test2FileRollingOutputStream() throws IOException
    {
        testFileRollingOutputStream( 5, 3 ); // 5 bytes only :)
    }

    @Test
    public void test3FileRollingOutputStream() throws IOException
    {
        testFileRollingOutputStream( 35, 2 );
        testFileRollingOutputStream( 36, 2 );
        testFileRollingOutputStream( 37, 2 );
    }

    public void testFileRollingOutputStream(
        final int maxsize,
        final int numberOfFiles
        ) throws IOException
    {
        final String assertMsgPrefix = "max:" + maxsize + " ->";
        final File logDirectoryFile = File.createTempFile(
                "FileRollingOutputStreamTest",
                StringHelper.EMPTY
                );
        logDirectoryFile.deleteOnExit();
        final FileRoller fileRoller = new DefaultFileRoller( logDirectoryFile );

        LOGGER.info( "work with " + logDirectoryFile );
        LOGGER.info( "maxsize " + maxsize );

        final FileRollingOutputStream os = new FileRollingOutputStream( fileRoller, maxsize );

        os.write( BUFFER[ 0 ] );
        long cLen = 1;
        assertThat( os.length() ).as( assertMsgPrefix ).isEqualTo( cLen );

        os.write( BUFFER );
        cLen += BUFFER.length;
        assertThat( os.length() ).as( assertMsgPrefix ).isEqualTo( cLen );

        os.write( BUFFER, BUFFER_OFFSET, LEN );
        cLen += LEN;
        assertThat( os.length() ).as( assertMsgPrefix ).isEqualTo( cLen );

        os.flush();

        LOGGER.info(
            assertMsgPrefix
                + "os.length() after flush = "
                + os.length()
            );

        os.close();
        assertThat( os.length() ).as( assertMsgPrefix ).isEqualTo( cLen );
        assertThat( os.getFileList() ).hasSize( numberOfFiles );

        int filelen = 0;

        for( final File file : os.getFileList() ) {
            LOGGER.info(
                assertMsgPrefix
                    + "result in " + file
                    + " (" + file.length() + ")"
                );
            filelen += file.length();

            if( file.length() > maxsize ) {
                LOGGER.warn(
                    assertMsgPrefix
                        + " length found is " + file.length()
                        );
                }

            file.delete(); // cleanup
            }

        assertThat( os.length() ).isEqualTo( filelen );
        assertThat( os.getMaxLength() ).isEqualTo( maxsize );
    }
}
