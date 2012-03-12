package com.googlecode.cchlib.swing.hexeditor;

import java.awt.Font;
import java.awt.Graphics;


/**
 *
 *
 */
public interface HexEditor
{
    public Font getCustomFont();

    public int getLineas();

    public int getInicio();

    public int getBorderWidth();

    public void cuadro(Graphics g, int i, int y, int j);

    public void printString(Graphics g, String s, int i, int j);

    public int getCursorPos();
    public void incCursorPos();
    public void setCursorPos( int index );

    //public byte[] getBuffer();
    public ArrayReadAccess getBuffer();
}
