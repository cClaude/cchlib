package cx.ath.choisnet.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import javax.annotation.Nonnull;

/**
 * {@link ByteBuffer} is a class similar than {@link StringBuilder}
 * but for bytes.
 * <p>
 * There is class was written before {@link java.nio.ByteBuffer} that
 * come with JDK 1.4, but is not really related.
 *
 * @since 1.53
 *
 * @see StringBuilder
 */
public class ByteBuffer implements Comparable<ByteBuffer>, Cloneable, Serializable
{
    private static final long serialVersionUID = 2L;

    private static final int  DEFAULT_SIZE     = 2048;

    private transient byte[]  buffer;
    private transient int     lastPos;

    /**
     * Constructs a {@link ByteBuffer} with no byte in it and an initial
     * capacity of 2048 bytes.
     */
    public ByteBuffer() // ----------------------------------------------------
    {
        this( DEFAULT_SIZE );
    }

    /**
     * Constructs a {@link ByteBuffer} with no byte in it and the specified
     * initial capacity
     *
     * @param capacity
     *            the initial capacity.
     */
    public ByteBuffer( final int capacity ) // --------------------------------------
    {
        this.buffer  = new byte[ capacity ];
        this.lastPos = 0;
    }

    /**
     * Constructs a ByteBuffer initialized to the contents of the specified array.
     * The initial capacity of the ByteBuffer is 2048 plus the length of the array
     * argument.
     *
     * @param bytes
     *            the array to copy
     *
     *            If bytes is null, an empty ByteBuffer is created with no byte
     *            in it and an initial capacity of 2048 bytes.
     */
    public ByteBuffer( final byte[] bytes ) // --------------------------------
    {
        this.lastPos = 0;

        if( bytes == null ) {
            this.buffer = new byte[ DEFAULT_SIZE ];
        } else {
            this.buffer = new byte[ DEFAULT_SIZE + bytes.length ];

            this.append( bytes, 0, bytes.length );
        }
    }

    /**
     * Constructs a {@link ByteBuffer} witch is a copy of the
     * given {@code byteBuffer}
     *
     * @param byteBuffer
     *            the {@link ByteBuffer} to copy.
     */
    public ByteBuffer( final ByteBuffer byteBuffer )
    {
        this.buffer  = new byte[ byteBuffer.buffer.length ];
        this.lastPos = byteBuffer.lastPos;

        System.arraycopy( byteBuffer.buffer, 0, this.buffer, 0, byteBuffer.lastPos );
    }

    /**
     ** Act as setLength( 0 );
     */
    public void reset() // ----------------------------------------------------
    {
        this.lastPos = 0;
    }

    /**
     * Sets the length of the byte sequence. The ByteBuffer is changed to a new
     * ByteBuffer whose length is specified by the argument.
     * <p>
     * If the newLength argument is less than the current length, the length is
     * changed to the specified length.
     * <p>
     * If the newLength argument is greater than or equal to the current length,
     * sufficient null byte are appended so that length becomes the newLength
     * argument.
     *
     * The newLength argument must be greater than or equal to 0.
     *
     * @param newLength
     *            the new length
     *
     * @throws IndexOutOfBoundsException
     *             if the newLength argument is negative.
     *
     * @see #length()
     */
    public void setLength( final int newLength ) // ---------------------------
        throws IndexOutOfBoundsException
    {
        if( newLength < 0 ) {
            throw new IndexOutOfBoundsException();
        }

        if( newLength < this.lastPos ) {
            this.lastPos = newLength;
        } else {
            ensureCapacity( newLength );

            for( int i = this.lastPos; i < newLength; i++ ) {
                this.buffer[ i ] = 0;
            }

            this.lastPos = newLength;
        }
    }

    /**
     * Returns the length (byte count).
     *
     * @return the length of the sequence of byte currently represented by this object
     */
    public int length() // ----------------------------------------------------
    {
        return this.lastPos;
    }

    /**
     * Returns the current capacity. The capacity is the amount of storage
     * available for newly inserted characters, beyond which an allocation
     * will occur.
     *
     * @return the current capacity
     */
    public int capacity() // --------------------------------------------------
    {
        return this.buffer.length;
    }

    /**
     *
     * Ensures that the capacity is at least equal to the specified minimum.
     * If the current capacity is less than the argument, then a new internal
     * array is allocated with greater capacity. The new capacity is the
     * larger of:
     * <ul>
     * <li>The minimumCapacity argument.</li>
     * <li>Twice the old capacity, plus 2.</li>
     * </ul>
     * If the minimumCapacity argument is non-positive, this method takes no
     * action and simply returns.
     *
     * @param minimumCapacity
     *            the minimum desired capacity.
     */
    public void ensureCapacity( final int minimumCapacity ) // ----------------
    {
        if( minimumCapacity > capacity() ) {
            //
            // Il faut agrandir le buffer
            //
            int newLength = this.buffer.length;

            while( newLength < minimumCapacity ) {
                newLength = (newLength + 1) << 1;
            }

            //
            // Allocation du nouveau buffer
            //
            final byte[] newBuffer = new byte[newLength];

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
     * Ajoute le tableau de bytes donne.
     *
     * @param bytes
     *            tableau de bytes e ajouter dans le buffer.
     *
     * @return l'objet ByteBuffer lui-meme
     */
    public ByteBuffer append( final byte[] bytes )
    {
        return append( bytes, 0, bytes.length );
    }

    /**
     * Ajoute une partie du tableau de bytes donne.
     *
     * @param bytes
     *            tableau de bytes e ajouter dans le buffer.
     * @param offset
     *            position de depart
     * @param len
     *            longueur.
     * @return l'objet ByteBuffer lui-meme
     */
    public ByteBuffer append( final byte[] bytes, final int offset, final int len )
    {
        final int lenOfCopy = len - offset;

        ensureCapacity( length() + lenOfCopy );

        System.arraycopy( bytes, offset, this.buffer, this.lastPos, lenOfCopy );

        this.lastPos += lenOfCopy;

        return this;
    }

    /**
     * Ajout le byte donne.
     *
     * @param b
     *            byte a ajouter dans le buffer.
     * @return l'objet ByteBuffer lui-meme
     */
    public ByteBuffer append( final byte b ) // TODO improve (need tests+ before)
    {
        try {
            this.buffer[ this.lastPos ] = b;
        }
        catch( final ArrayIndexOutOfBoundsException e ) {
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

    /*
     ** Ajoute le contenu courant du buffer nioByteBuffer
     **
     ** @return l'objet ByteBuffer lui-meme
     **
     ** @since 2.02.035 public ByteBuffer _append( final java.nio.ByteBuffer nioByteBuffer ) // ---- { // $$$$$$$$$$$ BOF
     *        !!!! A REVOIR !!! // $$$$$$$$$$$ BOF !!!! A REVOIR !!! // $$$$$$$$$$$ BOF !!!! A REVOIR !!!
     *
     *        while( nioByteBuffer.hasRemaining() ) { append( nioByteBuffer.get() ); } // $$$$$$$$$$$ BOF !!!! A REVOIR
     *        !!! // $$$$$$$$$$$ BOF !!!! A REVOIR !!! // $$$$$$$$$$$ BOF !!!! A REVOIR !!!
     *
     *        return this; }
     */

    /**
     * Add {@code channel} content to current buffer.
     *
     * @param channel
     *            bytes to add
     * @return Current {@link ByteBuffer} for initialization chaining
     * @throws IOException if any
     * @since 2.02
     */
    public ByteBuffer append( final ReadableByteChannel channel ) throws IOException
    {
        return append( channel, DEFAULT_SIZE );
    }

    /**
     * Add {@code channel} content to current buffer.
     *
     * @param channel
     *            bytes to add
     * @param bufferSize
     *            buffer size to use for reading data.
     * @return Current {@link ByteBuffer} for initialization chaining
     * @throws IOException if any
     */
    public ByteBuffer append(
        final ReadableByteChannel channel,
        final int                 bufferSize
        ) throws IOException
    {
        final byte[]              byteBuffer = new byte[ bufferSize ];
        final java.nio.ByteBuffer nioBuffer  = java.nio.ByteBuffer.wrap( byteBuffer );

        int len;

        while( (len = channel.read( nioBuffer )) != -1 ) {
            nioBuffer.flip();

            append( byteBuffer, 0, len );

            nioBuffer.clear();
        }

        return this;
    }

    /**
     * @return a copy of the internal buffer
     */
    public byte[] array()
    {
        final byte[] bufferCopy = new byte[this.lastPos];

        System.arraycopy( this.buffer, 0, bufferCopy, 0, bufferCopy.length );

        return bufferCopy;
    }

    /**
     * Tests if this {@link ByteBuffer} starts with the specified prefix.
     *
     * @param prefix
     *            the prefix
     * @return true if the giving {@link ByteBuffer} represented by the
     *         argument is a prefix of this {@link ByteBuffer};
     *         false otherwise.
     */
    public boolean startsWith( final ByteBuffer prefix )
    {
        return startsWith( prefix.array() );
    }

    /**
     * Tests if this {@link ByteBuffer} starts with the specified prefix.
     *
     * @param prefix
     *            the prefix
     * @return true if the giving byte array represented by the
     *         argument is a prefix of this {@link ByteBuffer};
     *         false otherwise.
     */
    public boolean startsWith( final byte[] prefix )
    {
        if( prefix.length > this.lastPos ) {
            return false;
        }

        for( int i = 0; i < prefix.length; i++ ) {
            if( this.buffer[ i ] != prefix[ i ] ) {
                return false;
            }
        }

        return true;
    }

    /**
     * Tests if this {@link ByteBuffer} ends with the specified prefix.
     *
     * @param prefix
     *            the prefix
     * @return true if the giving {@link ByteBuffer} represented by the
     *         argument is a suffix of this {@link ByteBuffer};
     *         false otherwise.
     */
    public boolean endsWith( final ByteBuffer prefix )
    {
        return endsWith( prefix.array() );
    }

    /**
     * Tests if this {@link ByteBuffer} ends with the specified prefix.
     *
     * @param prefix
     *            the prefix
     * @return true if the giving byte array represented by the
     *         argument is a suffix of this {@link ByteBuffer};
     *         false otherwise.
     */
    public boolean endsWith( final byte[] prefix )
    {
        if( prefix.length > this.lastPos ) {
            return false;
        }

        int j = this.lastPos - prefix.length;

        for( int i = 0; i < prefix.length; i++ ) {
            if( this.buffer[ j++ ] != prefix[ i ] ) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int compareTo( final ByteBuffer aByteBuffer )
    {
        //
        // Recupere la longueur la plus courte
        //
        final int length = this.lastPos < aByteBuffer.lastPos ? this.lastPos : aByteBuffer.lastPos;

        for( int i = 0; i < length; i++ ) {
            final int cmp = this.buffer[ i ] - aByteBuffer.buffer[ i ];

            if( cmp != 0 ) {
                return cmp;
            }
        }

        //
        // Ils sont egaux jusqu'à la position du plus court
        //
        return this.lastPos - aByteBuffer.lastPos;
    }

    /**
     * Constructs a new String by decoding the internal array of bytes using
     * the platform's default charset. The length of the new String is a function
     * of the charset, and hence may not be equal to the length of the internal array.
     *
     * @return a new String by decoding the internal array of bytes
     */
    @Override
    public String toString()
    {
        return new String( this.buffer, 0, this.lastPos );
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated use corresponding constructor instead.
     */
    @Override
    @Deprecated
    @SuppressWarnings({"squid:S1182","squid:S2975","squid:S1133"})
    public ByteBuffer clone()
    {
        return new ByteBuffer( this );
    }

    /*
     * java.io.Serializable
     *
     * @since 2.01.013
     */
    private void writeObject( final ObjectOutputStream stream )
            throws IOException
    {
        stream.defaultWriteObject();

        //
        // On sauvegarde le contenu des champs transient
        //
        stream.writeInt( this.buffer.length );

        final int max = this.lastPos;
        stream.writeInt( max );

        for( int i = 0; i < max; i++ ) {
            stream.writeByte( this.buffer[ i ] );
        }
    }

    /*
     * java.io.Serializable
     *
     * @since 2.01.013
     */
    private void readObject( final ObjectInputStream stream )
            throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();

        //
        // Reinitialisation des champs non sauvegardes automatiquements
        //
        this.buffer = new byte[stream.readInt()];

        final int max = stream.readInt();

        for( int i = 0; i < max; i++ ) {
            this.buffer[ i ] = stream.readByte();
        }

        this.lastPos = max;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + Arrays.hashCode( this.array() );
        result = (prime * result) + this.lastPos;
        return result;
    }

    @Override
    public boolean equals( final Object obj )
    {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( !(obj instanceof ByteBuffer) ) {
            return false;
        }
        final ByteBuffer other = (ByteBuffer)obj;
        if( !Arrays.equals( this.array() , other.array() ) ) {
            return false;
        }
        if( this.lastPos != other.lastPos ) {
            return false;
        }
        return true;
    }

    /**
     * Create a {@link ByteBuffer} be consuming an {@link InputStream}
     *
     * @param input
     *            {@link InputStream} to load in {@link ByteBuffer}
     * @return The new {@link ByteBuffer}
     * @throws IOException
     *             if any
     */
    public static ByteBuffer newByteBuffer( @Nonnull final InputStream input )
        throws IOException
    {
        final ByteBuffer bb     = new ByteBuffer();
        final byte[]     buffer = new byte[ 1024 ];

        int len;

        while( (len = input.read( buffer, 0, buffer.length )) != -1 ) {
            bb.append( buffer, 0, len );
        }

        return bb;
    }
}
