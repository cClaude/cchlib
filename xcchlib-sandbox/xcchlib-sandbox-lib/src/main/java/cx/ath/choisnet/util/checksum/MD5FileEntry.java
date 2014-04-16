/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/checksum/MD5FileEntry.java
** Description   :
** Encodage      : ANSI
**
**  2.00.006 2005.09.30 Claude CHOISNET - Version initiale
**  2.01.002 2005.10.03 Claude CHOISNET
**                      Implementation de l'interface java.io.Serializable
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.checksum.MD5FileEntry
**
*/
package cx.ath.choisnet.util.checksum;

import java.io.File;

/**
**
** @author Claude CHOISNET
** @version 2.00.006
** @since   2.01.002
*/
public class MD5FileEntry
    implements java.io.Serializable
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** */
private File file;

/** */
private transient byte[] md5Bytes;

/**
** Create new entry without MD5 value, like a directory.
*/
public MD5FileEntry( File file ) // ---------------------------------------
{
 this( file, null );
}

/**
** Create new entry with MD5 value, like a file.
*/
public MD5FileEntry( File file, byte[] md5Bytes ) // ----------------------
{
 this.file      = file;
 this.md5Bytes  = md5Bytes;
}

/**
** @return the MD5 value as a byte array, or null if there is no MD5 for
**         this entry.
*/
public byte[] getKey() // -------------------------------------------------
{
 return this.md5Bytes;
}

/**
** @return the MD5 value as String, or null if there is no MD5 for
**         this entry.
**
** @see MD5#computeHEX( byte[] )
*/
public String getHEXKey() //-----------------------------------------------
{
 if( this.md5Bytes == null ) {
    return null;
    }
 else {
    return MD5.computeHEX( this.md5Bytes );
    }
}

/**
**
*/
public File getFile() //---------------------------------------------------
{
 return this.file;
}

/**
** java.io.Serializable
*/
private void writeObject( java.io.ObjectOutputStream stream ) // ----------
    throws java.io.IOException
{
 stream.defaultWriteObject();

 //
 // Sauvegarde du tableau
 //
 final int len = this.md5Bytes == null ? 0 : this.md5Bytes.length;

 stream.writeInt( len );

for( int i = 0; i<len; i++ ) {
    stream.writeByte( this.md5Bytes[ i ] );
    }
}

/**
** java.io.Serializable
*/
private void readObject( java.io.ObjectInputStream stream ) // ------------
    throws java.io.IOException, ClassNotFoundException
{
 stream.defaultReadObject();

 final int len = stream.readInt();

 if( len == 0 ) {
    this.md5Bytes = null;
    }
 else {
    this.md5Bytes = new byte[ len ];

    for( int i = 0; i<len; i++ ) {
        this.md5Bytes[ i ] = stream.readByte();
        }
    }
}

} // class
