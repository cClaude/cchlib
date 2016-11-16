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
 * NEEDDOC
 */
class JHexEditorASCII
    extends JComponent
        implements MouseListener,
                   KeyListener
{
    private static final long serialVersionUID = 1L;
    private final HexEditorModel model;

    public JHexEditorASCII(
        final HexEditorModel   hexEditorModel,
        final FocusListener    focusListener
        )
    {
        this.model = hexEditorModel;

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

        final Dimension d=new Dimension();
        //FontMetrics fn=getFontMetrics(getCustomFont());
        final FontMetrics fn = this.model.getFontMetrics();
        final int h=fn.getHeight();
        final int nl=this.model.getDisplayLinesCount();
        d.setSize(
            ((fn.stringWidth(" ")+1)*(16))+(this.model.getBorderWidth()*2)+1,
            (h*nl)+(this.model.getBorderWidth()*2)+1
            );
        return d;
    }

    @Override
    public void paint(final Graphics g)
    {
        //debug("paint("+g+")");
        //debug("cursor="+model.getCursorPos()+" buff.length="+model.getBuffer().getLength());
        final Dimension d=getMinimumSize();
        g.setColor(Color.white);
        g.fillRect(0,0,d.width,d.height);
        g.setColor(Color.black);

        g.setFont( this.model.getFont() );

        //datos ascii
        final int ini=this.model.getIntroduction()*16;
        int fin=ini+(this.model.getDisplayLinesCount()*16);
        if(fin>this.model.getBuffer().getLength()) {
            fin=this.model.getBuffer().getLength();
            }

        int x=0;
        int y=0;
        for(int n=ini;n<fin;n++)
        {
            if(n==this.model.getCursorPos()) {
                g.setColor(Color.blue);

                if( hasFocus() ) {
                    this.model.drawBackground(g,x,y,1); }
                else {
                    this.model.drawTable(g,x,y,1);
                    }
                if( hasFocus() ) {
                    g.setColor(Color.white);
                    }
                else {
                    g.setColor(Color.black);
                    }
                }
            else {
                g.setColor(Color.black);
                }

            String s = (new Character(this.model.getBuffer().getChar( n ))).toString();
            if((this.model.getBuffer().getByte( n )<20)||(this.model.getBuffer().getByte( n )>126)) {
                s = Character.toString( (char)16 );
                }
            this.model.printString(g,s,(x++),y);

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
        final FontMetrics fn = this.model.getFontMetrics();
        x=x/(fn.stringWidth(" ")+1);
        y=y/fn.getHeight();
        //debug("x="+x+" ,y="+y);
        return x+((y+this.model.getIntroduction())*16);
    }

    @Override// mouselistener
    public void mouseClicked(final MouseEvent e)
    {
        //debug("mouseClicked("+e+")");
        this.model.setCursorPos( calcularPosicionRaton(e.getX(),e.getY()) );
        this.requestFocus();
        this.model.repaintAll();
    }

    @Override// mouselistener
    public void mousePressed(final MouseEvent e)
    {
    }

    @Override// mouselistener
    public void mouseReleased(final MouseEvent e)
    {
    }

    @Override// mouselistener
    public void mouseEntered(final MouseEvent e)
    {
    }

    @Override// mouselistener
    public void mouseExited(final MouseEvent e)
    {
    }

    @Override//KeyListener
    public void keyTyped(final KeyEvent e)
    {
        final ArrayReadWriteAccess buf = this.model.getBufferRW();

        if( buf != null ) {
            //debug("keyTyped("+e+")");

            //he.getBuffer()[he.getCursorPos()]=(byte)e.getKeyChar();
            buf.setByte(
                    this.model.getCursorPos(),
                    (byte)e.getKeyChar()
                    );

            if(this.model.getCursorPos()!=(this.model.getBuffer().getLength()-1)) {
                this.model.incCursorPos();
                }
            this.model.repaintAll();
            }
    }

    @Override//KeyListener
    public void keyPressed(final KeyEvent e)
    {
        //debug("keyPressed("+e+")");
        this.model.keyPressed(e);
    }

    @Override//KeyListener
    public void keyReleased(final KeyEvent e)
    {
        //debug("keyReleased("+e+")");
    }

    @Override
    @Deprecated
    public boolean isFocusTraversable()
    {
        return true;
    }

    @Override
    public boolean isFocusable()
    {
        return true;
    }
}
