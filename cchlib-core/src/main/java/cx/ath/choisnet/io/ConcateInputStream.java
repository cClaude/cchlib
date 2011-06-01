package cx.ath.choisnet.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import cx.ath.choisnet.ToDo;

/**
 * {@link InputStream} based on InputStream concatenation.
 * <p>
 * InputStreams are read in sequence, when first end of stream is reatch, then
 * second stream is read.
 * </p>
 * @author Claude CHOISNET
 */
@ToDo
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
        currentStream = 0;
        inputStreamArray = new java.io.InputStream[2];
        inputStreamArray[0] = firstInputStream;
        inputStreamArray[1] = secondInputStream;

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
    public ConcateInputStream( String datas, InputStream inputStream )
    {
        this( new ByteArrayInputStream( datas.getBytes() ), inputStream );
    }

    @Override
    public int available()
        throws java.io.IOException
    {
        if(currentStream < inputStreamArray.length) {
            return inputStreamArray[currentStream].available();
            } 
        else {
            return 0;
        }
    }

    @Override
    public void close()
        throws java.io.IOException
    {
        for(int i = 0; i < inputStreamArray.length; i++) {
            inputStreamArray[i].close();
        }
    }

    @Override
    public int read()
        throws java.io.IOException
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

    @Override
    public void mark(int i)
    {
        super.mark(i);
    }

    @Override
    public void reset() throws IOException
    {
        super.reset();
    }

    private void check()
        throws RuntimeException
    {
        if(inputStreamArray == null) {
            throw new RuntimeException("inputStreamArray is null.");
        }
        int length = inputStreamArray.length;

        for(int i = 0; i < length; i++)  {
            if(inputStreamArray[i] == null) {
                throw new RuntimeException("one or more InputStream is null.");
                }
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("[");

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
