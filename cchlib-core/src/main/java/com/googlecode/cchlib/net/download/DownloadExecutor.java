package com.googlecode.cchlib.net.download;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Launch download tasks
 * <p>
 * {@link DownloadExecutor} use a {@link ThreadPoolExecutor} to
 * manage downloads.
 * </p>
 * @since 4.1.5
 * @see DownloadToFile
 * @see DownloadToString
 * @see DownloadEvent
 */
public class DownloadExecutor
{
    private final ThreadPoolExecutor pool;
    private final MD5FilterInputStreamBuilder downloadFilterBuilder;

    /**
     * Create DownloadExecutor
     *
     * @param downloadMaxThread Max number of parallel threads
     */
    public DownloadExecutor(
        final int                           downloadMaxThread,
        final MD5FilterInputStreamBuilder   downloadFilterBuilder
        )
    {
        final BlockingQueue<Runnable> queue  = new LinkedBlockingDeque<Runnable>();

        this.pool    = new ThreadPoolExecutor(
                0, // min thread
                downloadMaxThread, // max thread
                0, // keepAliveTime
                TimeUnit.MILLISECONDS,
                queue
                );
        pool.setCorePoolSize( downloadMaxThread );

        this.downloadFilterBuilder = downloadFilterBuilder;
    }

    /**
     * Executes the given task sometime in the future.
     * <p>
     * This method is mainly here to offer the possibility to retry a failed
     * download.
     * </p>
     *
     * @param command the task to execute
     * @throws RejectedExecutionException if task cannot be accepted for execution
     * @throws NullPointerException if command is null
     */
    public void execute( final Runnable command )
        throws RejectedExecutionException, NullPointerException
    {
        pool.execute( command );
    }

    /**
     * Attempts to stop all actively executing tasks
     *
     * @return list of tasks that never commenced execution
     * @see ThreadPoolExecutor#shutdownNow()
     */
    public List<Runnable> cancel()
    {
        return pool.shutdownNow();
    }

    /**
     * Add downloads based on a {@link Collection} of {@link URL}
     * <p>
     * Read general description for more details
     * </p>
     *
     * @param downloadURLs      {@link Collection} of {@link URL} to download.
     * @param eventHandler      A valid {@link DownloadEvent}.
     * @throws RejectedExecutionException if task cannot be accepted for execution
     * @see DownloadToString
     */
    public void add(
            final Collection<? extends DownloadURL> downloadURLs,
            final DownloadEvent                     eventHandler
            )
        throws RejectedExecutionException
    {
        for( DownloadURL u: downloadURLs ) {
            addDownload( u, eventHandler );
            }
    }

    /**
     * Add downloads based on an {@link Iterable} object of {@link URL}
     * <p>
     * Read general description for more details
     * </p>
     *
     * @param downloadURLs  {@link Iterable} of {@link URL} to download.
     * @param eventHandler   A valid {@link DownloadEvent}.
     * @throws RejectedExecutionException if task cannot be accepted for execution
     * @see DownloadToString
     */
    public void add(
            final Iterable<DownloadURL> downloadURLs,
            final DownloadEvent         eventHandler
            )
        throws RejectedExecutionException
    {
        for( DownloadURL u: downloadURLs ) {
            addDownload( u, eventHandler );
           }
    }

    /**
     * Add a download for giving {@link URL}
     * <p>
     * Read general description for more details
     * </p>
     *
     * @param downloadURL           A valid {@link DownloadURL}.
     * @param eventHandler          A valid {@link DownloadEvent}.
     * @throws RejectedExecutionException if task cannot be accepted for execution
     * @see DownloadToFile
     */
    public void addDownload(
            final DownloadURL       downloadURL,
            final DownloadEvent     eventHandler
            )
        throws RejectedExecutionException
    {
        Runnable command;

        switch( downloadURL.getType() ) {
            case STRING:
                command = new DownloadToString( downloadURL, eventHandler );
                break;

            default:
                command = new DownloadToFile( downloadURL, eventHandler, downloadFilterBuilder );
                break;
            }

        pool.execute( command );
    }

    /**
     * Blocks until all tasks have completed execution
     * <p>
     * DownloadExecutor is no more valid after this call. Any call to
     * add or execute methods will cause an {@link RejectedExecutionException}
     * </p>
     */
    public void waitClose()
    {
        // Wait pool finish
        do {
            // TODO: handle shutdown() use pool.awaitTermination( 1, TimeUnit.SECONDS );
            try { Thread.sleep( 2 * 1000 ); } catch( InterruptedException ignore ) {}
            } while( pool.getActiveCount() > 0 );

        pool.shutdown();
    }

    /**
     * Returns the approximate number of threads that are actively executing tasks.
     * @return the number of threads
     */
    public int getPollActiveCount()
    {
        return pool.getActiveCount();
    }

    /**
     * Returns the approximate number of tasks in queue.
     * @return the number of task to do.
     */
    public int getPoolQueueSize()
    {
        return pool.getQueue().size();
    }
}
