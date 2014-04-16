/*
** ------------------------------------------------------------------------
** Nom           : cx/ath/choisnet/zip/SimpleZip.java
** Description   :
** Encodage      : ANSI
**
**  2.01.019 2005.10.18 Claude CHOISNET - Version initiale
**  2.01.020 2005.10.20 Claude CHOISNET
**                      Refonte de la classe.
** ------------------------------------------------------------------------
**
** cx.ath.choisnet.zip.SimpleZip
**
*/
package cx.ath.choisnet.zip;

import cx.ath.choisnet.io.FileIterator;
import cx.ath.choisnet.util.Wrappable;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
** <p>
** This class will allow you to perform basic pkzip compatible
** data compression.
** </p>
**
** @author Claude CHOISNET
** @since   2.01.019
** @version 2.01.020
** @see SimpleUnZip
*/
public class SimpleZip
    implements java.io.Closeable
{
    /**
    **
    */
    interface AppenerListener extends java.util.EventListener
    {
        public void fileAppend( ZipEntry entry );
    }

/**
**
*/
private ZipOutputStream zos;

/** */
private byte[] buffer;

/**
** <p>
** Create a new SimpleZip object, based on an OutputStream
** using default bufferSize = 4096 bytes.
** </p>
*/
public SimpleZip( OutputStream output ) // --------------------------------
    throws java.io.IOException
{
 this( output, 4096 );
}

/**
** <p>
** Create a new SimpleZip object, based on an OutputStream
** </p>
*/
public SimpleZip( OutputStream output, int bufferSize ) // ----------------
    throws java.io.IOException
{
 this.zos       = new ZipOutputStream( output );
 this.buffer    = new byte[ bufferSize ];

 setMethod( ZipOutputStream.DEFLATED );
}

/**
**
*/
@Override
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 try {
    this.zos.flush();
    this.zos.finish();
    }
 finally {
    this.zos.close();
    }
}

/**
** Sets the ZIP file comment.
*/
public void setComment( String comment ) // -------------------------------
{
 this.zos.setComment( comment );
}

/**
** Sets the compression level for subsequent entries which are DEFLATED.
*/
public void setLevel( int level ) // --------------------------------------
{
 this.zos.setLevel( level );
}

/**
** <p>
** Sets the default compression method for subsequent entries.This default
** will be used whenever the compression method is not specified for an
** individual ZIP file entry, and is initially set to DEFLATED.
** </p>
**
** @see ZipOutputStream#DEFLATED
** @see ZipOutputStream#STORED
*/
public void setMethod( int method ) // ------------------------------------
{
 this.zos.setMethod( method );
}

/**
** <p>
** Append a SimpleZipEntry
** </p>
** @param simpleZipEntry a valid SimpleZipEntry to append in the archive.
**
*/
public void add( SimpleZipEntry simpleZipEntry ) // -----------------------
    throws java.io.IOException
{
 this.zos.putNextEntry( simpleZipEntry.getZipEntry() );

 if( ! simpleZipEntry.getZipEntry().isDirectory() ) {
    BufferedInputStream    bis = new BufferedInputStream( simpleZipEntry.getInputStream() );
    int                    len;

    while( (len = bis.read( buffer, 0, buffer.length ) ) != -1) {
        this.zos.write( buffer, 0, len );
        }

    bis.close();
    }

 this.zos.flush();
 this.zos.closeEntry();
}

/**
** <p>
** Append a Collection of SimpleZipEntry
** </p>
** @param c a valid Collection of SimpleZipEntry to append in the archive.
**
** @see cx.ath.choisnet.util.IteratorWrapper
*/
public void addAll( Iterable<SimpleZipEntry> c ) // -----------------------
    throws java.io.IOException
{
 for( SimpleZipEntry entry : c ) {
    this.add( entry );
    }
}

/**
** <p>
** Get entry from folder and sub-folders
** </p>
** <br/>
** <p>
** <b>Exemple:</b><br/>
**  <pre>
**  File        source      = new File( arg[ 0 ] );
**  SimpleZip   instance    = new SimpleZip( new java.io.FileOutputStream( arg[ 1 ] ) );
**
**  instance.addFolder(
**          source,
**          new cx.ath.choisnet.zip.impl.SimpleZipEntryFactoryImpl( source )
**              {
**                  public SimpleZipEntry wrappe( File file )
**                  {
**                      System.out.println( "add: " + file );
**
**                      return super.wrappe( file );
**                  }
**              }
**          );
**  instance.close();
**  </pre>
** </p>
**
** @param folderFile    folder to read.
** @param wrapper       an object able to wrapped File to a SimpleZipEntry
**
** @see cx.ath.choisnet.zip.impl.SimpleZipEntryFactoryImpl
*/
public void addFolder( // -------------------------------------------------
    final File                              folderFile,
    final Wrappable<File,SimpleZipEntry>    wrapper
    )
    throws java.io.IOException
{
 this.addAll(
    new cx.ath.choisnet.util.IteratorWrapper<File,SimpleZipEntry>(
        new FileIterator( folderFile ),
        wrapper
        )
    );
}

/**
** <p>
** Get entry from folder and sub-folders
** </p>
** @param folderFile a valid folder
public void addFolder( final File folderFile ) // -------------------------
    throws java.io.IOException
{
 this.addFolder( folderFile, new SimpleZipEntryFactoryImpl( folderFile ) );
}
*/

    /**
    ** java -cp build/classes cx.ath.choisnet.zip.SimpleZip <sourcefolder> <zipfile>
    **
    */
    public static void main( String[] arg ) // --------------------------------
        throws java.io.IOException
    // %JAVA_1_5_HOME%\bin\java -cp build/classes cx.ath.choisnet.zip.SimpleZip D:/ProcParm c:\testzip.zip
    {
     final File source = new File( arg[ 0 ] );

     try( final SimpleZip instance = new SimpleZip( new FileOutputStream( arg[ 1 ] ) ) ) {
         instance.addFolder(
                 source,
                 new cx.ath.choisnet.zip.impl.SimpleZipEntryFactoryImpl( source )
                     {
                         @Override
                         public SimpleZipEntry wrappe( File file )
                         {
                             System.out.println( "add: " + file );

                             return super.wrappe( file );
                         }
                     }
                 );
         }
    }

} // class
