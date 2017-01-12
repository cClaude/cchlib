package com.googlecode.cchlib.swing;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.InvocationEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.swing.SwingUtilities;
import com.googlecode.cchlib.lang.Threads;

/**
 * Methods of {@link RunnableSupplierHelper} causes <i>doRun.run()</i> to
 * be executed asynchronously on the AWT event dispatching thread as required
 * by AWT and Swing. This will happen after all pending AWT events have been
 * processed. These methods should be used when an application thread needs
 * to update the GUI and excepted a result.
 */
public class RunnableSupplierHelper
{
    private static class RunnableSupplierImpl<R> implements RunnableSupplier<R>
    {
        private final Supplier<R>  supplier;
        private R                  result;
        private ExecutionException exception;
        private boolean            done = false;

        RunnableSupplierImpl( final Supplier<R> supplier )
        {
            this.supplier = supplier;
        }

        @Override
        public void run()
        {
            try {
                this.result = this.supplier.get();
            }
            catch( final Exception cause ) {
                this.exception = new ExecutionException( cause );
            } finally {
                this.done = true;
            }
        }

        @Override
        public R getResult()
        {
            return this.result;
        }

        @Override
        public ExecutionException getExecutionException()
        {
            return this.exception;
        }

        @Override
        public boolean isDone()
        {
            return this.done;
        }
    }

    private RunnableSupplierHelper()
    {
        // All static
    }

    /**
     * See global description.
     * <ul>
     * <li>Unlike {@link SwingUtilities#invokeAndWait(Runnable)} or
     * {@link SwingUtilities#invokeLater(Runnable)} this method
     * return a result.</li>
     * <li>Unlike {@link SwingUtilities#invokeLater(Runnable)} this method wait
     * for the result. When invoked this method will check every (
     * {@code loopTimeoutDelay} , {@code loopTimeoutUnit} ) if result if
     * available.</li>
     * <li>This method will always return after a delay of (
     * {@code loopCountTimeout} * {@code loopTimeoutDelay} ) unit
     * {@code loopTimeoutUnit}</li>
     * <li>This implementation is based on
     * {@link EventQueue#invokeLater(Runnable)}</li>
     * </ul>
     *
     * @param <R>
     *            Type the result.
     * @param runnableSupplier
     *            The {@link Runnable} and {@link Supplier} object
     * @param loopCountTimeout
     *            Maximum number of check before returning result
     * @param loopTimeoutDelay
     *            The minimum delay between result checks
     * @param loopTimeoutUnit
     *            The minimum delay unit.
     * @return The run result.
     * @throws ExecutionException
     *             if any error occur during execution.
     * @see #newRunnableSupplier(Supplier)
     * @see SwingUtilities#invokeLater(Runnable)
     * @see EventQueue#invokeLater(Runnable)
     * @see SwingUtilities#invokeAndWait(Runnable)
     * @see EventQueue#invokeAndWait(Runnable)
     */
    public static <R> R safeInvokeAndWait(
        @Nonnull final RunnableSupplier<R> runnableSupplier,
        @Nonnegative final int             loopCountTimeout,
        @Nonnegative final int             loopTimeoutDelay,
        @Nonnull final TimeUnit            loopTimeoutUnit
        ) throws ExecutionException
    {
        EventQueue.invokeLater( runnableSupplier );

        for( int i = 1; (i < loopCountTimeout) && ! runnableSupplier.isDone(); i++ ) {
            Threads.sleep( loopTimeoutDelay, loopTimeoutUnit );
        }

        final ExecutionException executionException = runnableSupplier.getExecutionException();

        if( executionException == null ) {
            return runnableSupplier.getResult();
        }

        throw executionException;
    }

    /**
     * See global description.
     * <ul>
     * <li>Unlike {@link SwingUtilities#invokeAndWait(Runnable)} or
     * {@link SwingUtilities#invokeLater(Runnable)} this method
     * return a result.</li>
     * <li>This implementation is based on
     * {@link EventQueue#invokeAndWait(Runnable)}</li>
     * </ul>
     *
     * @param <R>
     *            Type the result.
     * @param runnableSupplier
     *            The {@link Runnable} and {@link Supplier} object
     * @return The run result.
     * @throws ExecutionException
     *             if any error occur during execution.
     * @see #newRunnableSupplier(Supplier)
     * @see SwingUtilities#invokeAndWait(Runnable)
     * @see EventQueue#invokeAndWait(Runnable)
     */
   public static <R> R safeInvokeAndWait(
        @Nonnull final RunnableSupplier<R> runnableSupplier
        ) throws ExecutionException
    {
        try {
            EventQueue.invokeAndWait( runnableSupplier );
        }
        catch( InvocationTargetException | InterruptedException cause ) {
            throw new ExecutionException( cause );
        }

        final ExecutionException executionException = runnableSupplier.getExecutionException();

        if( executionException == null ) {
            return runnableSupplier.getResult();
        }

        throw executionException;
    }

    /**
     * See global description.
     * <ul>
     * <li>Unlike {@link SwingUtilities#invokeAndWait(Runnable)} or
     * {@link SwingUtilities#invokeLater(Runnable)} this
     * method return a result.</li>
     * <li>This implementation call directly the system event queue
     * to post the task to execute</li>
     * </ul>
     *
     * @param <R>
     *            Type the result.
     * @param runnableSupplier
     *            The {@link Runnable} and {@link Supplier} object
     * @return The run result.
     * @throws InterruptedException
     *             if any thread has interrupted this thread
     * @throws InvocationTargetException
     *             if an throwable is thrown when running runnable
     * @throws ExecutionException
     *             if any handled error occur during execution.
     */
   @SuppressWarnings({
       "squid:S1160", // More than one exception (from AWT)
       "squid:S00112" // Generic exceptions (from AWT)
       })
   public static <R> R invokeAndWait( final RunnableSupplier<R> runnableSupplier )
       throws InterruptedException, InvocationTargetException, ExecutionException
    {
        if( EventQueue.isDispatchThread() ) {
            throw new Error( "Cannot call invokeAndWait from the event dispatcher thread" );
        }
        final Object source = Toolkit.getDefaultToolkit();

        @SuppressWarnings("squid:S2094") // from AWT
        class AWTInvocationLock {
            // empty
        }
        final Object lock = new AWTInvocationLock();

        final InvocationEvent event = new InvocationEvent( source, runnableSupplier, lock, true );

        synchronized( lock ) {
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent( event );
            while( !event.isDispatched() ) {
                lock.wait();
            }
        }

        final Throwable eventThrowable = event.getThrowable();

        if( eventThrowable != null ) {
            throw new InvocationTargetException( eventThrowable );
        }

        final ExecutionException executionException = runnableSupplier.getExecutionException();

        if( executionException != null ) {
            throw executionException;
        }

        return runnableSupplier.getResult();
    }

    /**
     * Simple factory for {@link RunnableSupplier}
     *
     * @param <R>
     *            Type the result.
     * @param supplier
     *            The {@link Supplier} to build runnable object.
     * @return The {@link RunnableSupplier}
     */
    public static <R> RunnableSupplier<R> newRunnableSupplier( final Supplier<R> supplier  )
    {
        return new RunnableSupplierImpl<>( supplier );
    }
}
