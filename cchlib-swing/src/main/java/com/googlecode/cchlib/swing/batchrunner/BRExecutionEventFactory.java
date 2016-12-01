package com.googlecode.cchlib.swing.batchrunner;

import java.io.File;
import java.io.Serializable;

/**
 * {@link BRExecutionEvent} factory
 *
 * @since 4.1.8
 */
@FunctionalInterface
public interface BRExecutionEventFactory extends Serializable
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
