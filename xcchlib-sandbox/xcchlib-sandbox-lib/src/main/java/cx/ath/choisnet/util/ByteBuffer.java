/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/ByteBuffer.java
** Description   :
** Encodage      : ANSI
**
**  1.53.011 2005.08.18 Claude CHOISNET - Version initiale
**  1.53.012 2005.08.18 Claude CHOISNET
**                      Nombreuses optimisations
**  2.01.013 2005.10.09 Claude CHOISNET
**                      Implements java.io.Serializable et
**                      java.lang.Cloneable
**  2.02.035 2005.12.27 Claude CHOISNET
**                      Ajout de append( java.nio.ByteBuffer )
**  2.02.037 2005.12.28 Claude CHOISNET
**                      Modification de clone() utilise maintenant la
**                      methode System.arraycopy()
**  2.02.042 2006.01.09 Claude CHOISNET
**                      Ajout du constructeur:
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.ByteBuffer
**
*/
package cx.ath.choisnet.util;

/**
** Buffer d'octects e taille variable, e ne pas confondre avec la classe
** {@link java.nio.ByteBuffer} (java.nio.ByteBuffer) apparue dans le
** JDK 1.4. Cette classe est comparable e la classe {@link StringBuffer}.
**
** @author Claude CHOISNET
** @since   1.53.011
** @version 2.02.042
**
** @see StringBuilder
*/
public class ByteBuffer
    implements
        Comparable<ByteBuffer>,
        Cloneable,
        java.io.Serializable
{
/** serialVersionUID */
private static final long serialVersionUID = 2L;

/** Internal buffer */
private transient byte[] buffer;

/** Last valid position in the internal buffer */
private transient int lastPos;

/** Default size */
private final static int DEFAULT_SIZE = 2048;

/**
** Constructs a ByteBuffer with no byte in it and an initial
** capacity of 2048 bytes.
*/
public ByteBuffer() // ----------------------------------------------------
{
 this( DEFAULT_SIZE );
}

/**
** Constructs a ByteBuffer with no byte in it and the specified initial capacity
**
** @param capacity the initial capacity.
*/
public ByteBuffer( int capacity ) // --------------------------------------
{
 this.buffer    = new byte[ capacity ];
 this.lastPos   = 0;
}

/**
** Constructs a ByteBuffer initialized to the contents of the specified array.
** The initial capacity of the ByteBuffer is 2048 plus the length of the array
** argument.
**
** @param bytes the array to copy
**
** If bytes is null, an empty ByteBuffer is created with no byte in it and
** an initial capacity of 2048 bytes.
**
** @since 2.02.042
*/
public ByteBuffer( final byte[] bytes ) // --------------------------------
{
 this.lastPos = 0;

 if( bytes == null ) {
    this.buffer = new byte[ DEFAULT_SIZE ];
    }
 else {
    this.buffer = new byte[ DEFAULT_SIZE + bytes.length ];

    this.append( bytes, 0, bytes.length );
    }
}

/**
** Act as setLength( 0 );
*/
public void reset() // ----------------------------------------------------
{
 this.lastPos = 0;
}

/**
** Sets the length of the byte sequence. The ByteBuffer is changed to a
** new ByteBuffer whose length is specified by the argument.
**
** If the newLength argument is less than the current
** length, the length is changed to the specified length.
**
** If the newLength argument is greater than or equal to the current
** length, sufficient null byte are appended so that
** length becomes the newLength argument.
**
** The newLength argument must be greater than or equal to 0.
**
** @param newLength the new length
**
** @throws IndexOutOfBoundsException - if the newLength argument is negative.
**
** @see #length()
*/
public void setLength( final int newLength ) // ---------------------------
    throws IndexOutOfBoundsException
{
 if( newLength < 0 ) {
    throw new IndexOutOfBoundsException();
    }

 if( newLength < this.lastPos ) {
    this.lastPos = newLength;
    }
 else {
    ensureCapacity( newLength );

    for( int i = this.lastPos; i<newLength; i++ ) {
        this.buffer[ i ] = 0;
        }

    this.lastPos = newLength;
    }
}

/**
** Returns the length (byte count).
**
** @return the length of the sequence of byte currently
**         represented by this object
**
*/
public int length() // ----------------------------------------------------
{
 return this.lastPos;
}

/**
** <P>
** Returns the current capacity. The capacity is the amount of storage
** available for newly inserted characters, beyond which an allocation
** will occur.
** </P>
**
** @return the current capacity
*/
public int capacity() // --------------------------------------------------
{
 return this.buffer.length;
}

/**
** @param minimumCapacity   the minimum desired capacity.
**
** Ensures that the capacity is at least equal to the specified minimum. If
** the current capacity is less than the argument, then a new internal array
** is allocated with greater capacity. The new capacity is the larger of:
** <ul>
**  <li>The minimumCapacity argument.</li>
**  <li>Twice the old capacity, plus 2.</li>
** </ul>
** If the minimumCapacity argument is nonpositive, this method takes no
** action and simply returns.
**
*/
public void ensureCapacity( final int minimumCapacity ) // ----------------
{
 //System.out.println( "  minimumCapacity/capacity() = " + minimumCapacity + "/" + capacity() );

 if( minimumCapacity > capacity() ) {
    //
    // Il faut agrandir le buffer
    //
    int newLength = this.buffer.length;

    while( newLength < minimumCapacity ) {
        newLength = (newLength + 1 )<< 1;
        }

    //System.out.println( "  => newLength = " + newLength );

    //
    // Allocation du nouveau buffer
    //
    final byte[] newBuffer = new byte[ newLength ];

    //
    // Recopie des donnees
    //
    System.arraycopy( this.buffer, 0, newBuffer, 0, this.buffer.length );

    //
    // Changement de reference.
    //
    this.buffer = newBuffer;
    }
}

/**
** Ajoute le tableau de bytes donne.
**
** @param bytes tableau de bytes e ajouter dans le buffer.
**
** @return l'objet ByteBuffer lui-meme
*/
public ByteBuffer append( byte[] bytes ) // -------------------------------
{
 return append( bytes, 0, bytes.length );
}

/**
** Ajoute une partie du tableau de bytes donne.
**
** @param bytes     tableau de bytes e ajouter dans le buffer.
** @param offset    position de depart
** @param len       longueur.
**
**
** @return l'objet ByteBuffer lui-meme
*/
public ByteBuffer append( // ----------------------------------------------
    final byte[]    bytes,
    final int       offset,
    final int       len
    )
{
 final int lenOfCopy = len - offset;

 ensureCapacity( length() + lenOfCopy );

 System.arraycopy( bytes, offset, this.buffer, this.lastPos, lenOfCopy );

 this.lastPos += lenOfCopy;

 return this;
}

/**
** Ajout le byte donne.
**
** @param b byte a ajouter dans le buffer.
**
** @return l'objet ByteBuffer lui-meme
*/
public ByteBuffer append( final byte b ) // -------------------------------
{
 try {
    this.buffer[ this.lastPos ] = b;
    }
 catch( ArrayIndexOutOfBoundsException e ) {
    //
    // Agrandis la taille du buffer
    //
    ensureCapacity( capacity() + 1 );

    //
    // Essaye encore !
    //
    this.buffer[ this.lastPos ] = b;
    }

 this.lastPos++; // doit imperativement se trouver apres

 return this;
}

/**
** Ajoute le contenu courant du buffer nioByteBuffer
**
** @return l'objet ByteBuffer lui-meme
**
** @since 2.02.035
public ByteBuffer _append( final java.nio.ByteBuffer nioByteBuffer ) // ----
{
 // $$$$$$$$$$$ BOF !!!! A REVOIR !!!
 // $$$$$$$$$$$ BOF !!!! A REVOIR !!!
 // $$$$$$$$$$$ BOF !!!! A REVOIR !!!

 while( nioByteBuffer.hasRemaining() ) {
    append( nioByteBuffer.get() );
    }
 // $$$$$$$$$$$ BOF !!!! A REVOIR !!!
 // $$$$$$$$$$$ BOF !!!! A REVOIR !!!
 // $$$$$$$$$$$ BOF !!!! A REVOIR !!!

 return this;
}
*/

/**
** Ajoute le contenu courant du flux correspondant au channel donne.
**
** @param channel byte a ajouter dans le buffer.
**
** @return l'objet ByteBuffer lui-meme
**
** @since 2.02.035
*/
public ByteBuffer append( // ----------------------------------------------
    final java.nio.channels.ReadableByteChannel channel
    )
    throws java.io.IOException
{
 return append( channel, DEFAULT_SIZE );
}

/**
** Ajoute le contenu courant du flux correspondant au channel donne.
**
** @param channel       byte a ajouter dans le buffer.
** @param bufferSize    taille du buffer utiliser pour le transfert des
**                      donnees.
**
** @return l'objet ByteBuffer lui-meme
**
** @since 2.02.035
*/
public ByteBuffer append( // ----------------------------------------------
    final java.nio.channels.ReadableByteChannel channel,
    final int                                   bufferSize
    )
    throws java.io.IOException
{
 final byte[]               byteBuffer  = new byte[ bufferSize ];
 final java.nio.ByteBuffer  buffer      = java.nio.ByteBuffer.wrap( byteBuffer );
 int                        len;

 while( (len = channel.read( buffer )) != -1 ) {
    buffer.flip();

    this.append( byteBuffer, 0, len );

    buffer.clear();
    }

 return this;
}

/**
** @return a copy of the internal buffer
*/
public byte[] array() // --------------------------------------------------
{
 final byte[] bufferCopy = new byte[ this.lastPos ];

 //
 // Recopie des donnees
 //
 System.arraycopy( this.buffer, 0, bufferCopy, 0, bufferCopy.length );

 return bufferCopy;
}

/**
**
*/
public boolean startsWith( final ByteBuffer aByteBuffer ) // --------------
{
 return startsWith( aByteBuffer.array() );
}

/**
**
*/
public boolean startsWith( final byte[] pattern ) // ----------------------
{
 if( pattern.length > this.lastPos ) {
    return false;
    }

 for( int i = 0; i<pattern.length; i++ ) {

    if( this.buffer[ i ] != pattern[ i ] ) {
        return false;
        }
    }

 return true;
}

/**
**
*/
public boolean endsWith( final ByteBuffer aByteBuffer ) // ----------------
{
 return endsWith( aByteBuffer.array() );
}

/**
**
*/
public boolean endsWith( final byte[] pattern ) // ------------------------
{
 if( pattern.length > this.lastPos ) {
    return false;
    }

 int j = this.lastPos - pattern.length;

 for( int i = 0; i<pattern.length; i++ ) {

    if( buffer[ j++ ] != pattern[ i ] ) {
        return false;
        }
    }

 return true;
}

/**
**
public int compareTo( Object o ) // ---------------------------------------
    throws ClassCastException
{
 return compareTo( (ByteBuffer)o );
}
*/

/**
**
*/
@Override
public int compareTo( ByteBuffer aByteBuffer ) // -------------------------
{
 //
 // Recupere la longueur la plus courte
 //
 final int length = this.lastPos < aByteBuffer.lastPos ?  this.lastPos : aByteBuffer.lastPos;

 for( int i = 0; i < length; i ++ ) {
    int cmp = this.buffer[ i ] - aByteBuffer.buffer[ i ];

    if( cmp != 0 ) {
        return cmp;
        }
    }

 //
 // Ils sont egaux jusqu'e la position du plus court
 //
 return this.lastPos - aByteBuffer.lastPos;
}


/**
**
*/
@Override
public boolean equals( Object o ) // --------------------------------------
{
 try {
    return compareTo( (ByteBuffer)o ) == 0;
    }
 catch( ClassCastException e ) {
    return false;
    }
}

/**
**
*/
@Override
public String toString() // -----------------------------------------------
{
 return new String( this.buffer, 0, this.lastPos );
}

/**
**
*/
@Override
public ByteBuffer clone() // ----------------------------------------------
    throws CloneNotSupportedException
{
 ByteBuffer newByteBuffer = (ByteBuffer)super.clone();

 newByteBuffer.buffer = new byte[ this.buffer.length ];

 final int max = this.lastPos;
//
// for( int i=0; i<max; i++ ) {
//    newByteBuffer.buffer[ i ] = this.buffer[ i ];
//    }

 newByteBuffer.lastPos = max;

 System.arraycopy( this.buffer, 0, newByteBuffer.buffer, 0, max );

 return newByteBuffer;
}


/**
** java.io.Serializable
**
** @since 2.01.013
*/
private void writeObject( java.io.ObjectOutputStream stream ) // ----------
    throws java.io.IOException
{
 stream.defaultWriteObject();

 //
 // On sauvegarde le contenu des champs transient
 //
 stream.writeInt( this.buffer.length );

 final int max = this.lastPos;
 stream.writeInt( max );

 for( int i=0; i<max; i++ ) {
    stream.writeByte( this.buffer[ i ] );
    }
}

/**
** java.io.Serializable
**
** @since 2.01.013
*/
private void readObject( java.io.ObjectInputStream stream ) // ------------
    throws java.io.IOException, ClassNotFoundException
{
 stream.defaultReadObject();

 //
 // Reinitialisation des champs non sauvegardes automatiquements
 //
 this.buffer = new byte[ stream.readInt() ];

 final int max = stream.readInt();

 for( int i=0; i<max; i++ ) {
    this.buffer[ i ] = stream.readByte();
    }

 this.lastPos = max;
}

/**
** java -cp build\classes cx.ath.choisnet.util.ByteBuffer
**
public final static void main( String[] args ) // -------------------------
    throws Exception
{
 final byte[]   bytes   = { '1','2','3','4','5','6','7' };
 ByteBuffer     buf1    = new ByteBuffer( 1 );
 ByteBuffer     buf2    = new ByteBuffer();

 System.out.println( "1:length/capacity = [" + buf1.length() + "/" + buf1.capacity() + "]" );

 for( int i = 0; i<bytes.length; i++ ) {
    buf1.append( bytes[ i ] );
    System.out.println( "1:length/capacity = [" + buf1.length() + "/" + buf1.capacity() + "]" );
    }

 buf2.append( buf1.array() );

 System.out.println( "1:instance = [" + buf1 + "]" );
 System.out.println( "1:length/capacity = [" + buf1.length() + "/" + buf1.capacity() + "]" );
 System.out.println( "2:instance = [" + buf2 + "]" );
 System.out.println( "2:length/capacity = [" + buf2.length() + "/" + buf2.capacity() + "]" );
 System.out.println( "1.equals(2)     ? = [" + buf1.equals( buf2 ) + "]" );
 System.out.println( "2.equals(1)     ? = [" + buf2.equals( buf1 ) + "]" );
 System.out.println( "1.startsWith(2) ? = [" + buf1.startsWith( buf2 ) + "]" );
 System.out.println( "1.endsWith(2)   ? = [" + buf1.endsWith( buf2 ) + "]" );
 System.out.println( "2.startsWith(1) ? = [" + buf2.startsWith( buf1 ) + "]" );
 System.out.println( "2.endsWith(1)   ? = [" + buf2.endsWith( buf1 ) + "]" );

 buf1.append( bytes );

 System.out.println( "1:instance = [" + buf1 + "]" );
 System.out.println( "1:length/capacity = [" + buf1.length() + "/" + buf1.capacity() + "]" );
 System.out.println( "2:instance = [" + buf2 + "]" );
 System.out.println( "2:length/capacity = [" + buf2.length() + "/" + buf2.capacity() + "]" );
 System.out.println( "1.equals(2)     ? = [" + buf1.equals( buf2 ) + "]" );
 System.out.println( "2.equals(1)     ? = [" + buf2.equals( buf1 ) + "]" );
 System.out.println( "1.startsWith(2) ? = [" + buf1.startsWith( buf2 ) + "]" );
 System.out.println( "1.endsWith(2)   ? = [" + buf1.endsWith( buf2 ) + "]" );
 System.out.println( "2.startsWith(1) ? = [" + buf2.startsWith( buf1 ) + "]" );
 System.out.println( "2.endsWith(1)   ? = [" + buf2.endsWith( buf1 ) + "]" );

 ByteBuffer buf3 = cx.ath.choisnet.test.TestSerializable.test( buf1 );
 System.out.println( "1.equals(3)     ? = [" + buf1.equals( buf3 ) + "]" );
 System.out.println( "3.equals(1)     ? = [" + buf3.equals( buf1 ) + "]" );
 System.out.println( "1.toString()    ? = [" + buf1.toString() + "]" );
 System.out.println( "3.toString()    ? = [" + buf3.toString() + "]" );

 ByteBuffer buf4 = buf1.clone();
 System.out.println( "1.equals(4)     ? = [" + buf1.equals( buf4 ) + "]" );
 System.out.println( "4.equals(1)     ? = [" + buf4.equals( buf1 ) + "]" );
 System.out.println( "1.toString()    ? = [" + buf1.toString() + "]" );
 System.out.println( "4.toString()    ? = [" + buf4.toString() + "]" );
}
*/

} // class
