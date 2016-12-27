package samples.threads.timeout;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ThreadTimeOut
{
    static class AnyException extends Exception
    {
        private static final long serialVersionUID = 1L;
    }

    static class CustomTask implements Callable<String>
    {
        @Override
        public String call() throws AnyException, InterruptedException
        {
            Thread.sleep( 4_000 ); // Just to demo a long running task of 4 seconds.
            return "Ready!";
        }
    }

    private ThreadTimeOut()
    {
        // App
    }

    @SuppressWarnings({
        "squid:S106","squid:S106", // CLI App
        "squid:S1166" // Hide TimeoutException
        })
    public static void main(final String[] args) throws Exception
    {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<String>  future   = executor.submit( new CustomTask() );

        try {
            System.out.println("Started..");
            System.out.println( future.get( 3, TimeUnit.SECONDS ) );
            System.out.println("Finished!");
        } catch (final TimeoutException e) {
            System.out.println("Terminated!");
        }

        executor.shutdownNow();
    }
}

