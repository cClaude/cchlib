package com.googlecode.cchlib.swing.hexeditor;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.io.Closeable;
import javax.swing.JComponent;

/**
 * TODOC
 *
 */
public interface HexEditorModel extends Closeable
{
    /**
     * TODOC
     * @return TODOC
     */
    public int getIntroduction();

    /**
     * TODOC
     * @return TODOC
     */
    public int getCursorPos();

    /**
     *
     */
    public void incCursorPos();

    /**
     *
     * @param index
     */
    public void setCursorPos( int index );

    /**
     * TODOC
     * @return TODOC
     */
    public ArrayReadAccess getBuffer();

    /**
     * TODOC
     * @return TODOC
     */
    public ArrayReadWriteAccess getBufferRW();

    /**
     * TODOC
     * @return TODOC
     */
    public int getDisplayLinesCount();

    public Font getFont();

    public int getBorderWidth();

    public void drawTable(Graphics g, int i, int y, int j);

    public void printString(Graphics g, String s, int i, int j);

    public void drawBackground(Graphics g, int x, int y, int i);

    public void keyPressed(KeyEvent e);

    public void repaintAll();

    public FontMetrics getFontMetrics();

    public void adjustmentValueChanged(AdjustmentEvent event);

    public void mouseWheelMoved(MouseWheelEvent event);

    public void adjustingScrollBarValues(Rectangle rec);

    public void setRootComponent(JComponent rootComponent);

    public void updateCursor();
}
