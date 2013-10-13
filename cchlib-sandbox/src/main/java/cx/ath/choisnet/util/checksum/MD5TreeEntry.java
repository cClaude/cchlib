/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/checksum/MD5TreeEntry.java
** Description   :
** Encodage      : ANSI
**
**  2.01.021 2005.10.20 Claude CHOISNET - Version initiale
**  2.02.037 2005.12.28 Claude CHOISNET
**                      La classe en maintenant Cloneable
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.checksum.MD5TreeEntry
**
*/
package cx.ath.choisnet.util.checksum;

import cx.ath.choisnet.util.checksum.MD5;

/**
** Conteneur pour une empreinte : "Message Digest"
**
** @author Claude CHOISNET
** @since   2.01.087
** @version 2.02.037
*/
public class MD5TreeEntry
    implements
        Comparable<MD5TreeEntry>,
        Cloneable,
        java.io.Serializable
{
/** serialVersionUID */
private static final long serialVersionUID = 2L;

/** Message Digest */
private transient byte[] mdBytes;

/**
** Construction d'une entrée à partir de sa valeur brute.
*/
public MD5TreeEntry( final byte[] mdBytes ) // ----------------------------
{
 this.mdBytes = mdBytes;
}

/**
** @return the "Message Digest" value as a byte array, or null if there is
**         no "Message Digest" for this entry.
*/
public byte[] getKey() // -------------------------------------------------
{
 return this.mdBytes;
}

/**
** @return the "Message Digest" value as String, or null if there is
**         no "Message Digest" for this entry.
**
** @see MD5#computeHEX( byte[] )
*/
public String getHEXKey() //-----------------------------------------------
{
 if( this.mdBytes == null ) {
    return null;
    }
 else {
    return MD5.computeHEX( this.mdBytes );
    }
}

/**
**
*/
public int compareTo( MD5TreeEntry anOtherMD5TreeEntry ) // ---------------
{
 if( anOtherMD5TreeEntry.mdBytes.length != this.mdBytes.length ) {
    return anOtherMD5TreeEntry.mdBytes.length - this.mdBytes.length;
    }

 for( int i = 0; i <this.mdBytes.length; i++ ) {
    int cmp = anOtherMD5TreeEntry.mdBytes[ i ] - this.mdBytes[ i ];

    if( cmp != 0 ) {
        return cmp;
        }
    }

 return 0;
}

/**
**
*/
public boolean equals( Object object ) // ---------------------------------
{
 if( object != null ) {
    try {
        return compareTo( (MD5TreeEntry)object ) == 0;
        }
    catch( ClassCastException e ) {
        // return false;
        }
    }

 return false;
}

/**
**
*/
public String toString() //------------------------------------------------
{
 return getHEXKey();
}

/**
**
*/
public MD5TreeEntry clone() // --------------------------------------------
    throws CloneNotSupportedException
{
 MD5TreeEntry newMD5TreeEntry = MD5TreeEntry.class.cast( super.clone() );

 newMD5TreeEntry.mdBytes = new byte[ this.mdBytes.length ];

 System.arraycopy( this.mdBytes, 0, newMD5TreeEntry.mdBytes, 0, this.mdBytes.length );

 return newMD5TreeEntry;
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
 final int len = this.mdBytes == null ? 0 : this.mdBytes.length;

 stream.writeInt( len );

for( int i = 0; i<len; i++ ) {
    stream.writeByte( this.mdBytes[ i ] );
    }
}

/**
** java.io.Serializable
*/
private void readObject( java.io.ObjectInputStream stream ) // ------------
    throws java.io.IOException, ClassNotFoundException
{
 stream.defaultReadObject();

 int len = stream.readInt();

 if( len == 0 ) {
    this.mdBytes = null;
    }
 else {
    this.mdBytes = new byte[ len ];

    for( int i = 0; i<len; i++ ) {
        this.mdBytes[ i ] = stream.readByte();
        }
    }
}

/**
** Construction d'une entrée à partir de sa chaîne en HEXADECIMAL.
*/
public final static MD5TreeEntry newInstance( final String hexKey ) // ----
    throws NumberFormatException
{
 return new MD5TreeEntry( MD5.computeMessageDigest( hexKey ) );
}

} // class

