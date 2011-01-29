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
import cx.ath.choisnet.io.FileIterator;
import cx.ath.choisnet.test.TstCaseHelper;
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
    protected static final int MAX_FILES_COUNT = 5000;

    public void test_Base() 
        throws  NoSuchAlgorithmException,
                FileNotFoundException,
                IOException, 
                CancelRequestException
    {
        DefaultDigestFileCollector instance = new DefaultDigestFileCollector();
        
        instance.addDigestEventListener( getDigestEventListener() );

        File            root  = TstCaseHelper.getUserHomeDirFile();
        Iterable<File>  files = new FileIterator(
                root,
                new java.io.FileFilter()
                {
                    int count = 0;
                    
                    @Override
                    public boolean accept( File f )
                    {
                        if( f.isFile() && f.length() > 0 ) {
                            if( count > MAX_FILES_COUNT ) {
                                return false;
                            }
                            count++;
                            return true;
                        }
                        return false;
                    }
                }
                );
        
        System.out.printf("adding... : %s\n",root);
        instance.add( files );
        
        int dsc = instance.getDuplicateSetsCount();
        int dfc = instance.getDuplicateFilesCount();
        
        System.out.printf("getDuplicateSetsCount: %d\n",dsc);
        System.out.printf("getDuplicateFilesCount: %d\n",dfc);
        
        System.out.println("compute duplicate count");
        instance.computeDuplicateCount();

        System.out.printf("getDuplicateSetsCount: %d\n",instance.getDuplicateSetsCount());
        System.out.printf("getDuplicateFilesCount: %d\n",instance.getDuplicateFilesCount());

        assertEquals("getDuplicateSetsCount:",dsc,instance.getDuplicateSetsCount());
        assertEquals("getDuplicateFilesCount:",dfc,instance.getDuplicateFilesCount());

        System.out.println("remove non duplicate");
        instance.removeNonDuplicate();
        
        assertEquals("getDuplicateSetsCount:",dsc,instance.getDuplicateSetsCount());
        assertEquals("getDuplicateFilesCount:",dfc,instance.getDuplicateFilesCount());
        
        Map<String, Set<File>> map = instance.getFiles();

        for(Map.Entry<String,Set<File>> entry:map.entrySet()) {
            String      k = entry.getKey();
            Set<File>   s = entry.getValue();
            
            System.out.printf( "%s : %d\n", k, s.size() );
            for(File f:s) {
                System.out.printf(  "%s\n", f );
            }
        }

        System.out.println( "\ndone." );
    }
    
    private DigestEventListener getDigestEventListener()
    {
        return new DigestEventListener()
        {
            @Override
            public void computeDigest( File file )
            {
                System.out.printf( "computeDigest[%s]\n", file );
            }
            @Override
            public void computeDigest( File file, long length )
            {//Partial compute
//                System.out.printf( "computeDigest[%s] length=%d\n", file, length );
            }
            @Override
            public void ioError( IOException e, File file )
            {
                System.err.flush();
                System.out.printf( "ioError[%s] file=%s\n", e, file );
                System.out.flush();
                e.printStackTrace(System.err);
                System.err.flush();
            }
            @Override
            public boolean isCancel()
            {
                return false;
            }
        };
    }
}
