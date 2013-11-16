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
    private final static Logger logger = Logger.getLogger( FileDataTypesTest.class );
    private final File dirFile = new File(
            new File( "." ).getAbsoluteFile().getParentFile().getParentFile(),
            "cchlib-core-sample/output/XXX"
            //"cchlib-core-sample/output/com.tumblr.www/XXX"
            );

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {}

    @Before
    public void setUp() throws Exception
    {}

    @After
    public void tearDown() throws Exception
    {}

    /**
     * Test method for {@link com.googlecode.cchlib.io.filetype.FileDataTypes#findDataTypeDescription(java.io.File)}.
     * @throws IOException
     * @throws FileNotFoundException
     */
    @Test
    public void testFindDataTypeDescription() throws FileNotFoundException, IOException
    {
        logger.info( "check: " + dirFile );

        File[] files = dirFile.listFiles( FileFilterHelper.fileFileFilter() );

        if( files == null ) {
            logger.info( "No files for test (dir not found)" );
            }
        else {
            int identify = 0;
            int rename   = 0;

            logger.info( "found " + files.length + " for test" );

            for( File file : files ) {
                FileDataTypeDescription desc = FileDataTypes.findDataTypeDescription( file );

                if( desc == null ) {
                    logger.warn( "can find type of file: " + file );
                    }
                else {
                    logger.info( "file: " + file + " is " + desc.getExtension() );
                    identify++;

                    if( file.getName().toLowerCase().endsWith( ".xxx" ) || (file.getName().indexOf( '.' ) == -1) ) {
                        String oldName = file.getName();
                        String name    = oldName.substring( 0, oldName.length() - 4 );
                        String newName = name + desc.getExtension();

                        file.renameTo( new File( file.getParentFile(), newName ) );

                        rename++;
                        }
                    }
                }

            logger.info( "identify:" + identify + " rename:" + rename + " on " + files.length );
            }
    }

    @Test
    public void testFindExtendedFileDataTypeDescription() throws IOException
    {
        logger.info( "check: " + dirFile );

        File[] files = dirFile.listFiles( FileFilterHelper.fileFileFilter() );

        if( files == null ) {
            logger.info( "No files for test (dir not found)" );
            }
        else {
            int identify = 0;

            logger.info( "found " + files.length + " for test" );

            for( File file : files ) {
                ExtendedFileDataTypeDescription desc = FileDataTypes.findExtendedFileDataTypeDescription( file );

                if( desc == null ) {
                    logger.warn( "can find type of file: " + file );
                    }
                else {
                    logger.info( "file: " + file + " is " + desc );

                    identify++;
                    }
                }

            logger.info( "identify:" + identify + " on " + files.length );
            }
    }

}
