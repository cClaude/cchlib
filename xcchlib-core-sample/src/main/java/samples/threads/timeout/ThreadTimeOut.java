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
            Thread.sleep(4000); // Just to demo a long running task of 4 seconds.
            return "Ready!";
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new CustomTask());

        try {
            System.out.println("Started..");
            System.out.println(future.get(3, TimeUnit.SECONDS));
            System.out.println("Finished!");
        } catch (TimeoutException e) {
            System.out.println("Terminated!");
        }

        executor.shutdownNow();
    }
}

