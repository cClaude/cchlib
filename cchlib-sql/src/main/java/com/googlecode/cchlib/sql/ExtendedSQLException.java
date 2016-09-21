package com.googlecode.cchlib.sql;

import java.sql.SQLException;

/**
 * Encapsulation SQLException and add original SQL query
 *
 * @since 4.1.3
 * @deprecated use {@link SimpleSQL} instead of {@link ConnectionQuery}
 */
@Deprecated
public class ExtendedSQLException extends SQLException
{
    private static final long serialVersionUID = 1L;
    private final String sqlQuery;

    /**
     * Create ExtendedSQLException
     *
     * @param sqle      Caught SQLException
     * @param sqlQuery  SQL query that generate SQLException
     */
    public ExtendedSQLException( final SQLException sqle, final String sqlQuery )
    {
        super( sqle );

        this.sqlQuery = sqlQuery;
    }

    /**
     * Returns SQL query that generate SQLException
     * @return SQL query that generate SQLException
     */
    public String getSQLQuery()
    {
        return this.sqlQuery;
    }

    @Override
    public String getMessage()
    {
        return super.getMessage() + " cause by " + getSQLQuery();
    }

}
