package com.googlecode.cchlib.lang;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.Arrays;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.googlecode.cchlib.io.IOHelper;

/**
 * <p>
 * Provide similar feature than {@link StringBuilder}
 * for byte array's
 * </p>
 */
public class ByteArrayBuilder // $codepro.audit.disable largeNumberOfMethods
    implements  Comparable<ByteArrayBuilder>,
                Cloneable,
                Serializable
{
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_SIZE = 2048;

    private transient byte[] buffer;
    private transient int    lastPos;

    /**
     * Create a ByteArrayBuilder with a default capacity of 2048 bytes
     */
    public ByteArrayBuilder()
    {
        this(DEFAULT_SIZE);
    }

    /**
     * Create a ByteArrayBuilder with giving capacity
     *
     * @param capacity size of internal buffer
     */
    public ByteArrayBuilder(final int capacity)
    {
        this.buffer = new byte[capacity];
        this.lastPos = 0;
    }

    /**
     * Create a ByteArrayBuilder with a default
     * capacity of 2048 bytes plus giving buffer size
     * and append giving bytes to buffer
     *
     * @param bytes array of bytes to append to the buffer (could be null)
     */
    public ByteArrayBuilder(@Nullable final byte[] bytes)
    {
        this.lastPos = 0;

        if( bytes == null ) {
            this.buffer = new byte[DEFAULT_SIZE];
            }
        else {
            this.buffer = new byte[DEFAULT_SIZE + bytes.length];

            append(bytes, 0, bytes.length);
            }
    }

    /**
     * Empty buffer, similar to setLength(0) but quickest
     */
    public void reset()
    {
        this.lastPos = 0;
    }

    /**
     * Set length of ByteArrayBuilder
     *
     * @param newLength new length for buffer, if newLength is
     * bigger than current length buffer is padding with 0
     * @throws IndexOutOfBoundsException if newLength is negative
     */
    public void setLength( final int newLength )
        throws IndexOutOfBoundsException
    {
        if(newLength < 0) {
            throw new IndexOutOfBoundsException();
            }

        if(newLength < this.lastPos) {
            this.lastPos = newLength;
            }
        else {
            ensureCapacity(newLength);

            for(int i = this.lastPos; i < newLength; i++) {
                this.buffer[i] = 0;
                }

            this.lastPos = newLength;
            }
    }

    /**
     * @return current length of buffer
     */
    public int length()
    {
        return this.lastPos;
    }

    /**
     * @return current capacity of buffer
     */
    public int capacity()
    {
        return this.buffer.length;
    }

    /**
     * Ensure minimum capacity of buffer
     *
     * @param minimumCapacity minimum capacity of buffer
     */
    public void ensureCapacity(final int minimumCapacity)
    {
        if(minimumCapacity > capacity()) {
            int newLength;

            for(newLength = this.buffer.length; newLength < minimumCapacity; ) {
                newLength = (newLength + 1) << 1;
                }

            final byte[] newBuffer = new byte[newLength];

            System.arraycopy(this.buffer, 0, newBuffer, 0, this.buffer.length);

            this.buffer = newBuffer;
            }
    }

    /**
     * Append a byte array
     *
     * @param bytes a valid bytes array
     * @return caller for initialization chaining
     */
    public ByteArrayBuilder append(@Nonnull final byte[] bytes)
    {
        return append(bytes, 0, bytes.length);
    }

    /**
     * Append a some bytes from a bytes array
     *
     * @param bytes a valid bytes array
     * @param offset first offset
     * @param len  length of copy
     * @return caller for initialization chaining
     */
    public final ByteArrayBuilder append(@Nonnull final byte[] bytes, final int offset, final int len)
    {
        // this method is final because: use in constructor
        ensureCapacity(length() + len);
        System.arraycopy(bytes, offset, this.buffer, this.lastPos, len);
        this.lastPos += len;

        return this;
    }

    /**
     * Append a byte to buffer
     * @param b byte to append
     * @return caller for initialization chaining
     */
    public ByteArrayBuilder append(final byte b)
    {
        try {
            this.buffer[this.lastPos] = b;
            }
        catch(final ArrayIndexOutOfBoundsException e) { // $codepro.audit.disable logExceptions
            ensureCapacity(capacity() + 1);
            this.buffer[this.lastPos] = b;
            }

        this.lastPos++;

        return this;
    }

    /**
     * Apprend content of an {@link InputStream}
     * <br>
     * (InputStream is not close by this method)
     *
     * @param is InputStream to add
     * @return caller for initialization chaining
     * @throws java.io.IOException if any I/O occurs
     */
    public ByteArrayBuilder append(@Nonnull final InputStream is)
        throws IOException
    {
        return append(is, DEFAULT_SIZE);
    }

    /**
     * Append content of an {@link InputStream}
     * <br>
     * (InputStream is not close by this method)
     *
     * @param is InputStream to add
     * @param bufferSize intermediate buffer size
     * @return caller for initialization chaining
     * @throws IOException If the first byte cannot be read
     *         for any reason other than the end of the file,
     *         if the input stream has been closed, or if some
     *         other I/O error occurs.
     */
    public ByteArrayBuilder append(
            @Nonnull final InputStream is,
            final int                  bufferSize
            )
        throws IOException
    {
        return append(is, new byte[bufferSize]);
    }

    /**
     * Apprend content of an {@link InputStream}
     * <br>
     * (InputStream is not close by this method)
     *
     * @param is {@link InputStream} to add
     * @param b  Buffer for {@link InputStream} reading.
     * @return caller for initialization chaining
     * @throws IOException If the first byte cannot be read
     *         for any reason other than the end of the file,
     *         if the input stream has been closed, or if some
     *         other I/O error occurs.
     * @throws NullPointerException if b is null.
     */
    public ByteArrayBuilder append(
            @Nonnull final InputStream is,
            @Nonnull final byte[]      b
            )
        throws IOException
    {
        int len;

        while( (len = is.read( b )) != -1 ) {
            append( b, 0, len);
            }

        return this;
    }

    /**
     * Append {@link ReadableByteChannel} content to buffer.
     *
     * @param channel channel to append to buffer
     * @return caller for initialization chaining
     * @throws IOException if any I/O occurs
     * @see java.io.FileInputStream#getChannel()
     * @see java.nio.channels.FileChannel
     */
    public ByteArrayBuilder append( @Nonnull final ReadableByteChannel channel )
        throws IOException
    {
        return append(channel, DEFAULT_SIZE);
    }

    /**
     * Append {@link ReadableByteChannel} content to buffer.
     *
     * @param channel channel to append to buffer
     * @param bufferSize temporary buffer size.
     * @return caller for initialization chaining
     * @throws IOException if any I/O occurs
     */
    public ByteArrayBuilder append( //
        @Nonnull     final ReadableByteChannel channel, //
        @Nonnegative final int                 bufferSize)
        throws IOException
    {
        final byte[]     byteBuffer = new byte[bufferSize];
        final ByteBuffer bbuffer    = ByteBuffer.wrap(byteBuffer);

        int len;

        while((len = channel.read(bbuffer)) != -1) {
            bbuffer.flip();

            append(byteBuffer, 0, len);
            bbuffer.clear();
            }

        return this;
    }

    /**
     * Returns a copy of internal buffer.
     * @return array of bytes
     */
    public byte[] array()
    {
        final byte[] bufferCopy = new byte[this.lastPos];

        System.arraycopy(this.buffer, 0, bufferCopy, 0, bufferCopy.length);

        return bufferCopy;
    }

    /**
     * Tests if this ByteArrayBuilder starts with the
     * specified prefix.
     *
     * @param prefix the prefix
     * @return true if current ByteArrayBuilder start with giving
     *         ByteArrayBuilder
     */
    public boolean startsWith(@Nonnull final ByteArrayBuilder prefix)
    {
        return startsWith(prefix.array());
    }

    /**
     * Tests if this ByteArrayBuilder starts with the
     * specified prefix.
     *
     * @param prefix the prefix
     * @return true if current ByteArrayBuilder start with giving
     *         bytes
     */
    public boolean startsWith(@Nonnull final byte[] prefix)
    {
        if( prefix.length > this.lastPos ) {
            return false;
            }

        for(int i = 0; i < prefix.length; i++) {
            if(this.buffer[i] != prefix[i]) {
                return false;
                }
            }

        return true;
    }

    /**
     * Tests if this ByteArrayBuilder ends with the
     * specified suffix.
     *
     * @param suffix the suffix.
     * @return true if the byte sequence represented by
     *         the argument is a suffix of the byte
     *         sequence represented by this object;
     *         false otherwise.
     */
    public boolean endsWith(@Nonnull final ByteArrayBuilder suffix)
    {
        return endsWith(suffix.array());
    }

    /**
     * Tests if this ByteArrayBuilder ends with the
     * specified suffix.
     *
     * @param suffix the suffix.
     * @return true if the byte sequence represented by
     *         the argument is a suffix of the byte
     *         sequence represented by this object;
     *         false otherwise.
     */
    public boolean endsWith(@Nonnull final byte[] suffix)
    {
        if( suffix.length > this.lastPos ) {
            return false;
            }

        int j = this.lastPos - suffix.length;

        for( int i = 0; i < suffix.length; i++ ) {
            if( this.buffer[j++] != suffix[i] ) {
                return false;
                }
            }

        return true;
    }

    /**
     * Compare content of ByteArrayBuilders
     *
     * @param aByteArrayBuilder to compare
     * @return 0 if ByteArrayBuilder have same content, none 0
     *         if different.
     */
    @Override
    public int compareTo(@Nonnull final ByteArrayBuilder aByteArrayBuilder)
    {
        final int length = (this.lastPos >= aByteArrayBuilder.lastPos)
                        ? aByteArrayBuilder.lastPos
                        : this.lastPos;

        for( int i = 0; i < length; i++ ) {
            final int cmp = this.buffer[i] - aByteArrayBuilder.buffer[i];

            if(cmp != 0) {
                return cmp;
                }
            }

        return this.lastPos - aByteArrayBuilder.lastPos;
    }

    @Override
    public boolean equals(final Object o) // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.obeyEqualsContract.obeyGeneralContractOfEquals
    {
        if( this == o ) {
            return true;
            }
        else if( o instanceof ByteArrayBuilder ) {
            return compareTo( ByteArrayBuilder.class.cast( o )) == 0;
            }
        return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 3; // $codepro.audit.disable numericLiterals
        hash = (97 * hash) + Arrays.hashCode(this.buffer); // $codepro.audit.disable numericLiterals
        hash = (97 * hash) + this.lastPos; // $codepro.audit.disable numericLiterals
        return hash;
    }

    /**
     * Returns internal buffer as a String by decoding
     * the internal array of bytes using the platform's
     * default charset.
     *
     * @return a string representing the data in this sequence.
     */
    @Override
    public String toString()
    {
        return new String(this.buffer, 0, this.lastPos);
    }

    /**
     * Returns internal buffer as a String by decoding
     * the internal array of bytes using the specified <code>charset</code>.
     *
     * @param charset {@link Charset} to use for encoding
     * @return a string representing the data in this sequence.
     */
    public String toString( final Charset charset )
    {
        return new String(this.buffer, 0, this.lastPos, charset);
    }

    @Override
    public ByteArrayBuilder clone() // NOSONAR
        throws CloneNotSupportedException
    {
        final ByteArrayBuilder newByteBuffer = (ByteArrayBuilder)super.clone();

        newByteBuffer.buffer = new byte[this.buffer.length];

        final int max = this.lastPos;
        newByteBuffer.lastPos = max;

        System.arraycopy(this.buffer, 0, newByteBuffer.buffer, 0, max);

        return newByteBuffer;
    }

    /**
     * Create a new {@link InputStream} for this {@link ByteArrayBuilder}
     *
     * @return a new {@link InputStream} (must be close)
     * @throws IOException if any I/O error occurred
     * @see #array()
     * @since 4.1.7
     */
    public InputStream createInputStream() throws IOException
    {
        return new ByteArrayInputStream( this.array() );
    }

    /**
     * Copy  {@link ByteArrayBuilder} content to <code>out</code>
     *
     * @param out A valid {@link OutputStream}
     * @throws IOException if any I/O error occurred
     * @see #array()
     * @since 4.1.7
     */
    public void copyTo( @Nonnull final OutputStream out ) throws IOException
    {
        try (ByteArrayInputStream is = new ByteArrayInputStream( this.array() )) {
            IOHelper.copy( is, out );
            }
    }

    /**
     * Replaces each sub byte array of this ByteArrayBuilder that matches the
     * given pattern with the given replacement.
     *
     * @param pattern Pattern to replace
     * @param replace Replace value for pattern
     * @return a new ByteArrayBuilder
     */
    public ByteArrayBuilder replaceAll(
        @Nonnull final byte[]    pattern,
        @Nonnull final byte[]    replace
        )
    {
        return replace( this.array(), pattern, replace );
    }

    /**
     * TODOC
     * TODO make public (improve?)
     *
     * @param sbytes
     * @param pattern
     * @param replace
     * @return a new ByteArrayBuilder
     */
    private static ByteArrayBuilder replace(
        @Nonnull final byte[]    sbytes,
        @Nonnull final byte[]    pattern,
        @Nonnull final byte[]    replace
        )
    {
        byte[] dbytes;

        int i = KPM.indexOf( sbytes, pattern );

        if( i>=0 ) {
            final ByteArrayBuilder babDest = new ByteArrayBuilder();
            int              offset  = 0;

            do {
                if( i > 0 ) {
                    // add before pattern
                    babDest.append( sbytes, offset, i - offset);
                    }
                // add replace
                babDest.append( replace );

                offset = i + pattern.length;

                i = KPM.indexOf( sbytes, offset, pattern );
                } while( i >= 0 );
            // add after last pattern
            babDest.append( sbytes, offset, sbytes.length - offset );

            dbytes = babDest.array();
            }
        else {
            dbytes = sbytes;
            }

        return new ByteArrayBuilder( dbytes );
    }

    private void writeObject( @Nonnull final ObjectOutputStream stream ) throws IOException
    {
        stream.defaultWriteObject();
        stream.writeInt(this.buffer.length);

        final int max = this.lastPos;

        stream.writeInt(max);

        for(int i = 0; i < max; i++) {
            stream.writeByte(this.buffer[i]);
        }
    }

    private void readObject( @Nonnull final ObjectInputStream stream ) throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();
        this.buffer = new byte[stream.readInt()];

        final int max = stream.readInt();

        for(int i = 0; i < max; i++) {
            this.buffer[i] = stream.readByte();
            }

        this.lastPos = max;
    }


    /**
     * The Knuth-Morris-Pratt Pattern Matching Algorithm can be used
     * to search a byte array.
     */
    private static class KPM
    {
        /**
         * Search the data byte array for the first occurrence
         * of the byte array pattern.
         */
        public static int indexOf(final byte[] data, final byte[] pattern)
        {
            return indexOf( data, 0, pattern );
        }

        /**
         * Search the data byte array for the first occurrence
         * of the byte array pattern.
         */
        public static int indexOf(final byte[] data, final int from, final byte[] pattern)
        {
            final int[] failure = computeFailure( pattern );

            int j = 0;

            for (int i = from/*0*/; i < data.length; i++) {
                while( (j > 0) && (pattern[j] != data[i]) ) {
                    j = failure[j - 1];
                    }

                if (pattern[j] == data[i]) {
                    j++;
                    }

                if( j == pattern.length ) {
                    return (i - pattern.length) + 1;
                    }
                }
            return -1;
        }

        /**
         * Computes the failure function using a boot-strapping process,
         * where the pattern is matched against itself.
         */
        private static int[] computeFailure( final byte[] pattern )
        {
            final int[] failure = new int[ pattern.length ];
            int   j       = 0;

            for( int i = 1; i < pattern.length; i++ ) {
                while( (j>0) && (pattern[j] != pattern[i]) ) {
                    j = failure[j - 1];
                    }

                if( pattern[j] == pattern[i] ) {
                    j++;
                    }

                failure[i] = j;
                }

            return failure;
        }
    }
}
