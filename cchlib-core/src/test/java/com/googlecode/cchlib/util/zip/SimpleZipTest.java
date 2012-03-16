package com.googlecode.cchlib.util.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.io.IOHelper;
import junit.framework.Assert;

/**
 * Test {@link SimpleZip} and {@link SimpleUnZip}
 */
public class SimpleZipTest
{
    final private static Logger slogger = Logger.getLogger(SimpleZipTest.class);

    public static final File TEMP_DIR_FILE = new File( System.getProperty("java.io.tmpdir" ) );
    public static final File ZIP_SOURCE_DIR_FILE = new File( new File("."), "src" );
    public static final File ZIP_DESTINATION_ZIP = new File( TEMP_DIR_FILE, "mysrc.zip" );

    public final static String UNZIP_ZIP_FILENAME  = "../metadata/lib/registry.jar";
    public final static File   UNZIP_DEST_DIR_FILE = new File( TEMP_DIR_FILE, "registry.jar" );

    @Test
    public void test_SimpleZip() throws IOException
    {
        SimpleZip instance = new SimpleZip(
                new FileOutputStream( ZIP_DESTINATION_ZIP )
                );
        ZipListener l = new ZipListener()
        {
            @Override
            public void entryPostProcessing(ZipEntry zipEntry)
            {
                slogger.info("ZipListener>entryPostProcessing: " + zipEntry.getName() );
            }
            @Override
            public void entryAdded(ZipEntry zipentry)
            {
                slogger.info("ZipListener>entryAdded: " + zipentry.getName() );
            }
        };

        instance.addZipListener( l );
        instance.addFolder(
                ZIP_SOURCE_DIR_FILE,
                new DefaultSimpleZipWrapper( ZIP_SOURCE_DIR_FILE )
                );
        instance.removeZipListener( l );
        instance.close();

        boolean del = ZIP_DESTINATION_ZIP.delete();

        Assert.assertTrue(
            "Can't delete: " + ZIP_DESTINATION_ZIP,
            del
            );
    }

    @Test
    public void test_SimpleSimpleUnZip() throws IOException
    {
        InputStream is       = new FileInputStream( UNZIP_ZIP_FILENAME );
        SimpleUnZip instance = new SimpleUnZip( is );

        UnZipListener l = new UnZipListener()
        {
            @Override
            public void entryPostProcessing(UnZipEvent event)
            {
                final ZipEntry ze         = event.getZipEntry();
                final String   zename     = ze.getName();
                final File     outputFile = event.getFile();

                slogger.info(
                    "UnZipListener>entryPostProcessing: "
                        + zename
                        + " -> "
                        + outputFile
                    );
            }
            @Override
            public void entryAdded(UnZipEvent event)
            {
                slogger.info(
                        "UnZipListener>entryAdded: "
                            + event.getZipEntry().getName()
                            + " -> "
                            + event.getFile()
                        );
            }
        };
        instance.addZipListener( l );
        instance.saveAll( UNZIP_DEST_DIR_FILE );
        instance.removeZipListener( l );
        instance.close();

        int count = instance.getFileCount();

        slogger.info( "Unzip file count:" + count );

        Assert.assertTrue( "No file to unzip: ", count > 0 );

        IOHelper.deleteTree( UNZIP_DEST_DIR_FILE );

        Assert.assertFalse(
            "Can't delete: " + UNZIP_DEST_DIR_FILE,
            UNZIP_DEST_DIR_FILE.exists()
            );
    }
}
