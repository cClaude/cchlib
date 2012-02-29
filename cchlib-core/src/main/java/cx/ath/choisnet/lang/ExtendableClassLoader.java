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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * TODOC
 *
 * <p><b>This class is suspect, no time for now
 * to check it, but decompile process give
 * a strange result</b></p>
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
 * @author Claude CHOISNET
 *
 */
@ToDo
public class ExtendableClassLoader extends ClassLoader
{//TODO: TestCase
    final private List<File>              paths = new ArrayList<File>();
    final private Map<File,JarFile>       jars  = new HashMap<File,JarFile>();
    final private Map<String,Class<?>>    cache = new HashMap<String,Class<?>>();

    public ExtendableClassLoader()
    {
        init();
    }

    public ExtendableClassLoader( final ClassLoader parent )
    {
        super(parent);

        init();
    }

    private void init()
    {
//        paths = new LinkedList();
//        jars  = new HashMap<File,JarFile>();
//        cache = new HashMap<String,Class<?>>();
    }

    public void addClassPath( final String path )
        throws java.io.IOException
    {
        addClassPath(new File(path));
    }

    public void addClassPath( final File path )
        throws java.io.IOException
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

    public void removeClassPath( final String path )
    {
        removeClassPath( new File( path ) );
    }

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

    private byte[] getClassFromAddedClassPaths( final String className )
    {
        byte result[];
label0:
        {
            result = null;
    //    0    0:aconst_null
    //    1    1:astore_2
            try {
                String fileName = className.replace( '.', '/' ) + ".class";
    //    2    2:new             #22  <Class StringBuilder>
    //    3    5:dup
    //    4    6:invokespecial   #23  <Method void StringBuilder()>
    //    5    9:aload_1
    //    6   10:bipush          46
    //    7   12:bipush          47
    //    8   14:invokevirtual   #24  <Method String String.replace(char, char)>
    //    9   17:invokevirtual   #25  <Method StringBuilder StringBuilder.append(String)>
    //   10   20:ldc1            #26  <String ".class">
    //   11   22:invokevirtual   #25  <Method StringBuilder StringBuilder.append(String)>
    //   12   25:invokevirtual   #27  <Method String StringBuilder.toString()>
    //   13   28:astore_3
                Iterator<File> i$ = paths.iterator();
    //   14   29:aload_0
    //   15   30:getfield        #6   <Field java.util.LinkedList cx.ath.choisnet.lang.ExtendableClassLoader.paths>
    //   16   33:invokevirtual   #28  <Method java.util.Iterator java.util.LinkedList.iterator()>
    //   17   36:astore          4
                do
                {
                    if( !i$.hasNext() ) {
                        break;
                    }
    //   18   38:aload           4
    //   19   40:invokeinterface #29  <Method boolean java.util.Iterator.hasNext()>
    //   20   45:ifeq            99
                    File path = i$.next();
    //   21   48:aload           4
    //   22   50:invokeinterface #30  <Method Object java.util.Iterator.next()>
    //   23   55:checkcast       #11  <Class java.io.File>
    //   24   58:astore          5
                    File f = new File(path, fileName);
    //   25   60:new             #11  <Class java.io.File>
    //   26   63:dup
    //   27   64:aload           5
    //   28   66:aload_3
    //   29   67:invokespecial   #31  <Method void File(File, String)>
    //   30   70:astore          6
                    if( !f.exists() ) {
                        continue;
                    }
    //   31   72:aload           6
    //   32   74:invokevirtual   #32  <Method boolean java.io.File.exists()>
    //   33   77:ifeq            96
                    result = ExtendableClassLoader.toBytes(new FileInputStream(f));
    //   34   80:new             #33  <Class java.io.FileInputStream>
    //   35   83:dup
    //   36   84:aload           6
    //   37   86:invokespecial   #34  <Method void FileInputStream(File)>
    //   38   89:invokestatic    #35  <Method byte[] cx.ath.choisnet.lang.ExtendableClassLoader.toBytes(java.io.InputStream)>
    //   39   92:astore_2
                    break;
    //   40   93:goto            99
                } while(true);
    //*  41   96:goto            38
                Iterator<Map.Entry<File,JarFile>> i2$ = jars.entrySet().iterator();
    //   42   99:aload_0
    //   43  100:getfield        #9   <Field java.util.HashMap cx.ath.choisnet.lang.ExtendableClassLoader.jars>
    //   44  103:invokevirtual   #36  <Method java.util.Set java.util.HashMap.entrySet()>
    //   45  106:invokeinterface #37  <Method java.util.Iterator java.util.Set.iterator()>
    //   46  111:astore          4
                JarFile  jarFile;
                JarEntry jarEntry;
                do {
                    if(!i2$.hasNext()) {
                        break label0;
                    }
    //   47  113:aload           4
    //   48  115:invokeinterface #29  <Method boolean java.util.Iterator.hasNext()>
    //   49  120:ifeq            177
                    Map.Entry<File,JarFile> entry = i2$.next();
    //   50  123:aload           4
    //   51  125:invokeinterface #30  <Method Object java.util.Iterator.next()>
    //   52  130:checkcast       #38  <Class java.util.Map$Entry>
    //   53  133:astore          5
                    jarFile = entry.getValue();
    //   54  135:aload           5
    //   55  137:invokeinterface #39  <Method Object java.util.Map$Entry.getValue()>
    //   56  142:checkcast       #15  <Class java.util.jar.JarFile>
    //   57  145:astore          6
                    jarEntry = jarFile.getJarEntry(fileName);
    //   58  147:aload           6
    //   59  149:aload_3
    //   60  150:invokevirtual   #40  <Method java.util.jar.JarEntry java.util.jar.JarFile.getJarEntry(String)>
    //   61  153:astore          7
                } while(jarEntry == null);

                result = ExtendableClassLoader.toBytes(jarFile.getInputStream(jarEntry));
            }
            catch(Exception ignore) {
            }
        }
        return result;
    }

    @Override
    public URL findResource( final String name )
    {
        Iterator<File> i1$ = paths.iterator();

        do {
            if( !i1$.hasNext() ) {
                break;
                }

            File path = i1$.next();
            File f = new File(path, name);

            if( f.exists() ) {
                try {
                    @SuppressWarnings("deprecation")
                    URL u = f.toURL();
                    return u;
                    }
                catch(MalformedURLException ignore) {
                    }
                }
            } while(true);

        Iterator<Map.Entry<File,JarFile>> i2$ = jars.entrySet().iterator();

        do {
            if( !i2$.hasNext() ) {
                break;
                }

            Map.Entry<File,JarFile> entry       = i2$.next();
            JarFile                 jarFile     = entry.getValue();
            JarEntry                jarEntry    = jarFile.getJarEntry(name);

            if( jarEntry != null ) {
                try {
                    @SuppressWarnings("deprecation")
                    URL jarURL = entry.getKey().toURL();

                    return new URL( "jar:" + jarURL.toString() + "!/" + jarEntry.getName() );
                    }
                catch(MalformedURLException ignore) {
                    }
                }
            } while(true);

        return null;
    }

    /**
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    protected static final byte[] toBytes( final InputStream inputStream )
        throws IOException
    {
        final ByteArrayBuilder  result = new ByteArrayBuilder( inputStream.available() );
        final byte[]            buffer = new byte[ 4096 ];
        int                     len;

        while( (len = inputStream.read(buffer)) != -1 ) {
            result.append(buffer, 0, len);
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
