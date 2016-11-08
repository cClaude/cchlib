package com.googlecode.cchlib.swing.batchrunner;

import java.io.File;

/**
 * {@link BRExecutionEvent} factory
 *
 * @since 4.1.8
 */
public interface BRExecutionEventFactory
{
    /**
     * Create a {@link BRExecutionEvent} for <code>sourceFile</code>
     * and for <code>destinationFile</code>.
     *
     * @param sourceFile      Current source file
     * @param destinationFile Current destination file according to
     *                        {@link BRRunnable#buildOutputFile(File)}
     *
     * @return a {@link BRExecutionEvent}
     * @see BRRunnable#buildOutputFile(File)
     * @see BRRunnable#execute(BRExecutionEvent)
     */
    BRExecutionEvent newSBRExecutionEvent( File sourceFile, File destinationFile );
}
