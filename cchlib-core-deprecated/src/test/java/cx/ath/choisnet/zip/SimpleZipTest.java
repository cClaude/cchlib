package cx.ath.choisnet.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import com.googlecode.cchlib.io.IOHelper;

/**
 * @deprecated
 */
public class SimpleZipTest
{
    final private static Logger slogger = Logger.getLogger(SimpleZipTest.class);

    public static final File TEMP_DIR_FILE = new File( System.getProperty("java.io.tmpdir" ) );
    public static final File ZIP_SOURCE_DIR_FILE = new File( new File("."), "src" );
    public static final File ZIP_DESTINATION_ZIP = new File( TEMP_DIR_FILE, "mysrc.zip" );

    public final static String UNZIP_ZIP_FILENAME  = "../lib/log4j-1.2.15.jar";
    public final static File   UNZIP_DEST_DIR_FILE = new File( TEMP_DIR_FILE, "log4j-1.2.15.jar" );

    @Test
    @Ignore
    public void testSimpleZip()
        throws java.io.IOException
    {
        SimpleZip instance = new SimpleZip(
                new FileOutputStream( ZIP_DESTINATION_ZIP )
                );
        instance.addPostProcessingListener(
                new ZipEventListener()
                {
                    @Override
                    public void newFile( ZipEntry zipentry )
                    {
                        slogger.info("PP>add: " + zipentry.getName() );
                    }
                });
        instance.addProcessingListener(
                new ZipEventListener()
                {
                    @Override
                    public void newFile( ZipEntry zipentry )
                    {
                        slogger.info("P>add: " + zipentry.getName() );
                    }
                });
        instance.addFolder(
                ZIP_SOURCE_DIR_FILE,
                new cx.ath.choisnet.zip.impl.SimpleZipEntryFactoryImpl( ZIP_SOURCE_DIR_FILE )
                {// Just to debug !
                    @Override
                    public SimpleZipEntry wrappe(File file)
                    {
                        SimpleZipEntry sze = super.wrappe(file);
                        slogger.info("add: " + file + " -> " + sze.getZipEntry().getName() );
                        return sze;
                    }
                });

        instance.close();

        boolean del = ZIP_DESTINATION_ZIP.delete();

        Assert.assertTrue( "Can't delete: " + ZIP_DESTINATION_ZIP, del);
    }

    @Test
    @Ignore
    public void testUnzip()
        throws java.io.IOException
    {
        InputStream is       = new FileInputStream( UNZIP_ZIP_FILENAME );
        SimpleUnZip instance = new SimpleUnZip( is );

        instance.addPostProcessingListener(
                new ZipEventListener()
                {
                    @Override
                    public void newFile( ZipEntry zipentry )
                    {
                        slogger.info("PP>ext: " + zipentry.getName() );
                    }
                });
        instance.addProcessingListener(
                new ZipEventListener()
                {
                    @Override
                    public void newFile( ZipEntry zipentry )
                    {
                        slogger.info("P>ext: " + zipentry.getName() );
                    }
                });
        instance.saveAll( UNZIP_DEST_DIR_FILE );
        instance.close();

        int count = instance.getFileCount();

        slogger.info( "Unzip file count:" + count );

        Assert.assertTrue( "No file to unzip: ", count > 0 );

        IOHelper.deleteTree( UNZIP_DEST_DIR_FILE );
    }
}
