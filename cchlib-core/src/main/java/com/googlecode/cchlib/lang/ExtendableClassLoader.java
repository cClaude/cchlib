// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.doNotSubclassClassLoader
package com.googlecode.cchlib.lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.log4j.Logger;
import cx.ath.choisnet.ToDo;

/**
 * A custom {@link ClassLoader} able to add extra directory or jar
 */
@ToDo
public class ExtendableClassLoader extends ClassLoader
{
    private static final Logger LOGGER = Logger.getLogger( ExtendableClassLoader.class );
    private static final int BUFFER_SIZE = 4096;

    /** ArrayList preserve insert order */
    private final ArrayList<File>              paths = new ArrayList<File>();
    /** LinkedHashMap preserve insert order */
    private final LinkedHashMap<File,JarFile>  jars  = new LinkedHashMap<File,JarFile>();
    /** Loaded class, no order */
    private final Map<String,Class<?>>         cache = new HashMap<String,Class<?>>();

    /**
     * Creates a new ExtendableClassLoader using the <tt>ClassLoader</tt> returned by
     * the method {@link #getSystemClassLoader()
     * <tt>getSystemClassLoader()</tt>} as the parent class loader.
     *
     * @throws  SecurityException
     *          If a security manager exists and its
     *          <tt>checkCreateClassLoader</tt> method doesn't allow creation
     *          of a new class loader.
     */
    public ExtendableClassLoader()
    {
        super();
    }

    /**
     * Creates a new ExtendableClassLoader using the specified parent class loader for
     * delegation.
     *
     * @param  parent The parent class loader
     *
     * @throws  SecurityException
     *          If a security manager exists and its
     *          <tt>checkCreateClassLoader</tt> method doesn't allow creation
     *          of a new class loader.
     */
    public ExtendableClassLoader(
        final ClassLoader parent
        )
    {
        super( parent );
    }

    /**
     * Add path to this class loader
     *
     * @param path Path to add, could be a directory or a jar.
     * @throws IOException if any
     */
    public void addClassPath( final String path )
        throws IOException
    {
        addClassPath( new File( path ) );
    }

    /**
     * Add path to this class loader
     *
     * @param pathFile Path to add, could be a directory or a jar.
     * @throws IOException if any
     */
    public void addClassPath( final File pathFile )
        throws IOException
    {
        if( pathFile.isFile() ) {
            synchronized( jars ) {
                jars.put( pathFile, new JarFile( pathFile ) );
                }
            }
        else {
            synchronized( paths ) {
                paths.add( pathFile );
                }
            }
    }

// TODO should fail if a class from this path is in memory
//    /**
//     * Remove a path for this class loader.
//     *
//     * @param path
//     */
//    public void removeClassPath( final String path )
//    {
//        removeClassPath( new File( path ) );
//    }

// TODO should fail if a class from this path is in memory
//    /**
//     * Remove a path for this class loader.
//     *
//     * @param path
//     */
//    public void removeClassPath( final File path )
//    {
//        JarFile found;
//
//        synchronized( jars ) {
//            found = jars.remove(path);
//            }
//
//        if( found == null ) {
//            synchronized( paths ) {
//                paths.remove( path );
//                }
//            }
//    }

    /**
     * Try to get bytes array of the class from extra class path.
     * @param className The class name
     * @return a byte array with the stream content
     */
    private byte[] getClassFromAddedClassPaths(
        final String className
        )
    {
        final String fileName = className.replace('.', '/') + ".class";
        byte[]       result   = null;

        for( File path : this.paths ) {
            File f = new File(path, fileName); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.pathManipulation

            if( f.exists() ) {
                try {
                    result = toBytes( new FileInputStream( f ) );
                    }
                catch( IOException e ) {
                    LOGGER.warn( "Can not read : " + f, e );
                    //Try to continue anyway
                    }
                break;
                }
            }

        for( final Map.Entry<File,JarFile> entry : this.jars.entrySet() ) {
            JarFile  jarFile  = entry.getValue();
            JarEntry jarEntry = jarFile.getJarEntry( fileName );

            if( jarEntry != null ) {
                try {
                    result = toBytes( jarFile.getInputStream( jarEntry ) );
                    }
                catch( IOException e ) {
                    LOGGER.warn( "Can not read : " + jarEntry.getName() + " from : " + jarFile.getName(), e );
                    //Try to continue anyway
                    }
                break;
                }
            }

        return result;
    }

    @Override
    public URL findResource( final String name )
    {
        for( File path : this.paths) {
            File f = new File( path, name );

            if( f.exists() ) {
                try {
                    @SuppressWarnings("deprecation")
                    URL url = f.toURL();
                    return url;
                    }
                catch( MalformedURLException ignore ) {
                    throw new RuntimeException( ignore ); // Should not occur
                    }
                }
            }

        for( Map.Entry<File,JarFile> entry : this.jars.entrySet() ) {
            JarFile     jarFile  = entry.getValue();
            JarEntry    jarEntry = jarFile.getJarEntry(name);

            if( jarEntry != null ) {
                try {
                    @SuppressWarnings("deprecation")
                    URL jarURL = entry.getKey().toURL();

                    return new URL( "jar:" + jarURL.toString() + "!/" + jarEntry.getName() );
                    }
                catch(MalformedURLException ignore) {
                    throw new RuntimeException( ignore ); // Should not occur
                    }
                }
            }

        return null;
    }

    /**
     * Put inputStream content into a array of bytes, and close the stream.
     * @param inputStream
     * @return a byte array with the stream content
     * @throws IOException if any
     */
    protected static final byte[] toBytes(
        final InputStream inputStream
        ) throws IOException
    {
        try {
            final ByteArrayBuilder  result = new ByteArrayBuilder( inputStream.available() );
            final byte[]            buffer = new byte[ BUFFER_SIZE ];
            int                     len;

            while( (len = inputStream.read(buffer)) != -1 ) {
                result.append( buffer, 0, len );
                }

            return result.array();
            }
        finally {
            inputStream.close();
            }
    }

    @Override
    public Class<?> loadClass( final String className )
        throws ClassNotFoundException
    {
        return loadClass( className, true );
    }

    @Override
    public synchronized Class<?> loadClass(
            final String    className,
            final boolean   resolveIt
            )
        throws ClassNotFoundException
    {
        if( resolveIt ) {
            ClassLoader parent = getParent();

            if( parent != null ) {
                try {
                    return parent.loadClass( className );
                    }
                catch( ClassNotFoundException ignore ) { // $codepro.audit.disable emptyCatchClause, logExceptions
                    // Try again later
                    }
                }
            }

        Class<?> classResult = cache.get( className );

        if( classResult != null ) {
            return classResult;
            }

        byte[] classData;

        try {
            classResult = super.findSystemClass( className );

            return classResult;
            }
        catch( ClassNotFoundException ignore ) { // $codepro.audit.disable logExceptions
            classData = getClassFromAddedClassPaths( className );
            }

        if( classData == null ) {
            throw new ClassNotFoundException( className );
            }

        classResult = defineClass( className, classData, 0, classData.length );

        if( classResult == null ) {
            throw new ClassFormatError( className );
            }

        if( resolveIt ) {
            resolveClass( classResult );
            }

        cache.put( className, classResult );

        return classResult;
    }

    @Override
    protected Class<?> findClass( final String name )
        throws ClassNotFoundException
    {
        return loadClass(name);
    }
}
