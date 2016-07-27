package com.googlecode.cchlib.servlet.simple.debug;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.googlecode.cchlib.servlet.simple.ChainingHttpServletResponse;

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
