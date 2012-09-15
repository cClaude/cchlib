package cx.ath.choisnet.util.duplicate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.io.FileIterator;
import junit.framework.TestCase;

@Deprecated
public class DefaultDigestFileCollectorTest
    extends TestCase
{
    private static final transient Logger logger = Logger.getLogger( DefaultDigestFileCollectorTest.class );
    private static final int MAX_FILES_COUNT = 150;

    public void test_Base()
        throws  NoSuchAlgorithmException,
                FileNotFoundException,
                IOException
    {
        DefaultDigestFileCollector instance = new DefaultDigestFileCollector();

        instance.addDigestEventListener( getDigestEventListener() );

        File            root  = FileHelper.getUserHomeDirFile();
        Iterable<File>  files = new FileIterator(
                root,
                new java.io.FileFilter()
                {
                    @Override
                    public boolean accept( File f )
                    {
                        if( f.isFile() && f.length() > 0 ) {
                            return true;
                        }
                        return false;
                    }
                }
                );

        logger.info("adding... : "+root);

        try {
            instance.add( files );
            }
        catch( cx.ath.choisnet.util.CancelRequestException e ) {
            logger.info("CancelRequestException (OBSOLETE) OK[" + e + "]" /*,e NOT AN ERROR */);
            }
        catch( com.googlecode.cchlib.util.CancelRequestException e ) {
            logger.info("CancelRequestException OK[" + e + "]" /*,e NOT AN ERROR */);
            }

        int dsc = instance.getDuplicateSetsCount();
        int dfc = instance.getDuplicateFilesCount();

        logger.info("getDuplicateSetsCount: "+dsc);
        logger.info("getDuplicateFilesCount: "+dfc);

        logger.info("compute duplicate count");
        instance.computeDuplicateCount();

        logger.info("getDuplicateSetsCount: "+instance.getDuplicateSetsCount());
        logger.info("getDuplicateFilesCount: "+instance.getDuplicateFilesCount());

        assertEquals("getDuplicateSetsCount:",dsc,instance.getDuplicateSetsCount());
        assertEquals("getDuplicateFilesCount:",dfc,instance.getDuplicateFilesCount());

        logger.info("remove non duplicate");
        instance.removeNonDuplicate();

        assertEquals("getDuplicateSetsCount:",dsc,instance.getDuplicateSetsCount());
        assertEquals("getDuplicateFilesCount:",dfc,instance.getDuplicateFilesCount());

        Map<String, Set<File>> map = instance.getFiles();

        for(Map.Entry<String,Set<File>> entry:map.entrySet()) {
            String      k = entry.getKey();
            Set<File>   s = entry.getValue();

            logger.info( k + " : " + s.size() );
            for(File f:s) {
                logger.info( f );
                }
            }

        logger.info( "done." );
    }

    private DigestEventListener getDigestEventListener()
    {
        return new DigestEventListener()
        {
            int countFile = 0;
            @Override
            public void computeDigest( File file )
            {
                logger.info( "computeDigest["+file+"]" );
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
                logger.warn("ioError["+e+"] file="+file);
            }
            @Override
            public boolean isCancel()
            {
                return countFile > MAX_FILES_COUNT;
            }
        };
    }
}
