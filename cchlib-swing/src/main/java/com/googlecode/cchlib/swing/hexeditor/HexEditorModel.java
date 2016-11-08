package com.googlecode.cchlib.swing.hexeditor;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import javax.swing.JComponent;

/**
 * TODOC
 *
 */
public interface HexEditorModel //extends Closeable
{
    /**
     * TODOC
     * @return TODOC
     */
    int getIntroduction();

    /**
     * TODOC
     * @return TODOC
     */
    int getCursorPos();

    /**
     *
     */
    void incCursorPos();

    /**
     *
     * @param index
     */
    void setCursorPos( int index );

    /**
     * TODOC
     * @return TODOC
     */
    ArrayReadAccess getBuffer();

    /**
     * TODOC
     * @return TODOC
     */
    ArrayReadWriteAccess getBufferRW();

    /**
     * TODOC
     * @return TODOC
     */
    int getDisplayLinesCount();

    Font getFont();

    int getBorderWidth();

    void drawTable(Graphics g, int i, int y, int j);

    void printString(Graphics g, String s, int i, int j);

    void drawBackground(Graphics g, int x, int y, int i);

    void keyPressed(KeyEvent e);

    void repaintAll();

    FontMetrics getFontMetrics();

    void adjustmentValueChanged(AdjustmentEvent event);

    void mouseWheelMoved(MouseWheelEvent event);

    void adjustingScrollBarValues(Rectangle rec);

    void setRootComponent(JComponent rootComponent);

    void updateCursor();

    void close() throws IOException;
}
