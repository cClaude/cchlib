package cx.ath.choisnet.lang;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import cx.ath.choisnet.ToDo;

/**
 * Provide similar feature than {@link StringBuilder}
 * <p>
 * </p>
 * @author Claude CHOISNET
 *
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
    public void ensureCapacity(int minimumCapacity)
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
    public ByteArrayBuilder append(ReadableByteChannel channel)
        throws java.io.IOException
    {
        return append(channel, DEFAULT_SIZE);
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
     * @return array of bytes
     */
    public byte[] array()
    {
        byte[] bufferCopy = new byte[lastPos];

        System.arraycopy(buffer, 0, bufferCopy, 0, bufferCopy.length);

        return bufferCopy;
    }

    /**
     * TODO: Doc!
     * @param aByteArrayBuilder
     * @return true if current ByteArrayBuilder start with giving
     *         ByteArrayBuilder
     */
    public boolean startsWith(ByteArrayBuilder aByteArrayBuilder)
    {
        return startsWith(aByteArrayBuilder.array());
    }

    /**
     * TODO: Doc!
     * @param bytes 
     * @return
     */
    public boolean startsWith(byte[] bytes)
    {
        if(bytes.length > lastPos) {
            return false;
        }

        for(int i = 0; i < bytes.length; i++) {
            if(buffer[i] != bytes[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * TODO: Doc!
     * @param aByteArrayBuilder
     * @return
     */
    public boolean endsWith(ByteArrayBuilder aByteArrayBuilder)
    {
        return endsWith(aByteArrayBuilder.array());
    }

    /**
     * TODO: Doc!
     * @param bytes
     * @return
     */
    public boolean endsWith(byte[] bytes)
    {
        if(bytes.length > lastPos) {
            return false;
        }

        int j = lastPos - bytes.length;

        for(int i = 0; i < bytes.length; i++) {
            if(buffer[j++] != bytes[i]) {
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

    @Override
    public String toString()
    {
        return new String(buffer, 0, lastPos);
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
