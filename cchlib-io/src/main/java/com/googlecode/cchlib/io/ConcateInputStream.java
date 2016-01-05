package com.googlecode.cchlib.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * {@link InputStream} based on InputStream concatenation.
 * <p>
 * InputStreams are read in sequence, when first end of stream is reach, then
 * second stream is read (and next if any).
 * </p>
 */
public class ConcateInputStream extends InputStream
{
    private int currentStream;
    private final InputStream[] inputStreamArray;

    /**
     * Create a ConcateInputStream based on two {@link InputStream}
     *
     * @param firstInputStream  First {@link InputStream}
     * @param secondInputStream Second {@link InputStream}
     */
    public ConcateInputStream(
            final InputStream firstInputStream,
            final InputStream secondInputStream
            )
    {
        this.currentStream       = 0;
        this.inputStreamArray    = new java.io.InputStream[2]; // $codepro.audit.disable numericLiterals
        this.inputStreamArray[0] = firstInputStream;
        this.inputStreamArray[1] = secondInputStream;

        check();
    }

    /**
     * Create a ConcateInputStream based on an {@link InputStream} and
     * a {@link  String}
     *
     * @param inputStream   First {@link InputStream}
     * @param datas         Second stream based on a {@link  String}
     */
    public ConcateInputStream(
            final InputStream   inputStream,
            final String        datas
            )
    {
        this( inputStream, new ByteArrayInputStream( datas.getBytes() ) );
    }

    /**
     * Create a ConcateInputStream based on a {@link  String} and
     * an {@link InputStream}
     *
     * @param datas         First stream based on a {@link  String}
     * @param inputStream   Second {@link InputStream}
     */
    public ConcateInputStream( final String datas, final InputStream inputStream )
    {
        this( new ByteArrayInputStream( datas.getBytes() ), inputStream );
    }

    /**
     * Create a ConcateInputStream based on an array of {@link InputStream}
     *
     * @param iss list of {@link InputStream}
     * @since 4.1.5
     */
    public ConcateInputStream( final InputStream...iss )
    {
        this.currentStream      = 0;
        this.inputStreamArray   = iss;

        check();
    }

    @Override
    public int available() throws IOException
    {
        if(currentStream < inputStreamArray.length) {
            return inputStreamArray[currentStream].available();
            }
        else {
            return 0;
        }
    }

    @Override
    public void close() throws IOException
    {
        for (final InputStream inputStreamArray1 : inputStreamArray) {
            inputStreamArray1.close();
        }
    }

    @Override
    public int read() throws IOException
    {
        int result = -1;

        for(; currentStream < inputStreamArray.length; currentStream++) {
            result = inputStreamArray[currentStream].read();

            if(result != -1)  {
                return result;
            }
        }

        return result;
    }

    @Override
    public boolean markSupported()
    {
        return false;
    }

//    @Override
//    public void mark(int i)
//    {
//        super.mark(i);
//    }

//    @Override
//    public void reset() throws IOException
//    {
//        super.reset();
//    }

    private void check() throws RuntimeException
    {
        if(inputStreamArray == null) {
            throw new RuntimeException("inputStreamArray is null.");
            }
        final int length = inputStreamArray.length;

        for(int i = 0; i < length; i++)  {
            if(inputStreamArray[i] == null) {
                throw new RuntimeException("one or more InputStream is null.");
                }
            }
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("[");

        sb.append( getClass().getName() );
        sb.append('[');
        sb.append(currentStream);
        sb.append('/');
        sb.append(inputStreamArray.length);
        sb.append(']');
        sb.append(inputStreamArray[0].toString());

        for(int i = 1; i < inputStreamArray.length; i++) {
            sb.append(',');
            sb.append(inputStreamArray[i].toString());
             }

        sb.append(']');

        return sb.toString();
    }
}
