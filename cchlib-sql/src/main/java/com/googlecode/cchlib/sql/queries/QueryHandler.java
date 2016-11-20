package com.googlecode.cchlib.sql.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.googlecode.cchlib.sql.SimpleSQL;

/**
 * Functional interface for {@link SimpleSQL#executeQuery(String, QueryHandler)}
 *
 * @param <T> Type expected by {@link SimpleSQL#executeQuery(String, QueryHandler)}
 *
 * @since 4.2
 */
@FunctionalInterface
public interface QueryHandler<T>
{
    /**
     * User function than transform <code>resultSet</code> into an
     * action or a computed <code>T</code> result.
     *
     * @param resultSet Result set return by SQL query.
     * @return Computed result from custom <code>resultSet</code> analysis
     * @throws SQLException
     * @throws QueryHandlerException
     */
    @SuppressWarnings("squid:S1160")
    T handle( ResultSet resultSet ) throws SQLException, QueryHandlerException;
}
