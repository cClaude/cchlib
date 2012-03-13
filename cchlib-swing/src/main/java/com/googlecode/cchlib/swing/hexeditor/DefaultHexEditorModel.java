package com.googlecode.cchlib.swing.hexeditor;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JComponent;


/**
 * TODOC
 *
 */
public class DefaultHexEditorModel implements HexEditorModel
{
    private final static Font customFont=new Font("Monospaced",0,12);
    private final static int BORDER_DEFAULT = 2;
    private final static int border = BORDER_DEFAULT;
    private JComponent rootComponent;
    private int displayLinesCount = 10;
    private int introduction;// inicio
    private ArrayReadAccess arrayAccess;
    private int cursor;
    private FontMetrics _fontMetrics;
    private ArrayReadWriteAccess arrayAccessRW;

    /**
     * TODOC
     * @param mainComponent
     */
    public DefaultHexEditorModel()
    {
        this.arrayAccess = new EmptyArray();
    }

    @Override
    public void setRootComponent(
        final JComponent rootComponent
        )
    {
        this.rootComponent = rootComponent;
    }

    /**
     * TODOC
     * @param arrayAccess ArrayReadWriteAccess or ArrayReadAccess
     */
    public void setArrayAccess(
        final ArrayReadAccess arrayAccess
        )
    {
        if( arrayAccess instanceof ArrayReadWriteAccess ) {
            this.arrayAccessRW = (ArrayReadWriteAccess) arrayAccess;
            }
        else {
            this.arrayAccessRW = null;
            }
        this.arrayAccess = arrayAccess;

        //private JComponent rootComponent;
        //private int displayLinesCount = 10;
        //
        this.introduction = 0;
        this.cursor       = 0;

        repaintAll();
    }

    //@Override
    public void updateCursor()
    {
        int n=(cursor/16);

//        System.out.print("- "+inicio+"<"+n+"<"+(linesCount+inicio)+"("+linesCount+")");

        if(n<introduction) {
            introduction=n;
            }
        else if(n>=introduction+displayLinesCount) {
            introduction=n-(displayLinesCount-1);
            }

//        System.out.println(" - "+inicio+"<"+n+"<"+(linesCount+inicio)+"("+linesCount+")");

        repaintAll();
    }

    @Override
    public int getDisplayLinesCount()
    {
        return displayLinesCount;
    }

    @Override
    public int getIntroduction()
    {
        return introduction;
    }

    @Override
    public ArrayReadAccess getBuffer()
    {
        return this.arrayAccess;
    }

    @Override
    public ArrayReadWriteAccess getBufferRW()
    {
        return this.arrayAccessRW;
    }

    @Override
    public int getBorderWidth()
    {
        return border;
    }

    @Override
    public int getCursorPos()
    {
        return this.cursor;
    }

    @Override
    public void setCursorPos( int index )
    {
        this.cursor = index;
    }

    @Override
    public void incCursorPos()
    {
        this.cursor++;
    }

    @Override
    public void drawBackground(Graphics g,int x,int y,int s)
    {
        FontMetrics fn=getFontMetrics();
        g.fillRect(((fn.stringWidth(" ")+1)*x)+border,(fn.getHeight()*y)+border,((fn.stringWidth(" ")+1)*s),fn.getHeight()+1);
    }

    @Override
    public void drawTable(Graphics g,int x,int y,int s)
    {
        FontMetrics fn=getFontMetrics();
        g.drawRect(((fn.stringWidth(" ")+1)*x)+border,(fn.getHeight()*y)+border,((fn.stringWidth(" ")+1)*s),fn.getHeight()+1);
    }

    @Override
    public void printString(Graphics g,String s,int x,int y)
    {
        FontMetrics fn=getFontMetrics();
        g.drawString(s,((fn.stringWidth(" ")+1)*x)+border,((fn.getHeight()*(y+1))-fn.getMaxDescent())+border);
    }

    @Override
    public Font getFont()
    {
        return customFont;
    }

    @Override
    public FontMetrics getFontMetrics()
    {
        if( _fontMetrics == null ) {
            _fontMetrics = rootComponent.getFontMetrics( getFont() );
            }

        return _fontMetrics;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case 33:    // rep
                if(cursor>=(16*displayLinesCount)) {
                    cursor-=(16*displayLinesCount);
                    }
                updateCursor();
                break;
            case 34:    // fin
                if( cursor<(arrayAccess.getLength()-(16*displayLinesCount))) {
                    cursor+=(16*displayLinesCount);
                    }
                updateCursor();
                break;
            case 35:    // fin
                cursor=arrayAccess.getLength()-1;
                updateCursor();
                break;
            case 36:    // ini
                cursor=0;
                updateCursor();
                break;
            case 37:    // <--
                if(cursor!=0) {
                    cursor--;
                    }
                updateCursor();
                break;
            case 38:    // <--
                if(cursor>15) {
                    cursor-=16;
                    }
                updateCursor();
                break;
            case 39:    // -->
                if(cursor!=(arrayAccess.getLength()-1)) {
                    cursor++;
                    }
                updateCursor();
                break;
            case 40:    // -->
                if(cursor<(arrayAccess.getLength()-16)) {
                    cursor+=16;
                    }
                updateCursor();
                break;
        }
    }

    @Override
    public void repaintAll()
    {
        if( rootComponent != null ) {
            rootComponent.repaint();
            }
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent event)
    {
        this.introduction = event.getValue();

        if( this.introduction < 0 ) {
            this.introduction = 0;
            }
        //repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event)
    {
        introduction += (event.getUnitsToScroll());

        if( (introduction + displayLinesCount ) >= arrayAccess.getLength()/16 ) {
            introduction=(arrayAccess.getLength()/16)-displayLinesCount;
            }

        if( introduction<0 ) {
            introduction=0;
            }
        //repaint();
    }

    @Override
    public void adjustingScrollBarValues(Rectangle rec)
    {
        FontMetrics fn = getFontMetrics();

        displayLinesCount= (rec.height / fn.getHeight() ) - 1;
        int n = (arrayAccess.getLength() /16 ) - 1;

        if( displayLinesCount>n ) {
            displayLinesCount = n;
            introduction      = 0;
            }
    }

}
