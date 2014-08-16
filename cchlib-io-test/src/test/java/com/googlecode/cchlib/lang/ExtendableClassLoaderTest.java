// $codepro.audit.disable staticFieldNamingConvention
package com.googlecode.cchlib.lang;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.io.IOHelper;

public class ExtendableClassLoaderTest
{
    private final static Logger LOGGER = Logger.getLogger( ExtendableClassLoaderTest.class );

    private static String compiledClassName;
    private static File compiledDirectoryFile;
    private static File compiledClassFile;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        final String      basename     = "TestDynamicClassBean";
        final String      filename     = basename + ".java";
        final String      packagename  = ExtendableClassLoaderTest.class.getPackage().getName();
        final String      fullfilename = packagename.replace( '.', File.separatorChar ) + File.separatorChar + filename;
        final InputStream is           = ExtendableClassLoaderTest.class.getClassLoader().getResourceAsStream( fullfilename );
        final File        tmpDirFile   = FileHelper.createTempDir();
        File              dirFile      = tmpDirFile;

        for( final String pn : packagename.split( "\\." ) ) {
            dirFile = new File( dirFile, pn );
            dirFile.mkdirs();
            }

        final File sourceFile = new File( dirFile, filename );

        if( is == null ) {
            LOGGER.error( "Resource not found:" + sourceFile );
            }
        try {
            IOHelper.copy( is, sourceFile );
            }
        finally {
            is.close();
            }

        LOGGER.info( "sourceFile = " + sourceFile );
        assertTrue( sourceFile.isFile() );

        compile( sourceFile );

        final File targetFile = new File(dirFile,  basename + ".class" );
        LOGGER.info( "targetFile = " + targetFile );
        assertTrue( targetFile.isFile() );

        compiledDirectoryFile  = tmpDirFile;
        compiledClassFile      = targetFile;
        compiledClassName      = packagename + '.' + basename;

        LOGGER.info( "compiledClassFile (ok) = " + compiledClassFile );
        LOGGER.info( "compiledDirectoryFile = " + compiledDirectoryFile );
        LOGGER.info( "compiledClassName = " + compiledClassName );
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {}

    @Before
    public void setUp() throws Exception
    {}

    @After
    public void tearDown() throws Exception
    {}

    @Test
    public void testAddClassPathFile() throws IOException, ClassNotFoundException
    {
        final ExtendableClassLoader ecl  = new ExtendableClassLoader();

        ecl.addClassPath( compiledDirectoryFile );

        final Class<?> clazz = ecl.loadClass( compiledClassName );
        LOGGER.info( "clazz = " + clazz );

        assertNotNull( clazz );
    }

//    @Test TODO public void testFindResourceString()
//    {
//        fail( "Not yet implemented" );
//    }

//    @Test TODO public void testLoadClassString()
//    {
//        fail( "Not yet implemented" );
//    }

//    @Test TODO public void testLoadClassStringBoolean()
//    {
//        fail( "Not yet implemented" );
//    }
//

    private static void compile( final File...files ) throws IOException
    {
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null)) {
            final Iterable<? extends JavaFileObject> compilationUnits1 =
                    fileManager.getJavaFileObjectsFromFiles(Arrays.asList(files));
            compiler.getTask(null, fileManager, null, null, null, compilationUnits1).call();
        }
    }
}
