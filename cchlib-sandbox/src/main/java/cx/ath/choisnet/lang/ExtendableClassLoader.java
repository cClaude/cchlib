/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/lang/ExtendableClassLoader.java
** Description   :
** Encodage      : ANSI
**
** -----------------------------------------------------------------------
**  DATE         AUTHOR        CHANGES
**  1996         C. McManis    Created
**  Jan 28 1998  O. Dedieu     Modify to support class paths adding
** -----------------------------------------------------------------------
**  2.00.004 2005.09.22 Claude CHOISNET - Initial version based on
**                      SimpleClassLoader.
**                      Append jarfile support, delegation support,
**                      findResource() support,
**                      misc optimisations and generics jdk 1.5
**  2.01.032 2005.09.22 Claude CHOISNET
**                      New method to download classes : toBytes()
**  3.02.042 2007.01.08 Claude CHOISNET
**                      Modification de findResource( final String name )
**                      afin d'être compatible avec les recommendations
**                      java 1.6
** ------------------------------------------------------------------------
**
** This class has been developped from the SimpleClassLoader class
** proposed by Chuck McManis in JavaWorld' article :
**
** http://www.javaworld.com/javaworld/jw-10-indepth.html
** http://www.javaworld.com/javaworld/jw-10-1996/jw-10-indepth.html
**
** cx.ath.choisnet.lang.ExtendableClassLoader
*/
package cx.ath.choisnet.lang;

import cx.ath.choisnet.util.ByteBuffer;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Map;

/**
** This class implements a {@link ClassLoader} that allow to extend the
** possible path to look up to load a class.<br/>
** <br/>
**
** @author  C. McManis
** @author  Olivier Dedieu
** @author  Claude CHOISNET
** @since   2.00.004
** @version 3.02.042
*/
public class ExtendableClassLoader
    extends ClassLoader
{

/** List of folder */
private LinkedList<File> paths;

/** List of jarfile */
private HashMap<File,JarFile> jars;

/** */
private HashMap<String,Class<?>> cache;

/**
** Constructs a new class loader and initializes it.
*/
public ExtendableClassLoader() // -----------------------------------------
{
 super();

 init();
}

/**
** Constructs a new class loader and initializes it.
*/
public ExtendableClassLoader( final ClassLoader parent ) // ---------------
{
 super( parent );

 init();
}

/**
** initializes object.
*/
private void init() // ----------------------------------------------------
{
 this.paths = new LinkedList<File>();
 this.jars  = new HashMap<File,JarFile>();
 this.cache = new HashMap<String,Class<?>>();
}

/**
** Add a path to the list of class paths
**
** @param path the class path
*/
public void addClassPath( final String path ) // --------------------------
    throws java.io.IOException
{
 addClassPath( new File( path ) );
}

/**
** Add a path to the list of class paths
**
** @param path the class path
*/
public void addClassPath( final File path ) // ----------------------------
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

/**
** Remove a class path
** <P>
** Remark: the previous loaded class with this path are not
** unloaded.
**
** @param path the class path to remove
*/
public void removeClassPath( final String path ) // -----------------------
{
 removeClassPath( new File( path ) );
}

/**
** Remove a class path
** <P>
** Remark: the previous loaded class with this path are not
** unloaded.
**
** @param path the class path to remove
*/
public void removeClassPath( final File path ) // -------------------------
{
 JarFile found;

 synchronized( jars ) {
    found = jars.remove( path );
    }

 if( found == null ) {
    synchronized( paths ) {
        paths.remove( path );
        }
    }
}

/**
** Looks up a class into all the class path and return its byte
**  code.
**
** @param className the class to load
**
** @return a byte array containing the byte code of the class
*/
private byte[] getClassFromAddedClassPaths( final String className ) // ---
{
 byte[] result = null;

 try {
    String fileName = className.replace( '.', '/' ) + ".class";

    //
    // Lookup the class into all the added class paths (folder)
    //
    for( File path : paths ) {
        File f = new File( path, fileName );

        if( f.exists() ) {
            result = toBytes( new FileInputStream( f ) );
            break;
            }
      }

    //
    // Lookup the class into all the added jarfiles
    //
    for( Map.Entry<File,JarFile> entry :jars.entrySet() ) {
        //
        //
        //
        JarFile     jarFile     = entry.getValue();
        JarEntry    jarEntry    = jarFile.getJarEntry( fileName );

        if( jarEntry != null ) {
            result = toBytes( jarFile.getInputStream( jarEntry ) );
            break;
            }
        }
    }
 catch( Exception ignore ) {
    // ignore
    }

 return result;
}

/**
**
*/
public URL findResource( final String name ) // ---------------------------
{
 //
 // Lookup the file into all the added class paths (folder)
 //
 for( File path : paths ) {
    File f = new File( path, name );

    if( f.exists() ) {
        try {
            // return f.toURL(); DEPRECATED in java 1.6
            return f.toURI().toURL();
            }
        catch( java.net.MalformedURLException ignore ) {
            // ignore
            }
        }
    }

 //
 // Lookup the file into all the added jarfiles
 //
 for( Map.Entry<File,JarFile> entry :jars.entrySet() ) {
    JarFile     jarFile     = entry.getValue();
    JarEntry    jarEntry    = jarFile.getJarEntry( name );

    if( jarEntry != null ) {
        try {
            // URL jarURL = entry.getKey().toURL(); DEPRECATED in java 1.6
            URL jarURL = entry.getKey().toURI().toURL();

            //
            // The syntax of a JAR URL is: jar:<url>!/{entry}
            //
            return new URL( "jar:" + jarURL.toString() + "!/" + jarEntry.getName() );
            }
        catch( java.net.MalformedURLException ignore ) {
            // ignore
            }
        }
    }

 return null;
}

/**
** Build a new byte array from the InputStream content.
**
** @return an byte array with all datas reading from InputStream.
*/
final static protected byte[] toBytes( final InputStream inputStream ) //--
    throws java.io.IOException
{
 final ByteBuffer   result  = new ByteBuffer( inputStream.available() );
 final byte[]       buffer  = new byte[ 4096 ];
 int                len;

 while( (len = inputStream.read( buffer )) != -1 ) {
    result.append( buffer, 0, len );
    }

 return result.array();
}

/**
** Requests the class loader to load a class with the specified
** name. The loadClass method is called by the Java Virtual Machine
** when a class loaded by a class loader first references another
** class.
**
** @param className the name of the desired <code>Class</code>.add
**
** @return the resulting <code>Class</code>, or <code>null</code> if
**          it was not found.
**
** @exception ClassNotFoundException
**            if the class loader cannot find a definition for the class.
**/
public Class loadClass( final String className ) // -----------------------
    throws ClassNotFoundException
{
 return loadClass( className, true );
}

/**
** Resolves the specified name to a Class. The method loadClass() is
** called by the virtual machine.
**
** @param className the name of the desired <code>Class</code>.
** @param resolveIt true if the <code>Class</code> needs to be resolved.
**
** @return  the resulting <code>Class</code>, or <code>null</code> if
**          it was not found.
**
** @exception ClassNotFoundException
**            if the class loader cannot find a definition for the class.
*/
public synchronized Class<?> loadClass( // --------------------------------
    final String  className,
    final boolean resolveIt
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
            // ignore, continue
            }
        }
    }

 Class classResult = (Class)cache.get( className );

//
// Check the cache of classes
//
 if( classResult != null ) {
    return classResult;
    }

//
// Check with the primordial class loader
//
try {
    classResult = super.findSystemClass( className );

    return classResult;
    }
catch( ClassNotFoundException ignore ) {
    // ignore, continue
    }

 //
 // Try to load it from the added class paths
 //
 byte[] classData = getClassFromAddedClassPaths( className );

 if( classData == null ) {
    throw new ClassNotFoundException( className );
    }

 //
 // Define it (parse the class file)
 //
 classResult = defineClass( className, classData, 0, classData.length );

 if( classResult == null ) {
    throw new ClassFormatError();
    }

 if( resolveIt ) {
    resolveClass( classResult );
    }

 //
 // Add the class to the cache
 //
 cache.put( className, classResult );

 return classResult;
}

/**
**
*/
protected Class<?> findClass( final String name ) // ----------------------
    throws ClassNotFoundException
{
 return loadClass( name );
}

} // class



/**
** toBytes
private byte[] private_getClass( InputStream inputStream ) // -------------
    throws java.io.IOException
{
 byte[] result = new byte[ inputStream.available() ];

 int len = inputStream.read( result );

 if( result.length != len ) {
    new java.io.IOException( "read error expected : " + result.length + " bytes, read : " + len );
    }

 return result;
}
*/
