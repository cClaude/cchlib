package com.googlecode.cchlib.swing.table;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import com.googlecode.cchlib.NeedDoc;

/**
 * NEEDDOC
 *
 * @since 4.1.8
 */
@NeedDoc
public class JTableColumnsAutoSizer implements TableModelListener, ComponentListener
{
    public static final int DEFAULT_COLUMN_MARGIN = 5;
    private final JTable table;
    private final int columnMargin;
    private final ForceColumnWidthModel forceColumnWidthModel;

    /**
     *
     * @param table
     */
    public JTableColumnsAutoSizer( final JTable table )
    {
        this( table, DEFAULT_COLUMN_MARGIN );
    }

    /**
     *
     * @param table
     * @param columnMargin
     */
    public JTableColumnsAutoSizer( final JTable table, final int columnMargin )
    {
        this( table, columnMargin, new ForceColumnWidthModel() {
            @Override
            public boolean isWidthFor( final int columnIndex )
            {
                return false;
            }
            @Override
            public int getWidthFor( final int columnIndex )
            {
                throw new UnsupportedOperationException();
            }
            @Override
            public int getRemainingColumnIndex()
            {
                return -1;
            }});
    }

    /**
     *
     * @param table
     * @param forceColumnWidthModel
     */
    public JTableColumnsAutoSizer(
        final JTable                table,
        final ForceColumnWidthModel forceColumnWidthModel
        )
    {
        this( table, DEFAULT_COLUMN_MARGIN, forceColumnWidthModel );
    }

    /**
     *
     * @param table
     * @param columnMargin
     * @param forceColumnWidthModel
     */
    public JTableColumnsAutoSizer(
        final JTable                table,
        final int                   columnMargin,
        final ForceColumnWidthModel forceColumnWidthModel
        )
    {
        this.table                 = table;
        this.columnMargin          = columnMargin;
        this.forceColumnWidthModel = forceColumnWidthModel;
    }

    /**
     *
     */
    public void apply()
    {
        final JTableHeader tableHeader = this.table.getTableHeader();

        if( tableHeader == null ) {
            // can't auto size a table without a header
            return;
            }

        final FontMetrics headerFontMetrics = tableHeader.getFontMetrics(tableHeader.getFont());

        final int[] minWidths = new int[this.table.getColumnCount()];
        final int[] maxWidths = new int[this.table.getColumnCount()];

        for( int columnIndex = 0; columnIndex < this.table.getColumnCount(); columnIndex++ ) {

            if( this.forceColumnWidthModel.isWidthFor( columnIndex ) ) {
                minWidths[ columnIndex ] = maxWidths[ columnIndex ] = this.forceColumnWidthModel.getWidthFor( columnIndex );
                }
            else {
                final int headerWidth = headerFontMetrics.stringWidth(this.table.getColumnName(columnIndex));
                minWidths[columnIndex] = headerWidth + this.columnMargin;

                final int maxWidth = getMaximalRequiredColumnWidth( columnIndex, headerWidth );
                maxWidths[columnIndex] = Math.max(maxWidth, minWidths[columnIndex]) + this.columnMargin;
                }
            }

        adjustMaximumWidths( minWidths, maxWidths );


        final int remainingIndex = this.forceColumnWidthModel.getRemainingColumnIndex();

        for( int i = 0; i < minWidths.length; i++ ) {
            if( i == remainingIndex ) {
                maxWidths[ remainingIndex ] = 0;
                final int w = this.table.getWidth() - sum( maxWidths );
                maxWidths[ remainingIndex ] = w;
                }

            if( minWidths[i] > 0 ) {
                this.table.getColumnModel().getColumn(i).setMinWidth( minWidths[i] );
                }

            if( maxWidths[i] > 0 ) {
                this.table.getColumnModel().getColumn(i).setMaxWidth( maxWidths[i] );
                this.table.getColumnModel().getColumn(i).setWidth   ( maxWidths[i] );
                }
            }
    }

    private void adjustMaximumWidths( final int[] minWidths, final int[] maxWidths )
    {
        if( this.table.getWidth() > 0 ) {
            // to prevent infinite loops in exceptional situations
            int breaker = 0;

            // keep stealing one pixel of the maximum width of the highest column until we can fit in the width of the table
            while((sum(maxWidths) > this.table.getWidth()) && (breaker < 10000)) {
                final int highestWidthIndex = findLargestIndex(maxWidths);

                maxWidths[highestWidthIndex] -= 1;
                maxWidths[highestWidthIndex] = Math.max(maxWidths[highestWidthIndex], minWidths[highestWidthIndex]);

                breaker++;
            }
        }
    }

    private int getMaximalRequiredColumnWidth(
        final int columnIndex,
        final int headerWidth
        )
    {
        int                maxWidth     = headerWidth;
        final TableColumn        column       = this.table.getColumnModel().getColumn(columnIndex);
        TableCellRenderer  cellRenderer = column.getCellRenderer();

        if(cellRenderer == null) {
            cellRenderer = new DefaultTableCellRenderer();
            }

        for(int row = 0; row < this.table.getModel().getRowCount(); row++) {
            final Component rendererComponent = cellRenderer.getTableCellRendererComponent(
                this.table,
                this.table.getModel().getValueAt(row, columnIndex),
                false,
                false,
                row,
                columnIndex
                );

            final double valueWidth = rendererComponent.getPreferredSize().getWidth();

            maxWidth = (int) Math.max(maxWidth, valueWidth);
            }

        return maxWidth;
    }

    private static int findLargestIndex( final int[] widths )
    {
        int largestIndex = 0;
        int largestValue = 0;

        for(int i = 0; i < widths.length; i++) {
            if(widths[i] > largestValue) {
                largestIndex = i;
                largestValue = widths[i];
            }
        }

        return largestIndex;
    }

    private static int sum( final int[] widths )
    {
        int sum = 0;

        for(final int width : widths) { sum += width; }

        return sum;
    }

    /**
     * Invoke {@link #apply()}
     */
    @Override//TableModelListener
    public void tableChanged( final TableModelEvent event )
    {
        apply();
    }

    /**
     * Invoke {@link #apply()}
     */
    @Override//ComponentListener
    public void componentResized( final ComponentEvent e )
    {
        apply();
    }

    @Override//ComponentListener
    public void componentMoved( final ComponentEvent e )
    {
        // empty
    }

    /**
     * Invoke {@link #apply()}
     */
    @Override//ComponentListener
    public void componentShown( final ComponentEvent e )
    {
        apply();
    }

    @Override//ComponentListener
    public void componentHidden( final ComponentEvent e )
    {
        // empty
    }
}
