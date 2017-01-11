package com.googlecode.cchlib.i18n.sample.full;

import java.awt.EventQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import com.googlecode.cchlib.lang.Threads;

public class RunnableSupplier<R> implements Runnable
{
    private final Supplier<R>  supplier;;
    private R                  result;
    private ExecutionException exception;
    private boolean            done = false;

    public RunnableSupplier( final Supplier<R> supplier )
    {
        this.supplier = supplier;
    }

    @Override
    public void run()
    {
        try {
            this.result = this.supplier.get();
            this.done   = true;
        }
        catch( final Exception exception ) {
            this.exception = new ExecutionException( exception );
        }
    }

    public R getResult()
    {
        return this.result;
    }

    public ExecutionException getExecutionException()
    {
        return this.exception;
    }

    public boolean isDone()
    {
        return this.done;
    }

    public static <R > R invokeLater(
        final RunnableSupplier<R> runnableSupplier,
        final int                 delay,
        final TimeUnit            unit
        ) throws ExecutionException
    {
        EventQueue.invokeLater( runnableSupplier );

        for( int i = 1; (i < 10) && ! runnableSupplier.isDone(); i++ ) {
            Threads.sleep( delay, unit );
        }

        Threads.sleep( delay, unit );

        final ExecutionException executionException = runnableSupplier.getExecutionException();

        if( executionException == null ) {
            return runnableSupplier.getResult();
        }

        throw executionException;
    }
}

