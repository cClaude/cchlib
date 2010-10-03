package cx.ath.choisnet.sql.mysql;

import cx.ath.choisnet.util.ExternalAppException;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class MySQLAdminException extends ExternalAppException
{
    private static final long serialVersionUID = 1L;

    public MySQLAdminException(String msg)
    {
        super(msg);
    }

    public MySQLAdminException(Throwable cause)
    {
        super(cause.getMessage(), cause);
    }

    public MySQLAdminException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
