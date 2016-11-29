package com.googlecode.cchlib.swing.batchrunner.misc;

import java.awt.Image;
import java.io.Serializable;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRFrame;
import com.googlecode.cchlib.swing.batchrunner.impl.BRExecutionEventFactoryImpl;

/**
 * Text for localization of {@link BRFrame},
 * progress monitor ({@link BRExecutionEventFactoryImpl})
 *
 * @since 1.4.8
 */
@NeedDoc
public interface BRXLocaleResources extends Serializable
{
    /**
     * Return a String for progress monitor
     * @return a String for progress monitor
     */
    String getProgressMonitorMessage();

    /**
     * Returns values use for Frame title
     * @return Frame title
     */
    String getFrameTitle();

    /**
     * Returns values use for Frame icon
     * @return Frame icon
     */
    Image getFrameIconImage();
}
