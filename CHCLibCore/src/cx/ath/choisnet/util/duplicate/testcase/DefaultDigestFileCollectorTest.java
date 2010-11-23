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
import cx.ath.choisnet.util.duplicate.DefaultDigestFileCollector;
import junit.framework.TestCase;

/**
 * @author Claude
 *
 */
public class DefaultDigestFileCollectorTest
    extends TestCase 
{

    public void test_Base() 
        throws  NoSuchAlgorithmException,
                FileNotFoundException,
                IOException
    {
        DefaultDigestFileCollector instance = new DefaultDigestFileCollector();
        //File            root  = new File("C:/Documents and Settings/Claude/Mes documents");
        File            root  = TstCaseHelper.getUserHomeDirFile();
        Iterable<File>  files = new FileIterator(
                root,
                new java.io.FileFilter()
                {
                    @Override
                    public boolean accept( File f )
                    {
                        if( f.isFile() && f.length() > 0 ) {
                            System.out.println(f);
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
    }
}
