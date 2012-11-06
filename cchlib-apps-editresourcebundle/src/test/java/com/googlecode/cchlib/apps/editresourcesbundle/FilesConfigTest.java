package com.googlecode.cchlib.apps.editresourcesbundle;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import org.junit.Test;
import com.googlecode.cchlib.io.filefilter.PatternFileFilter;
import cx.ath.choisnet.test.AssertHelper;
import cx.ath.choisnet.test.SerializableTestCaseHelper;

/**
 * TestCase
 */
public class FilesConfigTest 
{
    @Test
    public void test_Serializable()
        throws  FileNotFoundException,
                IOException,
                ClassNotFoundException
    {
        Iterator<File>  files = AssertHelper.getFilesFrom(
                                    new File("."),
                                    new PatternFileFilter(".*\\.properties")
                                    );
        // Hope there is at least 2 properties files
        File            lFile = files.next();
        //File            rFile = files.next();
        FilesConfig     fc    = new FilesConfig();

        FileObject leftFileObject = new FileObject(lFile,false);
        fc.setLeftFileObject( leftFileObject  );

        for( int i = 1; i<fc.getNumberOfFiles(); i++ ) {
            FileObject rightFileObject = new FileObject( files.next(), true );
            
            fc.setFileObject( rightFileObject, i  );
       		}

        fc.load();

        FilesConfig fcCopy = new FilesConfig(fc);

        assertEquals("Must be equals",fc,fcCopy);

        FilesConfig fcClone = SerializableTestCaseHelper.cloneOverSerialization( fc );

        assertEquals("Must be equals",fc,fcClone);
    }
}
