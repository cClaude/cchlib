package com.googlecode.cchlib.swing.hexeditor;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import org.apache.log4j.Logger;

/**
 *
 */
public class JHexEditor
    extends JPanel
        implements FocusListener,
                   AdjustmentListener,
                   MouseWheelListener
{
    private static final long serialVersionUID = 2L;
    private static final Logger LOGGER = Logger.getLogger( JHexEditor.class );

    private JScrollBar sb;
    private RowPanel rows;
    private final HexEditorModel model;

    public JHexEditor(
        final HexEditorModel model
        )
    {
        this.model = model;
        this.model.setRootComponent( this );
        addMouseWheelListener( this );

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        ColumnPanel columns = new ColumnPanel( getModel() );
        GridBagConstraints gbc_columns = new GridBagConstraints();
        gbc_columns.fill = GridBagConstraints.HORIZONTAL;
        gbc_columns.insets = new Insets(0, 0, 5, 5);
        gbc_columns.gridx = 1;
        gbc_columns.gridy = 0;
        add(columns, gbc_columns);

        rows = new RowPanel( getModel() );
        GridBagConstraints gbc_rows = new GridBagConstraints();
        gbc_rows.fill = GridBagConstraints.VERTICAL;
        gbc_rows.insets = new Insets(0, 0, 0, 5);
        gbc_rows.gridx = 0;
        gbc_rows.gridy = 1;
        add(rows, gbc_rows);

        JHexEditorASCII hexEditorASCII = new JHexEditorASCII( getModel(),this);
        GridBagConstraints gbc_hexEditorASCII = new GridBagConstraints();
        gbc_hexEditorASCII.fill = GridBagConstraints.BOTH;
        gbc_hexEditorASCII.insets = new Insets(0, 0, 0, 5);
        gbc_hexEditorASCII.gridx = 2;
        gbc_hexEditorASCII.gridy = 1;
        add(hexEditorASCII, gbc_hexEditorASCII);

        JHexEditorHEX hewEditorHEX = new JHexEditorHEX( getModel(),this);
        GridBagConstraints gbc_hewEditorHEX = new GridBagConstraints();
        gbc_hewEditorHEX.fill = GridBagConstraints.BOTH;
        gbc_hewEditorHEX.insets = new Insets(0, 0, 0, 5);
        gbc_hewEditorHEX.gridx = 1;
        gbc_hewEditorHEX.gridy = 1;
        add(hewEditorHEX, gbc_hewEditorHEX);

        sb = new JScrollBar(JScrollBar.VERTICAL);
        sb.addAdjustmentListener(this);
        sb.setMinimum( 0 );
        try {
            sb.setMaximum(
                    getModel().getBuffer().getLength() / getModel().getDisplayLinesCount()
                    );
            }
        catch( ArithmeticException e ) {
            e.printStackTrace(); // TODO: remove this !
            sb.setMaximum( 0 );
            }
        GridBagConstraints gbc_sb = new GridBagConstraints();
        gbc_sb.fill = GridBagConstraints.VERTICAL;
        gbc_sb.gridx = 3;
        gbc_sb.gridy = 1;
        add(sb, gbc_sb);

    }

    public HexEditorModel getModel()
    {
        return model;
    }

    @Override
    public void paint(Graphics g)
    {
        Rectangle rec = this.getBounds();
        getModel().adjustingScrollBarValues( rec );
        //FontMetrics fn=getFontMetrics(getCustomFont());
//        FontMetrics fn  = getModel().getFontMetrics();
//
//        displayLinesCount=(rec.height/fn.getHeight())-1;
//        int n=(arrayAccess.getLength()/16)-1;
//
//        if( displayLinesCount>n ) {
//            displayLinesCount=n;
//            inicio=0;
//            }

        sb.setValues(
                getModel().getIntroduction(),
                getModel().getDisplayLinesCount(),
                0,
                getModel().getBuffer().getLength()/16
                );
        sb.setValueIsAdjusting(true);
        super.paint(g);
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
//        inicio=e.getValue();
//
//        if(inicio<0) {
//            inicio=0;
//            }
//        repaint();
        getModel().adjustmentValueChanged( e );
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        LOGGER.info( "mouseWheelMoved" );
//        inicio+=(e.getUnitsToScroll());
//        if((inicio+displayLinesCount)>=arrayAccess.getLength()/16) {
//            inicio=(arrayAccess.getLength()/16)-displayLinesCount;
//            }
//        if(inicio<0) {
//            inicio=0;
//            }
//        repaint();
        getModel().mouseWheelMoved( e );
        repaint();
    }

}
