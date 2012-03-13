package com.googlecode.cchlib.swing.hexeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

/**
 * TODOC
 */
class JHexEditorASCII
    extends JComponent
        implements MouseListener,
                   KeyListener
{
    private static final long serialVersionUID = 1L;
    private HexEditorModel model;

    public JHexEditorASCII(
        HexEditorModel   hexEditorModel,
        FocusListener    focusListener
        )
    {
        this.model=hexEditorModel;
        addMouseListener(this);
        addKeyListener(this);
        addFocusListener( focusListener );
    }

    @Override
    public Dimension getPreferredSize()
    {
        //debug("getPreferredSize()");
        return getMinimumSize();
    }

    @Override
    public Dimension getMinimumSize()
    {
        //debug("getMinimumSize()");

        Dimension d=new Dimension();
        //FontMetrics fn=getFontMetrics(getCustomFont());
        FontMetrics fn = model.getFontMetrics();
        int h=fn.getHeight();
        int nl=model.getDisplayLinesCount();
        d.setSize(
            (fn.stringWidth(" ")+1)*(16)+(model.getBorderWidth()*2)+1,
            h*nl+(model.getBorderWidth()*2)+1
            );
        return d;
    }

    @Override
    public void paint(Graphics g)
    {
        //debug("paint("+g+")");
        //debug("cursor="+model.getCursorPos()+" buff.length="+model.getBuffer().getLength());
        Dimension d=getMinimumSize();
        g.setColor(Color.white);
        g.fillRect(0,0,d.width,d.height);
        g.setColor(Color.black);

        g.setFont( model.getFont() );

        //datos ascii
        int ini=model.getIntroduction()*16;
        int fin=ini+(model.getDisplayLinesCount()*16);
        if(fin>model.getBuffer().getLength()) {
            fin=model.getBuffer().getLength();
            }

        int x=0;
        int y=0;
        for(int n=ini;n<fin;n++)
        {
            if(n==model.getCursorPos()) {
                g.setColor(Color.blue);
                if( hasFocus() ) {
                    model.drawBackground(g,x,y,1); }
                else {
                    model.drawTable(g,x,y,1);
                    }
                if(hasFocus()) g.setColor(Color.white); else g.setColor(Color.black);
            } else
            {
                g.setColor(Color.black);
            }

            String s=""+new Character(model.getBuffer().getChar( n ));
            if((model.getBuffer().getByte( n )<20)||(model.getBuffer().getByte( n )>126)) {
                s=""+(char)16;
                }
            model.printString(g,s,(x++),y);

            if( x==16 ) {
                x=0;
                y++;
                }
            }
    }

//    private void debug(String s)
//    {
//        //if(he.DEBUG) System.out.println("JHexEditorASCII ==> "+s);
//    }

    // calcular la posicion del raton
    public int calcularPosicionRaton(int x,int y)
    {
        //FontMetrics fn=getFontMetrics(getCustomFont());
        FontMetrics fn = model.getFontMetrics();
        x=x/(fn.stringWidth(" ")+1);
        y=y/fn.getHeight();
        //debug("x="+x+" ,y="+y);
        return x+((y+model.getIntroduction())*16);
    }

    @Override// mouselistener
    public void mouseClicked(MouseEvent e)
    {
        //debug("mouseClicked("+e+")");
        model.setCursorPos( calcularPosicionRaton(e.getX(),e.getY()) );
        this.requestFocus();
        model.repaintAll();
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
        ArrayReadWriteAccess buf = model.getBufferRW();

        if( buf != null ) {
            //debug("keyTyped("+e+")");

            //he.getBuffer()[he.getCursorPos()]=(byte)e.getKeyChar();
            buf.setByte(
                    model.getCursorPos(),
                    (byte)e.getKeyChar()
                    );

            if(model.getCursorPos()!=(model.getBuffer().getLength()-1)) {
                model.incCursorPos();
                }
            model.repaintAll();
            }
    }

    @Override//KeyListener
    public void keyPressed(KeyEvent e)
    {
        //debug("keyPressed("+e+")");
        model.keyPressed(e);
    }

    @Override//KeyListener
    public void keyReleased(KeyEvent e)
    {
        //debug("keyReleased("+e+")");
    }

    @Override
    public boolean isFocusTraversable()
    {
        return true;
    }
}
