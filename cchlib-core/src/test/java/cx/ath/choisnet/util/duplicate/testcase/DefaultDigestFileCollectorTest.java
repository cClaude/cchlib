/**
 * 
 */
package cx.ath.choisnet.util.duplicate.testcase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

import com.googlecode.cchlib.io.FileHelper;
import cx.ath.choisnet.io.FileIterator;
import cx.ath.choisnet.util.CancelRequestException;
import cx.ath.choisnet.util.duplicate.DefaultDigestFileCollector;
import cx.ath.choisnet.util.duplicate.DigestEventListener;
import junit.framework.TestCase;

/**
 *
 * @author Claude CHOISNET
 */
public class DefaultDigestFileCollectorTest
    extends TestCase 
{
    private static final transient Logger slogger = Logger.getLogger( DefaultDigestFileCollectorTest.class );
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
//                    int count = 0;
                    
                    @Override
                    public boolean accept( File f )
                    {
                        if( f.isFile() && f.length() > 0 ) {
//                            if( count > MAX_FILES_COUNT ) {
//                                return false;
//                            }
//                            count++;
                            return true;
                        }
                        return false;
                    }
                }
                );
        
        slogger.info("adding... : "+root);
        
        try {
            instance.add( files );
        }
        catch( CancelRequestException e ) {
            slogger.info("CancelRequestException",e);
        }
        
        int dsc = instance.getDuplicateSetsCount();
        int dfc = instance.getDuplicateFilesCount();
        
        slogger.info("getDuplicateSetsCount: "+dsc);
        slogger.info("getDuplicateFilesCount: "+dfc);
        
        slogger.info("compute duplicate count");
        instance.computeDuplicateCount();

        slogger.info("getDuplicateSetsCount: "+instance.getDuplicateSetsCount());
        slogger.info("getDuplicateFilesCount: "+instance.getDuplicateFilesCount());

        assertEquals("getDuplicateSetsCount:",dsc,instance.getDuplicateSetsCount());
        assertEquals("getDuplicateFilesCount:",dfc,instance.getDuplicateFilesCount());

        slogger.info("remove non duplicate");
        instance.removeNonDuplicate();
        
        assertEquals("getDuplicateSetsCount:",dsc,instance.getDuplicateSetsCount());
        assertEquals("getDuplicateFilesCount:",dfc,instance.getDuplicateFilesCount());
        
        Map<String, Set<File>> map = instance.getFiles();

        for(Map.Entry<String,Set<File>> entry:map.entrySet()) {
            String      k = entry.getKey();
            Set<File>   s = entry.getValue();
            
            slogger.info( k + " : " + s.size() );
            for(File f:s) {
                slogger.info( f );
            }
        }

        slogger.info( "done." );
    }
    
    private DigestEventListener getDigestEventListener()
    {
        return new DigestEventListener()
        {
            int countFile = 0;
            @Override
            public void computeDigest( File file )
            {
                slogger.info( "computeDigest["+file+"]" );
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
                //System.err.flush();
                //System.out.printf( "ioError[%s] file=%s\n", e, file );
                //System.out.flush();
                //e.printStackTrace(System.err);
                //System.err.flush();
                slogger.warn("ioError["+e+"] file="+file);
            }
            @Override
            public boolean isCancel()
            {
                //return false;
                return countFile > MAX_FILES_COUNT;
            }
        };
    }
}
