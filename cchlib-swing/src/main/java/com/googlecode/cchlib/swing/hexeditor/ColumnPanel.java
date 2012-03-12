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
class ColumnPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private final HexEditor hexEditor;

    public ColumnPanel(HexEditor hexEditor)
    {
        this.hexEditor = hexEditor;
        this.setLayout(new BorderLayout(1,1));
    }

    @Override
    public Dimension getPreferredSize()
    {
        return getMinimumSize();
    }

    @Override
    public Dimension getMinimumSize()
    {
        Dimension d=new Dimension();
        FontMetrics fn=getFontMetrics(this.hexEditor.getCustomFont());
        int h=fn.getHeight();
        int nl=1;
        d.setSize(
            ((fn.stringWidth(" ")+1)*+((16*3)-1))+(this.hexEditor.getBorderWidth()*2)+1,
            h*nl+(this.hexEditor.getBorderWidth()*2)+1
            );
        return d;
    }

    @Override
    public void paint(Graphics g)
    {
        Dimension d=getMinimumSize();
        g.setColor(Color.white);
        g.fillRect(0,0,d.width,d.height);
        g.setColor(Color.black);
        g.setFont(this.hexEditor.getCustomFont());

        for(int n=0;n<16;n++)
        {
            if(n==(this.hexEditor.getCursorPos()%16)) this.hexEditor.cuadro(g,n*3,0,2);
            String s="00"+Integer.toHexString(n);
            s=s.substring(s.length()-2);
            this.hexEditor.printString(g,s,n*3,0);
        }
    }
}
