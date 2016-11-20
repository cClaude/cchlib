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
    private HexEditorModel model;

    public RowPanel(
        HexEditorModel hexEditorModel
        )
    {
        this.model = hexEditorModel;
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
        //FontMetrics fn=getFontMetrics(this.model.getCustomFont());
        FontMetrics fn = model.getFontMetrics();
        int h=fn.getHeight();
        int nl=this.model.getDisplayLinesCount();
        d.setSize(
            (fn.stringWidth(" ")+1)*(8)+(this.model.getBorderWidth()*2)+1,
            h*nl+(this.model.getBorderWidth()*2)+1
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
        g.setFont( this.model.getFont() );

        int ini=this.model.getIntroduction();
        final int fin=ini + this.model.getDisplayLinesCount();
        int y=0;

        for(int n=ini;n<fin;n++) {
            if(n==(this.model.getCursorPos()/16)) {
                this.model.drawTable(g,0,y,8);
                }
            String s="0000000000000"+Integer.toHexString(n);
            s=s.substring(s.length()-8);
            this.model.printString(g,s,0,y++);
        }
    }
}
