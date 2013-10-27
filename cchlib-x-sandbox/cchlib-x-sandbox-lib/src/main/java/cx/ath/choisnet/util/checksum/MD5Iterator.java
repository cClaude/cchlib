/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/checksum/MD5Iterator.java
** Description   :
** Encodage      : ANSI
**
**  2.00.005 2005.09.29 Claude CHOISNET
**  2.01.004 2005.10.03 Claude CHOISNET - Version initiale
**                      Impl�mente java.io.Serializable
**  2.01.008 2005.10.05 Claude CHOISNET - Version initiale
**                      Impl�mente Iterable<MD5FileEntry>
**  2.01.036 2005.12.27 Claude CHOISNET
**                      La classe est DEPRECATED au profit de MD5Tree
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.checksum.MD5Iterator
**
*/
package cx.ath.choisnet.util.checksum;

import cx.ath.choisnet.io.FileIterator;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

/**
**
** @author Claude CHOISNET
** @version 2.00.006
** @since   2.00.006
**
** @deprecated utilisez MD5Tree
** @see MD5Tree
*/
@Deprecated
public class MD5Iterator
    implements
        java.util.Iterator<MD5FileEntry>,
        java.io.Serializable,
        Iterable<MD5FileEntry>
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** */
private FileIterator fileIterator;

/** */
private transient byte[] buffer;

/** */
private transient MD5 md5;

/**
**
public MD5Iterator( // ----------------------------------------------------
    File ... files
    )
{
 this( new FileIterator( files ) );
}
*/

/**
**
*/
public MD5Iterator( // ----------------------------------------------------
    File folderFile
    )
{
 this( new FileIterator( folderFile ) );
}

/**
**
*/
public MD5Iterator( // ----------------------------------------------------
    FileIterator fileIterator
    )
{
 this( fileIterator, 9192 ); // 8ko
}

/**
** Create a MD5Iterator based on a FileIterator, and using a buffer of
** bufferSize bytes.
**
** @param fileIterator
** @param bufferSize    size of the internal buffer use for compute MD5
*/
public MD5Iterator( // ----------------------------------------------------
    FileIterator    fileIterator,
    int             bufferSize
    )
{
 this.fileIterator  = fileIterator;
 this.buffer        = new byte[ bufferSize ];
 this.md5           = new MD5();
}

/**
**
*/
@Override
public Iterator<MD5FileEntry> iterator() // -------------------------------
{
 return this;
}

/**
**
*/
@Override
public boolean hasNext() // -----------------------------------------------
{
 return this.fileIterator.hasNext();
}

/**
**
*/
@Override
public MD5FileEntry next() // ---------------------------------------------
    throws java.util.NoSuchElementException
{
 final File file = this.fileIterator.next();

 if( file.isDirectory() ) {
    return new MD5FileEntry( file, null );
    }
  else {
    md5.reset();

    try {
        final BufferedInputStream bis
            = new BufferedInputStream(
                    new FileInputStream( file )
                    );
        int len;

        while( (len = bis.read( buffer, 0, buffer.length )) != -1 ) {
            md5.update( buffer, 0, len );
            }

        return new MD5FileEntry( file, md5.getValue() );
        }
    catch( java.io.IOException e ) {
        java.util.NoSuchElementException nsee
                = new java.util.NoSuchElementException( file.toString() );

        nsee.initCause( e );

        throw nsee;
        }
    }

}

/**
**
*/
@Override
public void remove() // ---------------------------------------------------
    throws UnsupportedOperationException,IllegalStateException
{
 throw new UnsupportedOperationException();
}

/**
** java.io.Serializable
*/
private void writeObject( java.io.ObjectOutputStream stream ) // ----------
    throws java.io.IOException
{
 stream.defaultWriteObject();

 //
 // On ne sauvegarde pas le contenu du buffer, juste sa taille.
 //
 stream.writeInt( this.buffer.length );
}

/**
** java.io.Serializable
*/
private void readObject( java.io.ObjectInputStream stream ) // ------------
    throws java.io.IOException, ClassNotFoundException
{
 stream.defaultReadObject();

 //
 // R�initialisation des champs non sauvegard�s
 //
 int len = stream.readInt();

 this.buffer    = new byte[ len ];
 this.md5       = new MD5();
}

/**
** java -cp build/classes cx.ath.choisnet.util.checksum.MD5Iterator C:\SYSTEM
public static void main( String[] args ) // -------------------------------
{
 long           begin   = System.currentTimeMillis();
 FileIterator   fi      = new FileIterator( new File( args[ 0 ] ) );
 MD5Iterator    md5i    = new MD5Iterator( fi );

 for( MD5FileEntry entry : md5i ) {
    File    file    = entry.getFile();
    String  md5     = entry.getHEXKey();
    }

 long       end = System.currentTimeMillis();

 System.out.println( "--------------" );
 System.out.println( "delais : " + (end -begin ) + " ms" );
 System.out.println( "--------------" );
}
*/

} // class
