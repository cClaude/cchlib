package com.googlecode.cchlib.swing.batchrunner.impl;

import java.awt.Component;
import java.io.File;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEvent;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEventFactory;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRFrame;
import com.googlecode.cchlib.swing.batchrunner.misc.BRXLocaleResources;

/**
 * Default implementation of {@link BRExecutionEventFactory} that create
 * {@link BRExecutionEventImpl} objects.
 *
 */
public class BRExecutionEventFactoryImpl implements BRExecutionEventFactory
{
    private static final long serialVersionUID = 1L;

    private final Component progressMonitorParentComponent;
    private final String    progressMonitorMessage;

    /**
     * NEEDDOC
     *
     * @param progressMonitorParentComponent
     *            Parent component for progress monitor, typically the {@link BRFrame}.
     * @param progressMonitorMessage
     *            NEEDDOC
     */
    @NeedDoc
    public BRExecutionEventFactoryImpl(
            final Component progressMonitorParentComponent,
            final String    progressMonitorMessage
            )
    {
        this.progressMonitorParentComponent = progressMonitorParentComponent;
        this.progressMonitorMessage         = progressMonitorMessage;
    }

    /**
     * NEEDDOC
     *
     * @param progressMonitorParentComponent
     *            Parent component for progress monitor, typically the {@link BRFrame}.
     * @param resources
     *            NEEDDOC
     */
    public BRExecutionEventFactoryImpl(
            final Component          progressMonitorParentComponent,
            final BRXLocaleResources resources
            )
    {
        this( progressMonitorParentComponent, resources.getProgressMonitorMessage() );
    }

    public BRExecutionEventFactoryImpl( final BRFrame aBRFrame )
    {
        this( aBRFrame, aBRFrame.getProgressMonitorMessage() );
    }

    @Override
    public BRExecutionEvent newSBRExecutionEvent( final File sourceFile, final File destinationFile )
    {
        return new BRExecutionEventImpl(
                sourceFile,
                destinationFile,
                this.progressMonitorParentComponent,
                this.progressMonitorMessage
                );
    }
}
