package com.googlecode.cchlib.swing.batchrunner.impl;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.ProgressMonitorInputStream;

//not public
class CancellableBufferedInputStreamFactory
{
    private CancellableBufferedInputStreamFactory()
    {
        // All static
    }

    final static CancellableBufferedInputStream newCancellableBufferedInputStream( //
            final Component progressMonitorParentComponent, //
            final Object    progressMonitorMessage, //
            final File      file //
            ) throws FileNotFoundException
    {
        // Create progress monitor.
        final ProgressMonitorInputStream progressMonitorInputStream = new ProgressMonitorInputStream(
                progressMonitorParentComponent,
                progressMonitorMessage,
                new FileInputStream( file )
                );

        return new CancellableBufferedInputStream(progressMonitorInputStream, file);
   }

}
