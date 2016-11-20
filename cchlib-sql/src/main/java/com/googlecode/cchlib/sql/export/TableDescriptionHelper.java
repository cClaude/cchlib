package com.googlecode.cchlib.sql.export;

import java.util.Collection;

/**
 * Helper to create {@link TableDescription}
 */
public class TableDescriptionHelper
{
    private TableDescriptionHelper()
    {
        // All static
    }

    /**
     * Transform a table name to a {@link TableDescription}
     * @param tablename string describing table name
     * @return a {@link TableDescription}
     */
    public static TableDescription toTableDescription(
        final String tablename
        )
    {
        return new TableDescriptionImpl( tablename );
    }

    /**
     * Transform an array of table names to an array of {@link TableDescription}
     * @param tablenames an array of string describing table names .
     * @return an array of {@link TableDescription}
     */
    public static TableDescription[] toTableDescriptions(
            final String[] tablenames
            )
    {
        final TableDescription[] desc = new TableDescription[ tablenames.length ];

        for(int i = 0; i<tablenames.length; i++ ) {
            desc[ i ] = toTableDescription( tablenames[i] );
            }

        return desc;
    }

    /**
     * Transform a {@link Collection} of table names to an array of {@link TableDescription}
     * @param tablenames a {@link Collection} of string describing table names .
     * @return an array of {@link TableDescription}
     */
    public static TableDescription[] toTableDescriptions(
            final Collection<String> tablenames
            )
    {
        final TableDescription[] desc = new TableDescription[ tablenames.size() ];
        int index = 0;

        for( final String tablename : tablenames ) {
            desc[ index++ ] = toTableDescription( tablename );
            }

        return desc;
    }
}
