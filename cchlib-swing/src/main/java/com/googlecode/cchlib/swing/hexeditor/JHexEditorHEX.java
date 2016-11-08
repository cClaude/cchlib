// $codepro.audit.disable
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
//public
class JHexEditorHEX
    extends JComponent
        implements MouseListener, KeyListener
{
    private static final long serialVersionUID = 1L;
    private final HexEditorModel model;
    private int cursor=0;

    public JHexEditorHEX(
        final HexEditorModel hexEditorModel,
        final FocusListener  focusListener
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
    public Dimension getMaximumSize()
    {
        //debug("getMaximumSize()");
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
            ((fn.stringWidth(" ")+1)*+((16*3)-1))+(this.model.getBorderWidth()*2)+1,
            (h*nl)+(this.model.getBorderWidth()*2)+1
            );
        return d;
    }

    @Override
    public void paint(final Graphics g)
    {
        //debug("paint("+g+")");
        //debug("cursor="+he.getCursorPos()+" buff.length="+he.getBuffer().getLength());
        final Dimension d=getMinimumSize();
        g.setColor(Color.white);
        g.fillRect(0,0,d.width,d.height);
        g.setColor(Color.black);

        g.setFont( this.model.getFont() );

        final int ini=this.model.getIntroduction()*16;
        int fin=ini+(this.model.getDisplayLinesCount()*16);
        if(fin>this.model.getBuffer().getLength()) {
            fin=this.model.getBuffer().getLength();
            }

        //datos hex
        int x=0;
        int y=0;
        for(int n=ini;n<fin;n++) {
            if(n==this.model.getCursorPos()) {
                if(hasFocus()) {
                    g.setColor(Color.black);
                    this.model.drawBackground(g,(x*3),y,2);
                    g.setColor(Color.blue);
                    this.model.drawBackground(g,(x*3)+this.cursor,y,1);
                    }
                else {
                    g.setColor(Color.blue);
                    this.model.drawTable(g,(x*3),y,2);
                    }

                if(hasFocus()) {
                    g.setColor(Color.white);
                    }
                else {
                    g.setColor(Color.black);
                    }
                }
            else {
                g.setColor(Color.black);
                }

            String s=("0"+Integer.toHexString(this.model.getBuffer().getByte( n ) ));
            s=s.substring(s.length()-2);
            this.model.printString(g,s,((x++)*3),y);
            if(x==16)
            {
                x=0;
                y++;
            }
        }
    }

//    private void debug(String s)
//    {
//        //if(he.DEBUG) System.out.println("JHexEditorHEX ==> "+s);
//    }

    // calcular la posicion del raton
    public int calcularPosicionRaton(int x,int y)
    {
        //FontMetrics fn=getFontMetrics(getCustomFont());
        final FontMetrics fn = this.model.getFontMetrics();
        x=x/((fn.stringWidth(" ")+1)*3);
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
        //debug("keyTyped("+e+")");

        final char c=e.getKeyChar();
        if(((c>='0')&&(c<='9'))||((c>='A')&&(c<='F'))||((c>='a')&&(c<='f')))
        {
            final char[] str=new char[2];
            String n="00"+Integer.toHexString(this.model.getBuffer().getByte( this.model.getCursorPos() ));

            if(n.length()>2) {
                n=n.substring(n.length()-2);
                }
            str[1-this.cursor]=n.charAt(1-this.cursor);
            str[this.cursor]=e.getKeyChar();
            //he.getBuffer()[he.getCursorPos()]=(byte)Integer.parseInt(new String(str),16);

            final ArrayReadWriteAccess buff = this.model.getBufferRW();

            if( buff != null ) {
                buff.setByte(
                        this.model.getCursorPos(),
                        (byte)Integer.parseInt(new String(str),16)
                        );
                }

            if(this.cursor!=1) {
                this.cursor=1;
                }
            else if(this.model.getCursorPos()!=(this.model.getBuffer().getLength()-1)) {
                this.model.incCursorPos();
                this.cursor=0;
                }
            this.model.updateCursor();
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
