package com.googlecode.cchlib.swing.batchrunner.lazy;

import javax.swing.JPanel;
import com.googlecode.cchlib.swing.batchrunner.EnableListener;

/**
 * Handle custom panel.
 * <br/>
 * A custom panel is a panel with a content that is not handled by
 * {@link LazyBatchRunnerApp}. {@link LazyBatchRunnerApp} only handle
 * the panel it self.
 *
 * @since 1.4.7
 */
@Deprecated
public interface LazyBatchRunnerCustomJPanelFactory
{
    /**
     * According to {@link java.awt.BorderLayout} syntax
     * <br/>
     * Internal panel is positioning using {@link java.awt.BorderLayout#CENTER}.
     */
    public enum BorderLayoutConstraints
    {
        EAST,
        WEST,
        SOUTH,
        NORTH
    }

    /**
     * Returns a new custom {@link JPanel}.
     * @return a new custom {@link JPanel}, could return null if no
     *         custom panel is needed.
     */
    JPanel createCustomJPanel();

    /**
     * Returns a {@link BorderLayoutConstraints} according to desired
     * positioning.
     * @return a {@link BorderLayoutConstraints} according to desired
     *         positioning, could return null if no custom panel is
     *         needed.
     */
    BorderLayoutConstraints getCustomJPanelLayoutConstraints();

    /**
     * Returns a valid EnableListener for custom panel
     * @return a valid EnableListener for custom panel
     */
    EnableListener getEnableListener();
}
