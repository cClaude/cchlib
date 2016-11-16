package cx.ath.choisnet.util;

import java.io.IOException;

class FormattedPropertiesRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public FormattedPropertiesRuntimeException( final String message )
    {
        super( message );
    }

    public FormattedPropertiesRuntimeException( final IOException cause )
    {
        super( cause );
    }
}
