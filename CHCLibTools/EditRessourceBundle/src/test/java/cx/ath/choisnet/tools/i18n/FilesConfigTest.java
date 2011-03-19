package cx.ath.choisnet.tools.i18n;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import cx.ath.choisnet.io.PatternFileFilter;
import cx.ath.choisnet.test.SerializableTestCase;
import cx.ath.choisnet.test.TstCaseHelper;

/**
 * TestCase
 * @author Claude CHOISNET
 */
public class FilesConfigTest extends SerializableTestCase 
{
    public void test_Serializable() 
        throws  FileNotFoundException, 
                IOException,
                ClassNotFoundException
    {
        Iterator<File>  files = TstCaseHelper.getFilesFrom( 
                                    new File("."),
                                    new PatternFileFilter(".*\\.properties")
                                    );
        // Hope there is at least 2 properties files
        File            lFile = files.next(); 
        File            rFile = files.next(); 
        FilesConfig     fc    = new FilesConfig();
        
        FileObject leftFileObject = new FileObject(lFile,false);
        fc.setLeftFileObject( leftFileObject  );
        
        FileObject rightFileObject = new FileObject(rFile,true);
        fc.setRightFileObject( rightFileObject  );

        fc.load();
        
        FilesConfig fcCopy = new FilesConfig(fc);
        
        assertEquals("Must be equals",fc,fcCopy);
        
        FilesConfig fcClone = cloneOverSerialization( fc );

        assertEquals("Must be equals",fc,fcClone);
    }
}
