package cx.ath.choisnet.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import cx.ath.choisnet.ToDo;

/**
 * 
 * @author Claude CHOISNET
 *
 */
@ToDo
public class ConcateInputStream extends InputStream
{
    private int currentStream;
    private final InputStream[] inputStreamArray;

    public ConcateInputStream(InputStream firstInputStream, InputStream secondInputStream)
    {
        currentStream = 0;
        inputStreamArray = new java.io.InputStream[2];
        inputStreamArray[0] = firstInputStream;
        inputStreamArray[1] = secondInputStream;

        check();
    }

    public ConcateInputStream(InputStream inputStream, String datas)
    {
        this( inputStream, new ByteArrayInputStream( datas.getBytes() ) );
    }

    public ConcateInputStream( String datas, InputStream inputStream )
    {
        this( new ByteArrayInputStream( datas.getBytes() ), inputStream );
    }

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

    public void close()
        throws java.io.IOException
    {
        for(int i = 0; i < inputStreamArray.length; i++) {
            inputStreamArray[i].close();
        }
    }

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

    public boolean markSupported()
    {
        return false;
    }

    public void mark(int i)
    {
    }

    public void reset()
    {
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

    public String toString()
    {
        StringBuilder sb = new StringBuilder("[");

        sb.append( getClass().getName() );
        sb.append("[");
        sb.append(currentStream);
        sb.append("/");
        sb.append(inputStreamArray.length);
        sb.append("]");
        sb.append(inputStreamArray[0].toString());

        for(int i = 1; i < inputStreamArray.length; i++) {
            sb.append(",");
            sb.append(inputStreamArray[i].toString());
         }

        sb.append("]");

        return sb.toString();
    }
}
