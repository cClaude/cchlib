package cx.ath.choisnet.xml;

import java.io.IOException;

class XMLBuilderRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public XMLBuilderRuntimeException( final IOException cause )
    {
        super( cause );
    }
}
