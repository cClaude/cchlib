package com.googlecode.cchlib.servlet.simple.debug;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import org.apache.log4j.Logger;

/**
 * This class work like {@link ServletResponseWrapper} but add debug
 * traces (using log4j) of each call.
 * <p>
 * Provides a convenient implementation of the ServletResponse interface
 * that can be subclassed by developers wishing to adapt the response
 * from a Servlet. This class implements the Wrapper or Decorator pattern.
 * <p>
 * Methods default to calling through to the wrapped response object.
 *
 * @see ServletResponseWrapper
 */
public class DebugServletResponseWrapper implements ServletResponse
{
    private static final Logger LOGGER = Logger.getLogger( DebugServletResponseWrapper.class );

    private final ServletResponse response;

    /**
     * Creates a ServletResponse adaptor wrapping the given response object.
     *
     * @param response
     *            Current {@link ServletResponse}
     */
    public DebugServletResponseWrapper( final ServletResponse response )
    {
        this.response = response;
    }

    @Override
    public void flushBuffer() throws IOException
    {
        LOGGER.debug( "flushBuffer()" );

        this.response.flushBuffer();
    }

    @Override
    public int getBufferSize()
    {
        LOGGER.debug( "getBufferSize()" );

        return this.response.getBufferSize();
    }

    @Override
    public String getCharacterEncoding()
    {
        LOGGER.debug( "getCharacterEncoding()" );

        return this.response.getCharacterEncoding();
    }

    @Override
    public String getContentType()
    {
        LOGGER.debug( "getContentType()" );

        return this.response.getContentType();
    }

    @Override
    public Locale getLocale()
    {
        LOGGER.debug( "getLocale()" );

        return this.response.getLocale();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException
    {
        LOGGER.debug( "getOutputStream()" );

        return this.response.getOutputStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException
    {
        LOGGER.debug( "getWriter()" );

        return this.response.getWriter();
    }

    @Override
    public boolean isCommitted()
    {
        LOGGER.debug( "isCommitted()" );

        return this.response.isCommitted();
    }

    @Override
    public void reset()
    {
        LOGGER.debug( "reset()" );

        this.response.reset();
    }

    @Override
    public void resetBuffer()
    {
        LOGGER.debug( "resetBuffer()" );

        this.response.resetBuffer();
    }

    @Override
    public void setBufferSize( final int size )
    {
        LOGGER.debug( "setBufferSize(" + size + ")" );

        this.response.setBufferSize(size);
    }

    @Override
    public void setCharacterEncoding( final String charset )
    {
        LOGGER.debug( "setCharacterEncoding(" + charset + ")" );

        this.response.setCharacterEncoding(charset);
    }

    @Override
    public void setContentLength( final int len )
    {
        LOGGER.debug( "setContentLength(" + len + ")" );

        this.response.setContentLength(len);
    }

    @Override
    public void setContentType( final String type )
    {
        LOGGER.debug( "setContentType(" + type + ")" );

        this.response.setContentType(type);
    }

    @Override
    public void setLocale( final Locale locale )
    {
        LOGGER.debug( "setLocale(" + locale + ")" );

        this.response.setLocale(locale);
    }

    @Override
    public void setContentLengthLong( final long length )
    {
        LOGGER.debug( "setContentLengthLong(" + length + ")" );

        this.response.setContentLengthLong( length );
    }
}
