package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.EventListener;

/**
 * The listener interface for receiving start computing digest events.
 */
public interface DuplicateFileFinderEventListener
    extends EventListener, Serializable
{
    /**
     * Invoked before computing digest for this file
     *
     * @param file the file
     */
    void analysisStart( File file );

    /**
     * Invoked during computing digest for this file,
     * give current length done for this file.
     *
     * @param file the file
     * @param length the length actually computed
     */
    void analysisStatus( File file, long length );

    /**
     * Invoked after computing digest for this file
     *
     * @param file the file
     * @param hashString
     *          the Hash for file or null if file
     *          as been ignore
     */
    void analysisDone( File file, String hashString );

    /**
     * Invoked if any {@link IOException} occur. File will
     * be ignore.
     *
     * @param file  current file.
     * @param ioe   exception that append.
     */
    void ioError( File file, IOException ioe );

    /**
     * Invoked to check if process should be cancel.
     *
     * @return true if process should be stop.
     */
    boolean isCancel();
}
