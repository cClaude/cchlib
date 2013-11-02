// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.editresourcesbundle;

import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import org.junit.Test;
import com.googlecode.cchlib.apps.editresourcesbundle.files.CustomProperties;
import com.googlecode.cchlib.apps.editresourcesbundle.files.FileObject;
import com.googlecode.cchlib.apps.editresourcesbundle.prefs.Preferences;
import com.googlecode.cchlib.io.filefilter.PatternFileFilter;
import com.googlecode.cchlib.test.FilesTestCaseHelper;
import com.googlecode.cchlib.test.SerializableTestCaseHelper;
import cx.ath.choisnet.util.FormattedProperties.Store;

/**
 * Test cases
 */
public class FilesConfigTest
{
    @Test
    public void test_Serializable1()
        throws  FileNotFoundException,
                IOException,
                ClassNotFoundException
    {
        test_Serializable( FilesConfig.FileType.FORMATTED_PROPERTIES );
    }

    @Test
    public void test_Serializable2()
        throws  FileNotFoundException,
                IOException,
                ClassNotFoundException
    {
        test_Serializable( FilesConfig.FileType.PROPERTIES );
    }

    private void test_Serializable( final FilesConfig.FileType fileType )
        throws  FileNotFoundException,
                IOException,
                ClassNotFoundException
    {
        Iterator<File>  files = FilesTestCaseHelper.getFilesFrom(
                                    new File("."),
                                    new PatternFileFilter(".*\\.properties")
                                    );
        // Hope there is at least 2 properties files
        File            lFile = files.next();
        //File            rFile = files.next();

        Preferences fakePref = Preferences.createDefaultPreferences();
        fakePref.setNumberOfFiles( 3 );
        FilesConfig     fc   = new FilesConfig( fakePref );
        fc.setFileType( fileType );
        FileObject leftFileObject = new FileObject( lFile, false );
        fc.setFileObject( leftFileObject, 0 );

        for( int i = 1; i<fc.getNumberOfFiles(); i++ ) {
            FileObject rightFileObject = new FileObject( files.next(), true );

            fc.setFileObject( rightFileObject, i  );
            }

        fc.load();

        FilesConfig fcCopy = new FilesConfig(fc);

//        assertEquals("Must be equals",fc,fcCopy);
        assertTrue( "FilesConfig must be equals.", fcc.compare( fc, fcCopy )==0);

        FilesConfig fcClone = SerializableTestCaseHelper.cloneOverSerialization( fc );

//        assertEquals("Must be equals",fc,fcClone);
        assertTrue( "FilesConfig must be equals.", fcc.compare( fc, fcClone )==0);
    }

    private final FilesConfigComparator fcc = new FilesConfigComparator();
    class FilesConfigComparator implements Comparator<FilesConfig>
    {
        Comparator<FileObject> foComparator = new Comparator<FileObject>()
        {
            @Override
            public int compare( FileObject o1, FileObject o2 )
            {
                if( o1.isReadOnly() != o2.isReadOnly() ) {
                    return 1;
                    }

                // o1.getCustomProperties();

                return o1.getFile().compareTo( o2.getFile() );
            }
        };
        Comparator<CustomProperties> cpComparator = new Comparator<CustomProperties>()
        {
            @Override
            public int compare( CustomProperties o1, CustomProperties o2 )
            {
                // TODO Auto-generated method stub
                //o1.getFileObject()
                Set<String> l1 = o1.stringPropertyNames();
                Set<String> l2 = o2.stringPropertyNames();

                if( ! l1.containsAll( l2 ) ) {
                    return 1;
                    }
                else if( ! l2.containsAll( l1 ) ) {
                    return -1;
                    }

                for( String key : l1 ) {
                    int res = o1.getProperty( key ).compareTo( o2.getProperty( key ) );

                    if( res != 0 ) {
                        return res;
                        }
                    }

                for( String key : l1 ) {
                    int res = o1.getLineNumber( key ) - o2.getLineNumber( key );

                    if( res != 0 ) {
                        return res;
                        }
                    }

                return 0;
            }
        };

        @Override
        public int compare( FilesConfig o1, FilesConfig o2 )
        {
            int res = o1.getNumberOfFiles() - o2.getNumberOfFiles();
            if( res != 0 ) {
                return res;
                }

            res = o1.getFileType().ordinal() - o2.getFileType().ordinal();
            if( res != 0 ) {
                return res;
                }

            EnumSet<Store> s1 = o1.getFormattedPropertiesStore();
            EnumSet<Store> s2 = o2.getFormattedPropertiesStore();

            if( ! s1.containsAll( s2 ) ) {
                return 1;
                }
            else if( ! s2.containsAll( s1 ) ) {
                return -1;
                }

            for( int i = 0; i<o1.getNumberOfFiles(); i++ ) {
                res = foComparator.compare( o1.getFileObject( i ), o2.getFileObject( i ) );
                if( res != 0 ) {
                    return res;
                    }
                }

            for( int i = 0; i<o1.getNumberOfFiles(); i++ ) {
                res = cpComparator.compare( o1.getCustomProperties( i ), o2.getCustomProperties( i ) );
                if( res != 0 ) {
                    return res;
                    }
                }

            return 0;
        }
    }
}
