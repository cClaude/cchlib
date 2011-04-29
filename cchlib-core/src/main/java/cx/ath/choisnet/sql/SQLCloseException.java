package cx.ath.choisnet.sql;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Convert {@link SQLException} to  {@link IOException} to
 * be able create method close()
 * matching with {@link java.io.Closeable#close()}
 *
 * @author Claude CHOISNET
 */
public class SQLCloseException extends IOException
{
    private static final long serialVersionUID = 1L;

    protected SQLCloseException()
    {
    }

    public SQLCloseException( SQLException sqlException )
    {
        super( sqlException );
    }

    public SQLCloseException( String message, SQLException sqlException )
    {
        super( message, sqlException );
    }

}
