package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 
 * @author Claude CHOISNET
 *
 */
public class InputStreamThread extends Thread
{
    private final InputStream is;
    private final PipedOutputStream pipeOut;
    private final PipedInputStream pipeIn;
    private boolean running;
    private static final int ERROR_MAX = 10;

    public InputStreamThread(InputStream is)
        throws java.io.IOException
    {
        this("InputStreamThread", is);
    }

    public InputStreamThread(String threadName, InputStream is)
        throws java.io.IOException
    {
        super(threadName);

        this.is = is;

        pipeOut = new PipedOutputStream();
        pipeIn  = new PipedInputStream(pipeOut);
        running = true;

        setDaemon(true);
    }

    public InputStream getInputStream()
    {
        return pipeIn;
    }

    public void run()
    {
        int errorCount = 0;

        for(;;) { //_L2:
        if(!running)
        {
            break; /* Loop/switch isn't completed */
        }
    //    2    2:aload_0
    //    3    3:getfield        #11  <Field boolean cx.ath.choisnet.io.InputStreamThread.running>
    //    4    6:ifeq            60
        int c;
        try {
            c = is.read();
        }
        catch( IOException e ) {
            throw new RuntimeException( "InputStreamRun.run()" ,e );
        }
    //    5    9:aload_0
    //    6   10:getfield        #4   <Field java.io.InputStream cx.ath.choisnet.io.InputStreamThread.is>
    //    7   13:invokevirtual   #13  <Method int java.io.InputStream.read()>
    //    8   16:istore_2
        if(c == -1)
    //*   9   17:iload_2
    //*  10   18:iconst_m1
    //*  11   19:icmpne          25
        {
            break; /* Loop/switch isn't completed */
    //   12   22:goto            60
        }
        try
        {
            pipeOut.write(c);
    //   13   25:aload_0
    //   14   26:getfield        #7   <Field java.io.PipedOutputStream cx.ath.choisnet.io.InputStreamThread.pipeOut>
    //   15   29:iload_2
    //   16   30:invokevirtual   #14  <Method void java.io.PipedOutputStream.write(int)>
        }
    //*  17   33:goto            2
        catch(java.io.IOException e)
    //*  18   36:astore_3
        {
            if(errorCount++ > ERROR_MAX)
    //*  19   37:iload_1
    //*  20   38:iinc            1  1
    //*  21   41:bipush          10
    //*  22   43:icmple          57
            {
                throw new RuntimeException("Internal error (" + ERROR_MAX + " times)", e);
    //   23   46:new             #16  <Class RuntimeException>
    //   24   49:dup
    //   25   50:ldc1            #17  <String "Internal error (10 times)">
    //   26   52:aload_3
    //   27   53:invokespecial   #18  <Method void RuntimeException(String, Throwable)>
    //   28   56:athrow
            }
        }
        } // for(;;)
//        if(true) goto _L2; else goto _L1
//    //   29   57:goto            2
//_L1:
        try {
            pipeOut.close();
        }
        catch(java.io.IOException e) {
            throw new RuntimeException("InputStreamRun.run()", e);
        }
        return;
    }

    public void close()
        throws java.io.IOException
    {
        running = false;
    }
}
