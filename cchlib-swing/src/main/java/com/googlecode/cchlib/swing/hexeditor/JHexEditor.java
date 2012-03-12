package com.googlecode.cchlib.swing.hexeditor;

import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 */
public class JHexEditor
    extends JPanel
        implements FocusListener,AdjustmentListener,MouseWheelListener,
                   HexEditor
{
    private static final long serialVersionUID = 2L;
    private static Font customFont=new Font("Monospaced",0,12);
    //private byte[] buff;
    private int cursor;
    private int border=2;
    //private boolean DEBUG=false;
    private JScrollBar sb;
    private int inicio=0;
    private int lineas=10;
    private RowPanel rows;
    private ArrayReadWriteAccess arrayAccess;

    public JHexEditor(byte[] buff)
    {
        this( new DefaultArrayReadWriteAccess( buff ) );
    }

    public JHexEditor( File file ) throws FileNotFoundException
    {
        this( new ArrayReadWriteAccessFile( file ) );
    }

    public JHexEditor(
        final ArrayReadWriteAccess arrayAccess
        )
    {
        this.arrayAccess = arrayAccess;

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        ColumnPanel columns = new ColumnPanel(this);
        GridBagConstraints gbc_columns = new GridBagConstraints();
        gbc_columns.fill = GridBagConstraints.HORIZONTAL;
        gbc_columns.insets = new Insets(0, 0, 5, 5);
        gbc_columns.gridx = 1;
        gbc_columns.gridy = 0;
        add(columns, gbc_columns);

        rows = new RowPanel(this);
        GridBagConstraints gbc_rows = new GridBagConstraints();
        gbc_rows.fill = GridBagConstraints.VERTICAL;
        gbc_rows.insets = new Insets(0, 0, 0, 5);
        gbc_rows.gridx = 0;
        gbc_rows.gridy = 1;
        add(rows, gbc_rows);

        JHexEditorASCII hexEditorASCII = new JHexEditorASCII(this);
        GridBagConstraints gbc_hexEditorASCII = new GridBagConstraints();
        gbc_hexEditorASCII.fill = GridBagConstraints.BOTH;
        gbc_hexEditorASCII.insets = new Insets(0, 0, 0, 5);
        gbc_hexEditorASCII.gridx = 2;
        gbc_hexEditorASCII.gridy = 1;
        add(hexEditorASCII, gbc_hexEditorASCII);

        JHexEditorHEX hewEditorHEX = new JHexEditorHEX(this);
        GridBagConstraints gbc_hewEditorHEX = new GridBagConstraints();
        gbc_hewEditorHEX.fill = GridBagConstraints.BOTH;
        gbc_hewEditorHEX.insets = new Insets(0, 0, 0, 5);
        gbc_hewEditorHEX.gridx = 1;
        gbc_hewEditorHEX.gridy = 1;
        add(hewEditorHEX, gbc_hewEditorHEX);

        sb = new JScrollBar(JScrollBar.VERTICAL);
        GridBagConstraints gbc_sb = new GridBagConstraints();
        gbc_sb.fill = GridBagConstraints.VERTICAL;
        gbc_sb.gridx = 3;
        gbc_sb.gridy = 1;
        add(sb, gbc_sb);
    }

    @Override
    public void paint(Graphics g)
    {
        FontMetrics fn=getFontMetrics(getCustomFont());
        Rectangle rec=this.getBounds();
        lineas=(rec.height/fn.getHeight())-1;
        int n=(arrayAccess.getLength()/16)-1;
        if(lineas>n) { lineas=n; inicio=0; }

        sb.setValues(
                getInicio(),
                +getLineas(),
                0,
                arrayAccess.getLength()/16
                );
        sb.setValueIsAdjusting(true);
        super.paint(g);
    }

    protected void actualizaCursor()
    {
        int n=(cursor/16);

        System.out.print("- "+inicio+"<"+n+"<"+(lineas+inicio)+"("+lineas+")");

        if(n<inicio) inicio=n;
        else if(n>=inicio+lineas) inicio=n-(lineas-1);

        System.out.println(" - "+inicio+"<"+n+"<"+(lineas+inicio)+"("+lineas+")");

        repaint();
    }

    @Override//HexEditor
    public int getInicio()
    {
        return inicio;
    }

    @Override//HexEditor
    public int getLineas()
    {
        return lineas;
    }

//    @Override//HexEditor
//    public byte[] getBuffer()
//    {
//        return this.buff;
//    }

    @Override//HexEditor
    public ArrayReadWriteAccess getBuffer()
    {
        return this.arrayAccess;
    }

    @Override//HexEditor
    public int getBorderWidth()
    {
        return this.border;
    }

    @Override//HexEditor
    public int getCursorPos()
    {
        return this.cursor;
    }

    @Override//HexEditor
    public void setCursorPos( int index )
    {
        this.cursor = index;
    }

    @Override//HexEditor
    public void incCursorPos()
    {
        this.cursor++;
    }

    protected void fondo(Graphics g,int x,int y,int s)
    {
        FontMetrics fn=getFontMetrics(getCustomFont());
        g.fillRect(((fn.stringWidth(" ")+1)*x)+border,(fn.getHeight()*y)+border,((fn.stringWidth(" ")+1)*s),fn.getHeight()+1);
    }

    @Override//HexEditor
    public void cuadro(Graphics g,int x,int y,int s)
    {
        FontMetrics fn=getFontMetrics(getCustomFont());
        g.drawRect(((fn.stringWidth(" ")+1)*x)+border,(fn.getHeight()*y)+border,((fn.stringWidth(" ")+1)*s),fn.getHeight()+1);
    }

    @Override//HexEditor
    public void printString(Graphics g,String s,int x,int y)
    {
        FontMetrics fn=getFontMetrics(getCustomFont());
        g.drawString(s,((fn.stringWidth(" ")+1)*x)+border,((fn.getHeight()*(y+1))-fn.getMaxDescent())+border);
    }

    @Override//HexEditor
    public Font getCustomFont()
    {
        return customFont;
    }

    @Override
    public void focusGained(FocusEvent e)
    {
        this.repaint();
    }

    @Override
    public void focusLost(FocusEvent e)
    {
        this.repaint();
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        inicio=e.getValue();
        if(inicio<0) inicio=0;
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        inicio+=(e.getUnitsToScroll());
        if((inicio+lineas)>=arrayAccess.getLength()/16) inicio=(arrayAccess.getLength()/16)-lineas;
        if(inicio<0) inicio=0;
        repaint();
    }

    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case 33:    // rep
                if(cursor>=(16*lineas)) cursor-=(16*lineas);
                actualizaCursor();
                break;
            case 34:    // fin
                if(cursor<(arrayAccess.getLength()-(16*lineas))) cursor+=(16*lineas);
                actualizaCursor();
                break;
            case 35:    // fin
                cursor=arrayAccess.getLength()-1;
                actualizaCursor();
                break;
            case 36:    // ini
                cursor=0;
                actualizaCursor();
                break;
            case 37:    // <--
                if(cursor!=0) cursor--;
                actualizaCursor();
                break;
            case 38:    // <--
                if(cursor>15) cursor-=16;
                actualizaCursor();
                break;
            case 39:    // -->
                if(cursor!=(arrayAccess.getLength()-1)) cursor++;
                actualizaCursor();
                break;
            case 40:    // -->
                if(cursor<(arrayAccess.getLength()-16)) cursor+=16;
                actualizaCursor();
                break;
        }
    }
}
