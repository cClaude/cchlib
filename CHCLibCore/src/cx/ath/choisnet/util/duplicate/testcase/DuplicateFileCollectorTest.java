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
import cx.ath.choisnet.util.checksum.MessageDigestFile;
import cx.ath.choisnet.util.duplicate.DigestEventListener;
import cx.ath.choisnet.util.duplicate.DuplicateFileCollector;
import junit.framework.TestCase;

/**
 * @author Claude
 *
 */
public class DuplicateFileCollectorTest
    extends TestCase 
{

    public void test_Base() 
        throws  NoSuchAlgorithmException,
                FileNotFoundException,
                IOException
    {
        MessageDigestFile       messageDigestFile = new MessageDigestFile("MD5");
        DuplicateFileCollector  instance           = new DuplicateFileCollector( messageDigestFile, true );
        //File            root  = new File("C:/Documents and Settings/Claude/Mes documents");
        File            root  = TstCaseHelper.getUserHomeDirFile();
        Iterable<File>  files = new FileIterator(
                root,
                new java.io.FileFilter()
                {
                    @Override
                    public boolean accept( File f )
                    {
                        if( f.isFile() ) {
                            System.out.println(f);
                            return true;
                        }
                        return false;
                    }
                }
                );
        
        instance.addDigestEventListener( 
                new DigestEventListener()
                {
                    long currentFileLength = 0;
                    long cumul = 0;
                    
                    @Override
                    public void computeDigest( File file )
                    {
                        assertEquals("Bad cumul size!",currentFileLength,cumul);
                        
                        System.out.printf("Compute:%s\n",file);
                        currentFileLength = file.length();
                        cumul = 0;
                    }
                    @Override
                    public void ioError( IOException e, File file )
                    {
                        System.err.printf("IOException %s : %s\n",file,e);
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
                        return false;
                    }
                });
        
        System.out.printf("adding... : %s\n",root);
        instance.pass1Add( files );
        instance.pass2();
        
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
