package cx.ath.choisnet.servlet.debug;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import cx.ath.choisnet.servlet.ChainingHttpServletResponse;

/**
 *
 */
public abstract class DebugChainingHttpServletResponseWrapper
    extends DebugHttpServletResponseWrapper
        implements ChainingHttpServletResponse
{
    /**
     * @param response
     */
    public DebugChainingHttpServletResponseWrapper( HttpServletResponse response )
    {
        super( response );
    }

    @Override
    public abstract void finishResponse() throws IOException;
}
