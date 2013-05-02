package samples.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Test only
 */
public class MyThreadPool2
{
    private int poolSize = 2;
    private int maxPoolSize = 5;
    private long keepAliveTime = 10;
    private ThreadPoolExecutor threadPool = null;
    //private static FutureTask<?> futureTask = null;
    private final ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(5);

    public MyThreadPool2()
    {
        System.out.println( "Starting pool..." );

        threadPool = new ThreadPoolExecutor(poolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, queue);
    }

    public void runTask(Runnable task)
    {
        /*futureTask = (FutureTask<?>)*/ threadPool.submit( task );
        System.out.println( "Task count: " + queue.size() );
    }

    public void shutDown()
    {
        threadPool.shutdownNow();
        System.out.println("Shutting down pool...");
    }

    public static void main(String[] args)
    {
        MyThreadPool2 mtp = new MyThreadPool2();
        mtp.runTask( new MyTask2( "abracadabra" ) );

        // Wait 2 sec and stop
        try { Thread.sleep(2000); } catch( InterruptedException e ) { } // $codepro.audit.disable emptyCatchClause
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
        orgString = str;
    }

    private static String reverseString( final String str )
    {
        StringBuilder reversedString = new StringBuilder();

        for (int i = (str.length() - 1); i >= 0; i--) {
            reversedString.append( str.charAt( i ) );
            System.out.println( "Reversing one character per second."
                    + reversedString );
            try {
                Thread.sleep(1000);
                }
            catch( InterruptedException e ) {
                e.printStackTrace();
                break; // unfinished task !!
                }
            }

        return reversedString.toString();
    }

    @Override
    public void run()
    {
        System.out.println( "Running... reverse: " + orgString );

        reverseString( orgString );
        System.out.println( "Stop running" );
   }
}
