package com.googlecode.cchlib.servlet.simple.debug;

import javax.servlet.http.HttpServletResponse;
import com.googlecode.cchlib.servlet.simple.ChainingHttpServletResponse;

/**
 * NEEDDOC
 */
public abstract class DebugChainingHttpServletResponseWrapper
    extends DebugHttpServletResponseWrapper
        implements ChainingHttpServletResponse
{
    /**
     * NEEDDOC
     *
     * @param response NEEDDOC
     */
    public DebugChainingHttpServletResponseWrapper( final HttpServletResponse response )
    {
        super( response );
    }
}
