// $codepro.audit.disable concatenationInAppend
package samples.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.googlecode.cchlib.lang.Threads;

/**
 * Test only
 */
public class MyThreadPool2
{
    private final int poolSize = 2;
    private final int maxPoolSize = 5;
    private final long keepAliveTime = 10;
    private ThreadPoolExecutor threadPool = null;
    //private static FutureTask<?> futureTask = null;
    private final ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(5);

    public MyThreadPool2()
    {
        System.out.println( "Starting pool..." );

        this.threadPool = new ThreadPoolExecutor(this.poolSize, this.maxPoolSize, this.keepAliveTime, TimeUnit.SECONDS, this.queue);
    }

    public void runTask(final Runnable task)
    {
        /*futureTask = (FutureTask<?>)*/ this.threadPool.submit( task );
        System.out.println( "Task count: " + this.queue.size() );
    }

    public void shutDown()
    {
        this.threadPool.shutdownNow();
        System.out.println("Shutting down pool...");
    }

    public static void main(final String[] args)
    {
        final MyThreadPool2 mtp = new MyThreadPool2();
        mtp.runTask( new MyTask2( "abracadabra" ) );

        // Wait 2 sec and stop
        Threads.sleep( 2, TimeUnit.SECONDS );
        mtp.shutDown();
    }

//    private static void callCancel()
//    {
//        futureTask.cancel(true);
//        System.out.println("Cancelling...");
//    }
}

class MyTask2 implements Runnable
{
    final String orgString;

    public MyTask2( final String str )
    {
        this.orgString = str;
    }

    private static String reverseString( final String str )
    {
        final StringBuilder reversedString = new StringBuilder();

        for (int i = (str.length() - 1); i >= 0; i--) {
            reversedString.append( str.charAt( i ) );
            System.out.println( "Reversing one character per second."
                    + reversedString );

            if( Threads.sleepAndNotify( 1, TimeUnit.SECONDS ) ) {
                break; // unfinished task !!
                }
            }

        return reversedString.toString();
    }

    @Override
    public void run()
    {
        System.out.println( "Running... reverse: " + this.orgString );

        reverseString( this.orgString );
        System.out.println( "Stop running" );
   }
}
