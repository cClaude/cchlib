package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 *
 */
public class FilePathTest
{
    private static final Logger logger = Logger.getLogger( FilePathTest.class );

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void myTest()
    {
        File[] tstDatas = {
            new File( "C:/temps" ),
            new File( "C:/temps/1" ),
            File.listRoots()[ 0 ],
            new File( "C:/temps/1/2/3" ),
            /* */
            };

        for( File f : tstDatas ) {
            FilePath filePath = new FilePath( f );

            tstFilePath( filePath );
            }
        
        logger.info( ">> FilePathTest myTest() ** DONE <<");
    }

    private void tstFilePath( FilePath filePath )
    {
        int count = 0;

        logger.info( ">> test FilePath =" + filePath + " <<");

        for( File f : filePath ) {
            int index = filePath.size() - 1 - count;
            
            logger.info( "1 test file: " + f );
            logger.info( "2 test file[" + index + "]: " + filePath.getFilePart( index ) );
            Assert.assertEquals( filePath.getFilePart( index ), f );
            count++;
            }

        Assert.assertEquals( "Bad size or bad iterator", filePath.size(), count );

        // Test tout les cas de figure de l'iterateur startFrom()
        for( int firstElementIndex = filePath.size() - 1; firstElementIndex>=0; firstElementIndex-- ) {
            // init le compteur
            count = filePath.size() - 1 - firstElementIndex;
            logger.info( "--- test from index =" + firstElementIndex );

            for( File f : filePath.startFrom( firstElementIndex ) ) {
                int index = filePath.size() - 1 - count;

                logger.info( "1 test file: " + f );
                logger.info( "2 test file[" + index + "]: " + filePath.getFilePart( index ) );
                Assert.assertEquals( filePath.getFilePart( index ), f );
                count++;
                }

            Assert.assertEquals( "Bad size or bad iterator", filePath.size(), count );
            }
    }

}
