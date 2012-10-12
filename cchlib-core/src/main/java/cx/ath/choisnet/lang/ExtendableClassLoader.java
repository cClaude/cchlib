package cx.ath.choisnet.lang;

import cx.ath.choisnet.ToDo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * TODOC
 *
 * <p style="border:groove;">
 * <b>Warning:</b>
 * Insofar the code of this class comes from decompiling
 * my own code following the loss of source code, the use
 * of this class must do so under protest until I have
 * check its stability, it could be subject to significant
 * change.
 * </p>
 *
 *
 */
@ToDo
public class ExtendableClassLoader extends ClassLoader
{//TODO: TestCase
    final private List<File>              paths = new ArrayList<File>();
    final private Map<File,JarFile>       jars  = new HashMap<File,JarFile>();
    final private Map<String,Class<?>>    cache = new HashMap<String,Class<?>>();

    /**
     * TODOC
     */
    public ExtendableClassLoader()
    {
        super();
    }

    /**
     * TODOC
     *
     * @param parent
     */
    public ExtendableClassLoader(
        final ClassLoader parent
        )
    {
        super( parent );
    }

    /**
     * TODOC
     *
     * @param path
     * @throws IOException
     */
    public void addClassPath( final String path )
        throws IOException
    {
        addClassPath( new File( path ) );
    }

    /**
     * TODOC
     *
     * @param path
     * @throws IOException
     */
    public void addClassPath( final File path )
        throws IOException
    {
        if( path.isFile() ) {
            synchronized( jars ) {
                jars.put( path, new JarFile( path ) );
                }
            }
        else {
            synchronized( paths ) {
                paths.add( path );
                }
            }
    }

    /**
     * TODOC
     * @param path
     */
    public void removeClassPath( final String path )
    {
        removeClassPath( new File( path ) );
    }

    /**
     * TODOC
     * @param path
     */
    public void removeClassPath( final File path )
    {
        JarFile found;

        synchronized( jars ) {
            found = jars.remove(path);
            }

        if( found == null ) {
            synchronized( paths ) {
                paths.remove( path );
                }
            }
    }

    /**
     * TODOC
     * @param className
     * @return TODOC
     */
    private byte[] getClassFromAddedClassPaths(
        final String className
        )
    {
        byte[] result = null;
        String fileName;

        try {
            fileName = className.replace('.', '/') + ".class";

            for( File path : this.paths ) {
                File f = new File(path, fileName);

                if( f.exists() ) {
                    result = toBytes( new FileInputStream( f ) );
                    break;
                    }
                }

            for( Map.Entry<File,JarFile> entry : this.jars.entrySet() ) {
                JarFile  jarFile  = entry.getValue();
                JarEntry jarEntry = jarFile.getJarEntry( fileName );

                if( jarEntry != null ) {
                    result = toBytes(jarFile.getInputStream(jarEntry));
                    break;
                    }
                }
            }
        catch( Exception ignore ) {
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
                    }
                }
            }

        return null;
    }

    /**
     * TOODC
     * @param inputStream
     * @return TODOC
     * @throws IOException
     */
    protected static final byte[] toBytes(
        final InputStream inputStream
        ) throws IOException
    {
        final ByteArrayBuilder  result = new ByteArrayBuilder( inputStream.available() );
        final byte[]            buffer = new byte[ 4096 ];
        int                     len;

        while( (len = inputStream.read(buffer)) != -1 ) {
            result.append( buffer, 0, len );
            }

        return result.array();
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
                catch( ClassNotFoundException ignore ) {
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
        catch( ClassNotFoundException ignore ) {
            classData = getClassFromAddedClassPaths( className );
            }

        if( classData == null ) {
            throw new ClassNotFoundException( className );
            }

        classResult = defineClass( className, classData, 0, classData.length );

        if( classResult == null ) {
            throw new ClassFormatError();
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
