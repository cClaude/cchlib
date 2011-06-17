package com.googlecode.cchlib.net.download;

import java.net.URL;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Launch download tasks
 * <p>
 * TODO: General description !!!
 * </p>
 * @since 4.1.5
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
     * @throws RejectedExecutionException at discretion of RejectedExecutionHandler, if task cannot be accepted for execution
     * @throws NullPointerException - if command is null
     */
    public void execute( final Runnable command )
        throws RejectedExecutionException, NullPointerException
    {
        pool.execute( command );
    }

    /**
     * Add downloads based on a {@link Collection} of {@link URL}
     * <p>
     * Read general description for more details
     * </p>
     *
     * @param eventHandler  Event handler for these downloads
     * @param URLCollection {@link Collection} of {@link URL} to download.
     * @throws RejectedExecutionException at discretion of RejectedExecutionHandler, if task cannot be accepted for execution
     */
    public void add(
            DownloadStringEvent eventHandler,
            Collection<URL>     URLCollection
            )
        throws RejectedExecutionException
    {
        for( URL u: URLCollection ) {
            addDownload( eventHandler, u );
            }
    }

    /**
     * Add a download for giving {@link URL}
     * <p>
     * Read general description for more details
     * </p>
     *
     * @param eventHandler  Event handler for this download
     * @param url {@link URL} to download
     * @throws RejectedExecutionException at discretion of RejectedExecutionHandler, if task cannot be accepted for execution
     */
    public void addDownload(
            DownloadStringEvent eventHandler,
            URL                 url
            )
        throws RejectedExecutionException
    {
        Runnable command = new DownloadToString( eventHandler, url );
        pool.execute( command );
    }

    /**
     * Add downloads based on a {@link Collection} of {@link URL}
     * <p>
     * Read general description for more details
     * </p>
     *
     * @param eventHandler  Event handler for these downloads
     * @param URLCollection {@link Collection} of {@link URL} to download.
     * @throws RejectedExecutionException at discretion of RejectedExecutionHandler, if task cannot be accepted for execution
     */
    public void addDownload(
            DownloadFileEvent   eventHandler,
            Collection<URL>     URLCollection
            )
        throws RejectedExecutionException
    {
        for( URL u: URLCollection ) {
            addDownload( eventHandler, u );
            }
    }

    /**
     * Add a download for giving {@link URL}
     * <p>
     * Read general description for more details
     * </p>
     *
     * @param eventHandler  Event handler for this download
     * @param url {@link URL} to download
     * @throws RejectedExecutionException at discretion of RejectedExecutionHandler, if task cannot be accepted for execution
     */
    public void addDownload(
            DownloadFileEvent   eventHandler,
            URL                 url
            )
        throws RejectedExecutionException
    {
        Runnable command = new DownloadToFile( eventHandler, url );
        pool.execute( command );
    }

    /**
     * Wait process completed
     * <p>
     * DownloadExecutor is no more valid after this call
     * </p>
     */
    public void waitCompleted()
    {
        // Wait pool finish
        do {
            try { Thread.sleep( 2 * 1000 ); } catch( InterruptedException ignore ) {}
            } while( pool.getActiveCount() > 0 );

        pool.shutdown();
    }

}