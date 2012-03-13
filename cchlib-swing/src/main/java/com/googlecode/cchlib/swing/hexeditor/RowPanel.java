package com.googlecode.cchlib.swing.hexeditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 *
 *
 */
class RowPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private HexEditorLayout layout;
    private HexEditorData hexEditor;

    public RowPanel(
        HexEditorLayout layout,
        HexEditorData hexEditor
        )
    {
        this.layout = layout;
        this.hexEditor = hexEditor;
        this.setLayout(new BorderLayout(1,1));
    }

    public Dimension getPreferredSize()
    {
        return getMinimumSize();
    }

    public Dimension getMinimumSize()
    {
        Dimension d=new Dimension();
        FontMetrics fn=getFontMetrics(this.layout.getCustomFont());
        int h=fn.getHeight();
        int nl=this.hexEditor.getLines();
        d.setSize(
            (fn.stringWidth(" ")+1)*(8)+(this.layout.getBorderWidth()*2)+1,
            h*nl+(this.layout.getBorderWidth()*2)+1
            );
        return d;
    }

    public void paint(Graphics g)
    {
        Dimension d=getMinimumSize();
        g.setColor(Color.white);
        g.fillRect(0,0,d.width,d.height);
        g.setColor(Color.black);
        g.setFont(this.layout.getCustomFont());

        int ini=this.hexEditor.getInicio();
        final int fin=ini + this.hexEditor.getLines();
        int y=0;

        for(int n=ini;n<fin;n++) {
            if(n==(this.hexEditor.getCursorPos()/16)) {
                this.layout.drawTable(g,0,y,8);
                }
            String s="0000000000000"+Integer.toHexString(n);
            s=s.substring(s.length()-8);
            this.layout.printString(g,s,0,y++);
        }
    }
}
