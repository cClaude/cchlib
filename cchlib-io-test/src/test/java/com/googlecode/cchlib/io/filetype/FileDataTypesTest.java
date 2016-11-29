// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.internationalization.useLocaleSpecificMethods
package com.googlecode.cchlib.io.filetype;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.googlecode.cchlib.io.FileFilterHelper;

/**
 *
 */
public class FileDataTypesTest
{
    private static final Logger LOGGER = Logger.getLogger( FileDataTypesTest.class );
    private final File dirFile = new File(
            new File( "." ).getAbsoluteFile().getParentFile().getParentFile(),
            "cchlib-core-sample/output/XXX"
            //"cchlib-core-sample/output/com.tumblr.www/XXX"
            );

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        // Not used yet
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
        // Not used yet
    }

    @Before
    public void setUp() throws Exception
    {
        // Not used yet
    }

    @After
    public void tearDown() throws Exception
    {
        // Not used yet
    }

    /**
     * Test method for {@link com.googlecode.cchlib.io.filetype.FileDataTypes#findDataTypeDescription(java.io.File)}.
     * @throws IOException
     * @throws FileNotFoundException
     */
    @Test
    public void testFindDataTypeDescription() throws FileNotFoundException, IOException
    {
        LOGGER.info( "check: " + this.dirFile );

        final File[] files = this.dirFile.listFiles( FileFilterHelper.fileFileFilter() );

        if( files == null ) {
            LOGGER.info( "No files for test (dir not found)" );
            }
        else {
            int identify = 0;
            int rename   = 0;

            LOGGER.info( "found " + files.length + " for test" );

            for( final File file : files ) {
                final FileDataTypeDescription desc = FileDataTypes.findDataTypeDescription( file );

                if( desc == null ) {
                    LOGGER.warn( "can find type of file: " + file );
                    }
                else {
                    LOGGER.info( "file: " + file + " is " + desc.getExtension() );
                    identify++;

                    if( file.getName().toLowerCase().endsWith( ".xxx" ) || (file.getName().indexOf( '.' ) == -1) ) {
                        final String oldName = file.getName();
                        final String name    = oldName.substring( 0, oldName.length() - 4 );
                        final String newName = name + desc.getExtension();

                        file.renameTo( new File( file.getParentFile(), newName ) );

                        rename++;
                        }
                    }
                }

            LOGGER.info( "identify:" + identify + " rename:" + rename + " on " + files.length );
            }
    }

    @Test
    public void testFindExtendedFileDataTypeDescription() throws IOException
    {
        LOGGER.info( "check: " + this.dirFile );

        final File[] files = this.dirFile.listFiles( FileFilterHelper.fileFileFilter() );

        if( files == null ) {
            LOGGER.info( "No files for test (dir not found)" );
            }
        else {
            int identify = 0;

            LOGGER.info( "found " + files.length + " for test" );

            for( final File file : files ) {
                final ExtendedFileDataTypeDescription desc = FileDataTypes.findExtendedFileDataTypeDescription( file );

                if( desc == null ) {
                    LOGGER.warn( "can find type of file: " + file );
                    }
                else {
                    LOGGER.info( "file: " + file + " is " + desc );

                    identify++;
                    }
                }

            LOGGER.info( "identify:" + identify + " on " + files.length );
            }
    }

}
