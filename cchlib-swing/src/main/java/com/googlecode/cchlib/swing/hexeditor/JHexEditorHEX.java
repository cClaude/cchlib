package com.googlecode.cchlib.swing.hexeditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * TODOC
 */
//public
class JHexEditorHEX
    extends JComponent
        implements MouseListener, KeyListener
{
    private static final long serialVersionUID = 1L;
    private HexEditorModel model;
    private int cursor=0;

    public JHexEditorHEX(
        HexEditorModel hexEditorModel,
        FocusListener  focusListener
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

        Dimension d=new Dimension();
        //FontMetrics fn=getFontMetrics(getCustomFont());
        FontMetrics fn = model.getFontMetrics();
        int h=fn.getHeight();
        int nl=model.getDisplayLinesCount();
        d.setSize(
            ((fn.stringWidth(" ")+1)*+((16*3)-1))+(model.getBorderWidth()*2)+1,
            h*nl+(model.getBorderWidth()*2)+1
            );
        return d;
    }

    @Override
    public void paint(Graphics g)
    {
        //debug("paint("+g+")");
        //debug("cursor="+he.getCursorPos()+" buff.length="+he.getBuffer().getLength());
        Dimension d=getMinimumSize();
        g.setColor(Color.white);
        g.fillRect(0,0,d.width,d.height);
        g.setColor(Color.black);

        g.setFont( model.getFont() );

        int ini=model.getIntroduction()*16;
        int fin=ini+(model.getDisplayLinesCount()*16);
        if(fin>model.getBuffer().getLength()) {
            fin=model.getBuffer().getLength();
            }

        //datos hex
        int x=0;
        int y=0;
        for(int n=ini;n<fin;n++) {
            if(n==model.getCursorPos()) {
                if(hasFocus()) {
                    g.setColor(Color.black);
                    model.drawBackground(g,(x*3),y,2);
                    g.setColor(Color.blue);
                    model.drawBackground(g,(x*3)+cursor,y,1);
                    }
                else {
                    g.setColor(Color.blue);
                    model.drawTable(g,(x*3),y,2);
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

            String s=("0"+Integer.toHexString(model.getBuffer().getByte( n ) ));
            s=s.substring(s.length()-2);
            model.printString(g,s,((x++)*3),y);
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
        FontMetrics fn = model.getFontMetrics();
        x=x/((fn.stringWidth(" ")+1)*3);
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
        //debug("keyTyped("+e+")");

        char c=e.getKeyChar();
        if(((c>='0')&&(c<='9'))||((c>='A')&&(c<='F'))||((c>='a')&&(c<='f')))
        {
            char[] str=new char[2];
            String n="00"+Integer.toHexString((int)model.getBuffer().getByte( model.getCursorPos() ));

            if(n.length()>2) {
                n=n.substring(n.length()-2);
                }
            str[1-cursor]=n.charAt(1-cursor);
            str[cursor]=e.getKeyChar();
            //he.getBuffer()[he.getCursorPos()]=(byte)Integer.parseInt(new String(str),16);

            @SuppressWarnings("resource") // Should not be closed here.
            ArrayReadWriteAccess buff = model.getBufferRW();

            if( buff != null ) {
                buff.setByte(
                        model.getCursorPos(),
                        (byte)Integer.parseInt(new String(str),16)
                        );
                }

            if(cursor!=1) {
                cursor=1;
                }
            else if(model.getCursorPos()!=(model.getBuffer().getLength()-1)) {
                model.incCursorPos();
                cursor=0;
                }
            model.updateCursor();
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
