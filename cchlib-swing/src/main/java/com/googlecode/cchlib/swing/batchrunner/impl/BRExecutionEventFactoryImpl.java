package com.googlecode.cchlib.swing.batchrunner.impl;

import java.awt.Component;
import java.io.File;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEvent;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEventFactory;

/**
 * Default implementation of {@link BRExecutionEventFactory} that create
 * {@link BRExecutionEventImpl} objects.
 *
 */
public class BRExecutionEventFactoryImpl implements BRExecutionEventFactory
{
    private Component progressMonitorParentComponent;
    private String progressMonitorMessage;

    /**
     * TODOC
     * 
     * @param progressMonitorParentComponent
     * @param progressMonitorMessage
     */
    @NeedDoc
    public BRExecutionEventFactoryImpl( Component progressMonitorParentComponent, String progressMonitorMessage )
    {
        this.progressMonitorParentComponent = progressMonitorParentComponent;
        this.progressMonitorMessage         = progressMonitorMessage;
    }

    @Override
    public BRExecutionEvent newSBRExecutionEvent( final File sourceFile, final File destinationFile )
    {
        return new BRExecutionEventImpl( sourceFile, destinationFile, progressMonitorParentComponent, progressMonitorMessage );
    }
}
