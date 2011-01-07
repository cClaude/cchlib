package cx.ath.choisnet.lang;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import cx.ath.choisnet.ToDo;

/**
 * <p>
 * Provide similar feature than {@link StringBuilder}
 * </p>
 * 
 * @author Claude CHOISNET
 */
public class ByteArrayBuilder
    implements  Comparable<ByteArrayBuilder>,
                Cloneable,
                Serializable
{
    private static final long serialVersionUID = 2L;
    private static final int DEFAULT_SIZE = 2048;
    private transient byte[]    buffer;
    private transient int       lastPos;

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
    public ByteArrayBuilder(int capacity)
    {
        buffer = new byte[capacity];
        lastPos = 0;
    }

    /**
     * Create a ByteArrayBuilder with a default
     * capacity of 2048 bytes plus giving buffer size
     * and append giving bytes to buffer
     *
     * @param bytes array of bytes to append to the buffer (could be null)
     */
    public ByteArrayBuilder(byte[] bytes)
    {
        lastPos = 0;

        if(bytes == null) {
            buffer = new byte[DEFAULT_SIZE];
        }
        else {
            buffer = new byte[DEFAULT_SIZE + bytes.length];

            append(bytes, 0, bytes.length);
        }
    }

    /**
     * Empty buffer, similar to setLength(0) but quickest
     */
    public void reset()
    {
        lastPos = 0;
    }

    /**
     * Set length of ByteArrayBuilder
     *
     * @param newLength new length for buffer, if newLength is
     * bigger than current length buffer is padding with 0
     * @throws IndexOutOfBoundsException if newLength is negative
     */
    public void setLength(int newLength)
        throws IndexOutOfBoundsException
    {
        if(newLength < 0) {
            throw new IndexOutOfBoundsException();
        }

        if(newLength < lastPos) {
            lastPos = newLength;
        }
        else {
            ensureCapacity(newLength);

            for(int i = lastPos; i < newLength; i++) {
                buffer[i] = 0;
            }

            lastPos = newLength;
        }
    }

    /**
     * @return current length of buffer
     */
    public int length()
    {
        return lastPos;
    }

    /**
     * @return current capacity of buffer
     */
    public int capacity()
    {
        return buffer.length;
    }

    /**
     * Ensure minimum capacity of buffer
     *
     * @param minimumCapacity minimum capacity of buffer
     */
    public void ensureCapacity( int minimumCapacity )
    {
        if(minimumCapacity > capacity()) {
            int newLength;

            for(newLength = buffer.length; newLength < minimumCapacity; ) {
                newLength = newLength + 1 << 1;
            }

            byte[] newBuffer = new byte[newLength];

            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);

            buffer = newBuffer;
        }
    }

    /**
     * Append a byte array
     *
     * @param bytes a valid bytes array
     * @return caller for initialization chaining
     */
    public ByteArrayBuilder append(byte[] bytes)
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
    public ByteArrayBuilder append(byte[] bytes, int offset, int len)
    {
//        int lenOfCopy = len - offset;
//
//        ensureCapacity(length() + lenOfCopy);
//        System.arraycopy(bytes, offset, buffer, lastPos, lenOfCopy);
//        lastPos += lenOfCopy;
        //TODO: TestCase !
        ensureCapacity(length() + len);
        System.arraycopy(bytes, offset, buffer, lastPos, len);
        lastPos += len;
        return this;
    }

    /**
     * Append a byte to buffer
     * @param b byte to append
     * @return caller for initialization chaining
     */
    public ByteArrayBuilder append(byte b)
    {
        try {
            buffer[lastPos] = b;
        }
        catch(ArrayIndexOutOfBoundsException e) {
            ensureCapacity(capacity() + 1);
            buffer[lastPos] = b;
        }

        lastPos++;

        return this;
    }

    /**
     * TODO: Doc!
     * TODO: TestCase
     *
     * @param channel
     * @return caller for initialization chaining
     * @throws java.io.IOException
     */
    @ToDo
    public ByteArrayBuilder append(ReadableByteChannel channel)
        throws java.io.IOException
    {
        return append(channel, DEFAULT_SIZE);
    }
    
    /**
     * TODO: Doc!
     * TODO: TestCase
     *
     * @param is InputStream to add
     * @return caller for initialization chaining
     * @throws java.io.IOException
     */
    @ToDo
    public ByteArrayBuilder append(InputStream is)
        throws java.io.IOException
    {
        return append(is, DEFAULT_SIZE);
    }
    
    /**
     * TODO: Doc!
     * TODO: TestCase
     *
     * @param is InputStream to add
     * @param bufferSize intermediate buffer size
     * @return caller for initialization chaining
     * @throws java.io.IOException
     */
    @ToDo
    public ByteArrayBuilder append(
            InputStream is,
            int         bufferSize
            )
        throws java.io.IOException
    {
        return append(is, new byte[bufferSize]);
    }
    
    /**
     * TODO: Doc!
     * TODO: TestCase
     *
     * @param is InputStream to add
     * @param intermediateBuffer intermediate buffer for
     *        InputStream reading.
     * @return caller for initialization chaining
     * @throws java.io.IOException
     */
    @ToDo
    public ByteArrayBuilder append(
            InputStream is,
            byte[]      intermediateBuffer
            )
        throws java.io.IOException
    {
        int len;
        
        while( (len = is.read( intermediateBuffer )) != -1 ) {
            append( intermediateBuffer, 0, len);
        }
        
        return this;
    }

    /**
     * TODO: Doc!
     * TODO: TestCase
     *
     * @param channel
     * @param bufferSize
     * @return caller for initialization chaining
     * @throws java.io.IOException
     */
    public ByteArrayBuilder append(ReadableByteChannel channel, int bufferSize)
        throws java.io.IOException
    {
        byte[] byteBuffer = new byte[bufferSize];

        ByteBuffer buffer = ByteBuffer.wrap(byteBuffer);

        int len;
        while((len = channel.read(buffer)) != -1) {
            buffer.flip();

            append(byteBuffer, 0, len);
            buffer.clear();
        }

        return this;
    }

    /**
     * Returns a copy of internal buffer.
     * @return array of bytes
     */
    public byte[] array()
    {
        byte[] bufferCopy = new byte[lastPos];

        System.arraycopy(buffer, 0, bufferCopy, 0, bufferCopy.length);

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
    public boolean startsWith(ByteArrayBuilder prefix)
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
    public boolean startsWith(byte[] prefix)
    {
        if(prefix.length > lastPos) {
            return false;
        }

        for(int i = 0; i < prefix.length; i++) {
            if(buffer[i] != prefix[i]) {
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
    public boolean endsWith(ByteArrayBuilder suffix)
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
    public boolean endsWith(byte[] suffix)
    {
        if(suffix.length > lastPos) {
            return false;
        }

        int j = lastPos - suffix.length;

        for(int i = 0; i < suffix.length; i++) {
            if(buffer[j++] != suffix[i]) {
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
    public int compareTo(ByteArrayBuilder aByteArrayBuilder)
    {
        int length = lastPos >= aByteArrayBuilder.lastPos 
                        ? aByteArrayBuilder.lastPos 
                        : lastPos;

        for(int i = 0; i < length; i++) {
            int cmp = buffer[i] - aByteArrayBuilder.buffer[i];
            
            if(cmp != 0) {
                return cmp;
            }
        }

        return lastPos - aByteArrayBuilder.lastPos;
    }

    @Override
    public boolean equals(Object o)
    {
        if( o instanceof ByteArrayBuilder ) {
            return compareTo( ByteArrayBuilder.class.cast( o )) == 0;
        }
        return false;
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
        return new String(buffer, 0, lastPos);
    }

    /**
     * Returns internal buffer as a String by decoding
     * the internal array of bytes using the specified charset.
     * 
     * @return a string representing the data in this sequence. 
     */
    public String toString(Charset charset)
    {
        return new String(buffer, 0, lastPos, charset);
    }

    @Override
    public ByteArrayBuilder clone()
        throws java.lang.CloneNotSupportedException
    {
        ByteArrayBuilder newByteBuffer = (ByteArrayBuilder)super.clone();

        newByteBuffer.buffer = new byte[buffer.length];
        int max = lastPos;
        newByteBuffer.lastPos = max;

        System.arraycopy(buffer, 0, newByteBuffer.buffer, 0, max);

        return newByteBuffer;
    }

    private void writeObject(ObjectOutputStream stream)
        throws java.io.IOException
    {
        stream.defaultWriteObject();
        stream.writeInt(buffer.length);

        int max = lastPos;

        stream.writeInt(max);

        for(int i = 0; i < max; i++) {
            stream.writeByte(buffer[i]);
        }
    }

    private void readObject(ObjectInputStream stream)
        throws java.io.IOException, ClassNotFoundException
    {
        stream.defaultReadObject();
        buffer = new byte[stream.readInt()];

        int max = stream.readInt();

        for(int i = 0; i < max; i++) {
            buffer[i] = stream.readByte();
        }

        lastPos = max;
    }

}
