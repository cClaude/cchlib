package com.googlecode.cchlib.lang;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.io.IOHelper;

@SuppressWarnings("resource")
public class ExtendableClassLoaderTest
{
    private static final String EXPECTED_STRING = "TEST-String";

    private static final Logger LOGGER = Logger.getLogger( ExtendableClassLoaderTest.class );

    private static String compiledClassName;
    private static File   compiledDirectoryFile;
    private static File   compiledClassFile;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        final String classname        = "TestDynamicClassBean";
        final String javaFilename     = classname + ".java";
        final String packagename      = ExtendableClassLoaderTest.class.getPackage().getName();
        final String fullJavaFilename = packagename.replace( '.', File.separatorChar ) + File.separatorChar + javaFilename;

        LOGGER.info( "classname       = " + classname );
        LOGGER.info( "javaFilename    = " + javaFilename );
        LOGGER.info( "packagename     = " + packagename );
        LOGGER.info( "fullJavaFilename= " + fullJavaFilename );

        final File tempRootDirFile  = createRootTempDir();
        final File dirForFile       = createDirectoryForJavaAndClassFiles( tempRootDirFile, packagename );
        final File sourceFile       = new File( dirForFile, javaFilename );
        sourceFile.deleteOnExit();

        LOGGER.info( "tempRootDirFile = " + tempRootDirFile );
        LOGGER.info( "dirForFile      = " + dirForFile );
        LOGGER.info( "sourceFile      = " + sourceFile );

        try( final InputStream resourceAsStream = getResourceAsStream( fullJavaFilename ) ) {
             assertThat( resourceAsStream )
                 .as( "Resource not found: " + fullJavaFilename )
                 .isNotNull();

             copyResource( resourceAsStream, sourceFile );
        }

        assertThat( sourceFile ).isFile();

        // Compile code
        final DiagnosticCollector<JavaFileObject> diag = CompilerHelper.compile( Collections.singleton( sourceFile ) );

        displayDiagnotic( diag );

        assertThat( diag.getDiagnostics() ).as( "Compilation error" ).isEmpty();

        final File classFile = new File( dirForFile, classname + ".class" );
        classFile.deleteOnExit();

        LOGGER.info( "classFile       = " + classFile );
        assertThat( classFile ).isFile();

        compiledDirectoryFile  = tempRootDirFile;
        compiledClassFile      = classFile;
        compiledClassName      = packagename + '.' + classname;

        LOGGER.info( "compiledClassFile     = " + compiledClassFile );
        LOGGER.info( "compiledDirectoryFile = " + compiledDirectoryFile );
        LOGGER.info( "compiledClassName     = " + compiledClassName );
    }

    private static File createRootTempDir()
    {
        final File file = FileHelper.createTempDir();

        file.deleteOnExit();

        return file;
    }

    private static File createDirectoryForJavaAndClassFiles(
        final File   tempRootDirFile,
        final String packagename
        )
    {
        File dirForFile = tempRootDirFile;

        for( final String pn : packagename.split( "\\." ) ) {
            dirForFile = new File( dirForFile, pn );
            dirForFile.mkdirs();
            dirForFile.deleteOnExit();
            }

        return dirForFile;
    }

    private static InputStream getResourceAsStream(
        final String fullJavaFilename
        )
    {
        // Use class loader to get the resource
        return ExtendableClassLoaderTest.class.getClassLoader().getResourceAsStream( fullJavaFilename );
    }

    private static void copyResource( final InputStream in, final File outFile )
        throws IOException
    {
        IOHelper.copy( in, outFile );
    }

    private static void displayDiagnotic( final DiagnosticCollector<JavaFileObject> diag )
    {
        final List<Diagnostic<? extends JavaFileObject>> diagnostics = diag.getDiagnostics();

        LOGGER.info( "diagnostics     = " + diagnostics );

        for( final Diagnostic<? extends JavaFileObject> diagEntry : diagnostics ) {
            LOGGER.error( "diagEntry = " + diagEntry );
            diagEntry.getMessage( null );
        }
    }

    @Test
    public void testAddClassPathFile()
        throws  IOException,
                ClassNotFoundException,
                InstantiationException,
                IllegalAccessException,
                NoSuchMethodException,
                SecurityException,
                IllegalArgumentException,
                InvocationTargetException
    {
        // Create a custom class loader.
        final ExtendableClassLoader ecl  = new ExtendableClassLoader();

        LOGGER.info( "addClassPath: " + compiledDirectoryFile );
        ecl.addClassPath( compiledDirectoryFile );

        LOGGER.info( "loadClass: " + compiledClassName );
        final Class<?> clazz = ecl.loadClass( compiledClassName );
        LOGGER.info( "clazz = " + clazz );

        assertThat( clazz ).isNotNull();

        final Object instance = clazz.newInstance();

        assertThat( instance ).isNotNull();

        final Method setValue = clazz.getMethod( "setValue", String.class );
        final Method getValue = clazz.getMethod( "getValue" );
        final Method length   = clazz.getMethod( "length" );

        Object actual = getValue.invoke( instance );
        LOGGER.debug( "getValue: " + actual );
        assertThat( actual ).isNull();

        actual = setValue.invoke( instance, EXPECTED_STRING );
        assertThat( actual ).isNull();

        actual = getValue.invoke( instance );
        LOGGER.debug( "getValue: " + actual );
        assertThat( actual ).isEqualTo( EXPECTED_STRING );

        actual = length.invoke( instance );
        assertThat( actual ).isEqualTo( EXPECTED_STRING.length() );
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

}
