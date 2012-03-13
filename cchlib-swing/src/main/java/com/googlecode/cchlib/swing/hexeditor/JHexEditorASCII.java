package com.googlecode.cchlib.swing.hexeditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 */
public class JHexEditorASCII
    extends JComponent
        implements MouseListener,
                   KeyListener
{
    private static final long serialVersionUID = 1L;
    private JHexEditor he;

    public JHexEditorASCII(JHexEditor he)
    {
        this.he=he;
        addMouseListener(this);
        addKeyListener(this);
        addFocusListener(he);
    }

    private Font getCustomFont()
    {
        return he.getCustomFont();
    }

    @Override
    public Dimension getPreferredSize()
    {
        debug("getPreferredSize()");
        return getMinimumSize();
    }

    @Override
    public Dimension getMinimumSize()
    {
        debug("getMinimumSize()");

        Dimension d=new Dimension();
        FontMetrics fn=getFontMetrics(getCustomFont());
        int h=fn.getHeight();
        int nl=he.getLines();
        d.setSize(
            (fn.stringWidth(" ")+1)*(16)+(he.getBorderWidth()*2)+1,
            h*nl+(he.getBorderWidth()*2)+1
            );
        return d;
    }

    @Override
    public void paint(Graphics g)
    {
        debug("paint("+g+")");
        debug("cursor="+he.getCursorPos()+" buff.length="+he.getBuffer().getLength());
        Dimension d=getMinimumSize();
        g.setColor(Color.white);
        g.fillRect(0,0,d.width,d.height);
        g.setColor(Color.black);

        g.setFont(getCustomFont());

        //datos ascii
        int ini=he.getInicio()*16;
        int fin=ini+(he.getLines()*16);
        if(fin>he.getBuffer().getLength()) {
            fin=he.getBuffer().getLength();
            }

        int x=0;
        int y=0;
        for(int n=ini;n<fin;n++)
        {
            if(n==he.getCursorPos())
            {
                g.setColor(Color.blue);
                if(hasFocus()) he.fondo(g,x,y,1); else he.drawTable(g,x,y,1);
                if(hasFocus()) g.setColor(Color.white); else g.setColor(Color.black);
            } else
            {
                g.setColor(Color.black);
            }

            String s=""+new Character(he.getBuffer().getChar( n ));
            if((he.getBuffer().getByte( n )<20)||(he.getBuffer().getByte( n )>126)) {
                s=""+(char)16;
                }
            he.printString(g,s,(x++),y);

            if( x==16 ) {
                x=0;
                y++;
                }
            }
    }

    private void debug(String s)
    {
        //if(he.DEBUG) System.out.println("JHexEditorASCII ==> "+s);
    }

    // calcular la posicion del raton
    public int calcularPosicionRaton(int x,int y)
    {
        FontMetrics fn=getFontMetrics(getCustomFont());
        x=x/(fn.stringWidth(" ")+1);
        y=y/fn.getHeight();
        debug("x="+x+" ,y="+y);
        return x+((y+he.getInicio())*16);
    }

    @Override// mouselistener
    public void mouseClicked(MouseEvent e)
    {
        debug("mouseClicked("+e+")");
        he.setCursorPos( calcularPosicionRaton(e.getX(),e.getY()) );
        this.requestFocus();
        he.repaint();
    }

    @Override// mouselistener
    public void mousePressed(MouseEvent e)
    {
    }

    @Override// mouselistener
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override// mouselistener
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override// mouselistener
    public void mouseExited(MouseEvent e)
    {
    }

    @Override//KeyListener
    public void keyTyped(KeyEvent e)
    {
        debug("keyTyped("+e+")");

        //he.getBuffer()[he.getCursorPos()]=(byte)e.getKeyChar();
        he.getBuffer().setByte(
            he.getCursorPos(),
            (byte)e.getKeyChar()
            );

        if(he.getCursorPos()!=(he.getBuffer().getLength()-1)) {
            he.incCursorPos();
            }
        he.repaint();
    }

    @Override//KeyListener
    public void keyPressed(KeyEvent e)
    {
        debug("keyPressed("+e+")");
        he.keyPressed(e);
    }

    @Override//KeyListener
    public void keyReleased(KeyEvent e)
    {
        debug("keyReleased("+e+")");
    }

    @Override
    public boolean isFocusTraversable()
    {
        return true;
    }
}
