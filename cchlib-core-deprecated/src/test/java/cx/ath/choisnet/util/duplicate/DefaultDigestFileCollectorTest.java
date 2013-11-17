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
import com.googlecode.cchlib.io.FileIterable;

@Deprecated
public class DefaultDigestFileCollectorTest
{
    private static final Logger LOGGER = Logger.getLogger( DefaultDigestFileCollectorTest.class );
    private static final int MAX_FILES_COUNT = 150;
	private static final long FILE_MAX_SIZE = 5 * 1024 * 1024;

	@Test
    public void test_Base()
        throws  NoSuchAlgorithmException,
                FileNotFoundException,
                IOException
    {
        DefaultDigestFileCollector instance = new DefaultDigestFileCollector();

        instance.addDigestEventListener( getDigestEventListener() );

        File            root  = FileHelper.getUserHomeDirFile();
        Iterable<File>  files = new FileIterable(
                root,
                new java.io.FileFilter()
                {
                    @Override
                    public boolean accept( File f )
                    {
                        if( f.isFile() && f.length() > 0 && f.length() < FILE_MAX_SIZE ) {
                            return true;
                        }
                        return false;
                    }
                }
                );

        LOGGER.info("adding... : "+root);

        try {
            instance.add( files );
            }
        catch( cx.ath.choisnet.util.CancelRequestException e ) {
            LOGGER.info("CancelRequestException (OBSOLETE) OK[" + e + "]" /*,e NOT AN ERROR */);
            }
        catch( com.googlecode.cchlib.util.CancelRequestException e ) {
            LOGGER.info("CancelRequestException OK[" + e + "]" /*,e NOT AN ERROR */);
            }

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

            LOGGER.info( k + " : " + s.size() );
            for(File f:s) {
                LOGGER.info( f );
                }
            }

        LOGGER.info( "done." );
    }

    private DigestEventListener getDigestEventListener()
    {
        return new DigestEventListener()
        {
            int countFile = 0;
            @Override
            public void computeDigest( File file )
            {
                LOGGER.info( "computeDigest["+file+"]" );
                countFile++;
            }
            @Override
            public void computeDigest( File file, long length )
            {//Partial compute
//                System.out.printf( "computeDigest[%s] length=%d\n", file, length );
            }
            @Override
            public void ioError( IOException e, File file )
            {
                LOGGER.warn("ioError["+e+"] file="+file);
            }
            @Override
            public boolean isCancel()
            {
                return countFile > MAX_FILES_COUNT;
            }
        };
    }
}
