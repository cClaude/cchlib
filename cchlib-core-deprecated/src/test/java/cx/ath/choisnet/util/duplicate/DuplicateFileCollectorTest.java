package cx.ath.choisnet.util.duplicate;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.io.FileHelper;

/**
 *
 */
@Deprecated
public class DuplicateFileCollectorTest
{
    private static final Logger LOGGER = Logger.getLogger( DuplicateFileCollectorTest.class );
    private static final int MAX_FILES_COUNT = 25;
    private static final long FILE_MAX_LENGTH = 1 * 1024 * 1024;

    @Deprecated
    @Test
    public void test_Base()
        throws  NoSuchAlgorithmException,
                FileNotFoundException,
                IOException
    {
        cx.ath.choisnet.util.checksum.MessageDigestFile       messageDigestFile = new cx.ath.choisnet.util.checksum.MessageDigestFile("MD5");
        DuplicateFileCollector  instance          = new DuplicateFileCollector( messageDigestFile, true );
        File                    root              = FileHelper.getUserHomeDirFile();
        Iterable<File>          files             = FileIteratorBuilder.createFileIterator(root, FILE_MAX_LENGTH, MAX_FILES_COUNT * 2);

        instance.addDigestEventListener(
                new DigestEventListener()
                {
                    long currentFileLength = 0;
                    long cumul = 0;
                    int countFile = 0;
                    boolean canNotCheckCumulSinceALeastOneFileLocked = false;

                    @Override
                    public void computeDigest( File file )
                    {
                        if( ! canNotCheckCumulSinceALeastOneFileLocked ) {
                            assertEquals("Bad cumul size!",currentFileLength,cumul);
                        }

                        LOGGER.info( "ComputeD:"+file);
                        currentFileLength = file.length();
                        cumul = 0;
                    }
                    @Override
                    public void ioError( IOException e, File file )
                    {
                        LOGGER.warn( "IOException "+file+" : "+e/*,e JUST A WARNING*/);
                        canNotCheckCumulSinceALeastOneFileLocked = true;
                    }
                    @Override
                    public void computeDigest( File file, long length )
                    {
                        //System.out.printf("in:%s - reading %d bytes\n",file,length);
                        cumul += length;
                    }
                    @Override
                    public boolean isCancel()
                    {
                        //return false;
                        return (countFile++) > MAX_FILES_COUNT;
                    }
                });

        LOGGER.info( "adding... : "+root );
        instance.pass1Add( files );
        instance.pass2();

        int dsc = instance.getDuplicateSetsCount();
        int dfc = instance.getDuplicateFilesCount();

        LOGGER.info("getDuplicateSetsCount: "+dsc);
        LOGGER.info("getDuplicateFilesCount: "+dfc);

        LOGGER.info("compute duplicate count");
        instance.computeDuplicateCount();

        LOGGER.info("getDuplicateSetsCount: "+instance.getDuplicateSetsCount());
        LOGGER.info("getDuplicateFilesCount: "+instance.getDuplicateFilesCount());

        assertEquals("getDuplicateSetsCount:",dsc,instance.getDuplicateSetsCount());
        assertEquals("getDuplicateFilesCount:",dfc,instance.getDuplicateFilesCount());

        LOGGER.info("remove non duplicate");
        instance.removeNonDuplicate();

        assertEquals("getDuplicateSetsCount:",dsc,instance.getDuplicateSetsCount());
        assertEquals("getDuplicateFilesCount:",dfc,instance.getDuplicateFilesCount());

        Map<String, Set<File>> map = instance.getFiles();

        for(Map.Entry<String,Set<File>> entry:map.entrySet()) {
            String      k = entry.getKey();
            Set<File>   s = entry.getValue();

            LOGGER.info( "'"+k+" : "+ s.size() );
            for(File f:s) {
                LOGGER.info( f );
                }
            }

        LOGGER.info( "done." );
    }
}
