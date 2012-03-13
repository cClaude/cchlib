package com.googlecode.cchlib.swing.hexeditor;

import java.awt.Font;
import java.awt.Graphics;


/**
 *
 *
 */
public interface HexEditorLayout
{
    public Font getCustomFont();

    //public int getLineas();

    //public int getInicio();

    public int getBorderWidth();

    public void drawTable(Graphics g, int i, int y, int j);

    public void printString(Graphics g, String s, int i, int j);
}
