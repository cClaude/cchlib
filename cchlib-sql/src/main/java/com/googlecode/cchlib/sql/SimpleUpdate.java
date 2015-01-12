package com.googlecode.cchlib.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

/**
 * Execute SQL update statement using {@link DataSource}
 * <p>
 * This class is not indented to use for application
 * that use many SQL query. See {@link ConnectionQuery}
 * if you need to reuse {@link Connection}
 * </p>
 *
 * @see SimpleQuery
 * @see ConnectionQuery
 */
public class SimpleUpdate
    extends SimpleDataSource
{

    /**
     *  Create a SimpleUpdate object from a valid {@link DataSource}
     *
     * @param ds DataSource to use.
     * @throws NullPointerException if ds is null.
     */
    public SimpleUpdate(final DataSource ds)
    {
        super(ds);
    }

    /**
     *  Create a SimpleUpdate object from a valid {@link DataSource}
     *
     * @param resourceName DataSource name
     * @throws SimpleDataSourceException if any
     */
    public SimpleUpdate(final String resourceName)
        throws SimpleDataSourceException
    {
        super( SimpleDataSource.createDataSource(resourceName) );
    }

    /**
     *  Execute an SQL query
     *
     * @param query SQL query to execute
     * @return count of modified rows
     * @throws SQLException if any
     */
    public int doUpdate( final String query )
        throws SQLException
    {
        int rows = -1;

        try( final Connection  conn = createConnectionFromDataSource() ) {
            try( final Statement stmt = conn.createStatement() ) {
                rows = stmt.executeUpdate( query );
                }
            }

        return rows;
    }
}
