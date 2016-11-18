package com.googlecode.cchlib.swing.hexeditor;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import javax.swing.JComponent;
import org.apache.log4j.Logger;


/**
 * NEEDDOC
 *
 */
public class DefaultHexEditorModel implements HexEditorModel
{
    private static final Logger LOGGER = Logger.getLogger( DefaultHexEditorModel.class );
    private static final Font customFont=new Font("Monospaced",0,12);
    private static final int BORDER_DEFAULT = 2;

    private final int border = BORDER_DEFAULT; // Warn never change

    private JComponent rootComponent;
    private int displayLinesCount = 10;
    private int introduction;// inicio
    private ArrayReadAccess arrayAccess;
    private int cursor;
    private FontMetrics _fontMetrics;
    private ArrayReadWriteAccess arrayAccessRW;

    /**
     * NEEDDOC
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
     * NEEDDOC
     * @param arrayAccess ArrayReadWriteAccess or ArrayReadAccess
     */
    public void setArrayAccess(
        final ArrayReadAccess arrayAccess
        )
    {
        LOGGER.trace( "setArrayAccess: " + arrayAccess );

        if( arrayAccess instanceof ArrayReadWriteAccess ) {
            this.arrayAccessRW = (ArrayReadWriteAccess) arrayAccess;
            }
        else {
            this.arrayAccessRW = null;
            }

        this.arrayAccess  = arrayAccess;
        this.introduction = 0;
        this.cursor       = 0;

        repaintAll();
    }

    //@Override
    @Override
    public void updateCursor()
    {
        final int n = this.cursor / 16;

        if(n<this.introduction) {
            this.introduction=n;
            }
        else if(n>=(this.introduction+this.displayLinesCount)) {
            this.introduction=n-(this.displayLinesCount-1);
            }

        repaintAll();
    }

    @Override
    public int getDisplayLinesCount()
    {
        return this.displayLinesCount;
    }

    @Override
    public int getIntroduction()
    {
        return this.introduction;
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
        return this.border;
    }

    @Override
    public int getCursorPos()
    {
        return this.cursor;
    }

    @Override
    public void setCursorPos( final int index )
    {
        this.cursor = index;
    }

    @Override
    public void incCursorPos()
    {
        this.cursor++;
    }

    @Override
    public void drawBackground(final Graphics g,final int x,final int y,final int s)
    {
        final FontMetrics fn=getFontMetrics();

        g.fillRect(
                ((fn.stringWidth(" ")+1)*x)+this.border,
                (fn.getHeight()*y)+this.border,
                (fn.stringWidth(" ")+1)*s,
                fn.getHeight()+1
                );
    }

    @Override
    public void drawTable(final Graphics g,final int x,final int y,final int s)
    {
        final FontMetrics fn=getFontMetrics();

        g.drawRect(
                ((fn.stringWidth(" ")+1)*x)+this.border,
                (fn.getHeight()*y)+this.border,
                (fn.stringWidth(" ")+1)*s,
                fn.getHeight()+1
                );
    }

    @Override
    public void printString(final Graphics g,final String s,final int x,final int y)
    {
        final FontMetrics fn=getFontMetrics();
        g.drawString(s,((fn.stringWidth(" ")+1)*x)+this.border,((fn.getHeight()*(y+1))-fn.getMaxDescent())+this.border);
    }

    @Override
    public Font getFont()
    {
        return customFont;
    }

    @Override
    public FontMetrics getFontMetrics()
    {
        if( this._fontMetrics == null ) {
            this._fontMetrics = this.rootComponent.getFontMetrics( getFont() );
            }

        return this._fontMetrics;
    }

    @Override
    @SuppressWarnings("squid:MethodCyclomaticComplexity")
    public void keyPressed(final KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case 33:    // rep
                if(this.cursor>=(16*this.displayLinesCount)) {
                    this.cursor-=(16*this.displayLinesCount);
                    }
                updateCursor();
                break;
            case 34:    // fin
                if( this.cursor<(this.arrayAccess.getLength()-(16*this.displayLinesCount))) {
                    this.cursor+=(16*this.displayLinesCount);
                    }
                updateCursor();
                break;
            case 35:    // fin
                this.cursor=this.arrayAccess.getLength()-1;
                updateCursor();
                break;
            case 36:    // ini
                this.cursor=0;
                updateCursor();
                break;
            case 37:    // <--
                if(this.cursor!=0) {
                    this.cursor--;
                    }
                updateCursor();
                break;
            case 38:    // <--
                if(this.cursor>15) {
                    this.cursor-=16;
                    }
                updateCursor();
                break;
            case 39:    // -->
                if(this.cursor!=(this.arrayAccess.getLength()-1)) {
                    this.cursor++;
                    }
                updateCursor();
                break;
            case 40:    // -->
                if(this.cursor<(this.arrayAccess.getLength()-16)) {
                    this.cursor+=16;
                    }
                updateCursor();
                break;
        }
    }

    @Override
    public void repaintAll()
    {
        if( this.rootComponent != null ) {
            this.rootComponent.repaint();
            }
    }

    @Override
    public void adjustmentValueChanged(final AdjustmentEvent event)
    {
        this.introduction = event.getValue();

        if( this.introduction < 0 ) {
            this.introduction = 0;
            }
    }

    @Override
    public void mouseWheelMoved(final MouseWheelEvent event)
    {
        this.introduction += (event.getUnitsToScroll());

        if( (this.introduction + this.displayLinesCount ) >= (this.arrayAccess.getLength()/16) ) {
            this.introduction=(this.arrayAccess.getLength()/16)-this.displayLinesCount;
            }

        if( this.introduction<0 ) {
            this.introduction=0;
            }
    }

    @Override
    public void adjustingScrollBarValues(final Rectangle rec)
    {
        final FontMetrics fn = getFontMetrics();

        this.displayLinesCount= (rec.height / fn.getHeight() ) - 1;
        final int n = (this.arrayAccess.getLength() /16 ) - 1;

        if( this.displayLinesCount>n ) {
            this.displayLinesCount = n;
            this.introduction      = 0;
            }
    }

    @Override
    public void close() throws IOException
    {
        try {
            if( this.arrayAccess != null ) {
                this.arrayAccess.close();
                }
            }
        finally {
            if( this.arrayAccessRW != null ) {
                this.arrayAccessRW.close();
                }
            }
    }

}
