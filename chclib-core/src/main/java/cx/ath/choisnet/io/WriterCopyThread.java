package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class WriterCopyThread extends Thread
{
    private final Reader source;
    private final Writer destination;
    private boolean running;
    private boolean closeSource;
    private static final int ERROR_MAX = 10;
    private Object lock;
    private Writer spyWriter;

    public WriterCopyThread(Reader source, Writer destination)
        throws java.io.IOException
    {
        lock = new Object();
        spyWriter = null;

        super.setName((new StringBuilder()).append(getClass().getName()).append('@').append(Integer.toHexString(hashCode())).toString());

        this.source = source;
        this.destination = destination;
        running = true;
        closeSource = false;

        setDaemon(true);
    }

    public WriterCopyThread(String threadName, java.io.Reader source, java.io.Writer destination)
        throws java.io.IOException
    {
        this(source, destination);

        super.setName(threadName);
    }

    public void run()
    {
        int errorCount = 0;
        // 0 0:iconst_0
        // 1 1:istore_1
        for( ;; ) { // _L2:
            if( !running ) {
                break; /* Loop/switch isn't completed */
            }
            // 2 2:aload_0
            // 3 3:getfield #18 <Field boolean
            // cx.ath.choisnet.io.WriterCopyThread.running>
            // 4 6:ifeq 114
            int c;
            try {
                c = source.read();
            }
            catch( IOException e ) {
                // TODO Auto-generated catch block
                throw new RuntimeException( "StreamCopyThread.run() -  in "
                        + getName(), e );
            }
            // 5 9:aload_0
            // 6 10:getfield #16 <Field java.io.Reader
            // cx.ath.choisnet.io.WriterCopyThread.source>
            // 7 13:invokevirtual #22 <Method int java.io.Reader.read()>
            // 8 16:istore_2
            if( c == -1 )
            // * 9 17:iload_2
            // * 10 18:iconst_m1
            // * 11 19:icmpne 25
            {
                break; /* Loop/switch isn't completed */
                // 12 22:goto 114
            }

            try {
                synchronized( lock ) {
                    if( spyWriter != null ) {
                        spyWriter.write( c );
                    }
                }
                // 25 47:aload_3
                // 26 48:monitorexit
                // 27 49:goto 59
                // 28 52:astore 4
                // 29 54:aload_3
                // 30 55:monitorexit
                // 31 56:aload 4
                // 32 58:athrow
                destination.write( c );
                // 33 59:aload_0
                // 34 60:getfield #17 <Field java.io.Writer
                // cx.ath.choisnet.io.WriterCopyThread.destination>
                // 35 63:iload_2
                // 36 64:invokevirtual #23 <Method void
                // java.io.Writer.write(int)>
            }
            // * 37 67:goto 2
            catch( java.io.IOException e )
            // * 38 70:astore_3
            {
                if( errorCount++ > ERROR_MAX )
                // * 39 71:iload_1
                // * 40 72:iinc 1 1
                // * 41 75:bipush 10
                // * 42 77:icmple 111
                {
                    throw new RuntimeException(
                            "StreamCopyThread.run() - Internal error ("
                            + ERROR_MAX + " times) in "
                            + getName()
                            , e
                            );
                }
            }
            // if(true) goto _L2; else goto _L1
            // 57 111:goto 2
        } // for(;;
        // _L1:
        if( closeSource ) {
            try {
                source.close();
            }
            // * 64 128:goto 163
            catch( java.io.IOException e ) {
                throw new RuntimeException(
                        "StreamCopyThread.run() while closing source stream in "
                            + getName(),
                            e
                            );
            }
        }
    }

    public void registerSpyWriter(java.io.Writer spyWriter)
    {
        synchronized(lock) {
            this.spyWriter = spyWriter;
        }
    }

    public void stopSpyWriter()
    {
        synchronized(lock) {
            spyWriter = null;
        }
    }

    public void close()
        throws java.io.IOException
    {
        closeSource = true;
        cancel();
    }

    public void cancel()
        throws java.io.IOException
    {
        running = false;
    }
}
