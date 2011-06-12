package cx.ath.choisnet.sql.export;

import java.io.Serializable;

/**
 * Default implementation of {@link TableDescription}
 */
public final class TableDescriptionImpl implements TableDescription, Serializable
{
    private static final long serialVersionUID = 1L;
    private String name;
    private String whereCondition;

    /**
     * Create a {@link TableDescription} without condition, export all values
     *
     * @param name Name of the table
     */
    public TableDescriptionImpl( final String name )
    {
        this(name, null);
    }

    /**
     * Create a {@link TableDescription} with conditions, export values
     * according to where clause
     *
     * @param name Name of the table
     * @param whereCondition SQL Where clause or null
     */
    public TableDescriptionImpl(
            final String name,
            final String whereCondition
            )
    {
        this.name = name;
        this.whereCondition = whereCondition;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String getWhereCondition()
    {
        return whereCondition;
    }
}