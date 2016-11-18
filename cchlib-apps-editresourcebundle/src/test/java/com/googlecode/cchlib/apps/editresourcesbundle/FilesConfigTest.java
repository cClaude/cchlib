// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import org.junit.Assert;
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
        final Iterator<File>  files = FilesTestCaseHelper.getFilesFrom(
                                    new File("."),
                                    new PatternFileFilter(".*\\.properties")
                                    );
        // Hope there is at least 2 properties files
        final File            lFile = files.next();
        //File            rFile = files.next();

        final Preferences fakePref = Preferences.createDefaultPreferences();
        fakePref.setNumberOfFiles( 3 );
        final FilesConfig     fc   = new FilesConfig( fakePref );
        fc.setFileType( fileType );
        final FileObject leftFileObject = new FileObject( lFile, false );
        fc.setFileObject( leftFileObject, 0 );

        for( int i = 1; i<fc.getNumberOfFiles(); i++ ) {
            final FileObject rightFileObject = new FileObject( files.next(), true ); // $codepro.audit.disable avoidInstantiationInLoops

            fc.setFileObject( rightFileObject, i  );
            }

        fc.load();

        final FilesConfig fcCopy = new FilesConfig(fc);

//        assertEquals("Must be equals",fc,fcCopy);
        Assert.assertTrue( "FilesConfig must be equals.", this.fcc.compare( fc, fcCopy )==0);

        final FilesConfig fcClone = SerializableTestCaseHelper.cloneOverSerialization( fc );

//        assertEquals("Must be equals",fc,fcClone);
        Assert.assertTrue( "FilesConfig must be equals.", this.fcc.compare( fc, fcClone )==0);
    }

    private final FilesConfigComparator fcc = new FilesConfigComparator();
    private static final class FilesConfigComparator implements Comparator<FilesConfig>
    {
        private static final Comparator<FileObject> FILE_OBJECT_COMPARATOR = ( o1, o2 ) -> {
            if( o1.isReadOnly() != o2.isReadOnly() ) {
                return 1;
                }

            // o1.getCustomProperties();

            return o1.getFile().compareTo( o2.getFile() );
        };
        private static final Comparator<CustomProperties> CUSTOM_PROPERTIES_COMPARATOR = ( o1, o2 ) -> {
            // TODO Auto-generated method stub
            //o1.getFileObject()
            final Set<String> l1 = o1.stringPropertyNames();
            final Set<String> l2 = o2.stringPropertyNames();

            if( ! l1.containsAll( l2 ) ) {
                return 1;
                }
            else if( ! l2.containsAll( l1 ) ) {
                return -1;
                }

            for( final String key1 : l1 ) {
                final int res1 = o1.getProperty( key1 ).compareTo( o2.getProperty( key1 ) );

                if( res1 != 0 ) {
                    return res1;
                    }
                }

            if( o1.isLinesNumberHandle() && o2.isLinesNumberHandle() ) {
                for( final String key2 : l1 ) {
                    final int res2 = o1.getLineNumber( key2 ) - o2.getLineNumber( key2 );

                    if( res2 != 0 ) {
                        return res2;
                        }
                    }
                }

            return 0;
        };

        @Override
        public int compare( final FilesConfig o1, final FilesConfig o2 )
        {
            int res = o1.getNumberOfFiles() - o2.getNumberOfFiles();
            if( res != 0 ) {
                return res;
                }

            res = o1.getFileType().ordinal() - o2.getFileType().ordinal();
            if( res != 0 ) {
                return res;
                }

            final Set<Store> s1 = o1.getFormattedPropertiesStore();
            final Set<Store> s2 = o2.getFormattedPropertiesStore();

            if( ! s1.containsAll( s2 ) ) {
                return 1;
                }
            else if( ! s2.containsAll( s1 ) ) {
                return -1;
                }

            for( int i = 0; i<o1.getNumberOfFiles(); i++ ) {
                res = FILE_OBJECT_COMPARATOR.compare( o1.getFileObject( i ), o2.getFileObject( i ) );
                if( res != 0 ) {
                    return res;
                    }
                }

            for( int i = 0; i<o1.getNumberOfFiles(); i++ ) {
                res = CUSTOM_PROPERTIES_COMPARATOR.compare( o1.getCustomProperties( i ), o2.getCustomProperties( i ) );
                if( res != 0 ) {
                    return res;
                    }
                }

            return 0;
        }
    }
}
