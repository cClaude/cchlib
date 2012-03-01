package com.googlecode.cchlib.net.download;

import java.net.Proxy;
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
 * @see DownloadFileEvent
 * @see DownloadToString
 * @see DownloadStringEvent
 */
public class DownloadExecutor
{
    private final ThreadPoolExecutor pool;

    /**
     * Create DownloadExecutor
     *
     * @param downloadMaxThread Max number of parallel threads
     */
    public DownloadExecutor( final int downloadMaxThread )
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
     * @param eventHandler  Event handler for these downloads
     * @param proxy         {@link Proxy} to use for download (could be null)
     * @param URLCollection {@link Collection} of {@link URL} to download.
     * @throws RejectedExecutionException if task cannot be accepted for execution
     * @see DownloadToString
     */
    public void add(
            final DownloadStringEvent   eventHandler,
            final Proxy                 proxy,
            final Collection<URL>       URLCollection
            )
        throws RejectedExecutionException
    {
        for( URL u: URLCollection ) {
            addDownload( eventHandler, proxy, u );
            }
    }

    /**
     * Add downloads based on an {@link Iterable} object of {@link URL}
     * <p>
     * Read general description for more details
     * </p>
     *
     * @param eventHandler  Event handler for these downloads
     * @param proxy         {@link Proxy} to use for download (could be null)
     * @param URLs          {@link Iterable} of {@link URL} to download.
     * @throws RejectedExecutionException if task cannot be accepted for execution
     * @see DownloadToString
     */
    public void add(
            final DownloadStringEvent   eventHandler,
            final Proxy                 proxy,
            final Iterable<URL>         URLs
            )
        throws RejectedExecutionException
    {
        for( URL u: URLs ) {
            addDownload( eventHandler, proxy, u );
            }
    }

    /**
     * Add a download for giving {@link URL}
     * <p>
     * Read general description for more details
     * </p>
     *
     * @param eventHandler  Event handler for this download
     * @param proxy         {@link Proxy} to use for download (could be null)
     * @param url           {@link URL} to download
     * @throws RejectedExecutionException if task cannot be accepted for execution
     * @see DownloadToString
     */
    public void addDownload(
            final DownloadStringEvent eventHandler,
            final Proxy               proxy,
            final URL                 url
            )
        throws RejectedExecutionException
    {
        Runnable command = new DownloadToString( eventHandler, proxy, url );
        pool.execute( command );
    }

    /**
     * Add downloads based on a {@link Collection} of {@link URL}
     * <p>
     * Read general description for more details
     * </p>
     *
     * @param eventHandler  Event handler for these downloads
     * @param proxy         {@link Proxy} to use for download (could be null)
     * @param URLCollection {@link Collection} of {@link URL} to download.
     * @throws RejectedExecutionException if task cannot be accepted for execution
     * @see DownloadToFile
     */
    public void addDownload(
            final DownloadFileEvent eventHandler,
            final Proxy             proxy,
            final Collection<URL>   URLCollection
            )
        throws RejectedExecutionException
    {
        for( URL u: URLCollection ) {
            addDownload( eventHandler, proxy, u );
            }
    }

    /**
     * Add a download for giving {@link URL}
     * <p>
     * Read general description for more details
     * </p>
     *
     * @param eventHandler  Event handler for this download
     * @param proxy         {@link Proxy} to use for download (could be null)
     * @param url           {@link URL} to download
     * @throws RejectedExecutionException if task cannot be accepted for execution
     * @see DownloadToFile
     */
    public void addDownload(
            final DownloadFileEvent eventHandler,
            final Proxy             proxy,
            final URL               url
            )
        throws RejectedExecutionException
    {
        Runnable command = new DownloadToFile( eventHandler, proxy, url );
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

}
