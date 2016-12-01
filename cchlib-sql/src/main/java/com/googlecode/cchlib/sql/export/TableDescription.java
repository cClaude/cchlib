package com.googlecode.cchlib.sql.export;

/**
 * Describe a Table giving the name and optionally the where clause.
 *
 * <table border="2">
 * <tr><th>To produce</th><th>getName() must return</th><th>getWhereCondition() must return</th></tr>
 * <tr><td>SELECT * FROM `MyTable` WHERE `col1`='A' AND `col2`=3;</td><td>"MyTable"</td><td>"`col1`='A' AND `col2`=3"</td></tr>
 * <tr><td>SELECT * FROM `MyTable`;</td><td>"MyTable"</td><td>null</td></tr>
 * </table>
 *
 * @see ExportSQL
 * @see TableDescriptionHelper
 */
public interface TableDescription
{
    /**
     * Returns the name of the table to export
     * @return the table name
     */
    String getName();

    /**
     * Returns the SQL where clause condition to select values to export.
     * @return the SQL where condition
     */
    String getWhereCondition();

}
