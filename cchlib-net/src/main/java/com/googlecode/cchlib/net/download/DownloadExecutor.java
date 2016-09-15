package com.googlecode.cchlib.net.download;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.googlecode.cchlib.net.download.fis.DownloadFilterInputStreamBuilder;

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
    private static final int TWO_SECONDS = 2 * 1000;
    private final ThreadPoolExecutor pool;
    //private final MD5FilterInputStreamBuilder downloadFilterBuilder;
    private final DownloadFilterInputStreamBuilder downloadFilterBuilder;

    /**
     * Create DownloadExecutor
     *
     * @param downloadMaxThread Max number of parallel threads
     */
    public DownloadExecutor(
        final int                               downloadMaxThread,
        final DownloadFilterInputStreamBuilder  downloadFilterBuilder
        )
    {
        final BlockingQueue<Runnable> queue  = new LinkedBlockingDeque<>();

        this.pool    = new ThreadPoolExecutor(
                0, // min thread
                downloadMaxThread, // max thread
                0, // keepAliveTime
                TimeUnit.MILLISECONDS,
                queue
                );
        this.pool.setCorePoolSize( downloadMaxThread );

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
        this.pool.execute( command );
    }

    /**
     * Attempts to stop all actively executing tasks
     *
     * @return list of tasks that never commenced execution
     * @see ThreadPoolExecutor#shutdownNow()
     */
    public List<Runnable> cancel()
    {
        return this.pool.shutdownNow();
    }

    /**
     * Add downloads based on a {@link Collection} of {@link URL}
     * <p>
     * Read general description for more details
     * </p>
     *
     * @param downloadURLs      {@link Collection} of {@link URL} to download.
     * @param eventHandler  A valid {@link DownloadEvent} according downloadURLs type
     *
     * @throws RejectedExecutionException if task cannot be accepted for execution
     * @throws DownloadConfigurationException if downloadURLs or eventHandler are not a valid subtype
     * @see DownloadToFile
     * @see DownloadToString
     * @see DownloadFileURL
     * @see DownloadStringURL
     */
    public void add(
            final Collection<? extends DownloadURL> downloadURLs,
            final DownloadEvent                     eventHandler
            )
        throws  RejectedExecutionException,
                DownloadConfigurationException
    {
        for( final DownloadURL u: downloadURLs ) {
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
     * @param eventHandler  A valid {@link DownloadEvent} according to downloadURLs type
     *
     * @throws RejectedExecutionException if task cannot be accepted for execution
     * @throws DownloadConfigurationException if downloadURLs or eventHandler are not a valid subtype
     * @see DownloadToFile
     * @see DownloadToString
     * @see DownloadFileURL
     * @see DownloadStringURL
     */
    public void add(
            final Iterable<DownloadURL> downloadURLs,
            final DownloadEvent         eventHandler
            )
        throws  RejectedExecutionException,
                DownloadConfigurationException
    {
        for( final DownloadURL u: downloadURLs ) {
            addDownload( u, eventHandler );
           }
    }

    /**
     * Add a download for giving {@link URL}
     * <p>
     * Read general description for more details
     * </p>
     *
     * @param downloadURL   A valid {@link DownloadURL}.
     * @param eventHandler  A valid {@link DownloadEvent} according to downloadURL type
     *
     * @throws RejectedExecutionException if task cannot be accepted for execution
     * @throws DownloadConfigurationException if downloadURLs or eventHandler are not a valid subtype
     * @see DownloadToFile
     * @see DownloadToString
     * @see DownloadFileURL
     * @see DownloadStringURL
     */
    public void addDownload(
            final DownloadURL   downloadURL,
            final DownloadEvent eventHandler
            )
        throws  RejectedExecutionException,
                DownloadConfigurationException // $codepro.audit.disable unnecessaryExceptions
    {
        Runnable command;

        if( downloadURL instanceof DownloadStringURL ) {
            final DownloadStringURL downloadStringURL = DownloadStringURL.class.cast( downloadURL );

            command = new DownloadToString( downloadStringURL, eventHandler );
            }
        else if( downloadURL instanceof DownloadFileURL ) {
            final DownloadFileURL downloadFileURL = DownloadFileURL.class.cast( downloadURL );

            if( eventHandler instanceof DownloadFileEvent ) {
                final DownloadFileEvent fileEventHandler = DownloadFileEvent.class.cast( eventHandler );

                command = new DownloadToFile( downloadFileURL, fileEventHandler , this.downloadFilterBuilder );
                }
            else {
                throw new BadDownloadEventException();
                }
            }
        else {
            throw new BadDownloadURLException();
            }

        this.pool.execute( command );
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
            sleep( TWO_SECONDS );
            } while( this.pool.getActiveCount() > 0 );

        this.pool.shutdown();
    }

    private static void sleep( final int millisec )
    {
        try {
            Thread.sleep( millisec );
        }
        catch( final InterruptedException ignore ) {} // NOSONAR $codepro.audit.disable emptyCatchClause, logExceptions
    }

    /**
     * Returns the approximate number of threads that are actively executing tasks.
     * @return the number of threads
     */
    public int getPollActiveCount()
    {
        return this.pool.getActiveCount();
    }

    /**
     * Returns the approximate number of tasks in queue.
     * @return the number of task to do.
     */
    public int getPoolQueueSize()
    {
        return this.pool.getQueue().size();
    }
}
