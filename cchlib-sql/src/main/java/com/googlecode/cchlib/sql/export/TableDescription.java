package com.googlecode.cchlib.sql.export;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Describe a SQL Table giving the name and optionally the where clause.
 *
 * <table border="2">
 * <caption>SQL vs Java</caption>
 * <tr>
 * <th>To produce</th>
 * <th>getName() must return</th>
 * <th>getWhereCondition() must return</th>
 * </tr>
 * <tr>
 * <td>SELECT * FROM `MyTable` WHERE `col1`='A' AND `col2`=3;</td>
 * <td>"MyTable"</td>
 * <td>"`col1`='A' AND `col2`=3"</td>
 * </tr>
 * <tr>
 * <td>SELECT * FROM `MyTable`;</td>
 * <td>"MyTable"</td>
 * <td>null</td>
 * </tr>
 * </table>
 *
 * @see ExportSQL
 * @see TableDescriptionHelper
 */
public interface TableDescription
{
    /**
     * Returns the name of the table to export
     * @return the table name, implementation should nevez return null
     */
    @Nonnull
    String getName();

    /**
     * Returns the SQL where clause condition to select values to export.
     * @return the SQL where condition
     */
    @Nullable
    String getWhereCondition();
}
