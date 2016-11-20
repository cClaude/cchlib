package com.googlecode.cchlib.servlet.simple;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Abstract class that extend {@link HttpServletResponseWrapper} and
 * implement {@link ChainingHttpServletResponse}.
 *
 */
public abstract class ChainingHttpServletResponseWrapper
    extends HttpServletResponseWrapper
        implements ChainingHttpServletResponse
{
    /**
     * Creates a ServletResponse adaptor wrapping the given response object.
     * @param response ServletResponse to wrap
     * @throws IllegalArgumentException  if the response is null.
     */
    public ChainingHttpServletResponseWrapper( HttpServletResponse response )
    {
        super( response );
    }

    @Override
    public abstract void finishResponse() throws IOException;
}
