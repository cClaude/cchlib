package com.googlecode.cchlib.swing.hexeditor;

/**
 * TODOC
 *
 */
public interface HexEditorData
{
    /**
     * TODOC
     * @return TODOC
     */
    public int getLines();

    /**
     *
     * @return
     */
    public int getInicio();

    /**
     *
     * @return
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
     *
     * @return
     */
    public ArrayReadAccess getBuffer();
}
