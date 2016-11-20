package com.googlecode.cchlib.sql;

import java.sql.SQLException;

/**
 * Wrapper for {@link ClassNotFoundException} into {@link SQLException}
 *
 * Handle drivers issues within SQL exceptions.
 */
public class DataSourceFactoryClassNotFoundException extends SQLException
{
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a <code>DataSourceFactoryClassNotFoundException</code>
     * object with a given <code>driverClassName</code> and <code>cause</code>.
     * The <code>SQLState</code> is  initialized to <code>null</code>
     * and the vendor code is initialized to 0.
     * <p>
     * @param driverClassName class name of the driver class
     * @param cause the underlying reason
     */
    public DataSourceFactoryClassNotFoundException( final String driverClassName, final ClassNotFoundException cause )
    {
        super( driverClassName, cause );
    }
}
