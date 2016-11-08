package com.googlecode.cchlib.swing.table;

/**
 * Allow to customize {@link JTableColumnsAutoSizer} computed width
 *
 * @since 4.1.8
 * @see JTableColumnsAutoSizer
 */
public interface ForceColumnWidthModel
{
    /**
     * Define if column have is own algorithm to compute width
     * @param columnIndex the index of the desired column
     * @return true if column have is own algorithm to compute width
     */
    boolean isWidthFor( int columnIndex );

    /**
     * Returns width for column
     * @param columnIndex the index of the desired column
     * @return width for column
     */
    int getWidthFor( int columnIndex );

    /**
     * Returns index of column that should receive remaining space from table,
     * if return value is not a valid column index, remaining space (if any) is not used
     * @return index of column that should receive remaining space from table or -1
     */
    int getRemainingColumnIndex();
}
