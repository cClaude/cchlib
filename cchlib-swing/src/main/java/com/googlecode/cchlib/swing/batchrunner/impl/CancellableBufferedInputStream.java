package com.googlecode.cchlib.swing.batchrunner.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import javax.swing.ProgressMonitor;
import javax.swing.ProgressMonitorInputStream;
import com.googlecode.cchlib.swing.batchrunner.BRUserCancelException;

/**
 * return a buffered InputStream but add exception on read() methods
 * to trap user cancel action.
 */
final class CancellableBufferedInputStream extends BufferedInputStream {
    private final File file;
    /** Current file progress monitor */
    private final ProgressMonitor progressMonitor;

    CancellableBufferedInputStream( //
            final ProgressMonitorInputStream    progressMonitorInputStream, //
            final File                          file //
            )
    {
        super( progressMonitorInputStream );

        this.progressMonitor = progressMonitorInputStream.getProgressMonitor();
        this.file            = file;
    }

    /** check progress monitor */
    private void checkCanceled() throws BRUserCancelException
    {
        if( progressMonitor.isCanceled() ) {
            throw new BRUserCancelException( file );
            }
    }

    @Override
    public synchronized int read() throws IOException
    {
        final int res = super.read();

        checkCanceled();

        return res;
    }

    @Override
    public synchronized int read( final byte[] b, final int off, final int len ) throws IOException
    {
        final int res = super.read( b, off, len );

        checkCanceled();

        return res;
    }
}
