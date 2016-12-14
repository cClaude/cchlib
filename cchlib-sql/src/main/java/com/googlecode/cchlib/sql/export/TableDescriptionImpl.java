package com.googlecode.cchlib.sql.export;

import java.io.Serializable;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Default implementation of {@link TableDescription}
 */
public final class TableDescriptionImpl implements TableDescription, Serializable
{
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String whereCondition;

    /**
     * Create a {@link TableDescription} without condition, export all values
     *
     * @param name
     *            Name of the table
     */
    public TableDescriptionImpl( final String name )
    {
        this( name, null );
    }

    /**
     * Create a {@link TableDescription} with conditions, export values
     * according to where clause
     *
     * @param name
     *            Name of the table
     * @param whereCondition
     *            SQL Where clause or null
     */
    public TableDescriptionImpl(
        @Nonnull  final String name,
        @Nullable final String whereCondition
        )
    {
        if( name == null ) {
            throw new IllegalArgumentException( "name is null" );
        }

        this.name           = name;
        this.whereCondition = whereCondition;
    }

    @Override
    @Nonnull
    public String getName()
    {
        return this.name;
    }

    @Override
    @Nullable
    public String getWhereCondition()
    {
        return this.whereCondition;
    }
}