package com.googlecode.cchlib.sql.mysql;

import cx.ath.choisnet.util.ExternalAppException;

/**
 * NEEDDOC
 */
public class MySQLAdminException extends ExternalAppException
{
    private static final long serialVersionUID = 1L;

    public MySQLAdminException(final String msg)
    {
        super(msg);
    }

    public MySQLAdminException(final Throwable cause)
    {
        super(cause.getMessage(), cause);
    }

    public MySQLAdminException(final String msg, final Throwable cause)
    {
        super(msg, cause);
    }
}
