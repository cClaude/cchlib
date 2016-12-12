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
import javax.annotation.Nullable;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.NeedTestCases;

/**
 * A custom {@link ClassLoader} able to add extra directory or jar
 * <p>
 * This class implements a {@link ClassLoader} that allow to extend the
 * possible path to look up to load a class.
 * <p>
 * Based on SimpleClassLoader class proposed by Chuck McManis in
 * JavaWorld' article :
 * <p>
 * http://www.javaworld.com/javaworld/jw-10-indepth.html<br>
 * http://www.javaworld.com/javaworld/jw-10-1996/jw-10-indepth.html
 *
 * @author  C. McManis
 * @author  Olivier Dedieu
 * @author  Claude CHOISNET
 * @since   2.00
*/
@NeedDoc
@NeedTestCases
public class ExtendableClassLoader extends ClassLoader
{
    private static final Logger LOGGER = Logger.getLogger( ExtendableClassLoader.class );
    private static final int BUFFER_SIZE = 4096;
    private static final byte[] NOT_FOUND = null;

    /** ArrayList preserve insert order */
    private final ArrayList<File>              paths = new ArrayList<>();
    /** LinkedHashMap preserve insert order */
    private final LinkedHashMap<File,JarFile>  jars  = new LinkedHashMap<>();
    /** Loaded class, no order */
    private final Map<String,Class<?>>         cache = new HashMap<>();

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
    public void addClassPath( final String path ) throws IOException
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
            synchronized( this.jars ) {
                this.jars.put( pathFile, new JarFile( pathFile ) );
                }
            }
        else {
            synchronized( this.paths ) {
                this.paths.add( pathFile );
                }
            }
    }

    /**
     * Remove a class path
     * <P>
     * Remark: the previous loaded classes with this path are not unloaded.
     *
     * @param path
     *            the class path to remove
     * @throws IOException
     *             if any error occur while closing jar
     * @see #removeClassPath(File)
     */
    public void removeClassPath( final String path ) throws IOException
    {
        removeClassPath( new File( path ) );
    }

    /**
     * Remove a class path
     * <P>
     * Remark: the previous loaded class with this path are not unloaded.
     *
     * @param path
     *            the class path to remove
     * @throws IOException
     *             if any error occur while closing jar
     */
    public void removeClassPath( final File path ) throws IOException
    {
        boolean notRemoved = true;

        synchronized( this.jars ) {
            try (JarFile jar = this.jars.remove( path )) {
                // Just to close
                notRemoved = jar != null;
            }
        }

        if( notRemoved ) {
            synchronized( this.paths ) {
                this.paths.remove( path );
            }
        }
    }

    /**
     * Try to get bytes array of the class from extra class path.
     *
     * @param className
     *            The class name
     * @return a byte array with the stream content
     */
    private byte[] getClassFromAddedClassPaths( final String className )
    {
        final String fileName = className.replace( '.', '/' ) + ".class";

        for( final File path : this.paths ) {
            final File file = new File( path, fileName );

            if( file.exists() ) {
                final byte[] result = loadClassFromFile( file );

                if( result != null ) {
                    return result;
                }
            }
        }

        for( final Map.Entry<File, JarFile> entry : this.jars.entrySet() ) {
            try (JarFile jarFile = entry.getValue()) {
                final JarEntry jarEntry = jarFile.getJarEntry( fileName );

                final byte[] result = loadClassFromJar( jarFile, jarEntry );

                if( result != null ) {
                    return result;
                }
            }
            catch( final IOException closeIOException ) {
                LOGGER.warn( "Can close jar file : " + entry.getValue(), closeIOException );
            }
        }

        return NOT_FOUND;
    }

    @Nullable
    private static byte[] loadClassFromFile( final File file )
    {
        try {
            return toBytes( new FileInputStream( file ) );
            }
        catch( final IOException e ) {
            LOGGER.warn( "Can not read : " + file, e );

            //Try to continue anyway
            return NOT_FOUND;
            }
    }

    @Nullable
    private static byte[] loadClassFromJar(
        final JarFile  jarFile,
        final JarEntry jarEntry
        )
    {
        if( jarEntry != null ) {
            try {
                return toBytes( jarFile.getInputStream( jarEntry ) );
            }
            catch( final IOException e ) {
                LOGGER.warn( "Can not read : " + jarEntry.getName() + " from : " + jarFile.getName(), e );

                // Try to continue anyway
            }
        }
        return NOT_FOUND;
    }

    @Override
    public URL findResource( final String name )
    {
        for( final File path : this.paths ) {
            final File file = new File( path, name );

            if( file.exists() ) {
                final URL url = findResourceFromFile( file );

                if( url != null ) {
                    return url;
                }
            }
        }

        for( final Map.Entry<File, JarFile> entry : this.jars.entrySet() ) {
            try( final JarFile jarFile = entry.getValue() ) {
                final JarEntry jarEntry = jarFile.getJarEntry( name );

                findResourceFromJar( entry.getKey(), jarEntry );

            }
            catch( final IOException closeIOException ) {
                LOGGER.warn( "Can close jar file : " + entry.getValue(), closeIOException );
            }
        }

        return null;
    }

    @SuppressWarnings({
        "deprecation", // File.toURL()
        "squid:CallToDeprecatedMethod", // File.toURL()
        "squid:S00112" // new RuntimeException()
        })
    private URL findResourceFromFile( final File file )
    {
        try {
            return file.toURL();
        }
        catch( final MalformedURLException ignore ) {
            throw new RuntimeException( ignore ); // Should not occur
        }
    }

    @SuppressWarnings("squid:S00112") // new RuntimeException()
    private URL findResourceFromJar( final File jarFile, final JarEntry jarEntry )
    {
        if( jarEntry != null ) {
            try {
                @SuppressWarnings({"deprecation","squid:CallToDeprecatedMethod"})
                final URL jarURL = jarFile.toURL();

                return new URL(
                    "jar:" + jarURL.toString() + "!/" + jarEntry.getName()
                    );
            }
            catch( final MalformedURLException ignore ) {
                throw new RuntimeException( ignore ); // Should not occur
            }
        } else {
            return null;
        }
    }

    /**
     * Put inputStream content into a array of bytes, and close the stream.
     *
     * @param inputStream
     *            An {@link InputStream} where the class should loader
     * @return a byte array with the stream content
     * @throws IOException
     *             if any
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
    @SuppressWarnings("squid:S1166") // Ignore some exceptions
    public synchronized Class<?> loadClass(
            final String    className,
            final boolean   resolveIt
            )
        throws ClassNotFoundException
    {
        if( resolveIt ) {
            //
            // Find in current class loader
            //
            final ClassLoader parent = getParent();

            if( parent != null ) {
                try {
                    return parent.loadClass( className );
                    }
                catch( final ClassNotFoundException ignore ) {
                    // Try others solution above, so ignore previous exception
                    }
                }
            }

        //
        // Find from cache
        //
        final Class<?> classResult = this.cache.get( className );

        if( classResult != null ) {
            return classResult;
            }

        return deepLoadClass( className, resolveIt );
    }

    @SuppressWarnings("squid:S1166") // Ignore some exceptions
    private Class<?> deepLoadClass(
        final String className,
        final boolean resolveIt
        ) throws ClassNotFoundException, ClassFormatError
    {
        Class<?> classResult;
        byte[]   classData;

        try {
            classResult = super.findSystemClass( className );

            return classResult;
            }
        catch( final ClassNotFoundException ignore ) {
            // Try from added class path, so ignore previous exception
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

        this.cache.put( className, classResult );

        return classResult;
    }

    @Override
    protected Class<?> findClass( final String name )
        throws ClassNotFoundException
    {
        return loadClass( name );
    }
}
