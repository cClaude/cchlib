package cx.ath.choisnet.util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.ReadableByteChannel;

/**
 * @author Claude CHOISNET
 * @deprecated use {@link cx.ath.choisnet.lang.ByteArrayBuilder} instead 
 */
@Deprecated
public class ByteBuffer
    implements Comparable<ByteBuffer>, Cloneable, java.io.Serializable
{
    private static final long serialVersionUID = 2L;
    private transient byte[]    buffer;
    private transient int       lastPos;
    private static final int DEFAULT_SIZE = 2048;

    /**
     * Cre
     */
    public ByteBuffer()
    {
        this(DEFAULT_SIZE);
    }

    public ByteBuffer(int capacity)
    {
        buffer = new byte[capacity];
        lastPos = 0;
    }

    public ByteBuffer(byte[] bytes)
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

    public void reset()
    {
        lastPos = 0;
    }

    public void setLength(int newLength)
        throws java.lang.IndexOutOfBoundsException
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

    public int length()
    {
        return lastPos;
    }

    public int capacity()
    {
        return buffer.length;
    }

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

    public ByteBuffer append(byte[] bytes)
    {
        return append(bytes, 0, bytes.length);
    }

    public ByteBuffer append(byte[] bytes, int offset, int len)
    {
        int lenOfCopy = len - offset;

        ensureCapacity(length() + lenOfCopy);
        System.arraycopy(bytes, offset, buffer, lastPos, lenOfCopy);
        lastPos += lenOfCopy;
        return this;
    }

    public ByteBuffer append(byte b)
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

    public ByteBuffer append(ReadableByteChannel channel)
        throws java.io.IOException
    {
        return append(channel, DEFAULT_SIZE);
    }

    public ByteBuffer append(ReadableByteChannel channel, int bufferSize)
        throws java.io.IOException
    {
        byte[] byteBuffer = new byte[bufferSize];

        java.nio.ByteBuffer buffer = java.nio.ByteBuffer.wrap(byteBuffer);

        int len;
        while((len = channel.read(buffer)) != -1) {
            buffer.flip();

            append(byteBuffer, 0, len);
            buffer.clear();
        }

        return this;
    }

    public byte[] array()
    {
        byte[] bufferCopy = new byte[lastPos];

        System.arraycopy(buffer, 0, bufferCopy, 0, bufferCopy.length);

        return bufferCopy;
    }

    public boolean startsWith(ByteBuffer aByteBuffer)
    {
        return startsWith(aByteBuffer.array());
    }

    public boolean startsWith(byte[] pattern)
    {
        if(pattern.length > lastPos) {
            return false;
        }

        for(int i = 0; i < pattern.length; i++) {
            if(buffer[i] != pattern[i]) {
                return false;
            }
        }

        return true;
    }

    public boolean endsWith(ByteBuffer aByteBuffer)
    {
        return endsWith(aByteBuffer.array());
    }

    public boolean endsWith(byte[] pattern)
    {
        if(pattern.length > lastPos) {
            return false;
        }

        int j = lastPos - pattern.length;

        for(int i = 0; i < pattern.length; i++) {
            if(buffer[j++] != pattern[i]) {
                return false;
            }
        }

        return true;
    }

    public int compareTo(ByteBuffer aByteBuffer)
    {
        int length = lastPos >= aByteBuffer.lastPos ? aByteBuffer.lastPos : lastPos;

        for(int i = 0; i < length; i++) {
            int cmp = buffer[i] - aByteBuffer.buffer[i];
            if(cmp != 0) {
                return cmp;
            }
        }

        return lastPos - aByteBuffer.lastPos;
    }

    public boolean equals(Object o)
    {
        try {
            return compareTo((cx.ath.choisnet.util.ByteBuffer)o) == 0;
        }
        catch(ClassCastException e) {
            return false;
        }
    }

    public String toString()
    {
        return new String(buffer, 0, lastPos);
    }

    public ByteBuffer clone()
        throws java.lang.CloneNotSupportedException
    {
        ByteBuffer newByteBuffer = (cx.ath.choisnet.util.ByteBuffer)super.clone();

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
