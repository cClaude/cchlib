/*
** ------------------------------------------------------------------------
** Nom           : cx/ath/choisnet/zip/SimpleUnZip.java
** Description   :
** Encodage      : ANSI
**
**  2.01.034 2005.10.18 Claude CHOISNET - Version initiale
4** ------------------------------------------------------------------------
**
** cx.ath.choisnet.zip.SimpleUnZip
**
*/
package cx.ath.choisnet.zip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
** <p>
** This class will allow you to perform basic pkzip compatible
** data decompression.
** </p>
**
** @author Claude CHOISNET
** @since   2.01.034
** @version 2.01.034
** @see SimpleZip
*/
public class SimpleUnZip
    implements
        java.io.Closeable
{

/**
**
*/
private final ZipInputStream zis;

/** */
private final byte[] buffer;

/**
** <p>
** Create a new SimpleUnZip object, based on an InputStream
** using default bufferSize = 4096 bytes.
** </p>
*/
public SimpleUnZip( final InputStream input ) // --------------------------------
    throws java.io.IOException
{
 this( input, 4096 );
}

/**
** <p>
** Create a new SimpleUnZip object, based on an InputStream
** </p>
*/
public SimpleUnZip( final InputStream input, final int bufferSize ) // ----------------
    throws java.io.IOException
{
 this.zis       = new ZipInputStream( input );
 this.buffer    = new byte[ bufferSize ];
}

/**
**
*/
@Override
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 this.zis.close();
}

/**
**
** @return the last File object saved, or null if there are no more entries.
*/
public File saveNextEntry( final File folderFile ) // ---------------------
    throws java.io.IOException
{
 final File         file;
 final OutputStream output;

    {
    final ZipEntry zipEntry = this.zis.getNextEntry();

    if( zipEntry == null ) {
        return null;
        }

    file = new File( folderFile, zipEntry.getName() );
    file.setLastModified( zipEntry.getTime() );

    final File parent = file.getParentFile();

    if( zipEntry.isDirectory() ) {
        output = null; // c'est un dossier !

        file.mkdirs();
        }
    else {
        if( ! parent.isDirectory() ) {
            parent.mkdirs();
            }

        output = new BufferedOutputStream( new FileOutputStream( file ) );
        }
    }

 int len;

 while( (len = this.zis.read( this.buffer, 0, this.buffer.length )) != -1 ) {
    output.write( buffer, 0, len );
    }

 if( output != null ) {
    output.close();
    }

 return file;
}

/**
**
*/
public void saveAll( final File folderFile ) // ---------------------------
    throws java.io.IOException
{
 File f;

// if( ! folderFile.isDirectory() ) {
//    folderFile.mkdirs();
//    }
 while( (f = saveNextEntry( folderFile )) != null ) {
    // System.out.println( "f " + f );
    }
}

/**
** java -cp build/classes cx.ath.choisnet.zip.SimpleUnZip <zipfile> <destinationfolder>
**
*/
@SuppressWarnings("resource")
public static void main( final String[] arg ) // --------------------------------
    throws java.io.IOException
// %JAVA_1_5_HOME%\bin\java -cp build/classes cx.ath.choisnet.zip.SimpleUnZip c:\testzip.zip D:/test1/test2
{
 final SimpleUnZip instance = new SimpleUnZip( new java.io.FileInputStream( arg[ 0 ] ) );

 instance.saveAll( new File( arg[ 1 ] ) );
 instance.close();
}

} // class
