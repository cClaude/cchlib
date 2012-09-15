package cx.ath.choisnet.sql;

import java.sql.SQLException;

/**
 * Encapsulation SQLException and add original SQL query
 * 
 * @since 4.1.3
 */
public class ExtendedSQLException extends SQLException
{
    private static final long serialVersionUID = 1L;
    private String sqlQuery;

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
        return sqlQuery;
    }

    @Override
    public String getMessage()
    {
        return super.getMessage() + " cause by " + getSQLQuery();
    }

}
